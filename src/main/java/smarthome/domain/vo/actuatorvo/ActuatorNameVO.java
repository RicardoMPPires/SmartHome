package smarthome.domain.vo.actuatorvo;

import smarthome.domain.vo.ValueObject;

public class ActuatorNameVO implements ValueObject<String> {

    private final String actuatorName;

    /**
     * Constructor for ActuatorNameVO, it validates value is not null or empty before creating the object.
     * @param actuatorName Actuator Name
     */
    public ActuatorNameVO(String actuatorName) {
        if(actuatorName == null || actuatorName.isBlank()) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.actuatorName = actuatorName;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.actuatorName;
    }
}
