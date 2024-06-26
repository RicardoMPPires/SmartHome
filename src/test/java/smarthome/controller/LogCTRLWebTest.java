package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.domain.device.Device;
import smarthome.domain.log.Log;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.SunsetSensor;
import smarthome.domain.sensor.SwitchSensor;
import smarthome.domain.sensor.sensorvalues.EnergyConsumptionValue;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.TemperatureValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.domain.vo.roomvo.*;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.mapper.dto.LogDTO;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.LogRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;
import smarthome.utils.timeconfig.TimeConfigDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for WebLogController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class LogCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LogRepository logRepository;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private SensorRepository sensorRepository;


    /**
     * Tests the {@code findReadingsInAPeriod} method with valid parameters.
     * <p>
     * This test verifies that when valid parameters are provided, the method successfully returns
     * a collection of log DTOs.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void givenValidParams_findReadingsInAPeriodSuccessfullyReturnsCollectionOfLogsDTO() throws Exception {
        //Arrange

        // Create two logs
        SensorIDVO sensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorIDVO sensorID2 = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorType = new SensorTypeIDVO("TemperatureSensor");
        LogIDVO logID1 = new LogIDVO(UUID.randomUUID());
        LogIDVO logID2 = new LogIDVO(UUID.randomUUID());
        TimeStampVO time1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:00:30"));
        TimeStampVO time2 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:10:00"));
        SensorValueObject<Double> reading1 = new TemperatureValue("23");
        SensorValueObject<Double> reading2 = new TemperatureValue("25");
        Log log1 = new Log(logID1, time1, reading1, sensorID1, deviceID, sensorType);
        Log log2 = new Log(logID2, time2, reading2, sensorID2, deviceID, sensorType);

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:00";
        String endDate = "2024-04-04";
        String endTime = "12:30:00";

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.findReadingsByDeviceID(deviceID, initialSearch, finalSearch)).thenReturn(List.of(log1, log2));

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialDate, initialTime, endDate, endTime
        );

        // Create the resulting DTOs
        LogDTO logDTO1 = LogDTO.builder()
                .logID(logID1.getID())
                .time("2024-04-04T12:00:30")
                .reading(reading1.getValue().toString())
                .sensorID(sensorID1.getID())
                .deviceID(deviceID.getID())
                .sensorTypeID(sensorType.getID())
                .build();

        LogDTO logDTO2 = LogDTO.builder()
                .logID(logID2.getID())
                .time("2024-04-04T12:10:00")
                .reading(reading2.getValue().toString())
                .sensorID(sensorID2.getID())
                .deviceID(deviceID.getID())
                .sensorTypeID(sensorType.getID())
                .build();


        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.logDTOList[0].logID").value(logDTO1.getLogID()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].time").value(logDTO1.getTime()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].reading").value(logDTO1.getReading()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].sensorID").value(logDTO1.getSensorID()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].deviceID").value(logDTO1.getDeviceID()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].sensorTypeID").value(logDTO1.getSensorTypeID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].logID").value(logDTO2.getLogID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].time").value(logDTO2.getTime()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].reading").value(logDTO2.getReading()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].sensorID").value(logDTO2.getSensorID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].deviceID").value(logDTO2.getDeviceID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].sensorTypeID").value(logDTO2.getSensorTypeID()));
    }

    /**
     * Test case to verify that a GET request to the "/logs" endpoint with an existing device ID and no time frame
     * specified returns all readings for that device, regardless of the date.

     * This test case uses a mock repository to return a predefined list of logs when queried by device ID without
     * any time constraints. The assertions validate that each returned Log matches the expected log data.

     * Steps:
     * 1. Arrange: Create the necessary objects, including sensors, device, logs, and timestamps.
     * 2. Act: Perform a mock GET request to the "/logs" endpoint with the specified device ID.
     * 3. Assert: Validate that the response contains the expected logs with the correct data.
     *
     * @throws Exception if an exception occurs during the mock MVC request operation.
     */

    @Test
    void findReadings_WhenExistentDeviceIdAndNoTimeFrame_ShouldReturnAllDeviceReadings() throws Exception {
        //Arrange

        // Creates necessary objects to create two logs
        SensorIDVO sensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorIDVO sensorID2 = new SensorIDVO(UUID.randomUUID());

        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());

        SensorTypeIDVO sensorType = new SensorTypeIDVO("TemperatureSensor");

        LogIDVO logID1 = new LogIDVO(UUID.randomUUID());
        LogIDVO logID2 = new LogIDVO(UUID.randomUUID());

        String timeStampOne = "2024-04-04T12:00:30";
        String timeStampTwo = "2024-04-04T12:10:00";

        TimeStampVO time1 = new TimeStampVO(LocalDateTime.parse(timeStampOne));
        TimeStampVO time2 = new TimeStampVO(LocalDateTime.parse(timeStampTwo));

        SensorValueObject<Double> reading1 = new TemperatureValue("23");
        SensorValueObject<Double> reading2 = new TemperatureValue("25");

        Log log1 = new Log(logID1, time1, reading1, sensorID1, deviceID, sensorType);
        Log log2 = new Log(logID2, time2, reading2, sensorID2, deviceID, sensorType);

        when(logRepository.findReadingsByDeviceID(deviceID, null, null)).thenReturn(List.of(log1, log2));


        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.logDTOList[0].logID").value(log1.getId().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].time").value(timeStampOne))
                .andExpect(jsonPath("$._embedded.logDTOList[0].reading").value(log1.getReading().getValue()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].sensorID").value(log1.getSensorID().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].deviceID").value(log1.getDeviceID().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[0].sensorTypeID").value(log1.getSensorTypeID().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].logID").value(log2.getId().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].time").value(timeStampTwo))
                .andExpect(jsonPath("$._embedded.logDTOList[1].reading").value(log2.getReading().getValue()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].sensorID").value(log2.getSensorID().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].deviceID").value(log2.getDeviceID().getID()))
                .andExpect(jsonPath("$._embedded.logDTOList[1].sensorTypeID").value(log2.getSensorTypeID().getID()));
    }

    /**
     * Test case to verify that a GET request to the "/logs" endpoint with an existing device ID
     * and only an initial time frame specified returns a Bad Request status.

     * This test case creates a TimeConfigDTO with only the initial time frame set, simulating an
     * incomplete time configuration. The test ensures that the API responds with a 400 Bad Request
     * status when such a request is made.

     * Steps:
     * 1. Arrange: Create the necessary device ID and incomplete TimeConfigDTO.
     * 2. Act: Perform a mock GET request to the "/logs" endpoint with the specified device ID and incomplete time configuration.
     * 3. Assert: Validate that the response status is 400 Bad Request.
     *
     * @throws Exception if an exception occurs during the mock MVC request operation.
     */
    @Test
    void findReadings_WhenOnlyInitialTimeFrame_ShouldReturnBadRequest() throws Exception {
        //Arrange
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());

        String time = "12:00:30";
        String date = "2024-04-04";

        TimeConfigDTO incompleteTimeConfig = TimeConfigDTO.builder()
                .initialTime(time)
                .initialDate(date)
                .build();

        String jsonTimeConfig = objectMapper.writeValueAsString(incompleteTimeConfig);

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTimeConfig))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that a GET request to the "/logs" endpoint with an existing device ID
     * and only a final time frame specified returns a Bad Request status.

     * This test case creates a TimeConfigDTO with only the final time frame set, simulating an
     * incomplete time configuration. The test ensures that the API responds with a 400 Bad Request
     * status when such a request is made.

     * Steps:
     * 1. Arrange: Create the necessary device ID and incomplete TimeConfigDTO.
     * 2. Act: Perform a mock GET request to the "/logs" endpoint with the specified device ID and incomplete time configuration.
     * 3. Assert: Validate that the response status is 400 Bad Request.
     *
     * @throws Exception if an exception occurs during the mock MVC request operation.
     */

    @Test
    void findReadings_WhenOnlyFinalTimeFrame_ShouldReturnBadRequest() throws Exception {
        //Arrange
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());

        String time = "12:00:30";
        String date = "2024-04-04";

        TimeConfigDTO incompleteTimeConfig = TimeConfigDTO.builder()
                .endTime(time)
                .endDate(date)
                .build();

        String jsonTimeConfig = objectMapper.writeValueAsString(incompleteTimeConfig);

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTimeConfig))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case to verify that a GET request to the "/logs" endpoint with a non-existent device ID
     * and no time frame specified returns an empty response indicating no readings.

     * This test case uses a mock repository to return an empty list of logs when queried by a non-existent
     * device ID without any time constraints. The assertions validate that the response contains no log data.

     * Steps:
     * 1. Arrange: Create the necessary device ID and configure the mock repository to return an empty list.
     * 2. Act: Perform a mock GET request to the "/logs" endpoint with the specified non-existent device ID.
     * 3. Assert: Validate that the response status is OK and the response body is empty.
     *
     * @throws Exception if an exception occurs during the mock MVC request operation.
     */

    @Test
    void findReadings_WhenNonExistentDeviceIdAndNoTimeFrame_ShouldReturnNoReadings() throws Exception {

        //Arrange
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        when(logRepository.findReadingsByDeviceID(deviceID, null, null)).thenReturn(Collections.emptyList());

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }


    /**
     * Tests the {@code findReadingsInAPeriod} method with an invalid device ID.
     * <p>
     * This test verifies that when an invalid device ID is provided, the method returns
     * a {@code BadRequest} status.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void givenInvalidDeviceID_findReadingsInAPeriodReturnsBadRequestStatus() throws Exception {
        //Arrange

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:00";
        String endDate = "2024-04-04";
        String endTime = "12:30:00";


        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialDate, initialTime, endDate, endTime
        );

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", "I AM INVALID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isBadRequest());
    }


    /**
     * Tests the {@code findReadingsInAPeriod} method with invalid timestamps.
     * <p>
     * This test verifies that when invalid timestamps are provided, the method returns
     * a {@code BadRequest} status.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void givenInvalidTimeStamps_findReadingsInAPeriodReturnsBadRequestStatus() throws Exception {
        //Arrange
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:00";
        String endDate = "2024-04-04";
        String endTime = "12:30:00";

        String initialTimeFail = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();

        String timeConfigJson1 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                null, initialTime, endDate, endTime
        );

        String timeConfigJson2 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialDate, null, endDate, endTime
        );

        String timeConfigJson3 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialDate, initialTime, null, endTime
        );

        String timeConfigJson4 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialDate, initialTime, endDate, null
        );

        String timeConfigJson5 = String.format(
                "{\"initialTimeFail\":\"%s\",\"initialDate\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialTimeFail, initialDate, endDate, endTime
        );


        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson1))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson2))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson3))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson4))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson5))
                .andExpect(status().isBadRequest());

    }


    /**
     * Tests the {@code findReadingsInAPeriod} method when the device ID is not found.
     * <p>
     * This test verifies that when the specified device ID is not found, the method returns
     * an empty collection of logs.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void givenDeviceIdNotFound_findReadingsInAPeriodReturnsAnEmptyCollection() throws Exception {
        //Arrange

        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:00";
        String endDate = "2024-04-04";
        String endTime = "12:30:00";

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.findReadingsByDeviceID(deviceID, initialSearch, finalSearch)).thenReturn(new ArrayList<>());

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\"}",
                initialDate, initialTime, endDate, endTime
        );

        //Act & Assert
        mockMvc.perform(get("/logs")
                        .param("deviceId", deviceID.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }


    /**
     * Tests the {@code getMaxTempDiff} method with correct parameters.
     * <p>
     * This test verifies that when correct parameters are provided, the method successfully returns
     * the maximum temperature difference between indoor and outdoor sensors.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenGivenCorrectParams_thenGetMaxTempDiffMethodReturnsSuccessMessage() throws Exception {
        // Arrange

        // Arranging two rooms and roomRepository
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        RoomNameVO roomNameVO = new RoomNameVO("Balcony");
        RoomFloorVO floor = new RoomFloorVO(1);
        RoomLengthVO length = new RoomLengthVO(10);
        RoomWidthVO width = new RoomWidthVO(10);
        RoomHeightVO height = new RoomHeightVO(0);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
        Room outRoom = new Room(roomNameVO, floor, roomDimensions, houseID);
        RoomIDVO outRoomIDVO = outRoom.getId();

        RoomNameVO roomNameVO2 = new RoomNameVO("BedRoom");
        RoomFloorVO floor2 = new RoomFloorVO(1);
        RoomLengthVO length2 = new RoomLengthVO(10);
        RoomWidthVO width2 = new RoomWidthVO(10);
        RoomHeightVO height2 = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensions2 = new RoomDimensionsVO(length2, width2, height2);
        Room inRoom = new Room(roomNameVO2, floor2, roomDimensions2, houseID);
        RoomIDVO inRoomIDVO = inRoom.getId();

        when(roomRepository.findById(outRoomIDVO)).thenReturn(outRoom);
        when(roomRepository.findById(inRoomIDVO)).thenReturn(inRoom);

        // Setting up two devices and deviceRepository
        DeviceNameVO deviceNameVO = new DeviceNameVO("OutdoorDevice");
        DeviceModelVO deviceModelVO = new DeviceModelVO("OutdoorModel");
        Device outDevice = new Device(deviceNameVO, deviceModelVO, outRoomIDVO);
        DeviceIDVO outDeviceIDVO = outDevice.getId();

        DeviceNameVO deviceNameVO2 = new DeviceNameVO("IndoorDevice");
        DeviceModelVO deviceModelVO2 = new DeviceModelVO("IndoorModel");
        Device inDevice = new Device(deviceNameVO2, deviceModelVO2, inRoomIDVO);
        DeviceIDVO inDeviceIDVO = inDevice.getId();

        when(deviceRepository.findById(outDeviceIDVO)).thenReturn(outDevice);
        when(deviceRepository.findById(inDeviceIDVO)).thenReturn(inDevice);

        // Create two logs, one for the outside device, another for the inside device
        // Create log repository and stub the behaviour to return iterable containing said logs
        SensorIDVO sensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorIDVO sensorID2 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorType = new SensorTypeIDVO("TemperatureSensor");
        LogIDVO logID1 = new LogIDVO(UUID.randomUUID());
        LogIDVO logID2 = new LogIDVO(UUID.randomUUID());
        TimeStampVO time1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:00:30"));
        TimeStampVO time2 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:04:30"));
        String time2Str = time2.getValue().toString();
        SensorValueObject<Double> outReadingVO = new TemperatureValue("23");
        SensorValueObject<Double> inReadingVO = new TemperatureValue("25");

        Log log1 = new Log(logID1, time1, outReadingVO, sensorID1, outDeviceIDVO, sensorType);
        Iterable<Log> outLog = List.of(log1);
        Log log2 = new Log(logID2, time2, inReadingVO, sensorID2, inDeviceIDVO, sensorType);
        Iterable<Log> inLog = List.of(log2);

        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.getDeviceTemperatureLogs(outDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(outLog);
        when(logRepository.getDeviceTemperatureLogs(inDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(inLog);

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        // Act & Assert
        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isOk())
                .andExpect(content().string("The Maximum Temperature Difference within the selected Period was of 2.0 Cº which happened at " + time2Str));
    }


    /**
     * Tests the {@code getMaxTempDiff} method when no logs are found within the delta provided.
     * <p>
     * This test verifies that when no logs are found within the specified delta, the method returns
     * an appropriate message.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenNoLogsFoundWithinDeltaProvided_thenGetMaxTempDiffMethodReturnsAppropriateMessage() throws Exception {
        // Arrange

        // Arranging two rooms and roomRepository
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        RoomNameVO roomNameVO = new RoomNameVO("Balcony");
        RoomFloorVO floor = new RoomFloorVO(1);
        RoomLengthVO length = new RoomLengthVO(10);
        RoomWidthVO width = new RoomWidthVO(10);
        RoomHeightVO height = new RoomHeightVO(0);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
        Room outRoom = new Room(roomNameVO, floor, roomDimensions, houseID);
        RoomIDVO outRoomIDVO = outRoom.getId();

        RoomNameVO roomNameVO2 = new RoomNameVO("BedRoom");
        RoomFloorVO floor2 = new RoomFloorVO(1);
        RoomLengthVO length2 = new RoomLengthVO(10);
        RoomWidthVO width2 = new RoomWidthVO(10);
        RoomHeightVO height2 = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensions2 = new RoomDimensionsVO(length2, width2, height2);
        Room inRoom = new Room(roomNameVO2, floor2, roomDimensions2, houseID);
        RoomIDVO inRoomIDVO = inRoom.getId();

        when(roomRepository.findById(outRoomIDVO)).thenReturn(outRoom);
        when(roomRepository.findById(inRoomIDVO)).thenReturn(inRoom);

        // Setting up two devices and deviceRepository
        DeviceNameVO deviceNameVO = new DeviceNameVO("OutdoorDevice");
        DeviceModelVO deviceModelVO = new DeviceModelVO("OutdoorModel");
        Device outDevice = new Device(deviceNameVO, deviceModelVO, outRoomIDVO);
        DeviceIDVO outDeviceIDVO = outDevice.getId();

        DeviceNameVO deviceNameVO2 = new DeviceNameVO("IndoorDevice");
        DeviceModelVO deviceModelVO2 = new DeviceModelVO("IndoorModel");
        Device inDevice = new Device(deviceNameVO2, deviceModelVO2, inRoomIDVO);
        DeviceIDVO inDeviceIDVO = inDevice.getId();

        when(deviceRepository.findById(outDeviceIDVO)).thenReturn(outDevice);
        when(deviceRepository.findById(inDeviceIDVO)).thenReturn(inDevice);

        // Create two logs, one for the outside device, another for the inside device
        // Create log repository and stub the behaviour to return iterable containing said logs
        SensorIDVO sensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorIDVO sensorID2 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorType = new SensorTypeIDVO("TemperatureSensor");
        LogIDVO logID1 = new LogIDVO(UUID.randomUUID());
        LogIDVO logID2 = new LogIDVO(UUID.randomUUID());
        TimeStampVO time1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:00:30"));
        TimeStampVO time2 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:04:30"));
        SensorValueObject<Double> outReadingVO = new TemperatureValue("23");
        SensorValueObject<Double> inReadingVO = new TemperatureValue("25");

        Log log1 = new Log(logID1, time1, outReadingVO, sensorID1, outDeviceIDVO, sensorType);
        Iterable<Log> outLog = List.of(log1);
        Log log2 = new Log(logID2, time2, inReadingVO, sensorID2, inDeviceIDVO, sensorType);
        Iterable<Log> inLog = List.of(log2);

        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "1";

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.getDeviceTemperatureLogs(outDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(outLog);
        when(logRepository.getDeviceTemperatureLogs(inDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(inLog);

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        // Act & Assert
        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Readings were found within the provided time span, but with no matches within the delta provided"));
    }


    /**
     * Tests the {@code getMaxTempDiff} method when no logs are present.
     * <p>
     * This test verifies that when no logs are present, the method returns
     * an appropriate message.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenNoLogsPresent_thenGetMaxTempDiffMethodReturnsAppropriateMessage() throws Exception {
        // Arrange

        // Arranging two rooms and roomRepository
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        RoomNameVO roomNameVO = new RoomNameVO("Balcony");
        RoomFloorVO floor = new RoomFloorVO(1);
        RoomLengthVO length = new RoomLengthVO(10);
        RoomWidthVO width = new RoomWidthVO(10);
        RoomHeightVO height = new RoomHeightVO(0);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
        Room outRoom = new Room(roomNameVO, floor, roomDimensions, houseID);
        RoomIDVO outRoomIDVO = outRoom.getId();

        RoomNameVO roomNameVO2 = new RoomNameVO("BedRoom");
        RoomFloorVO floor2 = new RoomFloorVO(1);
        RoomLengthVO length2 = new RoomLengthVO(10);
        RoomWidthVO width2 = new RoomWidthVO(10);
        RoomHeightVO height2 = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensions2 = new RoomDimensionsVO(length2, width2, height2);
        Room inRoom = new Room(roomNameVO2, floor2, roomDimensions2, houseID);
        RoomIDVO inRoomIDVO = inRoom.getId();

        when(roomRepository.findById(outRoomIDVO)).thenReturn(outRoom);
        when(roomRepository.findById(inRoomIDVO)).thenReturn(inRoom);

        // Setting up two devices and deviceRepository
        DeviceNameVO deviceNameVO = new DeviceNameVO("OutdoorDevice");
        DeviceModelVO deviceModelVO = new DeviceModelVO("OutdoorModel");
        Device outDevice = new Device(deviceNameVO, deviceModelVO, outRoomIDVO);
        DeviceIDVO outDeviceIDVO = outDevice.getId();

        DeviceNameVO deviceNameVO2 = new DeviceNameVO("IndoorDevice");
        DeviceModelVO deviceModelVO2 = new DeviceModelVO("IndoorModel");
        Device inDevice = new Device(deviceNameVO2, deviceModelVO2, inRoomIDVO);
        DeviceIDVO inDeviceIDVO = inDevice.getId();

        when(deviceRepository.findById(outDeviceIDVO)).thenReturn(outDevice);
        when(deviceRepository.findById(inDeviceIDVO)).thenReturn(inDevice);

        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.getDeviceTemperatureLogs(outDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(new ArrayList<>());
        when(logRepository.getDeviceTemperatureLogs(inDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(new ArrayList<>());

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        // Act & Assert
        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isOk())
                .andExpect(content().string("There are no records available for the given period"));
    }


    /**
     * Tests the {@code getMaxTempDiff} method with invalid device IDs.
     * <p>
     * This test verifies that when invalid device IDs are provided, the method returns
     * a {@code BadRequest} status. It arranges two rooms and devices, constructs an
     * invalid request JSON body, and performs a GET request to the "/logs" endpoint with
     * the invalid device IDs.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenGivenInvalidDeviceIDs_ReturnsBadRequestStatus() throws Exception {
        // Arrange

        // Arranging two rooms and roomRepository
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        RoomNameVO roomNameVO = new RoomNameVO("Balcony");
        RoomFloorVO floor = new RoomFloorVO(1);
        RoomLengthVO length = new RoomLengthVO(10);
        RoomWidthVO width = new RoomWidthVO(10);
        RoomHeightVO height = new RoomHeightVO(0);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
        Room outRoom = new Room(roomNameVO, floor, roomDimensions, houseID);
        RoomIDVO outRoomIDVO = outRoom.getId();

        RoomNameVO roomNameVO2 = new RoomNameVO("BedRoom");
        RoomFloorVO floor2 = new RoomFloorVO(1);
        RoomLengthVO length2 = new RoomLengthVO(10);
        RoomWidthVO width2 = new RoomWidthVO(10);
        RoomHeightVO height2 = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensions2 = new RoomDimensionsVO(length2, width2, height2);
        Room inRoom = new Room(roomNameVO2, floor2, roomDimensions2, houseID);
        RoomIDVO inRoomIDVO = inRoom.getId();

        // Setting up two devices and deviceRepository
        DeviceNameVO deviceNameVO = new DeviceNameVO("OutdoorDevice");
        DeviceModelVO deviceModelVO = new DeviceModelVO("OutdoorModel");
        Device outDevice = new Device(deviceNameVO, deviceModelVO, outRoomIDVO);
        DeviceIDVO outDeviceIDVO = outDevice.getId();

        DeviceNameVO deviceNameVO2 = new DeviceNameVO("IndoorDevice");
        DeviceModelVO deviceModelVO2 = new DeviceModelVO("IndoorModel");
        Device inDevice = new Device(deviceNameVO2, deviceModelVO2, inRoomIDVO);
        DeviceIDVO inDeviceIDVO = inDevice.getId();

        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";


        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        // Act & Assert
        mockMvc.perform(get("/logs")
                        .param("outdoorId", "I AM INVALID")
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", "I AM INVALID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(status().isBadRequest());
    }


    /**
     * Tests the {@code getMaxTempDiff} method with invalid timestamps.
     * <p>
     * This test verifies that when invalid timestamps are provided, the method returns
     * a {@code BadRequest} status. It arranges two rooms and devices, constructs various
     * invalid request JSON bodies with missing or null timestamp values, and performs
     * GET requests to the "/logs" endpoint with the invalid timestamps.
     * </p>
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenGivenInvalidTimeStamps_ReturnsBadRequestStatus() throws Exception {
        // Arrange

        // Arranging two rooms and roomRepository
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        RoomNameVO roomNameVO = new RoomNameVO("Balcony");
        RoomFloorVO floor = new RoomFloorVO(1);
        RoomLengthVO length = new RoomLengthVO(10);
        RoomWidthVO width = new RoomWidthVO(10);
        RoomHeightVO height = new RoomHeightVO(0);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(length, width, height);
        Room outRoom = new Room(roomNameVO, floor, roomDimensions, houseID);
        RoomIDVO outRoomIDVO = outRoom.getId();

        RoomNameVO roomNameVO2 = new RoomNameVO("BedRoom");
        RoomFloorVO floor2 = new RoomFloorVO(1);
        RoomLengthVO length2 = new RoomLengthVO(10);
        RoomWidthVO width2 = new RoomWidthVO(10);
        RoomHeightVO height2 = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensions2 = new RoomDimensionsVO(length2, width2, height2);
        Room inRoom = new Room(roomNameVO2, floor2, roomDimensions2, houseID);
        RoomIDVO inRoomIDVO = inRoom.getId();

        when(roomRepository.findById(outRoomIDVO)).thenReturn(outRoom);
        when(roomRepository.findById(inRoomIDVO)).thenReturn(inRoom);

        // Setting up two devices and deviceRepository
        DeviceNameVO deviceNameVO = new DeviceNameVO("OutdoorDevice");
        DeviceModelVO deviceModelVO = new DeviceModelVO("OutdoorModel");
        Device outDevice = new Device(deviceNameVO, deviceModelVO, outRoomIDVO);
        DeviceIDVO outDeviceIDVO = outDevice.getId();

        DeviceNameVO deviceNameVO2 = new DeviceNameVO("IndoorDevice");
        DeviceModelVO deviceModelVO2 = new DeviceModelVO("IndoorModel");
        Device inDevice = new Device(deviceNameVO2, deviceModelVO2, inRoomIDVO);
        DeviceIDVO inDeviceIDVO = inDevice.getId();

        when(deviceRepository.findById(outDeviceIDVO)).thenReturn(outDevice);
        when(deviceRepository.findById(inDeviceIDVO)).thenReturn(inDevice);

        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.getDeviceTemperatureLogs(outDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(new ArrayList<>());
        when(logRepository.getDeviceTemperatureLogs(inDeviceIDVO, "TemperatureSensor", initialSearch, finalSearch))
                .thenReturn(new ArrayList<>());

        String timeConfigJson1 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                null, initialTime, endDate, endTime, deltaMin
        );

        String timeConfigJson2 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, null, endDate, endTime, deltaMin
        );

        String timeConfigJson3 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, initialTime, null, endTime, deltaMin
        );

        String timeConfigJson4 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\",\"deltaMin\":%s}",
                initialDate, initialTime, endDate, null, deltaMin
        );

        // Act & Assert
        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson1))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson2))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson3))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/logs")
                        .param("outdoorId", outDeviceIDVO.getID())
                        .param("indoorId", inDeviceIDVO.getID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson4))
                .andExpect(status().isBadRequest());

    }

    /**
     * Tests the behavior of getPeakPowerConsumption endpoint in LogController
     * when invalid timestamps are provided in the request.
     * <p>
     * This test verifies that the endpoint returns a Bad Request status
     * when the provided timestamps are missing or in an invalid format.
     * </p>
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void givenInvalidTimeStamps_getPeakPowerConsumptionReturnsBadRequestStatus() throws Exception {
        //Arrange

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        String initialTimeFail = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).toString();

        String timeConfigJson1 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                null, initialTime, endDate, endTime, deltaMin
        );

        String timeConfigJson2 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, null, endDate, endTime, deltaMin
        );

        String timeConfigJson3 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, initialTime, null, endTime, deltaMin
        );

        String timeConfigJson4 = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, initialTime, endDate, null, deltaMin
        );

        String timeConfigJson5 = String.format(
                "{\"initialTimeFail\":\"%s\",\"initialDate\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialTimeFail, initialDate, endDate, endTime, deltaMin
        );


        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson1))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson2))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson3))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson4))
                .andExpect(status().isBadRequest());

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson5))
                .andExpect(status().isBadRequest());

    }

    /**
     * Tests the behavior of getPeakPowerConsumption endpoint in LogController
     * when no power grid logs are found within the specified time range.
     * <p>
     * This test ensures that the endpoint returns an appropriate string message
     * indicating that there are no records available from the Grid Power Meter
     * for the given period.
     * </p>
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void whenNoPowerGridLogsAreFound_getPeakPowerConsumptionReturnsAppropriateStringMessage() throws Exception {
        //Arrange

        String expected = "There are no records available from the Grid Power Meter for the given period";

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorType = new SensorTypeIDVO("PowerGridMeter");
        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(deviceID.getID(), sensorType.getID(), initialSearch, finalSearch)).thenReturn(Collections.emptyList());

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(content().string(expected))
                .andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Tests the behavior of getPeakPowerConsumption endpoint in LogController
     * when only power grid logs are found within the specified time range
     * and no power source logs are found.
     * <p>
     * This test ensures that the endpoint calculates the peak power consumption
     * from the grid and returns an appropriate string message containing the
     * peak power consumption value and the time it occurred, along with a message
     * indicating that no power source device logs were found within the selected period.
     * </p>
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void whenGridLogsAreFoundButNoPowerSourceLogs_getPeakPowerConsumptionCalculatesConsumptionAndReturnsAppropriateStringMessage() throws Exception {
        //Arrange

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        LogIDVO powerGridLogID1 = new LogIDVO(UUID.randomUUID());
        TimeStampVO powerGridTime1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T11:35:30"));
        SensorValueObject powerGridReading1 = new EnergyConsumptionValue("23");
        DeviceIDVO powerGridDeviceID1 = new DeviceIDVO(UUID.randomUUID());
        SensorIDVO powerGridSensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO powerGridSensorTypeID1 = new SensorTypeIDVO("ElectricEnergyConsumptionSensor");
        Log powerGridLog1 = new Log(powerGridLogID1, powerGridTime1, powerGridReading1, powerGridSensorID1, powerGridDeviceID1, powerGridSensorTypeID1);

        String deviceID = System.getProperty("Grid Power Meter device", powerGridDeviceID1.getID());
        String sensorTypeID = System.getProperty("Grid Power Meter sensor type", powerGridSensorTypeID1.getID());

        List<Log> listOfPowerGridLogs = new ArrayList<>();
        listOfPowerGridLogs.add(powerGridLog1);

        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialSearch, finalSearch)).thenReturn(listOfPowerGridLogs);

        when(logRepository.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialSearch, finalSearch)).thenReturn(Collections.emptyList());

        String expected = "The Peak Power Consumption from the Grid within the selected Period was " + powerGridLog1.getReading().getValue() +
                " Wh which happened at " + powerGridLog1.getTime().getValue() + " (No Power Source Device Logs were found within the selected period)";


        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(content().string(expected))
                .andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Tests the behavior of getPeakPowerConsumption endpoint in LogController
     * when logs are found outside the delta range specified in the request.
     * <p>
     * This test ensures that the endpoint returns an appropriate string message
     * indicating that readings were found within the provided time span but with no
     * instant matches within the delta provided.
     * </p>
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void whenLogsAreFoundOutsideTheDeltaRange_getPeakPowerConsumptionReturnsAppropriateStringMessage() throws Exception {
        //Arrange

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "5";

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        // Arranging PowerGridLogs and Stubbing the repository
        LogIDVO powerGridLogID1 = new LogIDVO(UUID.randomUUID());
        TimeStampVO powerGridTime1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T11:35:30"));
        SensorValueObject powerGridReading1 = new EnergyConsumptionValue("23");
        DeviceIDVO powerGridDeviceID1 = new DeviceIDVO(UUID.randomUUID());
        SensorIDVO powerGridSensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO powerGridSensorTypeID1 = new SensorTypeIDVO("ElectricEnergyConsumptionSensor");
        Log powerGridLog1 = new Log(powerGridLogID1, powerGridTime1, powerGridReading1, powerGridSensorID1, powerGridDeviceID1, powerGridSensorTypeID1);

        String deviceID = System.getProperty("Grid Power Meter device", powerGridDeviceID1.getID());
        String sensorTypeID = System.getProperty("Grid Power Meter sensor type", powerGridSensorTypeID1.getID());

        List<Log> listOfPowerGridLogs = new ArrayList<>();
        listOfPowerGridLogs.add(powerGridLog1);

        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialSearch, finalSearch)).thenReturn(listOfPowerGridLogs);

        // Arranging PowerSourceLogs and Stubbing the repository
        LogIDVO powerSourceID1 = new LogIDVO(UUID.randomUUID());
        TimeStampVO powerSourceTime1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:20:30"));
        SensorValueObject powerSourceReading1 = new EnergyConsumptionValue("-3");
        DeviceIDVO powerSourceDeviceID1 = new DeviceIDVO(UUID.randomUUID());
        SensorIDVO powerSourceSensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO powerSourceSensorTypeID1 = new SensorTypeIDVO("ElectricEnergyConsumptionSensor");
        Log powerSourceLog1 = new Log(powerSourceID1, powerSourceTime1, powerSourceReading1, powerSourceSensorID1, powerSourceDeviceID1, powerSourceSensorTypeID1);

        List<Log> listOfPowerSourceLogs = new ArrayList<>();
        listOfPowerSourceLogs.add(powerSourceLog1);

        when(logRepository.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialSearch, finalSearch)).thenReturn(listOfPowerSourceLogs);

        String expected = "Readings were found within the provided time span, but with no instant matches within the delta provided";

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(content().string(expected))
                .andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Tests the behavior of getPeakPowerConsumption endpoint in LogController
     * when logs from both power grid and power source are found within the specified time range.
     * <p>
     * This test ensures that the endpoint successfully calculates the peak power consumption
     * and returns a string message containing the peak power consumption value and the time it occurred.
     * </p>
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    void whenLogsFromBothPowerGridAndSourceAreFoundAndWithinRange_getPeakPowerConsumptionSuccessfullyCalculatesPeakPowerAndReturnsStringMessage() throws Exception {
        //Arrange

        // Create the Json body
        String initialDate = "2024-04-04";
        String initialTime = "11:30:30";
        String endDate = "2024-04-04";
        String endTime = "12:30:30";
        String deltaMin = "10";

        String timeConfigJson = String.format(
                "{\"initialDate\":\"%s\",\"initialTime\":\"%s\",\"endDate\":\"%s\",\"endTime\":\"%s\", \"deltaMin\":\"%s\"}",
                initialDate, initialTime, endDate, endTime, deltaMin
        );

        TimeStampVO initialSearch = new TimeStampVO(initialDate, initialTime);
        TimeStampVO finalSearch = new TimeStampVO(endDate, endTime);

        // Arranging PowerGridLogs and stubbing the repository
        LogIDVO powerGridLogID1 = new LogIDVO(UUID.randomUUID());
        TimeStampVO powerGridTime1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:20:30"));
        SensorValueObject powerGridReading1 = new EnergyConsumptionValue("23");
        DeviceIDVO powerGridDeviceID1 = new DeviceIDVO(UUID.randomUUID());
        SensorIDVO powerGridSensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO powerGridSensorTypeID1 = new SensorTypeIDVO("ElectricEnergyConsumptionSensor");
        Log powerGridLog1 = new Log(powerGridLogID1, powerGridTime1, powerGridReading1, powerGridSensorID1, powerGridDeviceID1, powerGridSensorTypeID1);

        String deviceID = System.getProperty("Grid Power Meter device", powerGridDeviceID1.getID());
        String sensorTypeID = System.getProperty("Grid Power Meter sensor type", powerGridSensorTypeID1.getID());

        List<Log> listOfPowerGridLogs = new ArrayList<>();
        listOfPowerGridLogs.add(powerGridLog1);

        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialSearch, finalSearch)).thenReturn(listOfPowerGridLogs);

        // Arranging PowerSupplyLogs and stubbing the repository
        LogIDVO powerSourceID1 = new LogIDVO(UUID.randomUUID());
        TimeStampVO powerSourceTime1 = new TimeStampVO(LocalDateTime.parse("2024-04-04T12:16:30"));
        SensorValueObject powerSourceReading1 = new EnergyConsumptionValue("-3");
        DeviceIDVO powerSourceDeviceID1 = new DeviceIDVO(UUID.randomUUID());
        SensorIDVO powerSourceSensorID1 = new SensorIDVO(UUID.randomUUID());
        SensorTypeIDVO powerSourceSensorTypeID1 = new SensorTypeIDVO("ElectricEnergyConsumptionSensor");
        Log powerSourceLog1 = new Log(powerSourceID1, powerSourceTime1, powerSourceReading1, powerSourceSensorID1, powerSourceDeviceID1, powerSourceSensorTypeID1);

        List<Log> listOfPowerSourceLogs = new ArrayList<>();
        listOfPowerSourceLogs.add(powerSourceLog1);

        when(logRepository.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialSearch, finalSearch)).thenReturn(listOfPowerSourceLogs);

        String expected = "The Peak Power Consumption of the House within the selected Period was of 26 Wh which happened at " + powerGridTime1.getValue();

        //Act & Assert
        mockMvc.perform(get("/logs/peak-power-consumption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeConfigJson))
                .andExpect(content().string(expected))
                .andExpect(status().isOk())
                .andReturn();

    }

    /**
     * Tests that the getSunReading method returns HTTP status BAD_REQUEST when given invalid SensorTypeIDVO values.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenGivenInvalidSensorTypeIDVO_getSunReadingReturnsBadRequest() throws Exception {
        // Arrange
        String date = "2024-05-10";
        String latitude = "45";
        String longitude = "45";

        String sensorTypeId1 = " ";
        String sensorTypeId2 = "";
        String sensorTypeId3 = "1241212";
        String sensorTypeId4 = null;


        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorId", sensorTypeId1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorId", sensorTypeId2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorId", sensorTypeId3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorTypeId", sensorTypeId4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that the getSunReading method returns HTTP status BAD_REQUEST when the SensorTypeIDVO does not match any sensor.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenSensorTypeIDVODoesNotMatchAnySunSensor_getSunReadingReturnsBadRequest() throws Exception {
        // Arrange
        String date = "2024-05-10";
        String latitude = "45";
        String longitude = "45";

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO("HumiditySensor");
        String sensorTypeId = sensorTypeIDVO.getID();

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorTypeId", sensorTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that the getSunReading method returns HTTP status BAD_REQUEST when the chosen sensor is not a Sun sensor.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenSensorChosenIsNotASunSensor_getSunReadingReturnsBadRequest() throws Exception {
        // Arrange
        String date = "2024-05-10";
        String latitude = "45";
        String longitude = "45";

        SensorNameVO sensorNameVO = new SensorNameVO("sensor1");
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO("SwitchSensor");
        SwitchSensor sensor = new SwitchSensor(sensorNameVO,deviceIDVO,sensorTypeIDVO);
        String sensorTypeId = sensor.getSensorTypeID().getID();

        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.emptyList());

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorTypeId", sensorTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that the getSunReading method returns HTTP status BAD_REQUEST when the log repository is unable to save the reading.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenLogRepositoryIsUnableToSaveReading_getSunReadingReturnsBadRequest() throws Exception {
        // Arrange
        String date = "2024-05-10";
        String latitude = "45";
        String longitude = "45";

        SensorNameVO sensorNameVO = new SensorNameVO("sensor1");
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO("SunriseSensor");
        SunsetSensor sensor = new SunsetSensor(sensorNameVO,deviceIDVO,sensorTypeIDVO);
        String sensorTypeId = sensor.getSensorTypeID().getID();

        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));
        when(logRepository.save(any())).thenReturn(false);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorTypeId", sensorTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that the getSunReading method returns HTTP status OK and the expected sun reading as a String
     * when given correct parameters.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void whenGivenCorrectParameters_getSunReadingReturnsOkAndReadingAsString() throws Exception {
        // Arrange
        String date = "2024-05-10";
        String latitude = "45";
        String longitude = "45";

        SensorNameVO sensorNameVO = new SensorNameVO("sensor1");
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO("SunsetSensor");
        SunsetSensor sensor = new SunsetSensor(sensorNameVO,deviceIDVO,sensorTypeIDVO);
        String sensorTypeId = sensor.getSensorTypeID().getID();

        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));
        when(logRepository.save(any())).thenReturn(true);

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/logs/get-sun-reading")
                        .param("date", date)
                        .param("latitude", latitude)
                        .param("longitude", longitude)
                        .param("sensorTypeId", sensorTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2024-05-10T16:17:03Z[UTC]"));
    }
}