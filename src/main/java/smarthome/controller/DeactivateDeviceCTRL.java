package smarthome.controller;

import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.DeviceMapper;
import smarthome.service.DeviceService;
import smarthome.domain.vo.devicevo.DeviceIDVO;

/**
 * The DeactivateDeviceCTRL class represents a controller responsible for deactivating devices.
 * It interacts with a DeviceService to perform device deactivation operations.
 */

public class DeactivateDeviceCTRL {

    private final DeviceService deviceService;


    /**
     * Constructs a new DeactivateDeviceCTRL object with the specified DeviceService.
     * @param deviceService The DeviceService to be used by the controller.
     * @throws IllegalArgumentException if the provided DeviceService is null.
     */

    public DeactivateDeviceCTRL(DeviceService deviceService) {
        if(isNull(deviceService)){
            throw new IllegalArgumentException("Invalid service");
        }
        this.deviceService = deviceService;
    }

    /**
     * This method converts the device DTO into a DeviceIDVO in order to supply it to the device service, who then
     * calls deactivate device.
     * @param deviceDTO The DeviceDTO representing the device to be deactivated.
     * @return The device status after the operation is performed
     */
    public boolean deactivateDevice(DeviceDTO deviceDTO){
        try {
            DeviceIDVO deviceIDVO = DeviceMapper.createDeviceID(deviceDTO);
            return deviceService.deactivateDevice(deviceIDVO).isPresent();
        } catch (IllegalArgumentException e){
            return false;
        }
    }


    /**
     * Checks if the passed parameter to the method is non-null.
     * This method ensures that the provided parameter is not null before proceeding with further operations.
     * While this function may not be considered type-safe, it is always used after ensuring type safety
     * by type-checking the variables of the calling method (compile-time type checking).
     *
     * @param object The parameters to be checked for nullity.
     * @return true if the parameter is non-null, false otherwise.
     */

    private boolean isNull(Object object){
        return object == null;
    }
}
