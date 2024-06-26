package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

public class CountryVO implements ValueObject<String> {

    private final String country;

    /**
     * Constructor for CountryVO. Ensures value is not null or empty before creating the object.
     * @param country Country
     */
    public CountryVO(String country){
        if (country == null || country.trim().isEmpty() || !validCountry(country))
            throw new IllegalArgumentException("Please insert a valid country name.");
        this.country = country;
    }

    /**
     * Validates if the country is one of four permitted countries (Portugal, USA, Spain or France)
     * @param country String containing the country
     * @return boolean, true if it matches the permitted countries, false otherwise.
     */

    private boolean validCountry(String country){
        return switch (country) {
            case "Portugal", "USA", "Spain", "France" -> true;
            default -> false;
        };
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.country;
    }
}
