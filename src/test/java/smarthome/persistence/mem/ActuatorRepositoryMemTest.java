package smarthome.persistence.mem;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;
import smarthome.domain.vo.devicevo.DeviceIDVO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * When needed, To isolate the ActuatorRepository Class, doubles of the following Classes/Interfaces were created: ActuatorIDVO and Actuator.
 * Actuator Interface was mocked instead of a concrete implementation, to ensure that the test focuses solely on the
 * interaction between the ActuatorRepository and the Actuator interface, instead of depending on specific behaviour
 * of a particular Actuator implementation.
 */
class ActuatorRepositoryMemTest {
    /**
     * Success scenario: Verifies that a new Actuator is saved by the repository.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenNewValidActuator_WhenSave_ThenShouldReturnTrue(){
        //Arrange
        ActuatorIDVO idDouble = mock(ActuatorIDVO.class);
        Actuator actuatorDouble = mock(Actuator.class);
        when(actuatorDouble.getId()).thenReturn(idDouble);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();

        //Act
        boolean result = repository.save(actuatorDouble);

        //Assert
        assertTrue(result);
    }

    /**
     * Verifies that an already existing actuator (Actuator with the same ID) cannot be added twice to the repository.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenDuplicatedActuator_WhenSave_ThenShouldReturnFalse(){
        //Arrange
        ActuatorIDVO idDouble = mock(ActuatorIDVO.class);
        Actuator actuatorDouble = mock(Actuator.class);
        when(actuatorDouble.getId()).thenReturn(idDouble);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDouble);

        //Act
        boolean result = repository.save(actuatorDouble);

        //Assert
        assertFalse(result);
    }

    /**
     * Verifies that an invalid actuator (null) cannot be added to the repository.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenNullActuator_WhenSave_ThenShouldReturnFalse(){
        //Arrange
        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();

        //Act
        boolean result = repository.save(null);

        //Assert
        assertFalse(result);
    }

    /**
     * Verifies that when an empty repository is requested for all of its stored actuators, the size of the retrieved
     * collection is zero.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void havingAnEmptyRepository_WhenFindAll_ThenCollectionSizeShouldBeZero(){
        //Arrange
        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        int expected = 0;

        //Act
        Iterable<Actuator> actuatorCollection = repository.findAll();

        //Assert
        int result = IterableUtils.size(actuatorCollection);
        assertEquals(expected, result);
    }

    /**
     * Verifies that when a non-empty repository is requested for all of its stored actuators, the size of the retrieved
     * collection is the corresponding one. In this case, 3 actuators where previously added, then the size of the
     * collection is 3.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void havingARepositoryWithThreeActuators_WhenFindAll_ThenCollectionSizeShouldBeThree(){
        //Arrange
        //create double IDs
        ActuatorIDVO idDoubleOne = mock(ActuatorIDVO.class);
        ActuatorIDVO idDoubleTwo = mock(ActuatorIDVO.class);
        ActuatorIDVO idDoubleThree = mock(ActuatorIDVO.class);

        //Create double Actuators and conditioning their behaviour
        Actuator actuatorDoubleOne = mock(Actuator.class);
        when(actuatorDoubleOne.getId()).thenReturn(idDoubleOne);
        Actuator actuatorDoubleTwo = mock(Actuator.class);
        when(actuatorDoubleTwo.getId()).thenReturn(idDoubleTwo);
        Actuator actuatorDoubleThree = mock(Actuator.class);
        when(actuatorDoubleThree.getId()).thenReturn(idDoubleThree);

        //Save all the doubles in the repository
        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDoubleOne);
        repository.save(actuatorDoubleTwo);
        repository.save(actuatorDoubleThree);

        int expected = 3;

        //Act
        Iterable<Actuator> actuatorCollection = repository.findAll();

        //Arrange
        int result = IterableUtils.size(actuatorCollection);
        assertEquals(expected, result);
    }

    /**
     * Verifies that when the repository is requested for a specific stored Actuator by passing its ID,
     * the actuator is returned. The Actuator was previously added to ensure that the operation succeeds.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenMatchingID_WhenFindById_ThenShouldReturnTheCorrespondingActuator(){
        //Arrange
        ActuatorIDVO idDouble = mock(ActuatorIDVO.class);
        Actuator actuatorDouble = mock(Actuator.class);
        when(actuatorDouble.getId()).thenReturn(idDouble);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDouble);

        //Act
        Actuator requestedActuator = repository.findById(idDouble);

        //Arrange
        assertEquals(actuatorDouble, requestedActuator);
    }

    /**
     * Verifies that when the repository is requested for a specific Actuator that is not stored,
     * a match is not found and a null is returned.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenANonMatchingID_WhenFindByIdOnANonEmptyRepository_ThenShouldReturnNull(){
        //Arrange
        ActuatorIDVO idDoubleOne = mock(ActuatorIDVO.class);
        ActuatorIDVO idDoubleTwo = mock(ActuatorIDVO.class);

        Actuator actuatorDouble = mock(Actuator.class);
        when(actuatorDouble.getId()).thenReturn(idDoubleOne);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDouble);

        //Act
        Actuator requestedActuator = repository.findById(idDoubleTwo);

        //Arrange
        assertNull(requestedActuator);
    }

    /**
     * Verifies that when the repository is empty and it is requested to retrieve a specific Actuator,
     * a null is returned.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenANonMatchingID_WhenFindByIdOnAnEmptyRepository_ThenShouldReturnNull(){
        //Arrange
        ActuatorIDVO idDouble = mock(ActuatorIDVO.class);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();

        //Act
        Actuator requestedActuator = repository.findById(idDouble);

        //Arrange
        assertNull(requestedActuator);
    }

    /**
     * Verifies that when an ID is given, if the linked actuator is stored in the repository (meaning that an entry-set
     * of that ID(Key)-Actuator(Value) is present) the result is true.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenAnExistingId_WhenIsPresentIsInvoked_ThenShouldReturnTrue(){
        //Arrange
        //create double IDs
        ActuatorIDVO idDoubleOne = mock(ActuatorIDVO.class);
        ActuatorIDVO idDoubleTwo = mock(ActuatorIDVO.class);

        //Create double Actuators and conditioning their behaviour
        Actuator actuatorDoubleOne = mock(Actuator.class);
        when(actuatorDoubleOne.getId()).thenReturn(idDoubleOne);
        Actuator actuatorDoubleTwo = mock(Actuator.class);
        when(actuatorDoubleTwo.getId()).thenReturn(idDoubleTwo);

        //Save all the doubles in the repository
        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDoubleOne);
        repository.save(actuatorDoubleTwo);

        //Act
        boolean result = repository.isPresent(idDoubleTwo);

        //Arrange
        assertTrue(result);
    }

    /**
     * Verifies that when an ID is given, if the linked actuator is not stored in the repository (meaning that an entry-set
     * of that ID(Key)-Actuator(Value) is not present) the result is false.
     * (Please refer to the header of this Class for more information)
     */
    @Test
    void givenANonExistingId_WhenIsPresentIsInvoked_ThenShouldReturnFalse(){
        //Arrange
        //create double IDs
        ActuatorIDVO idDoubleOne = mock(ActuatorIDVO.class);
        ActuatorIDVO idDoubleTwo = mock(ActuatorIDVO.class);
        ActuatorIDVO idDoubleThree = mock(ActuatorIDVO.class);

        //Create double Actuators and conditioning their behaviour
        Actuator actuatorDoubleOne = mock(Actuator.class);
        when(actuatorDoubleOne.getId()).thenReturn(idDoubleOne);
        Actuator actuatorDoubleTwo = mock(Actuator.class);
        when(actuatorDoubleTwo.getId()).thenReturn(idDoubleTwo);

        //Save all the doubles in the repository
        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDoubleOne);
        repository.save(actuatorDoubleTwo);

