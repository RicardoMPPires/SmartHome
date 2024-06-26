package smarthome.domain.vo.actuatorvotest;

import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Represents a test class for ActuatorIDVO.
 */

class ActuatorIDVOTest {

    /**
     * Test case to verify that the ActuatorIDVO constructor throws an IllegalArgumentException
     * when given a null identifier. The result exception description must match the expected one.
     */

    @Test
    void givenNullIdentifier_whenCreatingActuatorIDVO_thenThrowsIllegalArgumentException() {
//        Arrange
        String expected = "Invalid Identifier";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorIDVO(null));
        String result = exception.getMessage();
//        Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that the getID method of ActuatorIDVO returns the correct ID as a string.
     */

    @Test
    void givenValidIdentifier_whenCreatingActuatorIDVO_thenReturnsCorrectIDAsString() {
//        Arrange
        UUID actuatorIdentifier = UUID.randomUUID();
        String expected = actuatorIdentifier.toString();
//        Act
        ActuatorIDVO actuatorID = new ActuatorIDVO(actuatorIdentifier);
        String result = actuatorID.getID();
//        Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that the equals method of ActuatorIDVO returns true when comparing to a different ActuatorIDVO object
     * with the same encapsulated string.
     */
    @Test
    void givenSameActuatorIDVO_ShouldReturnTrue(){
        //Arrange
        ActuatorIDVO actuatorID = new ActuatorIDVO(UUID.randomUUID());
        String strActuatorID = actuatorID.getID();
        ActuatorIDVO actuatorID2 = new ActuatorIDVO(UUID.fromString(strActuatorID));

        //Act
        boolean result = actuatorID.equals(actuatorID2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of ActuatorIDVO returns false when comparing two ActuatorIDVO objects with different identifiers.
     */
    @Test
    void givenTwoActuatorIDVOWithDifferentIdentifier_ShouldReturnFalse(){

        //Arrange
        UUID actuatorIdentifier1 = UUID.randomUUID();
        UUID actuatorIdentifier2 = UUID.randomUUID();
        ActuatorIDVO actuatorID1 = new ActuatorIDVO(actuatorIdentifier1);
        ActuatorIDVO actuatorID2 = new ActuatorIDVO(actuatorIdentifier2);

        //Act
        boolean result = actuatorID1.equals(actuatorID2);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of ActuatorIDVO returns false when comparing a ActuatorIDVO object with a different object.
     */
    @Test
    void givenActuatorIDVOAndDifferentObject_ShouldReturnFalse(){

        //Arrange
        UUID actuatorIdentifier = UUID.randomUUID();

        //Act
        ActuatorIDVO actuatorID = new ActuatorIDVO(actuatorIdentifier);
        Object object = new Object();

        //Assert
        assertNotEquals(actuatorID, object);
    }

    /**
     * Test case to verify that the hashCode method of RoomIDVO returns the correct hash code.
     */
    @Test
    void givenTwoRoomIDVOWithSameIdentifier_ShouldReturnSameHashCode() {
        // Arrange
        UUID actuatorIdentifier = UUID.randomUUID();

        // Act
        ActuatorIDVO actuatorID1 = new ActuatorIDVO(actuatorIdentifier);
        ActuatorIDVO actuatorID2 = new ActuatorIDVO(actuatorIdentifier);

        // Assert
        assertEquals(actuatorID1.hashCode(), actuatorID2.hashCode());
    }

    /**
     * Test case to verify that the hashCode method of RoomIDVO returns different hash codes for two RoomIDVO objects with different identifiers.
     */
    @Test
    void givenTwoRoomIDVOWithDifferentIdentifier_ShouldReturnDifferentHashCode() {
        // Arrange
        UUID actuatorIdentifier1 = UUID.randomUUID();
        UUID actuatorIdentifier2 = UUID.randomUUID();

        // Act
        ActuatorIDVO actuatorID1 = new ActuatorIDVO(actuatorIdentifier1);
        ActuatorIDVO actuatorID2 = new ActuatorIDVO(actuatorIdentifier2);

        // Assert
        assertNotEquals(actuatorID1.hashCode(), actuatorID2.hashCode());
    }
}