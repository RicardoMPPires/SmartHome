package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SunTimeServices;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SunTimeValue;

public interface SunSensor extends Sensor {
    SunTimeValue getReading(String date, String gpsLocation, SunTimeServices sunTimeCalculator, SensorValueFactory valueFactory);
}
