package smarthome.mapper;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorTypeMapperTest {

    @Test
    void givenValidActuators_ThenReturnDTO() {
        ActuatorType actuator1Double = mock(ActuatorType.class);
        ActuatorType actuator2Double = mock(ActuatorType.class);
        ActuatorType actuator3Double = mock(ActuatorType.class);
        ActuatorTypeIDVO actuatorTypeIDVO1 = mock(ActuatorTypeIDVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO2 = mock(ActuatorTypeIDVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO3 = mock(ActuatorTypeIDVO.class);
        when(actuator1Double.getId()).thenReturn(actuatorTypeIDVO1);
        when(actuator2Double.getId()).thenReturn(actuatorTypeIDVO2);
        when(actuator3Double.getId()).thenReturn(actuatorTypeIDVO3);
        when(actuatorTypeIDVO1.getID()).thenReturn("actuator1");
        when(actuatorTypeIDVO2.getID()).thenReturn("actuator2");
        when(actuatorTypeIDVO3.getID()).thenReturn("actuator3");
        List<ActuatorType> list = new ArrayList<>();
        list.add(actuator1Double);
        list.add(actuator2Double);
        list.add(actuator3Double);
        List<ActuatorTypeDTO> listDTO = ActuatorTypeMapper.domainToDTO(list);
        assertEquals("actuator1", listDTO.get(0).getActuatorTypeID());
        assertEquals("actuator2", listDTO.get(1).getActuatorTypeID());
        assertEquals("actuator3", listDTO.get(2).getActuatorTypeID());


    }

    @Test
    void givenNullActuatorTypeDTO_ThenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ActuatorTypeMapper.createActuatorTypeIDVO(null));
    }

    @Test
    void givenValidActuatorTypeDTO_ThenCreateActuatorTypeIDVO() {
        ActuatorTypeDTO actuator1Double = mock(ActuatorTypeDTO.class);
        ActuatorTypeDTO actuator2Double = mock(ActuatorTypeDTO.class);
        ActuatorTypeDTO actuator3Double = mock(ActuatorTypeDTO.class);
        when(actuator1Double.getActuatorTypeID()).thenReturn("actuator1");
        when(actuator2Double.getActuatorTypeID()).thenReturn("actuator2");
        when(actuator3Double.getActuatorTypeID()).thenReturn("actuator3");
        String actuator1 = actuator1Double.getActuatorTypeID();
        String actuator2 = actuator2Double.getActuatorTypeID();
        String actuator3 = actuator3Double.getActuatorTypeID();
        ActuatorTypeIDVO actuatorTypeIDVO1 = ActuatorTypeMapper.createActuatorTypeIDVO(actuator1Double);
        ActuatorTypeIDVO actuatorTypeIDVO2 = ActuatorTypeMapper.createActuatorTypeIDVO(actuator2Double);
        ActuatorTypeIDVO actuatorTypeIDVO3 = ActuatorTypeMapper.createActuatorTypeIDVO(actuator3Double);
        assertEquals("actuator1", actuatorTypeIDVO1.getID());
        assertEquals("actuator2", actuatorTypeIDVO2.getID());
        assertEquals("actuator3", actuatorTypeIDVO3.getID());
    }

    @Test
    void givenValidActuatorTypeDTO_ThenCreateActuatorTypeIDVOFromString() {
        ActuatorTypeDTO actuator1Double = mock(ActuatorTypeDTO.class);
        ActuatorTypeDTO actuator2Double = mock(ActuatorTypeDTO.class);
        ActuatorTypeDTO actuator3Double = mock(ActuatorTypeDTO.class);
        when(actuator1Double.getActuatorTypeID()).thenReturn("actuator1");
        when(actuator2Double.getActuatorTypeID()).thenReturn("actuator2");
        when(actuator3Double.getActuatorTypeID()).thenReturn("actuator3");
        String actuator1 = actuator1Double.getActuatorTypeID();
        String actuator2 = actuator2Double.getActuatorTypeID();
        String actuator3 = actuator3Double.getActuatorTypeID();
        ActuatorTypeIDVO actuatorTypeIDVO1 = ActuatorTypeMapper.createActuatorTypeIDVOFromString(actuator1);
        ActuatorTypeIDVO actuatorTypeIDVO2 = ActuatorTypeMapper.createActuatorTypeIDVOFromString(actuator2);
        ActuatorTypeIDVO actuatorTypeIDVO3 = ActuatorTypeMapper.createActuatorTypeIDVOFromString(actuator3);
        assertEquals("actuator1", actuatorTypeIDVO1.getID());
        assertEquals("actuator2", actuatorTypeIDVO2.getID());
        assertEquals("actuator3", actuatorTypeIDVO3.getID());
    }
}