package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.EnergyConsumptionValue;
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

class EnergyConsumptionSensorTest {
    /**
     * Test for the constructor of EnergyConsumptionSensor when the SensorNameVO is null.
     * It verifies that when a null SensorNameVO is provided, the constructor throws an IllegalArgumentException.
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
                    new EnergyConsumptionSensor(null, deviceID, sensorType));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test for the constructor of EnergyConsumptionSensor when the SensorNameVO is empty.
     * It verifies that when an empty SensorNameVO is provided, the constructor throws an IllegalArgumentException.
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
                    new EnergyConsumptionSensor(sensorName, null, sensorType));
            String result = exception.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(0, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test for the constructor of EnergyConsumptionSensor when the SensorTypeIDVO is null.
     * It verifies that when a null SensorTypeIDVO is provided, the constructor throws an IllegalArgumentException.
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
                    new EnergyConsumptionSensor(sensorName, deviceID, null));
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
        EnergyConsumptionSensor energyConsumptionSensor = new EnergyConsumptionSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, energyConsumptionSensor.getId());
        assertEquals(sensorNameDouble, energyConsumptionSensor.getSensorName());
        assertEquals(deviceIDDouble, energyConsumptionSensor.getDeviceID());
        assertEquals(sensorTypeDouble, energyConsumptionSensor.getSensorTypeID());
    }

    /**
     * Test for EnergyConsumptionSensor.
     * Given a valid SensorNameVO, DeviceIDVO, and SensorTypeIDVO,
     * when the constructor is called, then the sensor is created.
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsSensorName() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            EnergyConsumptionSensor energyConsumptionSensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            SensorNameVO result = energyConsumptionSensor.getSensorName();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(sensorName, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test for EnergyConsumptionSensor
     * Given a valid SensorNameVO, DeviceIDVO and SensorTypeIDVO, when the constructor is called, then the sensor is created
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsSensorID() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            EnergyConsumptionSensor energyConsumptionSensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            SensorIDVO result = energyConsumptionSensor.getId();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertNotNull(result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test for EnergyConsumptionSensor
     * Given a valid SensorNameVO, DeviceIDVO and SensorTypeIDVO, when the constructor is called, then the sensor is created
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsSensorType() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            EnergyConsumptionSensor energyConsumptionSensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            SensorTypeIDVO result = energyConsumptionSensor.getSensorTypeID();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(sensorType, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test for EnergyConsumptionSensor
     * Given a valid SensorNameVO, DeviceIDVO and SensorTypeIDVO, when the constructor is called, then the sensor is created
     */
    @Test
    void whenSensorIsSuccessfullyCreated_thenReturnsDeviceID() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            //Act
            EnergyConsumptionSensor energyConsumptionSensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            DeviceIDVO result = energyConsumptionSensor.getDeviceID();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(deviceID, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Tests that if simHardware is null, then an IllegalArgumentException is thrown
     */
    @Test
    void whenSimHardwareIsNull_thenExceptionIsThrown() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        SensorValueFactoryImpl factory = mock(SensorValueFactoryImpl.class);
        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            EnergyConsumptionSensor sensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            String expected = "Invalid parameters";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(initialDate, finalDate, null,factory));
            String result = exception.getMessage();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test that if initialDate is after finalDate, then an IllegalArgumentException is thrown
     */
    @Test
    void whenInitialDateIsAfterFinalDate_thenExceptionIsThrown() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        SimHardware simHardware = mock(SimHardware.class);
        SensorValueFactoryImpl factory = mock(SensorValueFactoryImpl.class);
        String initialDate = "17-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            EnergyConsumptionSensor sensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            String expected = "Invalid date";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(initialDate, finalDate, simHardware,factory));
            String result = exception.getMessage();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the initial date is after the current date.
     *
     * <p>
     * This test verifies the behavior of the EnergyConsumptionSensor class's getReading method when provided with an initial date that is after the current date. It expects that attempting to retrieve a reading with such dates will result in an IllegalArgumentException being thrown with the message "Invalid date".
     * </p>
     *
     * <p>
     * The test sets up different scenarios by providing initial dates that are in the future and also include invalid date formats. It expects that all such attempts will result in an IllegalArgumentException.
     * </p>
     *
     * <p>
     * The test uses the assertThrows method to verify that invoking the getReading method with invalid initial dates indeed throws an IllegalArgumentException. It captures the exception messages and compares them with the expected message. Additionally, it verifies that the mocked SensorIDVO object is constructed only once during the test execution.
     * </p>
     */
    @Test
    void whenInitialDateIsAfterCurrentDate_thenExceptionIsThrown() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        SimHardware simHardware = mock(SimHardware.class);
        SensorValueFactoryImpl factory = mock(SensorValueFactoryImpl.class);
        String initialDate1 = "17-12-2050 14:15:45";
        String finalDate1 = "16-12-2070 14:15:45";
        String initialDate2 = "17-12-2020 14:15:45";
        String finalDate2 = "16-12-2070 14:15:45";
        String initialDate3 = "17-12-2020 14:15:45:89";
        String finalDate3 = "16-12-2020 14:15:45";

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            EnergyConsumptionSensor sensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            String expected = "Invalid date";

