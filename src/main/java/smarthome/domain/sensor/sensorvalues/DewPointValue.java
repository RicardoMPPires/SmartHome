package smarthome.domain.sensor.sensorvalues;

import static java.lang.Double.parseDouble;

public class DewPointValue implements SensorValueObject<Double> {
    private final double dewPoint;
    /**
     * Constructor for Dew point value. It receives a dewPointValue in string form and validates it.
     * @param dewPoint dewPointValue
     * @throws IllegalArgumentException If reading invalid
     */
    public DewPointValue(String dewPoint) {
        if (!isReadingValid(dewPoint)) {
            throw new IllegalArgumentException("Invalid DewPoint reading");
        }
        this.dewPoint = parseDouble(dewPoint);
    }
    /**
     * A simple getter method of Value.
     * @return Encapsulated DewPointvalue.
     */

    public Double getValue() {
        return this.dewPoint;
    }

    /**
     * Ensures dewPointValue is not null or empty, as it would fail the parsing straight away. It then attempts to parse it into
     * a double.
     * @param dewPointValue dewPointValue
     * @return True or false.
     */

    private boolean isReadingValid(String dewPointValue) {
        try {
            parseDouble(dewPointValue);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}