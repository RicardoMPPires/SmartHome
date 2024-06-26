package smarthome.mapper;

import smarthome.domain.sensortype.SensorType;
import smarthome.mapper.dto.SensorTypeDTO;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorTypeMapperTest {

    /**
     * Test if the method createSensorTypeIDVO returns the correct SensorTypeIDVO object.
     * The method should return a SensorTypeIDVO object with the same ID as the SensorTypeDTO object.
     * The SensorTypeDTO object is created with a sensorTypeID and a unit.
     * The method createSensorTypeIDVO is called with the SensorTypeDTO object as parameter.
     * The expected SensorTypeIDVO object should be created with the same sensorTypeID.
     * The test will pass if the ID of the expected object is the same as the ID of the result object, and fail otherwise.
     */
    @Test
    void whenCreateSensorTypeIDVO_ThenSensorTypeIDVOisRetrieved() {
        // Arrange
        String sensorTypeID = "123e4567-e89b-12d3-a456-426655440000";
        String unit = "Celsius";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID, unit);
        SensorTypeIDVO expected = new SensorTypeIDVO(sensorTypeID);
        // Act
        SensorTypeIDVO result = SensorTypeMapper.createSensorTypeIDVO(sensorTypeDTO);
        // Assert
        assertEquals(expected.getID(), result.getID());
    }

    /**
     * Test if the method createSensorTypeIDVO throws an IllegalArgumentException.
     * The method should throw an IllegalArgumentException when called with a null SensorTypeDTO object as parameter.
     * The test will pass if an IllegalArgumentException is thrown, and fail otherwise.
     */
    @Test
    void whenCreateSensorTypeIDVOWithNull_ThenIllegalArgumentExceptionIsThrown() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> SensorTypeMapper.createSensorTypeIDVO(null));
    }

    /**
     * Test if the method domainToDTO returns a list of SensorTypeDTO objects.
     * The method should return a list of SensorTypeDTO objects with the same sensorTypeID and unit as the SensorType
     * objects in the provided list.
     * The SensorType objects are mocked and created with a mocked sensorTypeID and a mocked unit.
     * The method domainToDTO is called with a list of SensorType objects as parameter.
     * The result list should be created with a SensorTypeDTO object that has the same sensorTypeID and unit as the
     * SensorType object.
     * The test will pass if the ID and unit of the mocked value objects are the same as the ID and unit of the result
     * DTO object in the list, and fail otherwise.
     * Additionally, the test will pass if the size of the result list is the same as the expected list size, and fail
     * otherwise.
     */
    @Test
    void whenDomainToDTOIsCalled_ThenListOfSensorTypeDTOIsRetrieved() {
        // Arrange
        SensorTypeIDVO sensorTypeIDVODouble = mock(SensorTypeIDVO.class);
        UnitVO unitVODouble = mock(UnitVO.class);
        SensorType sensorTypeDouble = mock(SensorType.class);
        when(sensorTypeDouble.getId()).thenReturn(sensorTypeIDVODouble);
        when(sensorTypeDouble.getUnit()).thenReturn(unitVODouble);
        List<SensorType> sensorTypeList = new ArrayList<>();
        sensorTypeList.add(sensorTypeDouble);
        int expectedListSize = 1;
        // Act
        List<SensorTypeDTO> result = SensorTypeMapper.domainToDTO(sensorTypeList);
        // Assert
        assertEquals(expectedListSize, result.size());
        assertEquals(sensorTypeIDVODouble.getID(), result.get(0).getSensorTypeID());
        assertEquals(unitVODouble.getValue(), result.get(0).getUnit());
    }

    /**
     * Test if the method domainToDTO returns a list of SensorTypeDTO objects.
     * This is a similar test to the previous one, but with a list of size 2. Two SensorType objects are mocked and
     * created with mocked sensorTypeID and unit and added to the list.
     * The method domainToDTO is called with the list of SensorType objects as parameter.
     * The result list should be created with two SensorTypeDTO objects that have the same sensorTypeID and unit as the
     * SensorType objects.
     */
    @Test
    void whenDomainToDTOIsCalled_ThenListOfSensorTypeDTOIsRetrievedWithSize2() {
        // Arrange
        SensorTypeIDVO sensorTypeIDVODoubleOne = mock(SensorTypeIDVO.class);
        UnitVO unitVODoubleOne = mock(UnitVO.class);
        SensorType sensorTypeDoubleOne = mock(SensorType.class);
        when(sensorTypeDoubleOne.getId()).thenReturn(sensorTypeIDVODoubleOne);
        when(sensorTypeDoubleOne.getUnit()).thenReturn(unitVODoubleOne);
        SensorTypeIDVO sensorTypeIDVODoubleTwo = mock(SensorTypeIDVO.class);
        UnitVO unitVODoubleTwo = mock(UnitVO.class);
        SensorType sensorTypeDoubleTwo = mock(SensorType.class);
        when(sensorTypeDoubleTwo.getId()).thenReturn(sensorTypeIDVODoubleTwo);
        when(sensorTypeDoubleTwo.getUnit()).thenReturn(unitVODoubleTwo);
        List<SensorType> sensorTypeList = new ArrayList<>();
        sensorTypeList.add(sensorTypeDoubleOne);
        sensorTypeList.add(sensorTypeDoubleTwo);
        int expectedListSize = 2;
        // Act
        List<SensorTypeDTO> result = SensorTypeMapper.domainToDTO(sensorTypeList);
        // Assert
        assertEquals(expectedListSize, result.size());
        assertEquals(sensorTypeIDVODoubleOne.getID(), result.get(0).getSensorTypeID());
        assertEquals(unitVODoubleOne.getValue(), result.get(0).getUnit());
        assertEquals(sensorTypeIDVODoubleTwo.getID(), result.get(1).getSensorTypeID());
        assertEquals(unitVODoubleTwo.getValue(), result.get(1).getUnit());
    }


    /**
     * Test if the method domainToDTO returns an empty list of SensorTypeDTO objects.
     * The method should return an empty list of SensorTypeDTO objects when called with an empty list of SensorType
     * objects as parameter.
     * The test will pass if the size of the result list is 0, and fail otherwise.
     */
    @Test
    void whenDomainToDTOIsCalledWithEmptyList_ThenEmptyListOfSensorTypeDTOIsRetrieved() {
        // Arrange
        List<SensorType> sensorTypeList = new ArrayList<>();
        int expectedListSize = 0;
        // Act
        List<SensorTypeDTO> result = SensorTypeMapper.domainToDTO(sensorTypeList);
        // Assert
        assertEquals(expectedListSize, result.size());
    }

}