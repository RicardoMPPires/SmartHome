package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.DeviceMapper;
import smarthome.mapper.RoomMapper;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.RoomDTO;
import smarthome.service.DeviceService;

import java.util.Optional;

public class AddDeviceToRoomCTRL {
    private final DeviceService deviceService;

    /**
     * Constructs an instance of AddDeviceToRoomCTRL with the provided DeviceService dependency.
     * This constructor initializes the AddDeviceToRoomCTRL with the DeviceService used to add devices to rooms.
     * It ensures that the provided DeviceService is not null and throws an IllegalArgumentException if it is.
     *
     * @param deviceService The DeviceService used to add devices to rooms.
     * @throws IllegalArgumentException if the deviceService parameter is null. This exception is thrown to indicate an
     *                                  invalid or missing service.
     */
    public AddDeviceToRoomCTRL(DeviceService deviceService) {
        if (deviceService == null) {
            throw new IllegalArgumentException("Invalid services provided");
        }
        this.deviceService = deviceService;
    }

    /**
     * This method is responsible for adding a Device. It takes in a roomDTO and deviceDTO as parameters. Initially, it
     * converts the roomDTO into a RoomIDVO object using a room mapper. Subsequently, it invokes the private method
     * createDevices, which commences by extracting all the information from the DeviceDTO, converting it to Value Object
     * (VO) objects, and then proceeding to request the device addition from the device service. The method returns true if no issues
     * arise, and false otherwise.
     *
     * @param roomDTO   RoomDTO object
     * @param deviceDTO DeviceDTO object
     * @return True if device is successfully added, false otherwise.
     */
    public boolean addDeviceToRoom(RoomDTO roomDTO, DeviceDTO deviceDTO) {
        try {
            RoomIDVO roomIDVO = RoomMapper.createRoomIDVO(roomDTO);
            DeviceNameVO deviceName = DeviceMapper.createDeviceName(deviceDTO);
            DeviceModelVO model = DeviceMapper.createDeviceModel(deviceDTO);
            Optional<Device> device = this.deviceService.addDevice(deviceName, model, roomIDVO);
            return device.isPresent();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
