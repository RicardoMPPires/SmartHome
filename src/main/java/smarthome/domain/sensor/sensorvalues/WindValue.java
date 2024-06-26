package smarthome.domain.sensor.sensorvalues;

import static java.lang.Integer.parseInt;

/**
 * Represents a wind value consisting of wind speed and wind direction.
 * This class encapsulates this wind speed and direction type of information and validates
 * it´s integrity and format. It also provides an easy way of accessing that data using a common interface
 * that unifies all sensor values.
 */

public class WindValue implements SensorValueObject<String> {

    private final String windSpeed;
    private final String windDirection;
    String[] permittedDirections = {"N", "S", "E", "W", "NW", "NE", "SW", "SE"};

    /**
     * Constructs a WindValue object with the given reading.
     *
     * @param reading the reading in the format "windSpeed:windDirection"
     * @throws IllegalArgumentException if the reading is invalid
     */

    public WindValue(String reading)  { //Reading must have this format -> 44:W
        if(!isReadingValid(reading)){
            throw new IllegalArgumentException("Invalid reading");
        }
        String[] words = reading.split(":");
        this.windSpeed = words[0];
        this.windDirection = words[1].toUpperCase();
    }

    /**
     * Retrieves the wind value as an array containing wind speed and wind direction.
     * @return an array containing wind speed at index 0 and wind direction at index 1
     */

    public String getValue() {
        return this.windSpeed + ":" + this.windDirection;
    }

    /**
     * Checks if the given wind direction is valid.
     * It compares the given direction with all valid directions defined in
     * permittedDirections variable
     *
     * @param windDirection the wind direction to be validated
     * @return true if the wind direction is valid, otherwise false
     */

    private boolean isDirectionValid (String windDirection){
        for (String direction : permittedDirections){
            if (direction.equals(windDirection)){
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the reading. Ensures the reading is not null, can be split using the specified regex,
     * and that the wind speed can be parsed to integer. After that verifications it is also ensured that
     * the wind speed is not negative and the provided direction is valid.
     * @param reading Reading to validate
     * @return True or False
     */
    private boolean isReadingValid(String reading){
        int windSpeedValue;
        String windDirectionValue;
        try {
            String[] words = reading.split(":");
            windSpeedValue = parseInt(words[0]);
            windDirectionValue = words[1].toUpperCase();
        } catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException e){
            return false;
        }
        return isDirectionValid(windDirectionValue) && windSpeedValue > 0;
    }

}
