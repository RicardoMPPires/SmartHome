package smarthome.persistence.mem;

import smarthome.domain.sensortype.SensorType;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorTypeRepositoryMemTest {

    /**
     * This test verifies that the saving process of a valid SensorType must return true. By valid it´s
     * understood a not null SensorType, a not null SensorTypeID and a non-repeated SensorType.
     * All the collaborators that interview in this process are doubled ensuring isolation.
     */
    @Test
    void saveWithValidSensorType_ShouldReturnTrue(){
        //Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        boolean result = sensorTypeRepositoryMem.save(sensorTypeDouble);
        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify the behavior of the save method in SensorTypeRepository
     * when attempting to save a null SensorType.
     * This test verifies that the saving process of a null SensorType must return false.
     */

    @Test
    void saveNullSensorType_ShouldReturnFalse(){
        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        boolean result = sensorTypeRepositoryMem.save(null);
        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify the behavior of the save method in SensorTypeRepository
     * when attempting to save a SensorType with a null SensorTypeID.
     * This test verifies that the saving process a SensorType with a null SensorTypeID must return false.
     */
    @Test
    void saveWhenSensorTypeIDIsNull_ShouldReturnFalse(){
        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        boolean result = sensorTypeRepositoryMem.save(null);
        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify the behavior of the save method in SensorTypeRepository
     * when attempting to save a repeated SensorType.
     * This test verifies that the saving process a repeated SensorType return false.
     */
    @Test
    void saveRepeatedSensorType_ShouldReturnFalse(){
        //Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(sensorTypeDouble);
        boolean result = sensorTypeRepositoryMem.save(sensorTypeDouble);
        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify the behavior of the save method in SensorTypeRepository
     * when attempting to save a SensorType in a repository that already has one record.
     * This test verifies that the saving process the second valid SensorType return true.
     */

    @Test
    void saveValidSensorTypeWithOneExistingRecord_ShouldReturnTrue(){
        //Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        SensorTypeIDVO sensorTypeIDVOTwo = mock(SensorTypeIDVO.class);

        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(sensorTypeDouble);

        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVOTwo);

        boolean result = sensorTypeRepositoryMem.save(sensorTypeDouble);
        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify the behavior of the findAll method in SensorTypeRepository
     * when the repository is empty.
     * This test uses an Apache library that provides utility methods and decorators for Iterable instances.
     * It is invoked findAll() in an empty repository and the resulting iterable must be asserted empty.
     */

    @Test
    void findAll_whenRepositoryIsEmpty_ReturnedIterableShouldBeEmpty(){
        //Arrange
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();

        //Act
        Iterable<SensorType> sensorTypeIterable = sensorTypeRepositoryMem.findAll();
        boolean result = IterableUtils.isEmpty(sensorTypeIterable);

        //Assert
        assertTrue(result);
    }

    /**
     * Test case to verify the behavior of the findAll method in SensorTypeRepository
     * when the repository has one record.
     * This test uses an Apache library that provides utility methods and decorators for Iterable instances.
     * It is invoked findAll() in repository class and the resulting iterable must have the added sensorType.
     */

    @Test
    void findAll_whenRepositoryHasOneRecord_ReturnedIterableShouldHaveInsertedRecord(){
        //Arrange
        SensorType expected = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(expected.getId()).thenReturn(sensorTypeIDVO);

        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(expected);
        Iterable<SensorType> sensorTypeIterable = sensorTypeRepositoryMem.findAll();

        //Assert
       SensorType result = IterableUtils.get(sensorTypeIterable,0);
       assertEquals(expected,result);
    }

    /**
     * Test case to verify the behavior of the findAll method in SensorTypeRepository
     * when the repository has two records.
     * This test uses an Apache library that provides utility methods and decorators for Iterable instances.
     * It is invoked findAll() in repository class and the resulting iterable must have in the second index
     * the sensorType added last.
     */
    @Test
    void findAll_whenRepositoryHasTwoRecords_ReturnedIterableShouldHaveCorrectSensorTypeInSecondIndex(){
        //Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        SensorType expected = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVOTwo = mock(SensorTypeIDVO.class);
        when(expected.getId()).thenReturn(sensorTypeIDVOTwo);

        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(sensorTypeDouble);
        sensorTypeRepositoryMem.save(expected);

        Iterable<SensorType> sensorTypeIterable = sensorTypeRepositoryMem.findAll();
        //Assert
        SensorType result = IterableUtils.get(sensorTypeIterable,1);
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the behavior of the findById method in SensorTypeRepository
     * when the repository is empty.
     * This test asserts that null is returned when findByID() is invoked in an empty repository.
     */

    @Test
    void findById_WhenEmptyRepository_ShouldReturnNull(){

        //Arrange
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);

        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        SensorType result = sensorTypeRepositoryMem.findById(sensorTypeIDVO);

        //Assert
        assertNull(result);

    }

    /**
     * Test case to verify the behavior of the findById method in SensorTypeRepository
     * when the requested SensorType does not exist in the repository.
     * This test asserts that null is returned when findByID() invoked with a SensorType that does not exist in the repository.
     */

    @Test
    void findById_WhenNonExistingSensorType_ShouldReturnNull(){

        //Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        SensorTypeIDVO isPresentSensorTypeID = mock(SensorTypeIDVO.class);


        //Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(sensorTypeDouble);
        SensorType result = sensorTypeRepositoryMem.findById(isPresentSensorTypeID);

        //Assert
        assertNull(result);

    }

    /**
     * Test case to verify the behavior of the isPresent method in SensorTypeRepository
     * when the requested SensorType exists in the repository.
     * Is present should return true when an existing Sensor Type is searched.
     */
    @Test
    void isPresent_WhenExistingSensorType_ShouldReturnCorrectSensorType() {
        // Arrange
        SensorType expected = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(expected.getId()).thenReturn(sensorTypeIDVO);

        // Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(expected);
        SensorType result = sensorTypeRepositoryMem.findById(sensorTypeIDVO);

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify the behavior of the isPresent method in SensorTypeRepository
     * in an empty repository
     * Is present should return false when the repository is empty.
     */
    @Test
    void isPresent_WhenEmptyRepository_ShouldReturnFalse() {
        // Arrange
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);

        // Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        boolean result = sensorTypeRepositoryMem.isPresent(sensorTypeIDVO);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case to verify the behavior of the isPresent method in SensorTypeRepository
     * when the requested SensorType does not exist in the repository.
     * Is present should return false when a non-existing Sensor Type is searched.
     */
    @Test
    void isPresent_WhenNonExistingSensorType_ShouldReturnFalse() {
        // Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        SensorTypeIDVO isPresentSensorTypeID = mock(SensorTypeIDVO.class);

        // Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(sensorTypeDouble);
        boolean result = sensorTypeRepositoryMem.isPresent(isPresentSensorTypeID);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case to verify the behavior of the isPresent method in SensorTypeRepository
     * when the requested SensorType exists in the repository.
     * Is present should return true when an already existing Sensor Type is searched.
     */
    @Test
    void isPresent_WhenExistingSensorType_ShouldReturnTrue() {
        // Arrange
        SensorType sensorTypeDouble = mock(SensorType.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVO);

        // Act
        SensorTypeRepositoryMem sensorTypeRepositoryMem = new SensorTypeRepositoryMem();
        sensorTypeRepositoryMem.save(sensorTypeDouble);
        boolean result = sensorTypeRepositoryMem.isPresent(sensorTypeIDVO);

        // Assert
        assertTrue(result);
    }

}
