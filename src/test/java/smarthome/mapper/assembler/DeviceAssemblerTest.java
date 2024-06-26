package smarthome.mapper.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.roomvo.*;

import smarthome.persistence.jpa.datamodel.DeviceDataModel;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DeviceAssemblerTest is a test class for the RoomAssembler class.
 */

class DeviceAssemblerTest {

    private String deviceID;
    private String deviceName;
    private String deviceModel;
    private boolean deviceStatus;
    private String roomID;

    /**
     * This method sets up the necessary variables for the tests.
     */

    @BeforeEach
    void setUp() {
        deviceID = "123e4567-e89b-12d3-a456-426655440000";
        deviceName = "Fridge";
        deviceModel = "XPTO";
        deviceStatus = true;
        roomID = "00000000-0000-0000-0000-000000000000";
    }

    /**
     * This method tests the creation of a DeviceDataModel object.
     * It uses correct value objects to create a Device object.
     * It checks if the DeviceDataModel object created has the same attributes as the Room object.
     */

    @Test
    void givenCorrectValueObjects_whenCreatingDataModel_thenReturnDeviceDataModel() {
//        Arrange
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO(deviceStatus);
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        Device device = new Device(deviceIDVO, deviceNameVO, deviceModelVO, deviceStatusVO, roomIDVO);
        String expectedDeviceID = device.getId().getID();
        String expectedDeviceName = device.getDeviceName().getValue();
        String expectedDeviceModel = device.getDeviceModel().getValue();
        boolean expectedDeviceStatus = device.getDeviceStatus().getValue();
        String expectedRoomID = device.getRoomID().getID();
//        Act
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
//        Assert
        assertEquals(deviceDataModel.getDeviceID(), expectedDeviceID);
        assertEquals(deviceDataModel.getDeviceName(), expectedDeviceName);
        assertEquals(deviceDataModel.getDeviceModel(), expectedDeviceModel);
        assertEquals(deviceDataModel.getDeviceStatus(), expectedDeviceStatus);
        assertEquals(deviceDataModel.getRoomID(), expectedRoomID);
    }

    /**
     * This method tests the toDomain() method of the DeviceAssembler class.
     * It tests the conversion of a DeviceDataModel object to a Device object.
     * It uses a DeviceDataModel object to test the method.
     * It checks if the Device object created has the same attributes as the DeviceDataModel object.
     */

    @Test
    void givenDeviceDataModel_whenConvertingToDomain_thenReturnDomainDevice() {
//        Arrange
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO(deviceStatus);
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));


        Device device = new Device(deviceIDVO, deviceNameVO, deviceModelVO, deviceStatusVO, roomIDVO);

        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        String expectedDeviceID = deviceDataModel.getDeviceID();

        DeviceFactory devicefactory = new DeviceFactoryImpl();
//        Act
        Device newDevice = DeviceAssembler.toDomain(devicefactory, deviceDataModel);
//        Arrange
        assertEquals(newDevice.getId().getID(), expectedDeviceID);
        assertEquals(newDevice.getDeviceName().getValue(), deviceName);
        assertEquals(newDevice.getDeviceModel().getValue(), deviceModel);
        assertEquals(newDevice.getDeviceStatus().getValue(), deviceStatus);
        assertEquals(newDevice.getRoomID().getID(), roomID);
    }

    /**
     * This method tests the toDomainList() method of the DeviceAssembler class.
     * It tests the conversion of a list of DeviceDataModel objects to a list of Device objects.
     * It uses a list with two DeviceDataModel objects to test the method.
     * It checks if the Device objects created have the same attributes as the DeviceDataModel objects.
     */

    @Test
    void givenListWithTwoDeviceDataModelObjects_whenToDomainListIsCalled_thenReturnIterableWithDomainObjects() {
//         Arrange
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);
        DeviceStatusVO deviceStatusVO = new DeviceStatusVO(deviceStatus);
        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));


        Device device = new Device(deviceIDVO, deviceNameVO, deviceModelVO, deviceStatusVO, roomIDVO);

        DeviceIDVO secondDeviceIDVO = new DeviceIDVO(UUID.fromString("123e4568-e89b-12d3-a456-426655440000"));
        DeviceNameVO secondDeviceName = new DeviceNameVO("Heater");
        DeviceModelVO secondDeviceModel = new DeviceModelVO("XPT");
        DeviceStatusVO secondDeviceStatus = new DeviceStatusVO(true);
        RoomIDVO secondRoomID = new RoomIDVO(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        Device secondDevice = new Device(secondDeviceIDVO, secondDeviceName, secondDeviceModel, secondDeviceStatus,secondRoomID);

        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        DeviceDataModel secondDeviceDataModel = new DeviceDataModel(secondDevice);

        List<DeviceDataModel> deviceDataModelList = List.of(deviceDataModel, secondDeviceDataModel);

        DeviceFactory deviceFactory = new DeviceFactoryImpl();
//        Act
        Iterable<Device> deviceIterableList = DeviceAssembler.toDomainList(deviceFactory, deviceDataModelList);
//        Assert
        List<Device> deviceList = new ArrayList<>();
        deviceIterableList.forEach(deviceList::add);

        Device firstDevice = deviceList.get(0);

        assertEquals(deviceDataModel.getDeviceID(), firstDevice.getId().getID());
        assertEquals(deviceDataModel.getDeviceName(), firstDevice.getDeviceName().getValue());
        assertEquals(deviceDataModel.getDeviceModel(), firstDevice.getDeviceModel().getValue());
        assertEquals(deviceDataModel.getDeviceStatus(), firstDevice.getDeviceStatus().getValue());
        assertEquals(deviceDataModel.getRoomID(), firstDevice.getRoomID().getID());

        Device secondDeviceFromList = deviceList.get(1);

        assertEquals(secondDeviceDataModel.getDeviceID(), secondDeviceFromList.getId().getID());
        assertEquals(secondDeviceDataModel.getDeviceName(), secondDeviceFromList.getDeviceName().getValue());
        assertEquals(secondDeviceDataModel.getDeviceModel(), secondDeviceFromList.getDeviceModel().getValue());
        assertEquals(secondDeviceDataModel.getDeviceStatus(), secondDeviceFromList.getDeviceStatus().getValue());
        assertEquals(secondDeviceDataModel.getRoomID(), secondDeviceFromList.getRoomID().getID());
    }
}
