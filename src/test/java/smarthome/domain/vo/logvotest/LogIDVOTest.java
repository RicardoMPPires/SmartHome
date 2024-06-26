package smarthome.domain.vo.logvotest;

import org.junit.jupiter.api.Test;
import smarthome.domain.vo.logvo.LogIDVO;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LogIDVOTest {
    /**
     * Test case to verify that the LogIDVO constructor throws an IllegalArgumentException
     * when given a null identifier. The result exception description must also match the expected one.
     */
    @Test
    void givenNullIdentifier_ShouldThrowIllegalArgumentException(){

        //Arrange
        String expected = "Invalid Identifier";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new LogIDVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * Test case to verify that the getID method of LogIDVO returns the correct ID as a string.
     */
    @Test
    void getIDShouldReturnCorrectIDAsString(){

        //Arrange
        UUID logIdentifier = UUID.randomUUID();
        String expected = logIdentifier.toString();

        //Act
        LogIDVO logID = new LogIDVO(logIdentifier);
        String result = logID.getID();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that the equals method of LogIDVO returns true when comparing to a different LogIDVO object
     * with the same encapsulated string.
     */
    @Test
    void givenSameRoomIDVO_ShouldReturnTrue(){
        //Arrange
        LogIDVO logID = new LogIDVO(UUID.randomUUID());
        String strlogID = logID.getID();
        LogIDVO logID2 = new LogIDVO(UUID.fromString(strlogID));

        //Act
        boolean result = logID.equals(logID2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of LogIDVO returns false when comparing two LogIDVO objects with different identifiers.
     */
    @Test
    void givenTwoRoomIDVOWithDifferentIdentifier_ShouldReturnFalse(){

        //Arrange
        UUID logIdentifier1 = UUID.randomUUID();
        UUID logIdentifier2 = UUID.randomUUID();
        LogIDVO logID1 = new LogIDVO(logIdentifier1);
        LogIDVO logID2 = new LogIDVO(logIdentifier2);

        //Act
        boolean result = logID1.equals(logID2);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of LogIDVO returns false when comparing a LogIDVO object with a different object.
     */
    @Test
    void givenRoomIDVOAndDifferentObject_ShouldReturnFalse(){

        //Arrange
        UUID logIdentifier = UUID.randomUUID();

        //Act
        LogIDVO logID = new LogIDVO(logIdentifier);
        Object object = new Object();

        //Assert
        assertNotEquals(logID, object);
    }

    /**
     * Test case to verify that the hashCode method of LogIDVO returns the correct hash code.
     */
    @Test
    void givenTwoRoomIDVOWithSameIdentifier_ShouldReturnSameHashCode() {
        // Arrange
        UUID logIdentifier = UUID.randomUUID();

        // Act
        LogIDVO logID1 = new LogIDVO(logIdentifier);
        LogIDVO logID2 = new LogIDVO(logIdentifier);

        // Assert
        assertEquals(logID1.hashCode(), logID2.hashCode());
    }

    /**
     * Test case to verify that the hashCode method of LogIDVO returns different hash codes for two LogIDVO objects with different identifiers.
     */
    @Test
    void givenTwoRoomIDVOWithDifferentIdentifier_ShouldReturnDifferentHashCode() {
        // Arrange
        UUID logIdentifier1 = UUID.randomUUID();
        UUID logIdentifier2 = UUID.randomUUID();

        // Act
        LogIDVO logID1 = new LogIDVO(logIdentifier1);
        LogIDVO logID2 = new LogIDVO(logIdentifier2);

        // Assert
        assertNotEquals(logID1.hashCode(), logID2.hashCode());
    }
}
