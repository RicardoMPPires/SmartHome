package smarthome.domain.vo.devicevotest;

import smarthome.domain.vo.devicevo.DeviceModelVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceModelVOTest {

    /**
     * Validates that, when given a null DeviceModel, the class throws an exception.
     */
    @Test
    void whenNullDeviceModel_thenThrowsInstantiationException(){
        //Arrange
        String expected = "Device model cannot be null neither blank.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new DeviceModelVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Validates that, when given a blank DeviceModel, the class throws an exception.
     */
    @Test
    void whenBlankDeviceModel_thenThrowsInstantiationException(){
        //Arrange
        String deviceModel = " ";
        String expected = "Device model cannot be null neither blank.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new DeviceModelVO(deviceModel));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Validates that, when given an empty DeviceModel, the class throws an exception.
     */
    @Test
    void whenEmptyDeviceModel_thenThrowsInstantiationException(){
        //Arrange
        String deviceModel = "";
        String expected = "Device model cannot be null neither blank.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new DeviceModelVO(deviceModel));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Validates that when the method toString is called, class returns correctly
     */
    @Test
    void whenGetValue_ShouldReturnDeviceModel() {
        //Arrange
        String deviceModel = "Model HKT";
        DeviceModelVO modelVO = new DeviceModelVO(deviceModel);

        //Act
        String result = modelVO.getValue();

        //Assert
        assertEquals(deviceModel, result);
    }

}