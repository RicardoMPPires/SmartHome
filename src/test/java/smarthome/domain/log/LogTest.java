package smarthome.domain.log;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import smarthome.domain.sensor.sensorvalues.HumidityValue;
import smarthome.domain.sensor.sensorvalues.PositionValue;
import smarthome.domain.sensor.sensorvalues.SensorValueObject;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.logvo.LogIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LogTest {

    /**
     * Tests that creating a Log with any null parameters throws an IllegalArgumentException,
     * ensuring the integrity of the entire Log aggregate.
     * The System Under Test (SUT) in this test is the entire Log aggregate, including its
     * associated ValueObject instances.
     */
    @Test
    void whenGivenANullParameter_throwsIllegalArgumentException() {
        // Arrange
        SensorValueObject<?> reading = new HumidityValue("70");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("Humidity");
        LogIDVO logID = new LogIDVO(UUID.randomUUID());
        TimeStampVO time = new TimeStampVO(LocalDateTime.now());
        String expected = "Invalid parameters.";

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Log(null,sensorID,deviceID,sensorTypeID));
        String result1 = exception1.getMessage();
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Log(reading, null, deviceID,sensorTypeID));
        String result2 = exception2.getMessage();
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Log(reading, sensorID, null,sensorTypeID));
        String result3 = exception3.getMessage();
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new Log(reading,sensorID,deviceID,null));
        String result4 = exception4.getMessage();
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new Log(logID,null,reading,sensorID,deviceID,null));
        String result5 = exception5.getMessage();
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> new Log(null,time,reading,sensorID,deviceID,sensorTypeID));
        String result6 = exception6.getMessage();
        // Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
        assertEquals(expected,result5);
        assertEquals(expected,result6);
    }

    /**
     * Tests that creating a Log with any null parameters throws an IllegalArgumentException,
     * ensuring the integrity of the Log entity within the aggregate.
     * The System Under Test (SUT) in this test is the Log entity. Mocked ValueObject instances are used for testing.
     */
    @Test
    void whenGivenANullParameter_throwsIllegalArgumentException_SUTEntity() {
        // Arrange
        SensorValueObject<?> reading = mock(HumidityValue.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        LogIDVO logID = mock(LogIDVO.class);
        TimeStampVO time = mock(TimeStampVO.class);
        String expected = "Invalid parameters.";
        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Log(null, sensorID, deviceID, sensorTypeID));
        String result1 = exception1.getMessage();
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Log(reading, null, deviceID, sensorTypeID));
        String result2 = exception2.getMessage();
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Log(reading, sensorID, null, sensorTypeID));
        String result3 = exception3.getMessage();
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new Log(reading, sensorID, deviceID, null));
        String result4 = exception4.getMessage();
        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new Log(null,time,reading, sensorID, deviceID, sensorTypeID));
        String result5 = exception5.getMessage();
        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> new Log(logID, null, reading, sensorID, deviceID, sensorTypeID));
        String result6 = exception6.getMessage();
        // Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
        assertEquals(expected, result3);
        assertEquals(expected, result4);
        assertEquals(expected, result5);
        assertEquals(expected, result6);
    }

    /**
     * Tests that the Log entity returns the correct SensorIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the SensorIDVO associated with it.
     */
    @Test
    void returnsCorrectSensorID() {
        // Arrange
        SensorValueObject<?> reading = new HumidityValue("70");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("Humidity");
        Log log = new Log(reading,sensorID,deviceID,sensorTypeID);
        // Act
        SensorIDVO result = log.getSensorID();
        // Assert
        assertEquals(sensorID,result);
    }

    /**
     * Tests that the Log entity returns the correct SensorIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the SensorIDVO associated with it. Mocked ValueObject instances
     * are used for testing.
     */
    @Test
    void returnsCorrectSensorID_SUTEntity() {
        // Arrange
        SensorValueObject<?> reading = mock(HumidityValue.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        Log log = new Log(reading,sensorID,deviceID,sensorTypeID);
        // Act
        SensorIDVO result = log.getSensorID();
        // Assert
        assertEquals(sensorID,result);
    }

    /**
     * Tests that the Log entity returns the correct DeviceIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the DeviceIDVO associated with it.
     */
    @Test
    void returnsCorrectDeviceID() {
        // Arrange
        SensorValueObject<?> reading = new HumidityValue("70");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("Humidity");
        Log log = new Log(reading,sensorID,deviceID,sensorTypeID);
        // Act
        DeviceIDVO result = log.getDeviceID();
        // Assert
        assertEquals(deviceID,result);
    }

    /**
     * Tests that the Log entity returns the correct DeviceIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the DeviceIDVO associated with it. Mocked ValueObject instances
     * are used for testing.
     */
    @Test
    void returnsCorrectDeviceID_SUTEntity() {
        // Arrange
        SensorValueObject<?> reading = mock(HumidityValue.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        Log log = new Log(reading,sensorID,deviceID,sensorTypeID);
        // Act
        DeviceIDVO result = log.getDeviceID();
        // Assert
        assertEquals(deviceID,result);
    }

    /**
     * Tests that the Log entity returns the correct SensorTypeIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the SensorTypeIDVO associated with it.
     */
    @Test
    void returnsCorrectSensorTypeID() {
        // Arrange
        SensorValueObject<?> reading = new HumidityValue("70");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("Humidity");
        Log log = new Log(reading,sensorID,deviceID,sensorTypeID);
        // Act
        SensorTypeIDVO result = log.getSensorTypeID();
        // Assert
        assertEquals(sensorTypeID,result);
    }

    /**
     * Tests that the Log entity returns the correct SensorTypeIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the SensorTypeIDVO associated with it. Mocked ValueObject instances
     * are used for testing.
     */
    @Test
    void returnsCorrectSensorTypeID_SUTEntity() {
        // Arrange
        SensorValueObject<?> reading = mock(HumidityValue.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);
        Log log = new Log(reading,sensorID,deviceID,sensorTypeID);
        // Act
        SensorTypeIDVO result = log.getSensorTypeID();
        // Assert
        assertEquals(sensorTypeID,result);
    }

    /**
     * Tests that the Log entity returns the correct LogIDVO.
     * The System Under Test (SUT) in this test is the Log entity, which is expected
     * to correctly return the LogIDVO associated with it. Mocks a UUID construction
     * to ensure consistency in the LogIDVO generation.
     */
    @Test
    void returnsCorrectLogIDVO_SUTEntity(){
        SensorValueObject<?> reading = mock(HumidityValue.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        String expected = "Test";
        int expectedSize = 1;
        try (MockedConstruction<LogIDVO> mockUUID = mockConstruction(LogIDVO.class, (mock, context) ->
                when(mock.toString()).thenReturn(expected))) {

            Log log = new Log(reading,sensorID,deviceID,sensorTypeID);

            //Act
            String result = log.getId().toString();
            int resultSize = mockUUID.constructed().size();

            //Assert
            assertEquals(expected,result);
            assertEquals(expectedSize,resultSize);
        }
    }

    /**
     * Test case to verify that the getTime method of the Log class returns the correct time in UTC.
     *
     * <p>
     * The test sets up a mock object for the reading and creates a Log object with mocked sensorID,
     * deviceID, and sensorTypeID. It then retrieves the time from the Log object, converts it to UTC,
     * and truncates it to milliseconds for accurate comparison.
     * </p>
     *
     * <p>
     * To achieve consistent results across different environments, the test converts the current local time
     * to UTC and truncates it to milliseconds. It then retrieves the time from the Log object, converts it
     * to UTC, and truncates it to milliseconds for comparison. The times are then compared as strings.
     * </p>
     *
     * <p>
     * Note: This test assumes that the Log class internally uses LocalDateTime.now() to set the time,
     * so it may fail if the implementation changes or if there are time zone differences.
     * </p>
     */
    @Test
    void returnsCorrectTime() {
        // Arrange
        SensorValueObject<?> reading = mock(HumidityValue.class);
        SensorIDVO sensorID = mock(SensorIDVO.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        SensorTypeIDVO sensorTypeID = mock(SensorTypeIDVO.class);

        Log log = new Log(reading, sensorID, deviceID, sensorTypeID);

        // Get the current local time, convert it to UTC, and truncate to milliseconds
        LocalDateTime expectedUTCTime = LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime()
                .truncatedTo(ChronoUnit.SECONDS);

        // Act
        LocalDateTime resultUTCTime = log.getTime().getValue()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime()
                .truncatedTo(ChronoUnit.SECONDS);

        // Convert LocalDateTime objects to strings
        String expectedTimeString = expectedUTCTime.toString();
        String resultTimeString = resultUTCTime.toString();

        // Assert
        assertEquals(expectedTimeString, resultTimeString);
    }

    /**
     * Tests if the getReading() method returns the correct reading value.
     * Casts the result to a PositionValue object to ensure successful type conversion.
     */
    @Test
    void returnsReading(){

        // Arrange
        SensorValueObject<?> reading = new PositionValue("5");
        SensorIDVO sensorID = new SensorIDVO(UUID.randomUUID());
        DeviceIDVO deviceID = new DeviceIDVO(UUID.randomUUID());
        SensorTypeIDVO sensorTypeID = new SensorTypeIDVO("Humidity");
        LogIDVO logID = new LogIDVO(UUID.randomUUID());
        TimeStampVO time = new TimeStampVO(LocalDateTime.now());

        Log log = new Log(logID,time,reading,sensorID,deviceID,sensorTypeID);

        // Act
        PositionValue result = (PositionValue) log.getReading();

        // Assert
        assertEquals(reading,result);
    }
}
