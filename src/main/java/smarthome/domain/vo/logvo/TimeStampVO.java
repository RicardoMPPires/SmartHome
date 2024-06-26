package smarthome.domain.vo.logvo;

import smarthome.domain.vo.ValueObject;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Value object class representing a timestamp of a log entry of LocalDateTime type.
 */
public class TimeStampVO implements ValueObject<LocalDateTime> {

    private final LocalDateTime timeStamp;
    private static final String ERROR = "Invalid date/time entries";

    /**
     * Constructs a new TimeStampVO object with the provided date and time.
     * @param date The date of the timestamp.
     * @param time The time of the timestamp.
     */
    public TimeStampVO(String date, String time) {
        if(date == null || time == null || date.trim().isEmpty() || time.trim().isEmpty()){
            throw new IllegalArgumentException(ERROR);
        }
        try{
            LocalDateTime timeStampToTruncate = parseDateTime(date, time);
            this.timeStamp = timeStampToTruncate.truncatedTo(ChronoUnit.SECONDS);
        } catch (DateTimeException e){
            throw new IllegalArgumentException(ERROR);
        }
    }


    /**
     * Constructs a new TimeStampVO object with the provided LocalDateTime object.
     * @param localDateTime The LocalDateTime object representing the timestamp.
     */
    public TimeStampVO(LocalDateTime localDateTime){
        if(localDateTime==null){
            throw new IllegalArgumentException(ERROR);
        }
        this.timeStamp = localDateTime.truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Parses the date and time strings into a LocalDateTime object.
     * @param date The date string.
     * @param time The time string.
     * @return The LocalDateTime object representing the timestamp.
     */
    private LocalDateTime parseDateTime(String date, String time){
        return LocalDateTime.parse(date+"T"+time);
    }

    /**
     * Getter method that eturns the LocalDateTime object representing the timestamp.
     * @return The LocalDateTime object representing the timestamp.
     */
    @Override
    public LocalDateTime getValue() {
        return timeStamp;
    }

    /**
     * Compares this TimeStampVO object with another object for equality.
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TimeStampVO that = (TimeStampVO) obj;
        return Objects.equals(this.timeStamp, that.timeStamp);
    }


    /**
     * Generates a hash code for the TimeStampVO object.
     * @return The hash code generated for the TimeStampVO object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(timeStamp);
    }
}
