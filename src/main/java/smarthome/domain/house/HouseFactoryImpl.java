package smarthome.domain.house;

import org.springframework.stereotype.Component;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.housevo.LocationVO;

/**
 * Factory class for the House Entity.
 */

@Component
public class HouseFactoryImpl implements HouseFactory{

    /**
     * Method that creates a House Entity with the given locationVO.
     *
     * @param locationVO The locationVO of the house.
     * @return The House Entity if the locationVO is valid, null otherwise.
     */

    @Override
    public House createHouse(LocationVO locationVO) {
        try {
            return new House(locationVO);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Method that creates a House Entity with the given locationVO and HouseIDVO.
     * This method should be used when retrieving a house from persistence that has already an id.
     * @param houseIDVO The HouseIDVO of the house.
     * @param locationVO The locationVO of the house.
     * @return the created House object.
     */
    @Override
    public House createHouse(HouseIDVO houseIDVO, LocationVO locationVO) {
        return new House(houseIDVO,locationVO);
    }
}
