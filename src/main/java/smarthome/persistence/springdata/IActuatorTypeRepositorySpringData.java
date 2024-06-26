package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.ActuatorTypeDataModel;

public interface IActuatorTypeRepositorySpringData extends JpaRepository<ActuatorTypeDataModel, String> {
}
