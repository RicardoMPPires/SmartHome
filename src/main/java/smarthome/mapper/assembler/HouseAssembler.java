package smarthome.mapper.assembler;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.vo.housevo.*;
import smarthome.persistence.jpa.datamodel.HouseDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HouseAssembler {

    /**
     * Converts a HouseDataModel instance to a House domain object.
     *
     * @param houseFactory    The factory used to create House instances.
     * @param houseDataModel  The HouseDataModel instance to convert.
     * @return                The converted House domain object.
     */
    static public House toDomain(HouseFactory houseFactory, HouseDataModel houseDataModel) {
        // Create value objects from data model
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseDataModel.getId()));
        DoorVO doorVO = new DoorVO(houseDataModel.getDoor());
        StreetVO streetVO = new StreetVO(houseDataModel.getStreet());
        CityVO cityVO = new CityVO(houseDataModel.getCity());
        CountryVO countryVO = new CountryVO(houseDataModel.getCountry());
        PostalCodeVO postalCodeVO = new PostalCodeVO(houseDataModel.getPostalCode());
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        LatitudeVO latitudeVO = new LatitudeVO(houseDataModel.getLatitude());
        LongitudeVO longitudeVO = new LongitudeVO(houseDataModel.getLongitude());
        GpsVO gpsVO = new GpsVO(latitudeVO, longitudeVO);
        LocationVO locationVO = new LocationVO(addressVO, gpsVO);

        // Create and return House domain object
        return houseFactory.createHouse(houseIDVO, locationVO);
    }

    /**
     * Converts an iterable of HouseDataModel instances to a collection of House domain objects.
     *
     * @param houseFactory             The factory used to create House instances.
     * @param houseDataModelIterable   The iterable of HouseDataModel instances to convert.
     * @return                         A collection of converted House domain objects.
     */
    public static Iterable<House> toDomain(HouseFactory houseFactory, Iterable<HouseDataModel> houseDataModelIterable) {
        
        List<House> houses = new ArrayList<>();
        houseDataModelIterable.forEach(houseDataModel -> {
            House house = toDomain(houseFactory, houseDataModel);
            houses.add(house);
        });
        return houses;
    }


}
