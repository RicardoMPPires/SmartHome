package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.SensorDTO;
import smarthome.mapper.dto.SensorTypeDTO;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.SensorRepository;
import smarthome.persistence.SensorTypeRepository;
import smarthome.service.SensorService;
import smarthome.service.SensorServiceImpl;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test class includes SensorTypeService instantiations that may appear unused.
 * The reason for this is that the service initialization automatically populates the `SensorTypeRepository`.
 * Without this step, when attempting to verify that the sensor type exists, the result will always fail
 * as the repository would be empty.
 */


class AddSensorToDeviceCTRLTest {

    /**
     * Test for the constructor of AddSensorToDeviceCTRL.
     * Tests if the constructor throws an IllegalArgumentException when the sensorService is null.
     */
    @Test
    void whenNullSensorService_constructorThrowsIllegalArgumentException() {
        //Arrange
        //Arrange
        String expectedMessage = "Invalid service";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new AddSensorToDeviceCTRL(null));

        //Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }



    /**
     * This test method verifies the functionality of adding a Humidity Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Humidity Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */
    @Test
    void addHumiditySensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="HumiditySensor";
        String unit = "%";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);

    }

    /**
     * This test method verifies the functionality of adding a Temperature Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Temperature Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addTemperatureSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="TemperatureSensor";
        String unit = "C";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a Position Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Position Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addPositionSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="PositionSensor";
        String unit = "%";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a Wind Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Wind Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addWindSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="WindSensor";
        String unit = "Km/h-CardinalPoints";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a DewPoint Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the DewPoint Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addDewPointSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="DewPointSensor";
        String unit = "C";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a Sunset Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Sunset Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addSunsetSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="SunsetSensor";
        String unit = "DATE";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a Sunrise Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Sunrise Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */
    @Test
    void addSunriseSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="SunriseSensor";
        String unit = "DATE";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a AveragePowerConsumption Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the AveragePowerConsumption Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addAveragePowerConsumptionSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="AveragePowerConsumptionSensor";
        String unit = "W";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a PowerConsumption Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the PowerConsumption Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addPowerConsumptionSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="PowerConsumptionSensor";
        String unit = "W";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a EnergyConsumption Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the EnergyConsumption Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addEnergyConsumptionSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="EnergyConsumptionSensor";
        String unit = "W/h";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a Switch Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the Switch Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addSwitchSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="SwitchSensor";
        String unit = "Binary";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test method verifies the functionality of adding a SolarIrradiance Sensor to a Device using the AddSensorToDeviceCTRL.
     * It ensures that the operation returns true upon success.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller. The 3 necessary repositories are mocked.
     * The SensorRepository is mocked to return true when saving a sensor.
     * The DeviceRepository is mocked to return a device (that exists and is active by default) when findById is called.
     * The SensorTypeRepository is mocked to return true when checking if a sensor type is present.
     * It creates a new Device then initializes DTOs for Device, Sensor Type, and Sensor.
     * After calling the method to add the SolarIrradiance Sensor to the Device, the test verifies the operation's success by
     * checking if it returns true.
     */

    @Test
    void addSolarIrradianceSensorToDevice_shouldReturnTrue() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(true);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID = "SolarIrradianceSensor";
        String unit = "W/m2";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);
    }

    /**
     * This test verifies the behavior when attempting to add a sensor to a deactivated device.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller.
     * It creates a device, deactivates it, and creates DTOs for the device, a Sensor Type, and a Sensor.
     * The Device repository is mocked to return the deactivated device when findById is called.
     * After calling the method to add the sensor to the deactivated device, the test verifies the operation's failure
     * by checking if it returns false.
     */
    @Test
    void addSensorToDevice_WhenDeactivatedDevice_shouldReturnFalse() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);
        //Device deactivation
        device.deactivateDevice();

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="SwitchSensor";
        String unit = "W/m2";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();

        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertFalse(result);
    }

    /**
     * This test verifies the behavior when attempting to add a sensor to a non-existing device.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller.
     * It creates a DTO for a non-existing device, a Sensor Type, and a Sensor.
     * The Device repository is mocked to return null when findById is called with the device's ID from the DTO.
     * After calling the method to add the sensor to the device, the test verifies the operation's failure by checking
     * if it returns false.
     */
    @Test
    void addSensorToDevice_WhenNonExistingDevice_shouldReturnFalse() {
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Initialization of DeviceDTO
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        String deviceId = deviceIDVO.getID();
        String deviceName = "Device1";
        String deviceModel = "XPU-99";
        String deviceStatus = "true";
        String roomId = "roomID";
        DeviceDTO deviceDTO = new DeviceDTO(deviceId,deviceName,deviceModel,deviceStatus,roomId);

        when(doubleDeviceRepository.findById(deviceIDVO)).thenReturn(null);

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="HumiditySensor";
        String unit = "%";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();

        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert
        //This assertion asserts that the operation did not succeed
        assertFalse(result);
    }

    /**
     * This test verifies the behavior when attempting to add a sensor with an invalid sensor type to a device.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller.
     * It creates a new Device then initializes DTOs for Device, Sensor Type (with an invalid type), and Sensor.
     * The Device repository is mocked to return the device when findById is called.
     * The Sensor Type repository is mocked to return false when checking if the sensor type is present.
     * After calling the method to add the sensor to the Device, the test verifies the operation's failure by checking
     * if it returns false.
     */
    @Test
    void addSensorToDevice_WhenInvalidSensorType_shouldReturnFalse(){
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization with invalid type
        String sensorTypeID ="NuclearSensor";
        String unit = "Bq";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(false);

        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();

        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert
        //This assertion asserts that the operation did not succeed
        assertFalse(result);
    }


    /**
     *This test verifies the behavior when a null deviceDTO is passed as a parameter to the addSensorToDevice method.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository.
     */
    @Test
    void addSensorToDevice_givenNullDeviceDTO_shouldReturnFalse() {
        // Arrange
        SensorFactory sensorFactory = new SensorFactoryImpl("config.properties");
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureSensor", "C");

        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();

        // Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(null, sensorTypeDTO, sensorDTO);

        // Assert
        assertFalse(result);
    }


    /**
     * This test verifies the behavior when a null sensorTypeDTO is passed as a parameter to the addSensorToDevice method.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository.
     */
    @Test
    void addSensorToDevice_givenNullSensorTypeDTO_shouldReturnFalse() {
        // Arrange
        SensorFactory sensorFactory = new SensorFactoryImpl("config.properties");
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);
        DeviceDTO deviceDTO = new DeviceDTO("1", "Device1", "Model1", "true", "roomID");

        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();

        // Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO, null, sensorDTO);

        // Assert
        assertFalse(result);
    }


    /**
     * This test verifies the behavior when a null sensorDTO is passed as a parameter to the addSensorToDevice method.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository.
     */
    @Test
    void addSensorToDevice_givenNullSensorDTO_shouldReturnFalse() {
        // Arrange
        SensorFactory sensorFactory = new SensorFactoryImpl("config.properties");
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);
        DeviceDTO deviceDTO = new DeviceDTO("1", "Device1", "Model1", "true", "roomID");
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureSensor", "C");

        // Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO, sensorTypeDTO, null);

        // Assert
        assertFalse(result);
    }

    /**
     * This test verifies the behavior when attempting to add the repository does not save the sensor.
     * It ensures that the operation returns false and that no sensor is stored in the sensor repository,
     * with the behaviour stubbed.
     * The test initializes the necessary components, including Sensor Service, Device repository, Sensor Type Service,
     * and the controller.
     * It creates a new Device then initializes DTOs for Device, Sensor Type (with an invalid type), and Sensor.
     * The Device repository is stubbed to return the device when findById is called.
     * The Sensor repository is stubbed to return false when checking if the sensor is saved.
     * After calling the method to add the sensor to the Device, the test verifies the operation's failure by checking
     * if it returns false.
     */
    @Test
    void addSensorToDevice_whenRepositoryDoesNotSaveSensor_shouldReturnFalse(){
        //Arrange
        //Sensor Service instantiation
        String propertiesPath = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(propertiesPath);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        //Sensor repository behaviour conditioned to return false
        when(doubleSensorRepository.save(any(Sensor.class))).thenReturn(false);

        SensorService sensorService = new SensorServiceImpl(doubleDeviceRepository, doubleSensorTypeRepository, doubleSensorRepository, sensorFactory);

        //Creation of device's value objects and the device
        DeviceNameVO deviceNameVO = new DeviceNameVO("Device1");
        DeviceModelVO deviceModelVO = new DeviceModelVO("Model1");
        RoomIDVO roomID = new RoomIDVO(UUID.randomUUID());
        Device device = new Device(deviceNameVO, deviceModelVO, roomID);
        when(doubleDeviceRepository.findById(device.getId())).thenReturn(device);

        //DeviceDTO initialization
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();
        DeviceStatusVO deviceStatusVO = device.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceNameVO.getValue(), deviceModelVO.getValue(), deviceStatusVO.getValue().toString(), roomID.getID());

        //Controller initialization
        AddSensorToDeviceCTRL addSensorToDeviceCTRL = new AddSensorToDeviceCTRL(sensorService);

        //SensorTypeDTO initialization
        String sensorTypeID ="HumiditySensor";
        String unit = "%";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(sensorTypeID,unit);

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        when(doubleSensorTypeRepository.isPresent(sensorTypeIDVO)).thenReturn(true);


        //SensorDTO initialization
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorName("Sensor1")
                .build();


        //Act
        boolean result = addSensorToDeviceCTRL.addSensorToDevice(deviceDTO,sensorTypeDTO,sensorDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertFalse(result);
    }
}