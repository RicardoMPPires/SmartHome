package smarthome.domain.vo.actuatortype;

import smarthome.domain.DomainID;

import java.util.Objects;


public class ActuatorTypeIDVO implements DomainID {

    private final String actuatorTypeID;

    /**
     * Constructor for ActuatorTypeID Value Object. It receives an identifier, and creates the object if the identifier
     * is not null or empty;
     * @param actuatorTypeID String identifier for the actuator type;
     */

    public ActuatorTypeIDVO(String actuatorTypeID) {
        if (actuatorTypeID == null || actuatorTypeID.trim().isEmpty()){
            throw new IllegalArgumentException("ActuatorTypeID cannot be null");
        }
        this.actuatorTypeID = actuatorTypeID;
    }

    /**
     * Simple getter method;
     * @return Receives the encapsulated value (which in this case is a string);
     */
    public String getID() {
        return actuatorTypeID;
    }

    /**
     * Compares this ActuatorTypeIDVO object to another object of the same type to check if they are equal.
     *
     * @param o Object
     * @return True if the objects are equal, false otherwise;
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActuatorTypeIDVO that = (ActuatorTypeIDVO) o;
        return Objects.equals(actuatorTypeID, that.actuatorTypeID);
    }

    /**
     * Generates a hash code for the object;
     *
     * @return The hash code;
     */
    @Override
    public int hashCode() {
        return Objects.hash(actuatorTypeID);
    }
}