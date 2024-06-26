package smarthome.domain.vo.devicevotest;

import smarthome.domain.vo.devicevo.DeviceStatusVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DeviceStatusVOTest {

    @Test
    void givenFalseStatus_ShouldReturnFalse(){
        //Arrange
        boolean status = false;
        //Act
        DeviceStatusVO statusVO = new DeviceStatusVO(status);
        boolean result = statusVO.getValue();
        //Assert
        assertEquals(status, result);
    }

    @Test
    void givenTrueStatus_ShouldReturnTrue(){
        //Arrange
        boolean status = true;
        //Act
        DeviceStatusVO statusVO = new DeviceStatusVO(status);
        boolean result = statusVO.getValue();
        //Assert
        assertEquals(status, result);
    }
    @Test
    void givenTrueStatus_ShouldGetTrue(){
        //Arrange
        boolean status = true;
        boolean expected = true;
        DeviceStatusVO statusVO = new DeviceStatusVO(status);
        // Act
        boolean result = statusVO.getValue();
        // Assert
        assertEquals(expected,result);
    }
    @Test
    void givenFalseStatus_ShouldGetFalse(){
        //Arrange
        boolean status = true;
        boolean expected = true;
        DeviceStatusVO statusVO = new DeviceStatusVO(status);
        // Act
        boolean result = statusVO.getValue();
        // Assert
        assertEquals(expected,result);
    }
}
