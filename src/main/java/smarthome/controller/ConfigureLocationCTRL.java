package smarthome.controller;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.LocationVO;
import smarthome.mapper.HouseMapper;
import smarthome.mapper.dto.LocationDTO;
import smarthome.service.HouseService;

import java.util.Optional;

/**
 * Controller class for the Configure Location use case
 * This class is responsible for updating the location of the house
 */

public class ConfigureLocationCTRL {
    private final HouseService houseService;

    /**
     * Constructor for the ConfigureLocationCTRL class
     * Checks if the service is valid and initializes the service
     * Throws an IllegalArgumentException if the service is invalid
     *
     * @param houseService the service that will be used to update the location
     */
    public ConfigureLocationCTRL(HouseService houseService) {
        if (houseService == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        this.houseService = houseService;
    }

    /**
     * Method to update the location of the house
     * This method receives a location data transfer object and uses the house service to update the location
     * If the location data transfer object is null, the method returns false
     * If the location data transfer object is valid, the method uses the house service to update the location
     *
     * @param locationDTO the location data transfer object that will be used to update the location
     * @return true if the location was updated successfully, false otherwise
     */

    public boolean updateLocation(LocationDTO locationDTO) {
        try {
            LocationVO locationVO = HouseMapper.dtoToDomain(locationDTO);
            Optional<House> optionalHouse = houseService.updateLocation(locationVO);
            return optionalHouse.isPresent();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}