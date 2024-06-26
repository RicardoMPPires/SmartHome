package smarthome.domain.sensor.externalservices;

import org.shredzone.commons.suncalc.SunTimes;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

@Component
public class SunTimeCalculator implements SunTimeServices {

    /**
     * This method computes the sunset time given a date and GPS coordinates.
     * @param date Date of the day of the request. Accepted format: YYYY-MM-DD
     * @param gpsCoordinates Decimal location coordinates. Accepted format: (latitude : longitude)
     * @return The sunset time as a ZonedDateTime object. It returns null in case an IllegalArgumentException is thrown when invoking
     * the getSunTimes() method, due to invalid coordinates or date input.
     */
    public ZonedDateTime computeSunset(String date, String gpsCoordinates){
        SunTimes sunsetTime;
        try{
            sunsetTime = getSunTimes(date,gpsCoordinates);
            return Objects.requireNonNull(sunsetTime.getSet()).withZoneSameInstant(ZoneId.of("UTC"));
        } catch (IllegalArgumentException e){
            return null;
        }
    }

    /**
     * This method computes the sunrise time given a date and GPS coordinates.
     * In case an IllegalArgumentException is thrown (due to invalid coordinates or date input), it returns null.
     * @param date Date of the day of the request. Accepted format: YYYY-MM-DD
     * @param gpsCoordinates Decimal location coordinates. Accepted format: (latitude : longitude)
     * @return The sunrise time as a ZonedDateTime object. It returns null in case an IllegalArgumentException is thrown when invoking
     *      * the getSunTimes() method, due to invalid coordinates or date input.
     */
    public ZonedDateTime computeSunrise(String date, String gpsCoordinates){
        SunTimes sunriseTime;
        try{
            sunriseTime = getSunTimes(date,gpsCoordinates);
            return Objects.requireNonNull(sunriseTime.getRise()).withZoneSameInstant(ZoneId.of("UTC"));
        } catch (IllegalArgumentException e){
            return null;
        }
    }

    /**
     * This method retrieves the sunrise and sunset times given a date and GPS coordinates, using an external
     * library named as commons-suncalc.
     * @param date Date of the day of the request. Accepted format: YYYY-MM-DD
     * @param gpsCoordinates Decimal location coordinates. Accepted format: (latitude : longitude)
     * Throws an IllegalArgumentException if coordinates are invalid as well as date parsing and conversion is not successful.
     * @return The sunrise or sunset time.
     */
    private SunTimes getSunTimes(String date, String gpsCoordinates){
        double[] coordinatesArray = convertCoordinates(gpsCoordinates);
        if(coordinatesArray.length == 0){
            throw new IllegalArgumentException("Invalid Coordinates");
        }
        double latitude = coordinatesArray[0];
        double longitude = coordinatesArray[1];
        try{
            LocalDate localDate = parseLocalDate(date);
            Date toComputeDate = convertToDate(localDate);
            return SunTimes.compute()
                    .on(toComputeDate)
                    .at(latitude, longitude)
                    .execute();
        } catch (NullPointerException | IllegalArgumentException | DateTimeException exception){
            throw new IllegalArgumentException("Operation Failed");
        }
    }

    /**
     * This method converts a string representation of GPS coordinates into a double array.
     * By splitting the string in 2 parts using ":" as a delimiter, it then attempts to parse the latitude and longitude.
     * @param gpsCoordinates Location coordinates
     * @return Double array with latitude and longitude coordinates. If any error occurs, returns an empty array.
     */
    private double[] convertCoordinates(String gpsCoordinates){
        double latitude;
        double longitude;

        try {
            String[] parts = gpsCoordinates.split(":");
            latitude = Double.parseDouble(parts[0]);
            longitude = Double.parseDouble(parts[1]);

        }catch (NumberFormatException | PatternSyntaxException | NullPointerException exception){
            return new double[0];
        }
        return new double[] {latitude,longitude};
    }

    /**
     * This method parses a string representation of a date into a LocalDate object.
     * @param dateAsString Date in string format
     * @return Date as LocalDate object.
     */
    private  LocalDate parseLocalDate(String dateAsString) {
        return LocalDate.parse(dateAsString);
    }

    /**
     * This method converts a LocalDate object into a Date object.
     * @param localDate Date as LocalDate object.
     * @return Date object.
     */
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
