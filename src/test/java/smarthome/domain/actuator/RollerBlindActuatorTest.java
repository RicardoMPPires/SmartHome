package smarthome.domain.actuator;

import smarthome.domain.actuator.externalservices.SimHardwareAct;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RollerBlindActuatorTest {


    /**
     * Test if the constructor throws an IllegalArgumentException when the actuatorName is null.
     * The expected result is an IllegalArgumentException with the message "Invalid Parameters".
     */
    @Test
    void whenNullActuatorName_ThenThrowIllegalArgumentException(){
        //Arrange
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        String expected = "Invalid parameters";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> new RollerBlindActuator(null, actuatorTypeID, deviceIDVO));
        //Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }


    /**
     * Test if the constructor throws an IllegalArgumentException when the actuatorTypeID is null.
     * The expected result is an IllegalArgumentException with the message "Invalid Parameters".
     */
    @Test
    void whenNullActuatorTypeID_ThenThrowIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        String expected = "Invalid parameters";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> new RollerBlindActuator(actuatorName, null, deviceIDVO));
        //Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }


    /**
     * Test if the constructor throws an IllegalArgumentException when the deviceID is null.
     * The expected result is an IllegalArgumentException with the message "Invalid Parameters".
     */
    @Test
    void whenNullDeviceID_ThenThrowIllegalArgumentException(){
        //Arrange
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        String expected = "Invalid parameters";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new RollerBlindActuator(actuatorName, actuatorTypeID, null));
        //Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }



    @Test
    void whenValidPositionAndExecuteCommand_thenReturnsValue(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(50)).thenReturn(true);

        String expected = "50";

        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            //Act
            RollerBlindActuator rollerBlindActuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            String result = rollerBlindActuator.executeCommand(simHardwareAct, expected);
            //Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }

    @Test
    void whenGivenNullSimHardware_executeCommandReturnsAppropriateMessage(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(101)).thenReturn(true);

        String expected = "Invalid hardware, could not execute command";
        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            RollerBlindActuator actuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(null,"I will fail"));;
            String result = exception.getMessage();
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }

    @Test
    void whenGivenUnparseableValue_executeCommandReturnsAppropriateMessage(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(101)).thenReturn(true);

        String expected = "Unparseable value, could not execute command";
        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            RollerBlindActuator actuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,"I will fail"));;
            String result = exception.getMessage();
            //Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }

    @Test
    void whenInvalidPositionOver100AndExecuteCommand_ThenReturnFalse(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(101)).thenReturn(true);

        String expected = "Invalid value, could not execute command";
        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            RollerBlindActuator actuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,"101"));;
            String result = exception.getMessage();
            //Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }


    @Test
    void whenInvalidPositionUnder0AndExecuteCommand_ThenReturnFalse(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(-1)).thenReturn(true);
        String expected = "Invalid value, could not execute command";
        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            RollerBlindActuator actuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,"-1"));;
            String result = exception.getMessage();
            //Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }


    @Test
    void whenValidPositionEquals0AndExecuteCommand_ThenReturnValue(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(0)).thenReturn(true);
        String expected = "0";
        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            //Act
            RollerBlindActuator rollerBlindActuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            String result = rollerBlindActuator.executeCommand(simHardwareAct, expected);
            //Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }


    @Test
    void whenValidPositionEquals100AndExecuteCommand_ThenReturnTrue(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(100)).thenReturn(true);

        String expected = "100";
        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            //Act
            RollerBlindActuator rollerBlindActuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            String result = rollerBlindActuator.executeCommand(simHardwareAct, expected);
            //Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }


    @Test
    void whenValidPositionAndExecuteCommandFails_ThenReturnFalse() {
        // Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(50)).thenReturn(false);

        String expected = "Hardware error: Value was not set";

        try (MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)) {
            RollerBlindActuator actuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, ()
                    -> actuator.executeCommand(simHardwareAct,"50"));;
            String result = exception.getMessage();
            // Assert
            assertEquals(expected,result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }



    /**
     * This test verifies that the correct ActuatorID value object is returned when the getId() method is called.
     * It isolates all Actuators collaborators and compares the object returned by the previously referred method
     * with the doubled ActuatorID.
     * As in previous tests, it´s being used Mocked Construction to intercept ActuatorID instantiation during
     * RollerBlindActuator instantiation.
     * By this interception the "real" ActuatorID object is being substituted with a double.
     * The assertion is made by getting the doubled object from MockedConstruction.constructed() list and compare it with the object
     * returned in the getID() operation. The references must match because the objects must be the same.
     * This assertion gives us certainty that the intercepted object is the object being return (as it should)
     * These tests have an additional assertion that verifies the number of instances created for ActuatorIDVO,
     * ensuring that the number of mocked constructions of these objects match the expected.
     */
    @Test
    void whenGetId_ThenReturnActuatorIDObject(){
        //Arrange
        int expectedIdDoubleSize = 1;
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SimHardwareAct simHardwareAct = mock(SimHardwareAct.class);
        when(simHardwareAct.executeIntegerCommandSim(50)).thenReturn(true);

        try(MockedConstruction<ActuatorIDVO> actuatorIDVOMockedConstruction = mockConstruction(ActuatorIDVO.class)){
            //Act
            RollerBlindActuator rollerBlindActuator = new RollerBlindActuator(actuatorName, actuatorTypeID, deviceIDVO);
            ActuatorIDVO result = rollerBlindActuator.getId();
            //Assert
            ActuatorIDVO expected = actuatorIDVOMockedConstruction.constructed().get(0);
            assertEquals(expected, result);
            assertEquals(expectedIdDoubleSize, actuatorIDVOMockedConstruction.constructed().size());
        }
    }

    /**
     * This test validates that getActuatorName successfully returns actuatorNameVo object
     */
    @Test
    void getActuatorName_returnsActuatorNameVO(){
        // Arrange
        ActuatorNameVO name = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO type = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        RollerBlindActuator actuator = new RollerBlindActuator(name,type,deviceID);
        // Act
        ActuatorNameVO result = actuator.getActuatorName();
        // Assert
        assertEquals(name,result);
    }

    /**
     * This test validates that getActuatorName successfully returns ActuatorTypeIDVO object
     */
    @Test
    void getActuatorTypeID_returnsActuatorTypeIDVO(){
        // Arrange
        ActuatorNameVO name = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO type = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        RollerBlindActuator actuator = new RollerBlindActuator(name,type,deviceID);
        // Act
        ActuatorTypeIDVO result = actuator.getActuatorTypeID();
        // Assert
        assertEquals(type,result);
    }

    /**
     * This test validates that getDeviceID successfully returns DeviceIDVO object
     */
    @Test
    void getDeviceID_returnsDeviceIDVO(){
        // Arrange
        ActuatorNameVO name = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO type = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        RollerBlindActuator actuator = new RollerBlindActuator(name,type,deviceID);
        // Act
        DeviceIDVO result = actuator.getDeviceID();
        // Assert
        assertEquals(deviceID,result);
    }

    @Test
    void whenGetActuatorStatus_ReturnsDeviceStatusVOWithDefaultValue(){
        // Arrange
        ActuatorNameVO name = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO type = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        RollerBlindActuator actuator = new RollerBlindActuator(name,type,deviceID);
        String expected = "Default - 100";

        // Act
        String result = actuator.getActuatorStatus().getValue();
        // Assert
        assertEquals(expected,result);
    }

}