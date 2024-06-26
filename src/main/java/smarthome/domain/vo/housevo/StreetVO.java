package smarthome.domain.vo.housevo;

import smarthome.domain.vo.ValueObject;

public class StreetVO implements ValueObject<String> {
    private final String street;

    /**
     * Constructor for StreetVO, ensures value is not null or empty.
     * @param street Street
     */
    public StreetVO(String street) {
        if(street == null || street.isBlank()) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.street = street;
    }

    /**
     * Simple getter method
     * @return Encapsulated value
     */
    @Override
    public String getValue() {
        return this.street;
    }
}
