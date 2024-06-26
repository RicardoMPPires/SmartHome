package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

public class PostalCodeVO implements ValueObject<String> {

    private final String postalCode;

    /**
     * Constructor for PostalCodeVO, validates encapsulated value is not null or empty;
     * @param postalCode Postal Code
     */
    public PostalCodeVO(String postalCode){
        if (postalCode == null || postalCode.trim().isEmpty() || !validPostalCode(postalCode))
            throw new IllegalArgumentException("Please insert a valid country name.");
        this.postalCode = postalCode;
    }

    /**
     * Validates the postal code. The Postal Code must have a country code and a hyphen following the ISO 3166-1 alfa-2 convention.
     * @param postalCode Postal Code
     * @return boolean
     */

    private boolean validPostalCode(String postalCode){
        int index = postalCode.indexOf("-");
        String countryCode = postalCode.substring(0, index);
        return switch (countryCode) {
            case "PT", "US", "ES", "FR" -> true;
            default -> false;
        };
    }

    /**
     * Simple getter method.
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return postalCode;
    }
}
