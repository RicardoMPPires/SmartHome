package smarthome.mapper;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.*;
import smarthome.mapper.dto.HouseDTO;
import smarthome.mapper.dto.LocationDTO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static smarthome.mapper.HouseMapper.domainToDto;
import static smarthome.mapper.HouseMapper.dtoToDomain;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HouseMapperTest {

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct door information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectDoorInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "Portugal", "PT-4444-222", 1.0, 1.0);
        String expected = "25A";

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getDoor()).thenReturn("25A"))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            String result = locationVO.getDoor();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedLocationVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * This test checks if when trying to convert a null LocationDTO object to a LocationVO object
     * an IllegalArgumentException is thrown
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldThrowIllegalArgumentException() {
        //Arrange
        String expected = "LocationDTO is null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> dtoToDomain(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct street information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectStreetInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "USA", "US-4444-222", 1.0, 1.0);
        String expected = "Rua da Alegria";

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getStreet()).thenReturn("Rua da Alegria"))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            String result = locationVO.getStreet();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

        //Assert
        assertEquals(1, listOfMockedLocationVO.size());
        assertEquals(expected, result);
        }
    }

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct city information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectCityInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "Spain", "ES-4444-222", 1.0, 1.0);
        String expected = "Porto";

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getCity()).thenReturn("Porto"))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            String result = locationVO.getCity();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedLocationVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct country information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectCountryInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "France", "FR-4444-222", 1.0, 1.0);
        String expected = "Portugal";

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getCountry()).thenReturn("Portugal"))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            String result = locationVO.getCountry();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedLocationVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct postal code information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectPostalCodeInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "Portugal", "PT-4444-222", 1.0, 1.0);
        String expected = "4444-222";

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getPostalCode()).thenReturn("4444-222"))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            String result = locationVO.getPostalCode();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedLocationVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct latitude information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectLatitudeInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "Portugal", "PT-4444-222", 1.0, 5.0);
        double expected = 1.0;

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getLatitude()).thenReturn(1.0))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            double result = locationVO.getLatitude();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedLocationVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * This test is used to check if after using the dtoToDomain method to get a domain object
     * it is returning the correct longitude information
     */
    @Test
    void whenDtoToDomainIsCalled_ShouldGetCorrectLongitudeInformation() {
        //Arrange
        LocationDTO locationDTO = new LocationDTO("25A", "Rua da Alegria", "Porto", "Portugal", "PT-4444-222", 1.0, 5.0);
        double expected = 5.0;

        //Act
        try (MockedConstruction<LocationVO> mockedConstruction = mockConstruction(LocationVO.class, (mock, context) ->
                when(mock.getLongitude()).thenReturn(5.0))) {
            LocationVO locationVO = dtoToDomain(locationDTO);

            double result = locationVO.getLongitude();
            List<LocationVO> listOfMockedLocationVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedLocationVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * This test is used to check that given a House Object, it is correctly converted to a HouseDTO.
     * A mocked construction of the HouseDTO is built.
     * All the resultant HouseDTO attributes are compared to the actual House attributes.
     */
    @Test
    void whenDomainToDtoIsInvoked_ThenShouldReturnHouseDTOWithAllDomainHouseAttributes(){
        //Arrange
        House house = new House(
                new LocationVO(
                        new AddressVO(
                                new DoorVO("default door"),
                                new StreetVO("default street"),
                                new CityVO("default city"),
                                new CountryVO("Portugal"),
                                new PostalCodeVO("PT-1234-567")),
                        new GpsVO(
                                new LatitudeVO(0),
                                new LongitudeVO(0))
                )
        );

        String houseID = house.getId().getID();

        try (MockedConstruction<HouseDTO> mockedConstruction = mockConstruction(HouseDTO.class, (mock, context) ->
                {when(mock.getHouseID()).thenReturn(houseID);
                    when(mock.getDoor()).thenReturn("default door");
                    when(mock.getStreet()).thenReturn("default street");
                    when(mock.getCity()).thenReturn("default city");
                    when(mock.getCountry()).thenReturn("Portugal");
                    when(mock.getPostalCode()).thenReturn("PT-1234-567");
                    when(mock.getLatitude()).thenReturn(0D);
                    when(mock.getLongitude()).thenReturn(0D);

                }
                )){
                    //Act
                    HouseDTO houseDoubleDTO = domainToDto(house);

                    //Assert
                    List<HouseDTO> listOfMockedHouseDTO = mockedConstruction.constructed();
                    assertEquals(1, listOfMockedHouseDTO.size());

                    assertEquals(houseID, houseDoubleDTO.getHouseID());
                    assertEquals(house.getLocation().getDoor(), houseDoubleDTO.getDoor());
                    assertEquals(house.getLocation().getStreet(), houseDoubleDTO.getStreet());
                    assertEquals(house.getLocation().getCity(), houseDoubleDTO.getCity());
                    assertEquals(house.getLocation().getCountry(), houseDoubleDTO.getCountry());
                    assertEquals(house.getLocation().getPostalCode(), houseDoubleDTO.getPostalCode());
                    assertEquals(house.getLocation().getLatitude(), houseDoubleDTO.getLatitude());
                    assertEquals(house.getLocation().getLongitude(), houseDoubleDTO.getLongitude());
        }

    }
}