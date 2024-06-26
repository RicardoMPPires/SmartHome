package smarthome.domain.vo.roomvotest;

import smarthome.domain.vo.roomvo.RoomNameVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomNameVOTest {

    /**
     * This test verifies that the constructor of the RoomNameVO class throws an IllegalArgumentException
     * when provided with a null argument for the room name.
     * First, a null value is assigned to the roomName variable to represent an invalid parameter.
     * The expected exception message ("Invalid parameters.") is defined for comparison.
     * Then, an assertThrows statement is used to capture the exception thrown by the constructor when called with
     * the null `roomName`. The captured exception is stored in the `exception` variable.
     * The actual exception message is retrieved using the getMessage method and stored in the result variable.
     * Finally, the test asserts that the captured exception is indeed an IllegalArgumentException using assertThrows.
     * The test then compares the actual exception message (result) with the expected exception message (expected)
     * using `assertEquals`. This verifies that the constructor threw the correct exception with the correct message
     */
    @Test
    void givenNullParameter_returnsException(){
        //Arrange
        String expected = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomNameVO(null));
        String result = exception.getMessage();
        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that the constructor of the RoomNameVO class throws an IllegalArgumentException
     * when provided with an empty string argument for the room name.
     * First, an empty string "" is assigned to the roomName variable to represent an invalid parameter.
     * The expected exception message ("Invalid parameters.") is defined for comparison.
     * Then, an assertThrows statement is used to capture the exception thrown by the constructor when called with
     * the empty `roomName`. The captured exception is stored in the `exception` variable.
     * The actual exception message is retrieved using the getMessage method and stored in the result variable.
     * Finally, the test asserts that the captured exception is indeed an IllegalArgumentException using assertThrows.
     * The test then compares the actual exception message (result) with the expected exception message (expected)
     * using `assertEquals`. This verifies that the constructor threw the correct exception with the correct message.
     */
    @Test
    void givenEmptyParameter_returnsException(){
        //Arrange
        String roomName = "";
        String expected = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomNameVO(roomName));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that the constructor of the RoomNameVO class throws an IllegalArgumentException
     * when provided with a string argument containing only whitespace characters for the room name.
     * First, a string containing only a space (" ") is assigned to the roomName variable to represent an invalid parameter.
     * The expected exception message ("Invalid parameters.") is defined for comparison.
     * Then, an assertThrows statement is used to capture the exception thrown by the constructor when called with
     * the string containing only whitespace. The captured exception is stored in the `exception` variable.
     * The actual exception message is retrieved using the getMessage method and stored in the result variable.
     * Finally, the test asserts that the captured exception is indeed an IllegalArgumentException using assertThrows.
     * The test then compares the actual exception message (result) with the expected exception message (expected)
     * using `assertEquals`. This verifies that the constructor threw the correct exception with the correct message.
     */
    @Test
    void givenBlankParameter_returnsException(){
        //Arrange
        String roomName = " ";
        String expected = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomNameVO(roomName));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that the constructor of the RoomNameVO class successfully creates a new object
     * when provided with a valid room name argument.
     * First, a valid string "room" is assigned to the roomName variable.
     * Then, a new RoomNameVO object is created using the valid roomName.
     * The created object is stored in the roomNameVO variable.
     * Finally, the test asserts that the roomNameVO is not null using assertNotNull. This verifies that the constructor
     * did not throw any exceptions and successfully created the object.
     */
    @Test
    void givenValidParameter_returnsObject(){
        //Arrange
        String roomName = "room";

        //Act
        RoomNameVO roomNameVO = new RoomNameVO(roomName);

        //Assert
        assertNotNull(roomNameVO);
    }

    /**
     * This test verifies that the getRoomName method of the RoomNameVO class correctly retrieves and returns
     * the room name that was set during its creation.
     * First, a valid string "room" is assigned to the roomName variable.
     * A new RoomNameVO object is created using the roomName.
     * The expected room name ("room") is defined for comparison.
     * Then, the getRoomName method is called on the roomNameVO object to retrieve the stored room name.
     * The retrieved room name is stored in the result variable.
     * Finally, the test asserts that the result (the actual room name returned by getRoomName())
     * is equal to the `expected` room name. This verifies that the `getRoomName` method successfully
     * returns the room name that was set earlier.
     */
    @Test
    void callingGetRoomName_returnsRoomNameString(){
        //Arrange
        String roomName = "room";
        RoomNameVO roomNameVO = new RoomNameVO(roomName);
        String expected = "room";

        //Act
        String result = roomNameVO.getValue();

        //Assert
        assertEquals(expected,result);
    }
}
