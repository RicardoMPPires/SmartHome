package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.*;
import smarthome.domain.device.Device;

@Entity
@Table(name = "Device")
public class DeviceDataModel {
    @Id
    @Column(name = "id")
    private String deviceID;
    @Column(name = "device_name")
    private String deviceName;
    @Column(name = "device_model")
    private String deviceModel;
    @Column(name = "device_status")
    private boolean deviceStatus;
    @Column(name = "room_id")
    private String roomID;

    /**
     * Default constructor. Required by JPA.
     */
    public DeviceDataModel() {}
    /**
     * Constructor that creates a deviceDataModel object from a Device object. It takes a Device object as a parameter
     * and extracts the necessary information to create a DeviceDataModel object.
     * It is used to create a DeviceDataModel object from a Device object before persisting it in the database.
     *
     * @param device The Device object from which to create the DeviceDataModel object.
     */
    public DeviceDataModel(Device device) {

        this.deviceID = device.getId().getID();
        this.deviceName = device.getDeviceName().getValue();
        this.deviceModel = device.getDeviceModel().getValue();
        this.deviceStatus = device.getDeviceStatus().getValue();
        this.roomID = device.getRoomID().getID();
        }
    /**
     * Method to be used on the DeviceRepositoryJPA to update objects
     *
     * @param device The Device object from which to create the DeviceDataModel object.
     */
    public boolean updateFromDomain(Device device) {
        this.deviceID = device.getId().getID();
        this.deviceName = device.getDeviceName().getValue();
        this.deviceModel = device.getDeviceModel().getValue();
        this.deviceStatus = device.getDeviceStatus().getValue();
        this.roomID = device.getRoomID().getID();

        return true;
    }
    /**
     * Getters for the DeviceDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public String getDeviceID() {
        return deviceID;
    }
    /**
     * Getters for the DeviceDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public String getDeviceName() {return deviceName;
    }
    /**
     * Getters for the DeviceDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public String getDeviceModel() {
        return deviceModel;
    }
    /**
     * Getters for the DeviceDataModel attributes.
     *
     * @return The value of the attribute.
     */

    public boolean getDeviceStatus() {
        return deviceStatus;
    }


        /**
         * Getters for the DeviceDataModel attributes.
         *
         * @return The value of the attribute.
         */


    public String getRoomID() { return roomID; }

}


