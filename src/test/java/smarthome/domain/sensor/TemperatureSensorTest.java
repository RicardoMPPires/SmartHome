package smarthome.domain.sensor;

import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.TemperatureValue;
import smarthome.domain.sensor.externalservices.SimHardware;

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

class TemperatureSensorTest {
    /**
     * Test if the constructor throws an exception when the name is null.
     */
    @Test
    void whenSensorNameIsNull_thenExceptionIsThrown() {
        //Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters.";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new TemperatureSensor(null, deviceID, sensorType));
        String result = exception.getMessage();

        List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

        //Assert
        assertEquals(expected, result);
        assertEquals(0, listOfMockedSensorIDVO.size());
    }
}

    /**
     * Test if the constructor throws an exception when the name is empty.
     */
    @Test
    void whenSensorNameIsEmpty_thenExceptionIsThrown() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters.";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new TemperatureSensor(sensorName, null, sensorType));
        String result = exception.getMessage();

        List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

        //Assert
        assertEquals(expected, result);
        assertEquals(0, listOfMockedSensorIDVO.size());
    }
}

    /**
     * Test if the constructor throws an exception when the sensorType is null.
     */
        @Test
        void whenSensorTypeIsNull_thenExceptionIsThrown() {
            //Arrange
            SensorNameVO sensorName = mock(SensorNameVO.class);
            DeviceIDVO deviceID = mock(DeviceIDVO.class);
            String expected = "Invalid parameters.";
            try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new TemperatureSensor(sensorName, deviceID, null));
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
        TemperatureSensor temperatureSensor = new TemperatureSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, temperatureSensor.getId());
        assertEquals(sensorNameDouble, temperatureSensor.getSensorName());
        assertEquals(deviceIDDouble, temperatureSensor.getDeviceID());
        assertEquals(sensorTypeDouble, temperatureSensor.getSensorTypeID());
    }

    /**
     * Test if the constructor returns the correct sensor name.
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsSensorName(){
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        when(sensorName.toString()).thenReturn("Sensor1");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String expected = "Sensor1";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);

        //Act
        String result = sensor.getSensorName().toString();
        List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

        //Assert
        assertEquals(expected, result);
        assertEquals(1, listOfMockedSensorIDVO.size());
    }
}

    /**
     * Test if the constructor returns the correct sensor ID.
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsSensorID() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        String expected = "08";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.toString()).thenReturn("08"))) {
            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);

            //Act
            String result = sensor.getId().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(1, listOfMockedSensorIDVO.size());
            assertEquals(expected, result);
        }
    }

    /**
     * Test if the correct sensor type is returned, after the sensor is created.
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsSensorType() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        when(sensorType.toString()).thenReturn("08");
        String expected = "08";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);
        //Act
        String result = sensor.getSensorTypeID().toString();
        List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

        //Assert
        assertEquals(expected, result);
        assertEquals(1, listOfMockedSensorIDVO.size());
    }
}

    /**
     * Test if the correct device ID is returned, after the sensor is created.
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsDeviceID() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(deviceID.toString()).thenReturn("08");
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String expected = "08";
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);

        //Act
        String result = sensor.getDeviceID().toString();
        List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

        //Assert
        assertEquals(expected, result);
        assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test if the constructor throws an exception when the hardware is null.
     */
    @Test
    void whenSimHardwareIsNull_ThrowsIllegalArgumentException()
    {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);
            String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensor.getReading(null,valueFactory));
        String result = exception.getMessage();
        List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

        //Assert
        assertEquals(expected, result);
        assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Tests the case when the value factory is null, which should throw an IllegalArgumentException.
     * <p>
     * The test verifies that if the value factory passed to the {@code getReading} method is null, the sensor
     * throws an IllegalArgumentException with the message "Invalid parameters." The test ensures that no sensor
     * ID value objects are constructed during this process.
     * </p>
     */
    @Test
    void whenValueFactoryIsNull_ThrowsIllegalArgumentException()
    {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        SimHardware simHardware = mock(SimHardware.class);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);
            String expected = "Invalid parameters";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(simHardware,null));
            String result = exception.getMessage();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Tests the scenario where the sensor reading is correct, and it returns the temperature value successfully.
     * <p>
     * This test ensures that when a correct temperature reading is provided to the sensor, it returns the expected
     * temperature value. The sensor constructs a temperature value object using the provided reading and sensor type,
     * and the value factory. It then verifies that the returned temperature value matches the expected value.
     * Additionally, it checks that the constructed sensor ID value object list has a size of 1 after the operation.
     * </p>
     */
    @Test
    void whenReadingIsCorrect_thenReturnsValueSuccessfully() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        String reading = "36.1";

        double expected = 36.1;

        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        SensorValueObject value = mock(TemperatureValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading,sensorType)).thenReturn(value);

            TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);

            //Act
            double result = sensor.getReading(simHardware,valueFactory).getValue();
            Object resultObj = sensor.getReading(simHardware,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(1,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(TemperatureValue.class,resultObj);
        }
    }

    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsHumidityValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("TemperatureSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        TemperatureSensor sensor = new TemperatureSensor(sensorName, deviceID, sensorType);

        String reading = "36.1";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        double expected = 36.1;

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        TemperatureValue valueResult = sensor.getReading(simHardware,valueFactory);
        double result = valueResult.getValue();

        // Assert
        assertInstanceOf(TemperatureValue.class,valueResult);
        assertEquals(expected,result);
    }
}