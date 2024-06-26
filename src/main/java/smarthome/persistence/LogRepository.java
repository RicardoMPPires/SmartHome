package smarthome.persistence;

import smarthome.domain.log.Log;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;

/**
 * This interface defines the operations that a LogRepository must support.
 * It extends the generic Repository interface with LogIDVO as the ID type and Log as the entity type.
 */
public interface LogRepository extends Repository<LogIDVO, Log>{

    /**
     * Retrieves all logs associated with a specific device within a given time period.
     *
     * @param deviceID the ID of the device
     * @param from     the start of the time period
     * @param to       the end of the time period
     * @return an Iterable of logs that match the given criteria
     */
    Iterable<Log> findReadingsByDeviceID(DeviceIDVO deviceID, TimeStampVO from, TimeStampVO to);
    Iterable<Log> getDeviceTemperatureLogs(DeviceIDVO deviceID, String sensorType, TimeStampVO start, TimeStampVO end);
    Iterable<Log> findByDeviceIDAndSensorTypeAndTimeBetween(String deviceID, String sensorType, TimeStampVO start, TimeStampVO end);
    Iterable<Log> findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(String excludeDeviceID, String sensorType, TimeStampVO start, TimeStampVO end);
}
