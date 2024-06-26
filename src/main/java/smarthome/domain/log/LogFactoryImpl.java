package smarthome.domain.log;

import org.springframework.stereotype.Component;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;

@Component
public class LogFactoryImpl implements LogFactory {

    /**
     * * Constructs a new Log object with the provided parameters. Usually created right after a reading is obtained.
     *
     * @param reading      the sensor value object representing the reading
     * @param sensorID     the sensor ID object representing the ID of the sensor
     * @param deviceID     the device ID object representing the ID of the device
     * @param sensorTypeID the sensor type ID object representing the ID of the sensor type
     * @return a new Log object
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Log createLog(SensorValueObject<?> reading, SensorIDVO sensorID, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        return new Log(reading, sensorID, deviceID, sensorTypeID);
    }

    /**
     * Constructs a new Log object with the provided parameters. Usually created after a query to the persistence, includes the log ID and the timestamp attributes.
     *
     * @param logID        the log ID object representing the ID of the log
     * @param time         timestamp of the reading
     * @param reading      the sensor value object representing the reading
     * @param sensorID     the sensor ID object representing the ID of the sensor
     * @param deviceID     the device ID object representing the ID of the device
     * @param sensorTypeID the sensor type ID object representing the ID of the sensor type
     * @return a new Log object
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public Log createLog(LogIDVO logID, TimeStampVO time, SensorValueObject<?> reading, SensorIDVO sensorID, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        return new Log(logID, time, reading, sensorID, deviceID, sensorTypeID);
    }

}

