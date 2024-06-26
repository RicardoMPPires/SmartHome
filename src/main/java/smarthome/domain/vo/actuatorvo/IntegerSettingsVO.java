package smarthome.domain.vo.actuatorvo;

public class IntegerSettingsVO implements Settings {

    private final int lowerLimit;
    private final int upperLimit;

    /**
     * Constructor for IntegerSettingsVO.
     * First, parses the lower and upper limits to integers.
     * Then, checks if the limits are valid.
     * If valid, sets the limits.
     * If not, throws an exception.
     * Catches exceptions thrown by parsing the limits.
     * @param lowerLimit Lower Limit
     * @param upperLimit Upper Limit
     * @throws IllegalArgumentException if the limits are invalid.
     */
    public IntegerSettingsVO(String lowerLimit, String upperLimit) {
        try {
            int lowerValue = Integer.parseInt(lowerLimit);
            int upperValue = Integer.parseInt(upperLimit);
            if (validLimits(lowerValue, upperValue)) {
                this.lowerLimit = lowerValue;
                this.upperLimit = upperValue;
            } else {
                throw new IllegalArgumentException("Upper limit can't be less than or equal to lower limit.");
            }
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Invalid actuator settings");
        }
    }

    /**
     * Checks if the limits are valid.
     * @param lowerLimit Lower Limit
     * @param upperLimit Upper Limit
     * @return true if the limits are valid, false otherwise.
     */
    private boolean validLimits(int lowerLimit, int upperLimit) {
        return lowerLimit < upperLimit;
    }

    /**
     * Returns the limits as an array.
     * First, instantiates an array of integers with two positions.
     * Then, sets the lower and upper limits in the array, respectively to index 0 and 1.
     * @return an array containing the lower and upper limits.
     */
    @Override
    public Integer[] getValue() {
        Integer[] integerConfigs = new Integer[2];
        integerConfigs[0] = this.lowerLimit;
        integerConfigs[1] = this.upperLimit;
        return integerConfigs;
    }
}
