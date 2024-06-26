package smarthome.domain.sensortype;

import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SensorTypeFactoryImplTest {

     /*
    SYSTEM UNDER TEST: FACTORY + SENSORTYPE IMPLEMENTATION
    A double of all the other collaborators is done (which are the required value objects to create the sensorType).
     */

    /**
     * Test that verifies if the SensorTypeFactory creates a SensorType object with the given sensorTypeIDVO and unitVO.
     */
    @Test
    void givenSensorTypeFactory_whenCreateSensorType_thenSensorTypeIsCreated() {
        // Arrange
        SensorTypeFactoryImpl sensorTypeFactoryImpl = new SensorTypeFactoryImpl();
        SensorTypeIDVO sensorTypeIDVODouble = mock(SensorTypeIDVO.class);
        UnitVO unitVODouble = mock(UnitVO.class);
        // Act
        SensorType sensorType = sensorTypeFactoryImpl.createSensorType(sensorTypeIDVODouble, unitVODouble);
        // Assert
        UnitVO resultUnitVO = sensorType.getUnit();
        SensorTypeIDVO resultSensorTypeIDVO = sensorType.getId();
        assertEquals(unitVODouble, resultUnitVO);
        assertEquals(sensorTypeIDVODouble, resultSensorTypeIDVO);
    }

    /**
     * Test that verifies if the SensorTypeFactory returns null when the sensorTypeIDVO is null.
     */
    @Test
    void givenSensorTypeFactory_whenCreateSensorTypeWithNullSensorTypeIDVO_thenSensorTypeIsNotCreated() {
        // Arrange
        SensorTypeFactoryImpl sensorTypeFactoryImpl = new SensorTypeFactoryImpl();
        UnitVO unitVODouble = mock(UnitVO.class);
        // Act
        SensorType sensorType = sensorTypeFactoryImpl.createSensorType(null, unitVODouble);
        // Assert
        assertNull(sensorType);
    }

    /**
     * Test that verifies if the SensorTypeFactory returns null when the unitVO is null.
     */
    @Test
    void givenSensorTypeFactory_whenCreateSensorTypeWithNullUnitVO_thenSensorTypeIsNotCreated() {
        // Arrange
        SensorTypeFactoryImpl sensorTypeFactoryImpl = new SensorTypeFactoryImpl();
        SensorTypeIDVO sensorTypeIDVODouble = mock(SensorTypeIDVO.class);
        // Act
        SensorType sensorType = sensorTypeFactoryImpl.createSensorType(sensorTypeIDVODouble, null);
        // Assert
        assertNull(sensorType);
    }
}