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

class IntegerValueActuatorTest {

    /**
     * This test is used to check if an IllegalArgumentException is thrown when the actuator name is null.
     * First the actuator name is set to null.
     * Then, all the other parameters are mocked.
     * An expected string is set to compare with the exception message.
     * An exception is expected to be thrown when the IntSetRangeActuator is created.
     * A string is set to be attributed with the exception message.
     * Finally, the expected message is compared with the result message.
     */
    @Test
    void whenNullActuatorName_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);

        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new IntegerValueActuator(null, actuatorTypeID, deviceIDVO, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test is used to check if an IllegalArgumentException is thrown when the actuator type is null.
     * The actuator type is set to null while all the other parameters are mocked.
     * An expected string is set to compare with the exception message.
     * An exception is expected to be thrown when the IntSetRangeActuator is created.
     * A string is set to be attributed with the exception message.
     * Finally, the expected message is compared with the result message.
     */
    @Test
    void whenNullActuatorTypeID_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);

        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new IntegerValueActuator(actuatorName, null, deviceIDVO, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test is used to check if an IllegalArgumentException is thrown when the device ID is null.
     * The device ID is set to null while all the other parameters are mocked.
     * An expected string is set to compare with the exception message.
     * An exception is expected to be thrown when the IntSetRangeActuator is created.
     * A string is set to be attributed with the exception message.
     * Finally, the expected message is compared with the result message.
     */
    @Test
    void whenNullDeviceID_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new IntegerValueActuator(actuatorName, actuatorTypeID, null, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that if the settings are null, an IllegalArgumentException is thrown.
     * The settings are set to null while all the other parameters are mocked.
     * An expected string is set to compare with the exception message.
     * An exception is expected to be thrown when the IntSetRangeActuator is created.
     * A string is set to be attributed with the exception message.
     * Finally, the expected message is compared with the result message.
     */
    @Test
    void whenNullSettings_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new IntegerValueActuator(actuatorName, actuatorTypeID, deviceIDVO, null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that if the settings are not of the correct type, an IllegalArgumentException is thrown.
     * The settings are set to a DecimalSettingsVO object while all the other parameters are mocked.
     * An expected string is set to compare with the exception message.
     * An exception is expected to be thrown when the IntSetRangeActuator is created.
     * A string is set to be attributed with the exception message.
     * Finally, the expected message is compared with the result message.
     */
    @Test
    void whenInvalidSettings_thenThrowsIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        DecimalSettingsVO settings = mock(DecimalSettingsVO.class);

        String expected = "Invalid settings type";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new IntegerValueActuator(actuatorName, typeId, deviceId, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that if the value is within the actuator settings, but the executeCommand method fails, then returns an error message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * An expected size is set to compare with the size of the actuator ID list.
     * A string is set to be attributed with the expected actuator ID.
     * The actuatorIDVO is mocked to be used in the test with a given Id.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * The actuator ID, actuator name, actuator type and device Id are retrieved from the actuator.
     * Finally, all of these values are compared with the expected values, as well as the size of the list compared.
     */
    @Test
    void givenValidParameters_ifActuatorIsInstantiated_WhenGetAnyAttributes_ThenShouldReturnExpectedVOAttributes(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId= mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);

        int listExpectedSize = 1;
        String expectedActuatorId = "0001-XPTO-100-xpt11";

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class, (mock, context) ->
            when(mock.getID()).thenReturn("0001-XPTO-100-xpt11")))
        {
            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> actuatorIdList = mockedActuatorId.constructed();

            //Act
            String idResult = actuator.getId().getID();
            ActuatorNameVO nameResult = actuator.getActuatorName();
            ActuatorTypeIDVO typeResult = actuator.getActuatorTypeID();
            DeviceIDVO deviceIdResult = actuator.getDeviceID();

            //Assert
            assertEquals(expectedActuatorId, idResult);
            assertEquals(actuatorName, nameResult);
            assertEquals(deviceId, deviceIdResult);
            assertEquals(typeId, typeResult);
            assertEquals(listExpectedSize, actuatorIdList.size());
        }
    }

    /**
     * This test verifies that if the SimHardwareAct is null, the executeCommand method returns an error message.
     * The SimHardwareAct is set to null while all the other parameters are mocked.
     * A value is set to be used in the executeCommand method.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     */
    @Test
    void givenNullSimHardwareAct_WhenExecuteCommand_ThenShouldNotExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO configurations = mock(IntegerSettingsVO.class);

        String value = "5";
        String expected = "Invalid hardware, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, configurations);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(null, value);

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * This test verifies that if the value is lower than the lower limit, the executeCommand method returns an error message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * A value is set to be used in the executeCommand method.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     */
    @Test
    void givenValueLowerThanLowerLimitConfiguration_WhenExecuteCommand_ThenShouldNotExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);

        String value = "4";
        String expected = "Invalid value, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,value));;
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    @Test
    void givenUnparseableValue_executeCommandReturnsAppropriately(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);

        String value = "I will fail";
        String expected = "Unparseable value, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,value));;
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * This test verifies that if the value is equal to the lower limit, the executeCommand method returns a success message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * A value is set to be used in the executeCommand method.
     * SimhardwareAct is mocked to return true when the executeIntegerCommandSim method is called.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     */
    @Test
    void givenValueEqualToLowerLimitConfiguration_WhenExecuteCommand_ThenShouldReturnValue(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        int value = 5;
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(value)).thenReturn(true);


        String expected = "5";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(simHardwareAct, expected);

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * This test verifies that if the value is higher than the upper limit, the executeCommand method returns an error message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * A value is set to be used in the executeCommand method.
     * SimhardwareAct is mocked to return true when the executeIntegerCommandSim method is called.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The actuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     */
    @Test
    void givenValueEqualToUpperLimitConfiguration_WhenExecuteCommand_ThenShouldReturnValue(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        int value = 10;
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(value)).thenReturn(true);

        String expected = "10";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(simHardwareAct, expected);

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * This test verifies that if the value is higher than the upper limit, the executeCommand method returns an error message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * A value is set to be used in the executeCommand method.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     */
    @Test
    void givenValueHigherThanUpperLimitConfiguration_WhenExecuteCommand_ThenShouldNotExecuteCommand() {
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5, 10});
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);

        String value = "11";
        String expected = "Invalid value, could not execute command";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)) {

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,value));;
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }



    /**
     * This test verifies that if the value is within the actuator settings, the executeCommand method returns a success message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * A value is set to be used in the executeCommand method.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     */
    @Test
    void givenValueWithinActuatorSettings_WhenExecuteCommand_ThenShouldExecuteCommand(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(anyInt())).thenReturn(true);

        String value = "7";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(simHardwareAct, value);

            //Assert
            assertEquals(value, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    @Test
    void givenAppropriateValue_executeCommandReturnsValue_StatusVoSavesItToo(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(anyInt())).thenReturn(true);

        String value = "7";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            String result = actuator.executeCommand(simHardwareAct, value);
            String statusResult = actuator.getActuatorStatus().getValue();

            //Assert
            assertEquals(value, result);
            assertEquals(value, statusResult);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * This test verifies that if the value is within the actuator settings, but the executeCommand method fails, then returns an error message.
     * All the parameters are mocked, and the settings are set to a lower limit of 5 and an upper limit of 10.
     * The simHardwareAct is mocked to return false when the executeIntegerCommandSim method is called.
     * A value is set to be used in the executeCommand method.
     * An expected string is set to compare with the result message.
     * An expected size is set to compare with the size of the actuator ID list.
     * The actuatorIDVO is mocked to be used in the test.
     * The IntSetRangeActuator is created with the mocked parameters.
     * An actuatorIDVO list is created to check the size of the list.
     * A string is set to be attributed with the result of the executeCommand method.
     * Finally, the expected message is compared with the result message and the expected size is compared with the size of the list.
     *
     */
    @Test
    void givenValueWithinActuatorSettings_WhenExecuteCommandItFails_ThenShouldReturnErrorMessage(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeId = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);
        when(settings.getValue()).thenReturn(new Integer[]{5,10});
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(anyInt())).thenReturn(false);

        String value = "8";
        String expected = "Hardware error: Value was not set";
        int idListExpectedSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatorId = mockConstruction(ActuatorIDVO.class)){

            IntegerValueActuator actuator = new IntegerValueActuator(actuatorName, typeId, deviceId, settings);
            List<ActuatorIDVO> idList = mockedActuatorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,value));;
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

}