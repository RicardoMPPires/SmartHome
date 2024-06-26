package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.vo.housevo.LocationVO;
import smarthome.persistence.HouseRepository;

import java.util.Optional;


/**
 * Service class for House. It contains methods to create a new House, update the location of the House and get the first House ID.
 * It throws an IllegalArgumentException if the HouseRepository or HouseFactory objects are null.
 */

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final HouseFactory houseFactory;

    /**
     * Constructor for HouseService class.
     * It throws an IllegalArgumentException if the HouseRepository or HouseFactory objects are null.
     * It initializes the HouseRepository and HouseFactory objects.
     *
     * @param houseRepository HouseRepository object
     * @param houseFactory    HouseFactory object
     */
    public HouseServiceImpl(HouseRepository houseRepository, HouseFactory houseFactory) {
        if (houseRepository == null || houseFactory == null) {
            throw new IllegalArgumentException("Invalid parameters");
        } else {
            this.houseRepository = houseRepository;
            this.houseFactory = houseFactory;
        }
    }

    /**
     * Method to create a new House object.
     * It throws an IllegalArgumentException if the LocationVO object is null.
     * It calls the createHouse method of the HouseFactory object to create a new House object.
     * It calls the save method of the HouseRepository object to save the House object.
     *
     * @param locationVO LocationVO object
     * @return Optional House object, which may be empty in case House fails to be saved.
     */
    public Optional<House> addHouse(LocationVO locationVO) {
        if (locationVO == null) {
            throw new IllegalArgumentException("Invalid location");
        }
        House house = houseFactory.createHouse(locationVO);
        if(houseRepository.save(house)){
            return Optional.of(house);
        }
        return Optional.empty();
    }

    /**
     * Method to update the location of the House.
     * It throws an IllegalArgumentException if the LocationVO object is null.
     * First, it calls the getFirstHouse method to get the first House object.
     * If the House Optional is empty, it returns an empty optional.
     * If the House Optional is not empty, it calls the configureLocation() method on the House object to update its location.
     * It calls the update() method of the HouseRepository object to update the House object.
     * It returns an Optional object with the updated House object.
     * Returns an empty optional in case House fails to be updated.
     *
     * @param locationVO LocationVO object
     * @return Optional object with the updated House object or an empty Optional if the update operation is not
     * successful
     */

    public Optional<House> updateLocation(LocationVO locationVO) {
        if (locationVO == null) {
            throw new IllegalArgumentException("Invalid location");
        }
        Optional<House> house = getFirstHouse();
        if (house.isEmpty()) {
            return Optional.empty();
        } else {
            House houseToUpdate = house.get();
            houseToUpdate.configureLocation(locationVO);
            boolean result = houseRepository.update(houseToUpdate);
            if (result) {
                return Optional.of(houseToUpdate);
            } else {
                return Optional.empty();
            }
        }
    }

    /**
     * Method to get the first and only House object.
     * It calls the getFirstHouse method of the HouseRepository object to get the first House object.
     *
     * @return Optional House object
     */
    public Optional<House> getFirstHouse() {
        return houseRepository.getFirstHouse();
    }
}