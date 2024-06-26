package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;

import java.util.*;

/**
 * Service class responsible for managing devices in the smart home domain (create,save,checking for activation status and retrieving a list of devices in a room).
 */

@Service
public class DeviceServiceImpl implements DeviceService {

    private final RoomRepository roomRepository;
    private final DeviceFactory deviceFactory;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final ActuatorRepository actuatorRepository;
    private static final String NOT_PRESENT_MESSAGE = " is not present.";

    /**
     * Constructs an instance of V1DeviceService with the provided dependencies.
     * This constructor initializes the V1DeviceService with the necessary repositories and factory.
     * It ensures that the provided parameters are valid and throws an IllegalArgumentException if any are null.
     * @param roomRepository The repository storing and retrieving rooms.
     * @param deviceFactory The factory responsible for creating devices.
     * @param deviceRepository The repository storing and retrieving devices.
     * @param sensorRepository The repository storing and retrieving sensors.
     * @param actuatorRepository The repository storing and retrieving actuators.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public DeviceServiceImpl(RoomRepository roomRepository, DeviceFactory deviceFactory, DeviceRepository deviceRepository,
                             SensorRepository sensorRepository, ActuatorRepository actuatorRepository) {
        if (!validParams(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository)) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        this.roomRepository = roomRepository;
        this.deviceFactory = deviceFactory;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.actuatorRepository = actuatorRepository;
    }

    /**
     * Adds a new Device with the provided details to the system.
     * This method first checks if the specified Room, identified by roomIDVO, is present in the system.
     * If the Room is present, it creates a new Device using the deviceFactory with the provided DeviceNameVO
     * and DeviceModelVO for the name and model of the Device, respectively.
     * The newly created Device is then saved to the system using the deviceRepository.
     *
     * @param deviceNameVO  The Value Object representing the name of the Device to add.
     * @param deviceModelVO The Value Object representing the model of the Device to add.
     * @param roomIDVO      The Value Object representing the ID of the Room where the Device is to be added.
     * @return Optional<Device> if the Device is successfully added to the system.
     * It returns false if the specified Room is not found in the system or if there is an error
     * creating or saving the Device.
     */
    public Optional<Device> addDevice(DeviceNameVO deviceNameVO, DeviceModelVO deviceModelVO, RoomIDVO roomIDVO) {
        if (deviceNameVO == null || deviceModelVO == null || roomIDVO == null) {
            throw new IllegalArgumentException("DeviceNameVO, DeviceModelVO and RoomIDVO cannot be null.");
        }
        if (!roomRepository.isPresent(roomIDVO)) {
            throw new IllegalArgumentException("Room with ID: " + roomIDVO + NOT_PRESENT_MESSAGE);
        }
        Device newDevice = deviceFactory.createDevice(deviceNameVO, deviceModelVO, roomIDVO);
        if (deviceRepository.save(newDevice)) {
            return Optional.of(newDevice);
        }
        return Optional.empty();
    }

    /**
     * Deactivates the device with the given ID.
     * This method finds the first device with the corresponding given id and returns true if:
     * 1. device is found;
     * 2. device is not deactivated;
     * 3. internal device operation is successful;
     * 4. update operation e device repository is successful;
     *
     * @param deviceIDVO The ID of the device to be deactivated.
     * @return The method returns the resulting device status after the operation was performed.
     */

    public Optional<Device> deactivateDevice(DeviceIDVO deviceIDVO) {
        if (deviceIDVO == null) {
            throw new IllegalArgumentException("DeviceIDVO cannot be null.");
        }

        Optional<Device> optionalDevice = getDeviceById(deviceIDVO);

        if (optionalDevice.isPresent()) {
            Device device = optionalDevice.get();
            if (!device.isActive()) {
                throw new IllegalArgumentException("Device with ID: " + deviceIDVO + " is already deactivated.");
            }
            if (device.deactivateDevice() && deviceRepository.update(device)) {
                return Optional.of(device);
            }
            throw new IllegalArgumentException("Device could not be updated");
        }
        return Optional.empty();
    }

