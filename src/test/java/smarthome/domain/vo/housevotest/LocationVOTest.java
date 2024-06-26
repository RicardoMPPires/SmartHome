package smarthome.domain.vo.housevotest;

import smarthome.domain.vo.housevo.AddressVO;
import smarthome.domain.vo.housevo.GpsVO;
import smarthome.domain.vo.housevo.LocationVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocationVOTest {

    /**
     * This test verifies that the constructor of the LocationVO class successfully creates a new instance of the class
     * when called with valid arguments.
     * It first mocks objects of type AddressVO and GpsVO to represent the dependencies of the constructor.
     * Then,a new instance of LocationVO is created by passing the mocked AddressVO and GpsVO objects to the constructor.
     * Finally, the test asserts that the locationVO object is not null, verifying that the constructor was able to instantiate
     * a new `LocationVO` object successfully.
     */
    @Test
    void whenConstructorCalled_thenNewLocationInstantiated(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);

        //Act

        LocationVO locationVO = new LocationVO(addressVO,gpsVO);

        //Assert
        assertNotNull(locationVO);
    }

    /**
     * This test verifies that the constructor of the LocationVO class throws an IllegalArgumentException when provided
     * with a null AddressVO argument.
     * First, a null AddressVO object is created to represent an invalid argument.
     * A mock object of type GpsVO is created (argument can be anything in this case).
     * The expected exception message ("Invalid parameter.") is defined for comparison.
     * Then, an assertThrows statement is used to capture the exception thrown by the constructor when called with
     * the null `addressVO` and the mocked `gpsVO`. The captured exception is stored in the `exception` variable.
     * The actual exception message is retrieved using the getMessage method and stored in the result variable.
     * Finally, the test asserts that the captured exception is indeed an IllegalArgumentException using assertThrows.
     * The test then compares the actual exception message (result) with the expected exception message (expected) using
     * `assertEquals`. This verifies that the constructor threw the correct exception with the correct message.
     */
    @Test
    void whenAddressIsNull_thenThrowException(){
        //Arrange
        GpsVO gpsVO = mock(GpsVO.class);
        String expected = "Invalid parameter.";

        //Act

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new LocationVO(null,gpsVO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the constructor of the LocationVO class throws an IllegalArgumentException
     * when provided with a null GpsVO argument.
     * First, a mock object of type AddressVO is created (argument can be anything in this case).
     * A null GpsVO object is created to represent an invalid argument.
     * The expected exception message ("Invalid parameter.") is defined for comparison.
     * Then, an assertThrows statement is used to capture the exception thrown by the constructor when called with
     * the mocked `addressVO` and the null `gpsVO`. The captured exception is stored in the `exception` variable.
     * The actual exception message is retrieved using the getMessage method and stored in the result variable.
     * Finally, the test asserts that the captured exception is indeed an IllegalArgumentException using assertThrows.
     * The test then compares the actual exception message (result) with the expected exception message (expected)
     * using `assertEquals`. This verifies that the constructor threw the correct exception.
     */
    @Test
    void whenGpsIsNull_thenThrowException(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        String expected = "Invalid parameter.";

        //Act

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new LocationVO(addressVO,null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the getDoor() method of the LocationVO class correctly retrieves and returns
     * the door number information from the associated AddressVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked addressVO is configured to return "1904" when its getDoor() method is called,
     * simulating a specific address with the door number "1904".
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected door number ("1904") is defined for comparison.
     * Then, the getDoor() method of the locationVO object is called to retrieve the door number.
     * The returned door number is stored in the result variable.
     * Finally, the test asserts that the result (the actual door number returned by locationVO.getDoor())
     * is equal to the `expected` door number ("1904"). This verifies that the `getDoor()` method successfully
     * delegates the call to the `AddressVO` object and returns the correct door number.
     */
    @Test
    void whenGetDoorCalled_thenReturnDoor(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getDoor()).thenReturn("1904");
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        String expected = "1904";

        //Act
        String result = locationVO.getDoor();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * This test verifies that the getStreet() method of the LocationVO class correctly retrieves and returns
     * the street information from the associated AddressVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked addressVO is configured to return "Praça da avenida da rua" when its getStreet() method is called,
     * simulating a specific address with that street name.
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected street name ("Praça da avenida da rua") is defined for comparison.
     * Then the getStreet() method of the locationVO object is called to retrieve the street name.
     * The returned street name is stored in the result variable.
     * Finally, the test asserts that the result (the actual street name returned by locationVO.getStreet())
     * is equal to the `expected` street name ("Praça da avenida da rua"). This verifies that the `getStreet()` method successfully
     * delegates the call to the `AddressVO` object and returns the correct street information.
     */
    @Test
    void whenGetStreetCalled_thenReturnStreet(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getStreet()).thenReturn("Praça da avenida da rua");
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        String expected = "Praça da avenida da rua";

        //Act
        String result = locationVO.getStreet();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the getCity() method of the LocationVO class correctly retrieves and returns
     * the city information from the associated AddressVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked addressVO is configured to return "Polis" when its getCity() method is called,
     * simulating a specific address in the city "Polis".
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected city name ("Polis") is defined for comparison.
     * Then, the getCity() method of the locationVO object is called to retrieve the city name.
     * The returned city name is stored in the result variable.
     * Finally, the test asserts that the result (the actual city name returned by locationVO.getCity())
     * is equal to the `expected` city name ("Polis"). This verifies that the `getCity()` method successfully
     * delegates the call to the `AddressVO` object and returns the correct city information.
     */
    @Test
    void whenGetCityCalled_thenReturnCity(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getCity()).thenReturn("Polis");
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        String expected = "Polis";

        //Act
        String result = locationVO.getCity();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the getCountry() method of the LocationVO class correctly retrieves and returns
     * the country information from the associated AddressVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked addressVO is configured to return "Haiti" when its getCountry() method is called,
     * simulating a specific address located in Haiti.
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected country name ("Haiti") is defined for comparison.
     * Then, the getCountry() method of the locationVO object is called to retrieve the country name.
     * The returned country name is stored in the result variable.
     * Finally, the test asserts that the result (the actual country name returned by locationVO.getCountry())
     * is equal to the `expected` country name ("Haiti"). This verifies that the `getCountry()` method successfully
     * delegates the call to the `AddressVO` object and returns the correct country information.
     */
    @Test
    void whenGetCountryCalled_thenReturnCountry(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getCountry()).thenReturn("Haiti");
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        String expected = "Haiti";

        //Act
        String result = locationVO.getCountry();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the getPostalCode() method of the LocationVO class correctly retrieves and returns
     * the postal code information from the associated AddressVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked addressVO is configured to return "1848-212" when its getPostalCode() method is called,
     * simulating a specific address with that postal code.
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected postal code ("1848-212") is defined for comparison.
     * Then, the getPostalCode() method of the locationVO object is called to retrieve the postal code.
     * The returned postal code is stored in the result variable.
     * Finally, the test asserts that the result (the actual postal code returned by locationVO.getPostalCode())
     * is equal to the `expected` postal code ("1848-212"). This verifies that the `getPostalCode()` method successfully
     * delegates the call to the `AddressVO` object and returns the correct postal code information.
     */
    @Test
    void whenGetPostalCodeCalled_thenReturnPostalCode(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getPostalCode()).thenReturn("1848-212");
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        String expected = "1848-212";

        //Act
        String result = locationVO.getPostalCode();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the getLatitude() method of the LocationVO class correctly retrieves and returns
     * the latitude information from the associated GpsVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked gpsVO is configured to return 34.5 when its getLatitude() method is called,
     * simulating a specific location with that latitude.
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected latitude value (34.5) is defined for comparison.
     * Then, the getLatitude() method of the locationVO object is called to retrieve the latitude.
     * The returned latitude value is stored in the result variable.
     * Finally, the test asserts that the result (the actual latitude returned by locationVO.getLatitude())
     * is equal to the `expected` latitude value (34.5). This verifies that the `getLatitude()` method successfully
     * delegates the call to the `GpsVO` object and returns the correct latitude information.
     */
    @Test
    void whenGetLatitudeCalled_thenReturnLatitude(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(gpsVO.getLatitude()).thenReturn( 34.5);
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        double expected = 34.5;

        //Act
        double result = locationVO.getLatitude();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the getLongitude() method of the LocationVO class correctly retrieves and returns
     * the longitude information from the associated GpsVO object.
     * First, mock objects of type AddressVO and GpsVO are created.
     * The mocked gpsVO is configured to return 134.5 when its getLongitude() method is called,
     * simulating a specific location with that longitude.
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected longitude value (134.5) is defined for comparison.
     * Then, the getLongitude() method of the locationVO object is called to retrieve the longitude.
     * The returned longitude value is stored in the result variable.
     * Finally, the test asserts that the result (the actual longitude returned by locationVO.getLongitude())
     * is equal to the `expected` longitude value (134.5). This verifies that the `getLongitude()` method successfully
     * delegates the call to the `GpsVO` object and returns the correct longitude information.
     */
    @Test
    void whenGetLongitudeCalled_thenReturnLongitude(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(gpsVO.getLongitude()).thenReturn( 134.5);
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        double expected = 134.5;

        //Act
        double result = locationVO.getLongitude();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * This test verifies that the toString() method of the LocationVO class correctly formats and returns
     * a comprehensive string representation of the location, including both address and GPS information.
     * First, mock objects of type AddressVO and GpsVO are created with specific values for address components and GPS coordinates.
     * A new LocationVO object is created using the mocked addressVO and gpsVO.
     * The expected string representation of the location is defined for comparison.
     * Then, the toString() method of the locationVO object is called to generate the string representation.
     * The returned string is stored in the result variable.
     * Finally, the test asserts that the result (the actual string returned by locationVO.toString())
     * is equal to the `expected` string representation. This verifies that the `toString()` method correctly combines
     * address and GPS information into a readable format.
     */
    @Test
    void whenGetLocationAsString_returnLocation(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        when(addressVO.getDoor()).thenReturn("1904");
        when(addressVO.getStreet()).thenReturn("Praça da avenida da rua");
        when(addressVO.getCity()).thenReturn("Polis");
        when(addressVO.getCountry()).thenReturn("Haiti");
        when(addressVO.getPostalCode()).thenReturn("1848-212");
        GpsVO gpsVO = mock(GpsVO.class);
        when(gpsVO.getLatitude()).thenReturn( 34.5);
        when(gpsVO.getLongitude()).thenReturn( 134.5);
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        String expected = "Location: Address - Praça da avenida da rua, 1904. 1848-212, Polis, Haiti. GPS - latitude: 34.5, longitude: 134.5";

        //Act
        String result = locationVO.toString();

        //Assert
        assertEquals(expected,result);
    }
}
