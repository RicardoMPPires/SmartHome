package smarthome.domain.sensor.sensorvalues;


public class SwitchValue implements SensorValueObject<String> {
    private final String valueSwitch;

    /**
     * Constructs a SwitchValue object with the specified value.
     * This constructor creates a SwitchValue object with the provided value. It validates the provided value
     * to ensure it meets the requirements for a switch value. If the value is invalid, an IllegalArgumentException
     * is thrown.
     *
     * @param value The value to be assigned to the SwitchValue object.
     * @throws IllegalArgumentException If the provided value is invalid for a switch value.
     */

    public SwitchValue(String value) {
        if (!isReadingValid(value)) {
            throw new IllegalArgumentException("Invalid switch value");
        }
        this.valueSwitch = value;
    }

    /**
     * Retrieves the value of the SwitchValue object.
     * This method returns the value stored in the SwitchValue object.
     *
     * @return The value of the SwitchValue object.
     */

    public String getValue() {
        return this.valueSwitch;
    }

    /**
     * Checks if the given switch value is valid.
     * This method verifies if the provided switch value is either "On" or "Off".
     *
     * @param switchValue The switch value to be validated.
     * @return True if the switch value is valid ("On" or "Off"), false otherwise.
     */

    private boolean isReadingValid(String switchValue) {
        if (switchValue == null) return false;
        return switchValue.trim().equalsIgnoreCase("On") || switchValue.trim().equalsIgnoreCase("Off");
    }
}
