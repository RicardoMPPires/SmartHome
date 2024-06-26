package smarthome.domain.vo;

import smarthome.domain.vo.sensortype.UnitVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitVOTest {
    /**
     * Test if the UnitVO is created with valid parameters
     * Should return a UnitVO
     */
    @Test
    void testValidParameters_ShouldReturnUnitVO() {
        // Given
        String unit = "Celsius";
        // When
        UnitVO unitVO = new UnitVO(unit);
        // Then
        assertNotNull(unitVO);
    }

    /**
     * Test if the UnitVO is created with null unit
     * Should throw IllegalArgumentException
     */
    @Test
    void testNullUnit_ShouldThrowIllegalArgumentException() {
        // Given
        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> new UnitVO(null));
    }

    /**
     * Test if the UnitVO is created with empty unit
     * Should throw IllegalArgumentException
     */
    @Test
    void testEmptyUnit_ShouldThrowIllegalArgumentException() {
        // Given
        String unit = "";
        //When
        // Then
        assertThrows(IllegalArgumentException.class, () -> new UnitVO(unit));
    }

    @Test
    void givenValidUnit_ThenReturnUnit() {
        // Given
        String unit = "Celsius";
        // When
        UnitVO unitVO = new UnitVO(unit);
        // Then
        assertEquals(unit, unitVO.getValue());
    }
}