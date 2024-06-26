package smarthome.domain.vo.housevotest;

import smarthome.domain.vo.housevo.CountryVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryVOTest {

    @Test
    void testValidParameter_returnsCountryVO() {
        String country = "Portugal";
        CountryVO countryVO = new CountryVO(country);
        assertNotNull(countryVO);
    }

    @Test
    void testNullParameter_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CountryVO(null));
    }

    @Test
    void testEmptyParameter_throwsIllegalArgumentException() {
        String country = "";
        assertThrows(IllegalArgumentException.class, () -> new CountryVO(country));
    }

    /**
     * This test checks if when trying to create a CountryVO object with an invalid country
     * an IllegalArgumentException is thrown
     */

    @Test
    void testInvalidCountry_throwsIllegalArgumentException() {
        String country = "Burkina Faso";
        assertThrows(IllegalArgumentException.class, () -> new CountryVO(country));
    }

    @Test
    void testGetCountry_returnsCountry() {
        String country = "USA";
        CountryVO countryVO = new CountryVO(country);
        assertEquals(country, countryVO.getValue());
    }
}