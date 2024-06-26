package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;

public interface IDeviceRepositorySpringData extends JpaRepository<DeviceDataModel,String> {

    Iterable <DeviceDataModel> findByRoomID (String RoomID);

}
