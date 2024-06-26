package smarthome.service;

import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.SensorRepository;
import smarthome.persistence.SensorTypeRepository;
import smarthome.persistence.mem.DeviceRepositoryMem;
import smarthome.persistence.mem.SensorRepositoryMem;
import smarthome.persistence.mem.SensorTypeRepositoryMem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SensorServiceImplTest {

    /**
     * This test ensures that the constructor throws an Illegal Argument exception when receiving an invalid DeviceRepository
     */
    @Test
    void whenGivenAnInvalidDeviceRepository_ConstructorThrowsIllegalArgument() {
        // Arrange
        SensorTypeRepositoryMem sensorTypeRepositoryMem = mock(SensorTypeRepositoryMem.class);
        SensorRepositoryMem sensorRepositoryMem = mock(SensorRepositoryMem.class);
        SensorFactoryImpl factory = mock(SensorFactoryImpl.class);
        String expected = "Invalid repository";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SensorServiceImpl(null, sensorTypeRepositoryMem, sensorRepositoryMem, factory));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the constructor throws an Illegal Argument exception when receiving an invalid SensorTypeRepository
     */
    @Test
    void whenGivenAnInvalidSensorTypeRepository_ConstructorThrowsIllegalArgument() {
        // Arrange
        DeviceRepositoryMem deviceRepository = mock(DeviceRepositoryMem.class);
        SensorRepositoryMem sensorRepositoryMem = mock(SensorRepositoryMem.class);
        SensorFactoryImpl factory = mock(SensorFactoryImpl.class);
        String expected = "Invalid repository";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SensorServiceImpl(deviceRepository, null, sensorRepositoryMem, factory));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the constructor throws an Illegal Argument exception when receiving an invalid SensorRepository
     */
    @Test
    void whenGivenAnInvalidSensorRepository_ConstructorThrowsIllegalArgument() {
        // Arrange
        DeviceRepositoryMem deviceRepository = mock(DeviceRepositoryMem.class);
        SensorTypeRepositoryMem sensorTypeRepositoryMem = mock(SensorTypeRepositoryMem.class);
        SensorFactoryImpl factory = mock(SensorFactoryImpl.class);
        String expected = "Invalid repository";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SensorServiceImpl(deviceRepository, sensorTypeRepositoryMem, null, factory));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the constructor throws an Illegal Argument exception when receiving an invalid Factory
     */
    @Test
    void whenGivenAnInvalidFactory_ConstructorThrowsIllegalArgument() {
        // Arrange
        DeviceRepositoryMem deviceRepository = mock(DeviceRepositoryMem.class);
        SensorTypeRepositoryMem sensorTypeRepositoryMem = mock(SensorTypeRepositoryMem.class);
        SensorRepositoryMem sensorRepositoryMem = mock(SensorRepositoryMem.class);
        String expected = "Invalid repository";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SensorServiceImpl(deviceRepository, sensorTypeRepositoryMem, sensorRepositoryMem, null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the addSensor method throws an Illegal Argument exception when receiving a null
     * SensorNameVO.
     * First, all the necessary mocks are created , both for the instantiation of the sensor and the service.
     * Then, an expected message is created as a string.
     * The method is called with a null SensorNameVO while asserting that an Illegal Argument Exception is thrown.
     * A result string is created from the exception message and, finally, the expected message compared to the result.
     */
    @Test
    void whenAddSensorMethodCalled_ifSensorNameIsNull_thenThrowsIllegalArgumentException() {
        //Arrange
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeId = mock(SensorTypeIDVO.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorFactory sensorFactory = mock(SensorFactory.class);

        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        String expected = "SensorName, DeviceIDVO and SensorTypeIDVO must not be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(null, deviceId, sensorTypeId));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown with the expected message
     * when the DeviceIDVO parameter is null.
     * In this test case, all dependencies of sensor service implementation are doubled.
     */

    @Test
    void addSensor_WhenNullDeviceIDVO_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        //Arrange
        String expected = "SensorName, DeviceIDVO and SensorTypeIDVO must not be null.";
        //Doubling the objects necessary to instantiate SensorServiceImpl
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);

        //Sensor service implementation instantiation
        SensorService sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble, sensorRepositoryDouble, sensorFactoryDouble);

        SensorNameVO sensorNameVODouble = mock(SensorNameVO.class);
        SensorTypeIDVO sensorTypeIDVODouble = mock(SensorTypeIDVO.class);

        //Act + Assert -> Assertion of the expected exception and storing the thrown exception message
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(sensorNameVODouble, null, sensorTypeIDVODouble));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown with the expected message
     * when the DeviceIDVO parameter is null.
     * In this test case, all dependencies of sensor service implementation are doubled.
     */
    @Test
    void addSensor_WhenNullSensorTypeIDVO_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        //Arrange
        String expected = "SensorName, DeviceIDVO and SensorTypeIDVO must not be null.";
        //Doubling the objects necessary to instantiate SensorServiceImpl
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);

        //Sensor service implementation instantiation
        SensorService sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble, sensorRepositoryDouble, sensorFactoryDouble);

        SensorNameVO sensorNameVODouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIDVODouble = mock(DeviceIDVO.class);

        //Act + Assert -> Assertion of the expected exception and storing the thrown exception message
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(sensorNameVODouble, deviceIDVODouble, null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown with the expected message
     * when the sensor addition is made to a non-existing device.
     * In this test case, all dependencies of sensor service implementation are doubled.
     * Device repository double is conditioned to return null when its passed a device id.
     * This conditioning is to simulate when it's passed a device id that has no match with any of
     * the existing device's id.
     */

    @Test
    void addSensor_WhenNonExistingDevice_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        //Arrange
        //Doubling the objects necessary to instantiate SensorServiceImpl
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);

        //Sensor service implementation instantiation
        SensorService sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble, sensorRepositoryDouble, sensorFactoryDouble);

        //Doubling the objects necessary to addSensor method
        SensorNameVO sensorNameVODouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIDVODouble = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeIDVODouble = mock(SensorTypeIDVO.class);

        //Conditioning deviceIDVO double to return an id when queried about it's id
        String idDevice = UUID.randomUUID().toString();
        when(deviceIDVODouble.getID()).thenReturn(idDevice);

        //Adjusting the expected exception message with correct device id
        String expected = "Device with ID: " + idDevice + " is not active.";

        //Conditioning device repository double to return null, when passed the deviceIDVO.
        when(deviceRepositoryDouble.findById(deviceIDVODouble)).thenReturn(null);

        //Act + Assert -> Assertion of the expected exception and storing the thrown exception message
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(sensorNameVODouble, deviceIDVODouble, sensorTypeIDVODouble));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that an IllegalArgumentException is thrown with the expected message
     * when the sensor addition is made to an inactive device.
     * In this test case, all dependencies of sensor service implementation are doubled.
     * A device double is conditioned to return false when it's queried about its state.
     */

    @Test
    void addSensor_WhenDeactivatedDevice_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        //Arrange
        //Doubling the objects necessary to instantiate SensorServiceImpl
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);

        //Sensor service implementation instantiation
        SensorService sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble, sensorRepositoryDouble, sensorFactoryDouble);

        //Doubling the objects necessary to addSensor method
        SensorNameVO sensorNameVODouble = mock(SensorNameVO.class);
        DeviceIDVO deviceIDVODouble = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeIDVODouble = mock(SensorTypeIDVO.class);


        //Conditioning deviceIDVO double to return an id when queried about it's id
        String idDevice = UUID.randomUUID().toString();
        when(deviceIDVODouble.getID()).thenReturn(idDevice);

        //Adjusting the expected exception message with correct device id
        String expected = "Device with ID: " + idDevice + " is not active.";

        //Doubling device and condition its behavior to return false when queried about it's status
        Device deviceDouble = mock(Device.class);
        when(deviceDouble.isActive()).thenReturn(false);

        //Conditioning device repository double to return the previously created device, when passed the deviceIDVO
        when(deviceRepositoryDouble.findById(deviceIDVODouble)).thenReturn(deviceDouble);

        //Act + Assert -> Assertion of the expected exception and storing the thrown exception message
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(sensorNameVODouble, deviceIDVODouble, sensorTypeIDVODouble));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }


    /**
     * This test ensures that the addSensor method throws an Illegal Argument exception when the sensor type
     * is not present.
     * First, all the necessary mocks are created for the instantiation of the sensor, the device and the service.
     * The sensor type is set to return a string with an invalid type when toString is called.
     * The device is set to return true when isActive is called.
     * The device repository is set to return the device when findById is called.
     * The sensor type repository is set to return false when isPresent is called.
     * An expected message is created as a string.
     * The add sensor method is called with the mocks while asserting that an Illegal Argument Exception is thrown.
     * A result string is created from the exception message and, finally, the expected message compared to the result.
     */
    @Test
    void whenAddSensorMethodCalled_ifSensorTypeIsNotPresent_thenThrowsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeId = mock(SensorTypeIDVO.class);
        when(sensorTypeId.getID()).thenReturn("UbiquitySensor");

        Device device = mock(Device.class);
        when(device.isActive()).thenReturn(true);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        when(deviceRepository.findById(deviceId)).thenReturn(device);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        when(sensorTypeRepository.isPresent(sensorTypeId)).thenReturn(false);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        SensorFactory sensorFactory = mock(SensorFactory.class);

        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        String expected = "Sensor type with ID UbiquitySensor is not present.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(sensorName, deviceId, sensorTypeId));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the addSensor method throws an Illegal Argument exception when the sensor is not created.
     * First, all the necessary mocks are created for the instantiation of the sensor, the device and the service.
     * The device is set to return true when isActive is called.
     * The device repository is set to return the mocked device when findById is called and the sensor type repository
     * set to return true when isPresent is called.
     * An Illegal Argument Exception is created with a message as a string.
     * Then, the sensor repository is set to throw this exception when save called and, finally, the sensor factory
     * set to return the sensor when createSensor called.
     * An expected message is created as a string and service instantiated with the necessary mocks previously created.
     * The addSensor method is called with the necessary mocks while asserting that an Illegal Argument Exception
     * is thrown and the message retrieved onto a string.
     * Finally, the expected message is compared to the result.
     */
    @Test
    void whenAddSensorMethodCalled_ifSensorIsNotCreated_thenReturnsIllegalArgumentException() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeId = mock(SensorTypeIDVO.class);

        Sensor sensor = mock(Sensor.class);

        Device device = mock(Device.class);
        when(device.isActive()).thenReturn(true);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        when(deviceRepository.findById(deviceId)).thenReturn(device);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        when(sensorTypeRepository.isPresent(sensorTypeId)).thenReturn(true);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        IllegalArgumentException argException = new IllegalArgumentException("Sensor is null");
        when(sensorRepository.save(sensor)).thenThrow(argException);
        SensorFactory sensorFactory = mock(SensorFactory.class);
        when(sensorFactory.createSensor(sensorName, deviceId, sensorTypeId)).thenReturn(sensor);

        String expected = "Sensor is null";

        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.addSensor(sensorName, deviceId, sensorTypeId));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the addSensor method returns an empty optional when the sensor is not saved.
     * First, all the necessary mocks are created for the instantiation of the sensor, the device and the service.
     * The device is set to return true when isActive is called.
     * The device repository is set to return the device when findById is called, the sensor type repository set to
     * return true when isPresent is called and the sensor repository set to return false when save is called.
     * Finally, the sensor factory is set to return the sensor when createSensor is called.
     * An expected optional is created as an empty optional and a result optional created from the addSensor method,
     * being these compared to assert the test.
     */
    @Test
    void whenAddSensorMethodCalled_ifSensorIsNotSaved_thenReturnsEmptyOptional() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeId = mock(SensorTypeIDVO.class);

        Sensor sensor = mock(Sensor.class);

        Device device = mock(Device.class);
        when(device.isActive()).thenReturn(true);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        when(deviceRepository.findById(deviceId)).thenReturn(device);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        when(sensorTypeRepository.isPresent(sensorTypeId)).thenReturn(true);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        when(sensorRepository.save(sensor)).thenReturn(false);
        SensorFactory sensorFactory = mock(SensorFactory.class);
        when(sensorFactory.createSensor(sensorName, deviceId, sensorTypeId)).thenReturn(sensor);

        Optional<Sensor> expected = Optional.empty();

        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        //Act
        Optional<Sensor> result = sensorService.addSensor(sensorName, deviceId, sensorTypeId);

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures that the addSensor method returns an optional with the sensor when the sensor is saved.
     * First, all the necessary mocks are created for the instantiation of the sensor, the device and the service.
     * The device is set to return true when isActive is called.
     * The device repository is set to return the device when findById is called, the sensor type repository set to
     * return true when isPresent is called, the sensor repository set to return true when save is called and the
     * sensor factory behaviour stubbed to return the mocked sensor.
     * An expected optional is created as an optional with the sensor and a result optional created from the addSensor
     * method, being these compared to assert the test.
     */
    @Test
    void whenAddSensorMethodCalled_ifSensorIsSaved_thenReturnsOptional() {
        //Arrange
        SensorNameVO sensorName = mock(SensorNameVO.class);
        DeviceIDVO deviceId = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeId = mock(SensorTypeIDVO.class);

        Sensor sensor = mock(Sensor.class);

        Device device = mock(Device.class);
        when(device.isActive()).thenReturn(true);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        when(deviceRepository.findById(deviceId)).thenReturn(device);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        when(sensorTypeRepository.isPresent(sensorTypeId)).thenReturn(true);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        when(sensorRepository.save(sensor)).thenReturn(true);
        SensorFactory sensorFactory = mock(SensorFactory.class);
        when(sensorFactory.createSensor(sensorName, deviceId, sensorTypeId)).thenReturn(sensor);

        Optional<Sensor> expected = Optional.of(sensor);

        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        //Act
        Optional<Sensor> result = sensorService.addSensor(sensorName, deviceId, sensorTypeId);

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the method getSensorById in the SensorServiceImpl class.
     * This test case checks if the method throws an IllegalArgumentException when the SensorIDVO parameter is null.
     * The expected behavior is that the method throws an IllegalArgumentException with the message "SensorIDVO must not be null."
     */
    @Test
    void whenGetSensorByIdMethodCalled_ifSensorIDVOIsNull_thenThrowsIllegalArgumentException() {
        //Arrange
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);
        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble, sensorRepositoryDouble, sensorFactoryDouble);

        String expected = "SensorIDVO must not be null.";

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.getSensorById(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the method getSensorById in the SensorServiceImpl class.
     * This test case checks if the method returns an empty Optional when the sensor with the provided SensorIDVO is not present in the repository.
     * The expected behavior is that the method returns an empty Optional.
     */
    @Test
    void whenGetSensorByIdMethodCalled_ifSensorIsNotPresent_thenReturnsEmptyOptional() {
        //Arrange
        SensorIDVO sensorIDVO = mock(SensorIDVO.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        when(sensorRepository.isPresent(sensorIDVO)).thenReturn(false);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        SensorFactory sensorFactory = mock(SensorFactory.class);
        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        Optional<Sensor> expected = Optional.empty();

        //Act
        Optional<Sensor> result = sensorService.getSensorById(sensorIDVO);

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the method getSensorById in the SensorServiceImpl class.
     * This test case checks if the method returns an Optional containing the sensor when the sensor with the provided SensorIDVO is present in the repository.
     * The expected behavior is that the method returns an Optional containing the sensor.
     */
    @Test
    void whenGetSensorByIdMethodCalled_ifSensorIsPresent_thenReturnsOptionalWithSensor() {
        //Arrange
        SensorIDVO sensorIDVO = mock(SensorIDVO.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        Sensor sensor = mock(Sensor.class);
        when(sensorRepository.isPresent(sensorIDVO)).thenReturn(true);
        when(sensorRepository.findById(sensorIDVO)).thenReturn(sensor);

        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepository = mock(SensorTypeRepository.class);
        SensorFactory sensorFactory = mock(SensorFactory.class);
        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepository, sensorTypeRepository, sensorRepository, sensorFactory);

        Optional<Sensor> expected = Optional.of(sensor);

        //Act
        Optional<Sensor> result = sensorService.getSensorById(sensorIDVO);

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Unit test for verifying that SensorServiceImpl's getListOfSensorsInADevice method
     * throws an IllegalArgumentException when given a null DeviceIDVO.
     *
     * <p>This test ensures that the getListOfSensorsInADevice method correctly handles
     * cases where the provided DeviceIDVO is null.</p>
     */
    @Test
    void whenGivenNullDeviceIDVO_thenThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Device ID cannot be null";
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);
        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble,
                sensorRepositoryDouble, sensorFactoryDouble);
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.getListOfSensorsInADevice(null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Unit test for verifying that SensorServiceImpl's getListOfSensorsInADevice method
     * throws an IllegalArgumentException when given a DeviceIDVO for a non-existent device.
     *
     * <p>This test ensures that the getListOfSensorsInADevice method correctly interacts
     * with the DeviceRepository to handle cases where the device is not found.</p>
     */
    @Test
    void whenGivenDeviceIDVOForANonExistantDevice_thenThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Device not found";
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn("1fa85f64-5717-4562-b3fc-2c963f66afa6");

        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);
        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepositoryDouble, sensorTypeRepositoryDouble,
                sensorRepositoryDouble, sensorFactoryDouble);
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sensorService.getListOfSensorsInADevice(deviceIDVO));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Unit test for verifying that SensorServiceImpl's getListOfSensorsInADevice method
     * returns a list of sensors when given a valid DeviceIDVO and a matching device is found.
     *
     * <p>This test ensures that the getListOfSensorsInADevice method correctly interacts
     * with the SensorRepository and DeviceRepository to return the expected list of sensors.</p>
     */
    @Test
    void whenGivenValidDeviceIDVOAndMatchIsFound_thenReturnsListOfSensors(){
        // Arrange
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn("1fa85f64-5717-4562-b3fc-2c963f66afa6");

        Sensor sensor1 = mock(Sensor.class);
        SensorNameVO sensorNameVO1 = mock(SensorNameVO.class);
        when(sensor1.getSensorName()).thenReturn(sensorNameVO1);
        when(sensorNameVO1.getValue()).thenReturn("sensor1");

        Sensor sensor2 = mock(Sensor.class);
        SensorNameVO sensorNameVO2 = mock(SensorNameVO.class);
        when(sensor2.getSensorName()).thenReturn(sensorNameVO2);
        when(sensorNameVO2.getValue()).thenReturn("sensor2");

        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensor1);
        sensorList.add(sensor2);

        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorTypeRepository sensorTypeRepositoryDouble = mock(SensorTypeRepository.class);
        SensorFactory sensorFactoryDouble = mock(SensorFactory.class);
        SensorServiceImpl sensorService = new SensorServiceImpl(deviceRepositoryDouble,sensorTypeRepositoryDouble,
                sensorRepositoryDouble, sensorFactoryDouble);

        when(deviceRepositoryDouble.isPresent(deviceIDVO)).thenReturn(true);
        when(sensorRepositoryDouble.findByDeviceID(deviceIDVO)).thenReturn(sensorList);

        // Act
        List<Sensor> resultList = sensorService.getListOfSensorsInADevice(deviceIDVO);
        Sensor resultSensor1 = resultList.get(0);
        Sensor resultSensor2 = resultList.get(1);

        // Assert
        assertEquals(resultList.size(),2);
        assertEquals(resultSensor1.getSensorName().getValue(), "sensor1");
        assertEquals(resultSensor2.getSensorName().getValue(), "sensor2");
    }
}

