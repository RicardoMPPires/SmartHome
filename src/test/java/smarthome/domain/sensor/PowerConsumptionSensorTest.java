package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.PowerConsumptionValue;
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
import static org.mockito.Mockito.*;

class PowerConsumptionSensorTest {


    /**
     * Test for PowerConsumptionSensor
     * Given a null SensorNameVO, when the constructor is called, then an IllegalArgumentException is thrown
     */
    @Test
    void givenNullNameVO_throwsIllegalArgumentException() {
        //Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionSensor(null, deviceID, sensorTypeID));

            //Assert
            String result = exception.getMessage();
            List<SensorIDVO> createdInstances = mockedConstruction.constructed();
            assertEquals(expected, result);
            assertEquals(0, createdInstances.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * Given a null DeviceIDVO, when the constructor is called, then an IllegalArgumentException is thrown
     */
    @Test
    void givenNullDeviceID_throwsIllegalArgumentException() {
        //Arrange
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        SensorNameVO sensorName = mock(SensorNameVO.class);
        String expected = "Parameters cannot be null";

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionSensor(sensorName, null, sensorTypeID));

            //Assert
            String result = exception.getMessage();
            List<SensorIDVO> createdInstances = mockedConstruction.constructed();
            assertEquals(expected, result);
            assertEquals(0, createdInstances.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * Given a null SensorTypeIDVO, when the constructor is called, then an IllegalArgumentException is thrown
     */
    @Test
    void givenNullSensorTypeID_throwsIllegalArgumentException() {
        //Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorNameVO sensorName = mock(SensorNameVO.class);
        String expected = "Parameters cannot be null";

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionSensor(sensorName, deviceID, null));

            //Assert
            String result = exception.getMessage();
            List<SensorIDVO> createdInstances = mockedConstruction.constructed();
            assertEquals(expected, result);
            assertEquals(0, createdInstances.size());
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
        PowerConsumptionSensor powerConsumptionSensor = new PowerConsumptionSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, powerConsumptionSensor.getId());
        assertEquals(sensorNameDouble, powerConsumptionSensor.getSensorName());
        assertEquals(deviceIDDouble, powerConsumptionSensor.getDeviceID());
        assertEquals(sensorTypeDouble, powerConsumptionSensor.getSensorTypeID());
    }


    /**
     * Test for PowerConsumptionSensor
     * Given a null SimHardware, when getPowerConsumption is called, then an IllegalArgumentException is thrown
     */
    @Test
    void givenNullSimHardware_throwsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorTypeID);
        String expected = "Invalid parameters";

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> sensor.getReading(null,valueFactory));

            //Assert
            String result = exception.getMessage();
            List<SensorIDVO> createdInstances = mockedConstruction.constructed();
            assertEquals(expected, result);
            assertEquals(0, createdInstances.size());
        }
    }

    /**
     * Verifies that an IllegalArgumentException is thrown when a null value factory is provided.
     *
     * <p>This test ensures that an IllegalArgumentException is thrown when a null value factory
     * is passed to the getPowerConsumption method of the PowerConsumptionSensor class.</p>
     */
    @Test
    void givenNullValueFactory_throwsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorTypeID);
        String expected = "Invalid parameters";

        SimHardware simHardware = mock(SimHardware.class);

        try(MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)){
            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> sensor.getReading(simHardware,null));
            String result = exception.getMessage();

            //Assert
            List<SensorIDVO> createdInstances = mockedConstruction.constructed();
            assertEquals(expected, result);
            assertEquals(0, createdInstances.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * This test ensures the encapsulated VO's value is accessbile by calling getSensorName() : VO, then calling toString
     * on the same VO to get the name as a string
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

            PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorName().toString();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * This test ensures the encapsulated VO's value is accessbile by calling getSensorTypeID() : VO, then calling toString
     * on the same VO to get the ID as a string
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

            PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getSensorTypeID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * This test ensures the encapsulated VO's value is accessbile by calling getDeviceID() : VO, then calling toString
     * on the same VO to get the device ID as a string
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

            PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorTypeID);

            // Act
            String result = sensor.getDeviceID().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * This test ensures the encapsulated VO's value is accessbile by calling getId() : VO, then calling toString
     * on the same VO to get the ID as a string
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

            PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorTypeID);
            // Act
            String result = sensor.getId().getID();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            // Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }


    /**
     * Test for PowerConsumptionSensor
     * This test ensures the method getPowerConsumption returns the encapsulated value as a string
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_ReturnsValue() {
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String reading = "50";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);
        int expected = 50;

        SensorValueObject value = mock(PowerConsumptionValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading, sensorType)).thenReturn(value);

            PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorType);

            //Act
            int result = sensor.getReading(simHardware, valueFactory).getValue();
            Object resultObj = sensor.getReading(simHardware, valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(1, resultSize);
            assertEquals(expected, result);
            assertInstanceOf(PowerConsumptionValue.class, resultObj);
        }
    }

    /**
     * Validates that a HumidityValue object is returned when getReading is called with valid parameters.
     *
     * <p>
     * This test aims to verify that the PowerConsumptionSensor class returns a HumidityValue object
     * when the getReading method is invoked with valid parameters. The test sets up the necessary
     * mocks for sensor hardware and value factory, simulates a reading of 50 from the hardware, and
     * then checks if the returned value object is of type PowerConsumptionValue and has the expected value.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsHumidityValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("PowerConsumptionSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        PowerConsumptionSensor sensor = new PowerConsumptionSensor(sensorName, deviceID, sensorType);

        String reading = "50";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        int expected = 50;

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        PowerConsumptionValue valueResult = sensor.getReading(simHardware,valueFactory);
        int result = valueResult.getValue();

        // Assert
        assertInstanceOf(PowerConsumptionValue.class,valueResult);
        assertEquals(expected,result);
    }
}