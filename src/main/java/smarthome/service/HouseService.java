package smarthome.service;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.LocationVO;

import java.util.Optional;

public interface HouseService {

    Optional<House> addHouse(LocationVO locationVO);

    Optional<House> updateLocation(LocationVO locationVO);

    Optional<House> getFirstHouse();
}