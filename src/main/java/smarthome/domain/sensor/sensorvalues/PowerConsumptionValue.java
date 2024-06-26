package smarthome.domain.sensor.sensorvalues;

import static java.lang.Integer.parseInt;

public class PowerConsumptionValue implements SensorValueObject<Integer> {

    /**
     * The integer value representing the power consumption.
     */
    private final int powerConsumption;

    /**
     * Constructor for PowerConsumptionValue class.
     * @param powerConsumption int value to be stored. Must be greater than or equal to 0.
     * @throws IllegalArgumentException if powerConsumptionValue is negative.
     */
    public PowerConsumptionValue(String powerConsumption) {
        if (!isReadingValid(powerConsumption)) {
            throw new IllegalArgumentException("Invalid power consumption value");
        }
        this.powerConsumption = parseInt(powerConsumption);
    }

    private boolean isReadingValid(String positionValue) {
        try {
            int parsedReading = parseInt(positionValue);
            return parsedReading >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Retrieves the integer value stored in this PowerConsumptionValue.
     * @return The integer value representing the power consumption.
     */
    @Override
    public Integer getValue() {
        return this.powerConsumption;
    }
}
