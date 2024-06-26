package smarthome.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.device.Device;
import smarthome.domain.log.Log;
import smarthome.domain.log.LogFactory;
import smarthome.domain.log.LogFactoryImpl;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SunSensor;
import smarthome.domain.sensor.SunsetSensor;
import smarthome.domain.sensor.SwitchSensor;
import smarthome.domain.sensor.externalservices.SunTimeCalculator;
import smarthome.domain.sensor.sensorvalues.*;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.LogRepository;
import smarthome.persistence.RoomRepository;
import smarthome.domain.vo.DeltaVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.persistence.SensorRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class LogServiceImplTest {


    @MockBean
    SensorValueFactory sensorValueFactory;
    @MockBean
    SensorRepository sensorRepository;
    @MockBean
    SunTimeCalculator sunTimeCalculator;
    @MockBean
    LogFactoryImpl logFactory;
    @MockBean
    LogRepository logRepository;

    @Autowired
    LogServiceImpl logService;
    /**
     * Test to verify that IllegalArgumentException is thrown when given null parameters.
     */
    @Test
    void whenGivenNullRepository_constructorThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Repository cannot be null.";
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> new LogServiceImpl(null, deviceRepository, roomRepository,logFactory));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> new LogServiceImpl(logRepository, null, roomRepository,logFactory));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> new LogServiceImpl(logRepository, deviceRepository, null,logFactory));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, ()
                -> new LogServiceImpl(logRepository, deviceRepository, roomRepository,null));
        String result4 = exception4.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
    }

    /**
     * Test to verify that IllegalArgumentException is thrown when given null parameters for addLog method.
     */
    @Test
    void whenGivenNullParameters_addLogThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid parameters";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorValueObject<?> value = mock(SensorValueObject.class);
        SensorIDVO sensor = mock(SensorIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);


        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> service.addLog(null,sensor,deviceID,sensorType));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.addLog(value,null,deviceID,sensorType));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> service.addLog(value,sensor,null,sensorType));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, ()
                -> service.addLog(value,sensor,deviceID,null));
        String result4 = exception4.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
    }

    /**
     * Test to verify that IllegalArgumentException is thrown when the log factory throws an IllegalArgumentException.
     */
    @Test
    void whenLogFactoryThrowsIllegalArgument_addLogPropagatesIllegalArgumentException() {
        // Arrange
        String expected = "Invalid parameters";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorValueObject<?> value = mock(SensorValueObject.class);
        SensorIDVO sensor = mock(SensorIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        IllegalArgumentException exception = mock(IllegalArgumentException.class);

        when(exception.getMessage()).thenReturn(expected);

        when(logFactory.createLog(value, sensor, deviceID, sensorType)).thenThrow(exception);


        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        // Act
        Exception exceptionResult = assertThrows(IllegalArgumentException.class, ()
                -> service.addLog(value,sensor,deviceID,sensorType));
        String result = exceptionResult.getMessage();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * Test to verify that when the log is unsuccessfully saved, the method addLog returns an Optional empty.
     */
    @Test
    void whenSaveReturnsFalse_addLogReturnsOptionalEmpty() {
        // Arrange

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorValueObject<?> value = mock(SensorValueObject.class);
        SensorIDVO sensor = mock(SensorIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Log log = mock(Log.class);

        when(logFactory.createLog(value, sensor, deviceID, sensorType)).thenReturn(log);
        when(logRepository.save(log)).thenReturn(false);

        Optional<Log> expected = Optional.empty();
        // Act
        Optional<Log> result = service.addLog(value, sensor, deviceID, sensorType);

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that when the log is successfully saved, the method addLog returns an Optional
     * with the created log.
     */
    @Test
    void whenAddLogSuccessfullyCreatesAndSavesALog_returnsOptionalWithCreatedLog() {
        // Arrange

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorValueObject<?> value = mock(SensorValueObject.class);
        SensorIDVO sensor = mock(SensorIDVO.class);
        SensorTypeIDVO sensorType = mock(SensorTypeIDVO.class);

        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Log log = mock(Log.class);

        when(logFactory.createLog(value, sensor, deviceID, sensorType)).thenReturn(log);
        when(logRepository.save(log)).thenReturn(true);

        Optional<Log> expected = Optional.of(log);
        // Act
        Optional<Log> result = service.addLog(value, sensor, deviceID, sensorType);

        // Assert
        assertEquals(expected, result);
    }

        /**
         * Test to verify that IllegalArgumentException is thrown when given null parameters.
         */
    @Test
    void whenGivenNullParameters_findReadingsThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid parameters";
        String expectedTimeStamp = "Invalid time stamps";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> service.findReadingsFromDevice(null,initialTime,finalTime));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.findReadingsFromDevice(deviceID,null, finalTime));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> service.findReadingsFromDevice(null,null, null));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, ()
                -> service.findReadingsFromDevice(deviceID,initialTime, null));
        String result4 = exception4.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expectedTimeStamp,result2);
        assertEquals(expected,result3);
        assertEquals(expectedTimeStamp,result4);
    }

    /**
     * Test case to verify that when given invalid timestamps, the method findReadingsFromDeviceInATimePeriod
     * throws an IllegalArgumentException.
     */
    @Test
    void whenGivenInvalidTimeStamps_findReadingsThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid time stamps";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        LocalDateTime from = mock(LocalDateTime.class);
        LocalDateTime to = mock(LocalDateTime.class);
        LocalDateTime placeholder = mock(LocalDateTime.class);

        when(from.isAfter(to)).thenReturn(true);
        when(to.isAfter(placeholder)).thenReturn(true);

        when(initialTime.getValue()).thenReturn(from);
        when(finalTime.getValue()).thenReturn(to);

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> service.findReadingsFromDevice(deviceID,initialTime,finalTime));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.findReadingsFromDevice(deviceID,initialTime,finalTime));
        String result2 = exception2.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
    }

    /**
     * Test case to verify that when the repository throws an exception while attempting to find logs by device ID and
     * time frame, the method findReadingsFromDeviceInATimePeriod throws an IllegalArgumentException.
     */
    @Test
    void whenRepositoryThrowsException_FindLogByDeviceIDAndTimeFrameThrowsIllegalArgumentException(){
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        LocalDateTime from = mock(LocalDateTime.class);
        LocalDateTime to = mock(LocalDateTime.class);
        LocalDateTime placeholder = mock(LocalDateTime.class);
        when(from.isAfter(to)).thenReturn(false);
        when(to.isAfter(placeholder)).thenReturn(false);
        when(initialTime.getValue()).thenReturn(from);
        when(finalTime.getValue()).thenReturn(to);

        when(logRepository.findReadingsByDeviceID(deviceID,initialTime,finalTime))
                .thenThrow(new IllegalArgumentException("Invalid parameters"));

        List<Log> expected = Collections.emptyList();
        // Act
        List<Log> result = service.findReadingsFromDevice(deviceID,initialTime,finalTime);

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that when logs are found within a specified time frame for a given device ID,
     * the method findReadingsFromDeviceInATimePeriod successfully returns a list of logs.
     */
    @Test
    void whenLogsAreFound_FindLogByDeviceIDAndTimeFrameSuccessfullyReturnsAListOfLogs(){
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        LocalDateTime from = mock(LocalDateTime.class);
        LocalDateTime to = mock(LocalDateTime.class);
        LocalDateTime placeholder = mock(LocalDateTime.class);
        when(from.isAfter(to)).thenReturn(false);
        when(to.isAfter(placeholder)).thenReturn(false);
        when(initialTime.getValue()).thenReturn(from);
        when(finalTime.getValue()).thenReturn(to);

        Log log1 = mock(Log.class);
        Log log2 = mock(Log.class);
        List<Log> expected = new ArrayList<>();
        expected.add(log1);
        expected.add(log2);

        when(logRepository.findReadingsByDeviceID(deviceID,initialTime,finalTime))
                .thenReturn(expected);

        // Act
        List<Log> result = service.findReadingsFromDevice(deviceID,initialTime,finalTime);

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the behavior of findReadingsFromDevices() in service
     * when an existing device is provided with no timestamps. It should return all device readings.
     * <p>
     * This test case sets up a mock environment where the log repository returns a list of logs
     * when queried with an existing device ID and no time constraints. The test then calls the
     * {@code findReadingsFromDevice} method of {@code LogServiceImpl} and asserts that the returned
     * list matches the expected list of logs.
     */
    @Test
    void findReadings_WhenExistingDeviceAndNoTimeStamps_ShouldReturnAllDeviceReadings(){
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);


        Log log1 = mock(Log.class);
        Log log2 = mock(Log.class);
        List<Log> expected = new ArrayList<>();
        expected.add(log1);
        expected.add(log2);

        when(logRepository.findReadingsByDeviceID(deviceID,null,null))
                .thenReturn(expected);

        // Act
        List<Log> result = service.findReadingsFromDevice(deviceID,null,null);

        // Assert
        assertEquals(expected,result);
    }


    /**
     * Test case to verify the behavior of findReadingsFromDevices() in service
     * when a non-existing device is provided with no timestamps. It should an empty list.
     * <p>
     * This test case sets up a mock environment where the log repository returns an empty list
     * when queried for logs associated with a given device ID and no time constraints. The test then calls the
     * {@code findReadingsFromDevice} method of {@code LogServiceImpl} and asserts that the returned
     * list is empty.
     */

    @Test
    void findReadings_WhenNonExistingDeviceAndNoTimeStamps_ShouldReturnEmptyListOfReadings(){
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        when(logRepository.findReadingsByDeviceID(deviceID,null,null))
                .thenReturn(Collections.emptyList());

        // Act
        List<Log> result = service.findReadingsFromDevice(deviceID,null,null);

        // Assert
        assertTrue(result.isEmpty());
    }


    /**
     * Test to verify that IllegalArgumentException is thrown when given null outdoor device.
     */
    @Test
    void whenOutDoorDeviceIsNull_ThrowIllegalArgumentException(){
        // Arrange
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                logService.getMaxInstantaneousTempDifference(null, deviceIdInt, initialTime,
                                                                finalTime, delta));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test to verify that IllegalArgumentException is thrown when given null indoor device.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_IfInDoorDeviceIsNull_ThrowIllegalArgumentException(){
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                logService.getMaxInstantaneousTempDifference(deviceIdOut, null, initialTime,
                                                                finalTime, delta));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }


    /**
     * Test to verify that IllegalArgumentException is thrown when given null time configuration.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_IfTimeConfigIsNull_ThrowIllegalArgumentException(){
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, null,
                                                                finalTime, delta));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialTime,
                                                    null, delta));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialTime,
                                                                finalTime, null));
        String result = exception.getMessage();
        String result2 = exception2.getMessage();
        String result3 = exception3.getMessage();

        // Assert
        assertEquals(expected,result);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
    }

    /**
     * Test to verify that IllegalArgumentException is thrown when given null indoor device.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_ifInDoorDeviceIsNull_ThenThrowIllegalArgumentException(){
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                logService.getMaxInstantaneousTempDifference(deviceIdOut, null, initialTime,
                                                                finalTime, delta));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test to verify that IllegalArgumentException is thrown when the outdoor device is indoor.
     * The test mocks the device repository and room repository to return a room with a height of 1.0, which indicates
     * that the device is indoor.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_IfOutDoorDeviceIsIndoor_ThenThrowIllegalArgumentException() {
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Device deviceOut = mock(Device.class);
        when(deviceRepository.findById(deviceIdOut)).thenReturn(deviceOut);

        RoomIDVO roomIdOut = mock(RoomIDVO.class);
        when(deviceOut.getRoomID()).thenReturn(roomIdOut);

        Room roomOut = mock(Room.class);
        when(roomRepository.findById(roomIdOut)).thenReturn(roomOut);

        RoomDimensionsVO roomDimensionsOut = mock(RoomDimensionsVO.class);
        when(roomOut.getRoomDimensions()).thenReturn(roomDimensionsOut);
        when(roomOut.getRoomDimensions().getRoomHeight()).thenReturn(1.0);

        String expectedMessage = "Invalid Device Location";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialTime, finalTime, delta));

        String result = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Test to verify that IllegalArgumentException is thrown when the indoor device is outdoor.
     * The test mocks the device repository and room repository to return a room with a height of 0.0, which indicates
     * that the device is outdoor.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_IfInDoorDeviceIsOutdoor_ThenThrowIllegalArgumentException() {
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Device deviceOut = mock(Device.class);
        when(deviceRepository.findById(deviceIdOut)).thenReturn(deviceOut);

        RoomIDVO roomIdOut = mock(RoomIDVO.class);
        when(deviceOut.getRoomID()).thenReturn(roomIdOut);

        Room roomOut = mock(Room.class);
        when(roomRepository.findById(roomIdOut)).thenReturn(roomOut);

        RoomDimensionsVO roomDimensionsOut = mock(RoomDimensionsVO.class);
        when(roomOut.getRoomDimensions()).thenReturn(roomDimensionsOut);
        when(roomOut.getRoomDimensions().getRoomHeight()).thenReturn(0.0);

        Device deviceIn = mock(Device.class);
        when(deviceRepository.findById(deviceIdInt)).thenReturn(deviceIn);

        RoomIDVO roomIdIn = mock(RoomIDVO.class);
        when(deviceIn.getRoomID()).thenReturn(roomIdIn);

        Room roomIn = mock(Room.class);
        when(roomRepository.findById(roomIdIn)).thenReturn(roomIn);

        RoomDimensionsVO roomDimensionsIn = mock(RoomDimensionsVO.class);
        when(roomIn.getRoomDimensions()).thenReturn(roomDimensionsIn);
        when(roomIn.getRoomDimensions().getRoomHeight()).thenReturn(0.0);


        String expectedMessage = "Invalid Device Location";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialTime, finalTime, delta));

        String result = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, result);
    }


    /**
     * Test to verify that returns an error message when there are no records available for the given period in
     * the outdoor device's log.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_ifOutDoorDeviceLogEmpty_ThenReturnsStringMessage() {
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Device deviceOut = mock(Device.class);
        when(deviceRepository.findById(deviceIdOut)).thenReturn(deviceOut);

        RoomIDVO roomIdOut = mock(RoomIDVO.class);
        when(deviceOut.getRoomID()).thenReturn(roomIdOut);

        Room roomOut = mock(Room.class);
        when(roomRepository.findById(roomIdOut)).thenReturn(roomOut);

        RoomDimensionsVO roomDimensionsOut = mock(RoomDimensionsVO.class);
        when(roomOut.getRoomDimensions()).thenReturn(roomDimensionsOut);
        when(roomOut.getRoomDimensions().getRoomHeight()).thenReturn(0.0);

        Device deviceIn = mock(Device.class);
        when(deviceRepository.findById(deviceIdInt)).thenReturn(deviceIn);

        RoomIDVO roomIdIn = mock(RoomIDVO.class);
        when(deviceIn.getRoomID()).thenReturn(roomIdIn);

        Room roomIn = mock(Room.class);
        when(roomRepository.findById(roomIdIn)).thenReturn(roomIn);

        RoomDimensionsVO roomDimensionsIn = mock(RoomDimensionsVO.class);
        when(roomIn.getRoomDimensions()).thenReturn(roomDimensionsIn);
        when(roomIn.getRoomDimensions().getRoomHeight()).thenReturn(1.0);

        LocalDateTime initialDateTime = mock(LocalDateTime.class);
        when(initialTime.getValue()).thenReturn(initialDateTime);

        LocalDateTime finalDateTime = mock(LocalDateTime.class);
        when(finalTime.getValue()).thenReturn(finalDateTime);

        int deltaMin=5;
        when(delta.getValue()).thenReturn(deltaMin);

        String sensorType = "TemperatureSensor";

        when(logRepository.getDeviceTemperatureLogs(deviceIdOut, sensorType, initialTime, finalTime))
                .thenReturn(Collections.emptyList());

        String expectedMessage = "There are no records available for the given period";

        // Act
        String result = logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialTime,
                                                                        finalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Test to verify that returns an error message when there are no records available for the given period in
     * the indoor device's log.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_ifInDoorDeviceLogEmpty_ThenReturnsStringMessage() {
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Device deviceOut = mock(Device.class);
        when(deviceRepository.findById(deviceIdOut)).thenReturn(deviceOut);

        RoomIDVO roomIdOut = mock(RoomIDVO.class);
        when(deviceOut.getRoomID()).thenReturn(roomIdOut);

        Room roomOut = mock(Room.class);
        when(roomRepository.findById(roomIdOut)).thenReturn(roomOut);

        RoomDimensionsVO roomDimensionsOut = mock(RoomDimensionsVO.class);
        when(roomOut.getRoomDimensions()).thenReturn(roomDimensionsOut);
        when(roomOut.getRoomDimensions().getRoomHeight()).thenReturn(0.0);

        Device deviceIn = mock(Device.class);
        when(deviceRepository.findById(deviceIdInt)).thenReturn(deviceIn);

        RoomIDVO roomIdIn = mock(RoomIDVO.class);
        when(deviceIn.getRoomID()).thenReturn(roomIdIn);

        Room roomIn = mock(Room.class);
        when(roomRepository.findById(roomIdIn)).thenReturn(roomIn);

        RoomDimensionsVO roomDimensionsIn = mock(RoomDimensionsVO.class);
        when(roomIn.getRoomDimensions()).thenReturn(roomDimensionsIn);
        when(roomIn.getRoomDimensions().getRoomHeight()).thenReturn(1.0);

        LocalDateTime initialDateTime = mock(LocalDateTime.class);
        when(initialTime.getValue()).thenReturn(initialDateTime);

        LocalDateTime finalDateTime = mock(LocalDateTime.class);
        when(finalTime.getValue()).thenReturn(finalDateTime);

        int deltaMin=5;
        when(delta.getValue()).thenReturn(deltaMin);

        String sensorType = "TemperatureSensor";

        Log log1 = mock(Log.class);
        Log log2 = mock(Log.class);

        logRepository.save(log1);
        logRepository.save(log2);

        List<Log> outdoorLogs = Arrays.asList(log1, log2);

        when(logRepository.getDeviceTemperatureLogs(deviceIdOut, sensorType, initialTime, finalTime))
                .thenReturn(outdoorLogs);

        when(logRepository.getDeviceTemperatureLogs(deviceIdInt, sensorType, initialTime, finalTime))
                .thenReturn(Collections.emptyList());

        String expectedMessage = "There are no records available for the given period";

        // Act
        String result = logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialTime,
                                                                        finalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }


    /**
     * Test to verify that returns an error message when there are no records available for the given period.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_ifLogEntriesAreOutSideTheTimeWindowProvided_ThenReturnsStringMessage() {
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);
        DeltaVO delta = mock(DeltaVO.class);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Device deviceOut = mock(Device.class);
        when(deviceRepository.findById(deviceIdOut)).thenReturn(deviceOut);

        RoomIDVO roomIdOut = mock(RoomIDVO.class);
        when(deviceOut.getRoomID()).thenReturn(roomIdOut);

        Room roomOut = mock(Room.class);
        when(roomRepository.findById(roomIdOut)).thenReturn(roomOut);

        RoomDimensionsVO roomDimensionsOut = mock(RoomDimensionsVO.class);
        when(roomOut.getRoomDimensions()).thenReturn(roomDimensionsOut);
        when(roomOut.getRoomDimensions().getRoomHeight()).thenReturn(0.0);

        Device deviceIn = mock(Device.class);
        when(deviceRepository.findById(deviceIdInt)).thenReturn(deviceIn);

        RoomIDVO roomIdIn = mock(RoomIDVO.class);
        when(deviceIn.getRoomID()).thenReturn(roomIdIn);

        Room roomIn = mock(Room.class);
        when(roomRepository.findById(roomIdIn)).thenReturn(roomIn);

        RoomDimensionsVO roomDimensionsIn = mock(RoomDimensionsVO.class);
        when(roomIn.getRoomDimensions()).thenReturn(roomDimensionsIn);
        when(roomIn.getRoomDimensions().getRoomHeight()).thenReturn(1.0);

        LocalDateTime t1 = LocalDateTime.parse("2024-02-25T21:50:00");
        TimeStampVO time1 = mock(TimeStampVO.class);
        when(time1.getValue()).thenReturn(t1);

        LocalDateTime t2 = LocalDateTime.parse("2024-02-25T22:50:00");
        TimeStampVO time2 = mock(TimeStampVO.class);
        when(time2.getValue()).thenReturn(t2);

        int deltaMin=5;
        when(delta.getValue()).thenReturn(deltaMin);

        String sensorType = "TemperatureSensor";

        Log log1 = mock(Log.class);
        when(log1.getTime()).thenReturn(time1);
        Log log2 = mock(Log.class);
        when(log2.getTime()).thenReturn(time2);

        logRepository.save(log1);
        logRepository.save(log2);

        List<Log> outdoorLogs = Arrays.asList(log1, log2);

        LocalDateTime t3 = LocalDateTime.parse("2024-02-25T22:00:00");
        TimeStampVO time3 = mock(TimeStampVO.class);
        when(time3.getValue()).thenReturn(t3);

        LocalDateTime t4 = LocalDateTime.parse("2024-02-25T23:00:00");
        TimeStampVO time4 = mock(TimeStampVO.class);
        when(time4.getValue()).thenReturn(t4);

        Log log3 = mock(Log.class);
        when(log3.getTime()).thenReturn(time3);
        Log log4 = mock(Log.class);
        when(log4.getTime()).thenReturn(time4);

        logRepository.save(log3);
        logRepository.save(log4);

        List<Log> indoorLogs = Arrays.asList(log3, log4);

        LocalDateTime userInitTime = LocalDateTime.parse("2024-01-25T20:45:00");
        TimeStampVO userInitialTime = mock(TimeStampVO.class);
        when(userInitialTime.getValue()).thenReturn(userInitTime);

        LocalDateTime userFinTime = LocalDateTime.parse("2024-05-01T23:45:00");
        TimeStampVO userFinalTime = mock(TimeStampVO.class);
        when(userFinalTime.getValue()).thenReturn(userFinTime);

        when(logRepository.getDeviceTemperatureLogs(deviceIdOut, sensorType, userInitialTime, userFinalTime))
                .thenReturn(outdoorLogs);

        when(logRepository.getDeviceTemperatureLogs(deviceIdInt, sensorType, userInitialTime, userFinalTime))
                .thenReturn(indoorLogs);


        String expectedMessage = "Readings were found within the provided time span, but with no matches within the " +
                "delta provided";

        // Act
        String result = logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, userInitialTime,
                                                                        userFinalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Success test case for getMaxInstantaneousTempDifference method. Tests that when all there are logs available
     * within the time frame provided, and the delta allows for a match, the method returns the correct message.
     */
    @Test
    void whenGetMaxInstantaneousTempDifferenceIsCalled_ifLogEntriesAreInSideTheTimeWindowProvided_ThenReturnsStringMessage() {
        // Arrange
        DeviceIDVO deviceIdOut = mock(DeviceIDVO.class);
        DeviceIDVO deviceIdInt = mock(DeviceIDVO.class);

        DeltaVO delta = mock(DeltaVO.class);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        Device deviceOut = mock(Device.class);
        when(deviceRepository.findById(deviceIdOut)).thenReturn(deviceOut);

        RoomIDVO roomIdOut = mock(RoomIDVO.class);
        when(deviceOut.getRoomID()).thenReturn(roomIdOut);

        Room roomOut = mock(Room.class);
        when(roomRepository.findById(roomIdOut)).thenReturn(roomOut);

        RoomDimensionsVO roomDimensionsOut = mock(RoomDimensionsVO.class);
        when(roomOut.getRoomDimensions()).thenReturn(roomDimensionsOut);
        when(roomOut.getRoomDimensions().getRoomHeight()).thenReturn(0.0);

        Device deviceIn = mock(Device.class);
        when(deviceRepository.findById(deviceIdInt)).thenReturn(deviceIn);

        RoomIDVO roomIdIn = mock(RoomIDVO.class);
        when(deviceIn.getRoomID()).thenReturn(roomIdIn);

        Room roomIn = mock(Room.class);
        when(roomRepository.findById(roomIdIn)).thenReturn(roomIn);

        RoomDimensionsVO roomDimensionsIn = mock(RoomDimensionsVO.class);
        when(roomIn.getRoomDimensions()).thenReturn(roomDimensionsIn);
        when(roomIn.getRoomDimensions().getRoomHeight()).thenReturn(1.0);

        TimeStampVO initialDateTime = mock(TimeStampVO.class);

        LocalDateTime initialT = LocalDateTime.parse("2024-01-25T23:50:00");

        when(initialDateTime.getValue()).thenReturn(initialT);


        TimeStampVO finalDateTime = mock(TimeStampVO.class);

        LocalDateTime finalT = LocalDateTime.parse("2024-04-25T23:50:00");

        when(finalDateTime.getValue()).thenReturn(finalT);


        int deltaMin=6;
        when(delta.getValue()).thenReturn(deltaMin);

        String sensorType = "TemperatureSensor";

        Log log1 = mock(Log.class);
        TimeStampVO time1 = mock(TimeStampVO.class);
        when(log1.getTime()).thenReturn(time1);
        when(log1.getTime().getValue()).thenReturn(LocalDateTime.parse("2024-02-25T23:50:00"));
        SensorValueObject s3Value = mock(TemperatureValue.class);
        when(s3Value.getValue()).thenReturn(5.0);
        when(log1.getReading()).thenReturn(s3Value);

        Log log2 = mock(Log.class);
        TimeStampVO time2 = mock(TimeStampVO.class);
        when(log2.getTime()).thenReturn(time2);
        when(log2.getTime().getValue()).thenReturn(LocalDateTime.parse("2024-02-25T23:50:00"));
        SensorValueObject s4Value = mock(TemperatureValue.class);
        when(s4Value.getValue()).thenReturn(20.0);
        when(log2.getReading()).thenReturn(s4Value);

        logRepository.save(log1);
        logRepository.save(log2);

        List<Log> outdoorLogs = Arrays.asList(log1, log2);


        Log log3 = mock(Log.class);
        SensorValueObject sValue = mock(TemperatureValue.class);
        TimeStampVO time3 = mock(TimeStampVO.class);
        when(log3.getTime()).thenReturn(time3);
        when(log3.getTime().getValue()).thenReturn(LocalDateTime.parse("2024-02-25T23:55:00"));
        when(s4Value.getValue()).thenReturn(0.0);
        when(log3.getReading()).thenReturn(sValue);

        Log log4 = mock(Log.class);
        TimeStampVO time4 = mock(TimeStampVO.class);
        when(log4.getTime()).thenReturn(time4);
        when(log4.getTime().getValue()).thenReturn(LocalDateTime.parse("2024-02-25T23:45:00"));
        SensorValueObject s2Value = mock(TemperatureValue.class);
        when(s4Value.getValue()).thenReturn(10.0);
        when(log4.getReading()).thenReturn(s2Value);

        logRepository.save(log3);
        logRepository.save(log4);

        List<Log> indoorLogs = Arrays.asList(log3, log4);

        when(logRepository.getDeviceTemperatureLogs(deviceIdOut, sensorType, initialDateTime, finalDateTime))
                .thenReturn(outdoorLogs);

        when(logRepository.getDeviceTemperatureLogs(deviceIdInt, sensorType, initialDateTime, finalDateTime))
                .thenReturn(indoorLogs);

        String expectedMessage = "The Maximum Temperature Difference within the selected Period was of 10.0 Cº which "
                + "happened at 2024-02-25T23:55";

        // Act
        String result = logService.getMaxInstantaneousTempDifference(deviceIdOut, deviceIdInt, initialDateTime,
                                                                        finalDateTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Test case to verify that when given invalid timestamps, attempting to get the maximum instantaneous temperature
     * difference between an outdoor and indoor device throws an IllegalArgumentException.
     */
    @Test
    void whenGivenInvalidTimeStamps_GetMaxTemp_throwsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid time stamps";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        Room outdoorRoom = mock(Room.class);
        RoomDimensionsVO outdoorDimensions = mock(RoomDimensionsVO.class);
        when(outdoorDimensions.getRoomHeight()).thenReturn((double) 0);
        RoomIDVO outdoorRoomID = mock(RoomIDVO.class);

        Room indoorRoom = mock(Room.class);
        RoomIDVO indoorRoomID = mock(RoomIDVO.class);
        RoomDimensionsVO indoorDimensions = mock(RoomDimensionsVO.class);
        when(indoorDimensions.getRoomHeight()).thenReturn((double) 1);

        Device outdoorDevice = mock(Device.class); //Outdoor device
        Device indoorDevice = mock(Device.class); //Indoor device
        DeviceIDVO outdoorDeviceID = mock(DeviceIDVO.class);
        DeviceIDVO indoorDeviceID = mock(DeviceIDVO.class);

        // Arranging condition for isOutdoorDeviceInTheExterior
        when(roomRepository.findById(outdoorRoomID)).thenReturn(outdoorRoom);
        when(outdoorDevice.getRoomID()).thenReturn(outdoorRoomID);
        when(outdoorRoom.getRoomDimensions()).thenReturn(outdoorDimensions);

        // Arranging condition for isIndoorDeviceInTheInterior
        when(roomRepository.findById(indoorRoomID)).thenReturn(indoorRoom);
        when(indoorDevice.getRoomID()).thenReturn(indoorRoomID);
        when(indoorRoom.getRoomDimensions()).thenReturn(indoorDimensions);

        // Arranging behaviour of deviceRepository
        when(deviceRepository.findById(outdoorDeviceID)).thenReturn(outdoorDevice);
        when(deviceRepository.findById(indoorDeviceID)).thenReturn(indoorDevice);

        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        DeltaVO deltaVO = mock(DeltaVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        LocalDateTime from = mock(LocalDateTime.class);
        LocalDateTime to = mock(LocalDateTime.class);
        LocalDateTime placeholder = mock(LocalDateTime.class);

        when(from.isAfter(to)).thenReturn(true);
        when(to.isAfter(placeholder)).thenReturn(true);

        when(initialTime.getValue()).thenReturn(from);
        when(finalTime.getValue()).thenReturn(to);

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> service.getMaxInstantaneousTempDifference(outdoorDeviceID,indoorDeviceID,
                                                              initialTime,finalTime,deltaVO));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.getMaxInstantaneousTempDifference(outdoorDeviceID,indoorDeviceID,
                                                             initialTime,finalTime,deltaVO));
        String result2 = exception2.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
    }

    @Test
    void whenGivenNullParameters_getPeakPowerConsumptionThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid parameters";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        TimeStampVO start = mock(TimeStampVO.class);
        TimeStampVO end = mock(TimeStampVO.class);
        DeltaVO delta = mock(DeltaVO.class);

        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);


        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> service.getPeakPowerConsumption(null,end, delta));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.getPeakPowerConsumption(start, null, delta));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> service.getPeakPowerConsumption(start, end, null));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, ()
                -> service.getPeakPowerConsumption(null, null,null));
        String result4 = exception4.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
    }

    /**
     * Tests the behavior of the getPeakPowerConsumption method in LogServiceImpl
     * when given invalid timestamps, ensuring it throws an IllegalArgumentException
     * with the message "Invalid Time Stamps".
     */
    @Test
    void whenGivenInvalidTimeStamps_getPeakPowerConsumptionThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid time stamps";

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        DeltaVO delta = mock(DeltaVO.class);
        TimeStampVO initialTime = mock(TimeStampVO.class);
        TimeStampVO finalTime = mock(TimeStampVO.class);
        LogServiceImpl service = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        LocalDateTime from = mock(LocalDateTime.class);
        LocalDateTime to = mock(LocalDateTime.class);
        LocalDateTime placeholder = mock(LocalDateTime.class);

        when(from.isAfter(to)).thenReturn(true);
        when(to.isAfter(placeholder)).thenReturn(true);

        when(initialTime.getValue()).thenReturn(from);
        when(finalTime.getValue()).thenReturn(to);

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> service.getPeakPowerConsumption(initialTime,finalTime, delta));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.getPeakPowerConsumption(initialTime,finalTime, delta));
        String result2 = exception2.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
    }

    @Test
    void whenGetPeakPowerConsumptionIsCalled_ifPowerGridMeterDeviceLogEmpty_ThenReturnsAppropriateStringMessage() {
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        TimeStampVO initialTime = mock(TimeStampVO.class);
        when(initialTime.getValue()).thenReturn(LocalDateTime.now().minusHours(2).truncatedTo(ChronoUnit.SECONDS));
        TimeStampVO finalTime = mock(TimeStampVO.class);
        when(finalTime.getValue()).thenReturn(LocalDateTime.now().minusHours(1).truncatedTo(ChronoUnit.SECONDS));
        DeltaVO delta = mock(DeltaVO.class);
        String deviceID = "12345";
        String sensorTypeID = "EnergyConsumptionSensor";


        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(deviceID, sensorTypeID, initialTime, finalTime))
                .thenReturn(Collections.emptyList());

        String expectedMessage = "There are no records available from the Grid Power Meter for the given period";

        // Act
        String result = logService.getPeakPowerConsumption(initialTime, finalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Tests the behavior of getPeakPowerConsumption method in LogServiceImpl
     * when the power grid meter device log is empty for the specified time period.
     * <p>
     * This test ensures that the method returns an appropriate string message
     * when no records are available from the Grid Power Meter for the given period.
     * </p>
     */
    @Test
    void whenGetPeakPowerConsumptionIsCalled_ifPowerSourceDeviceLogEmpty_ThenReturnsPeakGridPowerMeterConsumptionStringMessage() {
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        TimeStampVO initialTime = mock(TimeStampVO.class);
        when(initialTime.getValue()).thenReturn(LocalDateTime.now().minusHours(10).truncatedTo(ChronoUnit.SECONDS));
        TimeStampVO finalTime = mock(TimeStampVO.class);
        when(finalTime.getValue()).thenReturn(LocalDateTime.now().minusHours(1).truncatedTo(ChronoUnit.SECONDS));
        DeltaVO delta = mock(DeltaVO.class);
        String powerGridDeviceID = "12345";
        String sensorTypeID = "EnergyConsumptionSensor";

        LogIDVO powerGridLogID1 = mock(LogIDVO.class);
        when(powerGridLogID1.getID()).thenReturn("123456");
        TimeStampVO powerGridTime1 = mock(TimeStampVO.class);
        when(powerGridTime1.getValue()).thenReturn(LocalDateTime.now().minusHours(2).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading1 = mock(EnergyConsumptionValue.class);
        when(powerGridReading1.getValue()).thenReturn(20);
        DeviceIDVO powerGridDeviceID1 = mock(DeviceIDVO.class);
        when(powerGridDeviceID1.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID1 = mock(SensorIDVO.class);
        when(powerGridSensorID1.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID1 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID1.getID()).thenReturn(sensorTypeID);
        Log powerGridLog1 = mock(Log.class);
        when(powerGridLog1.getReading()).thenReturn(powerGridReading1);
        when(powerGridLog1.getTime()).thenReturn(powerGridTime1);
        when(powerGridLog1.getId()).thenReturn(powerGridLogID1);
        when(powerGridLog1.getDeviceID()).thenReturn(powerGridDeviceID1);
        when(powerGridLog1.getSensorID()).thenReturn(powerGridSensorID1);
        when(powerGridLog1.getSensorTypeID()).thenReturn(powerGridSensorTypeID1);

        LogIDVO powerGridLogID2 = mock(LogIDVO.class);
        when(powerGridLogID2.getID()).thenReturn("123456");
        TimeStampVO powerGridTime2 = mock(TimeStampVO.class);
        when(powerGridTime2.getValue()).thenReturn(LocalDateTime.now().minusHours(3).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading2 = mock(EnergyConsumptionValue.class);
        when(powerGridReading2.getValue()).thenReturn(30);
        DeviceIDVO powerGridDeviceID2 = mock(DeviceIDVO.class);
        when(powerGridDeviceID2.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID2 = mock(SensorIDVO.class);
        when(powerGridSensorID2.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID2 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID2.getID()).thenReturn(sensorTypeID);
        Log powerGridLog2 = mock(Log.class);
        when(powerGridLog2.getReading()).thenReturn(powerGridReading2);
        when(powerGridLog2.getTime()).thenReturn(powerGridTime2);
        when(powerGridLog2.getId()).thenReturn(powerGridLogID2);
        when(powerGridLog2.getDeviceID()).thenReturn(powerGridDeviceID2);
        when(powerGridLog2.getSensorID()).thenReturn(powerGridSensorID2);
        when(powerGridLog2.getSensorTypeID()).thenReturn(powerGridSensorTypeID2);

        LogIDVO powerGridLogID3 = mock(LogIDVO.class);
        when(powerGridLogID3.getID()).thenReturn("123456");
        TimeStampVO powerGridTime3 = mock(TimeStampVO.class);
        when(powerGridTime3.getValue()).thenReturn(LocalDateTime.now().minusHours(3).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading3 = mock(EnergyConsumptionValue.class);
        when(powerGridReading3.getValue()).thenReturn(10);
        DeviceIDVO powerGridDeviceID3 = mock(DeviceIDVO.class);
        when(powerGridDeviceID3.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID3 = mock(SensorIDVO.class);
        when(powerGridSensorID3.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID3 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID3.getID()).thenReturn(sensorTypeID);
        Log powerGridLog3 = mock(Log.class);
        when(powerGridLog3.getReading()).thenReturn(powerGridReading3);
        when(powerGridLog3.getTime()).thenReturn(powerGridTime3);
        when(powerGridLog3.getId()).thenReturn(powerGridLogID3);
        when(powerGridLog3.getDeviceID()).thenReturn(powerGridDeviceID3);
        when(powerGridLog3.getSensorID()).thenReturn(powerGridSensorID3);
        when(powerGridLog3.getSensorTypeID()).thenReturn(powerGridSensorTypeID3);

        LogIDVO powerGridLogID4 = mock(LogIDVO.class);
        when(powerGridLogID4.getID()).thenReturn("123456");
        TimeStampVO powerGridTime4 = mock(TimeStampVO.class);
        when(powerGridTime4.getValue()).thenReturn(LocalDateTime.now().minusHours(3).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading4 = mock(EnergyConsumptionValue.class);
        when(powerGridReading4.getValue()).thenReturn(-3);
        DeviceIDVO powerGridDeviceID4 = mock(DeviceIDVO.class);
        when(powerGridDeviceID4.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID4 = mock(SensorIDVO.class);
        when(powerGridSensorID4.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID4 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID4.getID()).thenReturn(sensorTypeID);
        Log powerGridLog4 = mock(Log.class);
        when(powerGridLog4.getReading()).thenReturn(powerGridReading4);
        when(powerGridLog4.getTime()).thenReturn(powerGridTime4);
        when(powerGridLog4.getId()).thenReturn(powerGridLogID4);
        when(powerGridLog4.getDeviceID()).thenReturn(powerGridDeviceID4);
        when(powerGridLog4.getSensorID()).thenReturn(powerGridSensorID4);
        when(powerGridLog4.getSensorTypeID()).thenReturn(powerGridSensorTypeID4);

        List<Log> powerGridLogs = Arrays.asList(powerGridLog1, powerGridLog2, powerGridLog3, powerGridLog4);

        System.setProperty("Grid Power Meter device", powerGridDeviceID);
        System.setProperty("Grid Power Meter sensor type", sensorTypeID);

        when(logRepository.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(powerGridDeviceID, sensorTypeID, initialTime, finalTime))
                .thenReturn(Collections.emptyList());

        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(powerGridDeviceID, sensorTypeID, initialTime, finalTime))
                .thenReturn(powerGridLogs);

        String expectedMessage = "The Peak Power Consumption from the Grid within the selected Period was " + powerGridReading2.getValue() + " Wh which happened at " + powerGridTime2.getValue() + " (No Power Source Device Logs were found within the selected period)";

        // Act
        String result = logService.getPeakPowerConsumption(initialTime, finalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Tests the behavior of getPeakPowerConsumption method in LogServiceImpl
     * when there are no matching instant readings for the specified time period.
     * <p>
     * This test ensures that the method returns an appropriate string message
     * when readings are found within the provided time span but with no instant matches
     * within the provided delta.
     * </p>
     */
    @Test
    void whenGetPeakPowerConsumptionIsCalled_ifThereAreNoMatchingInstantReadings_ThenReturnsAppropriateStringMessage() {
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        TimeStampVO initialTime = mock(TimeStampVO.class);
        when(initialTime.getValue()).thenReturn(LocalDateTime.now().minusMinutes(50).truncatedTo(ChronoUnit.SECONDS));
        TimeStampVO finalTime = mock(TimeStampVO.class);
        when(finalTime.getValue()).thenReturn(LocalDateTime.now().minusMinutes(1).truncatedTo(ChronoUnit.SECONDS));
        DeltaVO delta = mock(DeltaVO.class);
        when(delta.getValue()).thenReturn(5);
        String powerGridDeviceID = "12345";
        String sensorTypeID = "EnergyConsumptionSensor";

        System.setProperty("Grid Power Meter device", powerGridDeviceID);
        System.setProperty("Grid Power Meter sensor type", sensorTypeID);

        LogIDVO powerGridLogID1 = mock(LogIDVO.class);
        when(powerGridLogID1.getID()).thenReturn("123456");
        TimeStampVO powerGridTime1 = mock(TimeStampVO.class);
        when(powerGridTime1.getValue()).thenReturn(LocalDateTime.now().minusMinutes(40).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading1 = mock(EnergyConsumptionValue.class);
        when(powerGridReading1.getValue()).thenReturn(20);
        DeviceIDVO powerGridDeviceID1 = mock(DeviceIDVO.class);
        when(powerGridDeviceID1.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID1 = mock(SensorIDVO.class);
        when(powerGridSensorID1.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID1 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID1.getID()).thenReturn(sensorTypeID);
        Log powerGridLog1 = mock(Log.class);
        when(powerGridLog1.getReading()).thenReturn(powerGridReading1);
        when(powerGridLog1.getTime()).thenReturn(powerGridTime1);
        when(powerGridLog1.getId()).thenReturn(powerGridLogID1);
        when(powerGridLog1.getDeviceID()).thenReturn(powerGridDeviceID1);
        when(powerGridLog1.getSensorID()).thenReturn(powerGridSensorID1);
        when(powerGridLog1.getSensorTypeID()).thenReturn(powerGridSensorTypeID1);

        LogIDVO powerGridLogID2 = mock(LogIDVO.class);
        when(powerGridLogID2.getID()).thenReturn("12456");
        TimeStampVO powerGridTime2 = mock(TimeStampVO.class);
        when(powerGridTime2.getValue()).thenReturn(LocalDateTime.now().minusMinutes(5).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading2 = mock(EnergyConsumptionValue.class);
        when(powerGridReading2.getValue()).thenReturn(30);
        DeviceIDVO powerGridDeviceID2 = mock(DeviceIDVO.class);
        when(powerGridDeviceID2.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID2 = mock(SensorIDVO.class);
        when(powerGridSensorID2.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID2 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID2.getID()).thenReturn(sensorTypeID);
        Log powerGridLog2 = mock(Log.class);
        when(powerGridLog2.getReading()).thenReturn(powerGridReading2);
        when(powerGridLog2.getTime()).thenReturn(powerGridTime2);
        when(powerGridLog2.getId()).thenReturn(powerGridLogID2);
        when(powerGridLog2.getDeviceID()).thenReturn(powerGridDeviceID2);
        when(powerGridLog2.getSensorID()).thenReturn(powerGridSensorID2);
        when(powerGridLog2.getSensorTypeID()).thenReturn(powerGridSensorTypeID2);

        List<Log> powerGridLogs = List.of(powerGridLog1, powerGridLog2);

        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(powerGridDeviceID, sensorTypeID, initialTime, finalTime))
                .thenReturn(powerGridLogs);

        LogIDVO powerSourceLogID1 = mock(LogIDVO.class);
        when(powerSourceLogID1.getID()).thenReturn("123456");
        TimeStampVO powerSourceTime1 = mock(TimeStampVO.class);
        when(powerSourceTime1.getValue()).thenReturn(LocalDateTime.now().minusMinutes(20).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerSourceReading1 = mock(EnergyConsumptionValue.class);
        when(powerSourceReading1.getValue()).thenReturn(-10);
        DeviceIDVO powerSourceDeviceID1 = mock(DeviceIDVO.class);
        when(powerSourceDeviceID1.getID()).thenReturn("123456789");
        SensorIDVO powerSourceSensorID1 = mock(SensorIDVO.class);
        when(powerSourceSensorID1.getID()).thenReturn("12456789");
        SensorTypeIDVO powerSourceSensorTypeID1 = mock(SensorTypeIDVO.class);
        when(powerSourceSensorTypeID1.getID()).thenReturn(sensorTypeID);
        Log powerSourceLog1 = mock(Log.class);
        when(powerSourceLog1.getReading()).thenReturn(powerSourceReading1);
        when(powerSourceLog1.getTime()).thenReturn(powerSourceTime1);
        when(powerSourceLog1.getId()).thenReturn(powerSourceLogID1);
        when(powerSourceLog1.getDeviceID()).thenReturn(powerSourceDeviceID1);
        when(powerSourceLog1.getSensorID()).thenReturn(powerSourceSensorID1);
        when(powerSourceLog1.getSensorTypeID()).thenReturn(powerSourceSensorTypeID1);

        List<Log> powerSourceLogs = List.of(powerSourceLog1);

        when(logRepository.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(powerGridDeviceID, sensorTypeID, initialTime, finalTime)).thenReturn(powerSourceLogs);

        String expectedMessage = "Readings were found within the provided time span, but with no instant matches within the delta provided";

        // Act
        String result = logService.getPeakPowerConsumption(initialTime, finalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Tests the behavior of getPeakPowerConsumption method in LogServiceImpl
     * when there are matching instant readings for the specified time period.
     * <p>
     * This test ensures that the method returns a successful string message
     * containing the peak power consumption of the house within the selected period.
     * </p>
     */
    @Test
    void whenGetPeakPowerConsumptionIsCalled_ifThereAreMatchingInstantReadings_ThenReturnsSuccessfulStringMessage() {
        // Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);

        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        TimeStampVO initialTime = mock(TimeStampVO.class);
        when(initialTime.getValue()).thenReturn(LocalDateTime.now().minusMinutes(10).truncatedTo(ChronoUnit.SECONDS));
        TimeStampVO finalTime = mock(TimeStampVO.class);
        when(finalTime.getValue()).thenReturn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        DeltaVO delta = mock(DeltaVO.class);
        when(delta.getValue()).thenReturn(5);
        String powerGridDeviceID = "12345";
        String sensorTypeID = "EnergyConsumptionSensor";

        System.setProperty("Grid Power Meter device", powerGridDeviceID);
        System.setProperty("Grid Power Meter sensor type", sensorTypeID);

        LogIDVO powerGridLogID1 = mock(LogIDVO.class);
        when(powerGridLogID1.getID()).thenReturn("123456");
        TimeStampVO powerGridTime1 = mock(TimeStampVO.class);
        when(powerGridTime1.getValue()).thenReturn(LocalDateTime.now().minusMinutes(2).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading1 = mock(EnergyConsumptionValue.class);
        when(powerGridReading1.getValue()).thenReturn(20);
        DeviceIDVO powerGridDeviceID1 = mock(DeviceIDVO.class);
        when(powerGridDeviceID1.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID1 = mock(SensorIDVO.class);
        when(powerGridSensorID1.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID1 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID1.getID()).thenReturn(sensorTypeID);
        Log powerGridLog1 = mock(Log.class);
        when(powerGridLog1.getReading()).thenReturn(powerGridReading1);
        when(powerGridLog1.getTime()).thenReturn(powerGridTime1);
        when(powerGridLog1.getId()).thenReturn(powerGridLogID1);
        when(powerGridLog1.getDeviceID()).thenReturn(powerGridDeviceID1);
        when(powerGridLog1.getSensorID()).thenReturn(powerGridSensorID1);
        when(powerGridLog1.getSensorTypeID()).thenReturn(powerGridSensorTypeID1);

        LogIDVO powerGridLogID2 = mock(LogIDVO.class);
        when(powerGridLogID2.getID()).thenReturn("123456");
        TimeStampVO powerGridTime2 = mock(TimeStampVO.class);
        when(powerGridTime2.getValue()).thenReturn(LocalDateTime.now().minusMinutes(8).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading2 = mock(EnergyConsumptionValue.class);
        when(powerGridReading2.getValue()).thenReturn(30);
        DeviceIDVO powerGridDeviceID2 = mock(DeviceIDVO.class);
        when(powerGridDeviceID2.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID2 = mock(SensorIDVO.class);
        when(powerGridSensorID2.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID2 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID2.getID()).thenReturn(sensorTypeID);
        Log powerGridLog2 = mock(Log.class);
        when(powerGridLog2.getReading()).thenReturn(powerGridReading2);
        when(powerGridLog2.getTime()).thenReturn(powerGridTime2);
        when(powerGridLog2.getId()).thenReturn(powerGridLogID2);
        when(powerGridLog2.getDeviceID()).thenReturn(powerGridDeviceID2);
        when(powerGridLog2.getSensorID()).thenReturn(powerGridSensorID2);
        when(powerGridLog2.getSensorTypeID()).thenReturn(powerGridSensorTypeID2);

        LogIDVO powerGridLogID3 = mock(LogIDVO.class);
        when(powerGridLogID3.getID()).thenReturn("123456");
        TimeStampVO powerGridTime3 = mock(TimeStampVO.class);
        when(powerGridTime3.getValue()).thenReturn(LocalDateTime.now().minusHours(3).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading3 = mock(EnergyConsumptionValue.class);
        when(powerGridReading3.getValue()).thenReturn(30);
        DeviceIDVO powerGridDeviceID3 = mock(DeviceIDVO.class);
        when(powerGridDeviceID3.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID3 = mock(SensorIDVO.class);
        when(powerGridSensorID3.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID3 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID3.getID()).thenReturn(sensorTypeID);
        Log powerGridLog3 = mock(Log.class);
        when(powerGridLog3.getReading()).thenReturn(powerGridReading3);
        when(powerGridLog3.getTime()).thenReturn(powerGridTime3);
        when(powerGridLog3.getId()).thenReturn(powerGridLogID3);
        when(powerGridLog3.getDeviceID()).thenReturn(powerGridDeviceID3);
        when(powerGridLog3.getSensorID()).thenReturn(powerGridSensorID3);
        when(powerGridLog3.getSensorTypeID()).thenReturn(powerGridSensorTypeID3);

        LogIDVO powerGridLogID4 = mock(LogIDVO.class);
        when(powerGridLogID4.getID()).thenReturn("123456");
        TimeStampVO powerGridTime4 = mock(TimeStampVO.class);
        when(powerGridTime4.getValue()).thenReturn(LocalDateTime.now().minusHours(3).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerGridReading4 = mock(EnergyConsumptionValue.class);
        when(powerGridReading4.getValue()).thenReturn(20);
        DeviceIDVO powerGridDeviceID4 = mock(DeviceIDVO.class);
        when(powerGridDeviceID4.getID()).thenReturn(powerGridDeviceID);
        SensorIDVO powerGridSensorID4 = mock(SensorIDVO.class);
        when(powerGridSensorID4.getID()).thenReturn("12456789");
        SensorTypeIDVO powerGridSensorTypeID4 = mock(SensorTypeIDVO.class);
        when(powerGridSensorTypeID4.getID()).thenReturn(sensorTypeID);
        Log powerGridLog4 = mock(Log.class);
        when(powerGridLog4.getReading()).thenReturn(powerGridReading4);
        when(powerGridLog4.getTime()).thenReturn(powerGridTime4);
        when(powerGridLog4.getId()).thenReturn(powerGridLogID4);
        when(powerGridLog4.getDeviceID()).thenReturn(powerGridDeviceID4);
        when(powerGridLog4.getSensorID()).thenReturn(powerGridSensorID4);
        when(powerGridLog4.getSensorTypeID()).thenReturn(powerGridSensorTypeID4);

        List<Log> powerGridLogs = List.of(powerGridLog1, powerGridLog2, powerGridLog3, powerGridLog4);


        when(logRepository.findByDeviceIDAndSensorTypeAndTimeBetween(powerGridDeviceID, sensorTypeID, initialTime, finalTime))
                .thenReturn(powerGridLogs);

        LogIDVO powerSourceLogID1 = mock(LogIDVO.class);
        when(powerSourceLogID1.getID()).thenReturn("123456");
        TimeStampVO powerSourceTime1 = mock(TimeStampVO.class);
        when(powerSourceTime1.getValue()).thenReturn(LocalDateTime.now().minusMinutes(4).truncatedTo(ChronoUnit.SECONDS));
        SensorValueObject powerSourceReading1 = mock(EnergyConsumptionValue.class);
        when(powerSourceReading1.getValue()).thenReturn(-5);
        DeviceIDVO powerSourceDeviceID1 = mock(DeviceIDVO.class);
        when(powerSourceDeviceID1.getID()).thenReturn("8229651651");
        SensorIDVO powerSourceSensorID1 = mock(SensorIDVO.class);
        when(powerSourceSensorID1.getID()).thenReturn("12456789");
        SensorTypeIDVO powerSourceSensorTypeID1 = mock(SensorTypeIDVO.class);
        when(powerSourceSensorTypeID1.getID()).thenReturn(sensorTypeID);
        Log powerSourceLog1 = mock(Log.class);
        when(powerSourceLog1.getReading()).thenReturn(powerSourceReading1);
        when(powerSourceLog1.getTime()).thenReturn(powerSourceTime1);
        when(powerSourceLog1.getId()).thenReturn(powerSourceLogID1);
        when(powerSourceLog1.getDeviceID()).thenReturn(powerSourceDeviceID1);
        when(powerSourceLog1.getSensorID()).thenReturn(powerSourceSensorID1);
        when(powerSourceLog1.getSensorTypeID()).thenReturn(powerSourceSensorTypeID1);

        List<Log> powerSourceLogs = List.of(powerSourceLog1);

        when(logRepository.findByNegativeReadingAndNotDeviceIDAndSensorTypeAndTimeBetween(powerGridDeviceID, powerSourceSensorTypeID1.getID(), initialTime, finalTime)).thenReturn(powerSourceLogs);

        String expectedMessage = "The Peak Power Consumption of the House within the selected Period was of " + Math.subtractExact((int) powerGridReading2.getValue(), (int) powerSourceReading1.getValue()) + " Wh which happened at " +powerGridTime2.getValue();

        // Act
        String result = logService.getPeakPowerConsumption(initialTime, finalTime, delta);

        // Assert
        assertEquals(expectedMessage, result);
    }

    /**
     * Verifies that when any of the parameters (date, gpsLocation, or sensorTypeIDVO) are null,
     * the getSunReading method throws an IllegalArgumentException with the message "Invalid parameters".
     */
    @Test
    void whenGivenNullParams_getSunReadingThrowsIllegalArgumentException() {
        // Arrange
        // Mocking necessary objects
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository,deviceRepository,roomRepository,logFactory);

        String date = "Will not reach";
        String gpsLocation = "Will not reach";
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        String expected = "Invalid parameters";

        // Act and Assert
        // Testing for null date
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(null, gpsLocation, sensorTypeIDVO));
        String result1 = exception1.getMessage();

        // Testing for null gpsLocation
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, null, sensorTypeIDVO));
        String result2 = exception2.getMessage();

        // Testing for null sensorIDVO
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, null));
        String result3 = exception3.getMessage();

        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
    }


    /**
     * Verifies that when the provided SensorTypeIDVO is not one of the two possible, the getSunReading method
     * throws an IllegalArgumentException with the message "Could not find Sensor".
     */
    @Test
    void whenProvidedSensorTypeIDVOIsNotOneOfTheTwoPermitted_getSunReadingThrowsIllegalArgumentException() {
        // Arrange
        // Mocking necessary objects
        String date = "Will not reach";
        String gpsLocation = "Will not reach";
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        when(sensorTypeIDVO.getID()).thenReturn("SwitchSensor");

        // Expected result
        String expected = "Could not find Sensor";

        // Act
        Exception thrownException = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, sensorTypeIDVO));
        String result = thrownException.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that the getSunReading method throws an IllegalArgumentException
     * when the SensorTypeIDVO is related to no sensor, causing a ClassCastException.
     */
    @Test
    void whenNoSensorExistsThatIsOfTheSpecifiedSensorTypeIDVO_getSunReadingThrowsIllegalArgumentExceptionDueToCastException() {
        // Arrange
        // Mocking necessary objects
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        String date = "Irrelevant";
        String gpsLocation = "Irrelevant";

        // Setting up mock behavior
        when(sensorTypeIDVO.getID()).thenReturn("SunriseSensor");
        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.emptyList());

        // Expected result
        String expected = "No Sun Sensors (either Sunrise or Sunset) were found in the system";

        // Act
        Exception thrownException = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, sensorTypeIDVO));
        String result = thrownException.getMessage();

        // Assert
        assertEquals(expected, result);
    }


    /**
     * Verifies that the getSunReading method throws an IllegalArgumentException
     * when the sensor's getReading method returns null.
     */
    @Test
    void whenSensorGetReadingReturnsNull_getSunReadingThrowsIllegalArgumentExceptionDueToNullPointException() {
        // Arrange
        // Mocking necessary objects
        SunsetSensor sensor = mock(SunsetSensor.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        String date = "Irrelevant";
        String gpsLocation = "Irrelevant";

        // Setting up mock behavior
        when(sensorTypeIDVO.getID()).thenReturn("SunriseSensor");
        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));
        when(sensor.getReading(date, gpsLocation, this.sunTimeCalculator, this.sensorValueFactory)).thenReturn(null);

        // Expected result
        String expected = "Unable to save reading";

        // Act
        Exception thrownException = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, sensorTypeIDVO));
        String result = thrownException.getMessage();

        // Assert
        assertEquals(expected, result);
    }


    /**
     * Verifies that the getSunReading method throws an IllegalArgumentException
     * when the LogRepository is unable to save the reading.
     */
    @Test
    void whenLogRepositoryIsUnableToSaveReading_getSunReadingThrowsIllegalArgumentException() {
        // Arrange
        // Mocking necessary objects
        SunsetSensor sensor = mock(SunsetSensor.class);
        SensorIDVO sensorIDVO = mock(SensorIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        Log log = mock(Log.class);
        SunTimeValue reading = mock(SunTimeValue.class);
        String date = "Irrelevant";
        String gpsLocation = "Irrelevant";

        // Setting up mock behavior
        when(sensorTypeIDVO.getID()).thenReturn("SunsetSensor");
        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));
        when(sensor.getReading(date, gpsLocation, this.sunTimeCalculator, this.sensorValueFactory)).thenReturn(reading);
        when(sensor.getId()).thenReturn(sensorIDVO);
        when(sensor.getDeviceID()).thenReturn(deviceIDVO);
        when(sensor.getSensorTypeID()).thenReturn(sensorTypeIDVO);
        when(logFactory.createLog(reading, sensorIDVO, deviceIDVO, sensorTypeIDVO)).thenReturn(log);
        when(logRepository.save(log)).thenReturn(false);

        // Expected result
        String expected = "Unable to save reading";

        // Act
        Exception thrownException = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, sensorTypeIDVO));
        String result = thrownException.getMessage();

        // Assert
        assertEquals(expected, result);
    }


    /**
     * Verifies that the getSunReading method throws an IllegalArgumentException
     * when the LogFactory is unable to produce a log.
     */
    @Test
    void whenLogFactoryIsUnableToProduceLog_getSunReadingThrowsIllegalArgumentException() {
        // Arrange
        // Mocking necessary objects
        SunsetSensor sensor = mock(SunsetSensor.class);
        SensorIDVO sensorIDVO = mock(SensorIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        IllegalArgumentException exception = mock(IllegalArgumentException.class);
        SunTimeValue reading = mock(SunTimeValue.class);
        String date = "Irrelevant";
        String gpsLocation = "Irrelevant";

        // Setting up mock behavior
        when(sensorTypeIDVO.getID()).thenReturn("SunsetSensor");
        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));
        when(sensor.getReading(date, gpsLocation, this.sunTimeCalculator, this.sensorValueFactory)).thenReturn(reading);
        when(sensor.getId()).thenReturn(sensorIDVO);
        when(sensor.getDeviceID()).thenReturn(deviceIDVO);
        when(sensor.getSensorTypeID()).thenReturn(sensorTypeIDVO);
        when(logFactory.createLog(reading, sensorIDVO, deviceIDVO, sensorTypeIDVO)).thenThrow(exception);

        // Expected result
        String expected = "Unable to save reading";

        // Act
        Exception thrownException = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, sensorTypeIDVO));
        String result = thrownException.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that the getSunReading method throws an IllegalArgumentException
     * when a ClassCastException is thrown while casting the sensor.
     */
    @Test
    void whenSensorCannotBeCastToSunSensor_getSunReadingThrowsIllegalArgumentExceptionDueToClassCastException() {
        // Arrange
        // Mocking necessary objects
        Sensor sensor = mock(Sensor.class); // This is not a SunSensor, so it will cause a ClassCastException
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        String date = "Irrelevant";
        String gpsLocation = "Irrelevant";

        // Setting up mock behavior
        when(sensorTypeIDVO.getID()).thenReturn("SunriseSensor");
        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));

        // Expected result
        String expected = "Unable to get sensor reading";

        // Act
        Exception thrownException = assertThrows(IllegalArgumentException.class, () ->
                logService.getSunReading(date, gpsLocation, sensorTypeIDVO));
        String result = thrownException.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies that the getSunReading method returns the reading value as a string
     * when successfully obtaining the reading and saving the log.
     */
    @Test
    void whenSuccessfullyObtainingReadingAndSavingLog_getSunReadingReturnsReadingAsString() {
        // Arrange
        // Mocking necessary objects
        SunSensor sensor = mock(SunSensor.class);
        SensorIDVO sensorIDVO = mock(SensorIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeIDVO = mock(SensorTypeIDVO.class);
        Log log = mock(Log.class);
        SunTimeValue reading = mock(SunTimeValue.class);
        ZonedDateTime value = mock(ZonedDateTime.class);
        String date = "Irrelevant";
        String gpsLocation = "Irrelevant";

        // Setting up mock behavior
        when(sensorTypeIDVO.getID()).thenReturn("SunsetSensor");
        when(sensorRepository.findBySensorTypeId(sensorTypeIDVO)).thenReturn(Collections.singletonList(sensor));
        when(sensor.getReading(date, gpsLocation, this.sunTimeCalculator, this.sensorValueFactory)).thenReturn(reading);
        when(sensor.getId()).thenReturn(sensorIDVO);
        when(sensor.getDeviceID()).thenReturn(deviceIDVO);
        when(sensor.getSensorTypeID()).thenReturn(sensorTypeIDVO);
        when(logFactory.createLog(reading, sensorIDVO, deviceIDVO, sensorTypeIDVO)).thenReturn(log);
        when(logRepository.save(log)).thenReturn(true);
        when(reading.getValue()).thenReturn(value);
        when(value.toString()).thenReturn("It works");

        // Expected result
        String expected = "It works";

        // Act
        String result = logService.getSunReading(date, gpsLocation, sensorTypeIDVO);

        // Assert
        assertEquals(expected, result);
    }

}
