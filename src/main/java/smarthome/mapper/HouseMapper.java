package smarthome.mapper;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.*;
import smarthome.mapper.dto.HouseDTO;
import smarthome.mapper.dto.LocationDTO;

public class HouseMapper {

    /**
     * Method to convert LocationDTO to LocationVO
     * @param locationDTO LocationDTO object
     * @return LocationVO
     */
    public static LocationVO dtoToDomain(LocationDTO locationDTO) {
        if (!validateLocationDTO(locationDTO)) {
            throw new IllegalArgumentException("LocationDTO is null");
        }
        return new LocationVO(createAddressVO(locationDTO), createGPSLocationVO(locationDTO));
    }

    /**
     * Method to convert House Domain Object to HouseDTO
     * @param house House object to be converted
     * @return HouseDTO
     */
    public static HouseDTO domainToDto(House house){
        return HouseDTO.builder()
                .houseID(house.getId().getID())
                .door(house.getLocation().getDoor())
                .street(house.getLocation().getStreet())
                .city(house.getLocation().getCity())
                .country(house.getLocation().getCountry())
                .postalCode(house.getLocation().getPostalCode())
                .latitude(house.getLocation().getLatitude())
                .longitude(house.getLocation().getLongitude())
                .build();
    }

    /**
     * Method to create AddressVO
     * @param locationDTO LocationDTO object
     * @return AddressVO
     */
    private static AddressVO createAddressVO(LocationDTO locationDTO) {
        return new AddressVO(createDoorVO(locationDTO), createStreetVO(locationDTO), createCityVO(locationDTO), createCountryVO(locationDTO), createPostalCodeVO(locationDTO));
    }

    /**
     * Method to create GPSLocationVO
     * @param locationDTO LocationDTO object
     * @return GPSLocationVO
     */
    private static GpsVO createGPSLocationVO(LocationDTO locationDTO) {
        return new GpsVO(createLatitudeVO(locationDTO), createLongitudeVO(locationDTO));
    }

    /**
     * Method to create DoorVO
     * @param locationDTO LocationDTO object
     * @return DoorVO
     */
    private static DoorVO createDoorVO(LocationDTO locationDTO) {
        return new DoorVO(locationDTO.getDoor());
    }

    /**
     * Method to create StreetVO
     * @param locationDTO LocationDTO object
     * @return StreetVO
     */
    private static StreetVO createStreetVO(LocationDTO locationDTO) {
        return new StreetVO(locationDTO.getStreet());
    }

    /**
     * Method to create CityVO
     * @param locationDTO LocationDTO object
     * @return CityVO
     */
    private static CityVO createCityVO(LocationDTO locationDTO) {
        return new CityVO(locationDTO.getCity());
    }

    /**
     * Method to create CountryVO
     * @param locationDTO LocationDTO object
     * @return CountryVO
     */
    private static CountryVO createCountryVO(LocationDTO locationDTO) {
        return new CountryVO(locationDTO.getCountry());
    }

    /**
     * Method to create PostalCodeVO
     * @param locationDTO LocationDTO object
     * @return PostalCodeVO
     */
    private static PostalCodeVO createPostalCodeVO(LocationDTO locationDTO) {
        return new PostalCodeVO(locationDTO.getPostalCode());
    }

    /**
     * Method to create LatitudeVO
     * @param locationDTO LocationDTO object
     * @return LatitudeVO
     */
    private static LatitudeVO createLatitudeVO(LocationDTO locationDTO) {
        return new LatitudeVO(locationDTO.getLatitude());
    }

    /**
     * Method to create LongitudeVO
     * @param locationDTO LocationDTO object
     * @return LongitudeVO
     */
    private static LongitudeVO createLongitudeVO(LocationDTO locationDTO) {
        return new LongitudeVO(locationDTO.getLongitude());
    }

    /**
     *  Method to validate LocationDTO before converting it to LocationVO.
     * @param locationDTO LocationDTO object
     * @return True or false
     */
    private static boolean validateLocationDTO(LocationDTO locationDTO) {
        return locationDTO != null;
    }
}