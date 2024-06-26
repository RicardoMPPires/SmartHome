package smarthome.domain.actuator;

import org.junit.jupiter.api.BeforeAll;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActuatorFactoryImplTest {

    /*
    SYSTEM UNDER TEST: FACTORY + ACTUATOR IMPLEMENTATION
    A double of all the other collaborators is done (which are the required value objects to create the actuator).
     */

    private static String filePath;

    @BeforeAll
    public static void setUp() {
        filePath = "config.properties";
    }

    /**
     * Success Scenario: Switch Actuator is created.
     * Upon actuator attributes request, returns all the expected attributes (ActuatorTypeIDVO, ActuatorNameVO and DeviceIDVO).
     * @throws ConfigurationException If a file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void createSwitchActuator_WhenGetActuatorAttributes_ThenShouldReturnExpectedAttributes() throws ConfigurationException {
        //Arrange
        ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO nameDouble = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeIdDouble = mock(ActuatorTypeIDVO.class);
        when(typeIdDouble.getID()).thenReturn("SwitchActuator");
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);

        Actuator switchActuator = factory.createActuator(nameDouble, typeIdDouble, deviceIdDouble, null);

        //Act
        ActuatorNameVO nameResult = switchActuator.getActuatorName();
        ActuatorTypeIDVO typeIdResult = switchActuator.getActuatorTypeID();
        DeviceIDVO deviceIdResult = switchActuator.getDeviceID();

        //Assert
        assertEquals(nameDouble, nameResult);
        assertEquals(typeIdDouble, typeIdResult);
        assertEquals(deviceIdDouble, deviceIdResult);
    }

    /**
     * Success Scenario: Roller Blind Actuator is created.
     * Upon actuator attributes request, returns all the expected attributes (ActuatorNameVO, ActuatorTypeIDVO and DeviceIDVO).
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void createRollerBlindActuator_WhenGetActuatorAttributes_ThenShouldReturnExpectedAttributes() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO nameDouble = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeIdDouble = mock(ActuatorTypeIDVO.class);
        when(typeIdDouble.getID()).thenReturn("RollerBlindActuator");
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);

        Actuator rollerBlindActuator = factory.createActuator(nameDouble, typeIdDouble, deviceIdDouble, null);

        //Act
        ActuatorNameVO nameResult = rollerBlindActuator.getActuatorName();
        ActuatorTypeIDVO typeIdResult = rollerBlindActuator.getActuatorTypeID();
        DeviceIDVO deviceIdResult = rollerBlindActuator.getDeviceID();

        //Assert
        assertEquals(nameDouble, nameResult);
        assertEquals(typeIdDouble, typeIdResult);
        assertEquals(deviceIdDouble, deviceIdResult);
    }

    /**
     * Success Scenario: Decimal Value Actuator is created.
     * Upon actuator attributes request, returns all the expected attributes (ActuatorNameVO, ActuatorTypeIDVO and DeviceIDVO).
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void createDecimalValueActuator_WhenGetActuatorAttributes_ThenShouldReturnExpectedAttributes() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO nameDouble = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeIdDouble = mock(ActuatorTypeIDVO.class);
        when(typeIdDouble.getID()).thenReturn("DecimalValueActuator");
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        DecimalSettingsVO settingsDouble = mock(DecimalSettingsVO.class);

        Actuator decimalValueActuator = factory.createActuator(nameDouble, typeIdDouble, deviceIdDouble, settingsDouble);

        //Act
        ActuatorNameVO nameResult = decimalValueActuator.getActuatorName();
        ActuatorTypeIDVO typeIdResult = decimalValueActuator.getActuatorTypeID();
        DeviceIDVO deviceIdResult = decimalValueActuator.getDeviceID();

        //Assert
        assertEquals(nameDouble, nameResult);
        assertEquals(typeIdDouble, typeIdResult);
        assertEquals(deviceIdDouble, deviceIdResult);
    }

    /**
     * Success Scenario: Integer Value Actuator is created.
     * Upon actuator attributes request, returns all the expected attributes (ActuatorNameVO, ActuatorTypeIDVO and DeviceIDVO).
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void createIntegerValueActuator_WhenGetActuatorAttributes_ThenShouldReturnExpectedAttributes() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO nameDouble = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO typeIdDouble = mock(ActuatorTypeIDVO.class);
        when(typeIdDouble.getID()).thenReturn("IntegerValueActuator");
        DeviceIDVO deviceIdDouble = mock(DeviceIDVO.class);
        IntegerSettingsVO settingsDouble = mock(IntegerSettingsVO.class);

        Actuator integerValueActuator = factory.createActuator(nameDouble, typeIdDouble, deviceIdDouble, settingsDouble);

        //Act
        ActuatorNameVO nameResult = integerValueActuator.getActuatorName();
        ActuatorTypeIDVO typeIdResult = integerValueActuator.getActuatorTypeID();
        DeviceIDVO deviceIdResult = integerValueActuator.getDeviceID();

        //Assert
        assertEquals(nameDouble, nameResult);
        assertEquals(typeIdDouble, typeIdResult);
        assertEquals(deviceIdDouble, deviceIdResult);
    }


    /**
     * Verifies that when wrong settings are given to create a Decimal Value Actuator, the actuator is not created. There is
     * an attempt to create the Decimal Value Actuator, but when the constructor attempts to cast the settings to a proper
     * DecimalValueVO object, a ClassCastException is thrown by the Actuator constructor. This exception is caught in the factory method and
     * a null is returned.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenWrongSettingsToADecimalValueActuator_WhenCreateActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID.getID()).thenReturn("DecimalValueActuator");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        IntegerSettingsVO settings = mock(IntegerSettingsVO.class);

        //Act
        Actuator result = factory.createActuator(actuatorName, actuatorTypeID, deviceID, settings);

        //Assert
        assertNull(result);
    }

    /**
     * Verifies that when wrong settings are given to create an Integer Value Actuator, the actuator is not created. There is
     * an attempt to create the Integer Value Actuator, but when the constructor attempts to cast the settings to a proper
     * IntegerValueVO object, a ClassCastException is thrown by the Actuator constructor. This exception is caught in the
     * factory method and a null is returned.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenWrongSettingsToAnIntegerValueActuator_WhenCreateActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID.getID()).thenReturn("IntegerValueActuator");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        DecimalSettingsVO settings = mock(DecimalSettingsVO.class);

        //Act
        Actuator result = factory.createActuator(actuatorName, actuatorTypeID, deviceID, settings);

        //Assert
        assertNull(result);
    }


    //ISOLATION TESTS

    /**
     * Verifies if a null ActuatorName VO is given, an IllegalArgumentException is thrown.
     * A double of all injected dependencies is made in order to isolate the ActuatorFactory Class.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void whenNullActuatorName_ThenThrowsIllegalArgumentException() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        String expected = "Invalid actuator parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
           factory.createActuator(null, actuatorTypeID, deviceID, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies if a null ActuatorTypeID VO is given, an IllegalArgumentException is thrown.
     * A double of all injected dependencies is made in order to isolate the ActuatorFactory Class.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void whenNullActuatorTypeID_ThenThrowsIllegalArgumentException() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        String expected = "Invalid actuator parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            factory.createActuator(actuatorName, null, deviceID, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies if a null DeviceID VO is given, an IllegalArgumentException is thrown.
     * A double of all injected dependencies is made in order to isolate the ActuatorFactory Class.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void whenNullDeviceID_ThenThrowsIllegalArgumentException() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        Settings settings = mock(Settings.class);
        String expected = "Invalid actuator parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            factory.createActuator(actuatorName, actuatorTypeID, null, settings));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that actuator type is not mentioned in the accessed file containing actuator details and paths.
     * The Actuator is not created.
     * Since its instantiation is not even initialized, no MockedConstruction is required for this test scenario.
     * Even if valid parameters for Actuator creation are given, the entire operation fails, due to the above-mentioned.
     * A double of all injected dependencies is made in order to isolate the ActuatorFactory Class.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenValidParameters_FileDoesNotContainActuatorType_WhenCreateActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);

        ActuatorTypeIDVO actuatorTypeID1 = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID1.getID()).thenReturn("xptoActuator");

        ActuatorTypeIDVO actuatorTypeID2 = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID2.getID()).thenReturn("HydraulicActuator");

        ActuatorTypeIDVO actuatorTypeID3 = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID3.getID()).thenReturn("NuclearActuator");

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);

        //Act
        Actuator newActuator1 = factory.createActuator(actuatorName, actuatorTypeID1, deviceID, settings);
        Actuator newActuator2 = factory.createActuator(actuatorName, actuatorTypeID2, deviceID, settings);
        Actuator newActuator3 = factory.createActuator(actuatorName, actuatorTypeID3, deviceID, settings);

        //Assert
        assertNull(newActuator1);
        assertNull(newActuator2);
        assertNull(newActuator3);
    }

    /**
     * Verifies that when a null Settings object is given, a DecimalValueActuator is not created, since a proper
     * constructor is not found.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenNullSettings_WhenCreateDecimalValueActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID.getID()).thenReturn("DecimalValueActuator");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        //Act
        Actuator result = factory.createActuator(actuatorName, actuatorTypeID, deviceID, null);

        //Assert
        assertNull(result);
    }

    /**
     * Verifies that when a null Settings object is given, an Integer ValueActuator is not created, since a proper
     constructor is not found.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenNullSettings_WhenCreateIntegerValueActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID.getID()).thenReturn("IntegerValueActuator");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        //Act
        Actuator result = factory.createActuator(actuatorName, actuatorTypeID, deviceID, null);

        //Assert
        assertNull(result);
    }

    /**
     * Verifies that when Settings are given to create an actuator that does not require such parameters, the actuator
     * is not created, since a proper constructor is not found. This test attempts to create a Switch Actuator.
     * @throws ConfigurationException If file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenNonNullSettingsToASwitchActuator_WhenCreateActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID.getID()).thenReturn("SwitchActuator");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);

        //Act
        Actuator result = factory.createActuator(actuatorName, actuatorTypeID, deviceID, settings);

        //Assert
        assertNull(result);
    }

    /**
     * Verifies that when Settings are given to create an actuator that does not require such parameters, the actuator
     * is not created, since a proper constructor is not found. This test attempts to create a Roller Blind Actuator.
     * @throws ConfigurationException If a file path used is invalid (it is encapsulated in the ActuatorFactory Class)
     */
    @Test
    void givenNonNullSettingsToARollerBlindActuator_WhenCreateActuator_ThenShouldReturnNull() throws ConfigurationException {
        //Arrange
         ActuatorFactoryImpl factory = new ActuatorFactoryImpl(filePath);
        ActuatorNameVO actuatorName = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeID.getID()).thenReturn("RollerBlindActuator");
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);

        //Act
        Actuator result = factory.createActuator(actuatorName, actuatorTypeID, deviceID, settings);

        //Assert
        assertNull(result);
    }
}