        //Act
        boolean result = repository.isPresent(idDoubleThree);

        //Arrange
        assertFalse(result);
    }

    /**
     * This test case verifies that a list of actuators associated with a given device ID
     * is returned when the device ID is present in the repository.
     */
    @Test
    void givenAValidDeviceID_whenFindByDeviceID_ShouldReturnListOfActuators() {
        //Arrange
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);

        Actuator actuatorDoubleOne = mock(Actuator.class);
        Actuator actuatorDoubleTwo = mock(Actuator.class);

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        when(actuatorDoubleOne.getId()).thenReturn(actuatorIDVO);
        ActuatorIDVO actuatorIDVO2 = mock(ActuatorIDVO.class);
        when(actuatorDoubleTwo.getId()).thenReturn(actuatorIDVO2);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDoubleOne);
        repository.save(actuatorDoubleTwo);

        when(actuatorDoubleOne.getDeviceID()).thenReturn(deviceIDVO);
        when(actuatorDoubleTwo.getDeviceID()).thenReturn(deviceIDVO);

        //Act
        List<Actuator> actuators = repository.findByDeviceID(deviceIDVO);

        //Assert
        assertEquals(2, actuators.size());
        assertTrue(actuators.contains(actuatorDoubleOne));
        assertTrue(actuators.contains(actuatorDoubleTwo));
    }

    /**
     * This test case verifies that an empty list of actuators is returned when the device ID is not present in the
     * repository.
     */
    @Test
    void givenNonExistentDeviceID_whenFindByDeviceID_ShouldReturnEmptyList() {
        //Arrange
        Actuator actuatorDoubleOne = mock(Actuator.class);
        Actuator actuatorDoubleTwo = mock(Actuator.class);

        ActuatorRepositoryMem repository = new ActuatorRepositoryMem();
        repository.save(actuatorDoubleOne);
        repository.save(actuatorDoubleTwo);

        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);

        //Act
        List<Actuator> actuators = repository.findByDeviceID(deviceIDVO);

        //Assert
        assertEquals(0, actuators.size());
    }

}
