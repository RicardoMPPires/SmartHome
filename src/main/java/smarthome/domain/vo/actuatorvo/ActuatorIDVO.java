package smarthome.domain.vo.actuatorvo;

import smarthome.domain.DomainID;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a value object for Actuator ID.
 */

public class ActuatorIDVO implements DomainID {

    /**
     * The UUID identifier for the actuator.
     */

    private final UUID identifier;

    /**
     * Constructs an ActuatorIDVO object with the given identifier.
     *
     * @param identifier The UUID identifier for the actuator.
     * @throws IllegalArgumentException If the identifier is null.
     */

    public ActuatorIDVO(UUID identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Invalid Identifier");
        }
        this.identifier = identifier;
    }

    /**
     * Compares this ActuatorIDVO object to another object of the same type to check if they are equal.
     *
     * @param o object
     * @return True if the objects are equal, false otherwise;
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActuatorIDVO that = (ActuatorIDVO) o;
        return Objects.equals(identifier, that.identifier);
    }

    /**
     * Generates a hash code for the object;
     *
     * @return The hash code;
     */
    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    /**
     * Gets the string representation of the actuator identifier.
     *
     * @return The string representation of the actuator identifier.
     */

    @Override
    public String getID() {
        return this.identifier.toString();
    }
}
