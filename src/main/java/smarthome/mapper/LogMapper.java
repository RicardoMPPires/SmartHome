package smarthome.mapper;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smarthome.domain.log.Log;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.mapper.dto.LogDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Utility class for mapping between Log domain objects and LogDTO data transfer objects.
 * <p>
 * This class provides static methods to convert between domain model objects (Log, DeviceIDVO, SensorIDVO, etc.)
 * and their corresponding DTO representations (LogDTO). It also includes methods to create value objects from
 * LogDTO attributes.
 * </p>
 */
public class LogMapper {


    /**
     * Factory for creating sensor value objects.
     * <p>
     * This static field holds an instance of {@code SensorValueFactory}, which is used to create instances
     * of {@code SensorValueObject} based on the sensor type and reading.
     * </p>
     * <p>
     * The {@code @Setter} annotation from Lombok generates a static setter method for this field,
     * allowing it to be set externally, typically by the Spring framework.
     * </p>
     */
    @Setter
    private static SensorValueFactory sensorValueFactory;
    private static final String ERRORMESSAGE = "LogDTO cannot be null.";



    /**
     * Private constructor to prevent instantiation of the LogMapper class.
     * <p>
     * This constructor is created to hide the implicit public constructor, preventing instantiation
     * of the LogMapper class from outside the class itself or its nested classes.
     */

    private LogMapper(){
        // Created to hide implicit public constructor
    }

    /**
     * Creates a {@code LogIDVO} instance from the given {@code LogDTO}.
     *
     * @param logDTO the log data transfer object, must not be null
     * @return a {@code LogIDVO} created from the log ID in the {@code logDTO}
     * @throws IllegalArgumentException if {@code logDTO} is null
     */
    public static LogIDVO createLogIDVO (LogDTO logDTO){
        if (logDTO == null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        return new LogIDVO(UUID.fromString(logDTO.getLogID()));
    }

    /**
     * Creates a {@code DeviceIDVO} instance from the given {@code LogDTO}.
     *
     * @param logDTO the log data transfer object, must not be null
     * @return a {@code DeviceIDVO} created from the device ID in the {@code logDTO}
     * @throws IllegalArgumentException if {@code logDTO} is null
     */
    public static DeviceIDVO createDeviceIDVO (LogDTO logDTO){
        if (logDTO == null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        return new DeviceIDVO(UUID.fromString(logDTO.getDeviceID()));
    }

    /**
     * Creates a {@code SensorIDVO} instance from the given {@code LogDTO}.
     *
     * @param logDTO the log data transfer object, must not be null
     * @return a {@code SensorIDVO} created from the sensor ID in the {@code logDTO}
     * @throws IllegalArgumentException if {@code logDTO} is null
     */
    public static SensorIDVO createSensorIDVO (LogDTO logDTO){
        if (logDTO == null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        return new SensorIDVO(UUID.fromString(logDTO.getSensorID()));
    }

    /**
     * Creates a {@code SensorTypeIDVO} instance from the given {@code LogDTO}.
     *
     * @param logDTO the log data transfer object, must not be null
     * @return a {@code SensorTypeIDVO} created from the device ID in the {@code logDTO}
     * @throws IllegalArgumentException if {@code logDTO} is null
     */
    public static SensorTypeIDVO createSensorTypeIDVO (LogDTO logDTO){
        if (logDTO == null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        return new SensorTypeIDVO(logDTO.getSensorTypeID());
    }

    /**
     * Creates a {@code SensorValueObject} from the given {@code LogDTO} and {@code SensorTypeIDVO}.
     *
     * @param logDTO the log data transfer object, must not be null
     * @param sensorTypeIDVO the sensor type ID value object, must not be null
     * @return a {@code SensorValueObject} created from the reading in the {@code logDTO} and the specified sensor type
     * @throws IllegalArgumentException if {@code logDTO} is null
     */
    public static SensorValueObject<?> createReading (LogDTO logDTO, SensorTypeIDVO sensorTypeIDVO){
        if (logDTO == null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        return sensorValueFactory.createSensorValue(logDTO.getReading(), sensorTypeIDVO);
    }

    /**
     * Creates a {@code TimeStampVO} instance from the given {@code LogDTO}.
     *
     * @param logDTO the log data transfer object, must not be null
     * @return a {@code TimeStampVO} created from the time in the {@code logDTO}
     * @throws IllegalArgumentException if {@code logDTO} is null
     */
    public static TimeStampVO createTimeStampVO (LogDTO logDTO){
        if (logDTO == null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        return new TimeStampVO(LocalDateTime.parse(logDTO.getTime()));
    }

    /**
     * Converts a domain model Log object to a data transfer object (DTO) LogDTO.
     * <p>
     * This method takes a Log object from the domain model and maps its attributes to a LogDTO object.
     * The LogDTO object represents the same data in a format suitable for transfer between different layers
     * or components of an application. If the provided Log object is null, the method throws an IllegalArgumentException
     * with an appropriate error message. Otherwise, it extracts the attributes of the Log object,
     * including log ID, timestamp, reading value, sensor ID, device ID, and sensor type ID, and uses them
     * to instantiate a new LogDTO object.
     * @param log The Log object from the domain model to be converted to a DTO.
     * @return A LogDTO object representing the data from the input Log object.
     * @throws IllegalArgumentException If the provided Log object is null.
     */
    public static LogDTO domainToDTO (Log log) {
        if (log == null){
            throw new IllegalArgumentException("Invalid parameter");
        }

        LocalDateTime timeValue = log.getTime().getValue();
        String formattedTime = timeValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        return LogDTO.builder()
                .logID(log.getId().getID())
                .time(formattedTime)
                .reading(log.getReading().getValue().toString())
                .sensorID(log.getSensorID().getID())
                .deviceID(log.getDeviceID().getID())
                .sensorTypeID(log.getSensorTypeID().getID())
                .build();
    }

    /**
     * Converts an iterable collection of domain model Log objects to a list of data transfer objects (DTOs) LogDTO.
     * <p>
     * This method takes an iterable collection of Log objects from the domain model and maps each Log object
     * to a corresponding LogDTO object. The resulting list contains LogDTO objects representing the data from
     * the input Log objects. If the provided iterable collection of Logs is null, the method throws an
     * IllegalArgumentException with an appropriate error message. Otherwise, it iterates over the input iterable
     * collection, applies the domainToDTO() method to each Log object to perform the conversion, and collects
     * the resulting LogDTO objects into a list.
     * @param listOfLogs The iterable collection of Log objects from the domain model to be converted to DTOs.
     * @return A list of LogDTO objects representing the data from the input Log objects.
     * @throws IllegalArgumentException If the provided iterable collection of Logs is null.
     */
    public static List<LogDTO> domainToDTO (List<Log> listOfLogs) {
        if (listOfLogs == null){
            throw new IllegalArgumentException("Invalid parameter");
        }
        List<smarthome.mapper.dto.LogDTO> listOfLogDTO = new ArrayList<>();
        for (Log log : listOfLogs) {
            smarthome.mapper.dto.LogDTO logDTO = LogMapper.domainToDTO(log);
            listOfLogDTO.add(logDTO);
        }
        return listOfLogDTO;
    }
}