package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

public interface IRoomRepositorySpringData extends JpaRepository<RoomDataModel, String> {
}
