package smarthome.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.mapper.ActuatorTypeMapper;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.service.ActuatorTypeService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/actuatortypes")
@CrossOrigin(origins = "*")

public class ActuatorTypeCTRLWeb {

    private final ActuatorTypeService actuatorTypeService;

    /**
     * Web controller for the actuatorType aggregate.
     * This controller is responsible for handling HTTP requests related to actuator types.
     * It provides an endpoint for retrieving a list of all actuator types available in the system.
     * The controller delegates the processing of the request to the ActuatorTypeService.
     */
    public ActuatorTypeCTRLWeb(ActuatorTypeService actuatorTypeService) {
        this.actuatorTypeService = actuatorTypeService;
    }


    /**
     * Retrieves a list of all actuator types available in the system.
     *
     * @return A response entity containing a list of actuator types with a self-link.
     */
    @GetMapping()
    public ResponseEntity<CollectionModel<ActuatorTypeDTO>> getActuatorTypes() {
        List<ActuatorType> actuatorTypes = actuatorTypeService.getListOfActuatorTypes();
        List<ActuatorTypeDTO> actuatorTypeDTOs = ActuatorTypeMapper.domainToDTO(actuatorTypes);
        Link selfLink = linkTo(methodOn(ActuatorTypeCTRLWeb.class).getActuatorTypes()).withSelfRel();
        CollectionModel<ActuatorTypeDTO> actuatorTypeDTOCollectionModel = CollectionModel.of(actuatorTypeDTOs, selfLink);
        return ResponseEntity.ok(actuatorTypeDTOCollectionModel);
    }
}
