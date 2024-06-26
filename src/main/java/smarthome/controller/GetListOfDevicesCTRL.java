package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.RoomDTO;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.RoomMapper;
import smarthome.service.DeviceService;
import smarthome.domain.vo.roomvo.RoomIDVO;

import java.util.Collections;
import java.util.List;

public class GetListOfDevicesCTRL {
    private final DeviceService deviceService;

    /**
     * Constructs an instance of GetListOfDevicesCTRL with the provided DeviceService dependency.
     * This constructor initializes the GetListOfDevicesCTRL with the DeviceService used to retrieve a list of devices.
     * It ensures that the provided DeviceService is not null and throws an IllegalArgumentException if it is.
     * @param deviceService The DeviceService used to retrieve a list of devices.
     * @throws IllegalArgumentException if the deviceService parameter is null. This exception is thrown to indicate an
     * invalid or missing service.
     */
    public GetListOfDevicesCTRL(DeviceService deviceService) {
        if(areParamsNull(deviceService)){
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.deviceService = deviceService;
    }

    /**
     * Retrieves a list of devices associated with the specified room.
     * This method creates a RoomIDVO from the provided RoomDTO and calls the getListOfDevicesInARoom method
     * of the DeviceService to retrieve the list of devices in the room.
     * It then maps the retrieved Device objects to DeviceDTOs using the DeviceMapper.
     * @param roomDTO The Data Transfer Object (DTO) containing Room information.
     * @return A list of DeviceDTOs representing devices associated with the specified room. It returns an empty list if no
     * devices are found or if there is an error during mapping.
     */
    public List<DeviceDTO> getListOfDevices(RoomDTO roomDTO) {
        try{
            RoomIDVO roomIDVO = RoomMapper.createRoomIDVO(roomDTO);
            List<Device> deviceList = deviceService.getListOfDevicesInARoom(roomIDVO);
            return DeviceMapper.domainToDTO(deviceList);
        } catch (IllegalArgumentException e){
            return Collections.emptyList();
        }
    }

    /**
     * This method checks if any of the parameters are null.
     * @param params Any object parameters
     * @return True or false
     */
    private boolean areParamsNull(Object... params){
        for (Object param : params){
            if (param == null){
                return true;
            }
        }
        return false;
    }
}