    /**
     * Retrieves the device with the given ID.
     * This method retrieves the device with the specified ID from the system.
     * If the device is found, it returns an Optional containing the device. Otherwise, it returns an empty Optional.
     *
     * @param deviceIDVO The ID of the device to retrieve.
     * @return An Optional containing the device with the specified ID if found, otherwise an empty Optional.
     */
    public Optional<Device> getDeviceById(DeviceIDVO deviceIDVO) {
        if (deviceIDVO == null) {
            throw new IllegalArgumentException("DeviceIDVO cannot be null.");
        }
        if (deviceRepository.isPresent(deviceIDVO)) {
            return Optional.of(deviceRepository.findById(deviceIDVO));
        }
        return Optional.empty();
    }

    /**
     * Retrieves the list of devices located in the room identified by the provided RoomIDVO.
     * This method queries the system to fetch all devices associated with the specified room.
     *
     * @param roomIDVO The Value Object representing the ID of the room to retrieve devices from.
     * @return The list of devices located in the specified room.
     * It returns an empty list if no devices are found or if the specified room does not exist.
     */
    public List<Device> getListOfDevicesInARoom(RoomIDVO roomIDVO) {
        if (roomIDVO == null) {
            throw new IllegalArgumentException("RoomIDVO cannot be null.");
        }
        if (!roomRepository.isPresent(roomIDVO)) {
            throw new IllegalArgumentException("Room with ID: " + roomIDVO + NOT_PRESENT_MESSAGE);
        }
        Iterable<Device> deviceIterable = this.deviceRepository.findByRoomID(roomIDVO);
        List<Device> deviceList = new ArrayList<>();
        deviceIterable.forEach(deviceList::add);
        return deviceList;
    }

    /**
     * This method obtains a list of devices by functionality. The return format is: String (related to the type) as key,
     * and a list of Device objects as values.
     * @return Map String to List of Devices
     */
    public Map<String, List<Device>> getListOfDeviceByFunctionality(){
        LinkedHashMap<String,List<DeviceIDVO>> sensorMap = getMapDeviceIDBySensorType();
        LinkedHashMap<String,List<DeviceIDVO>> actuatorMap = getMapDeviceIDBySensorAndActuatorType();
        LinkedHashMap<String,List<DeviceIDVO>> sensorActuatorMap = mergeMaps(sensorMap,actuatorMap);
        return getDevices(sensorActuatorMap);
    }

    /**
     * This method leverages the sensor repository implementation to retrieve a comprehensive list of sensors. Subsequently,
     * it iterates through each sensor object, extracting their sensorTypeID and deviceID. These sensorTypeIDs are then mapped
     * as keys in a map structure. For each unique sensorTypeID, a corresponding list of DeviceIDs is created, ensuring avoidance
     * of duplicate deviceIDs per sensorTypeID. The method's return value is of type Map, where each key corresponds to a sensorTypeID,
     * and its value is a list of DeviceIDVO objects representing the devices associated with that sensorTypeID.
     */
    private LinkedHashMap<String,List<DeviceIDVO>> getMapDeviceIDBySensorType(){
        Iterable<Sensor> sensorList = sensorRepository.findAll();

        if(sensorList != null){
            LinkedHashMap<String,List<DeviceIDVO>> map = new LinkedHashMap<>();
            for (Sensor sensor : sensorList){
                String type = sensor.getSensorTypeID().getID();
                DeviceIDVO deviceID = sensor.getDeviceID();
                updateMap(map,type,deviceID);
            }
            return map;
        }
        throw new IllegalArgumentException("Cannot access all sensors");
    }

