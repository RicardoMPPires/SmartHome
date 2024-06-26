package smarthome.domain.vo.roomvo;

public class RoomDimensionsVO {
    private final RoomLengthVO length;
    private final RoomWidthVO width;
    private final RoomHeightVO height;

    /**
     * Constructs a RoomDimensionsVO object with the given length, width, and height.
     *
     * @param length The length of the room.
     * @param width  The width of the room.
     * @param height The height of the room.
     * @throws IllegalArgumentException If the length, width, or height is null.
     */
    public RoomDimensionsVO(RoomLengthVO length, RoomWidthVO width, RoomHeightVO height) {
        if (!dimensionsAreValid(length, width, height)) {
            throw new IllegalArgumentException("Invalid dimensions");
        }
        this.length = length;
        this.width = width;
        this.height = height;
    }

    /**
     * Validates the length, width, and height objects.
     *
     * @param length The length of the room.
     * @param width  The width of the room.
     * @param height The height of the room.
     * @return True if the length, width, and height are not null, otherwise false.
     */
    private boolean dimensionsAreValid(RoomLengthVO length, RoomWidthVO width, RoomHeightVO height) {
        return length != null && width != null && height != null;
    }

    /**
     * Simple getter method
     * @return RoomLength's VO encapsulated value
     */
    public Double getRoomLength() {
        return this.length.getValue();
    }

    /**
     * Simple getter method
     * @return RoomWidth's VO encapsulated value
     */
    public Double getRoomWidth() {
        return this.width.getValue();
    }

    /**
     * Simple getter method
     * @return RoomHeight's VO encapsulated value
     */
    public Double getRoomHeight() {
        return this.height.getValue();
    }
}
