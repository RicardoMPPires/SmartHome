package smarthome.controller;

import smarthome.domain.log.Log;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.LogMapper;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.LogDTO;
import smarthome.service.LogService;
import smarthome.utils.timeconfig.TimeConfigDTO;
import smarthome.utils.timeconfig.TimeConfigMapper;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.util.List;

public class GetLogByDeviceIDAndTimeFrameCTRL {
    private final LogService logService;

    /**
     * Constructor for GetListOfReadingsFromDeviceInAGivenTimeCTRL.
     *
     * @param logService the service used for log data access
     * @throws IllegalArgumentException if the logService is null
     */
    public GetLogByDeviceIDAndTimeFrameCTRL(LogService logService){
        if (areParamsNull(logService)){
            throw new IllegalArgumentException("LogService cannot be null.");
        }
        this.logService = logService;
    }

    /**
     * Retrieves all log readings from a device within a given time period.
     *
     * @param device the device DTO
     * @param time the time configuration DTO
     * @return an Iterable of LogDTOs that match the given criteria
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public List<LogDTO> getLogByDeviceIDAndTimeFrame(DeviceDTO device, TimeConfigDTO time) {
        TimeStampVO initialTimeStamp = TimeConfigMapper.createInitialTimeStamp(time);
        TimeStampVO finalTimeStamp = TimeConfigMapper.createFinalTimeStamp(time);
        DeviceIDVO deviceIDVO = DeviceMapper.createDeviceID(device);

        List<Log> listOfLogs = logService.findReadingsFromDevice(deviceIDVO, initialTimeStamp, finalTimeStamp);
        return LogMapper.domainToDTO(listOfLogs);
    }

    /**
     * Checks if any of the provided parameters are null.
     * <p>
     * This method takes a variable number of parameters and iterates over them to determine if any of them are null.
     * If any parameter is found to be null, the method returns true; otherwise, it returns false. This method is useful
     * for checking multiple parameters for nullability in a concise way.
     * @param params The parameters to be checked for null.
     * @return true if any of the parameters are null, false otherwise.
     */
    private boolean areParamsNull(Object... params){
        for(Object param : params){
            if (param == null){
                return true;
            }
        }
        return false;
    }
}
