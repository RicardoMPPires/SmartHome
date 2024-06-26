package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.sensortype.SensorType;


/**
 * Represents the data model for sensor types in the smartHome system project.
 * This class maps sensor type data to a database using the Jakarta Persistence API.
 * Each instance of this class represents a single row in the sensor_type table.
 */
@Entity
@Table(name = "sensor_type")
public class SensorTypeDataModel {

    @Id
    @Column(name = "sensor_type_id")
    String sensorTypeId;
    @Column(name = "unit")
    String unit;

    /**
     * Default constructor for creating an empty SensorTypeDataModel object.
     * This constructor is required and primarily used by the Jakarta
     * Persistence framework.
     */
    public SensorTypeDataModel(){}

    /**
     * Constructs a SensorTypeDataModel using a given SensorType domain object.
     * This constructor extracts necessary information from the SensorType object
     * and initializes the data model with these details.
     *
     * @param sensorType The sensor type domain object to extract data from.
     */
    public SensorTypeDataModel(SensorType sensorType){
        this.sensorTypeId = sensorType.getId().getID();
        this.unit = sensorType.getUnit().getValue();
    }


    /**
     * Retrieves the sensor type identifier.
     * This identifier is used as the primary key in the SENSOR_TYPE table.
     *
     * @return The unique identifier of the sensor type.
     */
    public String getSensorTypeId() {
        return sensorTypeId;
    }


    /**
     * Retrieves the measurement unit associated with the sensor type.
     * This value corresponds to the 'unit' column in the SENSOR_TYPE table.
     *
     * @return The measurement unit of the sensor type.
     */
    public String getUnit() {
        return unit;
    }
}
