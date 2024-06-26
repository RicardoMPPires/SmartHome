package smarthome.domain.vo.actuatorvotest;

import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActuatorNameVOTest {

    @Test
    void givenNullParameter_returnsException(){
        // Arrange
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorNameVO(null));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenEmptyParameter_returnsException(){
        // Arrange
        String actuatorName = "";
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorNameVO(actuatorName));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenBlankParameter_returnsException(){
        // Arrange
        String actuatorName = " ";
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorNameVO(actuatorName));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenValidParameter_returnsObject(){
        //Arrange
        String actuatorName = "Actuator";
        //Act
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorName);
        //Assert
        assertNotNull(actuatorNameVO);
        assertEquals(actuatorName, actuatorNameVO.getValue());
    }

    @Test
    void callingGetActuatorName_returnsActuatorNameString() {
        // Arrange
        String actuatorName = "Actuator";
        String expected = "Actuator";
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorName);
        // Act
        String result = actuatorNameVO.getValue();
        // Assert
        assertEquals(expected,result);
    }

}
