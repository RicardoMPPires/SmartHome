package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.domain.actuator.*;
import smarthome.domain.device.Device;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.dto.ActuatorDTO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.service.ActuatorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ActuatorCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private ActuatorRepository actuatorRepository;

    @MockBean
    private ActuatorTypeRepository actuatorTypeRepository;


    /**
     * Test for the ActuatorCTRLWeb constructor. It tests if an IllegalArgumentException is thrown when the
     * ActuatorService is null.
     */
    @Test
    void whenActuatorServiceIsNull_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorService actuatorService = null;

        String expected = "Invalid service";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ActuatorCTRLWeb(actuatorService));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);
    }


    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO when a null actuatorType is
     * passed. The test creates an ActuatorDTO with a null actuatorType and converts it to a JSON string.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the actuatorType is a required field for creating an actuator, so a null actuatorType should
     * result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithNullActuatorType_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = null;
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO when a null deviceId is passed.
     * The test creates an ActuatorDTO with a null deviceId and converts it to a JSON string.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the deviceId is a required field for creating an actuator, so a null deviceId should result in
     * a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithNullDeviceId_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = null;
        String actuatorTypeID = "RollerBlindActuator";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a null actuatorName is
     * passed. The test creates an ActuatorDTO with a null actuatorName and converts it to a JSON string.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the actuatorName is a required field for creating an actuator, so a null actuatorName should
     * result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithNullActuatorName_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = null;
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a null lowerLimit is passed.
     * The test creates an ActuatorDTO with a null lowerLimit and converts it to a JSON string.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the lowerLimit is a required field for creating an actuator, so a null lowerLimit should result
     * in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithNullLowerLimit_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = null;
        String upperLimit = "30";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with an invalid lowerLimit
     * is passed. The test creates an ActuatorDTO with a lowerLimit that is a decimal value, which is invalid because
     * the lowerLimit should be an integer.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the lowerLimit is a required field for creating an actuator, and it should be an integer, so an
     * invalid lowerLimit should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithInvalidLowerLimit_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a null upperLimit is passed.
     * The test creates an ActuatorDTO with a null upperLimit and converts it to a JSON string.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the upperLimit is a required field for creating an actuator, so a null upperLimit should result
     * in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithNullUpperLimit_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10";
        String upperLimit = null;
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with an invalid upperLimit is
     * passed. The test creates an ActuatorDTO with an upperLimit that is a decimal value, which is invalid because
     * the upperLimit should be an integer.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the upperLimit is a required field for creating an actuator, and it should be an integer, so
     * an invalid upperLimit should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithInvalidUpperLimit_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10";
        String upperLimit = "30.0";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a lowerLimit higher than
     * the upperLimit is passed. The test creates an ActuatorDTO with a lowerLimit of "30" and an upperLimit of "10",
     * which is invalid because the lowerLimit should not be higher than the upperLimit.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the lowerLimit should not be higher than the upperLimit for creating an actuator, so an invalid
     * lowerLimit and upperLimit should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithLowerHigherThanUpperInteger_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "30";
        String upperLimit = "10";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a null precision is passed.
     * The test creates an ActuatorDTO with a null precision and converts it to a JSON string.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the precision is a required field for creating an actuator, so a null precision should result
     * in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithNullPrecision_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30.0";
        String precision = null;
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with an invalid precision is
     * passed. The test creates an ActuatorDTO with a precision of "1", which is invalid because the precision should
     * be a decimal value less than 1.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the precision is a required field for creating an actuator, and it should be a decimal value
     * less than 1, so an invalid precision should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithInvalidPrecision_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30.0";
        String precision = "1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a lowerLimit higher than
     * the upperLimit is passed. The test creates an ActuatorDTO with a lowerLimit of "30.0" and an upperLimit of
     * "10.0", which is invalid because the lowerLimit should not be higher than the upperLimit.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the lowerLimit should not be higher than the upperLimit for creating an actuator, so an invalid
     * lowerLimit and upperLimit should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithLowerHigherThanUpperDecimal_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "30.0";
        String upperLimit = "10.0";
        String precision = "0.1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a precision equal to zero
     * is passed. The test creates an ActuatorDTO with a precision of "-0.0", which is invalid because the precision
     * should be a decimal value greater than 0 and less than 1.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the precision is a required field for creating an actuator, and it should be a decimal value
     * greater than 0 and less than 1, so a precision equal to zero should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithPrecisionEqualToZero_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30.0";
        String precision = "-0.0";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a precision equal to one
     * is passed. The test creates an ActuatorDTO with a precision of "1", which is invalid because the precision
     * should be a decimal value less than 1.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the precision is a required field for creating an actuator, and it should be a decimal value
     * less than 1, so a precision equal to one should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorDTOWithPrecisionEqualToOne_whenAddActuatorToDeviceCalled_thenReturnBadRequest() throws Exception {
        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30.0";
        String precision = "1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when an ActuatorDTO with a non-existing actuatorType
     * is passed. The test creates an ActuatorDTO with an actuatorType of "NotPresent", which is invalid.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the actuatorType is a required field for creating an actuator, and it should exist in the
     * actuatorTypeRepository, so a non-existing actuatorType should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidDTO_whenAddActuatorCalled_ifActuatorToDeviceTypeNotPresent_thenReturnBadRequest() throws Exception {

        //Arrange
        String actuatorName = "Blind Roller";
        String deviceId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "NotPresent";
        String lowerLimit = "30.0";
        String upperLimit = "10.0";
        String precision = "0.1";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);

        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed but the device
     * associated with the ActuatorDTO does not exist in the system.
     * The test creates a valid ActuatorDTO and a Device object, but the device repository is stubbed to return false.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO
     * as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the device associated with the ActuatorDTO is a required field for creating an actuator, and
     * it should exist in the device repository, so a non-existing device should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorToDeviceCalled_ifDeviceNotPresent_thenReturnUnprocessableEntityCode()
            throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);
        device.deactivateDevice();

        String actuatorName = "Blind Roller";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "30.0";
        String upperLimit = "10.0";
        String precision = "0.1";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));



        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed but the device
     * associated with the ActuatorDTO is deactivated in the system.
     * The test creates a valid ActuatorDTO and a Device object, but the device is deactivated.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO
     * as the request body.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the device associated with the ActuatorDTO is a required field for creating an actuator, and
     * it should be active in the device repository, so a deactivated device should result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorToDeviceCalled_ifDeviceDeactivated_thenReturnBadRequest()
            throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);
        device.deactivateDevice();

        String actuatorName = "Blind Roller";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "30.0";
        String upperLimit = "10.0";
        String precision = "0.1";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));



        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed but the saving
     * of the Actuator in the repository fails.
     * The test creates a valid ActuatorDTO and a Device object, and the repositories are stubbed to return true for
     * the existence of the actuator type and the device, but false for the saving of the Actuator.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO as the
     * request body.
     * The test asserts that the response status is UNPROCESSABLE_ENTITY (422) and that the response body is empty.
     * This is because the saving of the Actuator in the repository is a required operation for creating an actuator,
     * and if it fails, it should result in an unprocessable entity response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorToDeviceCalled_ifSavingFails_thenReturnUnprocessableEntityCode()
            throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        String actuatorName = "Blind Roller";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "RollerBlindActuator";
        String lowerLimit = "30.0";
        String upperLimit = "10.0";
        String precision = "0.1";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));

        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.save(any(Actuator.class))).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed for a
     * DecimalValueActuator. The test creates a valid ActuatorDTO and a Device object, and the repositories are stubbed
     * to return true for the existence of the actuator type and the device, and for the saving of the Actuator.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO as the
     * request body.
     * The test asserts that the response status is CREATED (201) and that the response body contains the correct
     * actuator details.
     * This is because the ActuatorDTO is valid and the actuator type and device exist in their respective repositories,
     * and the saving of the Actuator in the repository is successful, so it should result in a created response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorCalledForDecimalValueActuator_ToDevice_thenReturnCreatedCode() throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        String actuatorName = "Actuator";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "DecimalValueActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30.0";
        String precision = "0.1";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));

        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.save(any(Actuator.class))).thenReturn(true);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$.lowerLimit").value(lowerLimit))
                .andExpect(jsonPath("$.upperLimit").value(upperLimit))
                .andExpect(jsonPath("$.precision").value(precision))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed for an
     * IntegerValueActuator. The test creates a valid ActuatorDTO and a Device object, and the repositories are stubbed
     * to return true for the existence of the actuator type and the device, and for the saving of the Actuator.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO as the
     * request body.
     * The test asserts that the response status is CREATED (201) and that the response body contains the correct
     * actuator details.
     * This is because the ActuatorDTO is valid and the actuator type and device exist in their respective repositories,
     * and the saving of the Actuator in the repository is successful, so it should result in a created response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorCalledForIntegerValueActuator_ToDevice_thenReturnCreatedCode() throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        String actuatorName = "Actuator";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "IntegerValueActuator";
        String lowerLimit = "10";
        String upperLimit = "30";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));

        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.save(any(Actuator.class))).thenReturn(true);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$.lowerLimit").value(lowerLimit))
                .andExpect(jsonPath("$.upperLimit").value(upperLimit))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed for a
     * RollerBlindActuator. The test creates a valid ActuatorDTO and a Device object, and the repositories are stubbed
     * to return true for the existence of the actuator type and the device, and for the saving of the Actuator.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO as the
     * request body.
     * The test asserts that the response status is CREATED (201) and that the response body contains the correct
     * actuator details.
     * This is because the ActuatorDTO is valid and the actuator type and device exist in their respective repositories,
     * and the saving of the Actuator in the repository is successful, so it should result in a created response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorCalledForRollerBlindActuator_ToDevice_thenReturnCreatedCode() throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        String actuatorName = "Actuator";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "RollerBlindActuator";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));

        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.save(any(Actuator.class))).thenReturn(true);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the addActuator endpoint when a valid ActuatorDTO is passed for a
     * SwitchActuator. The test creates a valid ActuatorDTO and a Device object, and the repositories are stubbed to
     * return true for the existence of the actuator type and the device, and for the saving of the Actuator.
     * It then performs a POST request to the "/actuators" endpoint with the JSON string of the ActuatorDTO as the
     * request body.
     * The test asserts that the response status is CREATED (201) and that the response body contains the correct
     * actuator details.
     * This is because the ActuatorDTO is valid and the actuator type and device exist in their respective repositories,
     * and the saving of the Actuator in the repository is successful, so it should result in a created response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenValidActuatorDTO_whenAddActuatorCalledForSwitchActuator_ToDevice_thenReturnCreatedCode() throws Exception {
        //Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        String actuatorName = "Actuator";
        String deviceId = device.getId().getID();
        String actuatorTypeID = "SwitchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));

        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(actuatorName)
                .deviceID(deviceId)
                .actuatorTypeID(actuatorTypeID)
                .build();

        String actuatorJson = objectMapper.writeValueAsString(actuatorDTO);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.save(any(Actuator.class))).thenReturn(true);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/actuators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(actuatorJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the getActuatorById endpoint when a null ActuatorID is passed.
     * It then performs a GET request to the "/actuators/{actuatorId}" endpoint with the null ActuatorID as the path
     * variable.
     * The test asserts that the response status is BAD_REQUEST (400) and that the response body is empty.
     * This is because the ActuatorID is a required parameter for retrieving an actuator, and if it is null, it should
     * result in a bad request.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenInvalidActuatorId_whenGetActuatorByIdCalled_thenReturnsBadRequest() throws Exception {
        //Arrange
        ActuatorIDVO actuatorId = null;

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the getActuatorById endpoint when an ActuatorID is passed that does not exist
     * in the repository.
     * It then performs a GET request to the "/actuators/{actuatorId}" endpoint with the non-existent ActuatorID as the
     * path variable.
     * The test asserts that the response status is NOT_FOUND (404) and that the response body is empty.
     * This is because the ActuatorID is a required parameter for retrieving an actuator, and if it does not exist in
     * the repository, it should result in a not found response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenNonExistentActuatorId_whenGetActuatorByIdCalled_theReturnNotFound() throws Exception {
        //Arrange
        String actuatorId = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies the behavior of the getActuatorById endpoint when a valid ActuatorID is passed and the
     * actuator is of type DecimalValueActuator.
     * The test creates a DecimalValueActuator and the repositories are stubbed to return true for the existence of the
     * actuator and to return the created actuator.
     * It then performs a GET request to the "/actuators/{actuatorId}" endpoint with the ActuatorID as the path
     * variable.
     * The test asserts that the response status is OK (200) and that the response body contains the correct actuator
     * details. This is because the ActuatorID is valid and the actuator exists in the repository, so it should result
     * in an OK response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenActuatorId_whenGetActuatorById_ifDecimalValueActuator_thenReturnActuatorDTO() throws Exception {
        //Arrange
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorName = "Actuator";
        String deviceId = "fa85f642-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "DecimalValueActuator";
        String lowerLimit = "10.0";
        String upperLimit = "30.0";
        String precision = "0.1";

        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorName);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        DecimalSettingsVO decimalSettingsVO = new DecimalSettingsVO(lowerLimit,upperLimit,precision);
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");

        Actuator actuator = new DecimalValueActuator(actuatorIDVO,actuatorNameVO,actuatorTypeIDVO,deviceIDVO,
                decimalSettingsVO,statusVO);

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        Link expectedLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorId)).withSelfRel();

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actuatorId").value(actuatorId))
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$.lowerLimit").value(lowerLimit))
                .andExpect(jsonPath("$.upperLimit").value(upperLimit))
                .andExpect(jsonPath("$.precision").value(precision))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedLink.getHref()))
                .andReturn();
    }

    /**
     * This test verifies the behavior of the getActuatorById endpoint when a valid ActuatorID is passed and the
     * actuator is of type IntegerValueActuator.
     * The test creates an IntegerValueActuator and the repositories are stubbed to return true for the existence of
     * the actuator and to return the created actuator.
     * It then performs a GET request to the "/actuators/{actuatorId}" endpoint with the ActuatorID as the
     * path variable.
     * The test asserts that the response status is OK (200) and that the response body contains the correct actuator
     * details. This is because the ActuatorID is valid and the actuator exists in the repository, so it should result
     * in an OK response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenActuatorId_whenGetActuatorById_ifIntegerValueActuator_thenReturnActuatorDTO() throws Exception {
        //Arrange
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorName = "Actuator";
        String deviceId = "fa85f642-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "IntegerValueActuator";
        String lowerLimit = "10";
        String upperLimit = "30";

        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorName);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        IntegerSettingsVO integerSettingsVO = new IntegerSettingsVO(lowerLimit,upperLimit);
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");

        Actuator actuator = new IntegerValueActuator(actuatorIDVO,actuatorNameVO,actuatorTypeIDVO,deviceIDVO,
                integerSettingsVO,statusVO);

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        Link expectedLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorId)).withSelfRel();

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actuatorId").value(actuatorId))
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$.lowerLimit").value(lowerLimit))
                .andExpect(jsonPath("$.upperLimit").value(upperLimit))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedLink.getHref()))
                .andReturn();
    }

    /**
     * This test verifies the behavior of the getActuatorById endpoint when a valid ActuatorID is passed and the
     * actuator is of type SwitchActuator.
     * The test creates a SwitchActuator and the repositories are stubbed to return true for the existence of the
     * actuator and to return the created actuator.
     * It then performs a GET request to the "/actuators/{actuatorId}" endpoint with the ActuatorID as the path
     * variable.
     * The test asserts that the response status is OK (200) and that the response body contains the correct actuator
     * details. This is because the ActuatorID is valid and the actuator exists in the repository, so it should result
     * in an OK response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenActuatorId_whenGetActuatorById_ifSwitchActuator_thenReturnActuatorDTO() throws Exception {
        //Arrange
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorName = "Actuator";
        String deviceId = "fa85f642-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "SwitchActuator";

        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorName);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");

        Actuator actuator = new SwitchActuator(actuatorIDVO,actuatorNameVO,actuatorTypeIDVO,deviceIDVO,statusVO);

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        Link expectedLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorId)).withSelfRel();

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actuatorId").value(actuatorId))
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedLink.getHref()))
                .andReturn();
    }


    /**
     * This test verifies the behavior of the getActuatorById endpoint when a valid ActuatorID is passed and the
     * actuator is of type RollerBlindActuator.
     * The test creates a SwitchActuator (which seems to be a mistake, it should be a RollerBlindActuator) and the
     * repositories are stubbed to return true for the existence of the actuator and to return the created actuator.
     * It then performs a GET request to the "/actuators/{actuatorId}" endpoint with the ActuatorID as the
     * path variable.
     * The test asserts that the response status is OK (200) and that the response body contains the correct actuator
     * details. This is because the ActuatorID is valid and the actuator exists in the repository, so it should result
     * in an OK response.
     *
     * @throws Exception if any exception occurs during the execution of the request or the assertions
     */
    @Test
    void givenActuatorId_whenGetActuatorById_ifRollerBlindActuator_thenReturnActuatorDTO() throws Exception {
        //Arrange
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        String actuatorName = "Actuator";
        String deviceId = "fa85f642-4562-b3fc-5717-6afa62c963f6";
        String actuatorTypeID = "RollerBlindActuator";

        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorName);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceId));
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);
        ActuatorStatusVO statusVO = new ActuatorStatusVO("This is valid");

        Actuator actuator = new SwitchActuator(actuatorIDVO,actuatorNameVO,actuatorTypeIDVO,deviceIDVO,statusVO);

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        Link expectedLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorId)).withSelfRel();

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators/" + actuatorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actuatorId").value(actuatorId))
                .andExpect(jsonPath("$.actuatorName").value(actuatorName))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeID))
                .andExpect(jsonPath("$.deviceID").value(deviceId))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedLink.getHref()))
                .andReturn();
    }


    /**
     * This test method tests the getActuatorsByDeviceID endpoint in the ActuatorsCTRLWeb class. It tests the endpoint by
     * providing a valid device ID and checking if the response contains the correct actuator information. The test mocks
     * the repository layer to return a list of actuators when the findByDeviceID method is called with the provided device ID.
     * The test expects the response to have an HTTP status of 200 (OK) and the correct actuator information in the
     * response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenDeviceID_whenGetActuatorsByDeviceID_thenReturnActuatorDTOCollection() throws Exception {
        //Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        String firstActuatorName = "Actuator 1";
        String firstActuatorTypeID = "RollerBlindActuator";
        String firstActuatorDeviceID = deviceID;

        ActuatorNameVO firstActuatorNameVO = new ActuatorNameVO(firstActuatorName);
        ActuatorTypeIDVO firstActuatorTypeIDVO = new ActuatorTypeIDVO(firstActuatorTypeID);

        Actuator firstActuator = new RollerBlindActuator(firstActuatorNameVO, firstActuatorTypeIDVO, deviceIDVO);

        String secondActuatorName = "Actuator 2";
        String secondActuatorTypeID = "SwitchActuator";
        String secondActuatorDeviceID = deviceID;

        ActuatorNameVO secondActuatorNameVO = new ActuatorNameVO(secondActuatorName);
        ActuatorTypeIDVO secondActuatorTypeIDVO = new ActuatorTypeIDVO(secondActuatorTypeID);

        Actuator secondActuator = new SwitchActuator(secondActuatorNameVO, secondActuatorTypeIDVO, deviceIDVO);

        List<Actuator> actuators = new ArrayList<>();
        actuators.add(firstActuator);
        actuators.add(secondActuator);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.findByDeviceID(deviceIDVO)).thenReturn(actuators);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.actuatorDTOList").exists())
                .andExpect(jsonPath("$._embedded.actuatorDTOList[0].actuatorName").value(firstActuatorName))
                .andExpect(jsonPath("$._embedded.actuatorDTOList[0].actuatorTypeID").value(firstActuatorTypeID))
                .andExpect(jsonPath("$._embedded.actuatorDTOList[0].deviceID").value(firstActuatorDeviceID))
                .andExpect(jsonPath("$._embedded.actuatorDTOList[0]._links.self").exists())
                .andExpect(jsonPath("$._embedded.actuatorDTOList[1].actuatorName").value(secondActuatorName))
                .andExpect(jsonPath("$._embedded.actuatorDTOList[1].actuatorTypeID").value(secondActuatorTypeID))
                .andExpect(jsonPath("$._embedded.actuatorDTOList[1].deviceID").value(secondActuatorDeviceID))
                .andExpect(jsonPath("$._embedded.actuatorDTOList[1]._links.self").exists())
                .andExpect(jsonPath("$._links.self").exists())
                .andReturn();
    }

    /**
     * This test method tests the getActuatorsByDeviceID endpoint in the ActuatorsCTRLWeb class. It tests the endpoint by
     * providing a valid device ID that does not have any actuators associated with it. The test mocks the repository layer
     * to return an empty list of actuators when the findByDeviceID method is called with the provided device ID. The test
     * expects the response to have an HTTP status of 200 (OK) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenDeviceIDWithNoActuators_whenGetActuatorsByDeviceID_thenReturnEmptyActuatorDTOCollection() throws Exception {
        //Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        Iterable<Actuator> actuators = Collections.emptyList();

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.findByDeviceID(deviceIDVO)).thenReturn(actuators);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * This test method tests the getActuatorsByDeviceID endpoint in the ActuatorsCTRLWeb class. It tests the endpoint by
     * providing a non-existent device ID and checking if the response contains an HTTP status of 400 (Bad Request). The
     * test mocks the repository layer to return false when the isPresent method is called with the provided device ID.
     * The test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNonExistentDeviceID_whenGetActuatorsByDeviceID_thenReturnBadRequest() throws Exception {
        //Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the getActuatorsByDeviceID endpoint in the ActuatorsCTRLWeb class. It tests the endpoint by
     * providing a device ID with null parameters and checking if the response contains an HTTP status of 400 (Bad Request).
     * The test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNullDeviceID_whenGetActuatorsByDeviceID_thenReturnBadRequest() throws Exception {
        //Arrange
        String deviceID = null;

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Test method to verify closing the roller blind actuator functionality.
     * This method tests the functionality of closing a roller blind actuator by sending a PATCH request
     * to the "/actuators/{actuatorId}" endpoint. It initializes the actuatorIDVO and sets up the necessary
     * double behavior for the actuator repository to simulate a successful scenario where the actuator is present,
     * found, and successfully closed.
     * The test then performs the PATCH request using MockMvc and verifies that the response
     * status is 200 (OK).
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void givenValidActuatorId_whenCloseRollerBlind_thenReturnOk() throws Exception {
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        RollerBlindActuator actuator = new RollerBlindActuator(new ActuatorNameVO("Actuator"), new ActuatorTypeIDVO("RollerBlindActuator"), new DeviceIDVO(UUID.randomUUID()));

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        mockMvc.perform(MockMvcRequestBuilders.post("/actuators/" + actuatorId + "/closerollerblind")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test method to verify closing the roller blind actuator functionality.
     * This method tests the functionality of closing a roller blind actuator by sending a PATCH request
     * to the "/actuators/{actuatorId}" endpoint. It initializes the actuatorIDVO and sets up the necessary
     * double behavior for the actuator repository to simulate a failure scenario where the actuator is present,
     * found, but its type is not a RollerBlindActuator and therefore cannot be closed.
     * The test then performs the PATCH request using MockMvc and verifies that the response
     * status is 422 (UNPROCESSABLE ENTITY).
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */
    @Test
    void givenValidActuatorIdButInvalidActuatorType_whenCloseRollerBlind_thenReturnUnprocessableEntity() throws Exception {
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        SwitchActuator actuator = new SwitchActuator(new ActuatorNameVO("Actuator"), new ActuatorTypeIDVO("SwitchActuator"), new DeviceIDVO(UUID.randomUUID()));

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        mockMvc.perform(MockMvcRequestBuilders.post("/actuators/" + actuatorId + "/closerollerblind")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    /**
     * Test method to verify closing the roller blind actuator functionality.
     * This method tests the functionality of closing a roller blind actuator by sending a PATCH request
     * to the "/actuators/{actuatorId}" endpoint. The string actuatorId is set to an empty string and so the
     * test verifies that the response status is 400 (BAD REQUEST).
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */
    @Test
    void givenInvalidActuatorId_whenCloseRollerBlind_thenReturnBadRequest() throws Exception {
        String actuatorId = " ";

        mockMvc.perform(MockMvcRequestBuilders.post("/actuators/" + actuatorId + "/closerollerblind")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test method to verify closing the roller blind actuator functionality.
     * This method tests the functionality of closing a roller blind actuator by sending a PATCH request
     * to the "/actuators/{actuatorId}" endpoint. The string actuatorId is valid but belongs to a non-existent
     * actuator and so the test verifies that the response status is 400 (BAD REQUEST).     *
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */
    @Test
    void givenNonExistentActuatorId_whenCloseRollerBlind_thenReturnBadRequest() throws Exception {
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/actuators/" + actuatorId + "/closerollerblind")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that when a non-existent Actuator ID is provided, the execution of the command returns a Bad
     * Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenNonExistentActuatorId_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        String command = "123";

        // Mock behavior for repository method indicating non-existence of the actuator
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(false);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that when a blank command is given, the execution of the command returns a Bad Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenBlankCommand_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        SwitchActuator actuator = new SwitchActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO, deviceIDVO,
                actuatorStatusVO);

        String command = " ";

        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that when a null command is given, the execution of the command returns a Bad Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenNullCommand_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        SwitchActuator actuator = new SwitchActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO, deviceIDVO,
                actuatorStatusVO);

        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command" + null)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    /**
     * Test case to verify that when an invalid command is given for a Switch Actuator type, the execution of the
     * command returns a Bad Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenInvalidCommandForASwitchActuatorType_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        SwitchActuator actuator = new SwitchActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO, deviceIDVO,
                actuatorStatusVO);

        String command = "2";

        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    /**
     * Test case to verify that when an invalid command is given for a Roller Blind Actuator type, the execution of the
     * command returns a Bad Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenInvalidCommandForARollerBlindActuatorType_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        RollerBlindActuator actuator = new RollerBlindActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO,
                deviceIDVO, actuatorStatusVO);
        String command = "-1";

        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    /**
     * Test case to verify that when an invalid command is given for an Actuator with an integer value type, the
     * execution of the command returns a Bad Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenInvalidCommandForAnIntegerValueActuatorType_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        IntegerSettingsVO integerSettingsVO = new IntegerSettingsVO("1", "2");
        IntegerValueActuator actuator = new IntegerValueActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO,
                deviceIDVO, integerSettingsVO, actuatorStatusVO);
        String command = "I am a String";

        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    /**
     * Test case to verify that when an invalid command is given for an Actuator with a decimal value type, the
     * execution of the command returns a Bad Request status.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenInvalidCommandForADecimalValueActuatorType_thenExecuteCommandReturnsBadRequest() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        DecimalSettingsVO decimalSettingsVO = new DecimalSettingsVO("1.1", "2.0", "0.5");
        DecimalValueActuator actuator = new DecimalValueActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO,
                deviceIDVO, decimalSettingsVO, actuatorStatusVO);
        String command = "I am a String";

        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that when a valid command is given, the execution of the command on the Actuator returns an
     * updated ActuatorDTO.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenValidCommand_thenExecuteCommandReturnsUpdatedActuatorDTO() throws Exception {
        // Prepare test data
        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO("Actuator1");
        ActuatorStatusVO actuatorStatusVO = new ActuatorStatusVO("default");
        SwitchActuator actuator = new SwitchActuator(actuatorIDVO, actuatorNameVO, actuatorTypeIDVO,
                deviceIDVO, actuatorStatusVO);

        String command = "1";



        // Mock behavior for repository methods
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);
        when(actuatorRepository.save(actuator)).thenReturn(true);

        // Expected links
        Link expectedSelfLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorById(actuatorId)).withSelfRel();
        Link expectedRelLink = linkTo(methodOn(ActuatorCTRLWeb.class).executeCommand(actuatorId, "{command}"))
                .withRel("ExecuteCommand");

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.patch("/actuators/" + actuatorId + "/act?command=" + command)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.actuatorId").value(actuatorId))
                .andExpect(jsonPath("$.actuatorName").value(actuatorNameVO.getValue()))
                .andExpect(jsonPath("$.actuatorTypeID").value(actuatorTypeIDVO.getID()))
                .andExpect(jsonPath("$.deviceID").value(deviceIDVO.getID()))
                .andExpect(jsonPath("$.status").value(command))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedSelfLink.getHref()))
                .andExpect(jsonPath("$._links.ExecuteCommand.href").value(expectedRelLink.getHref()))
                .andReturn();
    }
}
