package smarthome.controller;

import smarthome.domain.vo.DeltaVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.service.LogService;
import smarthome.utils.timeconfig.*;

/**
 * Controller class for getting the maximum instantaneous temperature difference between two devices, one located
 * on the outside of the house and the other on the inside.
 */
public class GetMaxInstantaneousTemperatureDifferenceCTRL {

    private final LogService logService;

    /**
     * Constructor for GetMaxInstantaneousTemperatureDifferenceCTRL.
     * @param logService the service responsible for handling the log data.
     */
    public GetMaxInstantaneousTemperatureDifferenceCTRL(LogService logService) {
        if(logService == null){
            throw new IllegalArgumentException("Invalid service");
        }
        this.logService = logService;
    }

    /**
     * Gets the maximum instantaneous temperature difference between two devices, one located on the outside of the house
     * and the other on the inside.
     * It converts the DTOs to VOs and calls the service to get the maximum instantaneous temperature difference.
     * @param outdoorDeviceDTO the outdoor device.
     * @param indoorDeviceDTO the indoor device.
     * @param timeConfigDTO the time configuration.
     * @return the maximum instantaneous temperature difference between the two devices, throws an IllegalArgumentException
     * if any of the parameters are null, or if there are errors in the outdoor or indoor device IDs, or in the time
     * configuration.
     */
    public String getMaxInstantaneousTemperature(DeviceDTO outdoorDeviceDTO, DeviceDTO indoorDeviceDTO, TimeConfigDTO timeConfigDTO) {
        DeviceIDVO outdoorDeviceID = DeviceMapper.createDeviceID(outdoorDeviceDTO);
        DeviceIDVO indoorDeviceID = DeviceMapper.createDeviceID(indoorDeviceDTO);
        TimeStampVO initialTimeStamp = TimeConfigMapper.createInitialTimeStamp(timeConfigDTO);
        TimeStampVO finalTimeStamp = TimeConfigMapper.createFinalTimeStamp(timeConfigDTO);
        DeltaVO delta = TimeConfigMapper.createDeltaVO(timeConfigDTO);
        return logService.getMaxInstantaneousTempDifference(outdoorDeviceID, indoorDeviceID, initialTimeStamp, finalTimeStamp, delta);
    }
}
