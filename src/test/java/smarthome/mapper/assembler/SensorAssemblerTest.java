package smarthome.mapper.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensor.*;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SensorAssemblerTest {

    private String sensorName;
    private String sensorTypeID;
    private String deviceID;

    @BeforeEach
    void setUp() {
        sensorName = "Sensor1";
        sensorTypeID = "TemperatureSensor";
        deviceID = "123e4567-e89b-12d3-a456-426655440002";
    }

    /**
     * Test case to verify that the toDomain method of SensorAssembler correctly converts a SensorDataModel object to a
     * Sensor object.
     * It checks if the Sensor object created has the same attributes as the SensorDataModel object.
     * First, it creates a Sensor object with the predefined values.
     * Then, it creates a SensorDataModel object from the Sensor Object.
     * It initializes a SensorFactory object with the path to the config.properties file.
     * Finally, it calls the toDomain method of SensorAssembler to convert the SensorDataModel object to a Sensor object.
     */
    @Test
    void givenSensorDataModel_whenConvertingToDomain_thenReturnSensorObject() {
        // Arrange
        SensorNameVO sensorNameVO = new SensorNameVO(sensorName);
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        Sensor sensor = new TemperatureSensor(sensorNameVO, deviceIDVO, sensorTypeIDVO);

        SensorDataModel sensorDataModel = new SensorDataModel(sensor);

        String path = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(path);

        // Act
        Sensor newSensor = SensorAssembler.toDomain(sensorFactory, sensorDataModel);

        // Assert
        assertEquals(sensorDataModel.getSensorId(), newSensor.getId().getID());
        assertEquals(sensorDataModel.getSensorName(), newSensor.getSensorName().getValue());
        assertEquals(sensorDataModel.getSensorTypeID(), newSensor.getSensorTypeID().getID());
        assertEquals(sensorDataModel.getDeviceID(), newSensor.getDeviceID().getID());
    }


    /**
     * Test case to verify that the toDomain method of SensorAssembler correctly converts a list of SensorDataModel objects to a
     * list of Sensor objects.
     * It checks if the Sensor objects created have the same attributes as the SensorDataModel objects.
     * First, it creates two Sensor objects with predefined values.
     * Then, it creates two SensorDataModel objects from the Sensor Objects.
     * It initializes a SensorFactory object with the path to the config.properties file.
     * It creates a list of SensorDataModel objects and adds the two SensorDataModel objects to it.
     * Finally, it calls the toDomain method of SensorAssembler to convert the list of SensorDataModel objects to a list
     * of Sensor objects.
     */
    @Test
    void givenSensorDataModelList_whenConvertingToDomain_thenReturnIterableWithSensorObjects() {
        // Arrange
        SensorNameVO sensorNameVO = new SensorNameVO(sensorName);
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.fromString(deviceID));

        Sensor sensor = new TemperatureSensor(sensorNameVO, deviceIDVO, sensorTypeIDVO);

        SensorNameVO secondSensorNameVO = new SensorNameVO("Sensor2");
        SensorTypeIDVO secondSensorTypeIDVO = new SensorTypeIDVO("HumiditySensor");
        DeviceIDVO secondDeviceIDVO = new DeviceIDVO(UUID.fromString("123e4567-e89b-12d3-a456-426655440003"));

        Sensor secondSensor = new HumiditySensor(secondSensorNameVO, secondDeviceIDVO, secondSensorTypeIDVO);

        SensorDataModel sensorDataModel = new SensorDataModel(sensor);
        SensorDataModel secondSensorDataModel = new SensorDataModel(secondSensor);

        String path = "config.properties";
        SensorFactory sensorFactory = new SensorFactoryImpl(path);

        List<SensorDataModel> sensorDataModelList = new ArrayList<>();
        sensorDataModelList.add(sensorDataModel);
        sensorDataModelList.add(secondSensorDataModel);

        // Act
        Iterable<Sensor> sensorIterable = SensorAssembler.toDomain(sensorFactory, sensorDataModelList);

        // Assert
        List<Sensor> sensorList = new ArrayList<>();
        sensorIterable.forEach(sensorList::add);

        Sensor firstSensor = sensorList.get(0);

        assertEquals(sensorDataModel.getSensorId(), firstSensor.getId().getID());
        assertEquals(sensorDataModel.getSensorName(), firstSensor.getSensorName().getValue());
        assertEquals(sensorDataModel.getSensorTypeID(), firstSensor.getSensorTypeID().getID());
        assertEquals(sensorDataModel.getDeviceID(), firstSensor.getDeviceID().getID());

        Sensor anotherSensor = sensorList.get(1);

        assertEquals(secondSensorDataModel.getSensorId(), anotherSensor.getId().getID());
        assertEquals(secondSensorDataModel.getSensorName(), anotherSensor.getSensorName().getValue());
        assertEquals(secondSensorDataModel.getSensorTypeID(), anotherSensor.getSensorTypeID().getID());
        assertEquals(secondSensorDataModel.getDeviceID(), anotherSensor.getDeviceID().getID());
    }

}