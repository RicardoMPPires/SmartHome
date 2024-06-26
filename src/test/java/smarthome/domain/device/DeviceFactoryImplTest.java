package smarthome.domain.device;

import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeviceFactoryImplTest {

    /**
    * Tests that when the createDevice method in DeviceFactory is called, then an object should be created.
    */
    @Test
    void whenConstructorInvoked_ThenMockObjectShouldBeCreated()  {
        //arrange
        DeviceNameVO nameDouble = mock(DeviceNameVO.class);
        DeviceModelVO modelDouble = mock(DeviceModelVO.class);
        RoomIDVO roomIdDouble = mock(RoomIDVO.class);

        try(MockedConstruction<Device> deviceDouble = mockConstruction(Device.class,(mock, context)-> {
            when(mock.getDeviceName()).thenReturn(nameDouble);
            when(mock.getDeviceModel()).thenReturn(modelDouble);
            when(mock.getRoomID()).thenReturn(roomIdDouble);
        })) {

            DeviceFactoryImpl factoryDevice = new DeviceFactoryImpl();

            // act
            Device device = factoryDevice.createDevice(nameDouble, modelDouble, roomIdDouble);

            // assert
            List<Device> devices = deviceDouble.constructed();

            assertEquals(1, devices.size());
            assertEquals(modelDouble, device.getDeviceModel());
            assertEquals(nameDouble, device.getDeviceName());
            assertEquals(roomIdDouble,device.getRoomID());
        }
    }

       /*
    SYSTEM UNDER TEST: FACTORY + DEVICE
    A double of all the other collaborators is done (essentially the required value objects to create the Device).
     */

    /**
     * Tests the behavior of the createDevice method in DeviceFactory when valid parameters are provided.
     * It verifies if the method returns the expected DeviceNameVO.
     */
    @Test
    void createDevice_WhenValidParameters_CorrectDeviceShouldBeReturned()  {
        //arrange
        DeviceNameVO expectedName = mock(DeviceNameVO.class);
        DeviceModelVO expectedModel = mock(DeviceModelVO.class);
        RoomIDVO expectedRoomID = mock(RoomIDVO.class);
        DeviceFactoryImpl deviceFactoryImpl = new DeviceFactoryImpl();

        //Act
        Device device = deviceFactoryImpl.createDevice(expectedName,expectedModel,expectedRoomID );
        DeviceNameVO resultName = device.getDeviceName();
        DeviceModelVO resultModel = device.getDeviceModel();
        RoomIDVO roomIDVO = device.getRoomID();

        //Assert
        // Verifying if the returned Device value objects matches the expected values
        assertEquals(expectedName,resultName);
        assertEquals(expectedModel,resultModel);
        assertEquals(expectedRoomID,roomIDVO);
    }

    /**
     * Tests the behavior of the createDevice method in DeviceFactory when null parameters are provided.
     * It ensures that the method propagates an IllegalArgumentException.
     */

    @Test
    void createDevice_WhenNullParameters_ShouldPropagateIllegalArgumentException()  {
        //arrange
        String expected = "Invalid parameters";
        DeviceModelVO modelDouble = mock(DeviceModelVO.class);
        RoomIDVO roomIdDouble = mock(RoomIDVO.class);
        DeviceFactoryImpl deviceFactoryImpl = new DeviceFactoryImpl();

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceFactoryImpl.createDevice(null,modelDouble,roomIdDouble));
        String result = exception.getMessage();
        //Assert
        assertEquals(expected,result);
    }

  /*  @Test
    void givenCreateDeviceWithDeviceIDAndDeviceStatus_whenCreateDeviceIsCalled_thenReturnDeviceWithCorrectParameters() {
//        Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        DeviceNameVO deviceName = mock(DeviceNameVO.class);
        DeviceModelVO deviceModel = mock(DeviceModelVO.class);
        DeviceStatusVO deviceStatus = mock(DeviceStatusVO.class);
        RoomIDVO roomID = mock(RoomIDVO.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
//        Act
        Device device = deviceFactory.createDevice(deviceID, deviceName, deviceModel, deviceStatus, roomID);
//        Assert
        DeviceIDVO expectedDeviceIDVO = device.getId();
        DeviceNameVO expectedDeviceName = device.getDeviceName();
        DeviceModelVO expectedDeviceModel = device.getDeviceModel();
        DeviceStatusVO expectedDeviceStatus = device.getDeviceStatus();
        RoomIDVO expectedRoomIDVO = device.getRoomID();

        assertEquals(deviceID, expectedDeviceIDVO);
        assertEquals(deviceName, expectedDeviceName);
        assertEquals(deviceModel, expectedDeviceModel);
        assertEquals(deviceStatus, expectedDeviceStatus);
        assertEquals(roomID, expectedRoomIDVO);
    }*/

    /**
     * Tests the behavior of the createDevice method in DeviceFactory when valid parameters are provided.
     * It verifies if the method returns the expected DeviceNameVO.
     */

    @Test
    public void givenCreateDeviceWithDeviceIDAndDeviceStatus_whenCreateDeviceIsCalled_thenReturnDeviceWithCorrectParameters() {
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        DeviceNameVO deviceName = mock(DeviceNameVO.class);
        DeviceModelVO deviceModel = mock(DeviceModelVO.class);
        DeviceStatusVO deviceStatus = mock(DeviceStatusVO.class);
        RoomIDVO roomID = mock(RoomIDVO.class);

        when(deviceID.getID()).thenReturn("deviceID");
        when(deviceName.getValue()).thenReturn("deviceName");
        when(deviceModel.getValue()).thenReturn("deviceModel");
        when(deviceStatus.getValue()).thenReturn(true);
        when(roomID.getID()).thenReturn("roomID");

        DeviceFactory deviceFactory = new DeviceFactoryImpl();

        // Act
        Device device = deviceFactory.createDevice(deviceID, deviceName, deviceModel, deviceStatus, roomID);

        // Assert
        assertEquals("deviceID", device.getId().getID());
        assertEquals("deviceName", device.getDeviceName().getValue());
        assertEquals("deviceModel", device.getDeviceModel().getValue());
        assertEquals(true, device.getDeviceStatus().getValue());
        assertEquals("roomID", device.getRoomID().getID());
    }



}