package smarthome.domain.sensor;

import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SensorFactoryImplTest {

    /**
     * This test ensures that when given an invalid path. The constructor throws an exception.
     */
    @Test
    void givenInvalidPath_constructorThrowsIllegalArgument(){
        // Arrange
        String path1 = " ";
        String path2 = "Invalid path";
        String expected = "Error reading file";

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class,() -> new SensorFactoryImpl(path1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class,() -> new SensorFactoryImpl(path2));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class,() -> new SensorFactoryImpl(null));
        String result3 = exception3.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
    }

    /**
     * Validates the method createSensor, when all prerequisites are met, successfully returns the specified sensor object.
     */
    @Test
    void givenCorrectVOs_createSensorReturnsASensorObject(){
        // Arrange
        String path = "config.properties";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        when(sensorType.getID()).thenReturn("SunriseSensor");

        try(MockedConstruction<SunriseSensor> mockedConstruction = mockConstruction(SunriseSensor.class)) {
            SensorFactoryImpl factory = new SensorFactoryImpl(path);

            // Act
            SunriseSensor sensorResult = (SunriseSensor) factory.createSensor(sensorName,deviceID,sensorType);
            SunriseSensor sensorExpected = mockedConstruction.constructed().get(0);

            // Assert
            assertEquals(sensorExpected,sensorResult);
        }
    }

    /**
     * This test ensures createSensor returns null when given a null SensorNameVo. It also ensures there was no
     * instance of Sunrise Sensor created, as the method returns null before reaching the creation phase.
     */
    @Test
    void givenNullSensorNameVO_createSensorReturnsNull(){
        // Arrange
        String path = "config.properties";
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        try(MockedConstruction<SunriseSensor> mockedConstruction = mockConstruction(SunriseSensor.class)) {
            SensorFactoryImpl factory = new SensorFactoryImpl(path);

            // Act
            SunriseSensor sensorResult = (SunriseSensor) factory.createSensor(null,deviceID,sensorType);
            int numberMockedConstructions = mockedConstruction.constructed().size();

            // Assert
            assertNull(sensorResult);
            assertEquals(0,numberMockedConstructions);
        }
    }

    /**
     * This test ensures createSensor returns null when given a null DeviceIDVO. It also ensures there was no
     * instance of Sunrise Sensor created, as the method returns null before reaching the creation phase.
     */
    @Test
    void givenNullDeviceIDVO_createSensorReturnsNull(){
        // Arrange
        String path = "config.properties";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        try(MockedConstruction<SunriseSensor> mockedConstruction = mockConstruction(SunriseSensor.class)) {
            SensorFactoryImpl factory = new SensorFactoryImpl(path);

            // Act
            SunriseSensor sensorResult = (SunriseSensor) factory.createSensor(sensorName,null,sensorType);
            int numberMockedConstructions = mockedConstruction.constructed().size();

            // Assert
            assertNull(sensorResult);
            assertEquals(0,numberMockedConstructions);
        }
    }

    /**
     * This test ensures createSensor returns null when given a null SensorTypeIDVO. It also ensures there was no
     * instance of Sunrise Sensor created, as the method returns null before reaching the creation phase.
     */
    @Test
    void givenNullSensorTypeID_createSensorReturnsNull(){
        // Arrange
        String path = "config.properties";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        try(MockedConstruction<SunriseSensor> mockedConstruction = mockConstruction(SunriseSensor.class)) {
            SensorFactoryImpl factory = new SensorFactoryImpl(path);

            // Act
            SunriseSensor sensorResult = (SunriseSensor) factory.createSensor(sensorName,deviceID,null);
            int numberMockedConstructions = mockedConstruction.constructed().size();

            // Assert
            assertNull(sensorResult);
            assertEquals(0,numberMockedConstructions);
        }
    }

    /**
     * This test ensures createSensor returns null when a valid SensorTypeIDVO returns a null when called with getID().
     * It also ensures there was no instance of Sunrise Sensor created, as the method returns null before reaching the
     * creation phase.
     */
    @Test
    void givenSensorTypeIDVO_whenGetIDFromVOreturnsNull_createSensorReturnsNull(){
        // Arrange
        String path = "config.properties";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        when(sensorType.getID()).thenReturn(null);

        try(MockedConstruction<SunriseSensor> mockedConstruction = mockConstruction(SunriseSensor.class)) {
            SensorFactoryImpl factory = new SensorFactoryImpl(path);

            // Act
            SunriseSensor sensorResult = (SunriseSensor) factory.createSensor(sensorName,deviceID,sensorType);
            int numberMockedConstructions = mockedConstruction.constructed().size();

            // Assert
            assertNull(sensorResult);
            assertEquals(0,numberMockedConstructions);
        }
    }

    /**
     * This test ensures createSensor returns null when a valid SensorTypeIDVO returns an invalid ID when called with getID().
     * It also ensures there was no instance of Sunrise Sensor created, as the method returns null before reaching the
     * creation phase.
     */
    @Test
    void givenSensorTypeIDVO_whenGetIDFromVOreturnsInvalidType_createSensorReturnsNull(){
        // Arrange
        String path = "config.properties";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        when(sensorType.getID()).thenReturn("Nuclear");

        try(MockedConstruction<SunriseSensor> mockedConstruction = mockConstruction(SunriseSensor.class)) {
            SensorFactoryImpl factory = new SensorFactoryImpl(path);

            // Act
            SunriseSensor sensorResult = (SunriseSensor) factory.createSensor(sensorName,deviceID,sensorType);
            int numberMockedConstructions = mockedConstruction.constructed().size();

            // Assert
            assertNull(sensorResult);
            assertEquals(0,numberMockedConstructions);
        }
    }

    /*
    SYSTEM UNDER TEST: FACTORY + SENSOR
    A double of all the other collaborators is done (essentially the required value objects to create the sensor).
    */

    /**
     * Test method to verify the behavior of createSensor when provided with a null SensorNameVO.
     * This method tests whether the createSensor method of SensorFactory returns null when
     * a null SensorNameVO is passed as an argument.
     * Steps:
     * 1. Arrange: Set SensorNameVO to null and double DeviceIDVO and SensorTypeIDVO.
     * 2. Act: Call the createSensor method of SensorFactory with null SensorNameVO.
     * 3. Assert: Verify that the returned sensor is null.
     */

    @Test
    void createSensor_WhenNullSensorNameVO_shouldReturnNull(){
        //Arrange
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        Sensor sensor = sensorFactoryImpl.createSensor(null,expectedDeviceID,expectedSensorTypeID);

        //Assert
        assertNull(sensor);
    }

    /**
     * Test method to verify the behavior of createSensor when provided with a null DeviceIDVO.
     * This method tests whether the createSensor method of SensorFactory returns null when
     * a null DeviceIDVO is passed as an argument.
     */

    @Test
    void createSensor_WhenNullDeviceIDVO_shouldReturnNull(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        Sensor sensor = sensorFactoryImpl.createSensor(expectedName,null,expectedSensorTypeID);

        //Assert
        assertNull(sensor);
    }

    /**
     * Test method to verify the behavior of createSensor when provided with a null SensorTypeIDVO.
     * This method tests whether the createSensor method of SensorFactory returns null when
     * a null SensorTypeIDVO is passed as an argument.
     */

    @Test
    void createSensor_WhenNullSensorTypeIDVO_shouldReturnNull(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        Sensor sensor = sensorFactoryImpl.createSensor(expectedName,expectedDeviceID,null);

        //Assert
        assertNull(sensor);
    }

    /**
     * Tests the behavior of the createSensor method in the SensorFactoryImpl class when provided with non-permitted sensor types.
     * <p>
     * This test verifies that the createSensor method of the SensorFactoryImpl class returns null when called with sensor type IDs
     * that are not permitted according to the configuration. It creates three Sensor objects using the factory,
     * each with a different non-permitted sensor type ID ("CO2Sensor", "NuclearSensor", "RotationSensor").
     * The test ensures that all three calls to createSensor return null, indicating that the factory correctly rejects
     * creation of sensors with non-permitted types.
     * </p>
     */
    @Test
    void whenNonPermittedSensorType_createSensorshouldReturnNull(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID =  mock(DeviceIDVO.class);

        SensorTypeIDVO expectedSensorTypeID1 = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID1.getID()).thenReturn("CO2Sensor");

        SensorTypeIDVO expectedSensorTypeID2 = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID2.getID()).thenReturn("NuclearSensor");

        SensorTypeIDVO expectedSensorTypeID3 = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID3.getID()).thenReturn("RotationSensor");

        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        Sensor sensor1 = sensorFactoryImpl.createSensor(expectedName,expectedDeviceID,expectedSensorTypeID1);
        Sensor sensor2 = sensorFactoryImpl.createSensor(expectedName,expectedDeviceID,expectedSensorTypeID2);
        Sensor sensor3 = sensorFactoryImpl.createSensor(expectedName,expectedDeviceID,expectedSensorTypeID3);

        //Assert
        assertNull(sensor1);
        assertNull(sensor2);
        assertNull(sensor3);
    }

    /**
     * Test method to verify the creation of a TemperatureSensor object with valid value objects.
     * This method tests whether the TemperatureSensor object is correctly created by the SensorFactory class when the
     * second createSensor method is called with valid SensorIDVO, SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the second createSensor method of SensorFactory to create a TemperatureSensor object.
     * 3. Assert: Verify that the created TemperatureSensor object has the expected attributes.
     */

    @Test
    void givenValidParameters_createSensorReturnsSensorObject() {
        // Arrange
        String path = "config.properties";
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        when(sensorType.getID()).thenReturn("TemperatureSensor");
        SensorFactoryImpl factory = new SensorFactoryImpl(path);

        // Act
        TemperatureSensor sensorResult = (TemperatureSensor) factory.createSensor(sensorID, sensorName, deviceID, sensorType);
        SensorNameVO resultName = sensorResult.getSensorName();
        DeviceIDVO resultDeviceID = sensorResult.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = sensorResult.getSensorTypeID();
        SensorIDVO resultSensorID = sensorResult.getId();

        // Assert
        assertEquals(sensorName, resultName);
        assertEquals(deviceID, resultDeviceID);
        assertEquals(sensorID, resultSensorID);
        assertEquals(sensorType, resultSensorTypeID);
    }


    /**
     * Test method to verify the behavior of the second createSensor method when provided with a non permitted sensor type.
     * By non permitted we mean, a sensor type that is not present in config.properties.
     * This method tests whether the second createSensor method of SensorFactory returns null when a SensorTypeIDVO
     * is non permitted.
     */
    @Test
    void givenNonPermittedSensorType_createSensorShouldReturnNull() {
        //Arrange
        String path = "config.properties";
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorIDVO expectedSensorID = mock(SensorIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("RotationSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl(path);

        //Act
        Sensor sensor = sensorFactoryImpl.createSensor(expectedSensorID, expectedName, expectedDeviceID, expectedSensorTypeID);

        //Assert
        assertNull(sensor);
    }


    /**
     * Test method to verify the creation of a TemperatureSensor object with valid value objects.
     * This method tests whether the TemperatureSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a TemperatureSensor object.
     * 3. Assert: Verify that the created TemperatureSensor object has the expected attributes.
     */
    @Test
    void createTemperatureSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("TemperatureSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        TemperatureSensor temperatureSensor = (TemperatureSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = temperatureSensor.getSensorName();
        DeviceIDVO resultDeviceID = temperatureSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = temperatureSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a HumiditySensor object with valid value objects.
     * This method tests whether the HumiditySensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a HumiditySensor object.
     * 3. Assert: Verify that the created HumiditySensor object has the expected attributes.
     */

    @Test
    void createHumiditySensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("HumiditySensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        HumiditySensor humiditySensor = (HumiditySensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = humiditySensor.getSensorName();
        DeviceIDVO resultDeviceID = humiditySensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = humiditySensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a PositionSensor object with valid value objects.
     * This method tests whether the PositionSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a PositionSensor object.
     * 3. Assert: Verify that the created PositionSensor object has the expected attributes.
     */

    @Test
    void createPositionSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("PositionSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        PositionSensor positionSensor = (PositionSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = positionSensor.getSensorName();
        DeviceIDVO resultDeviceID = positionSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = positionSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a WindSensor object with valid value objects.
     * This method tests whether the WindSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a WindSensor object.
     * 3. Assert: Verify that the created WindSensor object has the expected attributes.
     */

    @Test
    void createWindSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("WindSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        WindSensor windSensor = (WindSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = windSensor.getSensorName();
        DeviceIDVO resultDeviceID = windSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = windSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a DewPointSensor object with valid value objects.
     * This method tests whether the DewPointSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a DewPointSensor object.
     * 3. Assert: Verify that the created DewPointSensor object has the expected attributes.
     */

    @Test
    void createDewPointSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("DewPointSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        DewPointSensor dewPointSensor = (DewPointSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = dewPointSensor.getSensorName();
        DeviceIDVO resultDeviceID = dewPointSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = dewPointSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a SunsetSensor object with valid value objects.
     * This method tests whether the SunsetSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a SunsetSensor object.
     * 3. Assert: Verify that the created SunsetSensor object has the expected attributes.
     */

    @Test
    void createSunsetSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("SunsetSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        SunsetSensor sunsetSensor = (SunsetSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = sunsetSensor.getSensorName();
        DeviceIDVO resultDeviceID = sunsetSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = sunsetSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a SunriseSensor object with valid value objects.
     * This method tests whether the SunriseSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a SunriseSensor object.
     * 3. Assert: Verify that the created SunriseSensor object has the expected attributes.
     */

    @Test
    void createSunriseSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("SunriseSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        SunriseSensor sunriseSensor = (SunriseSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = sunriseSensor.getSensorName();
        DeviceIDVO resultDeviceID = sunriseSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = sunriseSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a AveragePowerConsumptionSensor object with valid value objects.
     * This method tests whether the AveragePowerConsumptionSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a AveragePowerConsumptionSensor object.
     * 3. Assert: Verify that the created AveragePowerConsumptionSensor object has the expected attributes.
     */

    @Test
    void createAveragePowerConsumptionSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("AveragePowerConsumptionSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        AveragePowerConsumptionSensor averagePowerConsumptionSensor = (AveragePowerConsumptionSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = averagePowerConsumptionSensor.getSensorName();
        DeviceIDVO resultDeviceID = averagePowerConsumptionSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = averagePowerConsumptionSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a PowerConsumptionSensor object with valid value objects.
     * This method tests whether the PowerConsumptionSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a PowerConsumptionSensor object.
     * 3. Assert: Verify that the created PowerConsumptionSensor object has the expected attributes.
     */

    @Test
    void createPowerConsumptionSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("PowerConsumptionSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        PowerConsumptionSensor powerConsumptionSensor = (PowerConsumptionSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = powerConsumptionSensor.getSensorName();
        DeviceIDVO resultDeviceID = powerConsumptionSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = powerConsumptionSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a SwitchSensor object with valid value objects.
     * This method tests whether the SwitchSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a SwitchSensor object.
     * 3. Assert: Verify that the created SwitchSensor object has the expected attributes.
     */

    @Test
    void createSwitchSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("SwitchSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        SwitchSensor switchSensor = (SwitchSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = switchSensor.getSensorName();
        DeviceIDVO resultDeviceID = switchSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = switchSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * Test method to verify the creation of a SolarIrradianceSensor object with valid value objects.
     * This method tests whether the SolarIrradianceSensor object is correctly created by the SensorFactory class
     * when provided with valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO instances.
     * Steps:
     * 1. Arrange: Double necessary value objects and create an instance of SensorFactory.
     * 2. Act: Call the createSensor method of SensorFactory to create a SolarIrradianceSensor object.
     * 3. Assert: Verify that the created SolarIrradianceSensor object has the expected attributes.
     */

    @Test
    void createSolarIrradianceSensor_WhenValidVOs_shouldReturnCorrectSensor(){
        //Arrange
        SensorNameVO expectedName = mock(SensorNameVO.class);
        DeviceIDVO expectedDeviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expectedSensorTypeID = mock(SensorTypeIDVO.class);
        when(expectedSensorTypeID.getID()).thenReturn("SolarIrradianceSensor");
        SensorFactoryImpl sensorFactoryImpl = new SensorFactoryImpl("config.properties");

        //Act
        SolarIrradianceSensor irradianceSensor = (SolarIrradianceSensor) sensorFactoryImpl.createSensor(expectedName, expectedDeviceID, expectedSensorTypeID);
        SensorNameVO resultName = irradianceSensor.getSensorName();
        DeviceIDVO resultDeviceID = irradianceSensor.getDeviceID();
        SensorTypeIDVO resultSensorTypeID = irradianceSensor.getSensorTypeID();

        //Assert
        assertEquals(expectedName,resultName);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }
}
