package smarthome.domain.vo.devicevo;

import smarthome.domain.vo.ValueObject;

public class DeviceNameVO implements ValueObject<String> {

    private final String deviceName;

    /**
     * Constructor for DeviceNameVO. Before creating the object, it checks if value is not null or empty
     * @param deviceName Device Name
     */
    public DeviceNameVO(String deviceName) {
        if(deviceName == null || deviceName.isBlank()) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.deviceName = deviceName;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.deviceName;
    }
}
