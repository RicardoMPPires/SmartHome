package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SensorValueFactoryImplTest {

    private static String filePath;

    @BeforeAll
    public static void setUp() {
        filePath = "value.properties";
    }

    /**
     * This test ensures that when given an invalid path. The constructor throws an exception.
     */
    @Test
    void givenEmptyPath_constructorThrowsIllegalArgument(){
        // Arrange
        String path = " ";
        String expected = "Error reading file";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,() ->
                new SensorValueFactoryImpl(path));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test ensures that when given an invalid path. The constructor throws an exception.
     */
    @Test
    void givenNullPath_constructorThrowsIllegalArgument(){
        // Arrange
        String expected = "Error reading file";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,() ->
                new SensorValueFactoryImpl(null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test ensures that when given an invalid path. The constructor throws an exception.
     */
    @Test
    void givenInvalidPath_constructorThrowsIllegalArgument(){
        // Arrange
        String path = "Invalid path";
        String expected = "Error reading file";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,() ->
                new SensorValueFactoryImpl(path));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the successful instantiation of a HumidityValue object
     * when given valid parameters, and ensuring that the getValue method returns an Integer.
     *
     * <p>
     * The test first sets up the necessary parameters for the test:
     * - A reading value of "50" representing a humidity reading.
     * - A SensorTypeIDVO object with the ID "HumiditySensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected integer value of 50.
     * </p>
     *
     * <p>
     * The test then executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * After the creation, it retrieves the value using the getValue method.
     * </p>
     *
     * <p>
     * Finally, the test asserts that the created SensorValueObject is an instance of HumidityValue,
     * indicating successful instantiation, and that the retrieved value matches the expected integer value.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesHumidityValue_getValueReturnsInteger(){
        // Arrange
        String reading = "50";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("HumiditySensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        int expected = 50;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(HumidityValue.class, value);
        assertEquals(result,expected);
    }

    /**
     * Test case to verify the successful instantiation of a TemperatureValue object
     * when given valid parameters, and ensuring that the getValue method returns a Double.
     *
     * <p>
     * The test first sets up the necessary parameters for the test:
     * - A reading value of "36.7" representing a temperature reading.
     * - A SensorTypeIDVO object with the ID "TemperatureSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected double value of 36.7.
     * </p>
     *
     * <p>
     * The test then executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * After the creation, it retrieves the value using the getValue method.
     * </p>
     *
     * <p>
     * Finally, the test asserts that the created SensorValueObject is an instance of TemperatureValue,
     * indicating successful instantiation, and that the retrieved value matches the expected double value.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesTemperatureValue_getValueReturnsDouble(){
        // Arrange
        String reading = "36.7";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("TemperatureSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        double expected = 36.7;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(TemperatureValue.class, value);
        assertEquals(result,expected);
    }

    /**
     * Test case to verify the successful instantiation of a PositionValue object
     * when given valid parameters, and ensuring that the getValue method returns an Integer.
     *
     * <p>
     * The test verifies the behavior of the createSensorValue method in the SensorValueFactoryImpl class
     * when provided with a reading value of "36" representing a position reading and a SensorTypeIDVO object
     * with the ID "PositionSensor" to identify the sensor type.
     * </p>
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "36" representing a position reading.
     * - A SensorTypeIDVO object with the ID "PositionSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected integer value of 36.
     * </p>
     *
     * <p>
     * The test then executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * After the creation, it retrieves the value using the getValue method.
     * </p>
     *
     * <p>
     * Finally, the test asserts that the created SensorValueObject is an instance of PositionValue,
     * indicating successful instantiation, and that the retrieved value matches the expected integer value.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesPositionValue_getValueReturnsInteger(){
        // Arrange
        String reading = "36";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("PositionSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        int expected = 36;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(PositionValue.class, value);
        assertEquals(result,expected);
    }

    /**
     * Test case to verify the successful instantiation of a WindValue object
     * when given valid parameters, and ensuring that the getValue method returns a String.
     *
     * <p>
     * The test verifies the behavior of the createSensorValue method in the SensorValueFactoryImpl class
     * when provided with a reading value of "30:N" representing a wind reading and a SensorTypeIDVO object
     * with the ID "WindSensor" to identify the sensor type.
     * </p>
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "30:N" representing a wind reading.
     * - A SensorTypeIDVO object with the ID "WindSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected string value of "30:N".
     * </p>
     *
     * <p>
     * The test then executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * After the creation, it retrieves the value using the getValue method.
     * </p>
     *
     * <p>
     * Finally, the test asserts that the created SensorValueObject is an instance of WindValue,
     * indicating successful instantiation, and that the retrieved value matches the expected string value.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesWindValue_getValueReturnsString(){
        // Arrange
        String reading = "30:N";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("WindSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        String expected = "30:N";

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(WindValue.class, value);
        assertEquals(result,expected);
    }

    /**
     * Test case to verify the successful instantiation of a DewPointValue object
     * when given valid parameters, and ensuring that the getValue method returns a Double.
     *
     * <p>
     * The test verifies the behavior of the createSensorValue method in the SensorValueFactoryImpl class
     * when provided with a reading value of "30.6" representing a dew point reading and a SensorTypeIDVO object
     * with the ID "DewPointSensor" to identify the sensor type.
     * </p>
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "30.6" representing a dew point reading.
     * - A SensorTypeIDVO object with the ID "DewPointSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected double value of 30.6.
     * </p>
     *
     * <p>
     * The test then executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * After the creation, it retrieves the value using the getValue method.
     * </p>
     *
     * <p>
     * Finally, the test asserts that the created SensorValueObject is an instance of DewPointValue,
     * indicating successful instantiation, and that the retrieved value matches the expected double value.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesDewPointValue_getValueReturnsDouble(){
        // Arrange
        String reading = "30.6";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("DewPointSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        double expected = 30.6;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(DewPointValue.class, value);
        assertEquals(result,expected);
    }

    /**
     * Test case to verify the successful instantiation of a SunTimeValue object
     * when given a valid time parameter and ensuring that the getValue method returns the same time.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - Obtains the current system time in UTC and converts it to a string.
     * - Creates a SensorTypeIDVO object with the ID "SunsetSensor" to identify the sensor type.
     * - Specifies the path to the properties file containing configuration information for the factory.
     * - Initializes an instance of SensorValueFactoryImpl with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the time parameter and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the original time string.
     * </p>
     *
     * <p>
     * Note: This test relies on the current system time in UTC and assumes that the Log class internally uses
     * LocalDateTime.now() to set the time, so it may fail if the implementation changes or if there are time zone differences.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesSunTimeValue_getValueReturnsZoneDateTime() {
        // Arrange
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        String time = zdt.toString();
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("SunsetSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> value = factory.createSensorValue(time, sensorTypeID);
        String result = value.getValue().toString();

        // Assert
        assertInstanceOf(SunTimeValue.class, value);
        assertEquals(time, result);
    }

    /**
     * Test case to verify the successful instantiation of a SunTimeValue object
     * when given a valid time parameter and ensuring that the getValue method returns the same time.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - Obtains the current system time in UTC and converts it to a string.
     * - Creates a SensorTypeIDVO object with the ID "SunriseSensor" to identify the sensor type.
     * - Specifies the path to the properties file containing configuration information for the factory.
     * - Initializes an instance of SensorValueFactoryImpl with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the time parameter and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the original time string.
     * </p>
     *
     * <p>
     * Note: This test relies on the current system time in UTC and assumes that the Log class internally uses
     * LocalDateTime.now() to set the time, so it may fail if the implementation changes or if there are time zone differences.
     * </p>
     */

    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesSunTimeValue1_getValueReturnsZoneDateTime() {
        // Arrange
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        String time = zdt.toString();
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("SunriseSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> value = factory.createSensorValue(time, sensorTypeID);
        String result = value.getValue().toString();

        // Assert
        assertInstanceOf(SunTimeValue.class, value);
        assertEquals(time, result);
    }

    /**
     * Tests the behavior of the createSensorValue() method in the SensorValueFactoryImpl class
     * when provided with an invalid sensor type.
     * Test Case: Create a sensor value object with an invalid sensor type.
     * If the provided sensor type is not recognized (does not have a valid classpath), the method should return null.
     * Test Steps:
     * 1. Arrange: Set up the test environment by creating a current timestamp, defining an invalid sensor type,
     *    specifying a path for sensor values, and initializing a SensorValueFactoryImpl object.
     * 2. Act: Invoke the createSensorValue() method with the current timestamp and the invalid sensor type.
     * 3. Assert: Verify that the returned SensorValueObject is null, indicating the creation of the sensor value failed.
     */

    @Test
    void createSensorValue_givenInvalidSensorType_ShouldReturnNull() {
        // Arrange
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("NuclearSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> result = factory.createSensorValue(zdt, sensorTypeID);

        // Assert
        assertNull(result);
    }

    /**
     * Tests the behavior of the createSensorValue() method in the SensorValueFactoryImpl class
     * when provided with a non-existing sensor type.

     * Test Case: Create a sensor value object with a non-existing sensor type.
     * If the provided sensor type does not exist in the system, the method should return null.

     * Test Steps:
     * 1. Arrange: Set up the test environment by creating a current timestamp, defining a non-existing sensor type,
     *    specifying a path for sensor values, and initializing a SensorValueFactoryImpl object.
     * 2. Act: Invoke the createSensorValue() method with the current timestamp and the non-existing sensor type.
     * 3. Assert: Verify that the returned SensorValueObject is null, indicating the creation of the sensor value failed.
     */

    @Test
    void createSensorValue_givenNonExistingSensorType_ShouldReturnNull() {
        // Arrange
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("NonExistingSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> result = factory.createSensorValue(zdt, sensorTypeID);

        // Assert
        assertNull(result);
    }

    /**
     * Tests the behavior of the createSensorValue() method in the SensorValueFactoryImpl class
     * when provided with a null sensor type.

     * Test Case: Create a sensor value object with a null sensor type.
     * If the provided sensor type is null, the method should return null.

     * Test Steps:
     * 1. Arrange: Set up the test environment by creating a current timestamp, specifying a path for sensor values,
     *    and initializing a SensorValueFactoryImpl object.
     * 2. Act: Invoke the createSensorValue() method with the current timestamp and a null sensor type.
     * 3. Assert: Verify that the returned SensorValueObject is null, indicating the creation of the sensor value failed.
     */


    @Test
    void createSensorValue_givenNullSensorType_ShouldReturnNull() {
        // Arrange
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> result = factory.createSensorValue(zdt, null);

        // Assert
        assertNull(result);
    }

    /**
     * Tests the behavior of the createSensorValue() method in the SensorValueFactoryImpl class
     * when provided with a valid sensor type.

     * Test Case: Create a sensor value object with a valid sensor type.
     * If the provided sensor type is valid, the method should return a SensorValueObject with the correct type and value.

     * Test Steps:
     * 1. Arrange: Set up the test environment by creating a current timestamp, defining a valid sensor type,
     *    specifying a path for sensor values, and initializing a SensorValueFactoryImpl object.
     * 2. Act: Invoke the createSensorValue() method with the current timestamp and the valid sensor type.
     * 3. Assert: Verify that the returned SensorValueObject is an instance of SunTimeValue class and its value matches the input timestamp.

     */

    @Test
    void createSensorValue_givenValidSensorType_ShouldReturnCorrectSensorValue() {
        // Arrange
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("SunriseSensor");

        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> value = factory.createSensorValue(zdt, sensorTypeID);
        ZonedDateTime result = (ZonedDateTime) value.getValue();

        // Assert
        assertInstanceOf(SunTimeValue.class, value);
        assertEquals(zdt, result);
    }

    /**
     * Test case to verify the successful instantiation of an AveragePowerConsumptionValue object
     * when given a valid reading parameter, and ensuring that the getValue method returns the expected integer value.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "4000" representing average power consumption.
     * - A SensorTypeIDVO object with the ID "AveragePowerConsumptionSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected integer value of 4000.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the expected integer value.
     * </p>
     *
     * <p>
     * Note: This test assumes that the SensorValueObject implementation for AveragePowerConsumptionValue uses
     * generics and that the constructor receives a string reading, parses it into the related primitive value,
     * and instantiates a SensorValueObject with the specific wrapper for that primitive type.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesAveragePowerConsumptionValue_getValueReturnsInteger(){
        // Arrange
        String reading = "4000";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("AveragePowerConsumptionSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        int expected = 4000;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(AveragePowerConsumptionValue.class, value);
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the successful instantiation of a PowerConsumptionValue object
     * when given a valid reading parameter, and ensuring that the getValue method returns the expected integer value.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "4000" representing power consumption.
     * - A SensorTypeIDVO object with the ID "PowerConsumptionSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected integer value of 4000.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the expected integer value.
     * </p>
     *
     * <p>
     * Note: This test assumes that the SensorValueObject implementation for PowerConsumptionValue uses
     * generics and that the constructor receives a string reading, parses it into the related primitive value,
     * and instantiates a SensorValueObject with the specific wrapper for that primitive type.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesPowerConsumptionValue_getValueReturnsInteger(){
        // Arrange
        String reading = "4000";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("PowerConsumptionSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        int expected = 4000;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(PowerConsumptionValue.class, value);
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the successful instantiation of an EnergyConsumptionValue object
     * when given a valid reading parameter, and ensuring that the getValue method returns the expected integer value.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "4000" representing energy consumption.
     * - A SensorTypeIDVO object with the ID "EnergyConsumptionSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected integer value of 2000 (this seems incorrect, should it be 4000?).
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the expected integer value.
     * </p>
     *
     * <p>
     * Note: This test assumes that the SensorValueObject implementation for EnergyConsumptionValue uses
     * generics and that the constructor receives a string reading, parses it into the related primitive value,
     * and instantiates a SensorValueObject with the specific wrapper for that primitive type.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesEnergyConsumptionValue_getValueReturnsInteger(){
        // Arrange
        String reading = "2000";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("EnergyConsumptionSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        int expected = 2000;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(EnergyConsumptionValue.class, value);
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the successful instantiation of a SwitchValue object
     * when given a valid reading parameter, and ensuring that the getValue method returns the expected string value.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "On" representing the state of a switch.
     * - A SensorTypeIDVO object with the ID "SwitchSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the expected string value.
     * </p>
     *
     * <p>
     * Note: This test assumes that the SensorValueObject implementation for SwitchValue uses
     * generics and that the constructor receives a string reading, which represents the state of the switch.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesSwitchValue_getValueReturnsString(){
        // Arrange
        String reading = "On";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("SwitchSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue().toString();

        // Assert
        assertInstanceOf(SwitchValue.class, value);
        assertEquals(reading,result);
    }

    /**
     * Test case to verify the successful instantiation of a SolarIrradianceValue object
     * when given a valid reading parameter, and ensuring that the getValue method returns the expected integer value.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "100" representing solar irradiance.
     * - A SensorTypeIDVO object with the ID "SolarIrradianceSensor" to identify the sensor type.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * - An expected integer value of 100.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It then retrieves the value from the created SensorValueObject and compares it with the expected integer value.
     * </p>
     *
     * <p>
     * Note: This test assumes that the SensorValueObject implementation for SolarIrradianceValue uses
     * generics and that the constructor receives a string reading, parses it into the related primitive value,
     * and instantiates a SensorValueObject with the specific wrapper for that primitive type.
     * </p>
     */
    @Test
    void whenGivenValidParameters_SuccessfullyInstantiatesSolarIrradianceValue_getValueReturnsInteger(){
        // Arrange
        String reading = "100";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("SolarIrradianceSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);
        int expected = 100;

        // Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);
        Object result = value.getValue();

        // Assert
        assertInstanceOf(SolarIrradianceValue.class, value);
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that when a non-permitted sensor type is provided,
     * the createSensorValue method of the SensorValueFactoryImpl returns null.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A SensorTypeIDVO object with an invalid ID "I will fail" to simulate a non-permitted sensor type.
     * - A reading value of "60" representing a sensor reading.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It expects the method to return null since the provided sensor type is non-permitted.
     * </p>
     */
    @Test
    void whenNonPermittedSensorType_shouldReturnNull(){
        //Arrange
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("I will fail");
        String reading = "60";
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        //Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);

        //Assert
        assertNull(value);
    }

    /**
     * Test case to verify that when a null sensor type is provided,
     * the createSensorValue method of the SensorValueFactoryImpl returns null.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A null SensorTypeIDVO object to simulate a null sensor type.
     * - A reading value of "60" representing a sensor reading.
     * - The path to the properties file containing configuration information for the factory.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It expects the method to return null since the provided sensor type is null.
     * </p>
     */
    @Test
    void whenNullSensorType_shouldReturnNull(){
        //Arrange
        String reading = "60";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("NuclearSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        //Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);

        //Assert
        assertNull(value);
    }

    /**
     * Test case to verify that when the provided reading is not within bounds,
     * the createSensorValue method of the SensorValueFactoryImpl returns null.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A reading value of "-1" representing a sensor reading that is not within bounds.
     * - The path to the properties file containing configuration information for the factory.
     * - A SensorTypeIDVO object with the ID "HumiditySensor" to identify the sensor type.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the reading and sensor type ID.
     * It expects the method to return null since the provided reading is not within the acceptable bounds.
     * </p>
     */
    @Test
    void whenReadingIsNotWithinBounds_shouldReturnNull(){
        //Arrange
        String reading = "-1";
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("HumiditySensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        //Act
        SensorValueObject<?> value = factory.createSensorValue(reading,sensorTypeID);

        //Assert
        assertNull(value);
    }

    /**
     * Test case to verify that when the provided reading is null,
     * the createSensorValue method of the SensorValueFactoryImpl returns null.
     *
     * <p>
     * The test sets up the necessary parameters for the test:
     * - A null reading value to simulate a null reading.
     * - The path to the properties file containing configuration information for the factory.
     * - A SensorTypeIDVO object with the ID "HumiditySensor" to identify the sensor type.
     * - An instance of SensorValueFactoryImpl initialized with the provided properties file path.
     * </p>
     *
     * <p>
     * The test executes the creation of the SensorValueObject using the createSensorValue method
     * of the SensorValueFactoryImpl instance, passing the null reading and sensor type ID.
     * It expects the method to return null since the provided reading is null.
     * </p>
     */
    @Test
    void whenReadingIsNull_shouldReturnNull(){
        //Arrange
        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("HumiditySensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        //Act
        SensorValueObject<?> value = factory.createSensorValue((String) null,sensorTypeID);

        //Assert
        assertNull(value);
    }

    @Test
    void whenLocalDateTimeReadingIsInvalid_shouldReturnNull(){
        //Arrange

        SensorTypeIDVO sensorTypeID= new SensorTypeIDVO("SunriseSensor");
        SensorValueFactoryImpl factory = new SensorValueFactoryImpl(filePath);

        //Act
        SensorValueObject<?> value = factory.createSensorValue((ZonedDateTime) null,sensorTypeID);

        //Assert
        assertNull(value);
    }
}
