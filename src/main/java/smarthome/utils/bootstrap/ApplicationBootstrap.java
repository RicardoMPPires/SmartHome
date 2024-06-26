package smarthome.utils.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.RollerBlindActuator;
import smarthome.domain.device.Device;
import smarthome.domain.house.House;
import smarthome.domain.log.Log;
import smarthome.domain.room.Room;
import smarthome.domain.sensor.EnergyConsumptionSensor;
import smarthome.domain.sensor.SunriseSensor;
import smarthome.domain.sensor.SunsetSensor;
import smarthome.domain.sensor.TemperatureSensor;
import smarthome.domain.sensor.sensorvalues.EnergyConsumptionValue;
import smarthome.domain.sensor.sensorvalues.TemperatureValue;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.housevo.*;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;
import smarthome.domain.vo.roomvo.*;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.persistence.*;

import java.util.UUID;

/**
 * The ApplicationBootstrap Class is a Spring Boot component that implements CommandLineRunner to initialize and save
 * default data into various repositories when the application starts. It creates and persists a default house,
 * several rooms, devices, sensors, and an actuator, ensuring the application has initial required data to work with.
 * A default House is added for its location to be configured as desired;
 * Three Rooms are added in order to add strategic devices to meet use cases needs (Electrical Circuit Room, Garden
 * and Kitchen);
 * Three Devices are added, one to each Room mentioned above. A Grid Power Meter, two Devices that have the
 * capacity to measure the surrounding temperature, and another one that adjusts the roller blinds;
 * An Energy Consumption Sensor is added to the Power Meter Device;
 * The temperature controller Devices get a Temperature Sensor;
 * An Actuator is placed on the roller blind Device controller.
 */
@Component
public class ApplicationBootstrap implements CommandLineRunner {

    HouseRepository houseRepository;

    RoomRepository roomRepository;

    DeviceRepository deviceRepository;

    SensorRepository sensorRepository;

    ActuatorRepository actuatorRepository;

    LogRepository logRepository;


