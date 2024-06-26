package smarthome.domain.vo.housevo;

public class AddressVO {

    private final DoorVO doorVO;
    private final StreetVO streetVO;
    private final CityVO cityVO;
    private final CountryVO countryVO;
    private final PostalCodeVO postalCodeVO;

    /**
     * Constructor for AddressVO. Before creating the object, it requests validation of each parameter.
     * @param doorVO Doort
     * @param streetVO Street
     * @param cityVO City
     * @param countryVO Country
     * @param postalCodeVO Postal Code
     */
    public AddressVO(DoorVO doorVO, StreetVO streetVO, CityVO cityVO, CountryVO countryVO, PostalCodeVO postalCodeVO) {
        if (!validArgs(doorVO, streetVO, cityVO, countryVO, postalCodeVO) || !validCountryVOPostalCodeVOCombination(countryVO, postalCodeVO))
            throw new IllegalArgumentException("Invalid arguments.");
        this.doorVO=doorVO;
        this.streetVO=streetVO;
        this.cityVO=cityVO;
        this.countryVO=countryVO;
        this.postalCodeVO=postalCodeVO;
    }

    /**
     * Simple getter method
     * @return Door value
     */
    public String getDoor() {
        return doorVO.getValue();
    }

    /**
     * Simple getter method
     * @return Street value
     */
    public String getStreet(){
        return streetVO.getValue();
    }

    /**
     * Simple getter method
     * @return City value
     */
    public String getCity(){
        return cityVO.getValue();
    }

    /**
     * Simple getter method
     * @return Country value
     */
    public String getCountry(){
        return countryVO.getValue();
    }

    /**
     * Simple getter method
     * @return Postal Code value
     */
    public String getPostalCode(){
        return postalCodeVO.getValue();
    }

    /**
     * Validates if the country and postal code are a valid combination.
     * The inserted CountryVO must match de correct country code inserted in PostalCodeVO.
     * @param countryVO CountryVO that encapsulates a String with the country name
     * @param postalCodeVO PostalCodeVO that encapsulates a String with the postal code in ISO 3166-1 alfa-2 convention.
     * @return boolean, true if the combination is valid, false otherwise.
     */

    private boolean validCountryVOPostalCodeVOCombination(CountryVO countryVO, PostalCodeVO postalCodeVO) {
        String country = countryVO.getValue().trim().toLowerCase();
        int index = postalCodeVO.getValue().indexOf("-");
        String countryCode = postalCodeVO.getValue().substring(0, index);
        return switch (country) {
            case "portugal" -> countryCode.equals("PT");
            case "usa" -> countryCode.equals("US");
            case "spain" -> countryCode.equals("ES");
            case "france" -> countryCode.equals("FR");
            default -> false;
        };
    }

    /**
     * Validates arguments are not null
     * @param doorVO Door
     * @param streetVO Street
     * @param cityVO City
     * @param countryVO Country
     * @param postalCodeVO Postal Code
     * @return True or false
     */
    private boolean validArgs(DoorVO doorVO, StreetVO streetVO, CityVO cityVO, CountryVO countryVO, PostalCodeVO postalCodeVO) {
        return doorVO != null && streetVO != null && cityVO != null && countryVO != null && postalCodeVO != null;
    }
}

