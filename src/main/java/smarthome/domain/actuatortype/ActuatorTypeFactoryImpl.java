package smarthome.domain.actuatortype;


import org.springframework.stereotype.Component;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;

@Component
public class ActuatorTypeFactoryImpl implements ActuatorTypeFactory{
    /**
     * Create an ActuatorType object
     *
     * @param actuatorTypeIDVO ActuatorTypeIDVO object
     * @return ActuatorType
     */
    public ActuatorType createActuatorType(ActuatorTypeIDVO actuatorTypeIDVO) {
        return new ActuatorType(actuatorTypeIDVO);
    }
}
