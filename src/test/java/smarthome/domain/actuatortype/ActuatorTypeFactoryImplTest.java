package smarthome.domain.actuatortype;

import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ActuatorTypeFactoryImplTest {

    /*
    SYSTEM UNDER TEST: FACTORY + ACTUATORTYPE IMPLEMENTATION
    A double of all the other collaborators is done (which are the required value objects to create the actuatorType).
     */

    /**
     * Test if the ActuatorTypeFactory can create an ActuatorType object given a ActuatorTypeIDVO
     */
    @Test
    void givenActuatorTypeIDVO_whenCreatingActuatorType_thenReturnActuatorType() {
        // Arrange
        ActuatorTypeIDVO actuatorTypeIDVODouble = mock(ActuatorTypeIDVO.class);
        ActuatorTypeFactoryImpl factoryActuatorType = new ActuatorTypeFactoryImpl();

        // Act
        ActuatorType actuatorType = factoryActuatorType.createActuatorType(actuatorTypeIDVODouble);

        // Assert
        ActuatorTypeIDVO resultActuatorTypeIDVO = actuatorType.getId();
        assertEquals(actuatorTypeIDVODouble, resultActuatorTypeIDVO);
    }

    /**
     * Test if the ActuatorTypeFactory can create an ActuatorType object given a ActuatorTypeIDVO. Throws
     * IllegalArgumentException with the message "ActuatorType cannot be null" and the object is not created
     */
    @Test
    void createActuatorType_whenNullActuatorType_ShouldPropagateIllegalArgumentException() {
        // Arrange
        String expected = "ActuatorType cannot be null";
        ActuatorTypeFactoryImpl factoryActuatorType = new ActuatorTypeFactoryImpl();

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> factoryActuatorType.createActuatorType(null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }
}