package smarthome.domain.vo.housevo;

public class LocationVO {

    private final AddressVO addressVO;
    private final GpsVO gpsVO;

    /**
     * Location constructor for the corresponding Value Object, with addressVO and gpsVO as parameters, throwing an Illegal
     * Argument Exception if either of the parameters' null.
     *
     * @param addressVO AddressVO object
     * @param gpsVO gpsVO object
     * @throws IllegalArgumentException If any VOs are null
     */
    public LocationVO(AddressVO addressVO, GpsVO gpsVO) throws IllegalArgumentException{
        if (addressVO == null || gpsVO == null)
            throw new IllegalArgumentException("Invalid parameter.");
        this.addressVO = addressVO;
        this.gpsVO = gpsVO;
    }

    /**
     * Method that returns the door number string from the addressVO.
     */
    public String getDoor(){
        return addressVO.getDoor();
    }

    /**
     * Method that returns the street string from addressVO.
     */
    public String getStreet(){
        return addressVO.getStreet();
    }

    /**
     * Method that returns the city string from the addressVO.
     */
    public String getCity(){
        return addressVO.getCity();
    }

    /**
     * Method that returns the string country from the addressVO.
     */
    public String getCountry(){
        return addressVO.getCountry();
    }

    /**
     * Method that returns the string postal code from the addressVO.
     */
    public String getPostalCode(){
        return addressVO.getPostalCode();
    }

    /**
     * Method that returns the double latitude from the gpsVO.
     */
    public double getLatitude(){
        return gpsVO.getLatitude();
    }

    /**
     * Method that returns the double longitude from the gpsVO.
     */
    public double getLongitude(){
        return gpsVO.getLongitude();
    }

    /**
     * Method that returns as string the concatenation of the attributes of both addressVO and gpsVO.
     */
    @Override
    public String toString() {
        return "Location: Address - " +
                getStreet() + ", " +
                getDoor() + ". " +
                getPostalCode() + ", " +
                getCity() + ", " +
                getCountry() + ". GPS - latitude: " +
                getLatitude() + ", longitude: " +
                getLongitude();
    }

}
