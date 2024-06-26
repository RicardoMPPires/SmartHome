package smarthome.mapper.assembler;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.*;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActuatorAssemblerTest {


    private static String filePath;

    @BeforeAll
    public static void setUp() {
        filePath = "config.properties";
    }

    /**
     * Test to verify if the ActuatorAssembler can convert an ActuatorDataModel object to a persisted Integer Actuator object.
     * The test creates an ActuatorDataModel object with valid parameters and then converts it to an Integer Actuator object.
     * The Actuator object is created with the same parameters as the ActuatorDataModel object.
     * @throws ConfigurationException If a file with actuator properties is not found
     */
    @Test
    void givenValidDataModel_whenToDomain_thenReturnIntegerActuator() throws ConfigurationException {
        //Arrange
        ActuatorFactoryImpl actuatorFactory  = new ActuatorFactoryImpl(filePath);
        ActuatorIDVO actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO actuatorTypeID = new ActuatorTypeIDVO("IntegerValueActuator");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        Settings integerSettings = new IntegerSettingsVO("1", "3");
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");
        Actuator integerActuator = new IntegerValueActuator(actuatorID, actuatorName, actuatorTypeID, deviceID, integerSettings,statusVO);
        ActuatorDataModel dataModel = new ActuatorDataModel(integerActuator);

        //Act
        Actuator actuator = ActuatorAssembler.toDomain(actuatorFactory, dataModel);

        //Assert
        assertEquals(dataModel.getActuatorID(), actuator.getId().getID());
        assertEquals(dataModel.getActuatorName(), actuator.getActuatorName().getValue());
        assertEquals(dataModel.getActuatorTypeID(), actuator.getActuatorTypeID().getID());
        assertEquals(dataModel.getDeviceID(), actuator.getDeviceID().getID());
        assertEquals(dataModel.getLowerLimit(), actuator.getLowerLimit());
        assertEquals(dataModel.getUpperLimit(), actuator.getUpperLimit());
        assertEquals(dataModel.getPrecision_value(), actuator.getPrecision());
    }

    /**
     * Test to verify if the ActuatorAssembler can convert an ActuatorDataModel object to a persisted Decimal Actuator object.
     * The test creates an ActuatorDataModel object with valid parameters and then converts it to a Decimal Actuator object.
     * The Actuator object is created with the same parameters as the ActuatorDataModel object.
     * @throws ConfigurationException If file with actuator properties is not found
     */
    @Test
    void givenValidDataModel_whenToDomain_thenReturnDecimalActuator() throws ConfigurationException {
        //Arrange
          ActuatorFactoryImpl actuatorFactory  = new ActuatorFactoryImpl(filePath);
        ActuatorIDVO actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO actuatorTypeID = new ActuatorTypeIDVO("DecimalValueActuator");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        Settings decimalSettings = new DecimalSettingsVO("1", "3", "0.1");
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");
        Actuator decimalActuator = new DecimalValueActuator(actuatorID, actuatorName, actuatorTypeID, deviceID, decimalSettings,statusVO);
        ActuatorDataModel dataModel = new ActuatorDataModel(decimalActuator);

        //Act
        Actuator actuator = ActuatorAssembler.toDomain(actuatorFactory, dataModel);

        //Assert
        assertEquals(dataModel.getActuatorID(), actuator.getId().getID());
        assertEquals(dataModel.getActuatorName(), actuator.getActuatorName().getValue());
        assertEquals(dataModel.getActuatorTypeID(), actuator.getActuatorTypeID().getID());
        assertEquals(dataModel.getDeviceID(), actuator.getDeviceID().getID());
        assertEquals(dataModel.getLowerLimit(), actuator.getLowerLimit());
        assertEquals(dataModel.getUpperLimit(), actuator.getUpperLimit());
        assertEquals(dataModel.getPrecision_value(), actuator.getPrecision());
    }

    /**
     * Test to verify if the ActuatorAssembler can convert an ActuatorDataModel object to a persisted Switch Actuator object.
     * The test creates an ActuatorDataModel object with valid parameters and then converts it to a Switch Actuator object.
     * The Actuator object is created with the same parameters as the ActuatorDataModel object.
     * @throws ConfigurationException If file with actuator properties is not found
     */
    @Test
    void givenValidDataModel_whenToDomain_thenReturnSwitchActuator() throws ConfigurationException {
        //Arrange
          ActuatorFactoryImpl actuatorFactory  = new ActuatorFactoryImpl(filePath);
        ActuatorIDVO actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO actuatorTypeID = new ActuatorTypeIDVO("SwitchActuator");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");
        Actuator switchActuator = new SwitchActuator(actuatorID, actuatorName, actuatorTypeID, deviceID, statusVO);
        ActuatorDataModel dataModel = new ActuatorDataModel(switchActuator);

        //Act
        Actuator actuator = ActuatorAssembler.toDomain(actuatorFactory, dataModel);

        //Assert
        assertEquals(dataModel.getActuatorID(), actuator.getId().getID());
        assertEquals(dataModel.getActuatorName(), actuator.getActuatorName().getValue());
        assertEquals(dataModel.getActuatorTypeID(), actuator.getActuatorTypeID().getID());
        assertEquals(dataModel.getDeviceID(), actuator.getDeviceID().getID());
        assertEquals(dataModel.getLowerLimit(), actuator.getLowerLimit());
        assertEquals(dataModel.getUpperLimit(), actuator.getUpperLimit());
        assertEquals(dataModel.getPrecision_value(), actuator.getPrecision());
    }

    /**
     * Test to verify if the ActuatorAssembler can convert an ActuatorDataModel object to a Roller Blind Actuator object.
     * The test creates an ActuatorDataModel object with valid parameters and then converts it to a persisted Roller Blind Actuator object.
     * The Actuator object is created with the same parameters as the ActuatorDataModel object.
     * @throws ConfigurationException If file with actuator properties is not found
     */
    @Test
    void givenValidDataModel_whenToDomain_thenReturnRollerBlindActuator() throws ConfigurationException {
        //Arrange
          ActuatorFactoryImpl actuatorFactory  = new ActuatorFactoryImpl(filePath);
        ActuatorIDVO actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO actuatorTypeID = new ActuatorTypeIDVO("RollerBlindActuator");
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");
        Actuator rollerBlindActuator = new SwitchActuator(actuatorID, actuatorName, actuatorTypeID, deviceID, statusVO);
        ActuatorDataModel dataModel = new ActuatorDataModel(rollerBlindActuator);

        //Act
        Actuator actuator = ActuatorAssembler.toDomain(actuatorFactory, dataModel);

        //Assert
        assertEquals(dataModel.getActuatorID(), actuator.getId().getID());
        assertEquals(dataModel.getActuatorName(), actuator.getActuatorName().getValue());
        assertEquals(dataModel.getActuatorTypeID(), actuator.getActuatorTypeID().getID());
        assertEquals(dataModel.getDeviceID(), actuator.getDeviceID().getID());
        assertEquals(dataModel.getLowerLimit(), actuator.getLowerLimit());
        assertEquals(dataModel.getUpperLimit(), actuator.getUpperLimit());
        assertEquals(dataModel.getPrecision_value(), actuator.getPrecision());
    }

    /**
     * Test to verify if the ActuatorAssembler can convert a list of ActuatorDataModel objects to a list of Actuator objects.
     * The test creates a list of ActuatorDataModel objects with valid parameters and then converts it to a list of Actuator objects.
     *
     * @throws ConfigurationException If a file with actuator properties is not found
     */
    @Test
    void givenListOfDataModels_whenToDomainList_thenReturnListOfActuators() throws ConfigurationException {
        //Arrange
          ActuatorFactoryImpl actuatorFactory  = new ActuatorFactoryImpl(filePath);

        // Create Integer Actuator
        ActuatorIDVO first_actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO first_actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO first_actuatorTypeID = new ActuatorTypeIDVO("IntegerValueActuator");
        DeviceIDVO first_deviceID = new DeviceIDVO(UUID.randomUUID());
        Settings integerSettings = new IntegerSettingsVO("1", "3");
        ActuatorStatusVO first_statusVO = new ActuatorStatusVO("This is valid");
        Actuator integerActuator = new IntegerValueActuator(first_actuatorID,first_actuatorName,first_actuatorTypeID,
                first_deviceID, integerSettings, first_statusVO);

        // create Decimal Actuator
        ActuatorIDVO second_actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO second_actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO second_actuatorTypeID = new ActuatorTypeIDVO("DecimalValueActuator");
        DeviceIDVO second_deviceID = new DeviceIDVO(UUID.randomUUID());
        Settings decimalSettings = new DecimalSettingsVO("1", "3", "0.1");
        ActuatorStatusVO second_statusVO = new ActuatorStatusVO("This is valid");
        Actuator decimalActuator = new DecimalValueActuator(second_actuatorID,second_actuatorName,second_actuatorTypeID,
                second_deviceID, decimalSettings, second_statusVO);

        // create Switch Actuator
        ActuatorIDVO third_actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO third_actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO third_actuatorTypeID = new ActuatorTypeIDVO("SwitchActuator");
        DeviceIDVO third_deviceID = new DeviceIDVO(UUID.randomUUID());
        ActuatorStatusVO third_StatusVO = new ActuatorStatusVO("This is valid");
        Actuator switchActuator = new SwitchActuator(third_actuatorID, third_actuatorName, third_actuatorTypeID,
                third_deviceID,third_StatusVO);


        // create Roller Blind Actuator
        ActuatorIDVO fourth_actuatorID = new ActuatorIDVO(UUID.randomUUID());
        ActuatorNameVO fourth_actuatorName = new ActuatorNameVO("actuatorName");
        ActuatorTypeIDVO fourth_actuatorTypeID = new ActuatorTypeIDVO("RollerBlindActuator");
        DeviceIDVO fourth_deviceID = new DeviceIDVO(UUID.randomUUID());
        ActuatorStatusVO fourth_statusVO = new ActuatorStatusVO("This is valid");
        Actuator rollerBlindActuator = new SwitchActuator(fourth_actuatorID, fourth_actuatorName, fourth_actuatorTypeID,
                fourth_deviceID, fourth_statusVO);

        // create Data Models of each actuator
        ActuatorDataModel integerDataModel = new ActuatorDataModel(integerActuator);
        ActuatorDataModel decimalDataModel = new ActuatorDataModel(decimalActuator);
        ActuatorDataModel switchDataModel = new ActuatorDataModel(switchActuator);
        ActuatorDataModel rollerBlindDataModel = new ActuatorDataModel(rollerBlindActuator);

        // create a list of data models
        List<ActuatorDataModel> dataModels = new ArrayList<>();
        dataModels.add(integerDataModel);
        dataModels.add(decimalDataModel);
        dataModels.add(switchDataModel);
        dataModels.add(rollerBlindDataModel);

        //Act
        Iterable<Actuator> actuatorToDomain = ActuatorAssembler.toDomainList(actuatorFactory, dataModels);

        //Assert
        List<Actuator> result = new ArrayList<>();
        actuatorToDomain.forEach(result::add);
        assertEquals(dataModels.get(0).getActuatorID(), result.get(0).getId().getID());
        assertEquals(dataModels.get(1).getActuatorName(), result.get(1).getActuatorName().getValue());
        assertEquals(dataModels.get(2).getActuatorTypeID(), result.get(2).getActuatorTypeID().getID());
        assertEquals(dataModels.get(3).getDeviceID(), result.get(3).getDeviceID().getID());
        assertEquals(dataModels.get(0).getLowerLimit(), result.get(0).getLowerLimit());
        assertEquals(dataModels.get(0).getUpperLimit(), result.get(0).getUpperLimit());
        assertEquals(dataModels.get(1).getPrecision_value(), result.get(1).getPrecision());
    }
}