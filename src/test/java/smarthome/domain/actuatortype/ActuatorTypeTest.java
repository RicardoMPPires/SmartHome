package smarthome.domain.actuatortype;

import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorTypeTest {

    /**
     * Test if the ActuatorType can create an ActuatorType object given a ActuatorTypeIDVO
     */
    @Test
    void givenValidActuatorTypeIDVO_whenCreatingActuatorType_thenReturnActuatorType() {
        // Arrange
        ActuatorTypeIDVO actuatorTypeIDVODouble = mock(ActuatorTypeIDVO.class);

        // Act
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIDVODouble);
        // Assert
        assertNotNull(actuatorType);
    }

    /**
     * Test if the ActuatorType throws an IllegalArgumentException when creating an ActuatorType object given a null ActuatorTypeIDVO
     */
    @Test
    void givenNullActuatorTypeIDVO_whenCreatingActuatorType_thenThrowIllegalArgumentException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new ActuatorType(null));
    }

    /**
     * Test if the ActuatorType gets the ActuatorType string
     */
    @Test
    void givenActuatorTypeIDVO_whenGettingActuatorType_thenReturnActuatorType() {
        // Arrange
        ActuatorTypeIDVO actuatorTypeIDVODouble = mock(ActuatorTypeIDVO.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIDVODouble);
        when(actuatorTypeIDVODouble.toString()).thenReturn("ActuatorType");

        // Act
        String actuatorTypeString = actuatorType.getId().toString();
        String expected = "ActuatorType";

        // Assert
        assertEquals(expected, actuatorTypeString);
    }

    /**
     * Test if the ActuatorType gets the ID
     */
    @Test
    void givenActuatorTypeIDVO_whenGettingID_thenReturnActuatorTypeIDVO() {
        // Arrange
        ActuatorTypeIDVO actuatorTypeIDVODouble = mock(ActuatorTypeIDVO.class);
        ActuatorType actuatorType = new ActuatorType(actuatorTypeIDVODouble);

        // Act
        ActuatorTypeIDVO actuatorTypeIDVO = actuatorType.getId();

        // Assert
        assertEquals(actuatorTypeIDVODouble, actuatorTypeIDVO);
    }
}