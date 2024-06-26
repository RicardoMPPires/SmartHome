package smarthome.persistence;

import smarthome.domain.device.Device;
import smarthome.domain.house.House;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.roomvo.RoomIDVO;

import java.util.List;

public interface DeviceRepository extends Repository<DeviceIDVO, Device>{
    Iterable<Device> findByRoomID(RoomIDVO roomID);

    boolean update(Device device);
}
