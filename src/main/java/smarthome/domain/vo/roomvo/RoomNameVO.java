package smarthome.domain.vo.roomvo;

import smarthome.domain.vo.ValueObject;

public class RoomNameVO implements ValueObject<String> {

    private final String roomName;

    /** RoomNameVO constructor for the corresponding Value Object, with a String roomName as its parameter.
     * If the String is either null, empty or blank it will throw an Illegal Argument Exception.
     *
     * @param roomName RoomName
     */
    public RoomNameVO(String roomName) {
        if(!areParametersValid(roomName)) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.roomName = roomName;
    }

    /**
     * Private method that verifies if roomName is null, empty or blank.
     *
     * @param roomName RoomName
     * @return True or false
     */
    private boolean areParametersValid(String roomName) {
        return roomName != null && !roomName.trim().isEmpty() && !roomName.isBlank();
    }

    /**
     * Simple getter method
     * @return The encapsulated value
     */
    @Override
    public String getValue() {
        return this.roomName;
    }
}
