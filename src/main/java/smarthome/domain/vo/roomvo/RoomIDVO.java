package smarthome.domain.vo.roomvo;

import smarthome.domain.DomainID;

import java.util.UUID;

/**
 * Represents a value object for Room ID.
 */
public class RoomIDVO implements DomainID {

    private final UUID identifier;

    /**
     * Constructs a RoomIDVO object with the given identifier.
     *
     * @param identifier The UUID identifier for the room.
     * @throws IllegalArgumentException If the identifier is null.
     */
    public RoomIDVO(UUID identifier){
        if(identifier == null){
            throw new IllegalArgumentException("Invalid Identifier");
        }
        this.identifier = identifier;
    }

    /**
     * Gets the string representation of the room identifier.
     *
     * @return The string representation of the room identifier.
     */
    @Override
    public String getID() {
        return this.identifier.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomIDVO)) return false;
        RoomIDVO roomIDVO = (RoomIDVO) o;
        return identifier.equals(roomIDVO.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }


}
