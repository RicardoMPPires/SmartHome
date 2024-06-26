package smarthome.persistence;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.HouseIDVO;

import java.util.Optional;

public interface HouseRepository extends Repository<HouseIDVO, House> {
    Optional<House> getFirstHouse();
    HouseIDVO getFirstHouseIDVO();
    boolean update(House house);
}
