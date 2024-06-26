package smarthome.domain.sensortype;

import smarthome.domain.DomainID;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.domain.vo.ValueObject;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class SensorTypeTest {

    /**
     * The following tests make sure that the getter methods in the class
     * perform as expected, with isolation.
     */
    @Test
    void whenGetIDCalled_thenSensorTypeIDIsReturned() {
        //Arrange
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        UnitVO unitDouble = mock(UnitVO.class);

        //Act
        SensorType sensorType = new SensorType(sensorTypeDouble, unitDouble);
        DomainID result = sensorType.getId();

        //Assert
        assertEquals(result, sensorTypeDouble);
    }

    @Test
    void whenGetUnitCalled_thenUnitVOIsReturned() {
        //Arrange
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        UnitVO unitDouble = mock(UnitVO.class);

        //Act
        SensorType sensorType = new SensorType(sensorTypeDouble, unitDouble);
        ValueObject<String> result = sensorType.getUnit();

        //Assert
        assertEquals(result, unitDouble);
    }

    /**
     * The following tests make sure that when there is
     * a null parameter, an IllegalArgumentException is thrown.
     */
    private void assertThrowsWithNullParameter(SensorTypeIDVO sensorTypeIDVO, UnitVO unitVO) {
        //Arrange
        String expected = "Invalid Parameters";

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SensorType(sensorTypeIDVO, unitVO));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    @Test
    void whenNullSensorTypeIDVO_thenThrowsIllegalArgumentException() {

        UnitVO unitDouble = mock(UnitVO.class);
        //Assert
        assertThrowsWithNullParameter(null, unitDouble);
    }

    @Test
    void whenNullUnitVO_thenThrowsIllegalArgumentException() {
        SensorTypeIDVO sensorTypeDouble = mock(SensorTypeIDVO.class);
        //Assert
        assertThrowsWithNullParameter(sensorTypeDouble, null);

    }
}
