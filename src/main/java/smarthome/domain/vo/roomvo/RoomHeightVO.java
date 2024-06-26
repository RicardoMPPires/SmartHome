package smarthome.domain.vo.roomvo;

import smarthome.domain.vo.ValueObject;

public class RoomHeightVO implements ValueObject<Double> {
    private final double height;

    /**
     * Constructs a RoomHeightVO object with the given height.
     *
     * @param height The height of the room.
     * @throws IllegalArgumentException If the height is invalid.
     */
    public RoomHeightVO(double height) {
        if (!validateHeight(height)) {
            throw new IllegalArgumentException("Invalid height value");
        }
        this.height = height;
    }

    /**
     * Validates the height of the room.
     *
     * @param height The height of the room.
     * @return True if the height is valid, false otherwise.
     */
    private boolean validateHeight(double height) {
        return height >= 0;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public Double getValue() {
        return this.height;
    }
}
