package smarthome.domain.vo.actuatortypevotest;

import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorTypeIDVOTest {
    @Test
    void testValidParameters_returnsActuatorTypeIDVO() {
        String type = "switchActuator";
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(type);
        assertNotNull(actuatorTypeIDVO);
    }

    @Test
    void testNullActuatorTypeID_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ActuatorTypeIDVO(null));
    }

    @Test
    void testEmptyActuatorTypeID_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ActuatorTypeIDVO(" "));
    }


    @Test
    void testGetActuatorTypeID_returnsActuatorTypeID() {
        String expected = "switchActuator";
        String type = "switchActuator";
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(type);
        assertEquals(expected, actuatorTypeIDVO.getID());
    }

    /**
     * Test case to verify that the equals method of ActuatorTypeIDVO returns true when comparing two ActuatorTypeIDVO objects
     * with the same identifier.
     */
    @Test
    void whenBothActuatorTypeIDVOsTheSame_shouldReturnTrue() {
        //Arrange
        String actuatorTypeId = "switchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId);
        ActuatorTypeIDVO actuatorTypeIDVO2 = actuatorTypeIDVO1;

        //Act
        boolean result = actuatorTypeIDVO1.equals(actuatorTypeIDVO2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of ActuatorTypeIDVO returns false when second ActuatorTypeIDVO object
     *  is null.
     */
    @Test
    void whenActuatorTypeIDVO2IsNull_shouldReturnFalse() {
        //Arrange
        String actuatorTypeId = "switchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId);
        ActuatorTypeIDVO actuatorTypeIDVO2 = null;

        //Act
        boolean result = actuatorTypeIDVO1.equals(actuatorTypeIDVO2);

        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of ActuatorTypeIDVO returns false when comparing an ActuatorTypeIDVO object
     * with a different type of object.
     */
    @Test
    void whenActuatorTypeIDVOComparedToOtherTypeOfObject_shouldReturnFalse() {
        //Arrange
        String actuatorTypeId = "switchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeId);

        Object otherObject = new Object();

        //Act
        boolean result = actuatorTypeIDVO.equals(otherObject);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the equals method of ActuatorTypeIDVO returns true when comparing two ActuatorTypeIDVO objects
     * with the same identifier.
     */
    @Test
    void whenActuatorTypeIDVOComparedToDifferentActuatorTypeIDVOWithSameID_shouldReturnTrue() {
        //Arrange
        String actuatorTypeId = "switchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId);
        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO(actuatorTypeId);

        //Act
        boolean result = actuatorTypeIDVO1.equals(actuatorTypeIDVO2);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the equals method of ActuatorTypeIDVO returns false when comparing two ActuatorTypeIDVO objects
     * with different identifiers.
     */
    @Test
    void whenActuatorTypeIDVOComparedToDifferentActuatorTypeIDVOWithDifferentID_shouldReturnFalse() {
        //Arrange
        String actuatorTypeId1 = "switchActuator";
        String actuatorTypeId2 = "lightActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId1);
        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO(actuatorTypeId2);

        //Act
        boolean result = actuatorTypeIDVO1.equals(actuatorTypeIDVO2);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that the hashCode method of ActuatorTypeIDVO returns different hash codes when called for two
     * ActuatorTypeIDVO objects with different identifiers.
     */
    @Test
    void whenHashCodeCalledOnTwoActuatorTypeWithDifferentID_shouldReturnDifferentHashCodes() {
        //Arrange
        String actuatorTypeId1 = "switchActuator";
        String actuatorTypeId2 = "lightActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId1);
        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO(actuatorTypeId2);

        //Act
        String result1 = actuatorTypeIDVO1.hashCode() + "";
        String result2 = actuatorTypeIDVO2.hashCode() + "";

        //Assert
        assertNotEquals(result1, result2);
    }

    @Test
    void whenHashCodeCalledOnTwoActuatorTypeWithSameID_shouldReturnSameHashCodes() {
        //Arrange
        String actuatorTypeId = "switchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId);
        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO(actuatorTypeId);

        //Act
        String result1 = actuatorTypeIDVO1.hashCode() + "";
        String result2 = actuatorTypeIDVO2.hashCode() + "";

        //Assert
        assertEquals(result1, result2);
    }
}