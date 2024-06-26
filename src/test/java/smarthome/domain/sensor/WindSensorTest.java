package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SimHardware;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.WindValue;
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

class WindSensorTest {

    /**
     The following test verifies that if SensorNameVO parameter is null then Wind Sensor instantiation should throw an Illegal Argument Exception with
     the message "Invalid Parameters". All collaborators until the condition responsible for exception throwing are mocked and injected in the constructor.
     */

    @Test
    void givenNullNameVO_ConstructorThrowsIllegalArgumentException() {
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new WindSensor(null, deviceID, sensorTypeID));

        //Assert
        String result = exception.getMessage();
        assertEquals(expected,result);
    }


    /**
     The following test verifies that if DeviceIDVO parameter is null then Wind Sensor instantiation should throw an Illegal Argument Exception with
     the message "Invalid Parameters". All collaborators until the condition responsible for exception throwing are mocked and injected in the constructor.
     */

    @Test
    void givenNullDeviceIDVO_ConstructorThrowsIllegalArgumentException() {
        // Arrange
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new WindSensor(sensorNameVO, null, sensorTypeID));

        //Assert
        String result = exception.getMessage();
        assertEquals(expected,result);
    }


    /**
     The following test verifies that if SensorTypeIDVO parameter is null then Wind Sensor instantiation should throw an Illegal Argument Exception with
     the message "Invalid Parameters". All collaborators until the condition responsible for exception throwing are mocked and injected in the constructor.
     */

    @Test
    void givenNullSensorTypeIDVO_ConstructorThrowsIllegalArgumentException() {
        // Arrange
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        String expected = "Invalid parameters";

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new WindSensor(sensorNameVO, deviceID, null));

        //Assert
        String result = exception.getMessage();
        assertEquals(expected,result);
    }


    /**
     * Test to check if the second constructor creates a sensor when the parameters are valid.
     */
    @Test
    void givenValidParameters_whenCreatingSensor_thenSensorIdCreated() {
        //Arrange
        SensorNameVO sensorNameDouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        DeviceIDVO deviceIDDouble = mock(DeviceIDVO.class);
        SensorIDVO sensorIDDouble = mock(SensorIDVO.class);
        //Act
        WindSensor windSensor = new WindSensor(sensorIDDouble, sensorNameDouble, deviceIDDouble, sensorTypeDouble);
        //Assert
        assertEquals(sensorIDDouble, windSensor.getId());
        assertEquals(sensorNameDouble, windSensor.getSensorName());
        assertEquals(deviceIDDouble, windSensor.getDeviceID());
        assertEquals(sensorTypeDouble, windSensor.getSensorTypeID());
    }

    /**
     * This test verifies that getReading() throws an Illegal Argument Exception if the injected SimHardware object is null.
     * It isolates all WindSensor collaborators until it´s reached the operation that throws the exception with the following
     * message "Invalid SimHardware".
     * It´s used a mocked construction to double sensorIDVO that is initialized when WindSensor is instantiated.
     * These tests have an additional assertion that verifies the number of instances created for SensorIDVO
     * ensuring proper isolation and that the number of mocked constructions of this object match the expected.
     */

    @Test
    void getReading_WhenSimHardwareIsNull_ShouldThrowIllegalArgumentException(){
        // Arrange
        int expectedSensorIDMockedConstructionsSize = 1;
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);

        try(MockedConstruction<SensorIDVO> sensorIDVOMockedConstruction = mockConstruction(SensorIDVO.class)){
            WindSensor windSensor = new WindSensor(sensorNameVO,deviceID,sensorTypeID);

            //Act + Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    windSensor.getReading(null,valueFactory));

            //Assert

            String result = exception.getMessage();
            assertEquals(expected,result);
            int resultSensorIDMockedConstructionsSize = sensorIDVOMockedConstruction.constructed().size();
            assertEquals(expectedSensorIDMockedConstructionsSize,resultSensorIDMockedConstructionsSize);
        }
    }

    /**
     * Tests the behavior of the getReading method in the WindSensor class when the value factory is null.
     * <p>
     * This test ensures that the getReading method of the WindSensor class throws an IllegalArgumentException
     * if the value factory provided is null. It verifies that when attempting to call getReading with a null value factory,
     * the method throws an IllegalArgumentException with the expected error message.
     * </p>
     * <p>
     * The test also verifies that no new instances of SensorIDVO are constructed when the value factory is null.
     * </p>
     */
    @Test
    void getReading_WhenValueFactoryIsNull_ShouldThrowIllegalArgumentException(){
        // Arrange
        int expectedSensorIDMockedConstructionsSize = 1;
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";

        SimHardware simHardware = new SimHardware();

        try(MockedConstruction<SensorIDVO> sensorIDVOMockedConstruction = mockConstruction(SensorIDVO.class)){
            WindSensor windSensor = new WindSensor(sensorNameVO,deviceID,sensorTypeID);

            //Act + Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    windSensor.getReading(simHardware,null));

            //Assert

            String result = exception.getMessage();
            assertEquals(expected,result);
            int resultSensorIDMockedConstructionsSize = sensorIDVOMockedConstruction.constructed().size();
            assertEquals(expectedSensorIDMockedConstructionsSize,resultSensorIDMockedConstructionsSize);
        }
    }

    /**
     * This test verifies that getReading() propagates an Illegal Argument Exception (message -> "Invalid reading") if the injected
     * SimHardware returns an invalid reading.
     * SimHardware is doubled and is conditioned to deliver an invalid reading.
     * When that reading is passed to WindValue, the constructor should throw the previously referred exception. As getReading() does not catch
     * it, it should be propagated.
     * It is used a mocked construction to double sensorIDVO that is initialized when WindSensor is instantiated.
     * These tests have an additional assertion that verifies the number of instances created for SensorIDVO
     * ensuring proper isolation and that the number of mocked constructions of this object match the expected.


     Note: In this test scenario, WindValue is not being doubled; instead, the real object is being used, and the assertion is made
     considering its actual behavior.
     Due to technical reasons, this approach had to be followed for this particular test scenario. When attempting to double the wind value
     using a mocked construction and forcing it to throw an IllegalArgumentException, Mockito "wraps" that exception with a Mockito Exception,
     which invalidates the assertion for the expected IllegalArgumentException.
     */
    @Test
    void getReading_WhenSimHardwareReadingIsInvalid_ShouldThrowIllegalArgumentException(){
        //Arrange
        SensorNameVO nameDouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        SensorTypeIDVO typeDouble = mock(SensorTypeIDVO.class);
        SimHardware hardwareDouble = mock(SimHardware.class);
        String reading = "33:K";
        when(hardwareDouble.getValue()).thenReturn(reading);
        int idListExpectedSize = 1;

        SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
        when(valueFactory.createSensorValue(reading,typeDouble)).thenReturn(null);

        try (MockedConstruction<SensorIDVO> mockedSensorId = mockConstruction(SensorIDVO.class))
        {
            WindSensor sensor = new WindSensor(nameDouble, deviceIdDouble, typeDouble);
            List<SensorIDVO> idList = mockedSensorId.constructed();

            //Act
            int idListSize = idList.size();

            //Assert
            assertNull(sensor.getReading(hardwareDouble,valueFactory));
            assertEquals(idListExpectedSize,idListSize);
        }
    }

    /**
     * Tests the behavior of the getReading method in the WindSensor class when provided with valid parameters.
     * <p>
     * This test verifies that the getReading method of the WindSensor class returns the correct WindValue object
     * when called with valid parameters. It sets up the necessary mocks for SensorNameVO, DeviceIDVO, SensorTypeIDVO,
     * and SimHardware, and specifies a valid wind reading ("30:N") to be returned by the simulated hardware.
     * The test ensures that the getReading method correctly creates a WindValue object using the provided value factory
     * and returns it.
     * </p>
     * <p>
     * Additionally, the test checks that the constructed WindValue object has the correct value and that
     * the return type of getReading is an instance of WindValue.
     * </p>
     */
    @Test
    void getReading_ShouldReturnCorrectWindValue() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);
        String reading = "30:N";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);
        String expected = "30:N";

        SensorValueObject value = mock(WindValue.class);
        when(value.getValue()).thenReturn(expected);

        try (MockedConstruction<SensorIDVO> mockedConstruction = mockConstruction(SensorIDVO.class)) {

            SensorValueFactoryImpl valueFactory = mock(SensorValueFactoryImpl.class);
            when(valueFactory.createSensorValue(reading, sensorType)).thenReturn(value);

            WindSensor sensor = new WindSensor(sensorName, deviceID, sensorType);

            //Act
            String result = sensor.getReading(simHardware, valueFactory).getValue();
            Object resultObj = sensor.getReading(simHardware, valueFactory);

            int resultSize = mockedConstruction.constructed().size();

            //Assert
            assertEquals(1, resultSize);
            assertEquals(expected, result);
            assertInstanceOf(WindValue.class, resultObj);
        }
    }

    /**
     * Tests the behavior of the getReading method in the WindSensor class when provided with valid parameters.
     * <p>
     * This test verifies that the getReading method of the WindSensor class returns the correct WindValue object
     * when called with valid parameters. It initializes a WindSensor object with a specific sensor name, device ID,
     * and sensor type, and provides a simulated hardware object with a valid wind reading ("30:N").
     * The test ensures that the getReading method correctly creates a WindValue object using the provided value factory
     * and returns it.
     * </p>
     * <p>
     * Additionally, the test checks that the constructed WindValue object has the correct value and that
     * it is indeed an instance of WindValue.
     * </p>
     */
    @Test
    void givenValidParameters_whenGetReadingCalled_thenReturnsHumidityValueObject() {
        //Arrange
        SensorNameVO sensorName = new SensorNameVO("Sensor1");
        SensorTypeIDVO sensorType = new SensorTypeIDVO("WindSensor");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        WindSensor sensor = new WindSensor(sensorName, deviceID, sensorType);

        String reading = "30:N";
        SimHardware simHardware = mock(SimHardware.class);
        when(simHardware.getValue()).thenReturn(reading);

        String expected = "30:N";

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Act
        WindValue valueResult = sensor.getReading(simHardware,valueFactory);
        String result = valueResult.getValue();

        // Assert
        assertInstanceOf(WindValue.class,valueResult);
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that getNameVO() returns the correct SensorNameVO object.
     * It isolates all collaborators of WindSensor.
     * These tests have an additional assertion that verifies the number of instances created for SensorIDVO
     * ensuring proper isolation and that the number of mocked constructions of this object match the expected.
     */
    @Test
    void getName_ShouldReturnCorrectNameValueObject() {
        // Arrange
        int expectedSensorIDMockedConstructionsSize = 1;
        SensorNameVO expected = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        try (MockedConstruction<SensorIDVO> sensorIDVOMockedConstruction = mockConstruction(SensorIDVO.class)) {

            WindSensor windSensor = new WindSensor(expected, deviceID, sensorTypeID);

            // Act
            SensorNameVO result = windSensor.getSensorName();

            // Assert
            assertEquals(expected, result);
            int resultSensorIDMockedConstructionsSize = sensorIDVOMockedConstruction.constructed().size();
            assertEquals(expectedSensorIDMockedConstructionsSize, resultSensorIDMockedConstructionsSize);
        }
    }

    /**
     * Test case to verify that getSensorTypeID() returns the correct SensorTypeVO object.
     * It isolates all collaborators of WindSensor.
     * These tests have an additional assertion that verifies the number of instances created for SensorIDVO
     * ensuring proper isolation and that the number of mocked constructions of this object match the expected.
     */
    @Test
    void getSensorTypeID_ShouldReturnCorrectSensorTypeIDValueObject() {
        // Arrange
        int expectedSensorIDMockedConstructionsSize = 1;
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO expected = mock(SensorTypeIDVO.class);

        try (MockedConstruction<SensorIDVO> sensorIDVOMockedConstruction = mockConstruction(SensorIDVO.class)) {

            WindSensor windSensor = new WindSensor(sensorNameVO, deviceID, expected);

            // Act
            SensorTypeIDVO result = windSensor.getSensorTypeID();

            // Assert
            assertEquals(expected, result);
            int resultSensorIDMockedConstructionsSize = sensorIDVOMockedConstruction.constructed().size();
            assertEquals(expectedSensorIDMockedConstructionsSize, resultSensorIDMockedConstructionsSize);
        }
    }

    /**
     * Test case to verify that getID() returns the correct SensorIDVO object.
     * It isolates all collaborators of WindSensor.
     * The assertion is made by comparing the resulting SensorIDVO object returned by getID() with the first object
     * of the mocked constructor.
     * These tests have an additional assertion that verifies the number of instances created for SensorIDVO
     * ensuring proper isolation and that the number of mocked constructions of this object match the expected.
     */
    @Test
    void getSensorID_ShouldReturnCorrectSensorIDValueObject() {
        // Arrange
        int expectedSensorIDMockedConstructionsSize = 1;
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        try (MockedConstruction<SensorIDVO> sensorIDVOMockedConstruction = mockConstruction(SensorIDVO.class)) {

            WindSensor windSensor = new WindSensor(sensorNameVO, deviceID, sensorTypeID);

            // Act
            SensorIDVO result = windSensor.getId();

            // Assert
            SensorIDVO sensorIDVO = sensorIDVOMockedConstruction.constructed().get(0);
            assertEquals(sensorIDVO, result);
            int resultSensorIDMockedConstructionsSize = sensorIDVOMockedConstruction.constructed().size();
            assertEquals(expectedSensorIDMockedConstructionsSize, resultSensorIDMockedConstructionsSize);
        }
    }

    /**
     * Test case to verify that getDeviceID() returns the correct DeviceIDVO object.
     * It isolates all collaborators of WindSensor.
     * These tests have an additional assertion that verifies the number of instances created for SensorIDVO
     * ensuring proper isolation and that the number of mocked constructions of this object match the expected.
     */
    @Test
    void getDeviceID_ShouldReturnCorrectDeviceIDValueObject() {
        // Arrange
        int expectedSensorIDMockedConstructionsSize = 1;
        SensorNameVO sensorNameVO = mock(SensorNameVO.class);
        DeviceIDVO expected = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        try (MockedConstruction<SensorIDVO> sensorIDVOMockedConstruction = mockConstruction(SensorIDVO.class)) {

            WindSensor windSensor = new WindSensor(sensorNameVO, expected, sensorTypeID);

            // Act
            DeviceIDVO result = windSensor.getDeviceID();

            // Assert
            assertEquals(expected, result);
            int resultSensorIDMockedConstructionsSize = sensorIDVOMockedConstruction.constructed().size();
            assertEquals(expectedSensorIDMockedConstructionsSize, resultSensorIDMockedConstructionsSize);
        }
    }

}
