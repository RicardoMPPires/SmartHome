package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.domain.device.Device;
import smarthome.domain.sensor.HumiditySensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.TemperatureSensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.mapper.dto.SensorDTO;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.SensorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SensorCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private SensorRepository sensorRepository;

    /**
     * This test verifies that when a SensorCTRLWeb is constructed with a null SensorService,
     * an IllegalArgumentException is thrown with the expected message.
     */
    @Test
    void givenNullSensorService_whenConstructingSensorCTRLWeb_thenThrowIllegalArgumentException() {
        //Arrange
        String expected = "Invalid service";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new SensorCTRLWeb(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);

    }

    /**
     * This test verifies that when a GET request is made with a valid sensor ID,
     * the response contains the expected sensor data and a status of OK (200).
     * The test sets up a sensor with a valid ID, name, device ID, and sensor type ID.
     * It then expects that the sensor repository will return this sensor when queried with the sensor ID.
     * The test performs a GET request with the sensor ID and asserts that the response status is OK and the body contains the expected sensor data.
     */
    @Test
    void givenValidSensorID_whenGetSensorByID_thenReturnSensor() throws Exception {
        //Arrange
        String sensorID = "1fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorName = "Sensor1";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";

        SensorIDVO sensorIDVO = new SensorIDVO(UUID.fromString(sensorID));
        SensorNameVO sensorNameVO = new SensorNameVO(sensorName);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);

        Sensor sensor = new TemperatureSensor(sensorIDVO, sensorNameVO, deviceIDVO, sensorTypeIDVO);

        Link expectedLink = linkTo(methodOn(SensorCTRLWeb.class).getSensorByID(sensorID)).withSelfRel();

        when(sensorRepository.isPresent(sensorIDVO)).thenReturn(true);
        when(sensorRepository.findById(sensorIDVO)).thenReturn(sensor);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors/" + sensorID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.sensorID").value(sensorID))
                .andExpect(jsonPath("$.sensorName").value(sensorName))
                .andExpect(jsonPath("$.deviceID").value(deviceID))
                .andExpect(jsonPath("$.sensorTypeID").value(sensorTypeID))
                .andExpect(jsonPath("$._links.self.href").value(expectedLink.getHref()))
                .andReturn();
    }

    /**
     * This test verifies that when a GET request is made with a sensor ID that does not exist in the sensor repository,
     * the response status is NOT FOUND (404).
     * The test sets up a  sensor with a valid ID, name, device ID, and sensor type ID.
     * It then expects that the sensor repository will return false when queried with the sensor ID.
     * The test performs a GET request with the sensor ID and asserts that the response status is NOT FOUND.
     */
    @Test
    void givenNotSavedSensorID_whenGetSensorByID_thenReturnNotFoundCode() throws Exception {
        //Arrange
        String sensorID = "1fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorName = "Sensor1";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";

        SensorIDVO sensorIDVO = new SensorIDVO(UUID.fromString(sensorID));
        SensorNameVO sensorNameVO = new SensorNameVO(sensorName);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);

        Sensor sensor = new TemperatureSensor(sensorIDVO, sensorNameVO, deviceIDVO, sensorTypeIDVO);

        when(sensorRepository.isPresent(sensorIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors/" + sensorID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    /**
     * This test verifies that when a GET request is made with an invalid sensor ID (null in this case),
     * the response status is BAD REQUEST (400).
     * The test performs a GET request with a null sensor ID and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenInvalidSensorId_whenGetSensorByID_thenReturnBadRequestCode() throws Exception {
        //Arrange
        SensorIDVO sensorIDVO = null;

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors/" + sensorIDVO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with a valid sensor DTO and the sensor is successfully saved,
     * the response contains the expected sensor data and a status of CREATED (201).
     * The test sets up a sensor DTO with a valid name, device ID, and sensor type ID.
     * It then expects that the sensor repository will return true when the sensor is saved.
     * The test performs a POST request with the sensor DTO and asserts that the response status is CREATED and the body contains the expected sensor data.
     */
    @Test
    void givenValidSensorDTO_whenAddSensorToDevice_thenReturnCreatedCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "1fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";

        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));


        Device device = mock(Device.class);
        when(device.isActive()).thenReturn(true);
        when(device.getId()).thenReturn(deviceIDVO);

        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);

        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(true);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorName").value(sensorName))
                .andExpect(jsonPath("$.deviceID").value(deviceID))
                .andExpect(jsonPath("$.sensorTypeID").value(sensorTypeID))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with a valid sensor DTO but the device does not exist in the device repository,
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a valid name, device ID, and sensor type ID.
     * It then expects that the device repository will return false when queried with the device ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenNotSavedDevice_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";

        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (null sensor type ID in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a valid name and device ID, but a null sensor type ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenInvalidSensorDTO_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(null)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (null sensor name in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a null sensor name, but valid device ID and sensor type ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenNullSensorName_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(null)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (null device ID in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a valid sensor name and sensor type ID, but a null device ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenNullDeviceId_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String sensorTypeID = "TemperatureSensor";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(null)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (empty sensor name in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with an empty sensor name, but valid device ID and sensor type ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenInvalidSensorName_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = " ";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (empty device ID in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a valid sensor name and sensor type ID, but an empty device ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenInvalidDeviceId_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "zzz";
        String sensorTypeID = "TemperatureSensor";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (empty sensor type ID in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a valid sensor name and device ID, but an empty sensor type ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenInvalidSensorType_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensorz";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (empty sensor type ID in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with a valid sensor name and device ID, but an empty sensor type ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenEmptySensorType_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "2fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = " ";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with an invalid sensor DTO (empty sensor name in this case),
     * the response status is BAD REQUEST (400).
     * The test sets up a sensor DTO with an empty sensor name, but valid device ID and sensor type ID.
     * The test performs a POST request with the sensor DTO and asserts that the response status is BAD REQUEST.
     */
    @Test
    void givenEmptySensorId_whenAddSensorToDevice_thenReturnBadRequestCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = " ";
        String sensorTypeID = "TemperatureSensor";
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);
        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }


    /**
     * This test verifies that when a POST request is made with a valid sensor DTO but the sensor fails to save,
     * the response status is UNPROCESSABLE ENTITY (422).
     * The test sets up a sensor DTO with a valid name, device ID, and sensor type ID.
     * It then expects that the sensor repository will return false when the sensor is saved.
     * The test performs a POST request with the sensor DTO and asserts that the response status is UNPROCESSABLE ENTITY.
     */
    @Test
    void givenValidSensorDTO_whenSavingFails_thenReturnUnprocessableEntityCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "1fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";

        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);

        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test verifies that when a POST request is made with a valid sensor DTO but the sensor already exists,
     * the response status is UNPROCESSABLE ENTITY (422).
     * The test sets up a sensor DTO with a valid name, device ID, and sensor type ID.
     * It then expects that the sensor repository will return false when the sensor is saved.
     * The test performs a POST request with the sensor DTO and asserts that the response status is UNPROCESSABLE ENTITY.
     */
    @Test
    void givenAlreadyExistentSensor_whenAddSensorToDevice_thenReturnConflictCode() throws Exception {
        //Arrange
        String sensorName = "Sensor1";
        String deviceID = "1fa85f64-5717-4562-b3fc-2c963f66afa6";
        String sensorTypeID = "TemperatureSensor";

        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String deviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(deviceRoomID));

        Device device = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);

        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        String sensorAsJSON = objectMapper.writeValueAsString(sensorDTO);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(sensorAsJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    void givenNullParam_getSensorsByDeviceIDReturnsBadRequest() throws Exception {
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

    @Test
    void givenInvalidDeviceId_getSensorsByDeviceIDReturnsBadRequest() throws Exception {
        //Arrange
        String deviceID1 = " ";
        String deviceID2 = "asfw";
        String deviceID3 = "1234-5242";

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/actuators")
                        .param("deviceId", deviceID3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    void givenDeviceIDWithNoSensors_whenGetSensorsByDeviceID_thenReturnEmptySensorDTOCollection() throws Exception {
        //Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        Iterable<Sensor> sensors = Collections.emptyList();

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(sensorRepository.findByDeviceID(deviceIDVO)).thenReturn(sensors);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void givenNonExistentDeviceID_whenGetSensorsByDeviceID_thenReturnBadRequest() throws Exception {
        //Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    @Test
    void givenDeviceID_whenGetSensorsByDeviceID_thenReturnASensorDTOCollection() throws Exception{
        //Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        String firstSensorName = "Sensor 1";
        String firstSensorTypeID = "HumiditySensor";

        SensorNameVO firstSensorNameVO = new SensorNameVO(firstSensorName);
        SensorTypeIDVO firstSensorTypeIDVO = new SensorTypeIDVO(firstSensorTypeID);

        Sensor firstSensor = new HumiditySensor(firstSensorNameVO,deviceIDVO,firstSensorTypeIDVO);

        String secondSensorName = "Sensor 2";
        String secondSensorTypeID = "TemperatureSensor";

        SensorNameVO secondSensorNameVO = new SensorNameVO(secondSensorName);
        SensorTypeIDVO secondSensorTypeIDVO = new SensorTypeIDVO(secondSensorTypeID);

        Sensor secondSensor = new TemperatureSensor(secondSensorNameVO,deviceIDVO,secondSensorTypeIDVO);

        List<Sensor> sensors = new ArrayList<>();
        sensors.add(firstSensor);
        sensors.add(secondSensor);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(sensorRepository.findByDeviceID(deviceIDVO)).thenReturn(sensors);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors")
                        .param("deviceId", deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.sensorDTOList").exists())
                .andExpect(jsonPath("$._embedded.sensorDTOList[0].sensorName").value(firstSensorName))
                .andExpect(jsonPath("$._embedded.sensorDTOList[0].sensorTypeID").value(firstSensorTypeID))
                .andExpect(jsonPath("$._embedded.sensorDTOList[0].deviceID").value(deviceID))
                .andExpect(jsonPath("$._embedded.sensorDTOList[0]._links.self").exists())
                .andExpect(jsonPath("$._embedded.sensorDTOList[1].sensorName").value(secondSensorName))
                .andExpect(jsonPath("$._embedded.sensorDTOList[1].sensorTypeID").value(secondSensorTypeID))
                .andExpect(jsonPath("$._embedded.sensorDTOList[1].deviceID").value(deviceID))
                .andExpect(jsonPath("$._embedded.sensorDTOList[1]._links.self").exists())
                .andExpect(jsonPath("$._links.self").exists())
                .andReturn();
    }
}