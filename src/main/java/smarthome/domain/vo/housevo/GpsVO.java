package smarthome.domain.vo.housevo;

public class GpsVO {
    private final LatitudeVO latitude;
    private final LongitudeVO longitude;

    /**
     * GPS Constructor for the corresponding Value Object composed by a Latitude and Longitude Value Objects
     * @param latitude Latitude Value Object
     * @param longitude Longitude Value Object
     */
    public GpsVO(LatitudeVO latitude, LongitudeVO longitude){
        if(latitude == null || longitude == null)
            throw new IllegalArgumentException("Latitude and longitude cannot be null.");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Simple getter method
     * @return Encapsulated latitude
     */
    public double getLatitude(){
        return latitude.getValue();
    }

    /**
     * Simple getter method
     * @return Encapsulated longitude
     */
    public double getLongitude(){
        return longitude.getValue();
    }

}
