package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

/**
 * Represents a value object for City.
 */
public class CityVO implements ValueObject<String> {
        private final String city;

        /**
         * Constructs a CityVO object with the given city.
         *
         * @param city The city.
         * @throws IllegalArgumentException If the city is null or empty.
         */

        public CityVO(String city) {
            if(city == null || city.isBlank()) {
                throw new IllegalArgumentException("Invalid parameters.");
            }
            this.city = city;
        }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.city;
    }
}
