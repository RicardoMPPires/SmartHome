package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.mapper.assembler.ActuatorAssembler;
import smarthome.mapper.assembler.SensorAssembler;
import smarthome.persistence.SensorRepository;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.Collections;
import java.util.Optional;

@Repository
public class SensorRepositorySpringData implements SensorRepository {

    private final ISensorRepositorySpringData iSensorRepositorySpringData;
    private final SensorFactory sensorFactory;

    public SensorRepositorySpringData(SensorFactory sensorFactory, ISensorRepositorySpringData iSensorRepositorySpringData) {
        this.iSensorRepositorySpringData = iSensorRepositorySpringData;
        this.sensorFactory = sensorFactory;
    }

    @Override
    public boolean save(Sensor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Sensor is null");
        }
        SensorDataModel sensorDataModel = new SensorDataModel(entity);
        try {
            this.iSensorRepositorySpringData.save(sensorDataModel);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Iterable<Sensor> findAll() {
        try {
            Iterable<SensorDataModel> sensorDataModelIterable = this.iSensorRepositorySpringData.findAll();
            return SensorAssembler.toDomain(this.sensorFactory, sensorDataModelIterable);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Sensor findById(SensorIDVO id) {
        if (id == null) {
            return null;
        }
        String sensorId = id.getID();
        try {
            Optional<SensorDataModel> sensorDataModelOptional = this.iSensorRepositorySpringData.findById(sensorId);
            if (sensorDataModelOptional.isPresent()) {
                SensorDataModel sensorDataModel = sensorDataModelOptional.get();
                return SensorAssembler.toDomain(this.sensorFactory, sensorDataModel);
            }
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }


    /**
     * Finds all Sensor objects in the repository that match the provided sensor type ID.
     * This method checks if the provided sensor type ID is null and returns null if it is.
     * It then attempts to retrieve SensorDataModel objects from the iSensorRepositorySpringData repository
     * using the provided sensor type ID. If matching sensors are found, they are converted to domain Sensor objects
     * using the SensorAssembler and returned. If no matching sensors are found or if a DataAccessException occurs,
     * null is returned.
     *
     * @param id The SensorTypeIDVO representing the type of sensor to search for.
     * @return An Iterable of Sensor objects that match the specified sensor type ID, or null if no matching sensors are found
     *         or if an exception occurs.
     */
    public Iterable<Sensor> findBySensorTypeId(SensorTypeIDVO id) {
        if (id == null) {
            return null;
        }
        String sensorId = id.getID();
        try {
            Iterable<SensorDataModel> sensorDataModelIterable = this.iSensorRepositorySpringData.findBySensorTypeID(sensorId);
            if (sensorDataModelIterable.iterator().hasNext()) {
                return SensorAssembler.toDomain(this.sensorFactory, sensorDataModelIterable);
            }
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean isPresent(SensorIDVO id) {
        if (id == null) {
            return false;
        }
        return findById(id) != null;
    }

    @Override
    public Iterable<Sensor> findByDeviceID(DeviceIDVO deviceID) {
        try {
            Iterable<SensorDataModel> sensorDataModelIterable = this.iSensorRepositorySpringData.findByDeviceID(deviceID.getID());
            return SensorAssembler.toDomain(this.sensorFactory, sensorDataModelIterable);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
}
