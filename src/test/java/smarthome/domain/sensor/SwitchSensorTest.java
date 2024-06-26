package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.SwitchValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class SwitchSensorTest {

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullNameVO_ThrowsIllegalArgument() {
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters.";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SwitchSensor(null, deviceID, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullDeviceID_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters.";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SwitchSensor(sensorName, null, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullSensorTypeID_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        String expected = "Invalid parameters.";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new SwitchSensor(sensorName, deviceID, null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test to check if the second constructor creates a sensor when the parameters are valid
     */
    @Test
    void givenValidParameters_whenCreatingSensor_thenSensorIsCreated() {
        //Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorIDVO sensorIDDouble = mock(SensorIDVO.class);
        //Act
        SwitchSensor switchSensor = new SwitchSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, switchSensor.getId());
        assertEquals(sensorNameDouble, switchSensor.getSensorName());
        assertEquals(deviceIDDouble, switchSensor.getDeviceID());
        assertEquals(sensorTypeDouble, switchSensor.getSensorTypeID());
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

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorName().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
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

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorTypeID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
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

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getDeviceID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
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

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class, (mock, context)
                -> when(mock.getID()).thenReturn(expected))) {

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorTypeID);
            // Act
            String result = sensor.getId().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullSimHardware_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        String expected = "Invalid parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorTypeID);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(null,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(1, listOfMockedSensorIDVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * Verifies that if a null value factory is provided, an IllegalArgumentException is thrown.
     * <p>
     * This test validates the behavior of the SwitchSensor class when a null value factory is passed to the
     * getReading method. The method should throw an IllegalArgumentException with the message "Invalid external service".
     * The system under test (SUT) is the SwitchSensor class, and it is provided with a double SensorNameVO, DeviceIDVO,
     * SensorTypeIDVO, and SimHardware objects. The expected behavior is that the method throws an exception without
     * constructing any SensorIDVO objects.
     * </p>
     */
    @Test
    void givenNullValueFactory_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SimHardware simHardware = mock(SimHardware.class);
        String expected = "Invalid parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorTypeID);

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(simHardware,null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(1, listOfMockedSensorIDVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * Verifies that when given valid parameters, the getReading method of the SwitchSensor class
     * returns the expected SwitchValue object.
     * <p>
     * This test ensures that the getReading method of the SwitchSensor class, which is the system under test (SUT),
     * correctly returns a SwitchValue object when provided with valid parameters such as a simulated hardware
     * (double SimHardware object) and a value factory (double SensorValueFactoryImpl object). The expected value
     * returned by the method is "On".
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_ReturnsValue() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String reading = "On";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);
        String expected = "On";

        SensorValueObject value = mock(SwitchValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading,sensorType)).thenReturn(value);

            SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorType);

            //Act
            String result = sensor.getReading(simHardware,valueFactory).getValue();
            Object resultObj = sensor.getReading(simHardware,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(1,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(SwitchValue.class,resultObj);
        }
    }

    /**
     * Test case to verify that SwitchSensor returns a SwitchValue object when provided with valid parameters.
     *
     * <p>
     * This test ensures that the HumiditySensor class's {@code getReading} method returns a HumidityValue object when
     * invoked with valid parameters.
     * </p>
     *
     * <p>
     * The test arranges the scenario by creating instances of SensorNameVO, SensorTypeIDVO, and DeviceIDVO,
     * representing the sensor's name, type, and device ID, respectively. It then creates an instance of SwitchSensor
     * using these parameters. Additionally, it mocks the behavior of the SimHardware object to return a specific
     * reading when its getValue method is called. The test also creates an instance of SensorValueFactoryImpl to
     * provide value creation functionality.
     * </p>
     *
     * <p>
     * The test acts by invoking the {@code getReading} method of the SwitchSensor instance with the mocked
     * SimHardware and SensorValueFactoryImpl objects. It then verifies that the returned value is an instance of
     * SwitchValue and that its value matches the expected value.
     * </p>
     *
     * <p>
     * This test does not perform any assertions on the behavior of mocked objects or on the internal state of the
     * system under test. It focuses solely on the output of the {@code getReading} method.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsHumidityValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("SwitchSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SwitchSensor sensor = new SwitchSensor(sensorName, deviceID, sensorType);

        String reading = "On";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        String expected = "On";

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        SwitchValue valueResult = sensor.getReading(simHardware,valueFactory);
        String result = valueResult.getValue();

        // Assert
        assertInstanceOf(SwitchValue.class,valueResult);
        assertEquals(expected,result);
    }
}