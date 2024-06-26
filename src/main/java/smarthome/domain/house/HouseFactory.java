package smarthome.domain.house;

import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.housevo.LocationVO;

public interface HouseFactory {
    House createHouse(LocationVO locationVO);

    House createHouse(HouseIDVO houseIDVO, LocationVO locationVO);
}
