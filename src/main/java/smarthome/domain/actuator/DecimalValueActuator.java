package smarthome.domain.actuator;

import smarthome.domain.DomainID;
import smarthome.domain.actuator.externalservices.ActuatorExternalService;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

import java.util.UUID;

public class DecimalValueActuator implements Actuator {
    private final ActuatorIDVO actuatorID;
    private ActuatorNameVO actuatorName;
    private final ActuatorTypeIDVO actuatorTypeID;
    private final DeviceIDVO deviceIDVO;
    private final DecimalSettingsVO decimalSettings;
    private ActuatorStatusVO actuatorStatusVO;


    /**
     * Constructor for the DecimalValueActuator. This constructor is used for new instances. By default, it creates
     * an actuatorStatus object with a default value.
     * @param actuatorName ActuatorName Value Object
     * @param actuatorTypeID ActuatorTypeID Value Object
     * @param deviceID DeviceID Value Object
     * @param settings Settings Value Object
     * @throws IllegalArgumentException If any of the parameters is invalid (null).
     */
    public DecimalValueActuator(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                                DeviceIDVO deviceID, Settings settings)  {

        if (!validParameters(actuatorName, actuatorTypeID, deviceID, settings)) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        try{
            this.decimalSettings = (DecimalSettingsVO) settings;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid settings type");
        }

        this.actuatorID = new ActuatorIDVO(UUID.randomUUID());
        this.actuatorName = actuatorName;
        this.actuatorTypeID = actuatorTypeID;
        this.deviceIDVO = deviceID;
        this.actuatorStatusVO = new ActuatorStatusVO("Initial reading");
    }

    /**
     * Constructs a new DecimalValueActuator object with the specified actuatorID, actuator name, type, device ID and
     * the settings interface. The input parameters were extracted from a DataModel of an existing actuator. Since the
     * DataModel is created from an existing actuator, it is considered that all the parameters are valid, since they
     * have been validated before persisting the actuator.
     * Tries to cast the settings interface to an DecimalSettingsVO object.
     */
    public DecimalValueActuator(ActuatorIDVO actuatorId, ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID,
                                DeviceIDVO deviceID, Settings settings, ActuatorStatusVO actuatorStatusVO) {

        this.actuatorID = actuatorId;
        this.actuatorName = actuatorName;
        this.actuatorTypeID = actuatorTypeID;
        this.deviceIDVO = deviceID;
        this.decimalSettings = (DecimalSettingsVO) settings;
        this.actuatorStatusVO = actuatorStatusVO;
    }

    /**
     * Verifies if all the parameters for the actuator's instantiation are valid (not null).
     * @param parameters ActuatorNameVO, ActuatorTypeIDVO, DeviceIDVO and DecimalSettingsVO
     * @return True if all parameters are valid (not null), false otherwise.
     */
    private boolean validParameters(Object... parameters) {
        for(Object parameter : parameters){
            if(parameter == null)
                return false;
        }
        return true;
    }

    /**
     * Executes a command on the actuator hardware with the specified string value.
     * @param hardware the {@code ActuatorExternalService} instance to interact with the hardware.
     * @param value the string representation of the value to be sent as a command to the actuator hardware.
     * @return a string representing the command execution result:
     *         - "Invalid hardware, could not execute command" if the hardware is null.
     *         - "Invalid value, could not execute command" if the value cannot be parsed into a double.
     *         - "Value out of actuator limits, could not execute command" if the parsed value is outside acceptable
     *         limits.
     *         - "Hardware error: Value was not set" if there was an error setting the value on the hardware.
     *         - "Value was rounded and set to X" if the value was rounded and successfully set.
     *         - The original string value if the command was successfully executed and the value was set.
     */
    public String executeCommand(ActuatorExternalService hardware, String value) {
        if(hardware == null)
            throw new IllegalArgumentException("Invalid hardware, could not execute command");

        // Attempts to parse value into double, in order to validate it and use as argument on ExternalHardware method
        double parsedValue;

        try {
            parsedValue = Double.parseDouble(value);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Unparseable value, could not execute command");
        }

        if (!isValueWithinLimits(parsedValue))
            throw new IllegalArgumentException("Value out of actuator limits, could not execute command");

        // If required, input value is rounded up to two decimal places
        double precision = decimalSettings.getValue()[2];
        if(!validValuePrecision(parsedValue, precision)) {
            parsedValue = round(parsedValue, countDecimalPlaces(precision));
        }

        if(!hardware.executeDecimalCommand(parsedValue)) {
            throw new IllegalArgumentException("Hardware error: Value was not set");
        }

        // This line is required to ensure that if the value is rounded, the correct String is saved
        String parsedValueAsString = String.valueOf(parsedValue);

        this.actuatorStatusVO = new ActuatorStatusVO(parsedValueAsString);
        return parsedValueAsString;
    }

