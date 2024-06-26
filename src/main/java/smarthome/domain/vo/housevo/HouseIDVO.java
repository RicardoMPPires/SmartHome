package smarthome.domain.vo.housevo;

import smarthome.domain.DomainID;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a value object for House ID.
 */
public class HouseIDVO implements DomainID {

    private final UUID identifier;

    /**
     * Constructs a HouseIDVO object with the given identifier.
     *
     * @param identifier The UUID identifier for the house.
     * @throws IllegalArgumentException If the identifier is null.
     */
    public HouseIDVO(UUID identifier){
        if(identifier == null){
            throw new IllegalArgumentException("Invalid Identifier");
        }
        this.identifier = identifier;
    }

    /**
     * Gets the string representation of the house identifier.
     *
     * @return The string representation of the house identifier.
     */
    @Override
    public String getID() {
        return this.identifier.toString();
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseIDVO)) return false;
        HouseIDVO houseIDVO = (HouseIDVO) o;
        return Objects.equals(identifier, houseIDVO.identifier);
    }
}
