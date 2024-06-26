package smarthome.mapper;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;

import java.util.ArrayList;
import java.util.List;

public class ActuatorTypeMapper {

    private static final String ERRORMESSAGE = "ActuatorTypeDTO cannot be null.";

    /**
     * Maps a list of ActuatorType to a list of ActuatorTypeDTO
     *
     * @param actuatorTypeList list of ActuatorType
     * @return list of ActuatorTypeDTO
     */
    public static List<ActuatorTypeDTO> domainToDTO(List<ActuatorType> actuatorTypeList) {
        List<ActuatorTypeDTO> actuatorTypeDTOList = new ArrayList<>();
        for (ActuatorType actuatorType : actuatorTypeList) {
            actuatorTypeDTOList.add(new ActuatorTypeDTO(actuatorType.getId().getID()));
        }
        return actuatorTypeDTOList;
    }

    /**
     * Creates an ActuatorTypeIDVO from an ActuatorTypeDTO
     *
     * @param actuatorTypeDTO ActuatorTypeDTO
     * @return ActuatorTypeIDVO
     * @throws IllegalArgumentException if ActuatorTypeDTO is null
     */
    public static ActuatorTypeIDVO createActuatorTypeIDVO(ActuatorTypeDTO actuatorTypeDTO) {
        if (actuatorTypeDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        String actuatorType = actuatorTypeDTO.getActuatorTypeID();
        return new ActuatorTypeIDVO(actuatorType);
    }

    public static ActuatorTypeIDVO createActuatorTypeIDVOFromString(String actuatorType){
        return new ActuatorTypeIDVO(actuatorType);
    }
}
