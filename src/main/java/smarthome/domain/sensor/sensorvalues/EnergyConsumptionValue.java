package smarthome.domain.sensor.sensorvalues;

import static java.lang.Integer.parseInt;

public class EnergyConsumptionValue implements SensorValueObject<Integer> {
    private final int primitiveValue;

    /**
     * Constructor for EnergyConsumptionValue
     * @param reading Reading value
     * @throws IllegalArgumentException If reading invalid
     */
    public EnergyConsumptionValue(String reading) {
        if(!validateReading(reading)){
            throw new IllegalArgumentException("Invalid reading");
        }
        this.primitiveValue = parseInt(reading);
    }

    /**
     * Method to validate the reading
     * @param reading Reading value
     * @return boolean
     */
    private boolean validateReading(String reading) {
        try {
            parseInt(reading);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public Integer getValue() {
        return this.primitiveValue;
    }

    public String getValueAsString() {
        return primitiveValue + "";
    }
}