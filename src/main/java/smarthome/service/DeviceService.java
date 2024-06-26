package smarthome.service;

import smarthome.domain.device.Device;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DeviceService {
    Optional<Device> addDevice(DeviceNameVO deviceNameVO, DeviceModelVO deviceModelVO, RoomIDVO roomIDVO);

    Optional<Device> deactivateDevice(DeviceIDVO deviceIDVO);

    List<Device> getListOfDevicesInARoom(RoomIDVO roomIDVO);

    Map<String, List<Device>> getListOfDeviceByFunctionality();

    Optional<Device> getDeviceById(DeviceIDVO deviceIDVO);
}