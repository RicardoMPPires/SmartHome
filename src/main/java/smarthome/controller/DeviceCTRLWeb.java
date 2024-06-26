package smarthome.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.device.Device;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.service.DeviceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The DeviceCTRLWeb class is a Spring Boot REST controller that handles HTTP requests related to devices.
 * It provides endpoints for retrieving device information, adding devices to rooms, and retrieving devices by room ID.
 * The class uses the DeviceService class to interact with the domain layer and perform business logic operations.
 */

@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = "*")

public class DeviceCTRLWeb {

    private final DeviceService deviceService;

    /**
     * Constructs a new DeviceCTRLWeb object with the specified DeviceService.
     *
     * @param deviceService The DeviceService to be used by the controller.
     */

    public DeviceCTRLWeb(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * This endpoint retrieves a device by its ID. It converts the device ID string into a DeviceIDVO object
     * and passes it to the device service to retrieve the device. If the device is found, it is converted to a
     * DeviceDTO object and returned with an HTTP status of 200 (OK). If the device is not found, a 404 (Not Found)
     * status is returned. If the device ID is invalid, a 400 (Bad Request) status is returned. The endpoint is
     * accessible via a GET request to /devices/{deviceID}.
     *
     * @param id The ID of the device to retrieve.
     * @return A ResponseEntity containing the DeviceDTO object and an HTTP status code.
     */
    @GetMapping("/{deviceID}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable("deviceID") String id) {
        try {
            DeviceIDVO deviceIDVO = DeviceMapper.createDeviceID(id);

            Optional<Device> device = this.deviceService.getDeviceById(deviceIDVO);

            if (device.isPresent()) {
                DeviceDTO deviceDTO = DeviceMapper.domainToDTO(device.get());

                Link selfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(deviceDTO.getDeviceID())).withSelfRel();
                deviceDTO.add(selfLink);

                Link deactivateLink = linkTo(methodOn(DeviceCTRLWeb.class).deactivateDevice(deviceDTO.getDeviceID())).withRel("deactivateDevice");
                deviceDTO.add(deactivateLink);

                Link addSensorLink = linkTo(methodOn(SensorCTRLWeb.class).addSensorToDevice(null)).withRel("addSensor");
                deviceDTO.add(addSensorLink);

                Link sensorTypeLink = linkTo(methodOn(SensorTypeCTRLWeb.class).getSensorTypes()).withRel("getSensorType");
                deviceDTO.add(sensorTypeLink);

                Link addActuatorLink = linkTo(methodOn(ActuatorCTRLWeb.class).addActuatorToDevice(null)).withRel("addActuator");
                deviceDTO.add(addActuatorLink);

                Link listOfActuatorsLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorsByDeviceID(deviceDTO.getDeviceID())).withRel("getActuatorsByDeviceId");
                deviceDTO.add(listOfActuatorsLink);

                Link actuatorTypeLink = linkTo(methodOn(ActuatorTypeCTRLWeb.class).getActuatorTypes()).withRel("getActuatorType");
                deviceDTO.add(actuatorTypeLink);

                Link findReadingsLink = linkTo(methodOn(LogCTRLWeb.class).findReadings(deviceDTO.getDeviceID(), null)).withRel("findReadingsInAPeriod");
                deviceDTO.add(findReadingsLink);

                return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This endpoint adds a device to a room. It converts the device DTO object into RoomIDVO, DeviceNameVO, and
     * DeviceModelVO objects and passes them to the device service to add the device to the room. If the device is
     * successfully added, the device is converted to a DeviceDTO object and returned with an HTTP status of 201
     * (Created). If the device cannot be added, a 422 (Unprocessable Entity) status is returned. If the device DTO
     * is invalid, a 400 (Bad Request) status is returned. The endpoint is accessible via a POST request to /devices.
     *
     * @param deviceDTO The DeviceDTO object representing the device to be added.
     * @return A ResponseEntity containing the created DeviceDTO object and an HTTP status code.
     */
    @PostMapping("")
    public ResponseEntity<DeviceDTO> addDeviceToRoom(@RequestBody DeviceDTO deviceDTO) {
        try {
            RoomIDVO roomIDVO = DeviceMapper.createRoomIDVO(deviceDTO);
            DeviceNameVO deviceNameVO = DeviceMapper.createDeviceName(deviceDTO);
            DeviceModelVO deviceModelVO = DeviceMapper.createDeviceModel(deviceDTO);

            Optional<Device> device = this.deviceService.addDevice(deviceNameVO, deviceModelVO, roomIDVO);

            if (device.isPresent()) {
                DeviceDTO createdDevice = DeviceMapper.domainToDTO(device.get());

                Link selfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(createdDevice.getDeviceID())).withSelfRel();
                createdDevice.add(selfLink);

                return new ResponseEntity<>(createdDevice, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This endpoint retrieves a list of devices associated with a specific room ID. It converts the room ID string
     * into a RoomIDVO object and passes it to the device service to retrieve the list of devices. If devices are
     * found, they are converted to a list of DeviceDTO objects and returned with an HTTP status of 200 (OK). If no
     * devices are found, a 200 (OK) status is returned with an empty list. If the room ID is invalid, a 400 (Bad
     * Request) status is returned. The endpoint is accessible via a GET request to /devices?roomID={roomID}.
     *
     * @param id The ID of the room to retrieve devices from.
     * @return A ResponseEntity containing a CollectionModel of DeviceDTO objects and an HTTP status code.
     */
    @GetMapping("")
    public ResponseEntity<CollectionModel<DeviceDTO>> getDevicesByRoomId(@RequestParam("roomID") String id) {
        try {
            RoomIDVO roomIDVO = DeviceMapper.createRoomIDVO(id);

            List<Device> deviceList = this.deviceService.getListOfDevicesInARoom(roomIDVO);

            List<DeviceDTO> deviceDTOList = DeviceMapper.domainToDTO(deviceList);
            addLink(deviceDTOList);

            CollectionModel<DeviceDTO> deviceDTOCollectionModel = CollectionModel.of(deviceDTOList);
            Link selfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDevicesByRoomId(id)).withSelfRel();
            deviceDTOCollectionModel.add(selfLink);

            Link addDeviceLink = linkTo(methodOn(DeviceCTRLWeb.class).addDeviceToRoom(null)).withRel("addDevice");
            deviceDTOCollectionModel.add(addDeviceLink);

            return new ResponseEntity<>(deviceDTOCollectionModel, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deactivates a device with the specified ID.
     * <p>
     * This endpoint deactivates a device identified by its unique ID. The device is deactivated
     * based on the provided ID. If the device is found and successfully deactivated, a response
     * containing the deactivated device information is returned with HTTP status code 200 (Ok).
     * If the device with the provided ID does not exist, a response with HTTP status code 404
     * (Not Found) is returned. If the provided ID is invalid, a response with HTTP status code 422
     * (Unprocessable Entity) is returned.
     *
     * @param id The unique identifier of the device to be deactivated.
     * @return ResponseEntity containing a DeviceWebDTO representing the deactivated device if
     * successful, or null if the device does not exist, along with the appropriate HTTP status code.
     */

    @PatchMapping("/{id}")
    public ResponseEntity<DeviceDTO> deactivateDevice(@PathVariable("id") String id) {

        try {
            DeviceIDVO deviceIDVO = DeviceMapper.createDeviceID(id);
            Optional<Device> deviceOptional = deviceService.deactivateDevice(deviceIDVO);

            if (deviceOptional.isPresent()) {
                Device device = deviceOptional.get();
                DeviceDTO deviceWebDTO = DeviceMapper.domainToDTO(device);

                //Self link
                Link selfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(id)).withSelfRel();
                deviceWebDTO.add(selfLink);

                return new ResponseEntity<>(deviceWebDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    /**
     * Retrieves devices grouped by functionality.
     * <p>
     * This endpoint retrieves a collection of devices grouped by their functionality. It first
     * fetches the list of devices grouped by functionality from the device service. Then it
     * maps the domain objects to DTOs using the device mapper. For each device DTO, a self-link
     * is created. Finally, the collection of device DTOs grouped by functionality is wrapped
     * into a CollectionModel with a self-link representing the endpoint itself.
     * A conversion is made from a Map data structure to a List of Map.Entry structure. This was made because
     * CollectionModel<T> only accepts structures that are Iterables.
     *
     * @return ResponseEntity containing a CollectionModel of device DTOs grouped by functionality
     * along with a self-link representing the endpoint, or null if an error occurs, with
     * the appropriate HTTP status code.
     */

    @GetMapping("/byfunctionality")
    public ResponseEntity<CollectionModel<Map.Entry<String, List<DeviceDTO>>>> getDevicesByFunctionality() {

        try {
            Map<String, List<Device>> map = deviceService.getListOfDeviceByFunctionality();
            Map<String, List<DeviceDTO>> mapWeb = DeviceMapper.domainToDTO(map);

            List<Map.Entry<String, List<DeviceDTO>>> mapWebEntryList = new ArrayList<>(mapWeb.entrySet());

            for (Map.Entry<String, List<DeviceDTO>> entry : mapWebEntryList) {
                List<DeviceDTO> listWebDTO = entry.getValue();
                addLink(listWebDTO);
            }
            //CollectionModel self-link creation
            Link selfCollectionLink = linkTo(methodOn(DeviceCTRLWeb.class).getDevicesByFunctionality()).withSelfRel();
            CollectionModel<Map.Entry<String, List<DeviceDTO>>> collectionModel = CollectionModel.of(mapWebEntryList, selfCollectionLink);

            return new ResponseEntity<>(collectionModel, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * This method adds a self link to each DeviceDTO object in the list. The self link points to the endpoint
     * that retrieves a device by its ID.
     *
     * @param deviceDTOList The list of DeviceDTO objects to add the self link to.
     */
    private void addLink(List<DeviceDTO> deviceDTOList) {
        for (DeviceDTO deviceDTO : deviceDTOList) {
            Link selfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(deviceDTO.getDeviceID())).withSelfRel();
            deviceDTO.add(selfLink);
        }
    }
}