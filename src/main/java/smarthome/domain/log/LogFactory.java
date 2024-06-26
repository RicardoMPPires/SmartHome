package smarthome.domain.log;

import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;

public interface LogFactory {
    Log createLog(SensorValueObject<?> reading, SensorIDVO sensorID, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID);

    Log createLog(LogIDVO logID, TimeStampVO time, SensorValueObject<?> reading, SensorIDVO sensorID, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID);

}
