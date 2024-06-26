package smarthome.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.mapper.SensorMapper;
import smarthome.mapper.dto.SensorDTO;
import smarthome.service.SensorService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sensors")
@CrossOrigin(origins = "*")


public class SensorCTRLWeb {
    private final SensorService sensorService;

    /**
     * Constructs a new SensorCTRLWeb with the provided SensorService.
     *
     * @param sensorService The service to be used for performing operations related to Sensors.
     * @throws IllegalArgumentException if the provided SensorService is null.
     */
    public SensorCTRLWeb(SensorService sensorService) {
        if (sensorService == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        this.sensorService = sensorService;
    }

    /**
     * Handles a POST request to add a new Sensor to a Device.
     * It creates the necessary Value Objects from the provided SensorDTO, uses the SensorService to add the Sensor,
     * and returns a ResponseEntity with the created SensorDTO and a self link.
     *
     * @param sensorDTO The SensorDTO representing the Sensor to be added.
     * @return A ResponseEntity with the created SensorDTO and a self link if the operation was successful,
     * or a ResponseEntity with an error status if the operation failed.
     * @throws IllegalArgumentException if the provided SensorDTO is null or invalid.
     */
    @PostMapping("")
    public ResponseEntity<SensorDTO> addSensorToDevice(@RequestBody SensorDTO sensorDTO) {
        try {
            //creating the Value Objects
            SensorNameVO sensorNameVO = SensorMapper.createSensorNameVO(sensorDTO);
            DeviceIDVO deviceIDVO = SensorMapper.createDeviceID(sensorDTO);
            SensorTypeIDVO sensorTypeIDVO = SensorMapper.createSensorTypeIDVO(sensorDTO);

            //use the service to add the sensor
            Optional<Sensor> optionalSensor = sensorService.addSensor(sensorNameVO, deviceIDVO, sensorTypeIDVO);

            //previous operation was successful, we return the newly created resource.
            if (optionalSensor.isPresent()) {
                Sensor sensorSaved = optionalSensor.get();

                //conversion of the domain object to a DTO object
                SensorDTO sensorResponseEntity = SensorMapper.domainToDTO(sensorSaved);

                //the self link is added to the response entity in order to allow the client to access the newly created resource.
                Link selfLink = linkTo(methodOn(SensorCTRLWeb.class).getSensorByID(sensorResponseEntity.getSensorID())).withSelfRel();
                sensorResponseEntity.add(selfLink);
                return new ResponseEntity<>(sensorResponseEntity, HttpStatus.CREATED);

                //previous operation failed, we return an error message.
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles a GET request to retrieve a Sensor by its ID.
     * It creates a SensorIDVO from the provided sensor ID string, uses the SensorService to retrieve the Sensor,
     * and returns a ResponseEntity with the retrieved SensorDTO and a self link.
     *
     * @param sensorID The string representing the ID of the Sensor to retrieve.
     * @return A ResponseEntity with the retrieved SensorDTO and a self link if the Sensor was found,
     * or a ResponseEntity with an error status if the Sensor was not found.
     * @throws IllegalArgumentException if the provided sensor ID string is null or invalid.
     */
    @GetMapping(path = "/{sensorID}")
    public ResponseEntity<SensorDTO> getSensorByID(@PathVariable("sensorID") String sensorID) {

        try {
            SensorIDVO sensorIDVO = SensorMapper.createSensorIDVO(sensorID);
            Optional<Sensor> optionalSensor = sensorService.getSensorById(sensorIDVO);

            if (optionalSensor.isPresent()) {
                Sensor sensor = optionalSensor.get();
                SensorDTO sensorDTO = SensorMapper.domainToDTO(sensor);

                Link selfLink = linkTo(methodOn(SensorCTRLWeb.class).getSensorByID(sensorDTO.getSensorID())).withSelfRel();
                sensorDTO.add(selfLink);
                return new ResponseEntity<>(sensorDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<SensorDTO>> getSensorsByDeviceID(@RequestParam("deviceId") String deviceId) {
        try {
            DeviceIDVO deviceIDVO = SensorMapper.createDeviceIDVOFromString(deviceId);
            List<Sensor> sensorList = this.sensorService.getListOfSensorsInADevice(deviceIDVO);
            List<SensorDTO> sensorDTOList = SensorMapper.domainToDTO(sensorList);
            addLink(sensorDTOList);

            CollectionModel<SensorDTO> sensorDTOCollectionModel = CollectionModel.of(sensorDTOList);
            Link selfLink = linkTo(methodOn(SensorCTRLWeb.class).getSensorsByDeviceID(deviceId)).withSelfRel();
            sensorDTOCollectionModel.add(selfLink);

            return new ResponseEntity<>(sensorDTOCollectionModel, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void addLink(List<SensorDTO> sensorDTOList) {
        for (SensorDTO sensorDTO : sensorDTOList) {
            Link selfLink = linkTo(methodOn(SensorCTRLWeb.class).getSensorByID(sensorDTO.getSensorID())).withSelfRel();
            sensorDTO.add(selfLink);
        }
    }
}