package smarthome.domain.device;

import org.springframework.stereotype.Component;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.roomvo.RoomIDVO;

@Component
public class DeviceFactoryImpl implements DeviceFactory{

    /**
     * Creates a Device with the specified parameters, that are all Value-Objects.
     * @param deviceName The name of the device.
     * @param deviceModel The model of the device.
     * @param roomID The ID of the room to which the device belongs.
     * @return  The Device created.
     */
    public Device createDevice(DeviceNameVO deviceName, DeviceModelVO deviceModel, RoomIDVO roomID){
        return new Device(deviceName,deviceModel,roomID);
    }

    /**
     * Creates a Device with the specified parameters, that are all Value-Objects.
     * This implementation is used when the DeviceID is already known and persisted in a database, and it is necessary to
     * create a Device object with it.
     *
     * @param deviceIDVO         The ID of the device.
     * @param deviceNameVO       The name of the device.
     * @param deviceModelVO      The model of the device.
     * @param deviceStatusVO     The status of the device, it is a boolean.
     * @param roomID             The ID of the room to which the device belongs.
     * @return The Device created.
     */
    public Device createDevice(DeviceIDVO deviceIDVO, DeviceNameVO deviceNameVO, DeviceModelVO deviceModelVO, DeviceStatusVO deviceStatusVO, RoomIDVO roomID){
        return new Device(deviceIDVO, deviceNameVO, deviceModelVO, deviceStatusVO, roomID);
    }
}
