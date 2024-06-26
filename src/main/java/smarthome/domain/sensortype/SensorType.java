package smarthome.domain.sensortype;

import smarthome.domain.AggregateRoot;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;

/**
 * Represents a Type of Sensor.
 * A Sensor type is characterized by its id and unit.
 * The constructor of this class validates the parameters and throws an IllegalArgumentException
 * if any of them are null.
 */
public class SensorType implements AggregateRoot {

    private final SensorTypeIDVO sensorTypeID;

    private final UnitVO sensorUnit;

    /**
     * Constructs a SensorType with the specified parameters, all of them being Value-Objects.
     *
     * @param sensorTypeID The id of the sensor type.
     * @param sensorUnit The unit of the sensor type.
     * @throws IllegalArgumentException If any of the parameters are null.
     */

    public SensorType (SensorTypeIDVO sensorTypeID, UnitVO sensorUnit){
        if (!areParamsValid(sensorTypeID, sensorUnit)){
            throw new IllegalArgumentException("Invalid Parameters");
        } else {
            this.sensorTypeID = sensorTypeID;
            this.sensorUnit = sensorUnit;
        }
    }

    private boolean areParamsValid(SensorTypeIDVO sensorTypeID, UnitVO sensorUnit){
        return sensorTypeID != null && sensorUnit != null;
    }

    /**
     * Getter method for the sensor type id.
     * @return The sensor type id.
     */

    @Override
    public SensorTypeIDVO getId() {
        return sensorTypeID;
    }

    /**
     * Getter method for the sensor type unit.
     * @return  The sensor type unit.
     */
    public UnitVO getUnit(){
        return sensorUnit;
    }
}
