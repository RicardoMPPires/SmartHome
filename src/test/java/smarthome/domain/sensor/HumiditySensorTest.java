package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.*;
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

class HumiditySensorTest {

    /**
     * This test verifies if the constructor throws an IllegalArgumentException when the sensor name is null.
     * First, the sensor name is set to null, then the expected message is set to "Parameters cannot be null".
     * Then, the constructor is called with the sensor name as a parameter, and the exception is thrown.
     * After that, the exception message is compared to the expected message.
     */
    @Test
    void whenSensorNameIsNull_throwsIllegalArgumentException() {
        //Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new HumiditySensor(null, deviceID, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test verifies if the constructor throws an IllegalArgumentException when the device ID is null.
     * First, the device ID is set to null, then the expected message is set to "Parameters cannot be null".
     * Then, the constructor is called with the device ID as a parameter, and the exception is thrown.
     * After that, the exception message is compared to the expected message.
     */
    @Test
    void whenDeviceIDIsNull_throwsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new HumiditySensor(sensorName, null, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test verifies if the constructor throws an IllegalArgumentException when the sensor type ID is null.
     * First, the sensor type ID is set to null, then the expected message is set to "Parameters cannot be null".
     * Then, the constructor is called with the sensor type ID as a parameter, and the exception is thrown.
     * After that, the exception message is compared to the expected message.
     */
    @Test
    void whenSensorTypeIDIsNull_throwsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        String expected = "Parameters cannot be null";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new HumiditySensor(sensorName, deviceID, null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
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
        HumiditySensor humiditySensor = new HumiditySensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, humiditySensor.getId());
        assertEquals(sensorNameDouble, humiditySensor.getSensorName());
        assertEquals(deviceIDDouble, humiditySensor.getDeviceID());
        assertEquals(sensorTypeDouble, humiditySensor.getSensorTypeID());
    }

    /**
     * This test verifies if the Sensor ID is returned as a String when the getID method is called.
     * First, the parameters are mocked, then the expected value is set to "Test".
     * Then, the SensorIDVO constructor is mocked to return the expected value.
     * After that, the HumiditySensor constructor is called with the mocked parameters.
     * Then, the getID method is called, calling the SensorIDVO getID method, which returns a String.
     * Afterward, a list of SensorIDVO is created to store the mocked SensorIDVO.
     * Finally, the expected SensorID is compared to the result, and the size of the list is checked.
     */
    @Test
    void whenCallingGetIDToGetSensorIDVO_andCallingGetIDAgainToGetString_thenReturnsSensorIDAsString() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn(expected))) {

            HumiditySensor sensor = new HumiditySensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getId().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected,result);
            assertEquals(1,listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test verifies if the constructor throws an IllegalArgumentException when the external service is null.
     * First, all the constructor's parameters are mocked and the external service is set to null.
     * Then, the sensorID is mocked and the sensor is created, to which follows the expected message, set to "Invalid external service".
     * Afterward, an exception is thrown when the getReading method is called, and the exception message stored.
     * After that, the list of SensorIDVO is created to store the mocked SensorIDVO.
     * Finally, the exception message is compared to the expected message and the size of the list checked.
     */
    @Test
    void whenSimHardwareIsNull_throwsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            HumiditySensor humiditySensor = new HumiditySensor(sensorName, deviceID, sensorTypeID);
            String expected = "Invalid parameters";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    humiditySensor.getReading(null,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test case to verify that HumiditySensor throws an IllegalArgumentException when the value factory is null.
     *
     * <p>
     * This test ensures that the HumiditySensor class's {@code getReading} method throws an IllegalArgumentException when provided with a null value factory.
     * </p>
     *
     * <p>
     * The test arranges the scenario by mocking necessary objects such as SensorNameVO, DeviceIDVO, SensorTypeIDVO, and SimHardware. It constructs a HumiditySensor instance with the mocked objects.
     * </p>
     *
     * <p>
     * The test acts by invoking the {@code getReading} method of the HumiditySensor instance with a null value factory, expecting an IllegalArgumentException to be thrown.
     * </p>
     *
     * <p>
     * The test asserts that the exception message matches the expected message and verifies that no SensorIDVO objects are constructed during the process.
     * </p>
     */
    @Test
    void whenValueFactoryIsNull_throwsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SimHardware simHardware = mock(SimHardware.class);
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            HumiditySensor humiditySensor = new HumiditySensor(sensorName, deviceID, sensorTypeID);
            String expected = "Invalid parameters";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    humiditySensor.getReading(simHardware,null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test case to verify that HumiditySensor returns the correct value when provided with valid parameters.
     *
     * <p>
     * This test ensures that the HumiditySensor class's {@code getReading} method returns the expected value when invoked with valid parameters.
     * </p>
     *
     * <p>
     * The test arranges the scenario by mocking necessary objects such as SensorNameVO, DeviceIDVO, SensorTypeIDVO, and SimHardware. It also mocks the behavior of the SimHardware's getValue method to return a specific reading. Additionally, it mocks the behavior of the SensorValueFactoryImpl to create a mock HumidityValue object with the expected reading.
     * </p>
     *
     * <p>
     * The test acts by invoking the {@code getReading} method of the HumiditySensor instance with the mocked SimHardware and SensorValueFactoryImpl objects, and then verifies that the returned value matches the expected value. It also verifies that the constructed size of SensorIDVO objects is 1.
     * </p>
     *
     * <p>
     * The test asserts that the returned value and the constructed object are of the expected types.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_ReturnsValue() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String reading = "50";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);
        int expected = 50;

        SensorValueObject value = mock(HumidityValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading,sensorType)).thenReturn(value);

            HumiditySensor sensor = new HumiditySensor(sensorName, deviceID, sensorType);

            //Act
            int result = sensor.getReading(simHardware,valueFactory).getValue();
            Object resultObj = sensor.getReading(simHardware,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(1,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(HumidityValue.class,resultObj);
        }
    }

    /**
     * Test case to verify that HumiditySensor returns a HumidityValue object when provided with valid parameters.
     *
     * <p>
     * This test ensures that the HumiditySensor class's {@code getReading} method returns a HumidityValue object when
     * invoked with valid parameters.
     * </p>
     *
     * <p>
     * The test arranges the scenario by creating instances of SensorNameVO, SensorTypeIDVO, and DeviceIDVO,
     * representing the sensor's name, type, and device ID, respectively. It then creates an instance of HumiditySensor
     * using these parameters. Additionally, it mocks the behavior of the SimHardware object to return a specific
     * reading when its getValue method is called. The test also creates an instance of SensorValueFactoryImpl to
     * provide value creation functionality.
     * </p>
     *
     * <p>
     * The test acts by invoking the {@code getReading} method of the HumiditySensor instance with the mocked
     * SimHardware and SensorValueFactoryImpl objects. It then verifies that the returned value is an instance of
     * HumidityValue and that its value matches the expected value.
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
        SensorTypeIDVO sensorType = new SensorTypeIDVO("HumiditySensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        HumiditySensor sensor = new HumiditySensor(sensorName, deviceID, sensorType);

        String reading = "50";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        int expected = 50;

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        HumidityValue valueResult = sensor.getReading(simHardware,valueFactory);
        int result = valueResult.getValue();

        // Assert
        assertInstanceOf(HumidityValue.class,valueResult);
        assertEquals(expected,result);
    }

    /**
     * This test verifies if the getSensorName method returns the sensor name.
     * First, the sensor name is mocked and the expected value is set to "Sensor".
     * Then, all the other constructor's parameters are mocked.
     * A string is set to the expected name and the SensorIDVO constructor mocked.
     * The humidity sensor is created and the getSensorName method called, being converted to String.
     * After that, a list of SensorIDVO is created to store the mocked SensorIDVO.
     * Finally, the expected value is compared to the result and the size of the list checked.
     */
    @Test
    void whenGetSensorNameCalled_ReturnsSensorName() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        when(sensorName.toString()).thenReturn("Sensor");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Sensor";

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            HumiditySensor humiditySensor = new HumiditySensor(sensorName,deviceID,sensorTypeID);

            //Act
            String result = humiditySensor.getSensorName().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected,result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test verifies if the getDeviceID method returns the device ID.
     * First, the sensor name is mocked, as well as the other constructor's parameters.
     * In this case, the device ID is mocked and the expected value is set to "XPTO".
     * The expected string is set to "XPTO" and the SensorIDVO constructor mocked.
     * Afterward, the humidity sensor is created and the getDeviceID method called, being converted to String.
     * Meanwhile, a list of SensorIDVO is created to store the mocked SensorIDVO.
     * Finally, the expected value is compared to the result and the size of the list checked.
     */
    @Test
    void whenGetDeviceIDCalled_ReturnsDeviceID() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(deviceID.toString()).thenReturn("XPTO");
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "XPTO";

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            HumiditySensor humiditySensor = new HumiditySensor(sensorName,deviceID,sensorTypeID);

            //Act
            String result = humiditySensor.getDeviceID().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected,result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * This test verifies if the getSensorTypeID method returns the sensor type ID.
     * First, the sensor name is mocked, as well as the other constructor's parameters.
     * In this case, the sensor type ID is mocked and the expected value is set to "XPTO".
     * The expected string is set to "XPTO" and the SensorIDVO constructor mocked.
     * Afterward, the humidity sensor is created and the getSensorTypeID method called, is converted to String.
     * Meanwhile, a list of SensorIDVO is created to store the mocked SensorIDVO.
     * Finally, the expected value is compared to the result and the size of the list checked.
     */
    @Test
    void whenGetSensorTypeCalled_ReturnsDeviceID() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        when(sensorTypeID.toString()).thenReturn("XPTO");
        String expected = "XPTO";

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            HumiditySensor humiditySensor = new HumiditySensor(sensorName,deviceID,sensorTypeID);

            //Act
            String result = humiditySensor.getSensorTypeID().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected,result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }
}



