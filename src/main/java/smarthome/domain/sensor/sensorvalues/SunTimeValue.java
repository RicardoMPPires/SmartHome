package smarthome.domain.sensor.sensorvalues;

import java.time.ZonedDateTime;

/**
 * Implementation of the Value interface for representing sun time values.
 * This class stores a ZonedDateTime value representing a specific time,
 * typically the sunrise or sunset time.
 */
public class SunTimeValue implements SensorValueObject<ZonedDateTime> {

    /**
     * The ZonedDateTime value representing the sun time.
     */
    private final ZonedDateTime sunTime;

    /**
     * Constructs a SunTimeValue with the provided ZonedDateTime.
     *
     * @param sunTime The ZonedDateTime value to be stored.
     * @throws IllegalArgumentException If the provided sunTimeValue is null.
     */
    public SunTimeValue(ZonedDateTime sunTime) {
        if (sunTime == null) {
            throw new IllegalArgumentException("Invalid sun time value");
        }
        this.sunTime = sunTime;
    }

    /**
     * Retrieves the ZonedDateTime value stored in this SunTimeValue.
     *
     * @return The ZonedDateTime value representing the sun time.
     */
    @Override
    public ZonedDateTime getValue() {
        return this.sunTime;
    }
}