    public ApplicationBootstrap(HouseRepository houseRepository, RoomRepository roomRepository, DeviceRepository deviceRepository,
                                SensorRepository sensorRepository, ActuatorRepository actuatorRepository, LogRepository logRepository) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.actuatorRepository = actuatorRepository;
        this.logRepository = logRepository;
    }

    @Override
    public void run(String... args) {

        //Add a House to the System:
        LocationVO locationVO = new LocationVO(
                new AddressVO(new DoorVO("default door"), new StreetVO("default street"), new CityVO("default city"),
                        new CountryVO("Portugal"), new PostalCodeVO("PT-1234-567")),
                new GpsVO(new LatitudeVO(0), new LongitudeVO(0)));

        House defaultHouse = new House(locationVO);
        houseRepository.save(defaultHouse);

        //Add Rooms to the System:
        String houseId = defaultHouse.getId().getID();
        Room electricalRoom = new Room(
                new RoomNameVO("Electrical Circuit Room"), new RoomFloorVO(0),
                new RoomDimensionsVO(new RoomLengthVO(2), new RoomWidthVO(2), new RoomHeightVO(2)),
                new HouseIDVO(UUID.fromString(houseId)));

        Room outdoorRoom = new Room(
                new RoomNameVO("Garden"), new RoomFloorVO(-1),
                new RoomDimensionsVO(new RoomLengthVO(40), new RoomWidthVO(70), new RoomHeightVO(0)),
                new HouseIDVO(UUID.fromString(houseId)));

        Room indoorRoom = new Room(
                new RoomNameVO("Kitchen"), new RoomFloorVO(1),
                new RoomDimensionsVO(new RoomLengthVO(7), new RoomWidthVO(6), new RoomHeightVO(2)),
                new HouseIDVO(UUID.fromString(houseId)));

        roomRepository.save(electricalRoom);
        roomRepository.save(outdoorRoom);
        roomRepository.save(indoorRoom);

        //Add Devices to the System:
        String electricalRoomId = electricalRoom.getId().getID();
        String outdoorRoomId = outdoorRoom.getId().getID();
        String indoorRoomId = indoorRoom.getId().getID();

        Device outdoorDevice = new Device(new DeviceNameVO("Garden Temperature Controller"),
                new DeviceModelVO("WS-5050"),
                new RoomIDVO(UUID.fromString(outdoorRoomId)));

        Device indoorDeviceOne = new Device(new DeviceNameVO("Kitchen Temperature Controller"),
                new DeviceModelVO("WS-2000"),
                new RoomIDVO(UUID.fromString(indoorRoomId)));

        Device indoorDeviceTwo = new Device(new DeviceNameVO("Kitchen Natural Light Controller"),
                new DeviceModelVO("RB-370"),
                new RoomIDVO(UUID.fromString(indoorRoomId)));

        Device gridPowerMeter = new Device(new DeviceNameVO("Grid Power Meter"),
                new DeviceModelVO("e-Redes"),
                new RoomIDVO(UUID.fromString(electricalRoomId)));

        Device powerSource = new Device(new DeviceNameVO("Power Source"),
                new DeviceModelVO("787B"),
                new RoomIDVO(UUID.fromString(electricalRoomId)));



        deviceRepository.save(outdoorDevice);
        deviceRepository.save(indoorDeviceOne);
        deviceRepository.save(indoorDeviceTwo);
        deviceRepository.save(gridPowerMeter);
        deviceRepository.save(powerSource);

        System.setProperty("Grid Power Meter device", gridPowerMeter.getId().getID());

        //Add Temperature Sensors and Power Consumption Sensor:
        String outdoorDevId = outdoorDevice.getId().getID();
        String indoorDevIdOne = indoorDeviceOne.getId().getID();
        String indoorDevIdTwo = indoorDeviceTwo.getId().getID();
        String gripPowerDevId = gridPowerMeter.getId().getID();
        String powerSourceID = powerSource.getId().getID();

        TemperatureSensor outTempSensor = new TemperatureSensor(
                new SensorNameVO("Outdoor Temperature Sensor"),
                new DeviceIDVO(UUID.fromString(outdoorDevId)),
                new SensorTypeIDVO("TemperatureSensor"));

        TemperatureSensor inTempSensor = new TemperatureSensor(
                new SensorNameVO("Indoor Temperature Sensor"),
                new DeviceIDVO(UUID.fromString(indoorDevIdOne)),
                new SensorTypeIDVO("TemperatureSensor"));

        EnergyConsumptionSensor gridPowerMeterEnergyConsumptionSensor = new EnergyConsumptionSensor(
                new SensorNameVO("Energy Consumption Sensor"),
                new DeviceIDVO(UUID.fromString(gripPowerDevId)),
                new SensorTypeIDVO("EnergyConsumptionSensor"));

        EnergyConsumptionSensor powerSourceEnergyConsumptionSensor = new EnergyConsumptionSensor(
                new SensorNameVO("Energy Consumption Sensor"),
                new DeviceIDVO(UUID.fromString(powerSourceID)),
                new SensorTypeIDVO("EnergyConsumptionSensor"));

        SunriseSensor sunriseSensor = new SunriseSensor(
                new SensorNameVO("Outdoor SunRise Sensor"),
                new DeviceIDVO(UUID.fromString(outdoorDevId)),
                new SensorTypeIDVO("SunriseSensor"));

        SunsetSensor sunsetSensor = new SunsetSensor(
                new SensorNameVO("Outdoor SunSet Sensor"),
                new DeviceIDVO(UUID.fromString(outdoorDevId)),
                new SensorTypeIDVO("SunsetSensor"));

        sensorRepository.save(outTempSensor);
        sensorRepository.save(inTempSensor);
        sensorRepository.save(gridPowerMeterEnergyConsumptionSensor);
        sensorRepository.save(powerSourceEnergyConsumptionSensor);
        sensorRepository.save(sunriseSensor);
        sensorRepository.save(sunsetSensor);

        System.setProperty("Grid Power Meter sensor", gridPowerMeterEnergyConsumptionSensor.getId().getID());
        System.setProperty("Grid Power Meter sensor type", gridPowerMeterEnergyConsumptionSensor.getSensorTypeID().getID());


        //Add Roller Blind Actuator:
        Actuator rollerBlindActuator = new RollerBlindActuator(
                new ActuatorNameVO("Kitchen Roller"),
                new ActuatorTypeIDVO("RollerBlindActuator"),
                new DeviceIDVO(UUID.fromString(indoorDevIdTwo)));

        actuatorRepository.save(rollerBlindActuator);

        //Add Logs for US34:
        String temperatureReading1 = "20";
        TemperatureValue temperature1 = new TemperatureValue(temperatureReading1);
        SensorIDVO outTempSensorID1 = outTempSensor.getId();
        DeviceIDVO outdoorDeviceID1 = outdoorDevice.getId();
        SensorTypeIDVO outTempSensorTypeID1 = outTempSensor.getSensorTypeID();
        TimeStampVO outTempDate1 = new TimeStampVO("2024-01-01", "12:00:00");
        LogIDVO outTempLogID1 = new LogIDVO(UUID.randomUUID());
        Log outTempLog1 = new Log(outTempLogID1, outTempDate1, temperature1, outTempSensorID1, outdoorDeviceID1, outTempSensorTypeID1);

        String temperatureReading2 = "18";
        TemperatureValue temperature2 = new TemperatureValue(temperatureReading2);
        SensorIDVO outTempSensorID2 = outTempSensor.getId();
        DeviceIDVO outdoorDeviceID2 = outdoorDevice.getId();
        SensorTypeIDVO outTempSensorTypeID2 = outTempSensor.getSensorTypeID();
        TimeStampVO outTempDate2 = new TimeStampVO("2024-01-01", "12:05:00");
        LogIDVO outTempLogID2 = new LogIDVO(UUID.randomUUID());
        Log outTempLog2 = new Log(outTempLogID2, outTempDate2, temperature2, outTempSensorID2, outdoorDeviceID2, outTempSensorTypeID2);

        String temperatureReading3 = "25";
        TemperatureValue temperature3 = new TemperatureValue(temperatureReading3);
        SensorIDVO outTempSensorID3 = outTempSensor.getId();
        DeviceIDVO outdoorDeviceID3 = outdoorDevice.getId();
        SensorTypeIDVO outTempSensorTypeID3 = outTempSensor.getSensorTypeID();
        TimeStampVO outTempDate3 = new TimeStampVO("2024-01-01", "12:10:00");
        LogIDVO outTempLogID3 = new LogIDVO(UUID.randomUUID());
        Log outTempLog3 = new Log(outTempLogID3, outTempDate3, temperature3, outTempSensorID3, outdoorDeviceID3, outTempSensorTypeID3);

        String temperatureReading4 = "30";
        TemperatureValue temperature4 = new TemperatureValue(temperatureReading4);
        SensorIDVO outTempSensorID4 = outTempSensor.getId();
        DeviceIDVO outdoorDeviceID4 = outdoorDevice.getId();
        SensorTypeIDVO outTempSensorTypeID4 = outTempSensor.getSensorTypeID();
        TimeStampVO outTempDate4 = new TimeStampVO("2024-01-01", "12:45:00");
        LogIDVO outTempLogID4 = new LogIDVO(UUID.randomUUID());
        Log outTempLog4 = new Log(outTempLogID4, outTempDate4, temperature4, outTempSensorID4, outdoorDeviceID4, outTempSensorTypeID4);

        String temperatureReading5 = "18";
        TemperatureValue temperature5 = new TemperatureValue(temperatureReading5);
        SensorIDVO inTempSensorID1 = inTempSensor.getId();
        DeviceIDVO indoorDeviceID1 = indoorDeviceOne.getId();
        SensorTypeIDVO inTempSensorTypeID1 = inTempSensor.getSensorTypeID();
        TimeStampVO inTempDate1 = new TimeStampVO("2024-01-01", "12:03:00");
        LogIDVO inTempLogID1 = new LogIDVO(UUID.randomUUID());
        Log inTempLog1 = new Log(inTempLogID1, inTempDate1, temperature5, inTempSensorID1, indoorDeviceID1, inTempSensorTypeID1);

        String temperatureReading6 = "19";
        TemperatureValue temperature6 = new TemperatureValue(temperatureReading6);
        SensorIDVO inTempSensorID2 = inTempSensor.getId();
        DeviceIDVO indoorDeviceID2 = indoorDeviceOne.getId();
        SensorTypeIDVO inTempSensorTypeID2 = inTempSensor.getSensorTypeID();
        TimeStampVO inTempDate2 = new TimeStampVO("2024-01-01", "12:06:00");
        LogIDVO inTempLogID2 = new LogIDVO(UUID.randomUUID());
        Log inTempLog2 = new Log(inTempLogID2, inTempDate2, temperature6, inTempSensorID2, indoorDeviceID2, inTempSensorTypeID2);

        String temperatureReading7 = "20";
        TemperatureValue temperature7 = new TemperatureValue(temperatureReading7);
        SensorIDVO inTempSensorID3 = inTempSensor.getId();
        DeviceIDVO indoorDeviceID3 = indoorDeviceOne.getId();
        SensorTypeIDVO inTempSensorTypeID3 = inTempSensor.getSensorTypeID();
        TimeStampVO inTempDate3 = new TimeStampVO("2024-01-01", "12:10:00");
        LogIDVO inTempLogID3 = new LogIDVO(UUID.randomUUID());
        Log inTempLog3 = new Log(inTempLogID3, inTempDate3, temperature7, inTempSensorID3, indoorDeviceID3, inTempSensorTypeID3);

        String temperatureReading8 = "25";
        TemperatureValue temperature8 = new TemperatureValue(temperatureReading8);
        SensorIDVO inTempSensorID4 = inTempSensor.getId();
        DeviceIDVO indoorDeviceID4 = indoorDeviceOne.getId();
        SensorTypeIDVO inTempSensorTypeID4 = inTempSensor.getSensorTypeID();
        TimeStampVO inTempDate4 = new TimeStampVO("2024-01-01", "12:15:00");
        LogIDVO inTempLogID4 = new LogIDVO(UUID.randomUUID());
        Log inTempLog4 = new Log(inTempLogID4, inTempDate4, temperature8, inTempSensorID4, indoorDeviceID4, inTempSensorTypeID4);

        logRepository.save(outTempLog1);
        logRepository.save(outTempLog2);
        logRepository.save(outTempLog3);
        logRepository.save(outTempLog4);
        logRepository.save(inTempLog1);
        logRepository.save(inTempLog2);
        logRepository.save(inTempLog3);
        logRepository.save(inTempLog4);

        // Add Logs for US36:
        String reading1 = "100";
        EnergyConsumptionValue energyConsumption1 = new EnergyConsumptionValue(reading1);
        SensorIDVO sensorID1 = gridPowerMeterEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID1 = gridPowerMeter.getId();
        SensorTypeIDVO sensorTypeID1 = gridPowerMeterEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date1 = new TimeStampVO("2024-01-01", "12:00:00");
        LogIDVO logID1 = new LogIDVO(UUID.randomUUID());
        Log log1 = new Log(logID1, date1, energyConsumption1, sensorID1, deviceID1, sensorTypeID1);

        String reading2 = "50";
        EnergyConsumptionValue energyConsumption2 = new EnergyConsumptionValue(reading2);
        SensorIDVO sensorID2 = gridPowerMeterEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID2 = gridPowerMeter.getId();
        SensorTypeIDVO sensorTypeID2 = gridPowerMeterEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date2 = new TimeStampVO("2024-01-01", "12:10:00");
        LogIDVO logID2 = new LogIDVO(UUID.randomUUID());
        Log log2 = new Log(logID2, date2, energyConsumption2, sensorID2, deviceID2, sensorTypeID2);

        String reading3 = "75";
        EnergyConsumptionValue energyConsumption3 = new EnergyConsumptionValue(reading3);
        SensorIDVO sensorID3 = gridPowerMeterEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID3 = gridPowerMeter.getId();
        SensorTypeIDVO sensorTypeID3 = gridPowerMeterEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date3 = new TimeStampVO("2024-01-01", "12:15:00");
        LogIDVO logID3 = new LogIDVO(UUID.randomUUID());
        Log log3 = new Log(logID3, date3, energyConsumption3, sensorID3, deviceID3, sensorTypeID3);

        String reading4 = "300";
        EnergyConsumptionValue energyConsumption4 = new EnergyConsumptionValue(reading4);
        SensorIDVO sensorID4 = gridPowerMeterEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID4 = gridPowerMeter.getId();
        SensorTypeIDVO sensorTypeID4 = gridPowerMeterEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date4 = new TimeStampVO("2024-01-01", "12:45:00");
        LogIDVO logID4 = new LogIDVO(UUID.randomUUID());
        Log log4 = new Log(logID4, date4, energyConsumption4, sensorID4, deviceID4, sensorTypeID4);

        String reading5 = "-25";
        EnergyConsumptionValue energyConsumption5 = new EnergyConsumptionValue(reading5);
        SensorIDVO sensorID5 = powerSourceEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID5 = powerSource.getId();
        SensorTypeIDVO sensorTypeID5 = powerSourceEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date5 = new TimeStampVO("2024-01-01", "12:03:00");
        LogIDVO logID5 = new LogIDVO(UUID.randomUUID());
        Log log5 = new Log(logID5, date5, energyConsumption5, sensorID5, deviceID5, sensorTypeID5);

        String reading6 = "-30";
        EnergyConsumptionValue energyConsumption6 = new EnergyConsumptionValue(reading6);
        SensorIDVO sensorID6 = powerSourceEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID6 = powerSource.getId();
        SensorTypeIDVO sensorTypeID6 = powerSourceEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date6 = new TimeStampVO("2024-01-01", "12:09:00");
        LogIDVO logID6 = new LogIDVO(UUID.randomUUID());
        Log log6 = new Log(logID6, date6, energyConsumption6, sensorID6, deviceID6, sensorTypeID6);

        String reading7 = "-5";
        EnergyConsumptionValue energyConsumption7 = new EnergyConsumptionValue(reading7);
        SensorIDVO sensorID7 = powerSourceEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID7 = powerSource.getId();
        SensorTypeIDVO sensorTypeID7 = powerSourceEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date7 = new TimeStampVO("2024-01-01", "12:12:00");
        LogIDVO logID7 = new LogIDVO(UUID.randomUUID());
        Log log7 = new Log(logID7, date7, energyConsumption7, sensorID7, deviceID7, sensorTypeID7);

        String reading8 = "-50";
        EnergyConsumptionValue energyConsumption8 = new EnergyConsumptionValue(reading8);
        SensorIDVO sensorID8 = powerSourceEnergyConsumptionSensor.getId();
        DeviceIDVO deviceID8 = powerSource.getId();
        SensorTypeIDVO sensorTypeID8 = powerSourceEnergyConsumptionSensor.getSensorTypeID();
        TimeStampVO date8 = new TimeStampVO("2024-01-01", "12:50:00");
        LogIDVO logID8 = new LogIDVO(UUID.randomUUID());
        Log log8 = new Log(logID8, date8, energyConsumption8, sensorID8, deviceID8, sensorTypeID8);

        logRepository.save(log1);
        logRepository.save(log2);
        logRepository.save(log3);
        logRepository.save(log4);
        logRepository.save(log5);
        logRepository.save(log6);
        logRepository.save(log7);
        logRepository.save(log8);
    }
}

