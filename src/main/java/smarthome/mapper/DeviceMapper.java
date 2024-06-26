package smarthome.mapper;

import smarthome.domain.device.Device;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.dto.DeviceDTO;

import java.util.*;

public class DeviceMapper {

    private static final String ERRORMESSAGE = "DeviceDTO cannot be null.";

    /**
     * Creates a new Device object from the provided DeviceDTO object.
     * Validates the provided DeviceDTO object to ensure it is not null
     * Then ensures the device name is valid, meaning not null, empty or blank.
     *
     * @param deviceDTO the DeviceDTO object to be converted to a Device object
     * @return a new Device object created from the provided DeviceDTO object
     * @throws IllegalArgumentException if the provided DeviceDTO object is null
     */
    public static DeviceNameVO createDeviceName(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        String deviceName = deviceDTO.getDeviceName();
        if (!isDeviceNameValid(deviceName)) {
            throw new IllegalArgumentException("Invalid device name.");
        }
        return new DeviceNameVO(deviceName);
    }

    /**
     * Creates a new Device object from the provided DeviceDTO object.
     * Validates the provided DeviceDTO object to ensure it is not null
     * Then ensures the device model is valid, meaning not null, empty or blank.
     *
     * @param deviceDTO the DeviceDTO object to be converted to a Device object
     * @return a new Device object created from the provided DeviceDTO object
     * @throws IllegalArgumentException if the provided DeviceDTO object is null
     */
    public static DeviceModelVO createDeviceModel(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        String deviceModel = deviceDTO.getDeviceModel();
        if (!isDeviceModelValid(deviceModel)) {
            throw new IllegalArgumentException("Invalid device model.");
        }
        return new DeviceModelVO(deviceModel);
    }

    /**
     * Creates a new Device object from the provided DeviceDTO object.
     * Validates the provided DeviceDTO object to ensure it is not null
     * Then ensures the device ID is valid, meaning not null, empty or blank.
     * Then, instantiates a new UUID object from the provided device ID string.
     *
     * @param deviceDTO the DeviceDTO object to be converted to a Device object
     * @return a new Device object created from the provided DeviceDTO object
     * @throws IllegalArgumentException if the provided DeviceDTO object is null
     */
    public static DeviceIDVO createDeviceID(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        String deviceID = deviceDTO.getDeviceID();
        if (!isDeviceIDValid(deviceID)) {
            throw new IllegalArgumentException("Invalid device ID");
        }
        UUID id = UUID.fromString(deviceID);
        return new DeviceIDVO(id);
    }

    /**
     * Creates a new RoomIDVO object from the provided DeviceDTO object.
     * Validates the provided DeviceDTO object to ensure it is not null
     * Then ensures the room ID is valid, meaning not null, empty or blank.
     * Then, instantiates a new UUID object from the provided room ID string.
     *
     * @param deviceDTO the DeviceDTO object to be converted to a RoomIDVO object
     * @return a new RoomIDVO object created from the provided DeviceDTO object
     */
    public static RoomIDVO createRoomIDVO(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        String roomID = deviceDTO.getRoomID();
        if (roomID == null || roomID.isEmpty()) {
            throw new IllegalArgumentException("Invalid room ID");
        }
        UUID id = UUID.fromString(roomID);
        return new RoomIDVO(id);
    }

    /**
     * Creates a new DeviceIDVO object from the provided device ID string.
     * Validates the provided device ID string to ensure it is not null, empty or blank.
     * Then, instantiates a new UUID object from the provided device ID string.
     *
     * @param deviceID the device ID string to be converted to a DeviceIDVO object
     * @return a new DeviceIDVO object created from the provided device ID string
     */
    public static DeviceIDVO createDeviceID(String deviceID) {
        if (deviceID == null || deviceID.trim().isEmpty()) {
            throw new IllegalArgumentException("DeviceID cannot be null.");
        }
        UUID uuid = uuidFromString(deviceID);

        if (uuid != null){
            UUID id = UUID.fromString(deviceID);
            return new DeviceIDVO(id);
        }
        throw new IllegalArgumentException("Invalid device ID");
    }

