package smarthome.persistence.mem;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorTypeRepositoryMemTest {

    /**
     * Test to check if the actuator type is saved given a null ActuatorType
     * The result should be false
     */
    @Test
    void whenActuatorTypeIsNull_thenTypeIsNotSaved() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();

        //Act
        boolean result = actuatorTypeRepositoryMem.save(null);

        //Assert
        assertFalse(result);
    }

    /**
     * Test to check if the actuator type is saved given a null ActuatorTypeID
     * The result should be false
     */
    @Test
    void whenActuatorTypeIDIsNull_thenTypeNotSaved() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type = mock(ActuatorType.class);
        when(type.getId()).thenReturn(null);

        //Act
        boolean result = actuatorTypeRepositoryMem.save(type);

        //Assert
        assertFalse(result);
    }

    /**
     * Test to check if the actuator type is saved given a valid ActuatorType
     * The result should be true
     */
    @Test
    void whenTypeIsValid_thenTypeIsSaved() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type = mock(ActuatorType.class);
        ActuatorTypeIDVO id = mock(ActuatorTypeIDVO.class);
        when(type.getId()).thenReturn(id);

        //Act
        boolean result = actuatorTypeRepositoryMem.save(type);

        //Assert
        assertTrue(result);
    }

    /**
     * Test to check if the actuator type is saved given a valid ActuatorType
     * Should be able to find the type by its ID
     */
    @Test
    void whenTypeIsSaved_thenIsFoundByTypeId() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type = mock(ActuatorType.class);
        ActuatorTypeIDVO id = mock(ActuatorTypeIDVO.class);
        when(type.getId()).thenReturn(id);
        actuatorTypeRepositoryMem.save(type);

        //Act
        ActuatorType result = actuatorTypeRepositoryMem.findById(id);

        //Assert
        assertEquals(type, result);
    }

    /**
     * Test to check if the actuator type is saved given a valid ActuatorType and then found in findAll
     * Should return a list of all the types saved
     */
    @Test
    void whenTypesAreSaved_thenFoundInFindAll() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type1 = mock(ActuatorType.class);
        ActuatorType type2 = mock(ActuatorType.class);
        ActuatorType type3 = mock(ActuatorType.class);
        ActuatorTypeIDVO type1ID = mock(ActuatorTypeIDVO.class);
        when(type1.getId()).thenReturn(type1ID);
        ActuatorTypeIDVO type2ID = mock(ActuatorTypeIDVO.class);
        when(type2.getId()).thenReturn(type2ID);
        ActuatorTypeIDVO type3ID = mock(ActuatorTypeIDVO.class);
        when(type3.getId()).thenReturn(type3ID);
        actuatorTypeRepositoryMem.save(type1);
        actuatorTypeRepositoryMem.save(type2);
        actuatorTypeRepositoryMem.save(type3);

        //Act
        Iterable<ActuatorType> iterable = actuatorTypeRepositoryMem.findAll();
        boolean isType1Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(type -> type.equals(type1));
        boolean isType2Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(type -> type.equals(type2));
        boolean isType3Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(type -> type.equals(type3));

        //Assert
        assertTrue(isType1Present);
        assertTrue(isType2Present);
        assertTrue(isType3Present);
    }

    /**
     * Test to check if the actuator type is saved given a valid ActuatorType and then found in isPresent
     * Should return true if the type is present
     */
    @Test
    void whenTypesAreSaved_thenFoundInRepository() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type1 = mock(ActuatorType.class);
        ActuatorType type2 = mock(ActuatorType.class);
        ActuatorType type3 = mock(ActuatorType.class);
        ActuatorTypeIDVO type1ID = mock(ActuatorTypeIDVO.class);
        when(type1.getId()).thenReturn(type1ID);
        ActuatorTypeIDVO type2ID = mock(ActuatorTypeIDVO.class);
        when(type2.getId()).thenReturn(type2ID);
        ActuatorTypeIDVO type3ID = mock(ActuatorTypeIDVO.class);
        when(type3.getId()).thenReturn(type3ID);
        actuatorTypeRepositoryMem.save(type1);
        actuatorTypeRepositoryMem.save(type2);
        actuatorTypeRepositoryMem.save(type3);

        //Act
        boolean isType3Present = actuatorTypeRepositoryMem.isPresent(type3ID);
        boolean isType1Present = actuatorTypeRepositoryMem.isPresent(type1ID);
        boolean isType2Present = actuatorTypeRepositoryMem.isPresent(type2ID);


        //Assert
        assertTrue(isType1Present);
        assertTrue(isType2Present);
        assertTrue(isType3Present);
    }

    /**
     * Test to check if a not saved actuator type is not found in findAll
     * Should return false if the type is not present
     */
    @Test
    void whenTypeIsNotSaved_thenNotFoundWithFindAll() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type1 = mock(ActuatorType.class);
        ActuatorType type2 = mock(ActuatorType.class);
        ActuatorType type3 = mock(ActuatorType.class);
        ActuatorTypeIDVO typeID = mock(ActuatorTypeIDVO.class);
        when(type3.getId()).thenReturn(typeID);
        actuatorTypeRepositoryMem.save(type1);
        actuatorTypeRepositoryMem.save(type2);

        //Act
        Iterable<ActuatorType> iterable = actuatorTypeRepositoryMem.findAll();
        boolean isType3Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(type -> type.equals(type3));

        //Assert
        assertFalse(isType3Present);
    }

    /**
     * Test to check if a not saved actuator type is not found by its ID
     * Should return a null object
     */
    @Test
    void whenTypeIsNotPresent_thenNotFoundById() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type1 = mock(ActuatorType.class);
        ActuatorType type2 = mock(ActuatorType.class);
        ActuatorType type3 = mock(ActuatorType.class);
        ActuatorTypeIDVO type3ID = mock(ActuatorTypeIDVO.class);
        when(type3.getId()).thenReturn(type3ID);
        actuatorTypeRepositoryMem.save(type1);
        actuatorTypeRepositoryMem.save(type2);

        //Act
        ActuatorType result = actuatorTypeRepositoryMem.findById(type3ID);

        //Assert
        assertNull(result);
    }

    /**
     * Test to check if a not saved actuator type is not found in isPresent
     * Should return a null object
     */
    @Test
    void whenTypeIDIsNotPresent_thenNotFound() {
        //Arrange
        ActuatorTypeRepositoryMem actuatorTypeRepositoryMem = new ActuatorTypeRepositoryMem();
        ActuatorType type1 = mock(ActuatorType.class);
        ActuatorType type2 = mock(ActuatorType.class);
        actuatorTypeRepositoryMem.save(type1);
        actuatorTypeRepositoryMem.save(type2);
        ActuatorTypeIDVO actuatorTypeID = mock(ActuatorTypeIDVO.class);

        //Act
        ActuatorType result = actuatorTypeRepositoryMem.findById(actuatorTypeID);

        //Assert
        assertNull(result);
    }
}