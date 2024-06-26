package smarthome.domain.vo;

import static java.lang.Integer.parseInt;

/**
 * Value object class representing a time difference in minutes between two timestamps.
 */
public class DeltaVO implements ValueObject<Integer> {

    private final int delta;
    private static final int DEFAULT = 5; // Default time delta in minutes for comparisons of instant matches on readings.

    /**
     * Constructs a DeltaVO object with the specified parameter.
     * If the parameter is null or empty, the method sets the time delta to the default value.
     * Also, if the parameter is less than or equal to 0, the method sets the time delta to the default value.
     *
     * @param delta the time difference in minutes between the start and end times
     */
    public DeltaVO(String delta) {
        if (delta == null || delta.trim().isEmpty()) {
            this.delta = DEFAULT;
        } else {
            int deltaMin = parseInt(delta);
            this.delta = defineDelta(deltaMin);
            }
    }

    /**
     * Defines the time difference in minutes between the start and end times.
     * If the delta provided is less than or equal to 0, the method returns the default time delta.
     * @param delta the time difference in minutes between the start and end times
     * @return the time difference in minutes between the start and end times
     */
    private int defineDelta(int delta) {
        if( delta <= 0) {
            return DEFAULT;
        } else {
            return delta;
        }
    }


    /**
     * Getter method that returns the time difference in minutes between the start and end times.
     * @return the time difference in minutes between the start and end times
     */
    @Override
    public Integer getValue() {
        return delta;
    }
}
