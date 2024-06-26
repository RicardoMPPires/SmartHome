package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.log.Log;
import smarthome.domain.log.LogFactory;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.mapper.assembler.LogAssembler;
import smarthome.persistence.LogRepository;
import smarthome.persistence.jpa.datamodel.LogDataModel;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class LogRepositorySpringData implements LogRepository {
    private final ILogRepositorySpringData iLogRepositorySpringData;
    private final LogFactory logFactory;
    private final SensorValueFactory sensorValueFactory;
    private static final String ERROR_MESSAGE = "Invalid parameters.";

    /**
     * Constructor for LogRepositorySpringData.
     *
     * @param iLogRepositorySpringData the Spring Data repository used for data access
     * @param logFactory               the factory used to create Log domain objects
     * @param sensorValueFactory       the factory used to create SensorValue domain objects
     */
    public LogRepositorySpringData(ILogRepositorySpringData iLogRepositorySpringData, LogFactory logFactory, SensorValueFactory sensorValueFactory) {
        this.iLogRepositorySpringData = iLogRepositorySpringData;
        this.logFactory = logFactory;
        this.sensorValueFactory = sensorValueFactory;
    }

    /**
     * Saves a log to the database.
     *
     * @param log the log to be saved
     * @return true if the log was saved successfully, false otherwise
     * @throws IllegalArgumentException if the log is null
     */
    @Override
    public boolean save(Log log) {
        if (log == null) {
            throw new IllegalArgumentException("Log cannot be null");
        }
        try {
            LogDataModel logdataModel = new LogDataModel(log);
            this.iLogRepositorySpringData.save(logdataModel);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Retrieves a log from the database by its ID.
     *
     * @param logIDVO the ID of the log to be retrieved
     * @return the retrieved log, or null if no log with the given ID was found
     * @throws IllegalArgumentException if the logIDVO is null
     */
    @Override
    public Log findById(LogIDVO logIDVO) {
        if (logIDVO == null) {
            throw new IllegalArgumentException("LogIDVO cannot be null");
        }
        try {
            Optional<LogDataModel> logDataModelOptional = this.iLogRepositorySpringData.findById(logIDVO.getID());
            return logDataModelOptional.map(dataModel ->
                    LogAssembler.toDomain(this.logFactory, this.sensorValueFactory, dataModel)).orElse(null);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Retrieves all logs associated with a specific device within a given time period.
     *
     * @param deviceID the ID of the device
     * @param from     the start of the time period
     * @param to       the end of the time period
     * @return an Iterable of logs that match the given criteria
     * @throws IllegalArgumentException if any of the parameters are null
     */
    @Override
    public Iterable<Log> findReadingsByDeviceID(DeviceIDVO deviceID, TimeStampVO from, TimeStampVO to) {
        if (deviceID == null) {
            throw new IllegalArgumentException("Invalid parameters: deviceID is null");
        }

        try {
            LocalDateTime fromValue = (from != null) ? from.getValue() : null;
            LocalDateTime toValue = (to != null) ? to.getValue() : null;

            Iterable<LogDataModel> logDataModelIterable = this.iLogRepositorySpringData.findByDeviceIDAndTimeBetween(deviceID.getID(), fromValue, toValue);
            return LogAssembler.toDomain(this.logFactory, this.sensorValueFactory, logDataModelIterable);
        } catch (DataAccessException e) {
            // Log the exception if necessary
            return new ArrayList<>();
        }
    }

    /**
     * Checks if a log with the given ID is present in the database.
     *
     * @param id the ID of the log
     * @return true if the log is present, false otherwise
     * @throws IllegalArgumentException if the id is null
     */
    public boolean isPresent(LogIDVO id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return findById(id) != null;
    }

    /**
     * Retrieves all logs from the database.
     *
     * @return an Iterable of all logs
     */
    @Override
    public Iterable<Log> findAll() {
        try {
            Iterable<LogDataModel> logDataModelIterable = this.iLogRepositorySpringData.findAll();
            return LogAssembler.toDomain(this.logFactory, this.sensorValueFactory, logDataModelIterable);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Retrieves all log data from the database that falls within the specified time range and is associated
     * with the specified device and sensor type.
     * It checks if the deviceID, sensorType, start, and end parameters are not null.
     * It then retrieves all log data that matches the specified device ID, sensor type, and time range. It uses the
     * findByDeviceIDAndSensorTypeAndTimeBetween() method of the ILogRepositorySpringData interface to query the database.
     * It then converts the LogDataModel objects to Log objects using LogAssembler and returns the result.
     * If a DataAccessException occurs, it returns null.
     * If any of the input parameters are null, it throws an IllegalArgumentException.
     *
     * @param deviceID   The device ID to filter the log data by.
     * @param sensorType The sensor type to filter the log data by.
     * @param start      The start of the time range to filter the log data by.
     * @param end        The end of the time range to filter the log data by.
     * @return An Iterable of Log objects that match the query criteria.
     */

    public Iterable<Log> getDeviceTemperatureLogs(DeviceIDVO deviceID, String sensorType, TimeStampVO start, TimeStampVO end) {
        if (deviceID == null || sensorType == null || start == null || end == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        try {
            String deviceIDString = deviceID.getID();
            LocalDateTime startTime = start.getValue();
            LocalDateTime endTime = end.getValue();
            Iterable<LogDataModel> listOfLogs = this.iLogRepositorySpringData.findByDeviceIDAndSensorTypeAndTimeBetween(deviceIDString, sensorType, startTime, endTime);
            return LogAssembler.toDomain(this.logFactory, this.sensorValueFactory, listOfLogs);
        } catch (DataAccessException e) {
            return null;
        }
    }


    /**
     * Finds and retrieves logs from the database for a specific device and sensor type within a given time range using Spring Data.
     * This method performs validation on the input parameters to ensure they are not null. It converts the TimeStampVO
     * objects to LocalDateTime before passing them to the Spring Data repository method.
     * If any parameter is invalid or if a DataAccessException occurs during query execution, null is returned.
     *
     * @param deviceID the ID of the device to filter logs.
     * @param sensorType the type of sensor to filter logs.
     * @param start the start timestamp of the period, represented as a TimeStampVO object.
     * @param end the end timestamp of the period, represented as a TimeStampVO object.
     * @return an Iterable of Log objects that match the specified criteria, or null if no logs are found or a DataAccessException occurs.
     * @throws IllegalArgumentException if any of the input parameters are null.
     */
    @Override
    public Iterable<Log> findByDeviceIDAndSensorTypeAndTimeBetween(String deviceID, String sensorType, TimeStampVO start, TimeStampVO end) {
        if (deviceID == null || sensorType == null || start == null || end == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        try {
            LocalDateTime startTime = start.getValue();
            LocalDateTime endTime = end.getValue();
            Iterable<LogDataModel> gridPowerMeterLogsDataModel = this.iLogRepositorySpringData.findByDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorType, startTime, endTime);
            return LogAssembler.toDomain(this.logFactory, this.sensorValueFactory, gridPowerMeterLogsDataModel);
        } catch (DataAccessException e) {
            return null;
        }
    }


    /**
     * Finds and retrieves logs from the database for a specific sensor type and time range, excluding logs from a specific device
     * and with negative readings, using Spring Data. This method performs validation on the input parameters to ensure they are not null.
     * It converts the TimeStampVO objects to LocalDateTime before passing them to the Spring Data repository method.
     * If any parameter is invalid or if a DataAccessException occurs during query execution, null is returned.
     *
     * @param excludeDeviceID the ID of the device to exclude from the logs.
     * @param sensorType the type of sensor to filter logs.
     * @param start the start timestamp of the period, represented as a TimeStampVO object.
     * @param end the end timestamp of the period, represented as a TimeStampVO object.
     * @return an Iterable of Log objects that match the specified criteria, or null if no logs are found or a DataAccessException occurs.
     * @throws IllegalArgumentException if any of the input parameters are null.
     */
    public Iterable<Log> findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(String excludeDeviceID, String sensorType, TimeStampVO start, TimeStampVO end){
        if (excludeDeviceID == null || sensorType == null || start == null || end == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        try {
            LocalDateTime startTime = start.getValue();
            LocalDateTime endTime = end.getValue();
            Iterable<LogDataModel> powerSourceLogsDataModel = this.iLogRepositorySpringData.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(excludeDeviceID, sensorType, startTime, endTime);
            return LogAssembler.toDomain(this.logFactory, this.sensorValueFactory, powerSourceLogsDataModel);
        } catch (DataAccessException e) {
            return null;
        }
    }
}