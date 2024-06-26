package smarthome.service;

import org.junit.jupiter.api.Test;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.house.HouseFactoryImpl;
import smarthome.domain.vo.housevo.LocationVO;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.mem.HouseRepositoryMem;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for HouseService
 */

class HouseServiceImplTest {

    /**
     * Test case to check if IllegalArgumentException is thrown when HouseRepository is null
     */
    @Test
    void givenNullHouseRepository_whenHouseServiceIsCreated_thenThrowIllegalArgumentException() {
//        Arrange
        HouseFactoryImpl v1HouseFactory = new HouseFactoryImpl();
        String expectedMessage = "Invalid parameters";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new HouseServiceImpl(null, v1HouseFactory));
//        Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to check if IllegalArgumentException is thrown when HouseFactory is null
     */

    @Test
    void givenNullHouseFactory_whenHouseServiceIsCreated_thenThrowIllegalArgumentException() {
//        Arrange
        HouseRepositoryMem memHouseRepository = new HouseRepositoryMem();
        String expectedMessage = "Invalid parameters";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> new HouseServiceImpl(memHouseRepository, null));
//        Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to check if IllegalArgumentException is thrown when LocationVO is null on addHouse method
     */

    @Test
    void givenValidHouseRepositoryAndHouseFactory_whenAddHouseIsCalledWithInvalidLocation_thenThrowIllegalArgumentException() {
//        Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        HouseFactory houseFactory = mock(HouseFactory.class);
        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
        String expectedMessage = "Invalid location";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> houseService.addHouse(null));
//        Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


    /**
     * Test case to check if IllegalArgumentException is thrown when LocationVO is null on updateLocation
     */

    @Test
    void givenValidHouseRepositoryAndHouseFactory_whenUpdateLocationIsCalledWithInvalidLocation_thenThrowIllegalArgumentException() {
//        Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        HouseFactory houseFactory = mock(HouseFactory.class);
        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
        String expectedMessage = "Invalid location";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> houseService.updateLocation(null));
//        Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to check if an empty optional is returned when LocationVO is valid but there is no House in the
     * repository.
     */
    @Test
    void whenUpdateLocationIsInvokedButThereIsNoHouse_thenShouldReturnAnEmptyOptional() {
//        Arrange
        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.getFirstHouse()).thenReturn(Optional.empty());
        HouseFactory houseFactory = mock(HouseFactory.class);

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
        LocationVO locationVO = mock(LocationVO.class);

//        Act
        Optional<House> result = houseService.updateLocation(locationVO);

//        Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test case to check if a non-empty optional with a House object is returned, with the expected house location,
     * i.e, update location succeeds.
     */
    @Test
    void whenUpdateLocationIsInvoked_AndConfigurationSucceeds_thenShouldReturnOptionalWithHouse() {
//        Arrange
        House houseDouble = mock(House.class);

        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.getFirstHouse()).thenReturn(Optional.of(houseDouble));
        when(houseRepository.update(houseDouble)).thenReturn(true);
        HouseFactory houseFactory = mock(HouseFactory.class);

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
        LocationVO locationDouble = mock(LocationVO.class);
        when(houseDouble.getLocation()).thenReturn(locationDouble);

//        Act
        Optional<House> result = houseService.updateLocation(locationDouble);
//        Assert
        assertTrue(result.isPresent());
        assertEquals(locationDouble, result.get().getLocation());
    }

    /**
     * Test case to check if an empty optional is returned, i.e, update location does not succeed.
     * In this case, house update fails during the transaction on the repository side.
     */
    @Test
    void whenUpdateLocationIsInvoked_AndConfigurationDoesNotSucceed_thenShouldReturnEmptyOptional() {
//        Arrange
        House houseDouble = mock(House.class);

        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.getFirstHouse()).thenReturn(Optional.of(houseDouble));
        when(houseRepository.update(houseDouble)).thenReturn(false);
        HouseFactory houseFactory = mock(HouseFactory.class);

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
        LocationVO locationDouble = mock(LocationVO.class);

//        Act
        Optional<House> result = houseService.updateLocation(locationDouble);
//        Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test case to check if an empty House optional is returned when HouseFactory returns null (fails to create House).
     */
    @Test
    void whenAddHouseIsCalled_FactoryCreatesNullHouse_thenRepositoryShouldReturnEmptyOptional() {
//        Arrange
        LocationVO locationVO = mock(LocationVO.class);

        HouseFactory houseFactory = mock(HouseFactory.class);
        when(houseFactory.createHouse(locationVO)).thenReturn(null);

        HouseRepository houseRepository = mock(HouseRepository.class);

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
//        Act
        Optional<House> returnedHouse = houseService.addHouse(locationVO);
//        Assert
        assertTrue(returnedHouse.isEmpty());

    }

    /**
     * Test case to check if House object is saved in the repository when HouseFactory returns an optional with a House
     * object
     */
    @Test
    void whenAddHouseIsCalled_FactoryCreatesValidHouse_thenShouldReturnOptionalWithHouse() {
//        Arrange
        LocationVO locationVO = mock(LocationVO.class);
        House houseDouble = mock(House.class);

        HouseFactory houseFactory = mock(HouseFactory.class);
        when(houseFactory.createHouse(locationVO)).thenReturn(houseDouble);

        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.save(houseDouble)).thenReturn(true);

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);
//        Act
        Optional<House> returnedHouse = houseService.addHouse(locationVO);
//        Assert
        assertTrue(returnedHouse.isPresent());
        assertEquals(houseDouble, returnedHouse.get());
    }




    /**
     * Test that asserts when there is a House in the repository, the corresponding House is retrieved.
     */
    @Test
    void whenGetFirstHouseIsInvoked_ThenShouldReturnHouse(){
//        Arrange
        House houseDouble = mock(House.class);

        HouseFactory houseFactory = mock(HouseFactory.class);

        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.getFirstHouse()).thenReturn(Optional.of(houseDouble));

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);

//        Act
        Optional<House> result = houseService.getFirstHouse();

//        Assert
        assertTrue(result.isPresent());
        assertEquals(houseDouble, result.get());
    }

    /**
     * Test that asserts when there is no House in the repository, an empty optional is retrieved.
     */
    @Test
    void whenGetFirstHouseIsInvoked_ThenShouldReturnNullHouse(){
//        Arrange
        HouseFactory houseFactory = mock(HouseFactory.class);

        HouseRepository houseRepository = mock(HouseRepository.class);
        when(houseRepository.getFirstHouse()).thenReturn(Optional.empty());

        HouseService houseService = new HouseServiceImpl(houseRepository, houseFactory);

//        Act
        Optional<House> result = houseService.getFirstHouse();

//        Assert
        assertTrue(result.isEmpty());
    }

}