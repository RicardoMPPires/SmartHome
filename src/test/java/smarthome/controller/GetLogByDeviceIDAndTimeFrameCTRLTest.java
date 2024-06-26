package smarthome.controller;

import org.junit.jupiter.api.Test;
import smarthome.domain.log.Log;
import smarthome.domain.log.LogFactory;
import smarthome.domain.sensor.sensorvalues.HumidityValue;
import smarthome.domain.sensor.sensorvalues.TemperatureValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.LogDTO;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.LogRepository;
import smarthome.persistence.RoomRepository;
import smarthome.service.LogServiceImpl;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.utils.timeconfig.TimeConfigDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetLogByDeviceIDAndTimeFrameCTRLTest {

    /**
     * Tests the scenario when a null LogService is provided to the constructor of GetLogByDeviceIDAndTimeFrameCTRL,
     * expecting an IllegalArgumentException to be thrown.
     * <p>
     * This test method verifies that when a null LogService is provided to the constructor of
     * GetLogByDeviceIDAndTimeFrameCTRL, an IllegalArgumentException is thrown with an appropriate error message. It
     * arranges the expected error message, instantiates a GetLogByDeviceIDAndTimeFrameCTRL object with a null
     * LogService within an assertThrows block to capture the thrown exception, and retrieves the actual error message
     * from the thrown exception. Finally, it verifies that the thrown exception is of type IllegalArgumentException
     * and that its message matches the expected one.
     */
    @Test
    void whenLogServiceIsNull_thenIllegalArgumentExceptionIsThrown() {
        //Arrange
        String expected = "LogService cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> new GetLogByDeviceIDAndTimeFrameCTRL(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Tests the scenario when an invalid DeviceDTO is provided to the GetLogByDeviceIDAndTimeFrameCTRL controller
     * method, expecting IllegalArgumentExceptions to be thrown with appropriate error messages.
     * <p>
     * This test method verifies that when different types of invalid DeviceDTO objects are provided to the
     * GetLogByDeviceIDAndTimeFrameCTRL controller method, IllegalArgumentExceptions are thrown with specific error
     * messages. It arranges the necessary mock objects and controller instance, as well as invalid DeviceDTO objects
     * with various types of invalid device IDs (including null, invalid format, blank, and non-matching format). Then,
     * it invokes the controller method with each invalid DeviceDTO object within separate assertThrows blocks to
     * capture the thrown exceptions. Finally, it retrieves the actual error messages from the thrown exceptions and
     * asserts that they match the expected messages.
     */
    @Test
    void whenGivenInvalidDeviceDTO_thenIllegalArgumentExceptionIsThrown() {
        //Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        GetLogByDeviceIDAndTimeFrameCTRL getLogByDeviceIDAndTimeFrameCTRL =
                new GetLogByDeviceIDAndTimeFrameCTRL(logService);

        String iDate = "2024-04-23";
        String iTime = "16:00";
        String eDate = "2024-04-23";
        String eTime = "18:00";
        TimeConfigDTO timeConfigDTO = new TimeConfigDTO(iDate,iTime,eDate,eTime,null);

        String placeHolder = "noImpact";

        String deviceID1 = "Fail";
        String deviceID2 = "135-6";
        String deviceID3 = " ";

        DeviceDTO deviceDTO1 = new DeviceDTO(deviceID1,placeHolder,placeHolder,placeHolder,placeHolder);
        DeviceDTO deviceDTO2 = new DeviceDTO(deviceID2,placeHolder,placeHolder,placeHolder,placeHolder);
        DeviceDTO deviceDTO3 = new DeviceDTO(deviceID3,placeHolder,placeHolder,placeHolder,placeHolder);
        DeviceDTO deviceDTO4 = new DeviceDTO(null,placeHolder,placeHolder,placeHolder,placeHolder);

        String controllerException = "DeviceDTO cannot be null.";
        String mapperException = "Invalid device ID";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(null, timeConfigDTO));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO1, timeConfigDTO));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO2, timeConfigDTO));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO3, timeConfigDTO));
        String result4 = exception4.getMessage();

        Exception exception5 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO4, timeConfigDTO));
        String result5 = exception5.getMessage();


        //Assert
        assertEquals(controllerException, result1); // DeviceDTO is null
        assertEquals(mapperException, result2); // DeviceID string has invalid string "Fail"
        assertEquals(mapperException, result3); // DeviceID string has int not correspondent to UUUI
        assertEquals(mapperException, result4); // DeviceID string is blank
        assertEquals(mapperException, result5); // DeviceID is null
    }

    /**
     * Tests the scenario when an invalid TimeConfigDTO is provided to the GetLogByDeviceIDAndTimeFrameCTRL controller
     * method, expecting IllegalArgumentExceptions to be thrown with appropriate error messages.
     * <p>
     * This test method verifies that when different types of invalid TimeConfigDTO objects are provided to the
     * GetLogByDeviceIDAndTimeFrameCTRL controller method, IllegalArgumentExceptions are thrown with specific error
     * messages. It arranges the necessary mock objects and controller instance, as well as an invalid DeviceDTO object
     * and invalid TimeConfigDTO objects with various types of invalid parameters (including null, invalid date/time
     * format, and initial date/time after end date/time). Then, it invokes the controller method with each invalid
     * TimeConfigDTO object within separate assertThrows blocks to capture the thrown exceptions. Finally, it retrieves
     * the actual error messages from the thrown exceptions and asserts that they match the expected messages.
     */
    @Test
    void whenGivenInvalidTimeConfig_thenIllegalArgumentExceptionIsThrown() {
        //Arrange
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);

        GetLogByDeviceIDAndTimeFrameCTRL getLogByDeviceIDAndTimeFrameCTRL =
                new GetLogByDeviceIDAndTimeFrameCTRL(logService);

        String deviceID = "f2e4113e-1b76-4eaa-8b4e-bb391b62e23e";
        String deviceName = "device1";
        String deviceModel = "model1";
        String deviceStatus = "status";
        String roomID = "rid";
        DeviceDTO deviceDTO = new DeviceDTO(deviceID,deviceName,deviceModel,deviceStatus,roomID);
        String controllerError = "Invalid TimeConfigDTO";
        String timeStampError = "Invalid date/time entries";
        String dateLogicError = "Invalid time stamps";

        String iDate = "2024-04-23";
        String iTime = "19:00";
        String eDate = "2024-04-22";
        String eTime = "18:00";

        TimeConfigDTO timeConfigDTO1 = new TimeConfigDTO(null,null,null,null,null);
        TimeConfigDTO timeConfigDTO2 = new TimeConfigDTO(iDate,iTime,eDate,"fail",null);
        TimeConfigDTO timeConfigDTO3 = new TimeConfigDTO("fail",iTime,eDate,eTime,null);
        TimeConfigDTO timeConfigDTO4 = new TimeConfigDTO(iDate, iTime, eDate, eTime,null);

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO, null));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO, timeConfigDTO1));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO, timeConfigDTO2));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO, timeConfigDTO3));
        String result4 = exception4.getMessage();

        Exception exception5 = assertThrows(IllegalArgumentException.class, ()
                -> getLogByDeviceIDAndTimeFrameCTRL.getLogByDeviceIDAndTimeFrame(deviceDTO, timeConfigDTO4));
        String result5 = exception5.getMessage();


        //Assert
        assertEquals(controllerError, result1); // TimeConfig is Null
        assertEquals(timeStampError, result2); // TimeConfig has all parameters null
        assertEquals(timeStampError, result3); // TimeConfig has end time null
        assertEquals(timeStampError, result4); // TimeConfig has initial date null
        assertEquals(dateLogicError, result5); // TimeConfig has initial time stamp after end time stamp
    }

    /**
     * Tests the scenario when a log associated with a device has no readings within the specified time frame,
     * expecting an empty list to be returned.
     * <p>
     * This test method verifies that when there are no device readings found within the specified time frame,
     * the GetLogByDeviceIDAndTimeFrameCTRL controller method returns an empty list of LogDTO objects.
     * It arranges the necessary mock objects including a DeviceDTO and TimeConfigDTO representing the device and time
     * frame, respectively. Additionally, it mocks the behavior of the LogRepository to return an empty list when
     * queried for logs within the specified time frame. Then, it invokes the controller method to get logs by device
     * ID and time frame, and asserts that the returned result is an empty list.
     */
    @Test
    void whenLogHasNoDeviceReadings_thenEmptyListIsReturned() {
        //Arrange
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        String deviceID = deviceIDVO.getID();
        String deviceName = "device1";
        String deviceModel = "model1";
        String deviceStatus = "status";
        String roomID = "rid";
        DeviceDTO deviceDTO = new DeviceDTO(deviceID,deviceName,deviceModel,deviceStatus,roomID);

        String iDate = "2024-04-23";
        String iTime = "16:00";
        String eDate = "2024-04-23";
        String eTime = "18:00";
        TimeConfigDTO timeConfigDTO = new TimeConfigDTO(iDate,iTime,eDate,eTime,null);

        LocalDateTime initalT = LocalDateTime.parse("2024-04-23T16:00");
        LocalDateTime finalT = LocalDateTime.parse("2024-04-23T18:00");

        TimeStampVO initalTimeStamp = new TimeStampVO(initalT);
        TimeStampVO finalTimeStamp = new TimeStampVO(finalT);

        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        when(logRepository.findReadingsByDeviceID(deviceIDVO, initalTimeStamp, finalTimeStamp))
                .thenReturn(new ArrayList<>());

        GetLogByDeviceIDAndTimeFrameCTRL ctrl = new GetLogByDeviceIDAndTimeFrameCTRL(logService);

        // Act
        Iterable<LogDTO> result = ctrl.getLogByDeviceIDAndTimeFrame(deviceDTO,timeConfigDTO);

        // Assert
        assertEquals(new ArrayList<>(),result);
    }

    /**
     * Tests the scenario when a log associated with a device has two readings within the specified time frame,
     * expecting an iterable of size two and accessible log attributes.
     * <p>
     * This test method verifies that when there are two device readings found within the specified time frame,
     * the GetLogByDeviceIDAndTimeFrameCTRL controller method returns an iterable of LogDTO objects,
     * where each LogDTO represents a device reading. It arranges the necessary mock objects including
     * a DeviceDTO and TimeConfigDTO representing the device and time frame, respectively. Additionally, it creates
     * mock Log objects with associated log details and adds them to a list. It then mocks the behavior of the
     * LogRepository to return this list of logs when queried for logs within the specified time frame. The controller
     * method is then invoked to get logs by device ID and time frame, and the returned iterable is converted into a
     * list for individual LogDTO assessment. Finally, it asserts that the attributes of each LogDTO match the expected
     * log details.
     */
    @Test
    void whenLogHasTwoDeviceReadings_returnsIterableSizeTwoAndLogsAreAccessible() {
        //Arrange

        // Arranging DeviceDTO - DeviceIDVO will be used later, string must match with DTO
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        String deviceID = deviceIDVO.getID();
        String deviceName = "device1";
        String deviceModel = "model1";
        String deviceStatus = "status";
        String roomID = "rid";
        DeviceDTO deviceDTO = new DeviceDTO(deviceID,deviceName,deviceModel,deviceStatus,roomID);

        // Arranging timeconfigDTO
        String iDate = "2024-04-23";
        String iTime = "16:00:00";
        String eDate = "2024-04-23";
        String eTime = "18:00:00";
        TimeConfigDTO timeConfigDTO = new TimeConfigDTO(iDate,iTime,eDate,eTime,null);

        String strInitialTimeStamp = "2024-04-23T16:00:00";
        LocalDateTime initialTime = LocalDateTime.parse(strInitialTimeStamp);
        TimeStampVO initialTimeStamp = new TimeStampVO(initialTime);
        String strFinalTimeStamp = "2024-04-23T18:00:00";
        LocalDateTime finalTime = LocalDateTime.parse(strFinalTimeStamp);
        TimeStampVO finalTimeStamp = new TimeStampVO(finalTime);

        // Arranging Logs and creating list of logs
        LogIDVO logID1 = new LogIDVO(UUID.randomUUID());
        String strLogID1 = logID1.getID();
        LogIDVO logID2 = new LogIDVO(UUID.randomUUID());
        String strLogID2 = logID2.getID();

        String reading1 = "50";
        HumidityValue reading1obj = new HumidityValue(reading1);
        String reading2 = "36.6";
        TemperatureValue reading2obj = new TemperatureValue(reading2);

        SensorIDVO sensorID1 = new SensorIDVO(UUID.randomUUID());
        String strSensorID1 = sensorID1.getID();
        SensorIDVO sensorID2 = new SensorIDVO(UUID.randomUUID());
        String strSensorID2 = sensorID2.getID();

        String type1 = "HumiditySensor";
        SensorTypeIDVO sensorTypeIDVO1 = new SensorTypeIDVO(type1);
        String type2 = "TemperatureSensor";
        SensorTypeIDVO sensorTypeIDVO2 = new SensorTypeIDVO(type2);


        Log log1 = new Log(logID1,initialTimeStamp,reading1obj,sensorID1,deviceIDVO,sensorTypeIDVO1);
        Log log2 = new Log(logID2,finalTimeStamp,reading2obj,sensorID2,deviceIDVO,sensorTypeIDVO2);
        ArrayList<Log> logList = new ArrayList<>();
        logList.add(log1);
        logList.add(log2);

        // Creating a repository double and stub of findByDevice... method
        LogRepository logRepository = mock(LogRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        RoomRepository roomRepository = mock(RoomRepository.class);
        LogFactory logFactory = mock(LogFactory.class);
        LogServiceImpl logService = new LogServiceImpl(logRepository, deviceRepository, roomRepository, logFactory);
        when(logRepository.findReadingsByDeviceID(deviceIDVO, initialTimeStamp, finalTimeStamp))
                .thenReturn(logList);

        // Creating ctrl and obtaining the result iterable
        GetLogByDeviceIDAndTimeFrameCTRL ctrl = new GetLogByDeviceIDAndTimeFrameCTRL(logService);
        Iterable<LogDTO> result = ctrl.getLogByDeviceIDAndTimeFrame(deviceDTO,timeConfigDTO);

        // Converting iterable into list for individual LogDTO assessment
        List<LogDTO> list = new ArrayList<>();
        result.forEach(list::add);

        LogDTO logDTO1 = list.get(0);
        LogDTO logDTO2 = list.get(1);

        // Act
        String expectedLogID1 = logDTO1.getLogID();
        String expectedTimeStamp1 = logDTO1.getTime();
        String expectedReading1 = logDTO1.getReading();
        String expectedSensorID1 = logDTO1.getSensorID();
        String expectedDeviceID1 = logDTO1.getDeviceID();
        String expectedSensorTypeID1 = logDTO1.getSensorTypeID();

        String expectedLogID2 = logDTO2.getLogID();
        String expectedTimeStamp2 = logDTO2.getTime();
        String expectedReading2 = logDTO2.getReading();
        String expectedSensorID2 = logDTO2.getSensorID();
        String expectedDeviceID2 = logDTO2.getDeviceID();
        String expectedSensorTypeID2 = logDTO2.getSensorTypeID();

        // Assert
        assertEquals(expectedLogID1,strLogID1);
        assertEquals(expectedTimeStamp1,strInitialTimeStamp);
        assertEquals(expectedReading1,reading1);
        assertEquals(expectedSensorID1,strSensorID1);
        assertEquals(expectedDeviceID1,deviceID);
        assertEquals(expectedSensorTypeID1,type1);

        assertEquals(expectedLogID2,strLogID2);
        assertEquals(expectedTimeStamp2,strFinalTimeStamp);
        assertEquals(expectedReading2,reading2);
        assertEquals(expectedSensorID2,strSensorID2);
        assertEquals(expectedDeviceID2,deviceID);
        assertEquals(expectedSensorTypeID2,type2);
    }
}
