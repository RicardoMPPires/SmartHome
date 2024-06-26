package smarthome.service;

import smarthome.domain.log.Log;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.DeltaVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling log data.
 */
public interface LogService {
    Optional<Log> addLog (SensorValueObject<?> value, SensorIDVO sensor, DeviceIDVO device, SensorTypeIDVO sensorType);
    List<Log> findReadingsFromDevice(DeviceIDVO deviceID, TimeStampVO initialTimeStamp, TimeStampVO finalTimeStamp);
    String getMaxInstantaneousTempDifference(DeviceIDVO outdoorDevice, DeviceIDVO indoorDevice, TimeStampVO initialTimeStamp, TimeStampVO finalTimeStamp, DeltaVO deltaMin);
    String getPeakPowerConsumption(TimeStampVO start, TimeStampVO end, DeltaVO delta);
    String getSunReading(String date, String gpsLocation, SensorTypeIDVO sensorIDVO);
}
