package smarthome.domain.vo.roomvotest;

import smarthome.domain.vo.roomvo.RoomWidthVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomWidthVOTest {

    @Test
    void getRoomWidth_Test(){
        //Arrange
        double width = 5;
        //Act
        RoomWidthVO roomWidth = new RoomWidthVO(width);
        double result = roomWidth.getValue();
        //Assert
        assertEquals(width, result);
    }
    @Test
    void roomWidth_objectCreation_FrontierSuccessTest(){
        //Arrange
        double width = 1;
        //Act
        RoomWidthVO roomWidth = new RoomWidthVO(width);
        //Assert
        assertNotNull(roomWidth);
    }
    @Test
    void roomWidth_objectCreation_FrontierFailTest(){
        // Arrange
        double width = 0;
        String expected = "Room Width has to be higher than zero";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomWidthVO(width));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

}