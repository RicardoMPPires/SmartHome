package smarthome.domain.actuator.externalservices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimHardwareActTest {

    /**
     * Test case to verify that the executeCommandSim method of SimHardwareAct returns true.

     * This test instantiates a SimHardwareAct object and invokes its executeCommandSim method.
     * It then asserts that the result is true, indicating successful execution of the command.

     */

    @Test
    void executeCommandSimShouldReturnTrue(){
        //Act
        SimHardwareAct simHardwareAct = new SimHardwareAct();
        boolean result = simHardwareAct.executeCommandSim();
        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the executeIntegerCommandSim method of SimHardwareAct returns true.

     * This test instantiates a SimHardwareAct object and invokes its executeIntegerCommandSim method.
     * It then asserts that the result is true, indicating successful execution of the command.

     */

    @Test
    void executeIntegerCommandSimShouldReturnTrue(){
        //Act
        SimHardwareAct simHardwareAct = new SimHardwareAct();
        boolean result = simHardwareAct.executeIntegerCommandSim(50);
        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify that the executeDecimalCommand() method of SimHardwareAct returns true.

     * This test instantiates a SimHardwareAct object and invokes the executeDecimalCommand() method.
     * It then asserts that the result is true, indicating successful execution of the command.
     */
    @Test
    void whenExecuteDecimalCommand_ThenShouldReturnTrue(){
        //Arrange
        SimHardwareAct simHardwareAct = new SimHardwareAct();
        double value = 44.44;
        //Act
        boolean result = simHardwareAct.executeDecimalCommand(value);
        //Assert
        assertTrue(result);
    }
}
