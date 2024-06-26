package smarthome.persistence.mem;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.AddressVO;
import smarthome.domain.vo.housevo.GpsVO;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.housevo.LocationVO;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HouseRepositoryMemTest {

    /**
     * Test for HouseRepository
     * Given a valid HouseEntity with a valid ID, when the method to save is called, then House is saved
     */
    @Test
    void givenHouseEntity_whenSave_thenHouseIsSaved(){
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house = mock(House.class);
        HouseIDVO houseID = mock(HouseIDVO.class);
        when(house.getId()).thenReturn(houseID);
        // Act
        boolean result = houseRepositoryMem.save(house);
        // Assert
        assertTrue(result);
    }

    /**
     * Test for HouseRepository
     * Given a null HouseEntity, when the method to save is called, then House is not saved
     */
    @Test
    void givenNullHouseEntity_whenSave_thenHouseIsNotSaved(){
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        // Act
        boolean result = houseRepositoryMem.save(null);
        // Assert
        assertFalse(result);
    }


    /**
     * Test for HouseRepository
     * Given a HouseEntity with a null ID, when the method to save is called, then House is not saved
     */
    @Test
    void givenHouseEntityWithNullID_whenSave_thenHouseIsNotSaved(){
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house = mock(House.class);
        when(house.getId()).thenReturn(null);
        // Act
        boolean result = houseRepositoryMem.save(house);
        // Assert
        assertFalse(result);
    }


    /**
     * Test for HouseRepository
     * Given a HouseEntity with an ID already in the repository, when the method to save is called, then House is not
     * saved, returning false
     */
    @Test
    void givenHouseEntityWithRepeatedID_whenSave_thenHouseIsNotSaved(){
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house = mock(House.class);
        HouseIDVO houseID = mock(HouseIDVO.class);
        when(house.getId()).thenReturn(houseID);
        houseRepositoryMem.save(house);
        // Act
        boolean result = houseRepositoryMem.save(house);
        // Assert
        assertFalse(result);
    }


    /**
     * Test for HouseRepository
     * Given a HouseEntity with a valid ID, when the method to find all is called, then House is found
     */
    @Test
    void whenHouseIsSaved_thenItShouldAppearInFindAll() {
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house1 = mock(House.class);
        HouseIDVO houseID = mock(HouseIDVO.class);
        when(house1.getId()).thenReturn(houseID);
        houseRepositoryMem.save(house1);
        // Act
        Iterable<House> foundHouses = houseRepositoryMem.findAll();
        boolean isHousePresent = StreamSupport.stream(foundHouses.spliterator(), false)
                .anyMatch(house -> house.equals(house1));
        // Assert
        assertTrue(isHousePresent);
    }


    /**
     * Test for HouseRepository
     * Given an empty repository, when the method to find all is called, then an empty iterable is returned
     */
    @Test
    void whenRepositoryIsEmpty_thenFindAllReturnsEmptyIterable() {
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        long expected = 0;
        // Act
        Iterable<House> foundHouses = houseRepositoryMem.findAll();
        long result = StreamSupport.stream(foundHouses.spliterator(), false).count();
        // Assert
        assertFalse(foundHouses.iterator().hasNext());
        assertEquals(expected, result);
    }


    /**
     * Test for HouseRepository
     * Given a HouseEntity with a valid ID, when the method to find by ID is called, then House is found
     */
    @Test
    void givenHouseID_whenFindById_thenHouseIsFound() {
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house = mock(House.class);
        HouseIDVO houseID = mock(HouseIDVO.class);
        when(house.getId()).thenReturn(houseID);
        houseRepositoryMem.save(house);
        // Act
        House foundHouse = houseRepositoryMem.findById(houseID);
        // Assert
        assertEquals(house, foundHouse);
    }


    /**
     * Test for HouseRepository
     * Given a HouseEntity with a valid ID but not present in the repository, when the method to find by ID is called,
     * then House is not found
     */
    @Test
    void givenHouseNotPresent_thenReturnNull() {
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house = mock(House.class);
        HouseIDVO houseID = mock(HouseIDVO.class);
        when(house.getId()).thenReturn(houseID);
        // Act
        House foundHouse = houseRepositoryMem.findById(houseID);
        //Assert
        assertNull(foundHouse);
    }


    /**
     * Test for HouseRepository
     * Given a HouseEntity with a valid ID, when the method to verify if present is called, then House is present
     */
    @Test
    void givenHouseID_whenIsPresent_thenHouseIsPresent(){
        // Arrange
        HouseRepositoryMem houseRepositoryMem = new HouseRepositoryMem();
        House house = mock(House.class);
        HouseIDVO houseID = mock(HouseIDVO.class);
        when(house.getId()).thenReturn(houseID);
        houseRepositoryMem.save(house);
        // Act
        boolean isPresent = houseRepositoryMem.isPresent(houseID);
        // Assert
        assertTrue(isPresent);
    }

    /**
     * Test for HouseRepository
     * Test to verify that House is successfully retrieved as expected. LocationVO is created with double attributes and
     * their behaviour is manipulated.
     */
    @Test
    void givenHouse_getFirstHouse(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getDoor()).thenReturn("1");
        when(addressVO.getStreet()).thenReturn("Rua do Ouro");
        when(addressVO.getCity()).thenReturn("Porto");
        when(addressVO.getCountry()).thenReturn("Portugal");
        when(addressVO.getPostalCode()).thenReturn("PT-1234-567");
        when(gpsVO.getLatitude()).thenReturn(75.7);
        when(gpsVO.getLongitude()).thenReturn(155.3);
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        House expectedHouse = new House(locationVO);
        HouseRepositoryMem repository = new HouseRepositoryMem();
        repository.save(expectedHouse);

        //Act

        Optional<House> resultHouse = repository.getFirstHouse();

        //Assert
        assertTrue(resultHouse.isPresent());
        assertEquals(expectedHouse, resultHouse.get());
    }

    /**
     * Test to verify that House ID is successfully retrieved as expected. LocationVO is created with double attributes
     * and their behaviour is manipulated.
     */
    @Test
    void givenHouse_getFirstHouseIDVO(){
        //Arrange
        AddressVO addressVO = mock(AddressVO.class);
        GpsVO gpsVO = mock(GpsVO.class);
        when(addressVO.getDoor()).thenReturn("1");
        when(addressVO.getStreet()).thenReturn("Rua do Ouro");
        when(addressVO.getCity()).thenReturn("Porto");
        when(addressVO.getCountry()).thenReturn("Portugal");
        when(addressVO.getPostalCode()).thenReturn("PT-1234-567");
        when(gpsVO.getLatitude()).thenReturn(75.7);
        when(gpsVO.getLongitude()).thenReturn(155.3);
        LocationVO locationVO = new LocationVO(addressVO,gpsVO);
        House expectedHouse = new House(locationVO);
        HouseRepositoryMem repository = new HouseRepositoryMem();

        //Act
        repository.save(expectedHouse);
        HouseIDVO result = repository.getFirstHouseIDVO();
        //Assert
        assertEquals(expectedHouse.getId(),result);
    }

    /**
     * Test to verify that House is not retrieved, as expected, since the repository is empty.
     */
    @Test
    void givenNothing_getHouse(){
        //Arrange
        HouseRepositoryMem repository = new HouseRepositoryMem();
        //act
        Optional<House> result = repository.getFirstHouse();
        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test to verify that House ID is null, as expected, since the repository is empty.
     */
    @Test
    void givenNothing_getHouseIDVO(){
        //Arrange
        HouseRepositoryMem repository = new HouseRepositoryMem();
        //act
        HouseIDVO result = repository.getFirstHouseIDVO();
        //Assert
        assertNull(result);
    }
}