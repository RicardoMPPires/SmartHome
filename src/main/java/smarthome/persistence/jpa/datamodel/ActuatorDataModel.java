package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import smarthome.domain.actuator.Actuator;

/**
 * DataModel class for the Actuator entity.
 * This class is used to persist the Actuator entity in the database.
 * The class is annotated with the JPA annotations to specify the table name and the column names.
 * The class has a constructor that initializes the ActuatorDataModel object with the provided parameters.
 * The class has getter methods to retrieve the values of the ActuatorDataModel object.
 * The class is used by the ActuatorRepository to persist and retrieve the Actuator entity from the database.
 */
@Entity
@Table(name = "actuator")
public class ActuatorDataModel {
    @Getter
    @Id
    @Column(name = "id")
    private String actuatorID;
    @Getter
    @Column(name = "actuator_name")
    private String actuatorName;
    @Getter
    @Column(name = "actuator_type_id")
    private String actuatorTypeID;
    @Getter
    @Column(name = "device_id")
    private String deviceID;
    @Getter
    @Column(name = "lower_limit")
    private String lowerLimit;
    @Getter
    @Column(name = "upper_limit")
    private String upperLimit;
    @Getter
    @Column(name = "precision_value")
    private String precision_value;
    @Getter
    @Column(name = "status")
    private String status;

    /**
     * Default constructor for an ActuatorDataModel.
     */
    public ActuatorDataModel() {
    }

    /**
     * Constructor to initialize the ActuatorDataModel object with the provided parameters.
     *
     * @param actuator Actuator to be converted in a data model
     * @result The ActuatorDataModel object is created with the provided parameters.
     */
    public ActuatorDataModel(Actuator actuator) {
        this.actuatorID = actuator.getId().getID();
        this.actuatorName = actuator.getActuatorName().getValue();
        this.actuatorTypeID = actuator.getActuatorTypeID().getID();
        this.deviceID = actuator.getDeviceID().getID();
        this.lowerLimit = actuator.getLowerLimit();
        this.upperLimit = actuator.getUpperLimit();
        this.precision_value = actuator.getPrecision();
        this.status = actuator.getActuatorStatus().getValue();
    }
}
