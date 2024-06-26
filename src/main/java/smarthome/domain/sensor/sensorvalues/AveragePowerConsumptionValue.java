package smarthome.domain.sensor.sensorvalues;

import static java.lang.Integer.parseInt;

/**
 * Value object for the average power consumption value
 */

public class AveragePowerConsumptionValue implements SensorValueObject<Integer> {

    private final int averagePowerConsumption;

    /**
     * Constructor for the average power consumption value. It validates the reading and throws an exception if it is invalid.
     *
     * @param reading the reading to be stored
     */

    public AveragePowerConsumptionValue(String reading) {
        if (!validateReading(reading)) {
            throw new IllegalArgumentException("Invalid reading");
        }
        this.averagePowerConsumption = parseInt(reading);
    }

    /**
     * Validates the reading. It must be a number and greater than or equal to 0.
     *
     * @param reading the reading to be validated
     * @return true if the reading is valid, false otherwise
     */

    private boolean validateReading(String reading) {
        double valueOfReading;
        try {
            valueOfReading = parseInt(reading);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return valueOfReading >= 0;
    }

    /**
     * Gets the value of the average power consumption.
     * It is an implementation of the getValue method from the ValueObject interface.
     *
     * @return the value of the average power consumption
     */

    @Override
    public Integer getValue() {
        return this.averagePowerConsumption;
    }
}
