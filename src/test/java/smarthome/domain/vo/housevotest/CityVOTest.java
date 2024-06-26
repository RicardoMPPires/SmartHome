package smarthome.domain.vo.housevotest;

import smarthome.domain.vo.housevo.CityVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityVOTest {

    @Test
    void givenValidCity_whenCreatingCityVO_thenCreateCityVO() {
//         Arrange
        String city = "Test";
//        Act
        CityVO cityVO = new CityVO(city);
        String result = cityVO.getValue();
//        Assert
        assertEquals(city, result);
    }

    @Test
    void givenNullCity_whenCreatingCityVO_thenThrowException() {
//         Arrange
        String expected = "Invalid parameters.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new CityVO(null));
        String result = exception.getMessage();
//        Assert
        assertEquals(expected, result);
    }

    @Test
    void givenBlankCity_whenCreatingCityVO_thenThrowException() {
//         Arrange
        String city = " ";
        String expected = "Invalid parameters.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new CityVO(city));
        String result = exception.getMessage();
//        Assert
        assertEquals(expected, result);
    }
}
