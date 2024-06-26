package smarthome.domain.sensor;

import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

public interface SensorFactory {
    Sensor createSensor (SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID);

    Sensor createSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID);
}
