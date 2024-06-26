package smarthome.domain.actuator;

import smarthome.domain.actuator.externalservices.ActuatorExternalService;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

import java.util.UUID;


/**
 * Represents a switch actuator that switches a
 load ON(true)/OFF(false).
 * This class implements both the DomainEntity and Actuator interfaces.
 */
public class SwitchActuator implements Actuator {

    private final ActuatorIDVO actuatorID;
    private ActuatorNameVO actuatorName;
    private final ActuatorTypeIDVO actuatorTypeID;
    private final DeviceIDVO deviceIDVO;
    private ActuatorStatusVO actuatorStatusVO;

    /**
     * Constructs a new SwitchActuator with the provided actuatorName, actuatorTypeID, and deviceIDVO.
     * Throws an IllegalArgumentException if any of the provided parameters is null.
     * Internally it generates it´s ID using a UUID and injecting that identifier in a ActuatorIDVO object.
     *
     * @param actuatorName   The name of the actuator.
     * @param actuatorTypeID The type ID of the actuator.
     * @param deviceIDVO     The ID of the device associated with the actuator.
     * @throws IllegalArgumentException If any of the provided parameters is null.
     */
    public SwitchActuator(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceIDVO) {
        if (!parametersAreValid(actuatorName, actuatorTypeID, deviceIDVO)) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        this.actuatorID = new ActuatorIDVO(UUID.randomUUID());
        this.actuatorName = actuatorName;
        this.actuatorTypeID = actuatorTypeID;
        this.deviceIDVO = deviceIDVO;
        this.actuatorStatusVO = new ActuatorStatusVO("Default: 1");
    }

    /**
     * Constructs a new SwitchActuator object with the specified actuatorID, actuator name, type, device ID and the
     * settings interface. The input parameters were extracted from a DataModel of an existing actuator. Since the
     * DataModel is created from an existing actuator, it is considered that all the parameters are valid, since they
     * have been validated before persisting the actuator.
     */
    public SwitchActuator(ActuatorIDVO actuatorID, ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                          DeviceIDVO deviceIDVO , ActuatorStatusVO actuatorStatusVO) {
        this.actuatorID = actuatorID;
        this.actuatorName = actuatorName;
        this.actuatorTypeID = actuatorTypeID;
        this.deviceIDVO = deviceIDVO;
        this.actuatorStatusVO = actuatorStatusVO;
    }

    /**
     * Checks if the provided actuator parameters are valid.
     *
     * @param actuatorName   The name of the actuator.
     * @param actuatorTypeID The type ID of the actuator.
     * @param deviceIDVO     The ID of the device associated with the actuator.
     * @return true if all parameters are not null, false otherwise.
     */
    private boolean parametersAreValid(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                                       DeviceIDVO deviceIDVO) {
        return actuatorName != null && actuatorTypeID != null && deviceIDVO != null;
    }

    /**
     * Executes a command on the actuator hardware with the specified string value.
     * @param simHardwareAct the {@code ActuatorExternalService} instance to interact with the hardware.
     * @param value the string representation of the value to be sent as a command to the actuator hardware.
     * @return a string representing the command execution result:
     *         - "Invalid hardware, could not execute command" if the hardware is null.
     *         - "Invalid value, could not execute command" if the value is invalid.
     *         - "Hardware error: Value was not set" if there was an error setting the value on the hardware.
     *         - The original string value if the command was successfully executed and the value was set.
     */
    public String executeCommand(ActuatorExternalService simHardwareAct, String value) {
        if (simHardwareAct == null) {
            throw new IllegalArgumentException("Invalid hardware, could not execute command");
        }
        if (!isCommandValid(value)){
            throw new IllegalArgumentException("Invalid value, could not execute command");
        }
        if (!simHardwareAct.executeIntegerCommandSim(Integer.parseInt(value))){
            throw new IllegalArgumentException("Hardware error: Value was not set");
        }
        this.actuatorStatusVO = new ActuatorStatusVO(value);
        return value;
    }

    /**
     * Validates the input string to determine if it can be parsed as an integer and if it is either 0 or 1.
     *
     * @param inputValue the string representation of the value to be validated.
     * @return {@code true} if the input string can be parsed as an integer and is either 0 or 1; {@code false}
     * otherwise.
     */
    private boolean isCommandValid (String inputValue){
        try{
            int value = Integer.parseInt(inputValue);
            return value == 1 || value == 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Retrieves the ID of the actuator.
     *
     * @return The ActuatorIDVO representing the ID of the actuator.
     */
    @Override
    public ActuatorIDVO getId() {
        return this.actuatorID;
    }

    /**
     * Simple getter method.
     * @return ActuatorTypeIDVO object
     */
    @Override
    public ActuatorTypeIDVO getActuatorTypeID() {
        return this.actuatorTypeID;
    }

    /**
     * Simple getter method.
     * @return DeviceIDVO object
     */
    @Override
    public DeviceIDVO getDeviceID() {
        return this.deviceIDVO;
    }

    /**
     * Simple getter method.
     * @return ActuatorName object
     */
    @Override
    public ActuatorNameVO getActuatorName() {
        return this.actuatorName;
    }

    /**
     * Retrieves the last saved status of the actuator.
     *
     * @return the current ActuatorStatusVO instance representing the status of the actuator.
     */
    @Override
    public ActuatorStatusVO getActuatorStatus() {
        return this.actuatorStatusVO;
    }

    /**
     * Method to retrieve the lower limit of the actuator as String.
     *
     * @return null since the SwitchActuator does not have a lower limit.
     */
    public String getLowerLimit() {
        return null;
    }

    /**
     * Method to retrieve the upper limit of the actuator as String.
     *
     * @return null since the SwitchActuator does not have an upper limit.
     */
    public String getUpperLimit() {
        return null;
    }

    /**
     * Method to retrieve the precision of the actuator as String.
     *
     * @return null since the SwitchActuator does not have a precision.
     */
    public String getPrecision() {
        return null;
    }
}

