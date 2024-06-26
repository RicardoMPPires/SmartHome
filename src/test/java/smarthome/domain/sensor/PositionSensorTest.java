package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.PositionValue;
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

class PositionSensorTest {

    /**
     * Given a null SensorNameVO, the constructor throws IllegalArgumentException.
     */
    @Test
    void givenNullSensorNameVO_ThrowsIllegalArgument() {
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid Parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new PositionSensor(null, deviceID, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Given a null DeviceIDVO, the constructor throws IllegalArgumentException.
     */
    @Test
    void givenNullDeviceID_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid Parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new PositionSensor(sensorName, null, sensorTypeID));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     *  Given a null SensorTypeIDVO, the constructor throws IllegalArgumentException.
     */
    @Test
    void givenNullSensorTypeID_ThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        String expected = "Invalid Parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new PositionSensor(sensorName, deviceID, null));
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
        PositionSensor positionSensor = new PositionSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);

        //Assert
        assertEquals(sensorIDDouble, positionSensor.getId());
        assertEquals(sensorNameDouble, positionSensor.getSensorName());
        assertEquals(deviceIDDouble, positionSensor.getDeviceID());
        assertEquals(sensorTypeDouble, positionSensor.getSensorTypeID());
    }

    /**
     *  When GetSensorName method is called, Then Returns Name.
     */
    @Test
    void whenGetSensorNameMethodIsCalled_ThenReturnsName() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        when(sensorName.toString()).thenReturn("Test");

        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorName().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     *  When GetSensorType method is called, Then Returns SensorTypeID.
     */
    @Test
    void whenGetSensorTypeMethodIsCalled_ThenReturnsSensorTypeID() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        when(sensorTypeID.getID()).thenReturn("Test");

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorTypeID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }


    /**
     *  When GetDeviceID method is called, Then Returns DeviceID.
     */
    @Test
    void whenGetDeviceIDMethodIsCalled_thenReturnsDeviceID() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(deviceID.getID()).thenReturn("Test");

        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);


        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getDeviceID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }


    /**
     *  When GetID method is called, Then Returns SensorID.
     */
    @Test
    void whenGetIDToGetSensorIDVOThenCallingGetIDAgainToGetStringAreCalled_ThenReturnsSensorIDAsString() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String expected = "Test";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn(expected))) {

            PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorTypeID);
            // Act
            String result = sensor.getId().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Given a null SimHardware, the getReading method throws IllegalArgumentException.
     */
    @Test
    void givenNullSimHardware_ThenThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorTypeID);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        int expected = 0;
        String expected2 = "Invalid parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(null,valueFactory));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, listOfMockedSensorIDVO.size());
            assertEquals(expected2, result);
        }
    }

    /**
     * Verifies that an IllegalArgumentException is thrown when the valueFactory parameter is null.
     *
     * <p>
     * This test method verifies that an IllegalArgumentException is thrown when the valueFactory parameter passed to
     * the getReading method of the PositionSensor is null. It ensures that the method correctly handles the case where
     * the valueFactory parameter is null.
     * </p>
     *
     * <p>
     * The test arranges the necessary mock objects for the sensorName, deviceID, and sensorTypeID, and creates a
     * PositionSensor object. It also sets up a SimHardware mock object. The test then attempts to call the getReading
     * method of the PositionSensor with a null valueFactory parameter and expects an IllegalArgumentException to be
     * thrown.
     * </p>
     *
     * <p>
     * Upon throwing the exception, the test verifies that the exception message is "Invalid parameters" and that the
     * mockedConstruction.constructed() method returns an empty list, indicating that no sensorIDVO objects were
     * constructed during the test.
     * </p>
     */
    @Test
    void givenValueFactoryNull_ThenThrowsIllegalArgument() {
        // Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorTypeID);
        SimHardware simHardware = mock(SimHardware.class);
        int expected = 0;
        String expected2 = "Invalid parameters";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            // Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(simHardware,null));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, listOfMockedSensorIDVO.size());
            assertEquals(expected2, result);
        }
    }

    /**
     * Verifies that the getReading method of PositionSensor returns the expected PositionValue object.
     *
     * <p>
     * This test method ensures that the getReading method of PositionSensor correctly returns the expected PositionValue object when provided with valid parameters. It sets up mock objects for the sensorName, deviceID, and sensorType, as well as a SimHardware mock object to simulate sensor readings. The method also mocks the behavior of the valueFactory to return a mocked PositionValue object when createSensorValue is called.
     * </p>
     *
     * <p>
     * Inside a try-with-resources block, the test calls the getReading method of PositionSensor with the simulated hardware and value factory. It then asserts that the returned PositionValue object has the expected value and that its type is correct. Additionally, it verifies that the mockedConstruction.constructed() method returns a list with one element, indicating that a SensorIDVO object was constructed during the test.
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

        SensorValueObject value = mock(PositionValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading,sensorType)).thenReturn(value);

            PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorType);

            //Act
            int result = sensor.getReading(simHardware,valueFactory).getValue();
            Object resultObj = sensor.getReading(simHardware,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(1,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(PositionValue.class,resultObj);
        }
    }

    /**
     * Verifies that the getReading method of PositionSensor returns a PositionValue object with the expected value.
     *
     * <p>
     * This test method ensures that the getReading method of PositionSensor returns a PositionValue object with the
     * expected value when provided with valid parameters. It sets up mock objects for the sensorName, deviceID, and
     * sensorType, as well as a SimHardware mock object to simulate sensor readings. The method also creates a
     * SensorValueFactoryImpl object for value creation.
     * </p>
     *
     * <p>
     * Inside the test, the getReading method of PositionSensor is called with the simulated hardware and value factory.
     * It then verifies that the returned PositionValue object has the expected value and that its type is correct.
     * </p>
     *
     * <p><strong>Note:</strong> The System Under Test (SUT) comprises the PositionSensor class, SensorValueFactoryImpl
     * class, and PositionValue class.</p>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsHumidityValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("PositionSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        PositionSensor sensor = new PositionSensor(sensorName, deviceID, sensorType);

        String reading = "50";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        int expected = 50;

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        PositionValue valueResult = sensor.getReading(simHardware,valueFactory);
        int result = valueResult.getValue();

        // Assert
        assertInstanceOf(PositionValue.class,valueResult);
        assertEquals(expected,result);
    }
}
