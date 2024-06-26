package smarthome.mapper.assembler;

import org.junit.jupiter.api.Test;
import smarthome.domain.log.Log;
import smarthome.domain.log.LogFactoryImpl;
import smarthome.domain.sensor.sensorvalues.HumidityValue;
import smarthome.domain.sensor.sensorvalues.SensorValueFactoryImpl;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.persistence.jpa.datamodel.LogDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogAssemblerTest {

    /**
     * SUT -> Log entity, VOs, DataModel, Factories and LogMapper.
     * Tests the behavior of the toDomain method in the LogAssembler class when provided with a valid LogDataModel object.
     * <p>
     * This test verifies that the toDomain method correctly converts a valid LogDataModel object into a Log domain object,
     * ensuring that the resulting Log object has the same values as the original LogDataModel object.
     * </p>
     * <p>
     * The test sets up a LogDataModel object representing a log with valid values.
     * It then arranges Log and LogDataModel objects, initializes factories, and converts the LogDataModel object to a Log domain object.
     * The test asserts that the resulting Log domain object has the same values as the original LogDataModel object.
     * </p>
     * <p>
     * The following values are compared between the original and converted Log objects:
     * <ul>
     *     <li>LogID</li>
     *     <li>Reading value</li>
     *     <li>Timestamp</li>
     *     <li>SensorID</li>
     *     <li>DeviceID</li>
     *     <li>SensorTypeID</li>
     * </ul>
     * </p>
     */
    @Test
    void whenDataModelIsValid_toDomainReturnsValidObjectWithSameValues(){
        // Arrange
        SensorValueObject<?> reading = new HumidityValue("50");
        SensorIDVO sensorIDVO = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO("HumiditySensor");
        Log logToPersist = new Log(reading,sensorIDVO,deviceIDVO,sensorTypeIDVO);
        String expectedLogID = logToPersist.getId().getID();
        String expectedReading = logToPersist.getReading().getValue().toString();
        String expectedTimestamp = logToPersist.getTime().toString();
        String expectedSensorID = logToPersist.getSensorID().getID();
        String expectedDeviceID = logToPersist.getDeviceID().getID();
        String expectedSensorTypeID = logToPersist.getSensorTypeID().getID();

        LogDataModel dataModel = new LogDataModel(logToPersist);

        LogFactoryImpl logFactory = new LogFactoryImpl();

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        Log logFromPersistence = LogAssembler.toDomain(logFactory,valueFactory,dataModel);

        // Act
        String resultLogID = logFromPersistence.getId().getID();
        String resultReading = logFromPersistence.getReading().getValue().toString();
        String resultTimestamp = logFromPersistence.getTime().toString();
        String resultSensorID = logFromPersistence.getSensorID().getID();
        String resultDeviceID = logFromPersistence.getDeviceID().getID();
        String resultSensorTypeID = logFromPersistence.getSensorTypeID().getID();

        // Assert
        assertEquals(expectedLogID,resultLogID);
        assertEquals(expectedReading,resultReading);
        assertEquals(expectedTimestamp,resultTimestamp);
        assertEquals(expectedSensorID,resultSensorID);
        assertEquals(expectedDeviceID,resultDeviceID);
        assertEquals(expectedSensorTypeID,resultSensorTypeID);
    }

    /**
     * SUT -> Log entity, VOs, DataModel, Factories and LogMapper.
     * Tests the behavior of the toDomain method in the LogAssembler class when provided with two valid LogDataModel objects.
     * <p>
     * This test verifies that the toDomain method correctly converts two valid LogDataModel objects into a list of Log domain objects,
     * ensuring that the resulting Log objects have the same values as the original LogDataModel objects.
     * </p>
     * <p>
     * The test sets up two LogDataModel objects representing logs with identical values except for the LogID and Timestamp.
     * It then arranges Log and LogDataModel objects, initializes factories, and converts the LogDataModel objects to Log domain objects.
     * The test asserts that the resulting Log domain objects have the same values as the original LogDataModel objects.
     * </p>
     * <p>
     * The following values are compared between the original and converted Log objects:
     * <ul>
     *     <li>LogID</li>
     *     <li>Reading value</li>
     *     <li>Timestamp</li>
     *     <li>SensorID</li>
     *     <li>DeviceID</li>
     *     <li>SensorTypeID</li>
     * </ul>
     * </p>
     */
    @Test
    void givenTwoValidDataModels_toDomainReturnsValidListWithObjectWithSameValues(){
        // Arrange

        // Arranging data models with 2 Logs - The only differing values will be the LogID and Timestamp
        SensorValueObject<?> reading1 = new HumidityValue("50");
        SensorIDVO sensorIDVO1 = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceIDVO1 = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeIDVO1 = new SensorTypeIDVO("HumiditySensor");
        Log logToPersist1 = new Log(reading1,sensorIDVO1,deviceIDVO1,sensorTypeIDVO1);
        String expectedLogID1 = logToPersist1.getId().getID();
        String expectedReading1 = logToPersist1.getReading().getValue().toString();
        String expectedTimestamp1 = logToPersist1.getTime().toString();
        String expectedSensorID1 = logToPersist1.getSensorID().getID();
        String expectedDeviceID1 = logToPersist1.getDeviceID().getID();
        String expectedSensorTypeID1 = logToPersist1.getSensorTypeID().getID();

        Log logToPersist2 = new Log(reading1,sensorIDVO1,deviceIDVO1,sensorTypeIDVO1);
        String expectedLogID2 = logToPersist2.getId().getID();
        String expectedTimestamp2 = logToPersist2.getTime().toString();

        LogDataModel dataModel1 = new LogDataModel(logToPersist1);
        LogDataModel dataModel2 = new LogDataModel(logToPersist2);
        List<LogDataModel> dataModelList = new ArrayList<>();
        dataModelList.add(dataModel1);
        dataModelList.add(dataModel2);

        // Arranging factories

        LogFactoryImpl logFactory = new LogFactoryImpl();

        SensorValueFactoryImpl valueFactory = new SensorValueFactoryImpl("value.properties");

        // Obtaining an iterable of logs and converting it into a list of logs, for easier access

        Iterable<Log> ListlogsFromPersistence = LogAssembler.toDomain(logFactory,valueFactory,dataModelList);

        List<Log> listLogs = new ArrayList<>();

        ListlogsFromPersistence.forEach(listLogs::add);

        Log log1 = listLogs.get(0);
        Log log2 = listLogs.get(1);

        // Act
        String resultLogID1 = log1.getId().getID();
        String resultReading1 = log1.getReading().getValue().toString();
        String resultTimestamp1 = log1.getTime().toString();
        String resultSensorID1 = log1.getSensorID().getID();
        String resultDeviceID1 = log1.getDeviceID().getID();
        String resultSensorTypeID1 = log1.getSensorTypeID().getID();

        String resultLogID2 = log2.getId().getID();
        String resultReading2 = log2.getReading().getValue().toString();
        String resultTimestamp2 = log2.getTime().toString();
        String resultSensorID2 = log2.getSensorID().getID();
        String resultDeviceID2 = log2.getDeviceID().getID();
        String resultSensorTypeID2 = log2.getSensorTypeID().getID();

        // Assert
        assertEquals(expectedLogID1,resultLogID1);
        assertEquals(expectedReading1,resultReading1);
        assertEquals(expectedTimestamp1,resultTimestamp1);
        assertEquals(expectedSensorID1,resultSensorID1);
        assertEquals(expectedDeviceID1,resultDeviceID1);
        assertEquals(expectedSensorTypeID1,resultSensorTypeID1);

        assertEquals(expectedLogID2,resultLogID2);
        assertEquals(expectedReading1,resultReading2);
        assertEquals(expectedTimestamp2,resultTimestamp2);
        assertEquals(expectedSensorID1,resultSensorID2);
        assertEquals(expectedDeviceID1,resultDeviceID2);
        assertEquals(expectedSensorTypeID1,resultSensorTypeID2);
    }
}
