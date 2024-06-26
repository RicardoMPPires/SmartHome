package smarthome.domain.actuatortype;

import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;

public interface ActuatorTypeFactory {
    ActuatorType createActuatorType(ActuatorTypeIDVO actuatorTypeIDVO);
}
