package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.sensortype.SensorType;
import smarthome.mapper.SensorTypeMapper;
import smarthome.mapper.dto.SensorTypeDTO;
import smarthome.service.SensorTypeService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/sensortypes")
@CrossOrigin(origins = "*")

public class SensorTypeCTRLWeb {

    private final SensorTypeService sensorTypeService;


    /**
     * Web controller for getting a list of sensor types.
     * This controller is responsible for handling HTTP requests related to sensor types.
     * It provides an endpoint for retrieving a list of all sensor types available in the system.
     * The controller delegates the processing of the request to the SensorTypeService.
     */
    @Autowired
    public SensorTypeCTRLWeb(SensorTypeService sensorTypeService) {
        this.sensorTypeService = sensorTypeService;
    }

    /**
     * Retrieves a list of all sensor types available in the system.
     *
     * @return A response entity containing a list of sensor types with a self-link.
     */
    @GetMapping
    public ResponseEntity<CollectionModel<SensorTypeDTO>> getSensorTypes() {
        List<SensorType> sensorTypes = sensorTypeService.getListOfSensorTypes();
        List<SensorTypeDTO> sensorTypesDTO = SensorTypeMapper.domainToDTO(sensorTypes);
        Link selfLink = linkTo(methodOn(SensorTypeCTRLWeb.class).getSensorTypes()).withSelfRel().withTitle("Get Sensor Types");
        CollectionModel<SensorTypeDTO> result = CollectionModel.of(sensorTypesDTO, selfLink);
        return ResponseEntity.ok(result);
    }
}

