package smarthome.domain.house;

import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.housevo.LocationVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;

/**
 * Test class for the HouseFactory class.
 */

class HouseFactoryImplTest {

    /**
     * Test that verifies if the HouseFactory creates a House Entity with the given locationVO.
     */

    @Test
    void givenLocation_whenCreatingHouse_thenReturnHouseWithMockedLocation() {
//        Arrange
        LocationVO locationDouble = mock(LocationVO.class);
        HouseFactoryImpl factoryHouse = new HouseFactoryImpl();

        try (MockedConstruction<House> houseDouble = mockConstruction(House.class)) {
//            Act
            House house = factoryHouse.createHouse(locationDouble);
//            Assert
            List<House> houses = houseDouble.constructed();
            House houseResult = houses.get(0);
            assertEquals(house, houseResult);
        }
    }

    /**
     * Test that verifies if the HouseFactory returns null when the locationVO is null.
     */

    @Test
    void givenNullLocation_whenCreatingHouse_thenReturnNull() {
//        Arrange
        HouseFactoryImpl factoryHouse = new HouseFactoryImpl();
//        Act
        House house = factoryHouse.createHouse(null);
//        Assert
        assertNull(house);
    }

       /*
    SYSTEM UNDER TEST: FACTORY + HOUSE
    A double of all the other collaborators is done (essentially the required value objects to create the actuator).
     */

    /**
     * Test that verifies if the house factory creates a House with the provided location.
     * This test intends to assert the successful creation of a House object with the correct LocationVO.
     */

    @Test
    void createHouse_WhenValidLocation_HouseShouldReturnProvidedLocation() {
        //Arrange
        LocationVO expected = mock(LocationVO.class);
        HouseFactoryImpl houseFactoryImpl = new HouseFactoryImpl();

        //Act
        House house = houseFactoryImpl.createHouse(expected);
        LocationVO result = house.getLocation();
        //Assert
        assertEquals(expected,result);
    }

    /**
     * Test that verifies if the house factory creates a House with the provided location and houseID
     * This test intends to assert the successful creation of a House object with the correct LocationVO and HouseIDVO.
     */

    @Test
    void createHouseWithHouseID_HouseShouldContainCorrectParameters() {
        //Arrange
        LocationVO expectedLocation = mock(LocationVO.class);
        HouseIDVO expectedhouseIDVO = mock(HouseIDVO.class);
        HouseFactoryImpl houseFactoryImpl = new HouseFactoryImpl();

        //Act
        House house = houseFactoryImpl.createHouse(expectedhouseIDVO,expectedLocation);
        LocationVO resultLocation = house.getLocation();
        HouseIDVO resultID = house.getId();
        //Assert
        assertEquals(expectedLocation,resultLocation);
        assertEquals(expectedhouseIDVO,resultID);
    }



}