            //Act
            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(initialDate1, finalDate1, simHardware,factory));
            String result1 = exception1.getMessage();

            Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(initialDate2, finalDate2, simHardware,factory));
            String result2 = exception2.getMessage();

            Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(initialDate3, finalDate3, simHardware,factory));
            String result3 = exception3.getMessage();

            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result1);
            assertEquals(expected, result2);
            assertEquals(expected, result3);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test that if finalDate format is invalid, then an IllegalArgumentException is thrown
     */
    @Test
    void whenFinalDateFormatIsInvalid_thenExceptionIsThrown() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        SimHardware simHardware = mock(SimHardware.class);
        String initialDate = "17-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45:89";
        SensorValueFactoryImpl factory = mock(SensorValueFactoryImpl.class);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {
            EnergyConsumptionSensor sensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);
            String expected = "Invalid date";

            //Act
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    sensor.getReading(initialDate, finalDate, simHardware,factory));
            String result = exception.getMessage();
            List<SensorIDVO> listOfMockedSensorIDVO = mockedConstruction.constructed();

            //Assert
            assertEquals(expected, result);
            assertEquals(1, listOfMockedSensorIDVO.size());
        }
    }

    /**
     * Test case to verify that the getReading method of the EnergyConsumptionSensor class returns the correct value successfully when provided with correct parameters.
     *
     * <p>
     * This test verifies the functionality of the getReading method in the EnergyConsumptionSensor class. The test ensures that when the method is called with valid parameters, it returns the expected value successfully.
     * </p>
     *
     * <p>
     * The test sets up the necessary dependencies for creating an EnergyConsumptionSensor instance, including instances of SensorNameVO, DeviceIDVO, and SensorTypeIDVO, which are value objects (VOs). It also mocks a SimHardware object to simulate hardware behavior.
     * </p>
     *
     * <p>
     * The simulated hardware is configured to return a specific reading for the specified initial and final dates. The test creates a mocked instance of EnergyConsumptionValue, representing the sensor reading, and configures it to return the expected value when its getValue method is called.
     * </p>
     *
     * <p>
     * The test verifies that the getReading method of the EnergyConsumptionSensor instance returns the expected value by asserting that the returned value matches the expected value. It also asserts that the type of the returned SensorValueObject is EnergyConsumptionValue.
     * </p>
     *
     * <p>
     * Additionally, the test ensures that the constructor of the SensorIDVO class is called exactly once during the test execution.
     * </p>
     */
    @Test
    void whenReadingIsCorrect_thenReturnsValueSuccessfully() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String reading = "25";
        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue(initialDate, finalDate)).thenReturn(reading);
        int expected = 25;

        SensorValueObject value = mock(EnergyConsumptionValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading,sensorType)).thenReturn(value);

            EnergyConsumptionSensor sensor = new EnergyConsumptionSensor(sensorName, deviceID, sensorType);

            //Act
            int result = sensor.getReading(initialDate, finalDate, simHardware,valueFactory).getValue();
            Object resultObj = sensor.getReading(initialDate, finalDate, simHardware,valueFactory);

            //Assert
            assertEquals(expected, result);
            assertInstanceOf(EnergyConsumptionValue.class,resultObj);
            assertEquals(1, mockedConstruction.constructed().size());
        }
    }

    /**
     * Test case to verify that the getReading method of the EnergyConsumptionSensor class returns an instance of EnergyConsumptionValue with the correct value when provided with correct parameters.
     *
     * <p>
     * This test focuses on the functionality of the EnergyConsumptionSensor class, which acts as the System Under Test (SUT). It verifies that the getReading method of the EnergyConsumptionSensor class correctly returns an instance of EnergyConsumptionValue with the correct value when provided with correct parameters.
     * </p>
     *
     * <p>
     * The test sets up the necessary dependencies for creating an EnergyConsumptionSensor instance, including instances of SensorNameVO, SensorTypeIDVO, and DeviceIDVO, which are value objects (VOs).
     * It also uses a SimHardware object, which represents a simulated hardware interface, and a SensorValueFactoryImpl object, which is a factory responsible for creating SensorValueObject instances.
     * </p>
     *
     * <p>
     * The simulated hardware is configured to return a specific reading.
     * The value factory is set up using a properties file ("value.properties") containing configuration information.
     * </p>
     *
     * <p>
     * The getReading method of the EnergyConsumptionSensor instance is then invoked with the simulated hardware and the value factory.
     * The test expects the method to return an instance of EnergyConsumptionValue, which represents the energy consumption reading, and the value to match the expected value.
     * </p>
     *
     * <p>
     * The test verifies this behavior by asserting that the type of the returned SensorValueObject is EnergyConsumptionValue, and the value obtained from it matches the expected value.
     * </p>
     */
    @Test
    void whenReadingIsCorrect_thenReturnsEnergyConsumptionValueObject() {
        // Arrange
        SensorNameVO sensorNameDouble = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorTypeDouble = new SensorTypeIDVO("EnergyConsumptionSensor");
        DeviceIDVO deviceIDDouble = new DeviceIDVO(UUID.randomUUID());
        EnergyConsumptionSensor sensor = new EnergyConsumptionSensor(sensorNameDouble, deviceIDDouble, sensorTypeDouble);

        String reading = "25";
        String initialDate = "15-12-2020 14:15:45";
        String finalDate = "16-12-2020 14:15:45";

        int expected = 25;

        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue(initialDate, finalDate)).thenReturn(reading);

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        EnergyConsumptionValue valueResult = sensor.getReading(initialDate, finalDate, simHardware,valueFactory);
        int result = valueResult.getValue();

        // Assert
        assertInstanceOf(EnergyConsumptionValue.class,valueResult);
        assertEquals(expected,result);
    }
}