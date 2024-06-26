package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.room.Room;

/**
 * RoomDataModel is a class that represents the Room entity in the database.
 * It is used to store Room objects in the database.
 */

@Entity
@Table(name = "room")
public class RoomDataModel {

    @Id
    @Column(name = "id")
    private String roomID;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "room_floor")
    private int roomFloor;
    @Column(name = "room_length")
    private double roomLength;
    @Column(name = "room_width")
    private double roomWidth;
    @Column(name = "room_height")
    private double roomHeight;
    @Column(name = "house_id")
    private String houseID;

    /**
     * Default constructor. Required by JPA.
     */

    public RoomDataModel() {
    }

    /**
     * Constructor that creates a RoomDataModel object from a Room object. It takes a Room object as a parameter
     * and extracts the necessary information to create a RoomDataModel object.
     * It is used to create a RoomDataModel object from a Room object before persisting it in the database.
     *
     * @param room The Room object from which to create the RoomDataModel object.
     */

    public RoomDataModel(Room room) {
        this.roomID = room.getId().getID();
        this.roomName = room.getRoomName().getValue();
        this.roomFloor = room.getFloor().getValue();
        this.roomLength = room.getRoomDimensions().getRoomLength();
        this.roomWidth = room.getRoomDimensions().getRoomWidth();
        this.roomHeight = room.getRoomDimensions().getRoomHeight();
        this.houseID = room.getHouseID().getID();
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public String getRoomID() {
        return roomID;
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public String getRoomName() {
        return roomName;
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public int getRoomFloor() {
        return roomFloor;
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public double getRoomLength() {
        return roomLength;
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public double getRoomWidth() {
        return roomWidth;
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public double getRoomHeight() {
        return roomHeight;
    }

    /**
     * Getters for the RoomDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public String getHouseID() {
        return houseID;
    }
}