    /**
     * Converts a device ID string to a UUID object. If the provided device ID string is not a valid UUID,
     * this method returns null.
     * @param deviceID the device ID string to be converted to a UUID object
     * @return a UUID object created from the provided device ID string, or null if the device ID string is not a valid UUID
     */
    private static UUID uuidFromString(String deviceID) {
        try{
            return UUID.fromString(deviceID);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Creates a new RoomIDVO object from the provided room ID string.
     * Validates the provided room ID string to ensure it is not null, empty or blank.
     * Then, instantiates a new UUID object from the provided room ID string.
     * @param roomID the room ID string to be converted to a RoomIDVO object
     * @return a new RoomIDVO object created from the provided room ID string
     */
    public static RoomIDVO createRoomIDVO(String roomID) {
        if(roomID.trim().isEmpty()){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        else {
            // Converts String from the DTO into a UUID
            UUID room = UUID.fromString(roomID);
            return new RoomIDVO(room);
        }
    }

    /**
     * Converts a list of Device domain objects to a list of DeviceDTO.
     * This method iterates through the provided list of Device domain objects, extracts relevant information
     * such as device name, model, room ID, status, and ID, and creates a new list of DeviceDTOs with this information.
     *
     * @param deviceList The list of Device domain objects to be converted.
     * @return A new list of DeviceDTO Data Transfer Objects representing the converted devices.
     */
    public static List<DeviceDTO> domainToDTO(List<Device> deviceList) {
        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        for (Device device : deviceList) {
            DeviceDTO deviceDTO = domainToDTO(device);
            deviceDTOList.add(deviceDTO);
        }
        return deviceDTOList;
    }

    /**
     * Converts a Device domain object to a DeviceDTO Data Transfer Object. This method extracts relevant information
     * such as device name, model, room ID, status, and ID from the provided Device domain object and creates a new
     * DeviceDTO with this information. The DeviceDTO is then returned.
     * @param device The Device domain object to be converted.
     * @return A new DeviceDTO Data Transfer Object representing the converted device.
     */
    public static DeviceDTO domainToDTO(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null.");
        }
        String name = device.getDeviceName().getValue();
        String model = device.getDeviceModel().getValue();
        String roomID = device.getRoomID().getID();
        String status = device.getDeviceStatus().getValue().toString();
        String deviceID = device.getId().getID();

        return new DeviceDTO(deviceID, name, model, status, roomID);
    }

    /**
     * Validates the device name.
     *
     * @param deviceName the device name to be validated
     * @return true if the device name is valid, false otherwise
     */
    private static boolean isDeviceNameValid(String deviceName) {
        if (deviceName == null || deviceName.isEmpty()) {
            return false;
        }
        return !deviceName.trim().isEmpty();
    }

    /**
     * Validates the device model.
     *
     * @param deviceModel the device model to be validated
     * @return true if the device model is valid, false otherwise
     */
    private static boolean isDeviceModelValid(String deviceModel) {
        if (deviceModel == null || deviceModel.isEmpty()) {
            return false;
        }
        return !deviceModel.trim().isEmpty();
    }

    /**
     * Validates the device ID.
     *
     * @param deviceID the device ID to be validated
     * @return true if the device ID is valid, false otherwise
     */
    private static boolean isDeviceIDValid(String deviceID) {
        try {
            UUID.fromString(deviceID);
        } catch (NullPointerException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Converts a Map of Device domain objects to a Map of DeviceDTO Data.
     * This method iterates through the provided Map of Device domain objects, converts each list of devices
     * to DeviceDTOs using the domainToDTO method, and creates a new Map with the converted DeviceDTO lists.
     *
     * @param map The Map where each key represents a functionality and the value is a list of Device domain objects.
     * @return A new Map where each key represents a functionality and the value is a list of DeviceDTO Data Transfer Objects.
     */
    public static LinkedHashMap<String, List<DeviceDTO>> domainToDTO(Map<String, List<Device>> map) {
        LinkedHashMap<String, List<DeviceDTO>> newMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<Device>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Device> deviceList = entry.getValue();
            List<DeviceDTO> deviceDTOList = domainToDTO(deviceList);
            newMap.put(key, deviceDTOList);
        }
        return newMap;
    }
}
