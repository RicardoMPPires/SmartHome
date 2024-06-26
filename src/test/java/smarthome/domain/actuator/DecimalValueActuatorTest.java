package smarthome.domain.actuator;

import smarthome.domain.actuator.externalservices.SimHardwareAct;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.actuatorvo.DecimalSettingsVO;
import smarthome.domain.vo.actuatorvo.IntegerSettingsVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * To properly isolate the DecimalValueActuator Class, doubles of all the actuator collaborators are done (ActuatorNameVO,
 * ActuatorTypeIDVO, DeviceIDVO, DecimalSettingsVO and SimHardwareAct), when applicable, and their behaviour is conditioned.
 */

class DecimalValueActuatorTest {
    /**
     * Verifies that when given an invalid hardware, the command cannot be executed.
     * The message "Invalid hardware, could not execute command" is returned.
     */
    @Test
    void givenNullHardware_WhenExecuteCommand_ThenShouldNotExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);

        String value = "5.6";
        String expected = "Invalid hardware, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(null,value));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value is lower than the lower limit, the operation fails.
     * The message "Value out of actuator limits, could not execute command" is returned.
     */
    @Test
    void givenValueLowerThanLowerLimitConfiguration_WhenExecuteCommand_ThenShouldNotExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);

        String value = "5.678";
        String expected = "Value out of actuator limits, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(hardware,value));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value is higher than the upper limit, the operation fails.
     * The message "Value out of actuator limits, could not execute command" is returned.
     */
    @Test
    void givenValueHigherThanUpperLimitConfiguration_WhenExecuteCommand_ThenShouldNotExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);

        String value = "10.6";
        String expected = "Value out of actuator limits, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(hardware,value));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value has a higher precision than acceptable, the actuator rounds the value to be set according
     * to its set precision and executes the command.
     * In this case, actuator has a precision of 0.001 and the value required to be set is 7.7777777.
     * The message "Value was rounded and set to 7.778" is returned.
     */
    @Test
    void givenValueWithHigherPrecisionThanActuatorSettings_WhenExecuteCommand_ThenShouldRoundValueToAdequatePrecisionAndExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.001});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(true);

        String value = "7.7777777";
        String expected = "7.778";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(hardware, value);

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value is equal to the lower limit the command is executed.
     * The message "Value was set" is returned.
     */
    @Test
    void givenValueEqualToLowerLimit_WhenExecuteCommand_ThenCommandIsExecuted(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(true);

        String value = "6.0";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(hardware, value);

            //Assert
            assertEquals(value, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    @Test
    void givenValidValue_executeCommandReturnsValue_andStatusIsSet(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.001});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(true);

        String value = "7.7777777";
        String expected = "7.778";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(hardware, value);
            String resultStatus = actuator.getActuatorStatus().getValue();

            //Assert
            assertEquals(expected, result);
            assertEquals(expected, resultStatus);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value is equal to the upper limit the command is executed.
     * The message "Value was set" is returned.
     */
    @Test
    void givenValueEqualToUpperLimit_WhenExecuteCommand_ThenCommandIsExecuted(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(true);

        String value = "9.0";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(hardware, value);

            //Assert
            assertEquals(value, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value has a lower precision that actuator settings, it is allowed for the command to be executed.
     * In this case, actuator has a precision of 0.01 and the value required to be set is 7.
     * The message "Value was set" is returned.
     */
    @Test
    void givenValueWithinActuatorSettings_WhenExecuteCommand_ThenCommandIsExecuted(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(true);

        String value = "7.55";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(hardware, value);

            //Assert
            assertEquals(value, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when set value respects actuator settings, operation may fail due to the connection to the hardware.
     * The message "Error: Value was not set" is returned.
     */
    @Test
    void givenValueWithinActuatorSettings_WhenExecuteCommandItFails_ThenShouldReturnErrorMessage(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(false);

        String value = "7.7";
        String expected = "Hardware error: Value was not set";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(hardware,value));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Test case to verify the behavior of the executeCommand method when given an unparseable value.
     * <p>
     * Given an ActuatorNameVO, ActuatorTypeIDVO, DeviceIDVO, and DecimalSettingsVO mocks,
     * along with a SimHardwareAct mock with a failing executeDecimalCommand method,
     * and an unparseable value,
     * when the executeCommand method is called,
     * then it should return "Invalid value, could not execute command",
     * and the size of the constructed ActuatorIDVO list should be 1.
     */
    @Test
    void givenUnparseableValue_executeCommandReturnsAppropriately(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);
        when(configurations.getValue()).thenReturn(new Double[]{6.0,9.0,0.01});
        SimHardwareAct hardware = mock(SimHardwareAct.class);
        when(hardware.executeDecimalCommand(anyDouble())).thenReturn(false);

        String value = "I will fail";
        String expected = "Unparseable value, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            DecimalValueActuator actuator = new DecimalValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(hardware,value));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when there is an attempt to instantiate the actuator with a null name, an IllegalArgumentException
     * is thrown and the message "Invalid actuator parameters" is returned.
     */
    @Test
    void givenNullName_WhenActuatorIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);

        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new DecimalValueActuator(null, typeId, deviceId, configurations));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when there is an attempt to instantiate the actuator with a null actuator type ID, an IllegalArgumentException
     * is thrown and the message "Invalid actuator parameters" is returned.
     */
    @Test
    void givenNullTypeID_WhenActuatorIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);

        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new DecimalValueActuator(actuatorName, null, deviceId, configurations));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when there is an attempt to instantiate the actuator with a null device ID, an IllegalArgumentException
     * is thrown and the message "Invalid actuator parameters" is returned.
     */
    @Test
    void givenNullDeviceID_WhenActuatorIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DecimalSettingsVO configurations = mock(DecimalSettingsVO.class);

        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new DecimalValueActuator(actuatorName, typeId, null, configurations));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when there is an attempt to instantiate the actuator with null settings, an IllegalArgumentException
     * is thrown and the message "Invalid actuator parameters" is returned.
     */
    @Test
    void givenNullSettings_WhenActuatorIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);

        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new DecimalValueActuator(actuatorName, typeId, deviceId, null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when there is an attempt to instantiate the actuator with invalid settings type (integer settings),
     * an IllegalArgumentException is thrown and the message "Invalid settings type" is returned.
     */
    @Test
    void givenInvalidSettings_WhenActuatorIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);

        String expected = "Invalid settings type";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new DecimalValueActuator(actuatorName, typeId, deviceId, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when a valid Actuator is created, when all its VOs are requested (except from the settings), the expected
     * Actuator VOs are returned. For the ActuatorID the actual ID in a String format is returned.
     * All the asserts are done in the same test in order to not have code repetition.
     */
    @Test
    void givenValidParameters_ActuatorIsInstantiated_WhenGetAnyAttributes_ThenShouldReturnExpectedVOAttributes(){
        //Arrange
        ActuatorNameVO nameDouble = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeIdDouble = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        DecimalSettingsVO settingsDouble = mock(DecimalSettingsVO.class);

        int idListExpectedSize = 1;
        String expectedActuatorId = "xpto-0000-500-cmp67";

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class, (mock, context) ->
            when(mock.getID()).thenReturn("xpto-0000-500-cmp67")))
        {
            DecimalValueActuator actuator = new DecimalValueActuator(nameDouble, typeIdDouble, deviceIdDouble, settingsDouble);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String actuatorIdResult = actuator.getId().getID();
            ActuatorNameVO nameResult = actuator.getActuatorName();
            ActuatorTypeIDVO typeResult = actuator.getActuatorTypeID();
            DeviceIDVO deviceIdResult = actuator.getDeviceID();

            //Assert
            assertEquals(expectedActuatorId, actuatorIdResult);
            assertEquals(nameDouble, nameResult);
            assertEquals(deviceIdDouble, deviceIdResult);
            assertEquals(typeIdDouble, typeResult);
            assertEquals(idListExpectedSize, idList.size());
        }
    }
}