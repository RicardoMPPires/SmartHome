package smarthome.persistence.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.mapper.assembler.ActuatorTypeAssembler;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.persistence.jpa.datamodel.ActuatorTypeDataModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The ActuatorTypeRepositoryJPA class is an implementation of the ActuatorTypeRepository interface that uses Java Persistence API (JPA).
 * It interacts with the database to perform CRUD operations on ActuatorType entities.
 */
public class ActuatorTypeRepositoryJPA implements ActuatorTypeRepository {

    private final ActuatorTypeFactory actuatorTypeFactory;
    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructor for the ActuatorTypeRepositoryJPA class, that takes an ActuatorTypeFactory and an EntityManagerFactory as parameters.
     *
     * @param actuatorTypeFactory The ActuatorTypeFactory used to create ActuatorType objects.
     * @param entityManagerFactory The EntityManagerFactory used to create EntityManagers.
     */
    public ActuatorTypeRepositoryJPA(ActuatorTypeFactory actuatorTypeFactory, EntityManagerFactory entityManagerFactory) {
        this.actuatorTypeFactory = actuatorTypeFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * This method saves an ActuatorType object to the database. It returns true if the ActuatorType is saved successfully, and false otherwise.
     * This method is an implementation of the save() method in the Repository interface.
     * First, it checks if the ActuatorType object is null, and throws an IllegalArgumentException if so.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * After that it creates an EntityTransaction by calling getTransaction() on the EntityManager. The transaction is begun
     * by calling begin() on the EntityTransaction.
     * Then it creates an ActuatorTypeDataModel object from the ActuatorType object by calling the ActuatorTypeDataModel constructor with
     * the ActuatorType object as a parameter.
     * Then it persists the ActuatorTypeDataModel object by calling persist() on the EntityManager.
     * Finally, it commits the transaction by calling commit() on the EntityTransaction.
     * If a RuntimeException is thrown, it returns false.
     * @param actuatorType The ActuatorType object to save.
     * @return A boolean value indicating whether the ActuatorType was saved successfully.
     * @throws IllegalArgumentException if the ActuatorType object is null.
     */
    @Override
    public boolean save(ActuatorType actuatorType) {
        if(actuatorType == null) {
            throw new IllegalArgumentException("Actuator type is null");
        }

        try (EntityManager em = this.entityManagerFactory.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);
            em.persist(actuatorTypeDataModel);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * This method retrieves all ActuatorType entities from the database, returning an Iterable containing all ActuatorType entities.
     * The method is an implementation of the findAll() method in the Repository interface.
     * First, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then, it creates a Query object by calling createQuery() on the EntityManager with a JPQL query as a parameter. This query
     * selects all ActuatorTypeDataModel objects from the database, ordered by actuatorTypeID.
     * Then, it gets the result list from the query and converts it to a list of ActuatorType objects using the ActuatorTypeAssembler class,
     * returning this list.
     * If a RuntimeException is thrown, it returns an empty list.
     *
     * @return An Iterable containing all ActuatorType entities.
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        try (EntityManager em = this.entityManagerFactory.createEntityManager()) {

            Query query = em.createQuery("SELECT at FROM ActuatorTypeDataModel at Order By at.actuatorTypeID ASC");
            List<ActuatorTypeDataModel> actuatorTypeDataModelList = query.getResultList();
            return ActuatorTypeAssembler.actuatorTypeListToDomain(this.actuatorTypeFactory, actuatorTypeDataModelList);

        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    /**
     * This method retrieves an ActuatorType entity from the database by its ID, returning the ActuatorType entity.
     * First, it checks if the ActuatorTypeIDVO object is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * After that, it calls the getDataModelFromActuatorTypeId() method to get an Optional of ActuatorTypeDataModel from the ActuatorTypeIDVO.
     * Then, it checks if the Optional is present, and if it is, it creates an ActuatorTypeDataModel object from the Optional.
     * Finally, it returns the ActuatorType object created from the ActuatorTypeDataModel object, by calling the actuatorTypeToDomain() method
     * from the ActuatorTypeAssembler class.
     * If a RuntimeException is thrown, it returns null.
     * @param actuatorTypeIDVO IDVO
     * @return The ActuatorType entity with the given ID, or null if it does not exist.
     */
    @Override
    public ActuatorType findById(ActuatorTypeIDVO actuatorTypeIDVO) {
        if(actuatorTypeIDVO == null) {
            throw new IllegalArgumentException("Actuator type id is null.");
        }
        try(EntityManager em = this.entityManagerFactory.createEntityManager()) {
            Optional<ActuatorTypeDataModel> actuatorTypeDataModel = getDataModelFromActuatorTypeId(em,actuatorTypeIDVO);
            if(actuatorTypeDataModel.isPresent()){
                ActuatorTypeDataModel actuatorTypeDataModelFromOptional = actuatorTypeDataModel.get();
                return ActuatorTypeAssembler.actuatorTypeToDomain(this.actuatorTypeFactory,actuatorTypeDataModelFromOptional);
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * This method checks if an ActuatorType entity with the specified ID exists in the database, returning a boolean value.
     * First, it checks if the ActuatorTypeIDVO object is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * After that, it calls the getDataModelFromActuatorTypeId() method to get an Optional of ActuatorTypeDataModel from the ActuatorTypeIDVO.
     * Finally, it returns true if the Optional is present, and false otherwise.
     * If a RuntimeException is thrown, it returns false.
     * @param actuatorTypeIDVO IDVO
     * @return True if an ActuatorType entity with the specified ID exists, false otherwise.
     */
    @Override
    public boolean isPresent(ActuatorTypeIDVO actuatorTypeIDVO) {
        if(actuatorTypeIDVO == null) {
            throw new IllegalArgumentException("Actuator type id is null.");
        }
        try(EntityManager em = this.entityManagerFactory.createEntityManager()){
            Optional<ActuatorTypeDataModel> actuatorTypeDataModel = getDataModelFromActuatorTypeId(em,actuatorTypeIDVO);
            return actuatorTypeDataModel.isPresent();
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * This method retrieves an ActuatorTypeDataModel object from the database by its ID.
     * First, it gets the ID of the ActuatorType from the ActuatorTypeIDVO object.
     * Then, it returns an Optional of ActuatorTypeDataModel by calling find() on the EntityManager with the ActuatorTypeDataModel
     * class and the ActuatorType ID as a parameters.
     * If the ActuatorTypeDataModel object is found, it returns the Optional, otherwise it returns null.
     * @param entityManager The EntityManager object.
     * @param actuatorTypeIDVO The ActuatorTypeIDVO object.
     * @return An Optional of ActuatorTypeDataModel.
     */
    private Optional<ActuatorTypeDataModel> getDataModelFromActuatorTypeId(EntityManager entityManager, ActuatorTypeIDVO actuatorTypeIDVO) {
        String actuatorTypeId = actuatorTypeIDVO.getID();
        return Optional.ofNullable(entityManager.find(ActuatorTypeDataModel.class, actuatorTypeId));
    }
}
