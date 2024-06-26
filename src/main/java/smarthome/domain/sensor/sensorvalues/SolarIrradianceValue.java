package smarthome.domain.sensor.sensorvalues;

import static java.lang.Integer.parseInt;

public class SolarIrradianceValue implements SensorValueObject<Integer> {

    private final int primitiveValue;

    /**
     * Constructor for SolarIrradianceValue
     * @param reading Sensor reading in a String format to be converted
     * @throws IllegalArgumentException If reading is invalid (null or not an integer value equal or higher than zero)
     */
    public SolarIrradianceValue(String reading) {
        if (!validReading(reading)) {
            throw new IllegalArgumentException("Invalid reading");
        }
        this.primitiveValue = parseInt(reading);
    }

    /**
     * Verifies if the reading is valid
     * @param reading String to store the reading of the sensor
     * @return True if the reading is valid (integer number and equal or higher than zero),
     * false otherwise
     */

    private boolean validReading(String reading) {
        if(reading == null)
            return false;
        try {
            int parsedReading = parseInt(reading);
            return parsedReading >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Simple getter method to retrieve the sensor reading
     * @return Value of the sensor reading in an integer format
     */
    public Integer getValue() {
        return this.primitiveValue;
    }
}
