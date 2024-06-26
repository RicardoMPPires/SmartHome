package smarthome.service;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.util.List;
import java.util.Optional;

public interface SensorService {
    Optional<Sensor> addSensor(SensorNameVO sensorName, DeviceIDVO deviceIDVO, SensorTypeIDVO sensorTypeIDVO);
    Optional<Sensor>getSensorById(SensorIDVO sensorIDVO);
    List<Sensor> getListOfSensorsInADevice(DeviceIDVO deviceIDVO);
}
