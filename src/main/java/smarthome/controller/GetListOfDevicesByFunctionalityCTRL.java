package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.service.DeviceService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetListOfDevicesByFunctionalityCTRL {
    private final DeviceService deviceService;

    /**
     * Constructs an instance of GetListOfDevicesByFunctionalityCTRL with the provided DeviceService dependency.
     * This constructor initializes the GetListOfDevicesByFunctionalityCTRL with the DeviceService used to retrieve devices
     * based on functionality.
     * It ensures that the provided DeviceService is not null and throws an IllegalArgumentException if it is.
     * @param deviceService The DeviceService used to retrieve devices based on functionality.
     * @throws IllegalArgumentException if the deviceService parameter is null. This exception is thrown to indicate an
     * invalid or missing service.
     */
    public GetListOfDevicesByFunctionalityCTRL(DeviceService deviceService){
        if (deviceService == null){
            throw new IllegalArgumentException("Invalid service");
        }
        this.deviceService = deviceService;
    }

    /**
     * Retrieves a Map of Devices grouped by functionality.
     * This method calls the getListOfDeviceByFunctionality method of the DeviceService to retrieve a Map of Devices
     * grouped by their functionality. It then maps the retrieved Device objects to DeviceDTOs using the DeviceMapper.
     * @return A Map where each key represents a functionality and the value is a list of DeviceDTOs associated with that
     * functionality. It returns an empty Map if no Devices are found or if there is an error during mapping.
     */
    public LinkedHashMap<String, List<DeviceDTO>> getListOfDevicesByFunctionality(){
        Map<String, List<Device>> map = deviceService.getListOfDeviceByFunctionality();
        return DeviceMapper.domainToDTO(map);
    }
}