package smarthome.domain.sensortype;

import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;

public interface SensorTypeFactory {
    SensorType createSensorType(SensorTypeIDVO sensorTypeIDVO, UnitVO unitVO);
}
