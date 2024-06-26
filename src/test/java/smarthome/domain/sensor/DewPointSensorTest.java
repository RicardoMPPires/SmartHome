package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.DewPointValue;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

class DewPointSensorTest {

    /**
     * This test ensures the constructor throws an Illegal Argument Exception when given a null VO. This test also
     * attempts to create a mocked construction of the sensorID, however, as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullNameVO_ThrowsIllegalArgument() {
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new DewPointSensor(null, deviceID, sensorTypeID));
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
        String expected = "Parameters cannot be null";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new DewPointSensor(sensorName, null, sensorTypeID));
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
        String expected = "Parameters cannot be null";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new DewPointSensor(sensorName, deviceID, null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
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
        DewPointSensor dewPointSensor = new DewPointSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, dewPointSensor.getId());
        assertEquals(sensorNameDouble, dewPointSensor.getSensorName());
        assertEquals(deviceIDDouble, dewPointSensor.getDeviceID());
        assertEquals(sensorTypeDouble, dewPointSensor.getSensorTypeID());
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

            DewPointSensor sensor = new DewPointSensor(sensorName, deviceID, sensorTypeID);

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

            DewPointSensor sensor = new DewPointSensor(sensorName, deviceID, sensorTypeID);

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

            DewPointSensor sensor = new DewPointSensor(sensorName, deviceID, sensorTypeID);

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

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn(expected))) {

            DewPointSensor sensor = new DewPointSensor(sensorName, deviceID, sensorTypeID);
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
     * attempts to create a mocked construction of the sensorID, however, as can be observed, the mocked construction
     * does not get to be created as it does not reach that stage.
     */
    @Test
    void givenNullSimHardware_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        DewPointSensor sensor = new DewPointSensor(sensorName, deviceID, sensorTypeID);
        SensorValueFactoryImpl factory = mock(SensorValueFactoryImpl.class);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            sensor.getReading(null,factory));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when a null factory is provided to the getReading
     * method of the DewPointSensor class.
     *
     * <p>
     * This test sets up the necessary dependencies for creating a DewPointSensor instance, including mocked instances
     * of SensorNameVO, DeviceIDVO, SensorTypeIDVO, and SimHardware.
     * The DewPointSensor instance is then used to invoke the getReading method with a null factory parameter.
     * The test expects the method to throw an IllegalArgumentException with the message "Invalid parameters".
     * </p>
     *
     * <p>
     * The test verifies this behavior using assertThrows and compares the expected error message with the actual error
     * message thrown by the method.
     * </p>
     */
    @Test
    void givenNullFactory_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        DewPointSensor sensor = new DewPointSensor(sensorName, deviceID, sensorTypeID);
        SimHardware simHardware = mock(SimHardware.class);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensor.getReading(simHardware,null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that the getReading method of the DewPointSensor class returns the expected value when provided with valid parameters.
     *
     * <p>
     * This test focuses on the functionality of the DewPointSensor class, which acts as the System Under Test (SUT). It verifies that the getReading method of the DewPointSensor class correctly returns the expected value when provided with valid parameters.
     * </p>
     *
     * <p>
     * The test sets up the necessary dependencies for creating a DewPointSensor instance, including instances of SensorNameVO, SensorTypeIDVO, and DeviceIDVO, which are value objects (VOs).
     * It also uses a SimHardware object, which represents a simulated hardware interface, and a SensorValueFactoryImpl object, which is a factory responsible for creating SensorValueObject instances.
     * </p>
     *
     * <p>
     * The simulated hardware is configured to return a specific reading.
     * The value factory is set up using a properties file ("value.properties") containing configuration information.
     * </p>
     *
     * <p>
     * The getReading method of the DewPointSensor instance is then invoked with the simulated hardware and the value factory.
     * The test expects the method to return an instance of DewPointValue, which represents the sensor reading, and the value to match the expected value.
     * </p>
     *
     * <p>
     * The test verifies this behavior by asserting that the returned value is equal to the expected value and that its type is DewPointValue.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReading_thenReturnValue() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        DewPointSensor sensor = new DewPointSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);

        String reading = "95.5";

        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        double expected = 95.5;

        SensorValueObject value = mock(DewPointValue.class);
        when(value.getValue()).thenReturn(expected);

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        when(valueFactory.createSensorValue(reading,sensorTypeDouble)).thenReturn(value);

//      Act
        Object resultObj = sensor.getReading(simHardware,valueFactory);
        double result = sensor.getReading(simHardware,valueFactory).getValue();

//      Assert
        assertEquals(expected, result);
        assertInstanceOf(DewPointValue.class,resultObj);
    }

    /**
     * Test case to verify that the getReading method of the DewPointSensor class returns an instance of DewPointValue with the expected value when provided with valid parameters.
     *
     * <p>
     * This test focuses on the functionality of the DewPointSensor class, which acts as the System Under Test (SUT). It verifies that the getReading method of the DewPointSensor class correctly returns an instance of DewPointValue with the expected value when provided with valid parameters.
     * </p>
     *
     * <p>
     * The test sets up the necessary dependencies for creating a DewPointSensor instance, including instances of SensorNameVO, SensorTypeIDVO, and DeviceIDVO, which are value objects (VOs).
     * It also uses a SimHardware object, which represents a simulated hardware interface, and a SensorValueFactoryImpl object, which is a factory responsible for creating SensorValueObject instances.
     * </p>
     *
     * <p>
     * The simulated hardware is configured to return a specific reading.
     * The value factory is set up using a properties file ("value.properties") containing configuration information.
     * </p>
     *
     * <p>
     * The getReading method of the DewPointSensor instance is then invoked with the simulated hardware and the value factory.
     * The test expects the method to return an instance of DewPointValue, which represents the sensor reading, and the value to match the expected value.
     * </p>
     *
     * <p>
     * The test verifies this behavior by asserting that the type of the returned SensorValueObject is DewPointValue, and the value obtained from it matches the expected value.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReading_thenReturnAveragePowerConsumptionValueObject() {
//        Arrange
        SensorNameVO sensorNameDouble = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorTypeDouble = new SensorTypeIDVO("DewPointSensor");
        DeviceIDVO deviceIDDouble = new DeviceIDVO(UUID.randomUUID());
        DewPointSensor sensor = new DewPointSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);

        String reading = "95.5";

        double expected = 95.5;

        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

//      Act
        DewPointValue valueResult = sensor.getReading(simHardware,valueFactory);
        double result = valueResult.getValue();

//      Assert
        assertInstanceOf(DewPointValue.class,valueResult);
        assertEquals(expected,result);
    }
}
