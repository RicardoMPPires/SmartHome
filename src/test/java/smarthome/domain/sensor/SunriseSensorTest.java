package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SunTimeCalculator;
import smarthome.domain.sensor.sensorvalues.*;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SunriseSensorTest {

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullNameVO_ThrowsIllegalArgument(){
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SunriseSensor(null, deviceID, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullDeviceID_ThrowsIllegalArgument(){
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SunriseSensor(sensorName, null, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullSensorTypeID_ThrowsIllegalArgument(){
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        String expected = "Parameters cannot be null";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SunriseSensor(sensorName, deviceID, null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test to check if the second constructor creates a sensor when the parameters are valid.
     */
    @Test
    void givenValidParameters_whenCreatingSensor_thenSensorIsCreated() {
        //Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorIDVO sensorIDDouble = mock(SensorIDVO.class);
        //Act
        SunriseSensor sunriseSensor = new SunriseSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, sunriseSensor.getId());
        assertEquals(sensorNameDouble, sunriseSensor.getSensorName());
        assertEquals(deviceIDDouble, sunriseSensor.getDeviceID());
        assertEquals(sensorTypeDouble, sunriseSensor.getSensorTypeID());
    }

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullSunTimeCalculator_ThrowsIllegalArgument(){
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);

        String date = "2024-03-20";
        String gpsLocation = "88.1579 : 77.6291";

        String expected = "Invalid parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunriseSensor sensor = new SunriseSensor(sensorName,deviceID,sensorTypeID);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(date, gpsLocation, null,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Verifies that the SunriseSensor correctly throws an IllegalArgumentException when a null value factory is provided.
     * This test checks if the SunriseSensor behaves as expected by throwing an IllegalArgumentException when invoked with
     * a null value factory parameter. It ensures that the sensor handles invalid input gracefully and maintains the
     * integrity of its functionality.
     * It mocks necessary dependencies such as SensorNameVO, DeviceIDVO, SensorTypeIDVO, and SunTimeCalculator to simulate
     * sensor initialization and calculation processes. The test constructs the SunriseSensor and invokes its getReading
     * method with a null value factory. The expected behavior is to throw an IllegalArgumentException. The test also
     * verifies that no SensorIDVO instances are constructed during this process.
     */
    @Test
    void givenNullValueFactory_ThrowsIllegalArgument(){
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SunriseSensor sensor = new SunriseSensor(sensorName,deviceID,sensorTypeID);
        SunTimeCalculator sunTimeCalculator = mock(SunTimeCalculator.class);

        String date = "2024-03-20";
        String gpsLocation = "88.1579 : 77.6291";

        String expected = "Invalid parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(date, gpsLocation, sunTimeCalculator,null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test case to verify that the SunriseSensor correctly retrieves the sunrise time value.
     *
     * <p>This test case simulates the behavior of the SunriseSensor class when provided with valid parameters:
     * sensor name, device ID, sensor type ID, date, GPS location, a SunTimeCalculator for sunrise calculation,
     * and a SensorValueFactoryImpl for creating sensor value objects. It verifies that the sensor retrieves
     * the sunrise time value correctly.</p>
     *
     * <p>The test case performs the following steps:</p>
     * <ol>
     *   <li>Mocks necessary objects including SensorNameVO, DeviceIDVO, SensorTypeIDVO, ZonedDateTime,
     *       SunTimeCalculator, SensorValueObject, and SensorValueFactoryImpl.</li>
     *   <li>Defines the date and GPS location for sunrise calculation.</li>
     *   <li>Mocks a ZonedDateTime object to simulate the sunrise time.</li>
     *   <li>Mocks the SunTimeCalculator to compute the sunrise time based on the provided date and GPS location.</li>
     *   <li>Mocks a SensorValueObject representing the sunrise time value.</li>
     *   <li>Mocks a SensorValueFactoryImpl to create sensor value objects.</li>
     *   <li>Defines the expected sunrise time value.</li>
     *   <li>Instantiates a SunriseSensor with the mocked objects.</li>
     *   <li>Invokes the getReading method of the SunriseSensor with the provided date, GPS location,
     *       SunTimeCalculator, and SensorValueFactoryImpl.</li>
     *   <li>Asserts that the returned SunriseValue object contains the expected sunrise time value.</li>
     * </ol>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_ReturnsValue(){
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String date = "2024-03-20";
        String gpsLocation = "88.1579 : 77.6291";

        ZonedDateTime zonedDateTimeDouble = mock(ZonedDateTime.class);
        when(zonedDateTimeDouble.toString()).thenReturn("2024-03-06T18:31:47Z[Europe/Lisbon]");

        SunTimeCalculator sunTimeCalculator = mock(SunTimeCalculator.class);
        when(sunTimeCalculator.computeSunrise(date,gpsLocation)).thenReturn(zonedDateTimeDouble);

        SensorValueObject value = mock(SunTimeValue.class);
        when(value.getValue()).thenReturn(zonedDateTimeDouble);

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        when(valueFactory.createSensorValue(zonedDateTimeDouble,sensorTypeID)).thenReturn(value);

        String expected = "2024-03-06T18:31:47Z[Europe/Lisbon]";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunriseSensor sensor = new SunriseSensor(sensorName,deviceID,sensorTypeID);

            SunTimeValue resultValue = sensor.getReading(date,gpsLocation,sunTimeCalculator,valueFactory);

            // Act
            String result = resultValue.getValue().toString();
            Object resultObj = sensor.getReading(date,gpsLocation,sunTimeCalculator,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            // Assert
            assertEquals(1,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(SunTimeValue.class,resultObj);
        }
    }

    /**
     * Verifies that the SunriseSensor returns a SunTimesValueObject when provided with valid parameters.
     * This test ensures that the SunriseSensor returns a SunTimesValueObject containing the expected sunrise time
     * when invoked with valid parameters. It verifies the correct behavior of the sensor in calculating sunrise
     * times based on the provided date and GPS location.
     * The System Under Test (SUT) is the SunriseSensor class. The test initializes a SunriseSensor object with a
     * SensorNameVO, DeviceIDVO, and SensorTypeIDVO representing the sensor's properties. It also creates a new
     * SunTimeCalculator instance to simulate sunrise time calculation. The sensor's getReading method is then called
     * with valid parameters: date, GPS location, SunTimeCalculator, and SensorValueFactoryImpl.
     * The test verifies that the returned value is an instance of SunTimeValue and that its value matches the expected
     * sunrise time.
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsSunTimesValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("SunriseSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SunriseSensor sensor = new SunriseSensor(sensorName, deviceID, sensorType);

        String expected = "2024-03-20T22:20:35Z[UTC]";
        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();

        String date = "2024-03-20";
        String gpsLocation = "88.1579 : 77.6291";

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");
        SunTimeValue sunValue = sensor.getReading(date,gpsLocation,sunTimeCalculator,valueFactory);

        // Act
        Object sunTimeValue = sensor.getReading(date,gpsLocation,sunTimeCalculator,valueFactory);
        String result = sunValue.getValue().toString();

        // Assert
        assertInstanceOf(SunTimeValue.class,sunTimeValue);
        assertEquals(expected,result);
    }
    /**
     * This test ensures the encapsulated VO's value is accessbile by calling getSensorName() : VO, then calling toString
     * on the same VO;
     */
    @Test
    void callingGetSensorNameToString_ReturnsNameVoAsString() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        when(sensorName.toString()).thenReturn("Test");

        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){

            SunriseSensor sensor = new SunriseSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorName().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected,result);
            assertEquals(1,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the encapsulated VO's value is accessbile by calling getSensorTypeID() : VO, then calling getID
     * again on the same VO to receive its value as String;
     */
    @Test
    void callingGetSensorTypeIDToString_ReturnsSensorTypeIDAsString() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        when(sensorTypeID.getID()).thenReturn("Test");

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){

            SunriseSensor sensor = new SunriseSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorTypeID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected,result);
            assertEquals(1,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the encapsulated VO's value is accessbile by calling getDeviceID() : VO, then calling getID
     * again on the same VO to receive its value as String;
     */
    @Test
    void callingGetDeviceIDWithgetID_ReturnsDeviceIDAsString() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(deviceID.getID()).thenReturn("Test");

        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);


        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){

            SunriseSensor sensor = new SunriseSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getDeviceID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected,result);
            assertEquals(1,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the encapsulated VO's value is accessbile by calling getID() : VO, then calling getID again
     * on the same VO to receive its value as String;
     */
    @Test
    void callingGetIDToGetSensorIDVOThenCallingGetIDAgainToGetString_ReturnsSensorIDAsString() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn(expected))) {

            SunriseSensor sensor = new SunriseSensor(sensorName, deviceID, sensorTypeID);
            // Act
            String result = sensor.getId().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected,result);
            assertEquals(1,listOfMockedSensorIDVO.size());
        }
    }
}
