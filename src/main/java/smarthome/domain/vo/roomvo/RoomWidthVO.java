package smarthome.domain.vo.roomvo;

import smarthome.domain.vo.ValueObject;

public class RoomWidthVO implements ValueObject<Double> {
    private final double roomWidth;

    /**
     * Constructs an instance of RoomWidthVO with the provided room width.
     * This constructor initializes a RoomWidthVO with the provided room width value.
     * It checks if the provided room width is valid (higher than zero) before setting the value.
     * @param roomWidth The width of the room.
     * @throws IllegalArgumentException if the roomWidth parameter is not valid (less than or equal to zero). This exception
     * is thrown to indicate an invalid or missing room width.
     */
    public RoomWidthVO (double roomWidth){
        if (isParamValid(roomWidth)) {
            this.roomWidth = roomWidth;
        }
        else {
            throw new IllegalArgumentException("Room Width has to be higher than zero");
        }
    }

    /**
     * Checks if width is above or equal to zero;
     * @param width Width
     * @return True or False
     */
    private boolean isParamValid(double width){
        return width > 0;
    }

    /**
     * Simple getter method
     * @return Returns the encapsulated value;
     */
    @Override
    public Double getValue() {
        return this.roomWidth;
    }
}
