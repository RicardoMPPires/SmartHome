package smarthome.domain.vo.devicevotest;

import smarthome.domain.vo.devicevo.DeviceIDVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeviceIDVOTest {
    /**
     * Test case to verify that the DeviceIDVO constructor throws an IllegalArgumentException
     * when given a null identifier. The result exception description must also match the expected one.
     */
    @Test
    void givenNullIdentifier_ShouldThrowIllegalArgumentException(){

        //Arrange
        String expected = "Invalid Identifier";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceIDVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * Test case to verify that the getID method of DeviceIDVO returns the correct ID as a string.
     */
    @Test
    void getIDShouldReturnCorrectIDAsString(){

        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        String expected = deviceIdentifier.toString();

        //Act
        DeviceIDVO deviceID = new DeviceIDVO(deviceIdentifier);
        String result = deviceID.getID();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * Test case to verify that the equals method of DeviceIDVO returns true when comparing two DeviceIDVO objects
     * with the same identifier.
     */
    @Test
    void whenBothDeviceIDVOsTheSame_shouldReturnTrue(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);
        DeviceIDVO deviceIDVO2 = deviceIDVO;

        //Act
        boolean result = deviceIDVO.equals(deviceIDVO2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of DeviceIDVO returns false when comparing two DeviceIDVO objects
     * with different identifiers.
     */
    @Test
    void whenDeviceIDVO2IsNull_shouldReturnFalse(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);

        //Act
        boolean result = deviceIDVO.equals(null);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of DeviceIDVO returns false when comparing a DeviceIDVO object
     * with a different type of object.
     */
    @Test
    void whenDeviceIDVOComparedToOtherTypeOfObject_shouldReturnFalse(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);
        Object otherObject = new Object();

        //Act
        boolean result = deviceIDVO.equals(otherObject);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of DeviceIDVO returns true when comparing two DeviceIDVO objects
     * with the same identifier.
     */
    @Test
    void whenDeviceIDVOComparedToDifferentDeviceIDVOWithSameId_shouldAssertTrue(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);
        DeviceIDVO deviceIDVO2 = new DeviceIDVO(deviceIdentifier);

        //Act
        boolean result = deviceIDVO.equals(deviceIDVO2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of DeviceIDVO returns false when comparing two DeviceIDVO objects
     * with different identifiers.
     */
    @Test
    void whenDeviceIDVOComparedToDifferentDeviceIDVOWithDifferentId_shouldAssertFalse(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        UUID deviceIdentifier2 = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);
        DeviceIDVO deviceIDVO2 = new DeviceIDVO(deviceIdentifier2);

        //Act
        boolean result = deviceIDVO.equals(deviceIDVO2);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the hashCode method of DeviceIDVO returns different hash codes for two DeviceIDVO objects
     * with different identifiers.
     */
    @Test
    void whenHashCodeCalledOnTwoDeviceIDVOWithDifferentId_shouldReturnDifferentHashCode(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        UUID deviceIdentifier2 = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);
        DeviceIDVO deviceIDVO2 = new DeviceIDVO(deviceIdentifier2);

        //Act
        int result = deviceIDVO.hashCode();
        int result2 = deviceIDVO2.hashCode();

        //Assert
        assertNotEquals(result,result2);
    }

    /**
     * Test case to verify that the hashCode method of DeviceIDVO returns the same hash code for two DeviceIDVO objects
     * with the same identifier.
     */
    @Test
    void whenHashCodeCalledOnTwoDeviceIDVOWithSameId_shouldReturnSameHashCode(){
        //Arrange
        UUID deviceIdentifier = UUID.randomUUID();
        DeviceIDVO deviceIDVO = new DeviceIDVO(deviceIdentifier);
        DeviceIDVO deviceIDVO2 = new DeviceIDVO(deviceIdentifier);

        //Act
        int result = deviceIDVO.hashCode();
        int result2 = deviceIDVO2.hashCode();

        //Assert
        assertEquals(result,result2);
    }
}