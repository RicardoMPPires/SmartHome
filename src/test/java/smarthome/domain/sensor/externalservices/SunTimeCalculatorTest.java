package smarthome.domain.sensor.externalservices;


import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SunTimeCalculatorTest {

    /**
     * This test ensures that, given valid date and gps, computeSunset returns a Zoned Date Time object, which
     * is then accessible by using toString()
     */
    @Test
    void givenValidDateAndGPs_computeSunsetSuccessfullyCreatesZonedDateTimeObjectAccessibleByToString()  {
        //Arrange
        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();
        String date = "2024-03-06";
        String coordinates = "41.1579 : -8.6291";
        String expected = "2024-03-06T18:31:47Z[UTC]";
        //Act
        ZonedDateTime resultZoneDateTime = sunTimeCalculator.computeSunset(date, coordinates);
        String result = resultZoneDateTime.toString();
        //Assert
        assertEquals(expected,result);
    }

    /**
     * Tests the behavior of the computeSunset method in the SunTimeCalculator class when provided with invalid dates.
     * <p>
     * This test verifies that the computeSunset method returns null when provided with invalid dates. Four different scenarios
     * are tested:
     * <ul>
     *     <li>Null date.</li>
     *     <li>Empty date.</li>
     *     <li>Date in an invalid format (YYYY/MM/DD).</li>
     *     <li>Date with invalid month (YYYY-DD-MM).</li>
     * </ul>
     * For each scenario, the test checks that the computeSunset method returns null.
     * </p>
     */
    @Test
    void givenInvalidDate_computeSunsetReturnsNull()  {
        //Arrange
        String date2 = " ";
        String date3 = "2024/13/06";
        String date4 = "2024-15-06";

        String coordinates = "41.1579 : -8.6291";
        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();

        //Act
        ZonedDateTime result1 = sunTimeCalculator.computeSunset(null, coordinates);
        ZonedDateTime result2 = sunTimeCalculator.computeSunset(date2, coordinates);
        ZonedDateTime result3 = sunTimeCalculator.computeSunset(date3, coordinates);
        ZonedDateTime result4 = sunTimeCalculator.computeSunset(date4, coordinates);

        //Assert
        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);
    }

    /**
     * Tests the behavior of the computeSunset method in the SunTimeCalculator class when provided with invalid GPS coordinates.
     * <p>
     * This test verifies that the computeSunset method returns null when provided with invalid GPS coordinates. Four different scenarios
     * are tested:
     * <ul>
     *     <li>Empty coordinates string.</li>
     *     <li>Coordinates in an invalid format (latitude/longitude separated by a slash).</li>
     *     <li>Latitude out of range (latitude > 90).</li>
     *     <li>Null coordinates string.</li>
     * </ul>
     * For each scenario, the test checks that the computeSunset method returns null.
     * </p>
     */
    @Test
    void givenInvalidGPS_computeSunsetReturnsNull()  {
        //Arrange
        String coordinates1 = " ";
        String coordinates2 = "41.1579/-8.6291";
        String coordinates3 = "59000.1579 : -8.6291";

        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();
        String date = "2024-03-06";

        //Act
        ZonedDateTime result1 = sunTimeCalculator.computeSunset(date,coordinates1);
        ZonedDateTime result2 = sunTimeCalculator.computeSunset(date,coordinates2);
        ZonedDateTime result3 = sunTimeCalculator.computeSunset(date,coordinates3);
        ZonedDateTime result4 = sunTimeCalculator.computeSunset(date,null);

        //Assert
        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);
    }


    /**
     * This test ensures that, given valid date and gps, computeSunrise returns a Zoned Date Time object, which
     * is then accessible by using toString()
     */
    @Test
    void givenValidDateAndGPs_ComputeSunriseSuccessfullyCreatesZonedDateTimeObjectAccessibleByToString()  {
        //Arrange
        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();
        String date = "2024-03-06";
        String coordinates = "41.1579 : -8.6291";
        String expected = "2024-03-06T07:00:19Z[UTC]";
        //Act
        ZonedDateTime resultZoneDateTime = sunTimeCalculator.computeSunrise(date, coordinates);
        String result = resultZoneDateTime.toString();
        //Assert
        assertEquals(expected,result);
    }

    /**
     * Tests the behavior of the computeSunrise method in the SunTimeCalculator class when provided with invalid dates.
     * <p>
     * This test verifies that the computeSunrise method returns null when provided with invalid dates. Four different scenarios
     * are tested:
     * <ul>
     *     <li>Empty date string.</li>
     *     <li>Date in an invalid format (year/month/day).</li>
     *     <li>Date with an invalid day (e.g., day 15 in a month with 30 days).</li>
     *     <li>Null date string.</li>
     * </ul>
     * For each scenario, the test checks that the computeSunrise method returns null.
     * </p>
     */
    @Test
    void givenInvalidDate_computeSunriseReturnsNull()  {
        //Arrange
        String date1 = " ";
        String date2 = "2024/13/06";
        String date3 = "2024-15-06";

        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();
        String coordinates = "41.1579 : -8.6291";

        //Act
        ZonedDateTime result1 = sunTimeCalculator.computeSunrise(date1,coordinates);
        ZonedDateTime result2 = sunTimeCalculator.computeSunrise(date2,coordinates);
        ZonedDateTime result3 = sunTimeCalculator.computeSunrise(date3,coordinates);
        ZonedDateTime result4 = sunTimeCalculator.computeSunrise(null,coordinates);

        //Assert
        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);
    }

    /**
     * Tests the behavior of the computeSunrise method in the SunTimeCalculator class when provided with invalid GPS coordinates.
     * <p>
     * This test verifies that the computeSunrise method returns null when provided with invalid GPS coordinates. Four different scenarios
     * are tested:
     * <ul>
     *     <li>Empty string as coordinates.</li>
     *     <li>Null coordinates.</li>
     *     <li>Coordinates in an invalid format (latitude/longitude separated by "/").</li>
     *     <li>Coordinates with unrealistic values (e.g., latitude greater than 90).</li>
     * </ul>
     * For each scenario, the test checks that the computeSunrise method returns null.
     * </p>
     */
    @Test
    void givenInvalidGPS_computeSunriseReturnsNull()  {
        //Arrange
        String coordinates1 = " ";
        String coordinates2 = "41.1579/-8.6291";
        String coordinates3 = "59000.1579 : -8.6291";

        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();
        String date = "2024-03-06";
        //Act
        ZonedDateTime result1 = sunTimeCalculator.computeSunrise(date, coordinates1);
        ZonedDateTime result2 = sunTimeCalculator.computeSunrise(date, null);
        ZonedDateTime result3 = sunTimeCalculator.computeSunrise(date, coordinates2);
        ZonedDateTime result4 = sunTimeCalculator.computeSunrise(date, coordinates3);

        //Assert
        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
        assertNull(result4);
    }
}