    /**
     * This method uses the actuator repository implementation to retrieve a complete list of actuators. It subsequently
     * iterates through each actuator object, extracting their actuatorTypeID and deviceID. These actuatorTypeIDs are then
     * mapped as keys in a map structure. For each unique actuatorTypeID, a corresponding list of DeviceIDs is created,
     * ensuring avoidance of duplicate deviceIDs per actuatorTypeID. The method's return value is a Map where each key
     * represents an actuatorTypeID, and its value is a list of DeviceIDVO objects each representing a device associated
     * with that actuatorTypeID.
     */
    private LinkedHashMap<String,List<DeviceIDVO>> getMapDeviceIDBySensorAndActuatorType(){
        Iterable<Actuator> actuatorList = actuatorRepository.findAll();

        if(actuatorList != null){
            LinkedHashMap<String,List<DeviceIDVO>> map = new LinkedHashMap<>();
            for (Actuator actuator : actuatorList){
                String type = actuator.getActuatorTypeID().getID();
                DeviceIDVO deviceID = actuator.getDeviceID();
                updateMap(map, type, deviceID);
            }
            return map;
        }
        throw new IllegalArgumentException("Cannot access all actuators");
    }

    /**
     * This method receives two hashmaps with the same key value types and returns a merged map.
     * @param map1 First map
     * @param map2 Second map
     * @return Map containing the merged key+value pairs of each map
     */
    private LinkedHashMap<String,List<DeviceIDVO>> mergeMaps (LinkedHashMap<String,List<DeviceIDVO>> map1, LinkedHashMap<String,List<DeviceIDVO>> map2){
        LinkedHashMap<String,List<DeviceIDVO>> newMap = new LinkedHashMap<>();
        newMap.putAll(map1);
        newMap.putAll(map2);
        return newMap;
    }

    /**
     * This method takes a Map with keys of type String and values of type List of DeviceIDVO. It then employs the
     * deviceRepository recursively to associate each DeviceIDVO object with its corresponding Device object. The resultant
     * Map mirrors the structure of the input, with Device objects replacing the original DeviceIDVO objects at each
     * corresponding key.
     * @param map Map <String,List<DeviceIDVO>>
     * @return LinkedHashMap with keys of type String and values of type List containing Device objects.
     */
    private LinkedHashMap<String, List<Device>> getDevices (Map<String, List<DeviceIDVO>> map) {
        LinkedHashMap<String, List<Device>> newMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<DeviceIDVO>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<DeviceIDVO> idList = entry.getValue();
            List<Device> deviceList = new ArrayList<>();
            for (DeviceIDVO id : idList) {
                Device device = this.deviceRepository.findById(id);
                deviceList.add(device);
            }
            newMap.put(key, deviceList);
        }
        return newMap;
    }

    /**
     * Updates a LinkedHashMap with keys of type String and values of type List containing DeviceIDVO objects.
     * If the map already contains the specified type as a key, the provided DeviceIDVO object is added to the corresponding list,
     * if it's not already present. If the type is not found in the map, a new entry is created with the type as the key and
     * a new List containing the provided DeviceIDVO object.
     *
     * @param map      The LinkedHashMap to update.
     * @param type     The type (String) representing the key in the map.
     * @param deviceID The DeviceIDVO object to add to the map.
     */
    private void updateMap (LinkedHashMap<String,List<DeviceIDVO>> map, String type, DeviceIDVO deviceID){
        if (map.containsKey(type)){
            List<DeviceIDVO> devicesPerKey = map.get(type);
            if(!devicesPerKey.contains(deviceID)){
                devicesPerKey.add(deviceID);
            }
        } else {
            List<DeviceIDVO> devicesPerKey = new ArrayList<>();
            devicesPerKey.add(deviceID);
            map.put(type,devicesPerKey);
        }
    }

    /**
     * Checks if all parameters passed to the method are non-null.
     * This method ensures that all parameters provided to it are not null before proceeding with further operations.
     * While this function may not be considered type-safe, it is always used after ensuring type safety
     * by type-checking the variables of the calling method (compiler).
     *
     * @param params The parameters to be checked for nullity.
     * @return true if all parameters are non-null, false otherwise.
     */

    private boolean validParams(Object... params) {
        for (Object param : params) {
            if (param == null) {
                return false;
            }
        }
        return true;
    }
}
