package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.RollerBlindActuator;
import smarthome.domain.device.Device;
import smarthome.domain.sensor.PositionSensor;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The DeviceCTRLWebTest class is a test class that tests the DeviceCTRLWeb class.
 * It tests the endpoints in the DeviceCTRLWeb class by mocking the repository layer.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class DeviceCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private SensorRepository sensorRepository;

    @MockBean
    private ActuatorRepository actuatorRepository;

    /**
     * This test method tests the getDeviceById endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * a valid device ID and checking if the response contains the correct device information. The test mocks the
     * repository layer to return a device object when the getDeviceById method is called with the provided device ID.
     * The test expects the response to have an HTTP status of 200 (OK) and the correct device information in the
     * response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenDeviceID_whenGetDeviceById_thenReturnDeviceDTO() throws Exception {
//        Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        boolean deviceStatus = true;
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f664521";

        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO(deviceStatus);
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        Device device = new Device(deviceIDVO, deviceNameVO, deviceModelVO, deviceStatusVO, roomIDVO);

        Link expectedSelfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(deviceID)).withSelfRel();
        Link expectedDeactivateLink = linkTo(methodOn(DeviceCTRLWeb.class).deactivateDevice(deviceID)).withRel("deactivateDevice");
        Link expectedAddSensorLink = linkTo(methodOn(SensorCTRLWeb.class).addSensorToDevice(null)).withRel("addSensor");
        Link expectedSensorTypeLink = linkTo(methodOn(SensorTypeCTRLWeb.class).getSensorTypes()).withRel("getSensorType");
        Link expectedAddActuatorLink = linkTo(methodOn(ActuatorCTRLWeb.class).addActuatorToDevice(null)).withRel("addActuator");
        Link expectedListOfActuatorsLink = linkTo(methodOn(ActuatorCTRLWeb.class).getActuatorsByDeviceID(deviceID)).withRel("getActuatorsByDeviceId");
        Link expectedActuatorTypeLink = linkTo(methodOn(ActuatorTypeCTRLWeb.class).getActuatorTypes()).withRel("getActuatorType");
        Link expectedFindReadingsLink = linkTo(methodOn(LogCTRLWeb.class).findReadings(deviceID, null)).withRel("findReadingsInAPeriod");

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceID").value(deviceID))
                .andExpect(jsonPath("$.deviceName").value(deviceName))
                .andExpect(jsonPath("$.deviceModel").value(deviceModel))
                .andExpect(jsonPath("$.deviceStatus").value("true"))
                .andExpect(jsonPath("$.roomID").value(roomID))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedSelfLink.getHref()))
                .andExpect(jsonPath("$._links.deactivateDevice.href").value(expectedDeactivateLink.getHref()))
                .andExpect(jsonPath("$._links.addSensor.href").value(expectedAddSensorLink.getHref()))
                .andExpect(jsonPath("$._links.getSensorType.href").value(expectedSensorTypeLink.getHref()))
                .andExpect(jsonPath("$._links.addActuator.href").value(expectedAddActuatorLink.getHref()))
                .andExpect(jsonPath("$._links.getActuatorsByDeviceId.href").value(expectedListOfActuatorsLink.getHref()))
                .andExpect(jsonPath("$._links.getActuatorType.href").value(expectedActuatorTypeLink.getHref()))
                .andExpect(jsonPath("$._links.findReadingsInAPeriod.href").value(expectedFindReadingsLink.getHref()))
                .andReturn();
    }

    /**
     * This test method tests the getDeviceById endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * a non-existent device ID and checking if the response contains an HTTP status of 404 (Not Found). The test mocks
     * the repository layer to return false when the isPresent method is called with the provided device ID. The test
     * expects the response to have an HTTP status of 404 (Not Found) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNonExistentDeviceID_whenGetDeviceById_thenReturnNotFound() throws Exception {
        //        Arrange
        String deviceID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices/" + deviceID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the getDeviceById endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * an invalid device ID and checking if the response contains an HTTP status of 400 (Bad Request). The test expects
     * the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenInvalidDeviceID_whenGetDeviceById_thenReturnBadRequest() throws Exception {
//        Arrange
        DeviceIDVO deviceIDVO = null;
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices/" + deviceIDVO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the createDevice endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * a valid device DTO and checking if the response contains the correct device information. The test mocks the
     * repository layer to return true when the save method is called with the provided device object. The test expects
     * the response to have an HTTP status of 201 (Created) and the correct device information in the response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenDeviceDTO_whenCreateDevice_thenReturnDeviceDTO() throws Exception {
//        Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceName(deviceName)
                .deviceModel(deviceModel)
                .roomID(roomID)
                .build();

        String deviceJSON = objectMapper.writeValueAsString(deviceDTO);

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.save(any(Device.class))).thenReturn(true);

//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(deviceJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.deviceName").value(deviceName))
                .andExpect(jsonPath("$.deviceModel").value(deviceModel))
                .andExpect(jsonPath("$.roomID").value(roomID))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test method tests the createDevice endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * a non-existent room ID and checking if the response contains an HTTP status of 404 (Not Found). The test mocks
     * the repository layer to return false when the isPresent method is called with the provided room ID. The test
     * expects the response to have an HTTP status of 404 (Not Found) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNonExistentRoom_whenAddDeviceToRoom_thenReturnNotFound() throws Exception {
//        Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceName(deviceName)
                .deviceModel(deviceModel)
                .roomID(roomID)
                .build();

        String deviceJSON = objectMapper.writeValueAsString(deviceDTO);

        when(roomRepository.isPresent(roomIDVO)).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(deviceJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the createDevice endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * an existing device and checking if the response contains an HTTP status of 422 (Unprocessable Entity). The test
     * mocks the repository layer to return false when the save method is called with the provided device object. The
     * test expects the response to have an HTTP status of 422 (Unprocessable Entity) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenExistingDevice_whenAddDeviceToRoom_thenReturnConflict() throws Exception {
//        Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceName(deviceName)
                .deviceModel(deviceModel)
                .roomID(roomID)
                .build();

        String deviceJSON = objectMapper.writeValueAsString(deviceDTO);

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.save(any(Device.class))).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(deviceJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the createDevice endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * a device DTO with null parameters and checking if the response contains an HTTP status of 400 (Bad Request). The
     * test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenDeviceDTOWithNullParameters_whenCreateDevice_thenReturnBadRequest() throws Exception {
//        Arrange
        String deviceName = null;
        String deviceModel = "Device Model";
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceName(deviceName)
                .deviceModel(deviceModel)
                .roomID(roomID)
                .build();

        String deviceJSON = objectMapper.writeValueAsString(deviceDTO);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(deviceJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the createDevice endpoint in the DeviceCTRLWeb class. It tests the endpoint by providing
     * a device DTO with valid parameters but the save method in the repository layer fails to save the device object.
     * The test expects the response to have an HTTP status of 422 (Unprocessable Entity) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenDeviceDTO_whenCreateDeviceFailsToSaveToRepository_thenReturnNull() throws Exception {
//        Arrange
        String deviceName = "Device Name";
        String deviceModel = "Device Model";
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceName(deviceName)
                .deviceModel(deviceModel)
                .roomID(roomID)
                .build();

        String deviceJSON = objectMapper.writeValueAsString(deviceDTO);

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.save(any(Device.class))).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(deviceJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the getDevicesByRoomId endpoint in the DeviceCTRLWeb class. It tests the endpoint by
     * providing a valid room ID and checking if the response contains the correct device information. The test mocks
     * the repository layer to return a list of devices when the findByRoomID method is called with the provided room ID.
     * The test expects the response to have an HTTP status of 200 (OK) and the correct device information in the
     * response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenRoomID_whenGetDevicesByRoomId_thenReturnDeviceDTOCollection() throws Exception {
//         Arrange
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        String firstDeviceName = "Device Name";
        String firstDeviceModel = "Device Model";
        String firstDeviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO deviceNameVO = new DeviceNameVO(firstDeviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(firstDeviceModel);
        RoomIDVO deviceRoomIDVO = new RoomIDVO(UUID.fromString(firstDeviceRoomID));

        Device firstDevice = new Device(deviceNameVO, deviceModelVO, deviceRoomIDVO);

        String secondDeviceName = "Device Name 2";
        String secondDeviceModel = "Device Model 2";
        String secondDeviceRoomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        DeviceNameVO secondDeviceNameVO = new DeviceNameVO(secondDeviceName);
        DeviceModelVO secondDeviceModelVO = new DeviceModelVO(secondDeviceModel);
        RoomIDVO secondDeviceRoomIDVO = new RoomIDVO(UUID.fromString(secondDeviceRoomID));

        Device secondDevice = new Device(secondDeviceNameVO, secondDeviceModelVO, secondDeviceRoomIDVO);

        List<Device> deviceList = new ArrayList<>();
        deviceList.add(firstDevice);
        deviceList.add(secondDevice);

        Link expectedFirstDeviceSelfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(firstDevice.getId().getID())).withSelfRel();
        Link expectedSecondDeviceSelfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDeviceById(secondDevice.getId().getID())).withSelfRel();
        Link expectedSelfLink = linkTo(methodOn(DeviceCTRLWeb.class).getDevicesByRoomId(roomID)).withSelfRel();
        Link expectedAddDeviceLink = linkTo(methodOn(DeviceCTRLWeb.class).addDeviceToRoom(null)).withRel("addDevice");


        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.findByRoomID(roomIDVO)).thenReturn(deviceList);
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices")
                        .param("roomID", roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.deviceDTOList").exists())
                .andExpect(jsonPath("$._embedded.deviceDTOList[0].deviceName").value(firstDeviceName))
                .andExpect(jsonPath("$._embedded.deviceDTOList[0].deviceModel").value(firstDeviceModel))
                .andExpect(jsonPath("$._embedded.deviceDTOList[0].roomID").value(firstDeviceRoomID))
                .andExpect(jsonPath("$._embedded.deviceDTOList[0]._links.self.href").value(expectedFirstDeviceSelfLink.getHref()))
                .andExpect(jsonPath("$._embedded.deviceDTOList[1].deviceName").value(secondDeviceName))
                .andExpect(jsonPath("$._embedded.deviceDTOList[1].deviceModel").value(secondDeviceModel))
                .andExpect(jsonPath("$._embedded.deviceDTOList[1].roomID").value(secondDeviceRoomID))
                .andExpect(jsonPath("$._embedded.deviceDTOList[1]._links.self.href").value(expectedSecondDeviceSelfLink.getHref()))
                .andExpect(jsonPath("$._links.self.href").value(expectedSelfLink.getHref()))
                .andExpect(jsonPath("$._links.addDevice.href").value(expectedAddDeviceLink.getHref()))
                .andReturn();
    }

    /**
     * This test method tests the getDevicesByRoomId endpoint in the DeviceCTRLWeb class. It tests the endpoint by
     * providing a valid room ID that does not have any devices associated with it. The test mocks the repository layer
     * to return an empty list of devices when the findByRoomID method is called with the provided room ID. The test
     * expects the response to have an HTTP status of 200 (OK) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenValidRoomIDWithoutDevices_whenGetDevicesByRoomId_thenReturnEmptyList() throws Exception {
//         Arrange
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        Iterable<Device> deviceList = Collections.emptyList();

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.findByRoomID(roomIDVO)).thenReturn(deviceList);
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices")
                        .param("roomID", roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * This test method tests the getDevicesByRoomId endpoint in the DeviceCTRLWeb class. It tests the endpoint by
     * providing a non-existent room ID and checking if the response contains an HTTP status of 400 (Bad Request). The
     * test mocks the repository layer to return false when the isPresent method is called with the provided room ID.
     * The test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNonExistentRoomID_whenGetDevicesByRoomId_thenReturnNotFound() throws Exception {
//         Arrange
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        when(roomRepository.isPresent(roomIDVO)).thenReturn(false);
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices")
                        .param("roomID", roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the getDevicesByRoomId endpoint in the DeviceCTRLWeb class. It tests the endpoint by
     * providing a room ID with null parameters and checking if the response contains an HTTP status of 400 (Bad Request).
     * The test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNullRoomID_whenGetDevicesByRoomId_thenReturnNotFound() throws Exception {
//         Arrange
        String roomIDVO = null;
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices")
                        .param("roomID", roomIDVO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * Test method to verify device deactivation functionality.
     * <p>
     * This method tests the functionality of deactivating a device by sending a PATCH request
     * to the "/devices/{id}" endpoint. It initializes necessary value objects to create a device,
     * creates a device object, and sets up the necessary double behavior for the device repository
     * to simulate a successful scenario where the device is present, found, and successfully updated.
     * <p>
     * The test then performs the PATCH request using MockMvc and verifies that the response
     * status is 201 (Created), the content type is HAL JSON, and the returned device details match
     * the expected values. It also verifies the presence of a self-link in the response.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void deactivateDevice_ShouldReturnOkStatusCodeAndReturnDeviceDetails() throws Exception {
        //Arrange
        //Initializing necessary value objects to create a device
        String deviceNameString = "DeviceName";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceNameString);

        String deviceModel = "DeviceModel";
        DeviceModelVO modelVO = new DeviceModelVO(deviceModel);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());
        String roomIdString = roomIDVO.getID();

        //Creating a device
        Device device = new Device(deviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO deviceID = device.getId();
        String deviceIdString = deviceID.getID();

        String status = "false";

        //Conditioning device repository to match the output with a successful scenario
        when(deviceRepository.isPresent(deviceID)).thenReturn(true);
        when(deviceRepository.findById(deviceID)).thenReturn(device);
        when(deviceRepository.update(device)).thenReturn(true);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + deviceIdString).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceID").value(deviceIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceStatus").value(status))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceName").value(deviceNameString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceModel").value(deviceModel))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomID").value(roomIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href").value("http://localhost/devices/" + deviceIdString));
    }

    /**
     * Test method to verify behavior when repository fails to update device status.
     * <p>
     * This method tests the behavior of the endpoint when the device repository fails
     * to update the device status during deactivation. It initializes necessary value
     * objects to create a device, creates a device object, and sets up the necessary
     * stubbing for the device repository to simulate a scenario where the device is
     * present, found, but the repository fails to update the device status.
     * <p>
     * The test then performs the PATCH request using MockMvc and verifies that the response
     * status is 422 (Unprocessable Entity), and the content is empty. This indicates that
     * the endpoint failed to update the device status and returned the appropriate status
     * code along with null content.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void deactivateDevice_WhenRepositoryDoesNotUpdate_ShouldReturnUnprocessableEntityStatusCodeAndNullContent() throws Exception {
        //Arrange
        //Initializing necessary value objects to create a device
        String deviceNameString = "DeviceName";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceNameString);

        String deviceModel = "DeviceModel";
        DeviceModelVO modelVO = new DeviceModelVO(deviceModel);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());

        //Creating a device
        Device device = new Device(deviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO deviceID = device.getId();
        String deviceIdString = deviceID.getID();

        //Conditioning device repository to match the output with a successful scenario
        when(deviceRepository.isPresent(deviceID)).thenReturn(true);
        when(deviceRepository.findById(deviceID)).thenReturn(device);
        when(deviceRepository.update(device)).thenReturn(false);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + deviceIdString).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Test method to verify behavior when attempting to deactivate an already inactive device.
     * <p>
     * This method tests the behavior of the endpoint when attempting to deactivate a device
     * that is already inactive. It initializes necessary value objects to create a device,
     * creates a device object, deactivates the device, and sets up the necessary stubbing
     * for the device repository to simulate a scenario where the device is present but
     * already inactive.
     * <p>
     * The test then performs the PATCH request using MockMvc and verifies that the response
     * status is 422 (Unprocessable Entity), and the content is empty. This indicates that
     * the endpoint failed to deactivate the device and returned the appropriate status
     * code along with null content. Additionally, it asserts that the device status remains
     * inactive after the request.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void deactivateDevice_WhenDeviceIsInactive_ShouldReturnUnprocessableEntityStatusCodeAndNullContent() throws Exception {
        //Arrange
        //Initializing necessary value objects to create a device
        String deviceNameString = "DeviceName";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceNameString);

        String deviceModel = "DeviceModel";
        DeviceModelVO modelVO = new DeviceModelVO(deviceModel);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());

        //Creating a device
        Device device = new Device(deviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO deviceID = device.getId();
        String deviceIdString = deviceID.getID();

        //Device deactivation
        device.deactivateDevice();

        //Conditioning device repository to match the output with a successful scenario
        when(deviceRepository.isPresent(deviceID)).thenReturn(true);
        when(deviceRepository.findById(deviceID)).thenReturn(device);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + deviceIdString).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string(""));

        //Assert device status
        assertFalse(device.isActive());
    }

    /**
     * Test method to verify behavior when the provided device ID cannot be parsed to UUID.
     * <p>
     * This method tests the behavior of the endpoint when the provided device ID in the URL
     * cannot be parsed to a UUID. It initializes a non-parsable device ID, and then performs
     * the PATCH request using MockMvc.
     * <p>
     * The test verifies that the response status is 422 (Unprocessable Entity) and the content
     * is empty. This indicates that the endpoint failed to process the request due to an invalid
     * device ID format and returned the appropriate status code along with null content.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void deactivateDevice_WhenDeviceIdIsNotParsableToUUID_ShouldReturnUnprocessableEntityStatusCodeAndNullContent() throws Exception {
        //Arrange
        String id = "123456789";

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + id)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string(""));

    }

    /**
     * Test method to verify behavior when attempting to deactivate a non-existent device.
     * <p>
     * This method tests the behavior of the endpoint when attempting to deactivate a device
     * that does not exist in the system. It initializes a non-existent device ID, and then
     * conditions the device repository to return false when queried if a given device ID matches
     * an existing device.
     * <p>
     * The test then performs the PATCH request using MockMvc and verifies that the response
     * status is 404 (Not Found) and the content is empty. This indicates that the endpoint
     * failed to find the device to deactivate and returned the appropriate status code along
     * with null content.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void deactivateDevice_WhenDeviceDoesNotExist_ShouldReturnNotFoundStatusCode() throws Exception {
        //Arrange

        //Extracting the generated device id
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        String deviceIdString = deviceID.getID();

        //Conditioning device repository to return false when queried if a given device id match an existing device
        when(deviceRepository.isPresent(deviceID)).thenReturn(false);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/devices/" + deviceIdString).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Test method to verify the behavior of retrieving devices grouped by functionality.
     * <p>
     * This method tests the behavior of the endpoint responsible for retrieving devices
     * grouped by functionality. It initializes necessary value objects to create devices,
     * sensors, and actuators. The devices are associated with sensors and actuators,
     * simulating a scenario where devices have functionality components.
     * <p>
     * The test then performs the GET request using MockMvc and verifies that the response
     * status is 200 (OK), and the content type is HAL JSON. Additionally, it verifies that
     * the response body contains a correct list of devices grouped by functionality,
     * with each device represented along with its associated functionality components.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void getDevicesByFunctionality_ShouldReturnOkayStatusCodeAndCorrectListOfDevicesGroupedByFunctionality() throws Exception {
        //Arrange

        //Initializing necessary value objects to create a device
        String deviceNameString = "DeviceName";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceNameString);

        String deviceModel = "DeviceModel";
        DeviceModelVO modelVO = new DeviceModelVO(deviceModel);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());
        String roomIdString = roomIDVO.getID();

        //Creating a device
        Device device = new Device(deviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO deviceID = device.getId();
        String deviceIdString = deviceID.getID();

        //Initializing necessary value objects to create a second device
        String secondDeviceNameString = "Second Device Name";
        DeviceNameVO secondDeviceNameVO = new DeviceNameVO(secondDeviceNameString);

        //Creating a device
        Device secondDevice = new Device(secondDeviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO secondDeviceID = secondDevice.getId();
        String secondDeviceIdString = secondDeviceID.getID();

        //Conditioning device repository to return the correct device associated with a deviceID
        when(deviceRepository.findById(deviceID)).thenReturn(device);
        when(deviceRepository.findById(secondDeviceID)).thenReturn(secondDevice);

        //Initializing a sensor
        String sensorNameString = "Position Sensor Name";
        SensorNameVO sensorNameVO = new SensorNameVO(sensorNameString);

        String positionTypeString = "PositionSensor";
        SensorTypeIDVO positionTypeIDVO = new SensorTypeIDVO(positionTypeString);

        Sensor positionSensor = new PositionSensor(sensorNameVO, deviceID, positionTypeIDVO);

        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(positionSensor);

        //Conditioning sensor repository double to return the previous created sensor
        when(sensorRepository.findAll()).thenReturn(sensorList);

        //Initializing an actuator
        String actuatorNameString = "Roller Blind Actuator Name";
        ActuatorNameVO actuatorNameVO = new ActuatorNameVO(actuatorNameString);

        String actuatorTypeString = "RollerBlindActuator";
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeString);

        Actuator rollerBlindActuator = new RollerBlindActuator(actuatorNameVO, actuatorTypeIDVO, secondDeviceID);

        List<Actuator> actuatorList = new ArrayList<>();
        actuatorList.add(rollerBlindActuator);

        //Conditioning sensor repository double to return the previous created sensor
        when(actuatorRepository.findAll()).thenReturn(actuatorList);

        String status = "true";

        //Act + //Assert

        mockMvc.perform(MockMvcRequestBuilders.get("/devices/byfunctionality").accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[0].PositionSensor[0].deviceName").value(deviceNameString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[0].PositionSensor[0].deviceID").value(deviceIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[0].PositionSensor[0].deviceModel").value(deviceModel))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[0].PositionSensor[0].roomID").value(roomIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[0].PositionSensor[0].deviceStatus").value(status))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[0].PositionSensor[0]._links.self.href").value("http://localhost/devices/" + deviceIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[1].RollerBlindActuator[0].deviceName").value(secondDeviceNameString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[1].RollerBlindActuator[0].deviceID").value(secondDeviceIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[1].RollerBlindActuator[0].deviceModel").value(deviceModel))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[1].RollerBlindActuator[0].deviceStatus").value(status))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.entryList[1].RollerBlindActuator[0]._links.self.href").value("http://localhost/devices/" + secondDeviceIdString))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href").value("http://localhost/devices/byfunctionality"));
    }

    /**
     * Test method to verify the behavior when the repository returns null.
     * <p>
     * This method tests the behavior of the endpoint when the repository returns null
     * while retrieving devices grouped by functionality. It initializes necessary value
     * objects to create devices, sensors, and actuators. The devices are associated with
     * sensors and actuators, simulating a scenario where devices have functionality components.
     * However, the actuator repository is conditioned to return null when queried for all actuators.
     * <p>
     * The test then performs the GET request using MockMvc and verifies that the response
     * status is 500 (Internal Server Error) and the content is empty. This indicates that
     * the endpoint failed to retrieve devices grouped by functionality due to a repository
     * returning null, and returned the appropriate status code along with null content.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */

    @Test
    void getDevicesByFunctionality_WhenRepositoryReturnsNull_ShouldReturnServiceUnavailableStatusCodeAndNullContent() throws Exception {
        //Arrange

        //Initializing necessary value objects to create a device
        String deviceNameString = "DeviceName";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceNameString);

        String deviceModel = "DeviceModel";
        DeviceModelVO modelVO = new DeviceModelVO(deviceModel);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());

        //Creating a device
        Device device = new Device(deviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO deviceID = device.getId();

        //Initializing necessary value objects to create a second device
        String secondDeviceNameString = "Second Device Name";
        DeviceNameVO secondDeviceNameVO = new DeviceNameVO(secondDeviceNameString);

        //Creating a device
        Device secondDevice = new Device(secondDeviceNameVO, modelVO, roomIDVO);

        //Extracting the generated device id
        DeviceIDVO secondDeviceID = secondDevice.getId();

        //Conditioning device repository to return the correct device associated with a deviceID
        when(deviceRepository.findById(deviceID)).thenReturn(device);
        when(deviceRepository.findById(secondDeviceID)).thenReturn(secondDevice);

        //Initializing a sensor
        String sensorNameString = "Position Sensor Name";
        SensorNameVO sensorNameVO = new SensorNameVO(sensorNameString);

        String positionTypeString = "PositionSensor";
        SensorTypeIDVO positionTypeIDVO = new SensorTypeIDVO(positionTypeString);

        Sensor positionSensor = new PositionSensor(sensorNameVO, deviceID, positionTypeIDVO);

        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(positionSensor);

        //Conditioning sensor repository double to return the previous created sensor
        when(sensorRepository.findAll()).thenReturn(sensorList);

        //Conditioning actuator repository to return null when queried for all sensors
        when(actuatorRepository.findAll()).thenReturn(null);


        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices/byfunctionality").accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isServiceUnavailable())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    /**
     * Test method to verify the behavior when there are no devices.
     * <p>
     * This method tests the behavior of the endpoint when there are no devices available
     * in the system. It conditions the sensor and actuator repositories to return empty lists,
     * simulating a scenario where no devices, sensors, or actuators are present.
     * <p>
     * The test then performs the GET request using MockMvc and verifies that the response
     * status is 200 (OK), and the response body contains an empty map. This indicates that
     * the endpoint successfully processed the request, found no devices, and returned
     * the appropriate status code along with an empty map.
     *
     * @throws Exception If an error occurs during the test execution related with mockMVC request to endpoint
     */
    @Test
    void getDevicesByFunctionality_WhenNoDevices_ShouldReturnOkStatusCodeAndEmptyMap() throws Exception {
        //Arrange

        List<Sensor> sensorList = new ArrayList<>();
        List<Actuator> actuatorList = new ArrayList<>();

        //Conditioning sensor repository double to return the previous created sensor
        when(sensorRepository.findAll()).thenReturn(sensorList);

        //Conditioning actuator repository to return null when queried for all sensors
        when(actuatorRepository.findAll()).thenReturn(actuatorList);

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/devices/byfunctionality").accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href").value("http://localhost/devices/byfunctionality"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }
}