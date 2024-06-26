package smarthome.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.actuatorvo.Settings;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.mapper.ActuatorMapper;
import smarthome.mapper.dto.ActuatorDTO;
import smarthome.service.ActuatorService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/actuators")
@CrossOrigin(origins = "*")
public class ActuatorCTRLWeb {

    private final ActuatorService actuatorService;


    /**
     * Constructor for ActuatorCTRLWeb, it receives an ActuatorService, which is the service that will be used to
     * perform the operations related to the Actuator.
     *
     * @param actuatorService actuator service
     */
    public ActuatorCTRLWeb(ActuatorService actuatorService) {
        if (actuatorService == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        this.actuatorService = actuatorService;
    }

    /**
     * This method receives an ActuatorDTO and creates an Actuator with the information provided in the DTO.
     * It first creates the ActuatorNameVO, ActuatorTypeIDVO, DeviceIDVO and Settings objects from the DTO.
     * Then it calls the addActuator method from the ActuatorService, passing the created objects, and creates a
     * Optional<Actuator> with the returned value.
     * If the Optional is present, it creates an ActuatorDTO from the Actuator and adds a self link to it.
     * If the Optional is not present, it returns an UNPROCESSABLE_ENTITY status.
     * If an IllegalArgumentException is thrown, it returns a BAD_REQUEST status.
     *
     * @param actuatorDTO actuatorDTO
     * @return ResponseEntity<ActuatorDTO>
     */
    @PostMapping("")
    public ResponseEntity<ActuatorDTO> addActuatorToDevice(@RequestBody ActuatorDTO actuatorDTO) {
        try {
            ActuatorNameVO actuatorNameVO = ActuatorMapper.createActuatorNameVO(actuatorDTO);
            ActuatorTypeIDVO actuatorTypeIDVO = ActuatorMapper.createActuatorTypeIDVO(actuatorDTO);
            DeviceIDVO deviceIDVO = ActuatorMapper.createDeviceIDVO(actuatorDTO);
            Settings settings = ActuatorMapper.createSettingsVO(actuatorDTO);

            Optional<Actuator> optionalActuator = this.actuatorService.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings);

            if (optionalActuator.isPresent()) {
                Actuator savedActuator = optionalActuator.get();
                ActuatorDTO createdActuatorDTO = ActuatorMapper.domainToDTO(savedActuator);

                Link selfLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(createdActuatorDTO.getActuatorId())).withSelfRel();
                createdActuatorDTO.add(selfLink);

                return new ResponseEntity<>(createdActuatorDTO, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method receives an actuator id and retrieves an Actuator with the given id.
     * It first creates an ActuatorIDVO from the id and calls the getActuatorById method from the ActuatorService,
     * creating an Optional<Actuator> with the returned value.
     * If the Optional is present, it creates an ActuatorDTO from the Actuator and adds a self link to it.
     * If the Optional is not present, it returns a NOT_FOUND status.
     * If an IllegalArgumentException is thrown, it returns a BAD_REQUEST status.
     *
     * @param actuatorId actuatorId
     * @return ResponseEntity<ActuatorDTO>
     */
    @GetMapping(path = "/{actuatorId}")
    public ResponseEntity<ActuatorDTO> getActuatorById(@PathVariable("actuatorId") String actuatorId) {
        try {
            ActuatorIDVO actuatorIDVO = ActuatorMapper.createActuatorIDVO(actuatorId);
            Optional<Actuator> optionalActuator = this.actuatorService.getActuatorById(actuatorIDVO);

            if (optionalActuator.isPresent()) {
                Actuator savedActuator = optionalActuator.get();
                ActuatorDTO actuatorDTO = ActuatorMapper.domainToDTO(savedActuator);

                Link selfLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorDTO.getActuatorId())).withSelfRel();
                actuatorDTO.add(selfLink);

                Link executeCommand = linkTo(methodOn(ActuatorCTRLWeb.class).executeCommand(actuatorId,"{command}")).withRel("ExecuteCommand");
                actuatorDTO.add(executeCommand);

                return new ResponseEntity<>(actuatorDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This endpoint retrieves a list of actuators associated with a specific device ID. It converts the device ID string
     * into a DeviceIDVO object and passes it to the actuator service to retrieve the list of actuators. If actuators are
     * found, they are converted to a list of ActuatorDTO objects and returned with an HTTP status of 200 (OK). If no
     * actuators are found, a 200 (OK) status is returned with an empty list. If the device ID is invalid, a 400 (Bad
     * Request) status is returned. The endpoint is accessible via a GET request to /actuators?deviceId={deviceId}.
     *
     * @param deviceId The ID of the device to retrieve actuators from.
     * @return A ResponseEntity containing a CollectionModel of ActuatorDTO objects and an HTTP status code.
     */
    @GetMapping()
    public ResponseEntity<CollectionModel<ActuatorDTO>> getActuatorsByDeviceID(@RequestParam("deviceId") String deviceId) {
        try {
            DeviceIDVO deviceIDVO = ActuatorMapper.createDeviceIDVOFromString(deviceId);
            List<Actuator> actuatorList = this.actuatorService.getListOfActuatorsInADevice(deviceIDVO);
            List<ActuatorDTO> actuatorDTOList = ActuatorMapper.domainToDTO(actuatorList);
            addLink(actuatorDTOList);

            CollectionModel<ActuatorDTO> actuatorDTOCollectionModel = CollectionModel.of(actuatorDTOList);
            Link selfLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorsByDeviceID(deviceId)).withSelfRel();
            actuatorDTOCollectionModel.add(selfLink);

            return new ResponseEntity<>(actuatorDTOCollectionModel, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Closes a RollerBlind actuator with the specified ID.
     * This endpoint closes an actuator identified by its unique ID. The actuator is closed
     * based on the provided ID. If the actuator is successfully closed, a response is returned with HTTP status code 200 (OK).
     * If the actuatorID provided is invalid a response with HTTP status code 422 (BAD REQUEST) is returned.
     *
     * @param actuatorId The unique identifier of the actuator to be closed.
     * @return ResponseEntity containing the appropriate HTTP status code.
     */
    @PostMapping("/{actuatorId}/closerollerblind")
    public ResponseEntity<Void> closeRollerBlind(@PathVariable("actuatorId") String actuatorId) {
        try {
            ActuatorIDVO actuatorIDVO = ActuatorMapper.createActuatorIDVO(actuatorId);
            if (this.actuatorService.closeRollerBlind(actuatorIDVO)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Executes a command on the specified actuator.
     *
     * @param actuatorId the unique identifier of the actuator
     * @param command the command value to be executed on the actuator
     * @return a ResponseEntity containing the ActuatorDTO representing the actuator with updated information, along
     * with HTTP status OK (200) if the command execution is successful; otherwise, returns HTTP status BAD_REQUEST
     * (400) if the command execution fails due to an invalid request or actuator not found
     */
    // /{actuatorId}/act?command=someCommandValue
    @PatchMapping("/{actuatorId}/act")
    public ResponseEntity<ActuatorDTO> executeCommand (@PathVariable("actuatorId") String actuatorId,
                                                       @RequestParam(name="command") String command) {
        try {
            ActuatorIDVO actuatorIDVO = ActuatorMapper.createActuatorIDVO(actuatorId);
            Actuator actuator = this.actuatorService.executeCommand(actuatorIDVO,command);
            ActuatorDTO actuatorDTO = ActuatorMapper.domainToDTO(actuator);

            Link selfLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorDTO.getActuatorId()))
                    .withSelfRel();
            actuatorDTO.add(selfLink);

            Link executeCommand = linkTo(methodOn(ActuatorCTRLWeb.class).executeCommand(actuatorId,"{command}"))
                    .withRel("ExecuteCommand");
            actuatorDTO.add(executeCommand);

            return new ResponseEntity<>(actuatorDTO,HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method adds a self link to each ActuatorDTO object in the list. The self link points to the endpoint
     * that retrieves an actuator by its ID.
     *
     * @param actuatorDTOList The list of DeviceDTO objects to add the self link to.
     */
    private void addLink(List<ActuatorDTO> actuatorDTOList) {
        for (ActuatorDTO actuatorDTO : actuatorDTOList) {
            Link selfLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorDTO.getActuatorId())).withSelfRel();
            actuatorDTO.add(selfLink);
        }
    }
}

