package smarthome.domain.device;

import smarthome.domain.AggregateRoot;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.roomvo.RoomIDVO;

import java.util.UUID;

public class Device implements AggregateRoot {

    private DeviceNameVO deviceName;
    private final DeviceModelVO deviceModel;
    private final RoomIDVO roomID;
    private DeviceStatusVO deviceStatus;
    private final DeviceIDVO deviceID;


    /**
     * Constructs a new Device object with the specified device name, model, and room ID.
     * Validates the provided parameters to ensure they are not null.
     * Sets the initial device status to as true, representing that the device is active.
     * Assigns a unique device ID using a randomly generated UUID.
     */
    public Device(DeviceNameVO deviceName, DeviceModelVO deviceModel, RoomIDVO roomID) {
        if (!allParametersAreValid(deviceName, deviceModel, roomID)) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.roomID = roomID;
        this.deviceStatus = new DeviceStatusVO(true);
        this.deviceID = new DeviceIDVO(UUID.randomUUID());
    }

    /**
     * Constructs a Device with the specified parameters.
     * This constructor is used when the device already has an ID. It's main purpose is to be used to create domain
     * objects from existing data models persisted in a database. No checks are made on the parameters as they
     * are assumed to be valid because they are coming from the database and when first created as domain objects
     * all parameters where checked.
     *
     * @param deviceIDVO     The ID of the device.
     * @param deviceName     The name of the device.
     * @param deviceModel    The deviceModel of the device.
     * @param deviceStatusVO The status of the device, it`s a boolean
     * @param roomID         The ID of the room to which the device belongs.
     */
    public Device(DeviceIDVO deviceIDVO, DeviceNameVO deviceName, DeviceModelVO deviceModel, DeviceStatusVO deviceStatusVO, RoomIDVO roomID) {

        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.roomID = roomID;
        this.deviceStatus = deviceStatusVO;
        this.deviceID = deviceIDVO;
    }

    /**
     * Deactivates the device.
     *
     * @return true if the device was successfully deactivated, false otherwise.
     */
    public boolean deactivateDevice() {
        this.deviceStatus = new DeviceStatusVO(false);

        return !this.deviceStatus.getValue();
    }

    /**
     * Checks if all the provided device parameters are valid (not null).
     *
     * @param deviceName  the name of the device
     * @param deviceModel the model of the device
     * @param roomID      the ID of the room where the device is located
     * @return true if all parameters are valid, false otherwise
     */

    private boolean allParametersAreValid(DeviceNameVO deviceName, DeviceModelVO deviceModel, RoomIDVO roomID) {
        return deviceName != null && deviceModel != null && roomID != null;
    }

    /**
     * Gets the device's name.
     *
     * @return the device name object (DeviceNameVO)
     */
    public DeviceNameVO getDeviceName() {
        return this.deviceName;
    }

    /**
     * Gets the device's model.
     *
     * @return the device model object (DeviceModelVO)
     */
    public DeviceModelVO getDeviceModel() {
        return this.deviceModel;
    }

    /**
     * Gets the device's status.
     *
     * @return the device status object (DeviceStatusVO)
     */
    public DeviceStatusVO getDeviceStatus() {
        return this.deviceStatus;
    }

    /**
     * Gets the device's ID.
     *
     * @return the device ID object (DeviceIDVO)
     */
    public RoomIDVO getRoomID() {
        return this.roomID;
    }

    /**
     * Gets the device's ID.
     *
     * @return the device ID object (DeviceIDVO)
     */
    @Override
    public DeviceIDVO getId() {
        return this.deviceID;
    }

    public boolean isActive() {
        return this.deviceStatus.getValue();
    }
}

