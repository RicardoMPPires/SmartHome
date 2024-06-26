package smarthome.domain.vo.actuatorvo;

import smarthome.domain.vo.ValueObject;

public class ActuatorStatusVO implements ValueObject<String> {

    private final String actuatorStatus;

    /**
     * Constructor for ActuatorStatusVO. Validates for null or empty. Additional validations are performed before this
     * object is constructed, based on the specific business rules of the actuator implementation.
     * @param actuatorStatus Device Status
     */
    public ActuatorStatusVO (String actuatorStatus) {
        if (actuatorStatus == null || actuatorStatus.trim().isEmpty()){
            throw new IllegalArgumentException("Invalid parameter");
        }
        this.actuatorStatus = actuatorStatus;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.actuatorStatus;
    }


}
