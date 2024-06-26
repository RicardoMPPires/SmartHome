package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SunTimeCalculator;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.SunTimeValue;
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

/**
 * Test class for SunsetSensor
 */

class SunsetSensorTest {

    /**
     * This test ensures the constructor throws an IllegalArgumentException when given a null SensorNameVO.
     */

    @Test
    void givenNullName_whenCreatingSensor_thenThrowException() {
//        Arrange
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String expected = "Parameters cannot be null";
//            Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SunsetSensor(null, deviceIDDouble, sensorTypeDouble));
//            Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * This test ensures the constructor throws an IllegalArgumentException when given a null SensorTypeIDVO.
     */

    @Test
    void givenNullSensorType_whenCreatingSensor_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String expected = "Parameters cannot be null";
//            Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SunsetSensor(sensorNameDouble, deviceIDDouble, null));
//            Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * This test ensures the constructor throws an IllegalArgumentException when given a null DeviceIDVO.
     */

    @Test
    void givenNullDeviceID_whenCreatingSensor_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";
//            Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SunsetSensor(sensorNameDouble, null, sensorTypeDouble));
//            Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
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
        SunsetSensor sunsetSensor = new SunsetSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, sunsetSensor.getId());
        assertEquals(sensorNameDouble, sunsetSensor.getSensorName());
        assertEquals(deviceIDDouble, sunsetSensor.getDeviceID());
        assertEquals(sensorTypeDouble, sunsetSensor.getSensorTypeID());
    }

    /**
     * Tests the behavior when the sun time calculator is null during the attempt to retrieve the sunset time.
     * This test verifies that when the sun time calculator is null while attempting to retrieve the sunset time,
     * the method should throw an IllegalArgumentException with an appropriate error message indicating invalid
     * external service.
     * The test sets up the necessary mock objects for sensor name, device ID, sensor type, and value factory.
     * It then calls the getReading method of the SunsetSensor class with a null sun time calculator.
     * The test expects an IllegalArgumentException to be thrown, and it verifies that the exception message
     * matches the expected message and that no instances of SensorIDVO are constructed during the process.
     */
    @Test
    void givenNullSunTimeCalculator_whenGettingSunsetTime_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);

        String date = "2021-06-01";

        String gpsLocation = "40.7128 : 74.0060";

        String expected = "Invalid parameters";

        int expectedListSize = 1;

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunsetSensor sensor = new SunsetSensor(sensorNameDouble,deviceIDDouble,sensorTypeDouble);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(date, gpsLocation, null,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(expectedListSize,listOfMockedSensorIDVO.size());
        }
    }

    @Test
    void givenInvalidDate_whenGettingSunsetTime_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        SunTimeCalculator calculator = mock(SunTimeCalculator.class);

        String gpsLocation = "40.7128 : 74.0060";

        String expected = "Invalid parameters";

        int expectedListSize = 1;

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunsetSensor sensor = new SunsetSensor(sensorNameDouble,deviceIDDouble,sensorTypeDouble);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(null, gpsLocation, calculator,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(expectedListSize,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Tests the behavior when an invalid date is provided while attempting to retrieve the sunset time.
     * This test verifies that when an invalid date (null) is provided while attempting to retrieve the sunset time,
     * the method should throw an IllegalArgumentException with an appropriate error message indicating invalid
     * external service.
     * The test sets up the necessary mock objects for sensor name, device ID, sensor type, value factory, and
     * sun time calculator. It then calls the getReading method of the SunsetSensor class with a null date.
     * The test expects an IllegalArgumentException to be thrown, and it verifies that the exception message
     * matches the expected message and that no instances of SensorIDVO are constructed during the process.
     */
    @Test
    void givenInvalidGPS_whenGettingSunsetTime_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        SunTimeCalculator calculator = mock(SunTimeCalculator.class);

        String date = "2021-06-01";

        String expected = "Invalid parameters";

        int expectedListSize = 1;

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunsetSensor sensor = new SunsetSensor(sensorNameDouble,deviceIDDouble,sensorTypeDouble);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(date, null, calculator,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(expectedListSize,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Tests the behavior when a null value factory is provided while attempting to retrieve the sunset time.
     * This test verifies that when a null value factory is provided while attempting to retrieve the sunset time,
     * the method should throw an IllegalArgumentException with an appropriate error message indicating invalid
     * external service.
     * The test sets up the necessary mock objects for sensor name, device ID, sensor type, sun time calculator,
     * and value factory. It then calls the getReading method of the SunsetSensor class with a null value factory.
     * The test expects an IllegalArgumentException to be thrown, and it verifies that the exception message
     * matches the expected message and that no instances of SensorIDVO are constructed during the process.
     */

    @Test
    void givenNullValueFactory_whenGettingSunsetTime_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SunTimeCalculator calculator = mock(SunTimeCalculator.class);

        String date = "2021-06-01";
        String gpsLocation = "40.7128 : 74.0060";

        String expected = "Invalid parameters";

        int expectedListSize = 1;

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunsetSensor sensor = new SunsetSensor(sensorNameDouble,deviceIDDouble,sensorTypeDouble);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(date, gpsLocation, calculator,null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(expectedListSize,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Tests the behavior of the SunsetSensor class when valid parameters are provided to retrieve the sunset time.
     * This test verifies that when valid parameters are provided to the getReading method of the SunsetSensor class,
     * it returns a SunTimesValueObject containing the expected sunset time.
     * The test sets up the necessary parameters including a sensor name, device ID, sensor type, sun time calculator,
     * and value factory. It then calls the getReading method of the SunsetSensor class with valid parameters for date
     * and GPS location.
     * The System Under Test (SUT) includes the SunsetSensor class, along with its related value objects (SunTimesValueObject),
     * value factory (SensorValueFactoryImpl), and sun time calculator (SunTimeCalculator). These components are all involved
     * in computing and representing sunset times.
     * The test expects the method to return a SunTimesValueObject with the expected sunset time, and it verifies that
     * the returned object is an instance of SunTimesValueObject and that its value matches the expected sunset time.
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsSunTimesValueObject() {
//        Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("SunsetSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SunsetSensor sensor = new SunsetSensor(sensorName, deviceID, sensorType);

        String expected = "2021-06-01T14:29:07Z[UTC]";

        SunTimeCalculator sunTimeCalculator = new SunTimeCalculator();

        String date = "2021-06-01";
        String gpsLocation = "40.7128 : 74.0060";

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
     * Tests the behavior of the SunsetSensor class when valid parameters are provided to retrieve the sunset time.
     * This test verifies that when valid parameters are provided to the getReading method of the SunsetSensor class,
     * it returns a SunTimeValue object containing the expected sunset time.
     * The test sets up the necessary parameters including a sensor name, device ID, sensor type, date, and GPS location.
     * It mocks the behavior of the SunTimeCalculator to return a predefined sunset time. Additionally, it mocks the
     * behavior of the SensorValueFactoryImpl to return a SunTimeValue object based on the computed sunset time.
     * The System Under Test (SUT) is the SunsetSensor class, which is responsible for retrieving sunset times. It interacts
     * with the SunTimeCalculator and SensorValueFactoryImpl to compute and represent sunset times, respectively.
     * The test expects the method to return a SunTimeValue object with the expected sunset time, and it verifies that
     * the returned object is an instance of SunTimeValue and that its value matches the expected sunset time.
     */

    @Test
    void givenValidParameters_whenGettingSunsetTime_thenReturnSunTimeValue() {
        // Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);

        String date = "2021-06-01";
        String gpsLocation = "40.7128 : 74.0060";

        ZonedDateTime zonedDateTimeDouble = mock(ZonedDateTime.class);
        when(zonedDateTimeDouble.toString()).thenReturn("2021-06-01T20:00:00Z[Europe/Lisbon]");

        SunTimeCalculator sunTimeCalculator = mock(SunTimeCalculator.class);
        when(sunTimeCalculator.computeSunset(date,gpsLocation)).thenReturn(zonedDateTimeDouble);

        SensorValueObject value = mock(SunTimeValue.class);
        when(value.getValue()).thenReturn(zonedDateTimeDouble);

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        when(valueFactory.createSensorValue(zonedDateTimeDouble,sensorTypeDouble)).thenReturn(value);

        String expected = "2021-06-01T20:00:00Z[Europe/Lisbon]";

        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SunsetSensor sensor = new SunsetSensor(sensorNameDouble,deviceIDDouble,sensorTypeDouble);

            SunTimeValue resultValue = sensor.getReading(date,gpsLocation,sunTimeCalculator,valueFactory);

            // Act
            String result = resultValue.getValue().toString();
            Object resultObj = sensor.getReading(date,gpsLocation,sunTimeCalculator,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            // Assert
            assertEquals(expectedSize,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(SunTimeValue.class,resultObj);
        }
    }

    /**
     * This test ensures that the method getSensorName returns the correct value.
     * It also ensures that the method creates a new instance of SensorIDVO.
     */

    @Test
    void givenGetSensorName_whenGettingSensorName_thenReturnSensorNameAsString() {
//        Arrange
        String name = "SunsetSensor";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        when(sensorName.getValue()).thenReturn(name);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class)) {
            SunsetSensor sunsetSensor = new SunsetSensor(sensorName, deviceIDDouble, sensorTypeDouble);
//            Act
            String result = sunsetSensor.getSensorName().getValue();
//            Assert
            List<SensorIDVO> createdInstances = sensorIDVOMock.constructed();
            assertEquals(name, result);
            assertEquals(expectedSize, createdInstances.size());
        }
    }

    /**
     * This test ensures that the method getSensorTypeID returns the correct value.
     * It also ensures that the method creates a new instance of SensorIDVO.
     */

    @Test
    void givenGetSensorTypeID_whenGettingSensorTypeID_thenReturnSensorTypeIDAsString() {
//        Arrange
        String sensorTypeID = "SunsetSensor";
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        when(sensorType.getID()).thenReturn(sensorTypeID);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class)) {
            SunsetSensor sunsetSensor = new SunsetSensor(sensorNameDouble, deviceIDDouble, sensorType);
//            Act
            String result = sunsetSensor.getSensorTypeID().getID();
//            Assert
            List<SensorIDVO> createdInstances = sensorIDVOMock.constructed();
            assertEquals(sensorTypeID, result);
            assertEquals(expectedSize, createdInstances.size());
        }
    }

    /**
     * This test ensures that the method getDeviceID returns the correct value.
     * It also ensures that the method creates a new instance of SensorIDVO.
     */

    @Test
    void givenGetDeviceID_whenGettingDeviceID_thenReturnDeviceIDAsString() {
//        Arrange
        String deviceID = "SunsetSensor";
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn(deviceID);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class)) {
            SunsetSensor sunsetSensor = new SunsetSensor(sensorNameDouble, deviceIDVO, sensorTypeDouble);
//            Act
            String result = sunsetSensor.getDeviceID().getID();
//            Assert
            List<SensorIDVO> createdInstances = sensorIDVOMock.constructed();
            assertEquals(deviceID, result);
            assertEquals(expectedSize, createdInstances.size());
        }
    }

    /**
     * This test ensures that the method getID returns the correct value.
     * It also ensures that the method creates a new instance of SensorIDVO.
     */

    @Test
    void givenGetID_whenGettingID_thenReturnSensorIDAsString() {
//        Arrange
        String sensorID = "123";
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn(sensorID))) {
            SunsetSensor sunsetSensor = new SunsetSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);
//            Act
            String result = sunsetSensor.getId().getID();
//            Assert
            List<SensorIDVO> createdInstances = sensorIDVOMock.constructed();
            assertEquals(sensorID, result);
            assertEquals(expectedSize, createdInstances.size());
        }
    }
}