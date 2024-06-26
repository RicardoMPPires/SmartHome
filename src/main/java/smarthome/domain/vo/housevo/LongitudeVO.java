package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

public class LongitudeVO implements ValueObject<Double> {

    private final double longitudeValue;

    /**
     * Constructor for longitudeVO, ensures the validity before creating the object.
     * @param longitudeValue Longitude
     */
    public LongitudeVO(double longitudeValue)  {
        if(invalidLongitudeValues(longitudeValue)){
            throw new IllegalArgumentException("Invalid longitude value");
        }
        this.longitudeValue = longitudeValue;
    }

    /**
     * Validades longitude
     * @param longitudeValue Longitude
     * @return True or False
     */
    private boolean invalidLongitudeValues(double longitudeValue){
        return longitudeValue > 180.0 || longitudeValue < -180.0;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public Double getValue() {
        return this.longitudeValue;
    }
}
