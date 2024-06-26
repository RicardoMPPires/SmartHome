package smarthome.mapper.assembler;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.HouseFactoryImpl;
import smarthome.domain.vo.housevo.*;
import smarthome.persistence.jpa.datamodel.HouseDataModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HouseAssemblerTest {

    /**
     * Test case to verify that the toDomain method of HouseAssembler creates the correct HouseDataModel object.
     * This test ensures that the HouseDataModel created from a House object retains its original attributes.
     * It verifies that the sameAs method of HouseDataModel correctly compares the created HouseDataModel
     * with the original House object.
     */

    @Test
    void toDomain_ShouldCreateCorrectHouseObject(){

        //Arrange

        //Address value objects
        DoorVO doorVO = new DoorVO("188");
        StreetVO streetVO = new StreetVO("Grove street home");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("France");
        PostalCodeVO postalCodeVO = new PostalCodeVO("FR-66-66");
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);

        //Gps value objects
        LatitudeVO latitudeVO = new LatitudeVO(6.8);
        LongitudeVO longitudeVO = new LongitudeVO(45.9);
        GpsVO gpsVO = new GpsVO(latitudeVO, longitudeVO);

        LocationVO locationVO = new LocationVO(addressVO, gpsVO);

        //House that Will be injected in HouseDataModel constructor
        House house = new House(locationVO);

        //Act
        HouseDataModel houseDataModel = new HouseDataModel(house);

        //Assert
        assertEquals(houseDataModel.getId(),house.getId().getID());
        assertEquals(houseDataModel.getDoor(),locationVO.getDoor());
        assertEquals(houseDataModel.getStreet(), locationVO.getStreet());
        assertEquals(houseDataModel.getCity(), locationVO.getCity());
        assertEquals(houseDataModel.getCountry(), locationVO.getCountry());
        assertEquals(houseDataModel.getLatitude(),locationVO.getLatitude());
        assertEquals(houseDataModel.getLongitude(),locationVO.getLongitude());
    }

    /**
     * Test case to verify that the toDomain method of HouseAssembler returns the correct House iterable.
     * This test ensures that the HouseAssembler properly converts a list of HouseDataModel objects into
     * an iterable of House objects.
     * It verifies that the size of the returned iterable matches the expected size and that each House object
     * in the iterable has the expected attributes.
     */
    @Test
    void toDomain_ShouldReturnCorrectHouseIterable(){

        //Arrange
        HouseFactory houseFactory = new HouseFactoryImpl();
        int expectedSize = 2;

        DoorVO doorVO = new DoorVO("188");
        DoorVO secondDoorVO = new DoorVO("288");

        StreetVO streetVO = new StreetVO("Grove street home");
        CityVO cityVO = new CityVO("Porto");
        CountryVO countryVO = new CountryVO("France");
        PostalCodeVO postalCodeVO = new PostalCodeVO("FR-66-66");

        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        AddressVO secondAddressVO = new AddressVO(secondDoorVO, streetVO, cityVO, countryVO, postalCodeVO);

        LatitudeVO latitudeVO = new LatitudeVO(6.8);
        LongitudeVO longitudeVO = new LongitudeVO(45.9);
        GpsVO gpsVO = new GpsVO(latitudeVO, longitudeVO);

        LocationVO locationVO = new LocationVO(addressVO, gpsVO);
        LocationVO secondLocationVO = new LocationVO(secondAddressVO, gpsVO);

        House house = new House(locationVO);
        House secondHouse = new House(secondLocationVO);

        HouseDataModel houseDataModel = new HouseDataModel(house);
        HouseDataModel secondhouseDataModel = new HouseDataModel(secondHouse);

        ArrayList<HouseDataModel> houseDataModels = new ArrayList<>();
        houseDataModels.add(houseDataModel);
        houseDataModels.add(secondhouseDataModel);

        //Act

        Iterable<House> houseIterable = HouseAssembler.toDomain(houseFactory,houseDataModels);

        //Assert
        //Asserts that the size matches the expected
        Iterator<House> iterator = houseIterable.iterator();
        int resultSize = IteratorUtils.size(iterator);
        assertEquals(expectedSize,resultSize);

        //Asserts that each element has the expected attributes

        List<House> houseList = new ArrayList<>();
        houseIterable.forEach(houseList::add);

        House firstEntry = houseList.get(0);

        LocationVO locationVO1 = firstEntry.getLocation();
        assertEquals(houseDataModel.getId(),firstEntry.getId().getID());
        assertEquals(houseDataModel.getDoor(),locationVO1.getDoor());
        assertEquals(houseDataModel.getStreet(), locationVO1.getStreet());
        assertEquals(houseDataModel.getCity(), locationVO1.getCity());
        assertEquals(houseDataModel.getCountry(), locationVO1.getCountry());
        assertEquals(houseDataModel.getLatitude(),locationVO1.getLatitude());
        assertEquals(houseDataModel.getLongitude(),locationVO1.getLongitude());

        House secondEntry = houseList.get(1);
        LocationVO locationVO2 = secondEntry.getLocation();
        assertEquals(secondhouseDataModel.getId(),secondEntry.getId().getID());
        assertEquals(secondhouseDataModel.getDoor(),locationVO2.getDoor());
        assertEquals(secondhouseDataModel.getStreet(), locationVO2.getStreet());
        assertEquals(secondhouseDataModel.getCity(), locationVO2.getCity());
        assertEquals(secondhouseDataModel.getCountry(), locationVO2.getCountry());
        assertEquals(secondhouseDataModel.getLatitude(),locationVO2.getLatitude());
        assertEquals(secondhouseDataModel.getLongitude(),locationVO2.getLongitude());


    }
}
