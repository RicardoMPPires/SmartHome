package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.AveragePowerConsumptionValue;
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

/**
 * Test class for the AveragePowerConsumptionSensor
 */

class AveragePowerConsumptionSensorTest {

    /**
     * Test to check if the constructor throws an exception when the sensor name is null
     */

    @Test
    void givenNullName_whenCreatingSensor_thenThrowException() {
//        Arrange
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String expected = "Parameters cannot be null";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AveragePowerConsumptionSensor(null, deviceIDDouble, sensorTypeDouble));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test to check if the constructor throws an exception when the sensor type is null
     */

    @Test
    void givenNullSensorType_whenCreatingSensor_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String expected = "Parameters cannot be null";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, null));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test to check if the constructor throws an exception when the device ID is null
     */

    @Test
    void givenNullDeviceID_whenCreatingSensor_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        String expected = "Parameters cannot be null";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AveragePowerConsumptionSensor(sensorNameDouble, null, sensorTypeDouble));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }


    /**
     * Test to check if the second constructor creates a sensor when the parameters are valid
     */
    @Test
    void givenValidParameters_whenCreatingSensor_thenSensorIsCreated() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorIDVO sensorIDDouble = mock(SensorIDVO.class);
//        Act
        AveragePowerConsumptionSensor averagePowerConsumptionSensor = new AveragePowerConsumptionSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
//        Assert
        assertEquals(sensorIDDouble, averagePowerConsumptionSensor.getId());
        assertEquals(sensorNameDouble, averagePowerConsumptionSensor.getSensorName());
        assertEquals(deviceIDDouble, averagePowerConsumptionSensor.getDeviceID());
        assertEquals(sensorTypeDouble, averagePowerConsumptionSensor.getSensorTypeID());
    }

    /**
     * Test to check if the getReading method throws an exception when the SimHardware is null
     */

    @Test
    void givenNullSimHardware_whenGetReading_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String initialDate = "01-01-2021 00:00:00";
        String finalDate = "01-01-2021 00:00:00";
        String expected = "Invalid parameters";
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensor.getReading(initialDate, finalDate, null,valueFactory));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test to check if the getReading method throws an exception when the date is invalid
     */

    @Test
    void givenInvalidDate_whenGetReading_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16/12/2020 14:15:45";
        SimHardware simHardware = mock(SimHardware.class);
        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        String expected = "Invalid date";
        AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensor.getReading(initialDate, finalDate, simHardware,valueFactory));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when a null value factory is provided to the getReading method of the AveragePowerConsumptionSensor class.
     *
     * <p>
     * This test sets up the necessary dependencies for creating an AveragePowerConsumptionSensor instance, including mocked instances of SensorNameVO, SensorTypeIDVO, DeviceIDVO, and SimHardware.
     * The instance of AveragePowerConsumptionSensor is then used to invoke the getReading method with null value factory parameter.
     * The test expects the method to throw an IllegalArgumentException with the message "Invalid date".
     * </p>
     *
     * <p>
     * The test verifies this behavior using assertThrows and compares the expected error message with the actual error message thrown by the method.
     * </p>
     */
    @Test
    void givenNullValueFactory_whenGetReading_thenThrowException() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16/12/2020 14:15:45";
        SimHardware simHardware = mock(SimHardware.class);
        String expected = "Invalid date";
        AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensor.getReading(initialDate, finalDate, simHardware,null));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that the getReading method of the AveragePowerConsumptionSensor class returns the expected value when provided with valid simulated hardware and value factory.
     *
     * <p>
     * This test sets up the necessary dependencies for creating an AveragePowerConsumptionSensor instance, including mocked instances of SensorNameVO, SensorTypeIDVO, DeviceIDVO, and SimHardware.
     * The simulated hardware is configured to return a specific reading for the given time range.
     * A mock SensorValueObject is also configured to return the expected value when its getValue method is called.
     * The value factory is set up to return this mock SensorValueObject when provided with the simulated reading and sensor type.
     * </p>
     *
     * <p>
     * The getReading method of the AveragePowerConsumptionSensor instance is then invoked with the specified initial and final dates, the simulated hardware, and the value factory.
     * The test expects the method to return the expected value obtained from the SensorValueObject created by the value factory.
     * </p>
     *
     * <p>
     * The test verifies this behavior by comparing the expected value with the actual value returned by the getReading method.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReading_thenReturnValue() {
//        Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);

        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45";
        String reading = "100";

        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue(initialDate, finalDate)).thenReturn(reading);

        int expected = 100;

        SensorValueObject value = mock(AveragePowerConsumptionValue.class);
        when(value.getValue()).thenReturn(expected);

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        when(valueFactory.createSensorValue(reading,sensorTypeDouble)).thenReturn(value);

