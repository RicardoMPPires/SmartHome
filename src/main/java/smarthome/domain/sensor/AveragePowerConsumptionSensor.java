package smarthome.domain.sensor;

import smarthome.domain.DomainID;
import smarthome.domain.sensor.externalservices.SensorExternalServices;
import smarthome.domain.sensor.sensorvalues.AveragePowerConsumptionValue;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class AveragePowerConsumptionSensor implements Sensor {

    private final SensorIDVO sensorID;
    private SensorNameVO sensorName;
    private final SensorTypeIDVO sensorTypeID;
    private final DeviceIDVO deviceID;

    /**
     * Constructor for the average power consumption sensor. It creates a new sensor with a random ID.
     * It throws an exception if any of the parameters is null.
     *
     * @param sensorName   the name of the sensor
     * @param deviceID     the ID of the device
     * @param sensorTypeID the type of the sensor
     */

    public AveragePowerConsumptionSensor(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        if (areParamsNull(sensorName, deviceID, sensorTypeID)) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
        this.sensorID = new SensorIDVO(UUID.randomUUID());
    }


    /**
     * Constructor for the average power consumption sensor.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorID     the ID of the sensor
     * @param sensorName   the name of the sensor
     * @param deviceID     the ID of the device
     * @param sensorTypeID the type of the sensor
     */
    public AveragePowerConsumptionSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Method to validate if the received parameters are null. It receives a SensorNameVO, SensorTypeIDVO and DeviceIDVO
     * and returns a boolean.
     *
     * @param sensorName   Sensor name
     * @param sensorTypeID SensorType ID
     * @param deviceID     Device ID
     * @return boolean
     */

    private boolean areParamsNull(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        return sensorName == null || deviceID == null || sensorTypeID == null;
    }

    /**
     * Retrieves a sensor reading within the specified time range using simulated hardware and a factory for creating sensor values.
     *
     * <p>
     * This method retrieves a sensor reading within the time range defined by the initial and final dates provided as parameters.
     * It utilizes simulated hardware to obtain the sensor value for the specified time range.
     * The sensor value is then used to create a SensorValueObject using the provided value factory.
     * </p>
     *
     * <p>
     * If the initial or final dates are not valid, an IllegalArgumentException is thrown with the message "Invalid date".
     * Additionally, if the simulated hardware reference is null, an IllegalArgumentException is thrown with the message "Invalid external service".
     * </p>
     *
     * <p>
     * If there are any issues with casting or value creation during the creation of the SensorValueObject by the factory,
     * the factory returns null, and this method returns null as well.
     * </p>
     *
     * @param initialDate The initial date of the time range for retrieving the sensor reading.
     * @param finalDate   The final date of the time range for retrieving the sensor reading.
     * @param simHardware The simulated hardware providing the sensor values.
     * @param valueFactory The factory for creating SensorValueObjects.
     * @return A SensorValueObject containing the sensor reading within the specified time range, or null if any issues occur during value creation.
     * @throws IllegalArgumentException If the initial or final dates are invalid, or if the simulated hardware reference is null.
     */
    public AveragePowerConsumptionValue getReading(String initialDate, String finalDate, SensorExternalServices simHardware, SensorValueFactory valueFactory) {
        if (!validateDate(initialDate, finalDate)) {
            throw new IllegalArgumentException("Invalid date");
        }
        if (simHardware == null || valueFactory == null) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        String simValue = simHardware.getValue(initialDate, finalDate);

        // If there is any issues with casting or value creation, the factory returns null
        return (AveragePowerConsumptionValue) valueFactory.createSensorValue(simValue,this.sensorTypeID);
    }

    /**
     * Validates the date. It receives an initial date and a final date and returns a boolean.
     *
     * @param initialDate Initial date
     * @param finalDate   Final date
     * @return boolean
     */

    private boolean validateDate(String initialDate, String finalDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime.parse(initialDate, formatter);
            LocalDateTime.parse(finalDate, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for SensorID;
     */
    @Override
    public SensorNameVO getSensorName() {
        return sensorName;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for SensorTypeID;
     */
    @Override
    public SensorTypeIDVO getSensorTypeID() {
        return sensorTypeID;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for DeviceID;
     */

    @Override
    public DeviceIDVO getDeviceID() {
        return deviceID;
    }

    /**
     * Simple getter method
     * It's an override from DomainEntity interface
     *
     * @return The encapsulated VO for SensorID;
     */

    @Override
    public DomainID getId() {
        return this.sensorID;
    }
}
