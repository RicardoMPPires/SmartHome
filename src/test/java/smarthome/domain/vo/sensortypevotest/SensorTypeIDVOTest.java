package smarthome.domain.vo.sensortypevotest;

import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SensorTypeIDVOTest {

    @Test
    void testValidParameters_returnsSensorTypeIDVO() {
        String type = "humidity";
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(type);
        assertNotNull(sensorTypeIDVO);
    }

    @Test
    void testNullSensorTypeID_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeIDVO(null));
    }

    @Test
    void testEmptySensorTypeID_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeIDVO(" "));
    }


    @Test
    void testGetSensorTypeID_returnsSensorTypeID() {
        String expected = "humidity";
        String type = "humidity";
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(type);
        assertEquals(expected, sensorTypeIDVO.getID());
    }
    /**
     * Test case to verify that the equals method of SensorTypeIDVO returns true when comparing the same SensorTypeIDVO object.
     */
    @Test
    public void givenSameSensorTypeIDVO_ShouldReturnTrue(){

        //Arrange
        String sensorTypeID = "humidity";

        //Act
        SensorTypeIDVO sensorID = new SensorTypeIDVO(sensorTypeID);

        //Assert
        assertTrue(sensorID.equals(sensorID));

    }

    /**
     * Test case to verify that the equals method of SensorTypeIDVO returns false when comparing a SensorTypeIDVO object with a null object.
     */
    @Test
    public void givenSensorTypeIDVOAndNullObject_ShouldReturnFalse(){

        //Arrange
        String sensorTypeID = "humidity";

        //Act
        SensorTypeIDVO sensorID = new SensorTypeIDVO(sensorTypeID);

        //Assert
        assertFalse(sensorID.equals(null));

    }

    /**
     * Test case to verify that the equals method of SensorTypeIDVO returns true when comparing two SensorTypeIDVO objects with the same sensorTypeID.
     */
    @Test
    public void givenTwoSensorTypeIDVOWithSameSensorTypeID_ShouldReturnTrue(){

        //Arrange
        String sensorTypeID = "humidity";

        //Act
        SensorTypeIDVO sensorID = new SensorTypeIDVO(sensorTypeID);
        SensorTypeIDVO sensorID2 = new SensorTypeIDVO(sensorTypeID);

        //Assert
        assertTrue(sensorID.equals(sensorID2));

    }

    /**
     * Test case to verify that the equals method of SensorTypeIDVO returns false when comparing two SensorTypeIDVO objects with different sensorTypeID.
     */
    @Test
    public void givenTwoSensorTypeIDVOWithDifferentSensorTypeID_ShouldReturnFalse(){

        //Arrange
        String sensorTypeID = "humidity";
        String sensorTypeID2 = "temperature";

        //Act
        SensorTypeIDVO sensorID = new SensorTypeIDVO(sensorTypeID);
        SensorTypeIDVO sensorID2 = new SensorTypeIDVO(sensorTypeID2);

        //Assert
        assertFalse(sensorID.equals(sensorID2));

    }

    /**
     * Test case to verify that the equals method of SensorTypeIDVO returns false when comparing a SensorTypeIDVO object with a different object.
     */
    @Test
    public void givenSensorTypeIDVOAndDifferentObject_ShouldReturnFalse(){

        //Arrange
        String sensorTypeID = "temperature";

        //Act
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        Object object = new Object();

        //Assert
        assertFalse(sensorTypeIDVO.equals(object));
    }

    /**
     * Test case to verify that the hashCode method of SensorTypeIDVO returns the correct hash code.
     */
    @Test
    public void givenTwoSensorTypeIDVOWithSameSensorTypeID_ShouldReturnSameHashCode() {

        // Arrange
        String sensorTypeID = "temperature";

        // Act
        SensorTypeIDVO sensorTypeID1 = new SensorTypeIDVO(sensorTypeID);
        SensorTypeIDVO sensorTypeID2 = new SensorTypeIDVO(sensorTypeID);

        // Assert
        assertEquals(sensorTypeID1.hashCode(), sensorTypeID2.hashCode());
    }

    /**
     * Test case to verify that the hashCode method of SensorTypeIDVO returns different hash codes for two SensorTypeIDVO objects with different sensorTypeID.
     */
    @Test
    public void givenTwoSensorTypeIDVOWithSameSensorTypeID_ShouldReturnDifferentHashCode() {

        // Arrange
        String sensorTypeID1 = "temperature";
        String sensorTypeID2 = "humidity";

        // Act
        SensorTypeIDVO sensorTypeIDVO1 = new SensorTypeIDVO(sensorTypeID1);
        SensorTypeIDVO sensorTypeIDVO2 = new SensorTypeIDVO(sensorTypeID2);

        // Assert
        assertNotEquals(sensorTypeIDVO1.hashCode(), sensorTypeIDVO2.hashCode());
    }

}