//      Act
        int result = sensor.getReading(initialDate, finalDate, simHardware,valueFactory).getValue();
        Object resultObj = sensor.getReading(initialDate, finalDate, simHardware,valueFactory);

//      Assert
        assertEquals(expected, result);
        assertInstanceOf(AveragePowerConsumptionValue.class,resultObj);
    }

    /**
     * Test case to verify that the getReading method of the AveragePowerConsumptionSensor class returns an instance of AveragePowerConsumptionValue with the expected value when provided with valid parameters.
     *
     * <p>
     * This test focuses on the functionality of the AveragePowerConsumptionSensor class, which acts as the System Under Test (SUT). It verifies that the getReading method of the AveragePowerConsumptionSensor class correctly returns an instance of AveragePowerConsumptionValue with the expected value when provided with valid parameters.
     * </p>
     *
     * <p>
     * The test sets up the necessary dependencies for creating an AveragePowerConsumptionSensor instance, including instances of SensorNameVO, SensorTypeIDVO, and DeviceIDVO, which are value objects (VOs).
     * It also uses a SimHardware object, which represents a simulated hardware interface, and a SensorValueFactoryImpl object, which is a factory responsible for creating SensorValueObject instances.
     * </p>
     *
     * <p>
     * The simulated hardware is configured to return a specific reading for the given time range.
     * The value factory is set up using a properties file ("config.properties") containing configuration information.
     * </p>
     *
     * <p>
     * The getReading method of the AveragePowerConsumptionSensor instance is then invoked with the specified initial and final dates, the simulated hardware, and the value factory.
     * The test expects the method to return an instance of AveragePowerConsumptionValue, which represents the sensor reading obtained within the specified time range, and the value to match the expected value.
     * </p>
     *
     * <p>
     * The test verifies this behavior by asserting that the type of the returned SensorValueObject is AveragePowerConsumptionValue, and the value obtained from it matches the expected value.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReading_thenReturnAveragePowerConsumptionValueObject() {
//        Arrange
        SensorNameVO sensorNameDouble = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorTypeDouble = new SensorTypeIDVO("AveragePowerConsumptionSensor");
        DeviceIDVO deviceIDDouble = new DeviceIDVO(UUID.randomUUID());
        AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);

        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45";
        String reading = "100";

        int expected = 100;

        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue(initialDate, finalDate)).thenReturn(reading);

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

//      Act
        AveragePowerConsumptionValue valueResult = sensor.getReading(initialDate, finalDate, simHardware,valueFactory);
        int result = valueResult.getValue();

//      Assert
        assertInstanceOf(AveragePowerConsumptionValue.class,valueResult);
        assertEquals(expected,result);
    }

    /**
     * This test ensures that the method getSensorName returns the correct value.
     * It also ensures that the method creates a new instance of SensorIDVO.
     */

    @Test
    void givenGetSensorName_whenGetSensorName_thenReturnSensorNameAsString() {
//        Arrange
        String name = "Average Power Consumption Sensor";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        when(sensorName.getValue()).thenReturn(name);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class)) {
            AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorName, deviceIDDouble, sensorTypeDouble);
//        Act
            String result = sensor.getSensorName().getValue();
//        Assert
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
    void givenGetSensorTypeID_whenGetSensorTypeID_thenReturnSensorTypeIDAsString() {
//        Arrange
        String sensorTypeID = "AveragePowerConsumptionSensor";
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeIDVO.getID()).thenReturn(sensorTypeID);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class)) {
            AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeIDVO);
//        Act
            String result = sensor.getSensorTypeID().getID();
//        Assert
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
    void givenGetDeviceID_whenGetDeviceID_thenReturnDeviceIDAsString() {
//        Arrange
        String deviceID = "DeviceID";
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn(deviceID);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class)) {
            AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDVO, sensorTypeDouble);
//        Act
            String result = sensor.getDeviceID().getID();
//        Assert
            List<SensorIDVO> createdInstances = sensorIDVOMock.constructed();
            assertEquals(deviceID, result);
            assertEquals(expectedSize, createdInstances.size());
        }
    }

    /**
     * This test ensures that the method getId returns the correct value.
     * It also ensures that the method creates a new instance of SensorIDVO.
     */

    @Test
    void givenGetId_whenGetId_thenReturnSensorIDAsString() {
//        Arrange
        String sensorID = "123";
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        int expectedSize = 1;

        try (MockedConstruction<SensorIDVO> sensorIDVOMock = mockConstruction(SensorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn(sensorID))) {
            AveragePowerConsumptionSensor sensor = new AveragePowerConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);
//        Act
            String result = sensor.getId().getID();
//        Assert
            List<SensorIDVO> createdInstances = sensorIDVOMock.constructed();
            assertEquals(sensorID, result);
            assertEquals(expectedSize, createdInstances.size());
        }
    }
}
