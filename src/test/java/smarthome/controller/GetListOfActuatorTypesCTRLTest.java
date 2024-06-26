package smarthome.controller;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.service.ActuatorTypeServiceImpl;
import smarthome.domain.actuatortype.ActuatorTypeFactoryImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetListOfActuatorTypesCTRLTest {

    /**
     * Test case to verify that the controller method returns a list of ActuatorTypeDTOs when getListOfActuatorTypes is called.
     *
     * The test performs the following steps:
     * 1. Initializes four ActuatorType instances and adds them to an ActuatorTypeList.
     * 2. Mocks the ActuatorTypeRepository with predefined behavior for the findAll method to return the ActuatorTypeList.
     * 3. Initializes the ActuatorTypeService and Controller.
     * 4. Calls the getListOfActuatorTypes method from the controller.
     * 5. Retrieves the list of ActuatorTypeDTOs returned by the controller.
     * 6. Compares the size of the returned list with the expected size (4).
     * 7. Compares each ActuatorTypeDTO in the returned list with the expected ActuatorTypeIDs.
     * 8. Asserts that the size of the returned list matches the expected size and each ActuatorTypeDTO has the expected ActuatorTypeID.
     */

    @Test
    void whenGetListOfActuatorTypes_thenReturnActuatorTypeDTO() {

        //Arrange
        //Initializing four actuatorType and add to an ActuatorTypeList
        List<ActuatorType> actuatorTypeList = new ArrayList<>();
        Iterable<ActuatorType> actuatorTypeIterable = actuatorTypeList;
        String expectedActuatorTypeID1 = "SwitchActuator";
        String expectedActuatorTypeID2 = "RollerBlindActuator";
        String expectedActuatorTypeID3 = "DecimalValueActuator";
        String expectedActuatorTypeID4 = "IntegerValueActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(expectedActuatorTypeID1);
        ActuatorType actuatorType1 = new ActuatorType(actuatorTypeIDVO1);
        actuatorTypeList.add(actuatorType1);

        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO(expectedActuatorTypeID2);
        ActuatorType actuatorType2 = new ActuatorType(actuatorTypeIDVO2);
        actuatorTypeList.add(actuatorType2);

        ActuatorTypeIDVO actuatorTypeIDVO3 = new ActuatorTypeIDVO(expectedActuatorTypeID3);
        ActuatorType actuatorType3 = new ActuatorType(actuatorTypeIDVO3);
        actuatorTypeList.add(actuatorType3);

        ActuatorTypeIDVO actuatorTypeIDVO4 = new ActuatorTypeIDVO(expectedActuatorTypeID4);
        ActuatorType actuatorType4 = new ActuatorType(actuatorTypeIDVO4);
        actuatorTypeList.add(actuatorType4);

        //Mock to ActuatorTypeRepository and add behaviour to his findAll method
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeRepositoryDouble.findAll()).thenReturn(actuatorTypeIterable);

        //Initializing ActuatorTypeService
        String path = "config.properties";
        ActuatorTypeFactoryImpl actuatorTypeFactory = new ActuatorTypeFactoryImpl();
        ActuatorTypeServiceImpl actuatorTypeService = new ActuatorTypeServiceImpl(actuatorTypeRepositoryDouble, actuatorTypeFactory, path);

        //Initializing Controller
        GetListOfActuatorTypesCTRL getListOfActuatorTypesCTRL = new GetListOfActuatorTypesCTRL(actuatorTypeService);

        //Act
        List<ActuatorTypeDTO> result = getListOfActuatorTypesCTRL.getListOfActuatorTypes();

        //Assert
        assertEquals(4, result.size());
        assertEquals(expectedActuatorTypeID1, result.get(0).getActuatorTypeID());
        assertEquals(expectedActuatorTypeID2, result.get(1).getActuatorTypeID());
        assertEquals(expectedActuatorTypeID3, result.get(2).getActuatorTypeID());
        assertEquals(expectedActuatorTypeID4, result.get(3).getActuatorTypeID());
    }

    /**
     * Test case to verify that an IllegalArgumentException is thrown when the ActuatorTypeService is null in the controller constructor.
     *
     * The test performs the following steps:
     * 1. Attempts to instantiate the controller with a null ActuatorTypeService.
     * 2. Uses assertThrows to verify that an IllegalArgumentException is thrown.
     * 3. Compares the message of the thrown exception with the expected message "Invalid service".
     *
     * @throws IllegalArgumentException If the ActuatorTypeService passed to the controller constructor is null.
     */
    @Test
    void givenNullActuatorTypeService_whenGetListOfActuatorTypes_thenThrowIllegalArgumentException() {
        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new GetListOfActuatorTypesCTRL(null));
        assertEquals("Invalid service", thrown.getMessage());
    }
}
