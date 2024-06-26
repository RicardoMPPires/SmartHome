package smarthome.mapper.assembler;

import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceAssembler {

    /**
     * Private constructor to prevent instantiation of the class.
     */

    private DeviceAssembler() {
    }

    /**
     * Converts a DeviceDataModel object to a Device object. It takes a DeviceFactory and a DeviceDataModel as parameters.
     * First, it creates Value-Objects from the DeviceDataModel object. Then, it uses the DeviceFactory to create a Device object
     * with the Value-Objects. Finally, it returns the Device object.
     *
     * @param deviceFactory   The DeviceFactory used to create Device objects.
     * @param deviceDataModel The DeviceDataModel object to be converted.
     * @return The Device object created.
     */
    public static Device toDomain(DeviceFactory deviceFactory, DeviceDataModel deviceDataModel)
    {
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceDataModel.getDeviceID()));
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceDataModel.getDeviceName());
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceDataModel.getDeviceModel());
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO(deviceDataModel.getDeviceStatus());

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(deviceDataModel.getRoomID()));

        return deviceFactory.createDevice(deviceIDVO,deviceNameVO,deviceModelVO,deviceStatusVO,roomIDVO);
    }
    /**
     * Converts a list of DeviceDataModel objects to a list of Device objects. It takes a DeviceFactory and an Iterable of DeviceDataModel
     * objects as parameters. First, it creates an empty list of Device objects. Then, it iterates over the DevceDataModel objects
     * and converts each one to a Device object using the toDomain() method. Finally, it returns the list of Device objects.
     *
     * @param deviceFactory       The DeviceFactory used to create Device objects.
     * @param deviceDataModelList The Iterable of DeviceDataModel objects to be converted.
     * @return The list of Device objects created.
     */

    public static Iterable<Device> toDomainList(DeviceFactory deviceFactory, Iterable<DeviceDataModel> deviceDataModelList) {

        List<Device> devices = new ArrayList<>();
        deviceDataModelList.forEach(deviceDataModel -> {
            Device device = toDomain(deviceFactory, deviceDataModel);
            devices.add(device);
        });

        return devices;
    }
}
