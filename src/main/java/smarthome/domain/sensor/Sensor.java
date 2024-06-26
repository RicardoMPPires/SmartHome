package smarthome.domain.sensor;

import smarthome.domain.AggregateRoot;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

public interface Sensor extends AggregateRoot {
    SensorTypeIDVO getSensorTypeID();
    DeviceIDVO getDeviceID();
    SensorNameVO getSensorName();
}
