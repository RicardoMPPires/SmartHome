package smarthome.domain.actuatortype;

import smarthome.domain.AggregateRoot;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;

public class ActuatorType implements AggregateRoot {

    private final ActuatorTypeIDVO actuatorTypeIDVO;


    /**
     * Constructor for ActuatorType
     *
     * @param actuatorTypeIDVO ActuatorTypeIDVO object
     * @throws IllegalArgumentException if ActuatorTypeIDVO invalid
     */
    public ActuatorType(ActuatorTypeIDVO actuatorTypeIDVO) {
        if (actuatorTypeIDVO == null) throw new IllegalArgumentException("ActuatorType cannot be null");
        this.actuatorTypeIDVO = actuatorTypeIDVO;


    }

    /**
     * Get the actuatorTypeIDVO
     *
     * @return ActuatorTypeIDVO
     */
    @Override
    public ActuatorTypeIDVO getId() {
        return actuatorTypeIDVO;
    }
}
