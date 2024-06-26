package smarthome.utils.timeconfig;

import smarthome.domain.vo.DeltaVO;
import smarthome.domain.vo.logvo.TimeStampVO;

/**
 * Assembler class for TimeConfigDTO objects.
 */
public class TimeConfigMapper {

    private static final String ERROR = "Invalid TimeConfigDTO";

    private TimeConfigMapper(){
        // Private constructor to prevent instantiation of this utility class
    }

    /**
     * Creates a DeltaVO object that represents the time difference in minutes between two timestamps
     * from the provided TimeConfigDTO object.
     * @param timeConfigDTO The TimeConfigDTO object.
     * @return The DeltaVO object.
     */
    public static DeltaVO createDeltaVO(TimeConfigDTO timeConfigDTO){
        if (timeConfigDTO==null){
            throw new IllegalArgumentException(ERROR);
        }
        return new DeltaVO(timeConfigDTO.getDeltaMin());
    }

    /**
     * Creates a TimeStampVO object representing the initial timestamp from the provided TimeConfigDTO object.
     * @param timeConfigDTO The TimeConfigDTO object.
     * @return The TimeStampVO object.
     */
    public static TimeStampVO createInitialTimeStamp(TimeConfigDTO timeConfigDTO){
        if (timeConfigDTO==null){
            throw new IllegalArgumentException(ERROR);
        }
        return new TimeStampVO(timeConfigDTO.getInitialDate(), timeConfigDTO.getInitialTime());
    }

    /**
     * Creates a TimeStampVO object that represents the final timestamp from the provided TimeConfigDTO object.
     * @param timeConfigDTO The TimeConfigDTO object.
     * @return The TimeStampVO object.
     */
    public static TimeStampVO createFinalTimeStamp(TimeConfigDTO timeConfigDTO){
        if (timeConfigDTO==null){
            throw new IllegalArgumentException(ERROR);
        }
        return new TimeStampVO(timeConfigDTO.getEndDate(), timeConfigDTO.getEndTime());
    }

}
