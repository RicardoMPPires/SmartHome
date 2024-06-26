package smarthome.persistence.mem;

import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.persistence.SensorRepository;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class SensorRepositoryMem implements SensorRepository {

    private final LinkedHashMap<SensorIDVO, Sensor> sensorMap = new LinkedHashMap<>();

    /**
     * @param sensor Entity to be saved
     * @return True or false
     */
    @Override
    public boolean save(Sensor sensor) {
        if(sensor == null || sensor.getId() == null || isPresent((SensorIDVO) sensor.getId())){
            return false;
        }
        sensorMap.put((SensorIDVO) sensor.getId(), sensor);
        return true;
    }

    /**
     * This method extracts all instances of Sensor that were previously saved.
     * @return An interable with the previously saved Sensor classes
     */
    @Override
    public Iterable<Sensor> findAll() {
        return sensorMap.values();
    }

    /**
     * This method returns an instance of Sensor, given a matching SensorIDVO. If ID is not a present key, returns null
     * @param id SensorIDVO object
     * @return Returns the Sensor object if key found, or null otherwise
     */
    @Override
    public Sensor findById(SensorIDVO id) {
        if(!isPresent(id)){
            return null;
        }
        return sensorMap.get(id);
    }

    /**
     * Verifies if there is a matching sensorIDVO as key
     * @param id SensorIDVO object
     * @return True or false
     */
    @Override
    public boolean isPresent(SensorIDVO id) {
        return sensorMap.containsKey(id);
    }

    @Override
    public Iterable<Sensor> findByDeviceID(DeviceIDVO deviceID) {
        return sensorMap.values().stream()
                .filter(sensor -> deviceID.equals(sensor.getDeviceID()))
                .collect(Collectors.toList());
    }


    /**
     * Finds all Sensor objects in the repository that match the provided sensor type ID.
     * This method filters the values in the sensorMap to find sensors with the specified sensor type ID.
     * The results are collected into a list and returned.
     *
     * @param id The SensorTypeIDVO representing the type of sensor to search for.
     * @return An Iterable of Sensor objects that match the specified sensor type ID.
     */
    @Override
    public Iterable<Sensor> findBySensorTypeId(SensorTypeIDVO id) {
        return sensorMap.values().stream()
                .filter(sensor -> id.equals(sensor.getSensorTypeID()))
                .collect(Collectors.toList());
    }
}
