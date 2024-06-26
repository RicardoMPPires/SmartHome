package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;

public interface IActuatorRepositorySpringData extends JpaRepository<ActuatorDataModel, String> {
    Iterable<ActuatorDataModel> findByDeviceID(String deviceID);
}
