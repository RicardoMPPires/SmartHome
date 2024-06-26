package smarthome.persistence;

import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;

public interface SensorRepository extends Repository<SensorIDVO, Sensor>{
    Iterable<Sensor> findByDeviceID(DeviceIDVO deviceID);
    Iterable<Sensor> findBySensorTypeId(SensorTypeIDVO id);
}
