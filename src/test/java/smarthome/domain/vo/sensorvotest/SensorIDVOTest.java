package smarthome.domain.vo.sensorvotest;

import smarthome.domain.vo.sensorvo.SensorIDVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SensorIDVOTest {
    /**
     * Verifies that an IllegalArgumentException is thrown when a null identifier is given.
     * The exception message thrown should be "Invalid Identifier".
     */
    @Test
    void whenIdentifierIsNull_ThenThrowsIllegalArgumentException(){
        //Assert
        String expected = "Invalid Identifier";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SensorIDVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that the getID method returns the correct sensor identifier in a string format.
     * The single collaborator is the UUID library, so it was not mocked.
     */
    @Test
    void givenValidIdentifier_whenGetID_ThenReturnSensorIDAsString(){
        //Assert
        UUID identifier = UUID.randomUUID();
        String expected = identifier.toString();

        SensorIDVO sensorID = new SensorIDVO(identifier);

        //Act
        String result = sensorID.getID();

        //Assert
        assertEquals(expected, result);
    }
    /**
     * Test case to verify that the equals method of SensorIDVO returns true when comparing the same SensorIDVO object.
     */
    @Test
    public void givenSameSensorIDVO_ShouldReturnTrue(){

        //Arrange
        UUID sensorIdentifier = UUID.randomUUID();

        //Act
        SensorIDVO sensorID = new SensorIDVO(sensorIdentifier);

        //Assert
        assertTrue(sensorID.equals(sensorID));

    }

    /**
     * Test case to verify that the equals method of SensorIDVO returns false when comparing a SensorIDVO object with a null object.
     */
    @Test
    public void givenSensorIDVOAndNullObject_ShouldReturnFalse(){

        //Arrange
        UUID sensorIdentifier = UUID.randomUUID();

        //Act
        SensorIDVO sensorID = new SensorIDVO(sensorIdentifier);

        //Assert
        assertFalse(sensorID.equals(null));

    }

    /**
     * Test case to verify that the equals method of SensorIDVO returns true when comparing two SensorIDVO objects with the same identifier.
     */
    @Test
    public void givenTwoSensorIDVOWithSameIdentifier_ShouldReturnTrue(){

        //Arrange
        UUID sensorIdentifier = UUID.randomUUID();

        //Act
        SensorIDVO sensorID1 = new SensorIDVO(sensorIdentifier);
        SensorIDVO sensorID2 = new SensorIDVO(sensorIdentifier);

        //Assert
        assertTrue(sensorID1.equals(sensorID2));

    }

    /**
     * Test case to verify that the equals method of SensorIDVO returns false when comparing two SensorIDVO objects with different identifiers.
     */
    @Test
    public void givenTwoSensorIDVOWithDifferentIdentifier_ShouldReturnFalse(){

        //Arrange
        UUID sensorIdentifier1 = UUID.randomUUID();
        UUID sensorIdentifier2 = UUID.randomUUID();

        //Act
        SensorIDVO sensorID1 = new SensorIDVO(sensorIdentifier1);
        SensorIDVO sensorID2 = new SensorIDVO(sensorIdentifier2);

        //Assert
        assertFalse(sensorID1.equals(sensorID2));

    }

    /**
     * Test case to verify that the equals method of SensorIDV0 returns false when comparing a SensorIDVO object with a different object.
     */
    @Test
    public void givenSensorIDVOAndDifferentObject_ShouldReturnFalse(){

        //Arrange
        UUID sensorIdentifier = UUID.randomUUID();

        //Act
        SensorIDVO sensorID = new SensorIDVO(sensorIdentifier);
        Object object = new Object();

        //Assert
        assertFalse(sensorID.equals(object));
    }

    /**
     * Test case to verify that the hashCode method of SensorIDVO returns the correct hash code.
     */
    @Test
    public void givenTwoSensorIDVOWithSameIdentifier_ShouldReturnSameHashCode() {
        // Arrange
        UUID sensorIdentifier = UUID.randomUUID();

        // Act
        SensorIDVO sensorID1 = new SensorIDVO(sensorIdentifier);
        SensorIDVO sensorID2 = new SensorIDVO(sensorIdentifier);

        // Assert
        assertEquals(sensorID1.hashCode(), sensorID2.hashCode());
    }

    /**
     * Test case to verify that the hashCode method of SensorIDVO returns different hash codes for two SensorIDVO objects with different identifiers.
     */
    @Test
    public void givenTwoRoomIDVOWithDifferentIdentifier_ShouldReturnDifferentHashCode() {
        // Arrange
        UUID sensorIdentifier = UUID.randomUUID();
        UUID sensorIdentifier2 = UUID.randomUUID();

        // Act
        SensorIDVO sensorID1 = new SensorIDVO(sensorIdentifier);
        SensorIDVO sensorID2 = new SensorIDVO(sensorIdentifier2);

        // Assert
        assertNotEquals(sensorID1.hashCode(), sensorID2.hashCode());
    }
}

