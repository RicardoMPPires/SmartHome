package smarthome.domain.log;

import org.junit.jupiter.api.Test;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.sensor.sensorvalues.TemperatureValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogFactoryTest {

    /**
     * This test case verifies if the createLog method of the LogFactoryImpl class throws an IllegalArgumentException
     * when one or more of the parameters are null. The IllegalArgumentException is expected to be thrown as the LogFactoryImpl
     * class does not allow creation of a Log object with null parameters.
     */
    @Test
    void whenCreateLogWithInvalidParameters_ShouldPropagateIllegalArgumentException() {
        //Arrange
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("123");
        String expected = "Invalid parameters.";
        LogFactoryImpl logFactory = new LogFactoryImpl();

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            logFactory.createLog(null, sensorID, deviceID, sensorTypeID));

        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test case verifies if the overloaded createLog method of the LogFactoryImpl class throws an IllegalArgumentException
     * when one or more of the parameters are null. The IllegalArgumentException is expected to be thrown as the LogFactoryImpl
     * class does not allow creation of a Log object with null parameters.
     */
    @Test
    void whenCreateLogUsingOverloadedMethodWithInvalidParameters_ShouldPropagateIllegalArgumentException() {
        //Arrange
        LogIDVO logID = new LogIDVO(UUID.randomUUID());
        TimeStampVO time = new TimeStampVO(LocalDateTime.now());
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("123");
        String expected = "Invalid parameters.";
        LogFactoryImpl logFactory = new LogFactoryImpl();

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            logFactory.createLog(logID, time, null, sensorID, deviceID, sensorTypeID));


        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test case verifies if the createLog method of the LogFactoryImpl class creates a valid Log instance
     * when all the parameters are valid. The Log object created should have the same values as the input parameters.
     */
    @Test
    void whenCreateLogWithValidParameters_ShouldCreateLog() {
        //Arrange
        TemperatureValue reading = new TemperatureValue("20.0");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("123");

        //Act
        LogFactoryImpl logFactory = new LogFactoryImpl();
        Log log = logFactory.createLog(reading, sensorID, deviceID, sensorTypeID);

        //Assert
        assertEquals(reading, log.getReading());
        assertEquals(sensorID, log.getSensorID());
        assertEquals(deviceID, log.getDeviceID());
        assertEquals(sensorTypeID, log.getSensorTypeID());
    }

    /**
     * This test case verifies if the overloaded createLog method of the LogFactoryImpl class creates a valid Log instance
     * when all the parameters are valid. The Log object created should have the same values as the input parameters,
     * including the LogID and timestamp.
     */
    @Test
    void whenCreateLogWithValidParameters_ShouldCreateLogWithLogID() {
        //Arrange
        LogIDVO logID = new LogIDVO(UUID.randomUUID());
        TimeStampVO time = new TimeStampVO(LocalDateTime.now());
        SensorValueObject<?> reading = new TemperatureValue("20.0");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID =  new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("123");

        //Act
        LogFactoryImpl logFactory = new LogFactoryImpl();
        Log log = logFactory.createLog(logID, time, reading, sensorID, deviceID, sensorTypeID);

        //Assert
        assertEquals(logID, log.getId());
        assertEquals(reading, log.getReading());
        assertEquals(sensorID, log.getSensorID());
        assertEquals(deviceID, log.getDeviceID());
        assertEquals(sensorTypeID, log.getSensorTypeID());

    }
}