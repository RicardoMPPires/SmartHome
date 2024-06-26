package smarthome.domain.vo.sensorvotest;

import smarthome.domain.vo.sensorvo.SensorNameVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorNameVOTest {

    @Test
    void givenNullParameter_returnsException(){
        // Arrange
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SensorNameVO(null));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenEmptyParameter_returnsException(){
        // Arrange
        String sensorName = "";
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SensorNameVO(sensorName));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenBlankParameter_returnsException(){
        // Arrange
        String sensorName = " ";
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SensorNameVO(sensorName));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenValidParameter_returnsObject(){
        //Arrange
        String sensorName = "Sensor";
        //Act
        SensorNameVO sensorNameVO = new SensorNameVO(sensorName);
        //Assert
        assertNotNull(sensorNameVO);
        assertEquals(sensorName, sensorNameVO.getValue());
    }

    @Test
    void callingGetSensorName_returnsSensorNameString() {
        // Arrange
        String sensorName = "Sensor";
        String expected = "Sensor";
        SensorNameVO sensorNameVO = new SensorNameVO(sensorName);
        // Act
        String result = sensorNameVO.getValue();
        // Assert
        assertEquals(expected,result);
    }

}