package smarthome.controller;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.mapper.ActuatorTypeMapper;
import smarthome.service.ActuatorTypeService;

import java.util.List;

import static java.util.Objects.isNull;

public class GetListOfActuatorTypesCTRL {
    private final ActuatorTypeService actuatorTypeService;

    public GetListOfActuatorTypesCTRL(ActuatorTypeService actuatorTypeService) {
        if (isNull(actuatorTypeService)) {
            throw new IllegalArgumentException("Invalid service");
        }
        this.actuatorTypeService = actuatorTypeService;
    }

    /**
     * Get a list of actuator types
     *
     * @return List of ActuatorTypeDTO
     */
    public List<ActuatorTypeDTO> getListOfActuatorTypes() {
        List<ActuatorType> actuatorTypes = this.actuatorTypeService.getListOfActuatorTypes();
        return ActuatorTypeMapper.domainToDTO(actuatorTypes);
    }
}
