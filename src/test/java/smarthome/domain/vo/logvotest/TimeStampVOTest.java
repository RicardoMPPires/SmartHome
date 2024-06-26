package smarthome.domain.vo.logvotest;

import org.junit.jupiter.api.Test;
import smarthome.domain.vo.logvo.TimeStampVO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeStampVOTest {

    /**
     * Test the constructor of the TimeStampVO class.
     */
    @Test
    void whenDateIsNull_thenExceptionIsThrown() {
        //Arrange
        String time = "12:00";
        String expected = "Invalid date/time entries";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeStampVO(null, time));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when one of the entries is null.
     */
    @Test
    void whenTimeIsNull_thenExceptionIsThrown() {
        //Arrange
        String date = "2021-01-01";
        String expected = "Invalid date/time entries";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeStampVO(date, null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the date is empty.
     */
    @Test
    void whenDateIsEmpty_thenExceptionIsThrown() {
        //Arrange
        String date = "";
        String time = "12:00";
        String expected = "Invalid date/time entries";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeStampVO(date, time));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the time is empty.
     */
    @Test
    void whenTimeIsEmpty_thenExceptionIsThrown() {
        //Arrange
        String date = "2021-01-01";
        String time = "";
        String expected = "Invalid date/time entries";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeStampVO(date, time));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the time is invalid.
     */
    @Test
    void whenTimeIsInvalid_thenExceptionIsThrown() {
        //Arrange
        String date = "2021-01-01";
        String time = "25:00";
        String expected = "Invalid date/time entries";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeStampVO(date, time));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the date is valid, so it
     * creates a TimeStampVO object.
     */
    @Test
    void whenDateTimeIsValid_thenTimeStampIsCreated() {
        //Arrange
        String date = "2021-01-01";
        String time = "12:00";

        //Act
        TimeStampVO timeStampVO = new TimeStampVO(date, time);

        //Assert
        assertEquals(date + "T" + time, timeStampVO.getValue().toString());
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the LocalDateTime entry is null.
     */
    @Test
    void whenLocalDateTimeIsNull_thenExceptionIsThrown() {
        //Arrange
        String expected = "Invalid date/time entries";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new TimeStampVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the LocalDateTime entry is valid.
     */
    @Test
    void whenLocalDateTimeIsValid_thenTimeStampIsCreated() {
        //Arrange
        String date = "2021-01-01";
        String time = "12:00";
        TimeStampVO timeStampVO = new TimeStampVO(date, time);

        //Act
        TimeStampVO timeStampVO2 = new TimeStampVO(timeStampVO.getValue());

        //Assert
        assertEquals(date + "T" + time, timeStampVO2.getValue().toString());
    }

    /**
     * Test the constructor of the TimeStampVO class for the case when the LocalDateTime entry is valid and returns
     * the same timestamp.
     */
    @Test
    void whenLocalDateTimeIsValid_thenTimeStampIsReturned() {
        //Arrange
        String date = "2021-01-01";
        String time = "12:00";
        TimeStampVO timeStampVO = new TimeStampVO(date, time);

        //Act
        TimeStampVO timeStampVO2 = new TimeStampVO(timeStampVO.getValue());

        //Assert
        assertEquals(timeStampVO.getValue(), timeStampVO2.getValue());
    }

    /**
     * Test the equals method of the TimeStampVO class for the case when the two TimeStampVO objects are equal.
     */
    @Test
    void testEquals() {
        TimeStampVO timeStamp1 = new TimeStampVO(LocalDateTime.of(2022, 1, 1, 12, 0));
        TimeStampVO timeStamp2 = new TimeStampVO(LocalDateTime.of(2022, 1, 1, 12, 0));
        TimeStampVO timeStamp3 = new TimeStampVO(LocalDateTime.of(2022, 1, 1, 13, 0));

        assertEquals(timeStamp1, timeStamp2);
        assertNotEquals(timeStamp1, timeStamp3);
        assertNotEquals(timeStamp2, timeStamp3);
    }

    /**
     * Test the hashCode method of the TimeStampVO class for the case when the two TimeStampVO objects are equal.
     */
    @Test
    void testHashCode() {
        TimeStampVO timeStamp1 = new TimeStampVO(LocalDateTime.of(2022, 1, 1, 12, 0));
        TimeStampVO timeStamp2 = new TimeStampVO(LocalDateTime.of(2022, 1, 1, 12, 0));
        TimeStampVO timeStamp3 = new TimeStampVO(LocalDateTime.of(2022, 1, 1, 13, 0));

        assertEquals(timeStamp1.hashCode(), timeStamp2.hashCode());
        assertNotEquals(timeStamp1.hashCode(), timeStamp3.hashCode());
        assertNotEquals(timeStamp2.hashCode(), timeStamp3.hashCode());
    }

}
