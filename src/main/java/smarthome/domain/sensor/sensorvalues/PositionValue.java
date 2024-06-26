package smarthome.domain.sensor.sensorvalues;

import static java.lang.Integer.parseInt;

/**
 * Implementation of the Value interface for representing position values.
 * This class stores an integer value representing a position that has to be between
 * 0 and 100.
 */
public class PositionValue implements SensorValueObject<Integer> {

    private final int position;

    /**
     * Constructs a PositionValue object with the provided integer value.
     * @param position The integer value to be stored.
     * @throws IllegalArgumentException If the provided positionValue is
     * not between 0 and 100.
     */
    public PositionValue(String position) {
        if (!isReadingValid(position)) {
            throw new IllegalArgumentException("Invalid Position Value, has to be between 0 and 100");
        }
        this.position = parseInt(position);
    }

    /**
     * Validates the position value so that it is between 0 and 100.
     */
    private boolean isReadingValid(String positionValue) {
        try {
            int parsedReading = parseInt(positionValue);
            return parsedReading >= 0 && parsedReading <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Integer getValue() {
        return this.position;
    }
}
