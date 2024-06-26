package smarthome.domain.vo.roomvotest;


import smarthome.domain.vo.roomvo.RoomIDVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class RoomIDVOTest {

    /**
     * Test case to verify that the RoomIDVO constructor throws an IllegalArgumentException
     * when given a null identifier. The result exception description must also match the expected one.
     */
    @Test
    void givenNullIdentifier_ShouldThrowIllegalArgumentException(){

        //Arrange
        String expected = "Invalid Identifier";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RoomIDVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * Test case to verify that the getID method of RoomIDVO returns the correct ID as a string.
     */
    @Test
    void getIDShouldReturnCorrectIDAsString(){

        //Arrange
        UUID roomIdentifier = UUID.randomUUID();
        String expected = roomIdentifier.toString();

        //Act
        RoomIDVO roomID = new RoomIDVO(roomIdentifier);
        String result = roomID.getID();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that the equals method of RoomIDVO returns true when comparing to a different RoomIDVO object
     * with the same encapsulated string.
     */
    @Test
    void givenSameRoomIDVO_ShouldReturnTrue(){
        //Arrange
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        String strRoomID = roomID.getID();
        RoomIDVO roomID2 = new RoomIDVO(UUID.fromString(strRoomID));

        //Act
        boolean result = roomID.equals(roomID2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of RoomIDVO returns false when comparing two RoomIDVO objects with different identifiers.
     */
    @Test
    void givenTwoRoomIDVOWithDifferentIdentifier_ShouldReturnFalse(){

        //Arrange
        UUID roomIdentifier1 = UUID.randomUUID();
        UUID roomIdentifier2 = UUID.randomUUID();
        RoomIDVO roomID1 = new RoomIDVO(roomIdentifier1);
        RoomIDVO roomID2 = new RoomIDVO(roomIdentifier2);

        //Act
        boolean result = roomID1.equals(roomID2);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of RoomIDVO returns false when comparing a RoomIDVO object with a different object.
     */
    @Test
    void givenRoomIDVOAndDifferentObject_ShouldReturnFalse(){

        //Arrange
        UUID roomIdentifier = UUID.randomUUID();

        //Act
        RoomIDVO roomID = new RoomIDVO(roomIdentifier);
        Object object = new Object();

        //Assert
        assertNotEquals(roomID, object);
    }

    /**
     * Test case to verify that the hashCode method of RoomIDVO returns the correct hash code.
     */
    @Test
    void givenTwoRoomIDVOWithSameIdentifier_ShouldReturnSameHashCode() {
        // Arrange
        UUID roomIdentifier = UUID.randomUUID();

        // Act
        RoomIDVO roomID1 = new RoomIDVO(roomIdentifier);
        RoomIDVO roomID2 = new RoomIDVO(roomIdentifier);

        // Assert
        assertEquals(roomID1.hashCode(), roomID2.hashCode());
    }

    /**
     * Test case to verify that the hashCode method of RoomIDVO returns different hash codes for two RoomIDVO objects with different identifiers.
     */
    @Test
    void givenTwoRoomIDVOWithDifferentIdentifier_ShouldReturnDifferentHashCode() {
        // Arrange
        UUID roomIdentifier1 = UUID.randomUUID();
        UUID roomIdentifier2 = UUID.randomUUID();

        // Act
        RoomIDVO roomID1 = new RoomIDVO(roomIdentifier1);
        RoomIDVO roomID2 = new RoomIDVO(roomIdentifier2);

        // Assert
        assertNotEquals(roomID1.hashCode(), roomID2.hashCode());
    }

}