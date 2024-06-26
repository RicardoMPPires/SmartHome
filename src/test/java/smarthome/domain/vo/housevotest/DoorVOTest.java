package smarthome.domain.vo.housevotest;

import smarthome.domain.vo.housevo.DoorVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoorVOTest {

    /**
     * This test validates that the constructor throws an exception when given an invalid parameter.
     */
    @Test
    void givenNullParameter_returnsException(){
        // Arrange
        String expected = "Invalid Parameters";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DoorVO(null));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test validates that the constructor throws an exception when given an invalid parameter.
     */
    @Test
    void givenEmptyParameter_returnsException(){
        // Arrange
        String door = " ";
        String expected = "Invalid Parameters";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DoorVO(door));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test validates that the object returns its value correctly.
     */
    @Test
    void callingGetDoor_returnsDoorString() {
        // Arrange
        String door = "4A";
        String expected = "4A";
        DoorVO doorVO = new DoorVO(door);
        // Act
        String result = doorVO.getValue();
        // Assert
        assertEquals(expected,result);
    }
}
