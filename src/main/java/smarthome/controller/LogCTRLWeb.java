package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.log.Log;
import smarthome.domain.vo.DeltaVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.LogMapper;
import smarthome.mapper.SensorMapper;
import smarthome.mapper.dto.LogDTO;
import smarthome.service.LogService;
import smarthome.utils.timeconfig.TimeConfigDTO;
import smarthome.utils.timeconfig.TimeConfigMapper;


import java.util.List;

/**
 * REST controller for managing logs in the Smart Home system.
 * <p>
 * This controller provides endpoints for creating log entries, retrieving log entries within a specific time period,
 * and calculating the maximum temperature difference between indoor and outdoor devices. It interacts with the
 * {@link LogService} to perform the necessary operations and uses various mappers to convert between domain objects
 * and DTOs.
 * </p>
 */
@RestController
@RequestMapping ("/logs")
@CrossOrigin(origins = "*")

public class LogCTRLWeb {

    private final LogService logService;


    /**
     * Constructs a new {@code WebLogController} with the specified {@code LogService}.
     *
     * @param logService the service for managing logs
     */
    @Autowired
    public LogCTRLWeb(LogService logService) {
        this.logService = logService;
    }

    /**
     * Finds readings for a specific device. A time period (timeConfigDTO) may be specified, which is optional.
     * <p>
     * This endpoint retrieves log entries for a specified device ID.
     * If a time period is specified it converts the device ID and timestamps from the DTO to value objects,
     * uses the {@code LogService} to find the logs, and returns the logs as a collection of {@link LogDTO}.
     * If a time period is not specified only the device ID is converted to value object and passed to log service
     * </p>
     * @param id the device ID
     * @param timeConfigDTO the time configuration data transfer object (Optional)
     * @return a {@code ResponseEntity} containing the list of log DTOs and HTTP status
     */

    @GetMapping("")
    public ResponseEntity<CollectionModel<LogDTO>> findReadings(
            @RequestParam(value = "deviceId") String id,
            @RequestBody(required = false) TimeConfigDTO timeConfigDTO) {

        try {
            DeviceIDVO deviceIDVO = DeviceMapper.createDeviceID(id);

            TimeStampVO initialTimeStamp = null;
            TimeStampVO finalTimeStamp = null;

            //Ensuring that mapping is only done if timeConfigDto is passed to the function
            if (timeConfigDTO != null) {
                initialTimeStamp = TimeConfigMapper.createInitialTimeStamp(timeConfigDTO);
                finalTimeStamp = TimeConfigMapper.createFinalTimeStamp(timeConfigDTO);
            }

            List<Log> logs = logService.findReadingsFromDevice(deviceIDVO, initialTimeStamp, finalTimeStamp);
            List<LogDTO> logsDTO = LogMapper.domainToDTO(logs);
            // Returns the logs with a status code
            return new ResponseEntity<>(CollectionModel.of(logsDTO), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Return an empty list with a status code
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Gets the maximum temperature difference between indoor and outdoor sensors within a time period.
     * <p>
     * This endpoint calculates the maximum instantaneous temperature difference between an indoor and an outdoor
     * device within a specified time period, as defined by the {@link TimeConfigDTO}. It converts the device IDs
     * and timestamps from the DTO to value objects, uses the {@code LogService} to calculate the temperature difference,
     * and returns the result as a string message.
     * </p>
     *
     * @param outId the outdoor device ID
     * @param inId the indoor device ID
     * @param timeConfigDTO the time configuration data transfer object
     * @return a {@code ResponseEntity} containing the maximum temperature difference message and HTTP status
     */
    @GetMapping(params = {"outdoorId", "indoorId"})
    public ResponseEntity<String> getMaxTempDiff(@RequestParam (value="outdoorId") String outId, @RequestParam (value="indoorId") String inId, @RequestBody TimeConfigDTO timeConfigDTO) {

        try {
            DeviceIDVO outdoorDeviceIDVO = DeviceMapper.createDeviceID(outId);
            DeviceIDVO indoorDeviceIDVO = DeviceMapper.createDeviceID(inId);
            TimeStampVO initialTimeStamp = TimeConfigMapper.createInitialTimeStamp(timeConfigDTO);
            TimeStampVO finalTimeStamp = TimeConfigMapper.createFinalTimeStamp(timeConfigDTO);
            DeltaVO delta = TimeConfigMapper.createDeltaVO(timeConfigDTO);


            String maxTempDiff = logService.getMaxInstantaneousTempDifference(outdoorDeviceIDVO, indoorDeviceIDVO, initialTimeStamp, finalTimeStamp, delta);
            // Returns a message with the Maximum Temperature Difference, plus a status code
            return new ResponseEntity<>(maxTempDiff, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Return an error message, plus a status code
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Handles HTTP GET requests to retrieve the peak power consumption of a house
     * within a specified time period.
     *
     * @param timeConfigDTO the configuration object containing start time, end time, and delta values.
     * @return a ResponseEntity containing a message with the peak power consumption or an error message.
     */
    @GetMapping("/peak-power-consumption")
    public ResponseEntity<String> getPeakPowerConsumption(@RequestBody TimeConfigDTO timeConfigDTO) {
        try {
            TimeStampVO start = TimeConfigMapper.createInitialTimeStamp(timeConfigDTO);
            TimeStampVO end = TimeConfigMapper.createFinalTimeStamp(timeConfigDTO);
            DeltaVO delta = TimeConfigMapper.createDeltaVO(timeConfigDTO);
            String peakPowerConsumption = logService.getPeakPowerConsumption(start, end, delta);
            // Returns a message with the Peak Power Consumption of the House in a given period, plus a status code
            return new ResponseEntity<>(peakPowerConsumption, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Returns an error message, plus a status code
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles a POST request to retrieve sun readings based on the given parameters.
     *
     * @param date      the date for which the sun reading is requested, in the format "YYYY-MM-DD"
     * @param latitude  the latitude coordinate of the location
     * @param longitude the longitude coordinate of the location
     * @param sensorTypeId  the unique identifier of the sensor type
     * @return a ResponseEntity containing the sun reading as a String if the request is successful, or a BAD_REQUEST status if an error occurs
     */
    @PostMapping("/get-sun-reading")
    public ResponseEntity<String> getSunReading (@RequestParam (value="date") String date,
                                                 @RequestParam (value="latitude") String latitude,
                                                 @RequestParam (value="longitude") String longitude,
                                                 @RequestParam (value="sensorTypeId") String sensorTypeId){

        try{
            String gpsCoordinates = latitude + ":" + longitude;
            SensorTypeIDVO sensorIDVO = SensorMapper.createSensorTypeIDVO(sensorTypeId);
            String reading = this.logService.getSunReading(date,gpsCoordinates,sensorIDVO);
            return new ResponseEntity<>(reading, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
