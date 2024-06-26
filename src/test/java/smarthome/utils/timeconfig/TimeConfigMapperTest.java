package smarthome.utils.timeconfig;

import org.junit.jupiter.api.Test;
import smarthome.domain.vo.DeltaVO;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeConfigMapperTest {

    /**
     * Tests the scenario when the DTO is null, expecting an IllegalArgumentException to be thrown.
     * This test method verifies that calling the createTimeConfig method of TimeConfigAssembler with a null DTO
     * results in an IllegalArgumentException being thrown, as expected.
     * It arranges the expected error message.
     * Then, it invokes the createTimeConfig method with a null parameter within an assertThrows block to capture the exception.
     * Finally, it verifies that the thrown exception is of type IllegalArgumentException and that its message matches the expected one.
     */
    @Test
    void whenDTONull_throwsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid TimeConfigDTO";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                TimeConfigMapper.createInitialTimeStamp(null));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                TimeConfigMapper.createFinalTimeStamp(null));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                TimeConfigMapper.createDeltaVO(null));
        String result = exception.getMessage();
        String result2 = exception2.getMessage();
        String result3 = exception3.getMessage();
        // Assert
        assertEquals(expected,result);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
    }

    /**
     * Tests the scenario when given full parameters, calls appropriate constructor, and the object has accessible attributes.
     * This test method verifies that a TimeConfig object created using a TimeConfigDTO with initial and end dates, times, and delta
     * correctly initializes its attributes and makes them accessible through getter methods.
     * It arranges the necessary input parameters including initial date, end date, initial time, end time, and delta in minutes.
     * Then, it creates a TimeConfigDTO object using these parameters and constructs a TimeConfig object using a factory method
     * (TimeConfigAssembler.createTimeConfig) with the DTO.
     * After that, it retrieves the initial timestamp, end timestamp, and delta value from the created TimeConfig object using getter methods.
     * Finally, it asserts that the retrieved values match the expected values based on the input parameters.
     */
    @Test
    void whenGivenFullParameters_callsAppropriateConstructor_objectHasAccessibleAttributes(){
        // Arrange
        String iDate = "2024-04-23";
        String iTime = "18:00";
        String eDate = "2024-04-23";
        String eTime = "23:00";
        String deltaMin = "60";

        String expectedIStamp = iDate + "T" + iTime;
        String expectedEStamp = eDate + "T" + eTime;
        int expected = 60;

        TimeConfigDTO dto = new TimeConfigDTO(iDate,iTime,eDate,eTime,deltaMin);

        TimeStampVO initial = TimeConfigMapper.createInitialTimeStamp(dto);
        TimeStampVO end = TimeConfigMapper.createFinalTimeStamp(dto);
        DeltaVO delta = TimeConfigMapper.createDeltaVO(dto);

        // Act
        LocalDateTime iStamp = initial.getValue();
        LocalDateTime eStamp = end.getValue();
        int deltaM = delta.getValue();

        // Assert
        assertEquals(expectedIStamp,iStamp.toString());
        assertEquals(expectedEStamp,eStamp.toString());
        assertEquals(expected,deltaM);
    }

    /**
     * Tests the scenario when given parameters excluding delta, calls appropriate constructor, and the object has accessible attributes.
     * This test method verifies that a TimeConfig object created using a TimeConfigDTO with initial and end dates and times
     * (excluding delta) correctly initializes its attributes and makes them accessible through getter methods.
     * It arranges the necessary input parameters including initial date, end date, initial time, and end time.
     * Then, it creates a TimeConfigDTO object using these parameters and constructs a TimeConfig object using a factory method
     * (TimeConfigAssembler.createTimeConfig) with the DTO.
     * After that, it retrieves the initial and end timestamps from the created TimeConfig object using getter methods.
     * Finally, it asserts that the retrieved timestamps match the expected values based on the input parameters.
     */
    @Test
    void whenGivenParametersExcludingDelta_callsAppropriateConstructor_objectHasAccessibleAttributes(){
        // Arrange
        String iDate = "2024-04-23";
        String eDate = "2024-04-23";
        String iTime = "18:00";
        String eTime = "23:00";

        String expectedIStamp = iDate + "T" + iTime;
        String expectedEStamp = eDate + "T" + eTime;

        TimeConfigDTO dto = TimeConfigDTO.builder()
                .initialDate(iDate)
                .initialTime(iTime)
                .endDate(eDate)
                .endTime(eTime)
                .build();

        TimeStampVO initial = TimeConfigMapper.createInitialTimeStamp(dto);
        TimeStampVO end = TimeConfigMapper.createFinalTimeStamp(dto);

        // Act
        LocalDateTime iStamp = initial.getValue();
        LocalDateTime eStamp = end.getValue();

        // Assert
        assertEquals(expectedIStamp,iStamp.toString());
        assertEquals(expectedEStamp,eStamp.toString());
    }
}
