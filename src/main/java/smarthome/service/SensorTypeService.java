package smarthome.service;

import smarthome.domain.sensortype.SensorType;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;

import java.util.List;

public interface SensorTypeService {
    List<SensorType> getListOfSensorTypes();
    boolean sensorTypeExists (SensorTypeIDVO sensorTypeID);
}
