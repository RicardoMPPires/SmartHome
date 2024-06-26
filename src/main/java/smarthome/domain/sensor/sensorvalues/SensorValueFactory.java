package smarthome.domain.sensor.sensorvalues;

import smarthome.domain.vo.sensortype.SensorTypeIDVO;

import java.time.ZonedDateTime;

public interface SensorValueFactory {
    SensorValueObject<?> createSensorValue(String reading, SensorTypeIDVO sensorTypeID);
    SensorValueObject<?> createSensorValue(ZonedDateTime reading, SensorTypeIDVO sensorTypeID);
}