    /**
     * Verifies if the value passed for the executeCommand() method is valid (within actuator limits).
     * @param value Value passed in the command
     * @return True if value is within limits.
     */
    private boolean isValueWithinLimits(double value) {
        double lowerLim = decimalSettings.getValue()[0];
        double upperLim = decimalSettings.getValue()[1];
        return (value >= lowerLim && value <= upperLim);
    }

    /**
     * Rounds a double value for a certain number of decimal places.
     * @param value Value to be rounded
     * @param decimalPlaces Number of decimal places desired
     * @return The rounded number with the required amount of decimal places.
     */
    private static double round(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }


    /**
     * Verifies if a given value respects the actuator precision.
     * @param value Value passed in the command
     * @param precision Precision defined for the actuator
     * @return True if the value precision granularity is valid, false otherwise (value precision is higher than the
     * actuator's defined precision)
     */
    private boolean validValuePrecision(double value, double precision) {
        return (countDecimalPlaces(value) <= countDecimalPlaces(precision));
    }

    /**
     * Calculates the number of decimal places of a double value.
     * In case the value introduced does not have decimal places, it implicitly has one decimal place (e.g. value
     * introduced: 5, it is interpreted as 5.0).
     * @param value Value to be analysed
     * @return The number of decimal places of the double value
     */
    private int countDecimalPlaces(double value) {
        String numberAsString = Double.toString(Math.abs(value));
        int decimalSeparatorIndex = numberAsString.indexOf('.');
        return numberAsString.length() - decimalSeparatorIndex - 1;
    }

    /**
     * Simple getter method to retrieve the Actuator ID.
     * @return The ActuatorIDVO of the actuator.
     */
    @Override
    public DomainID getId () {
        return this.actuatorID;
    }

    /**
     * Simple getter method to retrieve the Actuator Type ID.
     * @return The ActuatorTypeIDVO of the actuator.
     */
    @Override
    public ActuatorTypeIDVO getActuatorTypeID() {
        return this.actuatorTypeID;
    }

    /**
     * Simple getter method to retrieve the Device ID where the actuator is linked.
     * @return The DeviceIDVO where the actuator is connected to.
     */
    @Override
    public DeviceIDVO getDeviceID() {
        return this.deviceIDVO;
    }

    /**
     * Simple getter method to retrieve the actuator name.
     * @return The ActuatorNameVO of the actuator.
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
     * Method to retrieve the settings of the actuator.
     *
     * @return The DecimalSettingsVO of the actuator.
     */
    public DecimalSettingsVO getDecimalSettings() {
        return this.decimalSettings;
    }

    /**
     * Method to retrieve the lower limit of the actuator as String.
     *
     * @return The lower limit of the actuator as String.
     */
    public String getLowerLimit() {
        return decimalSettings.getValue()[0].toString();
    }

    /**
     * Method to retrieve the upper limit of the actuator as String.
     *
     * @return The upper limit of the actuator as String.
     */
    public String getUpperLimit() {
        return decimalSettings.getValue()[1].toString();
    }

    /**
     * Method to retrieve the precision of the actuator as String.
     *
     * @return The precision of the actuator as String.
     */
    public String getPrecision() {
        return decimalSettings.getValue()[2].toString();}
}



