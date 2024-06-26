package smarthome.persistence.jpa.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.actuatortype.ActuatorType;


/**
 * Represents the data model for actuator types in the smartHome system project.
 * This class maps actuator type data to a database using the Jakarta Persistence API.
 * The annotation @Entity indicates that this class is an entity that will be managed by the Jakarta Persistence framework.
 * The annotation @Table specifies the name of the table in the database where the data for this entity will be stored.
 * The field actuatorTypeId corresponds to the primary key column in the actuator_type table and it is annotated with @Id,
 * with the annotation @Column specifying the name of the column in the database.
 * Each instance of this class represents a single row in the actuator_type table.
 */
@Entity
@Table(name = "actuator_type")
public class ActuatorTypeDataModel {

    @Id
    @Column(name = "actuator_type_id")
    private String actuatorTypeID;

    /** Constructor for creating an empty ActuatorTypeDataModel object.
     * This constructor is required and primarily used by the Jakarta Persistence framework, being necessary
     * for the creation of instances of this class when retrieving data from the database. The new instance
     * will be then populated with the data obtained from the database.
     */
    public ActuatorTypeDataModel() {
    }

    /**
     * This constructor of an ActuatorTypeDataModel object uses an ActuatorType domain object as parameter to extract
     * data necessary to initialize the data model. The actuatorTypeId field is set with the identifier of the ActuatorType
     * domain object.
     *
     * @param actuatorType The actuator type domain object to extract data from.
     */
    public ActuatorTypeDataModel(ActuatorType actuatorType) {
        this.actuatorTypeID = actuatorType.getId().getID();
    }

    /** This getter method retrieves the actuator type identifier attribute.
     *
     * @return The unique identifier of the actuator type.
     */
    public String getActuatorTypeID() {
        return this.actuatorTypeID;
    }
}