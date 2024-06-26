package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.SolarIrradianceValue;
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
 * When needed, to isolate the SolarIrradianceSensor Class, doubles of the following Classes were created:
 * SensorNameVO, DeviceIDVO, SensorTypeIDVO, SensorIDVO, SimHardware and SolarIrradianceValue.
 */
class SolarIrradianceSensorTest {
    /**
     * Successful scenario:
     * Sensor is created.
     * Verifies that the expected sensorID value object is retrieved and a single MockedConstruction of SensorIDVO is created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenInvalidParameters_SensorIsCreated_WhenGetIdIsInvoked_ThenShouldReturnCorrectSensorIdVO(){
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        int idListExpectedSize = 1;

        try(MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class))
            {

            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            SensorIDVO result = (SensorIDVO) sensor.getId();

            //Assert
            assertEquals(idList.get(0), result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when a valid hardware object and value factory are provided to the {@code getReading} method of
     * {@code SolarIrradianceSensor}, it returns a {@code SolarIrradianceValue} object containing the correct value.
     * The hardware object is expected to provide a simulated reading, and the value factory is responsible for
     * creating the appropriate sensor value object based on the simulated reading and sensor type.
     * This test ensures that the sensor retrieves the correct solar irradiance value from the provided hardware,
     * utilizing the value factory to create the corresponding sensor value object.
     */
    @Test
    void givenValidHardware_whenGetReadingIsInvoked_ThenShouldReturnSolarIrradianceValue() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);

        String reading = "200";
        SimHardware hardwareDouble = mock(SimHardware.class);
        when(hardwareDouble.getValue()).thenReturn(reading);
        int expected = 200;
        int idListExpectedSize = 1;

        SensorValueObject value = mock(SolarIrradianceValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading,typeDouble)).thenReturn(value);

            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);

            //Act
            int result = sensor.getReading(hardwareDouble,valueFactory).getValue();
            Object resultObj = sensor.getReading(hardwareDouble,valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(idListExpectedSize,resultSize);
            assertEquals(expected, result);
            assertInstanceOf(SolarIrradianceValue.class,resultObj);
        }
    }

    /**
     * Verifies that when valid parameters are provided to the {@code getReading} method of {@code SolarIrradianceSensor},
     * it returns a {@code SolarIrradianceValue} object containing the expected value.
     *
     * <p>This test ensures that the sensor retrieves the correct solar irradiance value from the provided simulated
     * hardware, using the value factory to create the corresponding sensor value object.</p>
     */

    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsHumidityValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("SolarIrradianceSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SolarIrradianceSensor sensor = new SolarIrradianceSensor(sensorName, deviceID, sensorType);

        String reading = "200";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        int expected = 200;

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        SolarIrradianceValue valueResult = sensor.getReading(simHardware,valueFactory);
        int result = valueResult.getValue();

        // Assert
        assertInstanceOf(SolarIrradianceValue.class,valueResult);
        assertEquals(expected,result);
    }

    /**
     * Sensor is created. Then getReading() method is invoked with an invalid hardware.
     * The method throws an IllegalArgumentException.
     * It is verified that a single MockedConstruction of SensorIDVO is achieved.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenInvalidHardware_whenGetReadingIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";
        int idListExpectedSize = 1;

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);

        try (MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class))
        {
            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> sensor.getReading(null,valueFactory));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when an invalid value factory is provided to the {@code getReading} method of
     * {@code SolarIrradianceSensor}, an {@code IllegalArgumentException} is thrown. An invalid value factory
     * is defined as a null value.
     */
    @Test
    void givenInvalidValueFactory_whenGetReadingIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";
        int idListExpectedSize = 1;

        SimHardware simHardware = mock(SimHardware.class);

        try (MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class))
        {
            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () -> sensor.getReading(simHardware,null));
            String result = exception.getMessage();

            //Assert
            assertEquals(expected, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when the {@code getReading} method of {@code SolarIrradianceSensor} is invoked with valid hardware
     * but an invalid reading, it returns {@code null}.
     *
     * <p>This test ensures that the sensor correctly handles invalid readings by returning {@code null} when the value
     * factory fails to create a sensor value object.</p>
     */
    @Test
    void givenValidHardwareButInvalidReading_whenGetReadingIsInvoked() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        SimHardware hardwareDouble = mock(SimHardware.class);
        String reading = "19.r.20";
        when(hardwareDouble.getValue()).thenReturn(reading);
        int idListExpectedSize = 1;

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        when(valueFactory.createSensorValue(reading,typeDouble)).thenReturn(null);

        try (MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class))
        {
            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            int idListSize = idList.size();

            //Assert
            assertNull(sensor.getReading(hardwareDouble,valueFactory));
            assertEquals(idListExpectedSize,idListSize);
        }
    }

    /**
     * Successful scenario:
     * Sensor is created.
     * Verifies that the expected sensorName value object is retrieved and a single MockedConstruction of SensorIDVO is created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenInvalidParameters_SensorIsCreated_WhenGetSensorNameIsInvoked_ThenShouldReturnCorrectSensorNameVO(){
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        int idListExpectedSize = 1;

        try(MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class))
        {

            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            SensorNameVO result = sensor.getSensorName();

            //Assert
            assertEquals(nameDouble, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Successful scenario:
     * Sensor is created.
     * Verifies that the expected sensorTypeID value object is retrieved and a single MockedConstruction of SensorIDVO is created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenInvalidParameters_SensorIsCreated_WhenGetSensorTypeIdIsInvoked_ThenShouldReturnCorrectSensorTypeIDVO() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        int idListExpectedSize = 1;

        try (MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class)) {

            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            SensorTypeIDVO result = sensor.getSensorTypeID();

            //Assert
            assertEquals(typeDouble, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Successful scenario:
     * Sensor is created.
     * Verifies that the expected deviceID value object is retrieved and a single MockedConstruction of SensorIDVO is created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenInvalidParameters_SensorIsCreated_WhenGetDeviceIDIsInvoked_ThenShouldReturnCorrectDeviceIDVO() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        int idListExpectedSize = 1;

        try (MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class)) {

            SolarIrradianceSensor sensor = new SolarIrradianceSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            DeviceIDVO result = sensor.getDeviceID();

            //Assert
            assertEquals(deviceIdDouble, result);
            assertEquals(idListExpectedSize, idList.size());
        }
    }

    /**
     * Verifies that when a null name is given to instantiate the sensor, an IllegalArgumentException is thrown.
     * Sensor is not created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenNullSensorName_ThenShouldThrowAnIllegalArgumentException() {
        //Arrange
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        String expected = "Sensor parameters cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceSensor(null, deviceIdDouble, typeDouble));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when a null deviceID is given to instantiate the sensor, an IllegalArgumentException is thrown.
     * Sensor is not created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenNullDeviceID_ThenShouldThrowAnIllegalArgumentException() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        String expected = "Sensor parameters cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceSensor(nameDouble, null, typeDouble));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that when a null sensorTypeID is given to instantiate the sensor, an IllegalArgumentException is thrown.
     * Sensor is not created.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenNullSensorTypeID_ThenShouldThrowAnIllegalArgumentException() {
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        String expected = "Sensor parameters cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceSensor(nameDouble, deviceIdDouble, null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }


    /**
     * Test to check if the second constructor creates a sensor when the parameters are valid.
     */
    @Test
    void givenInvalidParameters_whenCreatingSensor_thenSensorIsCreated() {
        //Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorIDVO sensorIDDouble = mock(SensorIDVO.class);
        //Act
        SolarIrradianceSensor solarIrradianceSensor = new SolarIrradianceSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, solarIrradianceSensor.getId());
        assertEquals(sensorNameDouble, solarIrradianceSensor.getSensorName());
        assertEquals(deviceIDDouble, solarIrradianceSensor.getDeviceID());
        assertEquals(sensorTypeDouble, solarIrradianceSensor.getSensorTypeID());
    }
}