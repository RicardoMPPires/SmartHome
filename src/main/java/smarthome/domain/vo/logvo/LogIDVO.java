package smarthome.domain.vo.logvo;

import smarthome.domain.DomainID;

import java.util.UUID;

public class LogIDVO implements DomainID {

    private final UUID identifier;

    /**
     * Constructs a LogIDVO object with the given identifier.
     *
     * @param identifier The UUID identifier for the log.
     * @throws IllegalArgumentException If the identifier is null.
     */
    public LogIDVO(UUID identifier){
        if(identifier == null){
            throw new IllegalArgumentException("Invalid Identifier");
        }
        this.identifier = identifier;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by {@link java.util.HashMap}.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogIDVO logIDVO)) return false;
        return identifier.equals(logIDVO.identifier);
    }

    /**
     * Gets the string representation of the log identifier.
     * @return The string representation of the log identifier.
     */
    @Override
    public String getID() {
        return this.identifier.toString();
    }
}
