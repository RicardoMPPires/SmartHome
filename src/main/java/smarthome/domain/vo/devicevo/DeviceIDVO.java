package smarthome.domain.vo.devicevo;

import smarthome.domain.DomainID;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a value object for Device ID.
 */
public class DeviceIDVO implements DomainID {

    private final UUID deviceID;

    /**
     * Constructs a DeviceIDVO object with the given identifier (ID).
     *
     * @param deviceID The UUID identifier for the device.
     * @throws IllegalArgumentException If the identifier is null.
     */

    public DeviceIDVO (UUID deviceID){
        if(deviceID == null){
            throw new IllegalArgumentException("Invalid Identifier");
        }
        this.deviceID = deviceID;
    }


    /**
     * Gets the string representation of the device identifier (ID).
     *
     * @return The string representation of the device identifier (ID).
     */

    @Override
    public String getID() {
        return this.deviceID.toString();
    }

    /**
     * Compares this DeviceIDVO object to another object.
     *
     * @param o The object to compare this DeviceIDVO object to.
     * @return True if the given object is equal to this DeviceIDVO object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceIDVO deviceIDVO = (DeviceIDVO) o;
        return Objects.equals(this.deviceID, deviceIDVO.deviceID);
    }

    /**
     * Generates a hash code for this DeviceIDVO object.
     *
     * @return The hash code for this DeviceIDVO object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.deviceID);
    }

}
