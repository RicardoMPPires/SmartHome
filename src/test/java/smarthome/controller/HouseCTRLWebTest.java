package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.*;
import smarthome.mapper.dto.LocationDTO;
import smarthome.persistence.HouseRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The HouseCTRLWebTest Class contains tests to the endpoints in the HouseCTRLWeb Class by mocking the repository layer.
 * It also uses a MockMvc to test the Spring MVC Controller without actually starting a server with the application.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class HouseCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HouseRepository houseRepository;

    private LocationVO locationVO;

    @BeforeEach
    public void setUpLocationVO(){

        this.locationVO = new LocationVO(
                new AddressVO(
                        new DoorVO("default door"), new StreetVO("default street"), new CityVO("default city"),
                        new CountryVO("Portugal"), new PostalCodeVO("PT-1234-567")),
                new GpsVO(
                        new LatitudeVO(0), new LongitudeVO(0)));
    }


    /**
     * The test aims to use the getHouse endpoint in the HouseCTRLWeb Class. No Request Body is required, since there
     * is only one House in the system. The test mocks the repository layer to return a House Optional when getFirstHouse()
     * is invoked. The result is a Response Body with the corresponding House data and an HTTP status of 200 (OK).
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void whenGetHouseIsRequested_ShouldReturnResponseBodyWithDefaultHouseInformationAndOkStatusCode() throws Exception {

        //Arrange
        House defaultHouse = new House(locationVO);
        String houseID = defaultHouse.getId().getID();
        when(houseRepository.getFirstHouse()).thenReturn(Optional.of(defaultHouse));

        Link expSelfLink = linkTo(HouseCTRLWeb.class).withSelfRel();
        Link expLinkConfigureLocation = linkTo(HouseCTRLWeb.class).withRel("configureLocation");
        Link expLinklistDevsByFunctionality = linkTo(methodOn(DeviceCTRLWeb.class)
                .getDevicesByFunctionality()).withRel("listDevicesByFunctionality");
        Link expLinkListRooms = linkTo(methodOn(RoomCTRLWeb.class)
                .getListOfRooms()).withRel("listRooms");
        Link expLinkGetMaxTemDiff = linkTo(methodOn(LogCTRLWeb.class)
                .getMaxTempDiff(null, null, null)).withRel("getMaxTempDifference");
        Link expectedPeakPowerConsumption = linkTo(methodOn(LogCTRLWeb.class)
                .getPeakPowerConsumption(null)).withRel("getPeakPowerConsumption");

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/house")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.houseID").value(houseID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.door").value("default door"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("default street"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("default city"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("Portugal"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("PT-1234-567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(0D))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(0D))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href")
                        .value(expSelfLink.getHref()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.configureLocation.href")
                        .value(expLinkConfigureLocation.getHref()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.listDevicesByFunctionality.href")
                        .value(expLinklistDevsByFunctionality.getHref()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.listRooms.href")
                        .value(expLinkListRooms.getHref()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.getMaxTempDifference.href")
                        .value(expLinkGetMaxTemDiff.getHref()))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.getPeakPowerConsumption.href").
                        value(expectedPeakPowerConsumption.getHref()));
    }

    /**
     * The test aims to use the getHouse endpoint in the HouseCTRLWeb Class. No Request Body is required, since there
     * is only one House in the system. The test mocks the repository layer to return an empty Optional when getFirstHouse()
     * is invoked, simulating that there is no House in the system (bootstrap process fails). The result is an HTTP status
     * of 404 (NOT FOUND).
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void whenGetHouseIsRequestedButThereIsNoHouse_ShouldReturnNotFoundStatusCode() throws Exception {

        //Arrange
        when(houseRepository.getFirstHouse()).thenReturn(Optional.empty());

        //Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/house")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Assert
        String resultAsString = result.getResponse().getContentAsString();
        assertEquals("", resultAsString);
    }


    /**
     * The test aims to use the configureLocation endpoint in the HouseCTRLWeb Class. A Request Body containing the
     * required attributes to create a LocationVO is required. The test mocks the repository layer to return a House
     * Optional when getFirstHouse() is invoked. Then the system attempts to update the House location and sends a
     * Response Body containing the House information, showing that the location was successfully updated. The expected
     * result HTTP code status is 200 (OK).
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void whenConfigureLocationIsRequestedWithValidLocationDTO_ShouldReturnResponseBodyWithUpdatedHouseDTOAndOkStatusCode() throws Exception {

        //Arrange
        House defaultHouse = new House(locationVO);
        String houseID = defaultHouse.getId().getID();

        LocationDTO locationDTO = LocationDTO.builder()
                .door("123")
                .street("Rua das Flores")
                .city("Braga")
                .country("Portugal")
                .postalCode("PT-4444-333")
                .latitude(30)
                .longitude(60)
                .build();

        when(houseRepository.getFirstHouse()).thenReturn(Optional.of(defaultHouse));
        when(houseRepository.update(defaultHouse)).thenReturn(true);

        String jsonLocation = objectMapper.writeValueAsString(locationDTO);
        Link expectedLink = linkTo(HouseCTRLWeb.class).withRel("getHouse");

        //Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLocation)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.houseID").value(houseID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.door").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Rua das Flores"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Braga"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("Portugal"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("PT-4444-333"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(60))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.getHouse.href")
                        .value(expectedLink.getHref()))
                .andReturn();

        //Assert
        String resultAsString = result.getResponse().getContentAsString();
        assertNotNull(resultAsString);
    }


    /**
     * The test aims to use the configureLocation endpoint in the HouseCTRLWeb Class. A Request Body containing the
     * required attributes to create a LocationVO is required. The test mocks the repository layer to return a House
     * Optional when getFirstHouse() is invoked. The system then fails to update the House location probably due to some
     * problems during the transaction process. The expected HTTP result code is 422 (UNPROCESSABLE ENTITY). No Response
     * Body is sent.
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void whenConfigureLocationIsRequested_HouseFoundButIsNotUpdated_ShouldReturnUnprocessableStatusCode() throws Exception {

        //Arrange
        House defaultHouse = new House(locationVO);

        LocationDTO locationDTO = LocationDTO.builder()
                .door("123")
                .street("Rua das Flores")
                .city("Braga")
                .country("Portugal")
                .postalCode("PT-4444-333")
                .latitude(30)
                .longitude(60)
                .build();

        when(houseRepository.getFirstHouse()).thenReturn(Optional.of(defaultHouse));
        when(houseRepository.update(defaultHouse)).thenReturn(false);

        String jsonLocation = objectMapper.writeValueAsString(locationDTO);

        //Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLocation)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        //Assert
        String resultAsString = result.getResponse().getContentAsString();
        assertEquals("", resultAsString);
    }


    /**
     * The test aims to use the configureLocation endpoint in the HouseCTRLWeb Class. A Request Body containing the
     * required attributes to create a LocationVO is required. The test mocks the repository layer to return an empty
     * Optional when getFirstHouse() is invoked. The system fails to update the House location, since that no House was
     * found. The expected HTTP result code is 422 (UNPROCESSABLE ENTITY).
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void whenConfigureLocationIsRequested_HouseRepositoryIsEmpty_ShouldReturnUnprocessableStatusCode() throws Exception {

        //Arrange
        LocationDTO locationDTO = LocationDTO.builder()
                .door("123")
                .street("Rua das Flores")
                .city("Braga")
                .country("Portugal")
                .postalCode("PT-4444-333")
                .latitude(30)
                .longitude(60)
                .build();

        when(houseRepository.getFirstHouse()).thenReturn(Optional.empty());

        String jsonLocation = objectMapper.writeValueAsString(locationDTO);

        //Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLocation)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        //Assert
        String resultAsString = result.getResponse().getContentAsString();
        assertEquals("", resultAsString);
    }


    /**
     * The test aims to use the configureLocation endpoint in the HouseCTRLWeb Class. A Request Body containing the
     * required attributes to create a LocationVO is required, but in this case, the system receives an invalid LocationDTO.
     * Invalid Request Body may be:
     * 1. Invalid country;
     * 2. Invalid postal code;
     * 3. Absence of the door, street, city, country or postal code attributes.
     * Note: In case latitude or longitude are missing, the default value is 0 (no error occurs).
     * The expected HTTP result code is 400 (BAD REQUEST), no Response Body is sent.
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void whenConfigureLocationIsRequestedWithInvalidLocationDTO_ShouldReturnBadRequestStatusCode() throws Exception {

        //Arrange
        LocationDTO locationDTO = LocationDTO.builder()
                .door("123")
                .street("Rua das Flores")
                .city("Braga")
                .country("Italy")
                .postalCode("PT-4444-333")
                .latitude(30)
                .longitude(60)
                .build();

        String jsonLocation = objectMapper.writeValueAsString(locationDTO);

        //Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLocation)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //Assert
        String resultAsString = result.getResponse().getContentAsString();
        assertEquals("", resultAsString);
    }


    /**
     * The test aims to use the configureLocation endpoint in the HouseCTRLWeb Class. No Request Body is provided.
     * The expected HTTP result code is 400 (BAD REQUEST), no Response Body is sent.
     * @throws Exception If there is an error in the test execution
     */
    @Test
    void configureLocationIsRequestedWithoutBody_ShouldReturnBadRequestStatusCode() throws Exception {

        //Arrange
        String jsonLocation = "";

        //Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLocation)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        //Assert
        String resultAsString = result.getResponse().getContentAsString();
        assertEquals("", resultAsString);
    }

}