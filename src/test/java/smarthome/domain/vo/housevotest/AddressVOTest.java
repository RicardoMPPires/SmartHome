package smarthome.domain.vo.housevotest;

import smarthome.domain.vo.housevo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressVOTest {

    private DoorVO doorVODouble;
    private StreetVO streetVODouble;
    private CityVO cityVODouble;
    private CountryVO countryVODouble;
    private PostalCodeVO postalCodeVODouble;

    @BeforeEach
    void setup() {
        doorVODouble = mock(DoorVO.class);
        streetVODouble = mock(StreetVO.class);
        cityVODouble = mock(CityVO.class);
        countryVODouble = mock(CountryVO.class);
        postalCodeVODouble = mock(PostalCodeVO.class);
    }

    @Test
    void testValidParameter_returnsAddressVO() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("Spain");
        PostalCodeVO postalCodeVO = new PostalCodeVO("ES-1234-567");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        assertNotNull(addressVO);
    }
    @Test
    void testNullParameter_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AddressVO(null, null, null, null, null));
    }

    /**
     * This test checks if when trying to create an AddressVO object with an invalid country and postal code combination
     * an IllegalArgumentException is thrown
     */

    @Test
    void testInvalidCountryPostalCodeCombo_throwsIllegalArgumentException() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("USA");
        PostalCodeVO postalCodeVO = new PostalCodeVO("PT-1234-567");
        assertThrows(IllegalArgumentException.class, () -> new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO));
    }

    @Test
    void testGetDoor_returnsDoor() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("USA");
        PostalCodeVO postalCodeVO = new PostalCodeVO("US-1234-567");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        assertEquals("1", addressVO.getDoor());
    }

    @Test
    void testGetStreet_returnsStreet() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("France");
        PostalCodeVO postalCodeVO = new PostalCodeVO("FR-1234-567");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        assertEquals("Rua do Ouro", addressVO.getStreet());
    }

    @Test
    void testGetCity_returnsCity() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("Portugal");
        PostalCodeVO postalCodeVO = new PostalCodeVO("PT-1234-567");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        assertEquals("Porto", addressVO.getCity());
    }

    @Test
    void testGetCountry_returnsCountry() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("Portugal");
        PostalCodeVO postalCodeVO = new PostalCodeVO("PT-1234-567");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        assertEquals("Portugal", addressVO.getCountry());
    }

    @Test
    void testGetPostalCode_returnsPostalCode() {
        DoorVO doorVO = new DoorVO("1");
        StreetVO streetVO = new StreetVO("Rua do Ouro");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("Spain");
        PostalCodeVO postalCodeVO = new PostalCodeVO("ES-1234-567");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        assertEquals("ES-1234-567", addressVO.getPostalCode());
    }

    @Test
    void testValidArgs_returnsDoor_Isolation(){
        String door = "1";
        String street = "Rua do Ouro";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-1234-567";
        when(doorVODouble.getValue()).thenReturn(door);
        when(streetVODouble.getValue()).thenReturn(street);
        when(cityVODouble.getValue()).thenReturn(city);
        when(countryVODouble.getValue()).thenReturn(country);
        when(postalCodeVODouble.getValue()).thenReturn(postalCode);
        AddressVO addressVO = new AddressVO(doorVODouble, streetVODouble, cityVODouble, countryVODouble, postalCodeVODouble);
        assertEquals(door, addressVO.getDoor());
    }

    @Test
    void testValidArgs_returnsStreet_Isolation(){
        String door = "1";
        String street = "Rua do Ouro";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-1234-567";
        when(doorVODouble.getValue()).thenReturn(door);
        when(streetVODouble.getValue()).thenReturn(street);
        when(cityVODouble.getValue()).thenReturn(city);
        when(countryVODouble.getValue()).thenReturn(country);
        when(postalCodeVODouble.getValue()).thenReturn(postalCode);
        AddressVO addressVO = new AddressVO(doorVODouble, streetVODouble, cityVODouble, countryVODouble, postalCodeVODouble);
        assertEquals(street, addressVO.getStreet());
    }

    @Test
    void testValidArgs_returnsCity_Isolation(){
        String door = "1";
        String street = "Rua do Ouro";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-1234-567";
        when(doorVODouble.getValue()).thenReturn(door);
        when(streetVODouble.getValue()).thenReturn(street);
        when(cityVODouble.getValue()).thenReturn(city);
        when(countryVODouble.getValue()).thenReturn(country);
        when(postalCodeVODouble.getValue()).thenReturn(postalCode);
        AddressVO addressVO = new AddressVO(doorVODouble, streetVODouble, cityVODouble, countryVODouble, postalCodeVODouble);
        assertEquals(city, addressVO.getCity());
    }

    @Test
    void testValidArgs_returnsCountry_Isolation(){
        String door = "1";
        String street = "Rua do Ouro";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-1234-567";
        when(doorVODouble.getValue()).thenReturn(door);
        when(streetVODouble.getValue()).thenReturn(street);
        when(cityVODouble.getValue()).thenReturn(city);
        when(countryVODouble.getValue()).thenReturn(country);
        when(postalCodeVODouble.getValue()).thenReturn(postalCode);
        AddressVO addressVO = new AddressVO(doorVODouble, streetVODouble, cityVODouble, countryVODouble, postalCodeVODouble);
        assertEquals(country, addressVO.getCountry());
    }

    @Test
    void testValidArgs_returnsPostalCode_Isolation(){
        String door = "1";
        String street = "Rua do Ouro";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-1234-567";
        when(doorVODouble.getValue()).thenReturn(door);
        when(streetVODouble.getValue()).thenReturn(street);
        when(cityVODouble.getValue()).thenReturn(city);
        when(countryVODouble.getValue()).thenReturn(country);
        when(postalCodeVODouble.getValue()).thenReturn(postalCode);
        AddressVO addressVO = new AddressVO(doorVODouble, streetVODouble, cityVODouble, countryVODouble, postalCodeVODouble);
        assertEquals(postalCode, addressVO.getPostalCode());
    }
}