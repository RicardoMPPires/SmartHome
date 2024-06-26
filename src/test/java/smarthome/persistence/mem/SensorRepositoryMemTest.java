package smarthome.persistence.mem;

import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SunriseSensor;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorRepositoryMemTest {

    /**
     * This test ensures the repository is able to save a given Sensor, if all prerequisites are met. This validation
     * occurs by the method save() returning true, and by using the method findByID();
     */
    @Test
    void givenCorrectEntity_RepositorySavesTheSensorCorrectly(){
        // Arrange
        SensorIDVO sensorID = mock(SensorIDVO.class);

        SunriseSensor sensor = mock(SunriseSensor.class);
        when(sensor.getId()).thenReturn(sensorID);

        SensorRepositoryMem repository = new SensorRepositoryMem();

        // Act
        boolean result = repository.save(sensor);
        Sensor addedSensor = repository.findById(sensorID);

        // Assert
        assertTrue(result);
        assertEquals(addedSensor,sensor);
    }

    /**
     * This test ensures no sensors can be saved if the sensorID is already present as key.
     */
    @Test
    void givenDuplicateEntity_RepositoryDoesNotSaveReturningFalse(){
        // Arrange
        SensorIDVO sensorID = mock(SensorIDVO.class);

        SunriseSensor sensor = mock(SunriseSensor.class);
        when(sensor.getId()).thenReturn(sensorID);

        SensorRepositoryMem repository = new SensorRepositoryMem();
        repository.save(sensor);

        // Act
        boolean result = repository.save(sensor);

        // Assert
        assertFalse(result);
    }

    /**
     * This test validates that a null object is unable to be saved
     */
    @Test
    void givenInvalidEntity_RepositoryDoesNotSaveReturningFalse(){
        // Arrange
        SensorRepositoryMem repository = new SensorRepositoryMem();

        // Act
        boolean result = repository.save(null);

        // Assert
        assertFalse(result);
    }

    /**
     * This test validates that an object that delivers a null IDVO is unable to be saved.
     */
    @Test
    void givenEntityWithNullID_RepositoryDoesNotSaveReturningFalse(){
        // Arrange
        SensorRepositoryMem repository = new SensorRepositoryMem();
        SunriseSensor sensor = mock(SunriseSensor.class);
        when(sensor.getId()).thenReturn(null);
        // Act
        boolean result = repository.save(sensor);

        // Assert
        assertFalse(result);
    }

    /**
     * This test ensures that, if given a nullID, the method findByID returns null
     */
    @Test
    void whenCallingFindByID_givenNullID_returnsNull(){
        // Arrange
        SensorRepositoryMem repository = new SensorRepositoryMem();

        // Act and Assert
        assertNull(repository.findById(null));
    }

    /**
     * This test ensures that, when repository is empty, the method findByID returns null
     */
    @Test
    void callingFindByID_givenSensorIDToAnEmptyRepo_returnsNull(){
        // Arrange
        SensorRepositoryMem repository = new SensorRepositoryMem();
        SensorIDVO id = mock(SensorIDVO.class);

        // Act and Assert
        assertNull(repository.findById(id));
    }

    /**
     * This test ensures that, if repository is empty, the method findAll returns an Iterable with the size of 0
     */
    @Test
    void whenRepositoryIsEmpty_findAllReturnsAnEmptyList_VerifiedByCheckingTheIterableSize(){
        // Arrange
        SensorRepositoryMem repository = new SensorRepositoryMem();

        long expected = 0;

        // Act
        long result = StreamSupport.stream(repository.findAll().spliterator(), false).count();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test validates that, when findAll is used on a repository with two items, the iterable resulting from that
     * method returns a list of size2.
     */
    @Test
    void whenRepositoryHasTwoItems_findAllReturnsAnIterableWithSizeOfTwo_VerifiedByCheckingTheIterableSize(){
        // Arrange
        SensorIDVO sensorID1 = mock(SensorIDVO.class);
        SensorIDVO sensorID2 = mock(SensorIDVO.class);

        SunriseSensor sensor1 = mock(SunriseSensor.class);
        when(sensor1.getId()).thenReturn(sensorID1);

        SunriseSensor sensor2 = mock(SunriseSensor.class);
        when(sensor2.getId()).thenReturn(sensorID2);

        SensorRepositoryMem repository = new SensorRepositoryMem();
        repository.save(sensor1);
        repository.save(sensor2);

        long expected = 2;

        // Act
        long result = StreamSupport.stream(repository.findAll().spliterator(), false).count();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test ensures findAll provides an iterable that has items that are accessible.
     */
    @Test
    void callingFindAll_whenRepositoryHasOneEntry_entryIsAccessibleWhileUsingGetID(){
        // Arrange
        SensorIDVO sensorID1 = mock(SensorIDVO.class);

        SunriseSensor sensor1 = mock(SunriseSensor.class);
        when(sensor1.getId()).thenReturn(sensorID1);

        SensorRepositoryMem repository = new SensorRepositoryMem();
        repository.save(sensor1);

        // Act
        Iterable<Sensor> repositorySensors = repository.findAll();

        Sensor result = repositorySensors.iterator().next();
        // Assert
        assertEquals(sensor1,result);
    }

    /**
     * This test ensures that given a valid IDVO, it matches against a previously placed sensor with the same ID, returning true
     */
    @Test
    void whenRepositoryHasOneSensor_givenAMatchingID_isPresentReturnsTrue(){
        // Arrange
        SensorIDVO sensorID1 = mock(SensorIDVO.class);

        SunriseSensor sensor1 = mock(SunriseSensor.class);
        when(sensor1.getId()).thenReturn(sensorID1);

        SensorRepositoryMem repository = new SensorRepositoryMem();
        repository.save(sensor1);

        // Act
        boolean result = repository.isPresent(sensorID1);

        // Assert
        assertTrue(result);
    }

    /**
     * This test ensures that given a valid IDVO, the method isPresent returns false if no matching ID is found
     */
    @Test
    void whenRepositoryHasOneSensor_givenANonMatchingID_isPresentReturnsFalse(){
        // Arrange
        SensorIDVO sensorID1 = mock(SensorIDVO.class);
        SensorIDVO sensorID2 = mock(SensorIDVO.class);

        SunriseSensor sensor1 = mock(SunriseSensor.class);
        when(sensor1.getId()).thenReturn(sensorID1);

        SensorRepositoryMem repository = new SensorRepositoryMem();
        repository.save(sensor1);

        // Act
        boolean result = repository.isPresent(sensorID2);

        // Assert
        assertFalse(result);
    }
}