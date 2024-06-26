package smarthome.domain.actuator.externalservices;

import org.springframework.stereotype.Component;

/**
 * SimHardwareAct serves as an abstraction representing the communication with an External Physical Device. It is often
 * used within the actuator implementations, however, in a real world scenario, the actuator class would never
 * communicate directly with an external device. The actuator would return a response which would then be received by
 * the actuator service who would call a specific class at the "Interface adapters layer" who would then call a gateway
 * or another logical structure at the "Framework and drivers layer" who will be tasked with knowing the API of the
 * external device.
 */

@Component
public class SimHardwareAct implements ActuatorExternalService{

    /**
     * This method is used to execute a command on the simulated hardware.
     * @return True if the command was executed successfully, false otherwise (at the moment, it is defined to be
     * always true, since hardware interaction is still unknown).
     */
    public boolean executeCommandSim() {
        return true;
    }

    /**
     * Simulates the execution of an integer command on the actuator hardware.
     *
     * @param position the integer value to be sent as a command to the actuator hardware.
     * @return True if the command was executed successfully, false otherwise (at the moment, it is defined to be
     * always true, since hardware interaction is still unknown).
     */
    public boolean executeIntegerCommandSim(int position) {
        return true;
    }

    /**
     * This method is used to execute a command on the simulated hardware.
     * @param value The value to be set
     * @return True if the command was executed successfully, false otherwise (at the moment, it is defined to be
     * always true, since hardware interaction is still unknown).
     */
    public boolean executeDecimalCommand(double value){
        return true;
    }


}
