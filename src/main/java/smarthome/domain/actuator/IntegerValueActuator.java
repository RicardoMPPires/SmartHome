package smarthome.domain.actuator;

import smarthome.domain.actuator.externalservices.ActuatorExternalService;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

import java.util.UUID;

public class IntegerValueActuator implements Actuator {
    private ActuatorNameVO actuatorName;
    private final ActuatorTypeIDVO actuatorType;
    private final DeviceIDVO deviceID;
    private final ActuatorIDVO actuatorID;
    private final IntegerSettingsVO integerSettings;
    private ActuatorStatusVO actuatorStatusVO;

    /**
     * Constructs a new IntSetRangeActuator object with the specified actuator name, type, device ID and the settings
     * interface.
     * Validates the provided parameters to ensure they are not null.
     * Tries to cast the settings interface to an IntegerSettingsVO object.
     * Assigns a unique actuator ID using a randomly generated UUID.
     * This constructor is used for new instances. By default, it creates an actuatorStatus object with a default value.
     */
    public IntegerValueActuator(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorType, DeviceIDVO deviceID,
                                Settings settings){

        if(areParamsNull(actuatorName, actuatorType, deviceID, settings)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        try{
            this.integerSettings = (IntegerSettingsVO) settings;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid settings type");
        }
        this.actuatorName = actuatorName;
        this.actuatorType = actuatorType;
        this.deviceID = deviceID;
        this.actuatorID = new ActuatorIDVO(UUID.randomUUID());
        this.actuatorStatusVO = new ActuatorStatusVO("Initial reading");
    }

    /**
     * Constructs a new IntSetRangeActuator object with the specified actuatorID, actuator name, type, device ID and the
     * settings interface. The input parameters were extracted from a DataModel of an existing actuator. Since the
     * DataModel is created from an existing actuator, it is considered that all the parameters are valid, since they
     * have been validated before persisting the actuator.
     * Tries to cast the settings interface to an IntegerSettingsVO object.
     */
    public IntegerValueActuator(ActuatorIDVO actuatorID, ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorType,
                                DeviceIDVO deviceID, Settings settings, ActuatorStatusVO actuatorStatusVO) {
        this.actuatorID = actuatorID;
        this.actuatorName = actuatorName;
        this.actuatorType = actuatorType;
        this.deviceID = deviceID;
        this.integerSettings = (IntegerSettingsVO) settings;
        this.actuatorStatusVO = actuatorStatusVO;
    }

    /**
     * Executes a command on the actuator hardware with the specified string value.
     * @param simHardwareAct the {@code ActuatorExternalService} instance to interact with the hardware.
     * @param value the string representation of the value to be sent as a command to the actuator hardware.
     * @return a string representing the command execution result:
     *         - "Invalid hardware, could not execute command" if the hardware is null.
     *         - "Invalid value, could not execute command" if the value cannot be parsed into an integer or is not
     *         within limits.
     *         - "Hardware error: Value was not set" if there was an error setting the value on the hardware.
     *         - The original string value if the command was successfully executed and the value was set.
     */
    public String executeCommand(ActuatorExternalService simHardwareAct, String value) {
        if (simHardwareAct == null) {
            return "Invalid hardware, could not execute command";
        }

        // Attempts to parse value into double, in order to validate it and use as argument on the ExternalHardware
        int parsedValue;

        try {
            parsedValue = Integer.parseInt(value);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Unparseable value, could not execute command");
        }

        // Validates value is within limits
        if (!isValueWithinLimits(parsedValue)) {
            throw new IllegalArgumentException("Invalid value, could not execute command");
        }

        if (!simHardwareAct.executeIntegerCommandSim(parsedValue)) {
            throw new IllegalArgumentException("Hardware error: Value was not set");
        }
        this.actuatorStatusVO = new ActuatorStatusVO(value);
        return value;
    }



    /**
     * This method verifies if a given value is within the range of the pre-established limits for the present state of
     * the actuator.
     * @return true if the value is within the limits, false otherwise
     */
    private boolean isValueWithinLimits(int value){
        int lowerLimit = this.integerSettings.getValue()[0];
        int upperLimit = this.integerSettings.getValue()[1];
        return (value >= lowerLimit && value <= upperLimit);
    }

    /**
     * This method gets the actuator's ID.
     * @return the actuator ID object (ActuatorIDVO)
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
        return this.actuatorType;
    }

    /**
     * Simple getter method.
     * @return DeviceIDVO object
     */
    @Override
    public DeviceIDVO getDeviceID() {
        return this.deviceID;
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
     * This method verifies if a given set of parameters are null.
     * @return true if any of the parameters are null, false otherwise
     */
    private boolean areParamsNull(Object... params){
        for (Object param : params){
            if (param == null){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to retrieve the settings of the actuator.
     *
     * @return The IntegerSettingsVO of the actuator.
     */
    public IntegerSettingsVO getIntegerSettings() {
        return integerSettings;
    }

    /**
     * Method to retrieve the lower limit of the actuator as String.
     *
     * @return The lower limit of the actuator as String.
     */
    public String getLowerLimit() {
        return this.integerSettings.getValue()[0].toString();
    }

    /**
     * Method to retrieve the upper limit of the actuator as String.
     *
     * @return The upper limit of the actuator as String.
     */
    public String getUpperLimit() {
        return this.integerSettings.getValue()[1].toString();
    }

    /**
     * Method to retrieve the precision of the actuator as String.
     *
     * @return null since this actuator does not have a precision.
     */
    public String getPrecision() {
        return null;}

}
