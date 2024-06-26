package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.SensorTypeDataModel;

/**
 * Interface for Spring Data JPA repository for SensorTypeDataModel.
 * This repository interface extends JpaRepository, providing CRUD operations
 * for entities of type SensorTypeDataModel with a primary key of type String.
 * This interface can be used to interact with the database and perform operations like
 * find, save, delete, and update on SensorTypeDataModel entities.
 * Being a Spring Data repository, most common query methods are provided by default,
 * but custom queries can be defined as needed using Spring Data JPA annotations.
 */
public interface ISensorTypeRepositorySpringData extends JpaRepository<SensorTypeDataModel, String> {
}
