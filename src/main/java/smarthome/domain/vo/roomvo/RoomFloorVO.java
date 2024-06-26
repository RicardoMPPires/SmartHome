package smarthome.domain.vo.roomvo;

import smarthome.domain.vo.ValueObject;

public class RoomFloorVO implements ValueObject<Integer> {
    private final int roomFloor;

    /**
     * Constructor for RoomFloorVO. No validations are performed.
     * @param roomFloor Room floor
     */
    public RoomFloorVO(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    /**
     * Simple getter method
     * @return Floor;
     */
    @Override
    public Integer getValue() {
        return this.roomFloor;
    }
}
