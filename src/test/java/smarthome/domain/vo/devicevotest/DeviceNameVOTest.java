package smarthome.domain.vo.devicevotest;

import smarthome.domain.vo.devicevo.DeviceNameVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceNameVOTest {

    @Test
    void givenNullParameter_returnsException(){
        // Arrange
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceNameVO(null));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenEmptyParameter_returnsException(){
        // Arrange
        String deviceName = "";
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceNameVO(deviceName));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenBlankParameter_returnsException(){
        // Arrange
        String deviceName = " ";
        String expected = "Invalid parameters.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceNameVO(deviceName));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    @Test
    void givenValidParameter_returnsObject(){
        //Arrange
        String deviceName = "Device";
        //Act
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        //Assert
        assertNotNull(deviceNameVO);
        assertEquals(deviceName, deviceNameVO.getValue());
    }

    @Test
    void callingGetDeviceName_returnsDeviceNameString() {
        // Arrange
        String deviceName = "Device";
        String expected = "Device";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        // Act
        String result = deviceNameVO.getValue();
        // Assert
        assertEquals(expected,result);
    }
}