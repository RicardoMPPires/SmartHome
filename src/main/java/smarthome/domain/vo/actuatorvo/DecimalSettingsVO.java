package smarthome.domain.vo.actuatorvo;


public class DecimalSettingsVO implements Settings {
    private final double lowerLimit;
    private final double upperLimit;
    private final double precision_value;

    /**
     * Constructor for DecimalConfigurationVO.
     * @param lowerLimit Set lower limit as String
     * @param upperLimit Set upper limit as String
     * @param precision_value Set precision as String
     * @throws IllegalArgumentException If parameter validation fails (null settings or invalid values).
     */
    public DecimalSettingsVO(String lowerLimit, String upperLimit, String precision_value) {
        try {
            double lowerValue = Double.parseDouble(lowerLimit);
            double upperValue = Double.parseDouble(upperLimit);
            double precisionValue = Double.parseDouble(precision_value);
            if(validSettings(lowerValue, upperValue, precisionValue)) {
                this.lowerLimit = lowerValue;
                this.upperLimit = upperValue;
                this.precision_value = precisionValue;
            } else {
                throw new IllegalArgumentException("Invalid actuator settings");
            }
        } catch (NullPointerException | NumberFormatException e){
            throw new IllegalArgumentException("Invalid actuator settings");
        }
    }

    /**
     * Verifies if parameters are valid in a whole, considering the following conditions:
     * Lower limit cannot be higher or equal than the upper limit;
     * Precision must be higher than zero;
     * Lower and upper limits must have the same defined precision;
     * Number of decimal places (DP) of the limit values cannot be higher than the DP defined for the precision.
     * @param lowerLimit Lower set limit as double
     * @param upperLimit Upper set limit as double
     * @param precision Precision set as double
     * @return True if all conditions are met.
     */
    private boolean validSettings(double lowerLimit, double upperLimit, double precision){
        int lowerLimDP = countDecimalPlaces(lowerLimit);
        int upperLimDP = countDecimalPlaces(upperLimit);
        int precisionDP = countDecimalPlaces(precision);
        if(lowerLimit >= upperLimit)
            return false;
        if(precision <= 0.0 || precision >= 1.0)
            return false;
        if(lowerLimDP != upperLimDP)
            return false;
        if(lowerLimDP > precisionDP)
            return false;
        return true;
    }

    /**
     * Calculates the number of decimal places of a double value.
     * In case the value introduced does not have decimal places, it implicitly has one decimal place (e.g. value
     * introduced: 5, it is interpreted as 5.0).
     * @param value Value to be analysed
     * @return The number of decimal places of the double value
     */
    private int countDecimalPlaces(double value) {
        String numberAsString = Double.toString(Math.abs(value));
        int decimalSeparatorIndex = numberAsString.indexOf('.');
        return numberAsString.length() - decimalSeparatorIndex - 1;
    }

    /**
     * Mandatory method resultant from the implementation of the Settings interface (which extends ValueObject interface).
     * Retrieves the actuator settings.
     * @return A double array containing actuator defined settings where:
     * index 0 is the lower limit;
     * index 1 is the upper limit;
     * index 2 is the precision.
     */
    @Override
    public Double[] getValue() {
        return new Double[]{lowerLimit, upperLimit, precision_value};
    }
}
