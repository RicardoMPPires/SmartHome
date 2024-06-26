package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

public class LatitudeVO implements ValueObject<Double> {

    private final double latitudeValue;

    /**
     * Constructor for LatitudeVO, validates latitude before creating the object
     * @param latitudeValue Latitude
     */
    public  LatitudeVO(double latitudeValue) {
        if(invalidLatitudeValues(latitudeValue)){
            throw new IllegalArgumentException("Invalid latitude value");
        }
        this.latitudeValue = latitudeValue;
    }

    /**
     * Validates latitude is between -90 and 90
     * @param latitudeValue Latitude
     * @return True or false
     */
    private boolean invalidLatitudeValues(double latitudeValue){
        return latitudeValue > 90.0 || latitudeValue < -90.0;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public Double getValue() {
        return this.latitudeValue;
    }
}
