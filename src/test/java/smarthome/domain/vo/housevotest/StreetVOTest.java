package smarthome.domain.vo.housevotest;

import smarthome.domain.vo.housevo.StreetVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StreetVOTest {

    @Test
    void givenValidStreet_whenCreatingStreetVO_thenCreateStreetVO() {
//         Arrange
        String street = "Test";
//        Act
        StreetVO streetVO = new StreetVO(street);
        String result = streetVO.getValue();
//        Assert
        assertEquals(street, result);
    }

    @Test
    void givenNullStreet_whenCreatingStreetVO_thenThrowException() {
//         Arrange
        String expected = "Invalid parameters.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new StreetVO(null));
        String result = exception.getMessage();
//        Assert
        assertEquals(expected, result);
    }

    @Test
    void givenBlankStreet_whenCreatingStreetVO_thenThrowException() {
//         Arrange
        String street = " ";
        String expected = "Invalid parameters.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new StreetVO(street));
        String result = exception.getMessage();
//        Assert
        assertEquals(expected, result);
    }
}
