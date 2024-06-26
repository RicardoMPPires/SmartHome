package smarthome.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.HouseFactoryImpl;
import smarthome.domain.vo.housevo.*;
import smarthome.mapper.dto.LocationDTO;
import smarthome.persistence.HouseRepository;
import smarthome.service.HouseService;
import smarthome.service.HouseServiceImpl;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the ConfigureLocationCTRL class
 */

class ConfigureLocationCTRLTest {

    private HouseRepository houseRepositoryDouble;
    private HouseService houseService;

    /**
     * This method initializes a house repository double, a local house factory variable.
     * It also initializes a house service object with the previously created objects.
     */

    @BeforeEach
    void setUp() {
        houseRepositoryDouble = mock(HouseRepository.class);
        HouseFactory houseFactory = new HouseFactoryImpl();
        houseService = new HouseServiceImpl(houseRepositoryDouble, houseFactory);
    }

    /**
     * Method that tests the creation of a ConfigureLocationCTRL object
     * The test checks if the object creation throws an Illegal Argument Exception due to a null houseService.
     */

    @Test
    void givenNullHouseService_whenCreateConfigureLocationCTRL_thenThrowIllegalArgumentException() {
//        Arrange
        houseService = null;
//        Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ConfigureLocationCTRL(houseService));
    }

    /**
     * Method that tests the update of the location of the house
     * The test checks that if the location data transfer object is null then updateLocation returns
     * false.
     */

    @Test
    void givenNullLocationDTO_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(null);
//        Assert
        assertFalse(result);
    }

    /**
     * Test method to verify that when a valid LocationDTO is provided to update a location,
     * the updateLocation method in the ConfigureLocationCTRL returns true,
     * indicating a successful update of the house's location.
     * This test also verifies that the updated location values match the expected values.
     * In this test case it's used a double of houseRepository, and it's behavior is conditioned when:
     * 1. getFirstHouse() is invoked, it will be returned an optional of the created house instance;
     * 2. update(house) is invoked, true will be returned;
     */
    @Test
    void givenValidLocationDTO_whenUpdateLocation_thenReturnTrue() {
//        Arrange
        String doorNumber = "2";
        String changedDoorNumber = "345";
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";

        DoorVO door = new DoorVO(doorNumber);
        StreetVO street = new StreetVO(streetName);
        CityVO city = new CityVO(cityName);
        CountryVO country = new CountryVO(countryName);
        PostalCodeVO postalCode = new PostalCodeVO(postalCodeName);

        AddressVO addressVO = new AddressVO(door, street, city, country, postalCode);

        double expectedLatitudeValue = 77.777;
        double expectedLongitudeValue = -89.999;

        LatitudeVO latitude = new LatitudeVO(expectedLatitudeValue);
        LongitudeVO longitude = new LongitudeVO(expectedLongitudeValue);
        GpsVO gpsVO = new GpsVO(latitude, longitude);

        LocationVO locationVO = new LocationVO(addressVO, gpsVO);
        House house = new House(locationVO);

        LocationDTO locationDTO1 = new LocationDTO(changedDoorNumber, streetName, cityName, countryName, postalCodeName, expectedLatitudeValue, expectedLongitudeValue);

        //Conditioning house repository double to return the created house;
        when(houseRepositoryDouble.getFirstHouse()).thenReturn(Optional.of(house));

        //Conditioning house repository double to return true when update operation is invoked;
        when(houseRepositoryDouble.update(house)).thenReturn(true);

        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO1);

        LocationVO resultLocation = house.getLocation();
        String resultDoorNumber = resultLocation.getDoor();
        String resultStreetName = resultLocation.getStreet();
        String resultCountry = resultLocation.getCountry();
        String resultCity = resultLocation.getCity();
        String resultPostalCode = resultLocation.getPostalCode();
        double resultLatitude = resultLocation.getLatitude();
        double resultLongitude = resultLocation.getLongitude();

//        Assert
        assertTrue(result);
        assertEquals(changedDoorNumber, resultDoorNumber);
        assertEquals(streetName, resultStreetName);
        assertEquals(countryName, resultCountry);
        assertEquals(cityName, resultCity);
        assertEquals(postalCodeName, resultPostalCode);
        assertEquals(expectedLatitudeValue, resultLatitude);
        assertEquals(expectedLongitudeValue, resultLongitude);
    }

    /**
     * Test method to verify that when updateLocation is invoked and there is not a house yet registered,
     * the updateLocation method in the ConfigureLocationCTRL returns false.
     * In this test case it's used a double of houseRepository, and it's behavior is conditioned when:
     * 1. getFirstHouse() is invoked, it will be returned an empty House optional;
     */

    @Test
    void updateLocation_WhenHouseNotFound_ShouldReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";

        double expectedLatitudeValue = 77.777;
        double expectedLongitudeValue = -89.999;

        LocationDTO locationDTO1 = new LocationDTO(doorNumber, streetName, cityName, countryName, postalCodeName, expectedLatitudeValue, expectedLongitudeValue);
        when(houseRepositoryDouble.getFirstHouse()).thenReturn(Optional.empty());

        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO1);

