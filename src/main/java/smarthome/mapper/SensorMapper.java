package smarthome.mapper;


import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.mapper.dto.SensorDTO;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class SensorMapper {
    private static final String INVALID_DTO_ERROR = "Invalid DTO, Value Object cannot be created";

    /**
     * Creates a SensorNameVO from a SensorDTO.
     * @param sensorDTO the SensorDTO from which to create the SensorNameVO.
     * @return a SensorNameVO with the same name as the SensorDTO.
     * @throws IllegalArgumentException if the SensorDTO is null.
     */
    public static SensorNameVO createSensorNameVO(SensorDTO sensorDTO) {
        if (sensorDTO == null)
            throw new IllegalArgumentException(INVALID_DTO_ERROR);

        String sensorName = sensorDTO.getSensorName();
        return new SensorNameVO(sensorName);
    }

    /**
     * Maps a Sensor domain object to a SensorDTO.
     * @param sensor the Sensor to map to a SensorDTO.
     * @return a SensorDTO with the same attributes as the Sensor.
     * @throws IllegalArgumentException if the Sensor is null.
     */
    public static SensorDTO domainToDTO(Sensor sensor) {
        if (sensor == null) {
            throw new IllegalArgumentException("Invalid Sensor, DTO cannot be created");
        }
        return SensorDTO.builder()
                .sensorID(sensor.getId().getID())
                .sensorName(sensor.getSensorName().getValue())
                .deviceID(sensor.getDeviceID().getID())
                .sensorTypeID(sensor.getSensorTypeID().getID())
                .build();
    }

    /**
     * Converts a list of sensor domain objects to a list of sensor DTOs.
     *
     * @param sensors The list of sensor domain objects to be converted.
     * @return The list of sensor DTOs.
     */
    public static List<SensorDTO> domainToDTO(List<Sensor> sensors) {
        List<SensorDTO> sensorDTOList = new ArrayList<>();
        for (Sensor sensor : sensors) {
            SensorDTO sensorDTO = domainToDTO(sensor);
            sensorDTOList.add(sensorDTO);
        }
        return sensorDTOList;
    }

    /**
     * Creates a DeviceIDVO from a SensorDTO.
     * @param sensorDTO the SensorDTO from which to create the DeviceIDVO.
     * @return a DeviceIDVO with the same ID as the device ID of the SensorDTO.
     * @throws IllegalArgumentException if the SensorDTO is null or if the device ID of the SensorDTO is not a valid UUID.
     */
    public static DeviceIDVO createDeviceID(SensorDTO sensorDTO) {
        if (sensorDTO == null) {
            throw new IllegalArgumentException(INVALID_DTO_ERROR);
        }

        String deviceID = sensorDTO.getDeviceID();
        if (!isDeviceIDValid(deviceID)) {
            throw new IllegalArgumentException("Invalid device ID");
        }
        UUID id = UUID.fromString(deviceID);
        return new DeviceIDVO(id);
    }

    /**
     * Creates a SensorTypeIDVO from a SensorDTO.
     * @param sensorDTO the SensorDTO from which to create the SensorTypeIDVO.
     * @return a SensorTypeIDVO with the same ID as the sensor type ID of the SensorDTO.
     * @throws IllegalArgumentException if the SensorDTO is null.
     */
    public static SensorTypeIDVO createSensorTypeIDVO(SensorDTO sensorDTO) {
        if (sensorDTO == null) {
            throw new IllegalArgumentException(INVALID_DTO_ERROR);
        }

        String sensorTypeID = sensorDTO.getSensorTypeID();
        return new SensorTypeIDVO(sensorTypeID);
    }

    /**
     * This method is responsible for creating a SensorIDVO from a sensor ID string.
     * It first checks if the provided sensor ID string is not null, throwing an IllegalArgumentException if it is.
     * Then, it converts the sensor ID string to a UUID and creates a SensorIDVO with this UUID.
     *
     * @param sensorID The string representing the ID of the Sensor to create a SensorIDVO from.
     * @return A SensorIDVO with the UUID equivalent of the provided sensor ID string.
     * @throws IllegalArgumentException if the provided sensor ID string is null.
     */
    public static SensorIDVO createSensorIDVO(String sensorID) {
        if (sensorID == null) {
            throw new IllegalArgumentException("Invalid sensor ID");
        }
        UUID id = UUID.fromString(sensorID);
        return new SensorIDVO(id);
    }


    /**
     * This method is responsible for creating a SensorTypeIDVO from a sensor type ID string.
     * It first checks if the provided sensor type ID string is not null nor empty, throwing an IllegalArgumentException if it is.
     * Then, it creates a SensorTypeIDVO with the given string.
     *
     * @param sensorID the string representing the ID of the sensor.
     * @return a SensorTypeIDVO with the same ID as the provided sensor ID string.
     * @throws IllegalArgumentException if the provided sensor ID string is null or empty.
     */
    public static SensorTypeIDVO createSensorTypeIDVO(String sensorID) {
        if (sensorID == null || sensorID.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid sensor ID");
        }
        return new SensorTypeIDVO(sensorID);
    }


    /**
     * Checks if a device ID is a valid UUID.
     * @param deviceID the device ID to check.
     * @return true if the device ID is a valid UUID, false otherwise.
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
     * Unit test for verifying that the SensorMapper's createDeviceIDVOFromString method
     * throws an IllegalArgumentException when given a null argument.
     *
     * <p>This test ensures that the createDeviceIDVOFromString method correctly handles
     * null input by throwing an IllegalArgumentException with the expected error message.</p>
     */
    public static DeviceIDVO createDeviceIDVOFromString(String deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException("DeviceID cannot be null");
        } else {
            UUID id = UUID.fromString(deviceId);
            return new DeviceIDVO(id);
        }
    }

}