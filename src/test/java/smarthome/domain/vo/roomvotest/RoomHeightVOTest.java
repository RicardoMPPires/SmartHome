package smarthome.domain.vo.roomvotest;

import smarthome.domain.vo.roomvo.RoomHeightVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomHeightVOTest {

    /**
     * Test case to verify that the RoomHeightVO constructor throws an IllegalArgumentException
     * when given a negative height. The result exception description must also match the expected one.
     */
    @Test
    void shouldCreateAValidVO_height() {
        //Arrange
        double height = 1.5;
        RoomHeightVO vo_height = new RoomHeightVO(height);

        //Act
        double result = vo_height.getValue();

        //Assert
        assertEquals(height, result);
    }

    /**
     * Test case to verify that the RoomHeightVO constructor throws an IllegalArgumentException
     * when given a negative height. The result exception description must also match the expected one.
     */
    @Test
    void shouldThrowExceptionVO_heightWithInvalidHeight() {
        //Arrange
        double height = -1.7;
        String expectedMessage = "Invalid height value";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomHeightVO(height));
        String actualMessage = exception.getMessage();

        //Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test case to verify that the RoomHeightVO constructor throws an IllegalArgumentException
     * when given a zero height. The result exception description must also match the expected one.
     */
    @Test
    void shouldCreateValidVO_heightWithZeroHeight() {
        //Arrange
        double height = 0.0;
        RoomHeightVO vo_height = new RoomHeightVO(height);

        //Act
        double result = vo_height.getValue();

        //Assert
        assertEquals(height, result);
    }
}