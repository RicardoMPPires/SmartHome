package smarthome.persistence.jpa.datamodel;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import smarthome.domain.sensor.Sensor;

@Entity
@Table(name = "sensor")
public class SensorDataModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "sensor_type_id")
    private String sensorTypeID;
    @Column(name = "device_id")
    private String deviceID;


    /**
     * Default constructor for SensorDataModel. Required by JPA.
     */
    public SensorDataModel() {
    }


    /**
     * Constructor for SensorDataModel.
     *
     * @param sensor The sensor object to be converted to a SensorDataModel.
     */
    public SensorDataModel(Sensor sensor) {
        this.id = sensor.getId().getID();
        this.name = sensor.getSensorName().getValue();
        this.sensorTypeID = sensor.getSensorTypeID().getID();
        this.deviceID = sensor.getDeviceID().getID();
    }

    /**
     * Simple getter method for the sensor ID.
     *
     * @return The sensor ID.
     */
    public String getSensorId() {
        return id;
    }


    /**
     * Simple getter method for the sensor name.
     *
     * @return The sensor name.
     */
    public String getSensorName() {
        return name;
    }


    /**
     * Simple getter method for the sensor type ID.
     *
     * @return The sensor type ID.
     */
    public String getSensorTypeID() {
        return sensorTypeID;
    }


    /**
     * Simple getter method for the device ID.
     *
     * @return The device ID.
     */
    public String getDeviceID() {
        return deviceID;
    }
}