//        Assert
        assertFalse(result);
    }

    /**
     * Test method to verify that when updateLocation is invoked and update method in house repository fails,
     * the updateLocation method in the ConfigureLocationCTRL returns false.
     * In this test case it's used a double of houseRepository, and it's behavior is conditioned when:
     * 1. getFirstHouse() is invoked, it will be returned an optional of the created house instance;
     * 2. update(house) is invoked, false will be returned, simulating updating failure from the repository;
     */

    @Test
    void updateLocation_WhenUpdateOperationFailsInRepository_ShouldReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String changedDoorNumber = "345";
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";

        DoorVO door = new DoorVO(doorNumber);
        StreetVO street = new StreetVO(streetName);
        CityVO city = new CityVO(cityName);
        CountryVO country = new CountryVO(countryName);
        PostalCodeVO postalCode = new PostalCodeVO(postalCodeName);

        AddressVO addressVO = new AddressVO(door, street, city, country, postalCode);

        double expectedLatitudeValue = 77.777;
        double expectedLongitudeValue = -89.999;

        LatitudeVO latitude = new LatitudeVO(expectedLatitudeValue);
        LongitudeVO longitude = new LongitudeVO(expectedLongitudeValue);
        GpsVO gpsVO = new GpsVO(latitude, longitude);

        LocationVO locationVO = new LocationVO(addressVO, gpsVO);
        House house = new House(locationVO);

        LocationDTO locationDTO1 = new LocationDTO(changedDoorNumber, streetName, cityName, countryName, postalCodeName, expectedLatitudeValue, expectedLongitudeValue);

        //Conditioning house repository double to return the created house;
        when(houseRepositoryDouble.getFirstHouse()).thenReturn(Optional.of(house));

        //Conditioning house repository double to return false when update operation is invoked;
        when(houseRepositoryDouble.update(house)).thenReturn(false);

        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO1);

//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has a null door number and returns false
     */

    @Test
    void givenNullDoorNumber_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";
        double latitudeValue = 77.777;
        double longitudeValue = -89.999;
        LocationDTO locationDTO = new LocationDTO(null, streetName, cityName, countryName, postalCodeName, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has a null street name and returns false
     */

    @Test
    void givenNullStreetName_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";
        double latitudeValue = 77.777;
        double longitudeValue = -89.999;
        LocationDTO locationDTO = new LocationDTO(doorNumber, null, cityName, countryName, postalCodeName, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has a null city name and returns false
     */

    @Test
    void givenNullCityName_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String streetName = "Street2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";
        double latitudeValue = 77.777;
        double longitudeValue = -89.999;
        LocationDTO locationDTO = new LocationDTO(doorNumber, streetName, null, countryName, postalCodeName, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has a null country name and returns false
     */

    @Test
    void givenNullCountryName_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String streetName = "Street2";
        String cityName = "City2";
        String postalCodeName = "FR-1234-567";
        double latitudeValue = 77.777;
        double longitudeValue = -89.999;
        LocationDTO locationDTO = new LocationDTO(doorNumber, streetName, cityName, null, postalCodeName, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has a null postal code name and returns false
     */

    @Test
    void givenNullPostalCodeName_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        double latitudeValue = 77.777;
        double longitudeValue = -89.999;
        LocationDTO locationDTO = new LocationDTO(doorNumber, streetName, cityName, countryName, null, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has an invalid latitude value and returns false
     */

    @Test
    void givenInvalidLatitudeValue_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";
        double latitudeValue = -100.54321;
        double longitudeValue = -89.999;
        LocationDTO locationDTO = new LocationDTO(doorNumber, streetName, cityName, countryName, postalCodeName, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }

    /**
     * Method that tests the update of the location of the house when one of the values passed to the LocationDTO is invalid
     * The test checks if the location data transfer object has an invalid longitude value and returns false
     */

    @Test
    void givenInvalidLongitudeValue_whenUpdateLocation_thenReturnFalse() {
//        Arrange
        String doorNumber = "2";
        String streetName = "Street2";
        String cityName = "City2";
        String countryName = "France";
        String postalCodeName = "FR-1234-567";
        double latitudeValue = 77.777;
        double longitudeValue = -200.54321;
        LocationDTO locationDTO = new LocationDTO(doorNumber, streetName, cityName, countryName, postalCodeName, latitudeValue, longitudeValue);
        ConfigureLocationCTRL configureLocationCTRL = new ConfigureLocationCTRL(houseService);
//        Act
        boolean result = configureLocationCTRL.updateLocation(locationDTO);
//        Assert
        assertFalse(result);
    }
}
