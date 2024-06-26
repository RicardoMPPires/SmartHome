package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.HouseDataModel;

public interface IHouseRepositorySpringData extends JpaRepository<HouseDataModel,String> {

}
