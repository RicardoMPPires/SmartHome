package smarthome.domain.sensor.externalservices;

import java.time.ZonedDateTime;

public interface SunTimeServices {
    ZonedDateTime computeSunset(String date, String gpsCoordinates);
    ZonedDateTime computeSunrise(String date, String gpsCoordinates);

}
