package smarthome.domain.actuator;

import smarthome.domain.AggregateRoot;
import smarthome.domain.actuator.externalservices.ActuatorExternalService;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

import java.util.UUID;

public class RollerBlindActuator implements AggregateRoot, Actuator {

    private final ActuatorIDVO actuatorID;
    private final ActuatorTypeIDVO actuatorTypeID;
    private final DeviceIDVO deviceIDVO;
    private ActuatorNameVO actuatorName;
    private ActuatorStatusVO actuatorStatusVO;

    /**
     * Constructs a new RollerBlindActuator with the provided actuatorName, actuatorTypeID, and deviceIDVO. Throws an
     * IllegalArgumentException if any of the provided parameters is null.
     * Internally it generates its ID using a UUID and injecting that identifier in a ActuatorIDVO object.
     * @param actuatorName The name of the actuator.
     * @param actuatorTypeID The type ID of the actuator.
     * @param deviceIDVO The ID of the device associated with the actuator.
     * @throws IllegalArgumentException If any of the provided parameters is null.
     */
    public RollerBlindActuator(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceIDVO) {
        if (!parametersAreValid(actuatorName, actuatorTypeID, deviceIDVO)) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        this.actuatorID = new ActuatorIDVO(UUID.randomUUID());
        this.actuatorTypeID = actuatorTypeID;
        this.deviceIDVO = deviceIDVO;
        this.actuatorName = actuatorName;
        this.actuatorStatusVO = new ActuatorStatusVO("Default - 100");
    }

    /**
     * Constructs a new RollerBlindActuator object with the specified actuatorID, actuator name, type, device ID and the
     * settings interface. The input parameters were extracted from a DataModel of an existing actuator. Since the
     * DataModel is created from an existing actuator, it is considered that all the parameters are valid, since they
     * have been validated before persisting the actuator.
     */
    public RollerBlindActuator(ActuatorIDVO actuatorID, ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                               DeviceIDVO deviceIDVO, ActuatorStatusVO actuatorStatusVO) {
        this.actuatorID = actuatorID;
        this.actuatorTypeID = actuatorTypeID;
        this.deviceIDVO = deviceIDVO;
        this.actuatorName = actuatorName;
        this.actuatorStatusVO = actuatorStatusVO;
    }

    /**
     * Executes a command on the actuator hardware with the specified string value.
     * @param simHardwareAct the {@code ActuatorExternalService} instance to interact with the hardware.
     * @param value the string representation of the value to be sent as a command to the actuator hardware.
     * @return a string representing the command execution result:
     *         - "Invalid hardware, could not execute command" if the hardware is null.
     *         - "Invalid value, could not execute command" if the value cannot be parsed into an integer or does not
     *         pass validation.
     *         - "Hardware error: Value was not set" if there was an error setting the value on the hardware.
     *         - The original string value if the command was successfully executed and the value was set.
     */
    public String executeCommand(ActuatorExternalService simHardwareAct, String value){
        if (simHardwareAct == null) {
            throw new IllegalArgumentException("Invalid hardware, could not execute command");
        }

        // Attempts to parse value into double, in order to validate it and use as argument on the ExternalHardware
        int parsedValue;

        try {
            parsedValue = Integer.parseInt(value);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Unparseable value, could not execute command");
        }

        if (!validateCommand(parsedValue)) {
            throw new IllegalArgumentException("Invalid value, could not execute command");
        }

        if (!simHardwareAct.executeIntegerCommandSim(parsedValue)){
            throw new IllegalArgumentException("Hardware error: Value was not set");
        }
        this.actuatorStatusVO = new ActuatorStatusVO(value);
        return value;
    }

    /**
     * Simple getter method for the actuator ID.
     * @return The actuator ID.
     */
    @Override
    public ActuatorIDVO getId() {
        return this.actuatorID;
    }


    /**
     * Checks if the provided actuator parameters are valid.
     * @param actuatorName The name of the actuator.
     * @param actuatorTypeID The type ID of the actuator.
     * @param deviceIDVO The ID of the device associated with the actuator.
     * @return true if all parameters are not null, false otherwise.
     */
    private boolean parametersAreValid(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceIDVO) {
        return actuatorName != null && actuatorTypeID != null && deviceIDVO != null;
    }


    /**
     * Validates the command to move the roller blind to the specified position.
     * @param position The position to move the roller blind to.
     * @return true if the position is between 0 and 100, false otherwise.
     */
    private boolean validateCommand(int position) {
        return position >= 0 && position <= 100;
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
     * Method to retrieve the lower limit of the actuator.
     *
     * @return null since the RollerBlindActuator does not have a lower limit.
     */
    public String getLowerLimit() {
        return null;
    }

    /**
     * Method to retrieve the upper limit of the actuator.
     *
     * @return null since the RollerBlindActuator does not have an upper limit.
     */
    public String getUpperLimit() {
        return null;
    }

    /**
     * Method to retrieve the precision of the actuator.
     *
     * @return null since the RollerBlindActuator does not have a precision.
     */
    public String getPrecision() {
        return null;
    }
}
