package smarthome.domain.vo.devicevo;

import smarthome.domain.vo.ValueObject;

public class DeviceStatusVO implements ValueObject<Boolean> {

    private final boolean deviceStatus;

    /**
     * Constructor for DeviceStatusVO. No validations required.
     * @param deviceStatus Device Status
     */
    public DeviceStatusVO (boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public Boolean getValue() {
        return this.deviceStatus;
    }
}
