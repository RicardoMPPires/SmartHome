package smarthome.controller;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.LocationVO;
import smarthome.mapper.HouseMapper;
import smarthome.mapper.dto.HouseDTO;
import smarthome.mapper.dto.LocationDTO;
import smarthome.service.HouseService;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The HouseCTRLWeb Class is a Spring Boot REST controller that handles HTTP requests related to the House.
 * It provides endpoints for retrieving house information and to configure the location of the house.
 * The Class uses the HouseService interface to interact with the domain layer and perform business logic operations.
 */
@RestController
@RequestMapping(path = "/house")
@CrossOrigin(origins = "*")

public class HouseCTRLWeb {

    private final HouseService houseService;

    /**
     * Constructs a new HouseCTRLWeb object with the specified HouseService which is autowired by construction injection.
     *
     * @param houseService The HouseService to be used by the controller.
     */
    public HouseCTRLWeb(HouseService houseService){
        this.houseService = houseService;
    }

    /**
     * Spring Boot controller method that handles HTTP GET requests. It is responsible for fetching the details of the
     * first and only House object from the service layer, mapping it to a HouseDTO object, and then returning it.
     * The endpoint is accessible via a GET request to /house.
     * @return Response body with a HouseDTO and an OK HTTP status code (200). In case there is no House stored in
     * the system, a NOT FOUND HTTP Status Code (404) is sent back.
     */
    @GetMapping(path = "")
    public ResponseEntity<HouseDTO> getHouse(){

        Optional<House> house = houseService.getFirstHouse();

        if(house.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HouseDTO houseDTO = HouseMapper.domainToDto(house.get());

        Link selfLink = linkTo(methodOn(HouseCTRLWeb.class).getHouse()).withSelfRel();
        houseDTO.add(selfLink);

        Link updateLocation = linkTo(methodOn(HouseCTRLWeb.class)
                .configureLocation(null)).withRel("configureLocation");
        houseDTO.add(updateLocation);

        Link listRooms = linkTo(methodOn(RoomCTRLWeb.class).getListOfRooms()).withRel("listRooms");
        houseDTO.add(listRooms);

        Link listDevicesByFunctionality = linkTo(methodOn(DeviceCTRLWeb.class)
                .getDevicesByFunctionality()).withRel("listDevicesByFunctionality");
        houseDTO.add(listDevicesByFunctionality);

        Link getMaxTempDifference = linkTo(methodOn(LogCTRLWeb.class)
                .getMaxTempDiff(null, null, null)).withRel("getMaxTempDifference");
        houseDTO.add(getMaxTempDifference);

        Link getPeakPowerConsumption = linkTo(methodOn(LogCTRLWeb.class)
                .getPeakPowerConsumption(null)).withRel("getPeakPowerConsumption");
        houseDTO.add(getPeakPowerConsumption);

        return new ResponseEntity<>(houseDTO, HttpStatus.OK);
    }

    /**
     * Spring Boot controller method that handles HTTP PATCH requests. It receives a LocationDTO with the required
     * information for the update and sends it to the service layer. In case configuration succeeds, a House Optional
     * is returned and its content is mapped to a HouseDTO object and put in the Response Body.
     * The endpoint is accessible via a PATCH request to /house.
     * @param locationDTO Request Body with location data to be used to configure the House Location.
     * @return Successful update: Response body with a HouseDTO and an OK HTTP status code (200).
     * In case there is no House stored in the system or the update operation transaction fails in the persistence
     * layer, an UNPROCESSABLE ENTITY HTTP Status Code (422) is sent back, with no Response Body.
     * In case an invalid Request Body is received, a BAD REQUEST HTTP Status Code (400) is sent back, with no Response Body.
     */
    @PatchMapping(path = "")
    public ResponseEntity<HouseDTO> configureLocation(@RequestBody LocationDTO locationDTO){
        try{
            LocationVO locationVO = HouseMapper.dtoToDomain(locationDTO);

            Optional<House> updatedHouse = houseService.updateLocation(locationVO);
            if(updatedHouse.isPresent()) {

                HouseDTO houseDTO = HouseMapper.domainToDto(updatedHouse.get());
                Link getHouse = linkTo(methodOn(HouseCTRLWeb.class)
                        .getHouse()).withRel("getHouse");
                houseDTO.add(getHouse);
                return new ResponseEntity<>(houseDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}