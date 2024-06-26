package smarthome.persistence.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.mapper.assembler.HouseAssembler;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.jpa.datamodel.HouseDataModel;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the HouseRepository interface using Java Persistence API (JPA).
 * This class interacts with the database to perform CRUD operations on House entities.
 * An EntityManagerFactory is injected in constructor and for each CRUD operation an EntityManager is created and closed after its realization.
 */

public class HouseRepositoryJPA implements HouseRepository {

    private final HouseFactory houseFactory;
    private final EntityManagerFactory entityManagerFactory;
    private static final String QUERY = "SELECT e FROM HouseDataModel e";

    /**
     * Constructs a new HouseRepositoryJPA object.
     * @param houseFactory  Factory for creating House objects.
     * @param entityManagerFactory Factory for managing EntityManagers.
     */

    public HouseRepositoryJPA(HouseFactory houseFactory, EntityManagerFactory entityManagerFactory) {
        this.houseFactory = houseFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Saves a House entity to the database.
     *
     * @param entity The House entity to be saved.
     * @return True if the operation is successful, false otherwise.
     * @throws IllegalArgumentException if the provided House entity is null.
     */

    @Override
    public boolean save(House entity) {
        if(isNull(entity)) {
            throw new IllegalArgumentException("House is null");
        }

        try(EntityManager em = entityManagerFactory.createEntityManager()){
            EntityTransaction et = em.getTransaction();
            et.begin();
            HouseDataModel houseDataModel = new HouseDataModel(entity);
            em.persist(houseDataModel);
            et.commit();
            return true;
        }catch (RuntimeException e){
           return false;
        }
    }

    /**
     * Retrieves all House entities from the database.
     * It's used a constant JPQL to this operation in QUERY.
     * @return An Iterable containing all House entities or null if any error that
     * causes a run time exceptions occurs.
     */

    @Override
    public Iterable<House> findAll() {
        try(EntityManager em = entityManagerFactory.createEntityManager())  {
            Query query = em.createQuery(QUERY);
            List<HouseDataModel> houseDataModels = query.getResultList();

            return HouseAssembler.toDomain(houseFactory, houseDataModels);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Retrieves a House entity from the database by its ID.
     * @param id The ID of the House entity to retrieve.
     * @return The House entity corresponding to the provided ID, null if not found or any RunTimeException occurred.
     * @throws IllegalArgumentException if the provided ID is null.
     */

    @Override
    public House findById(HouseIDVO id) {

        if(isNull(id)) {
            throw new IllegalArgumentException("ID is null");
        }
        try(EntityManager em = entityManagerFactory.createEntityManager()){

           Optional<HouseDataModel> houseDataModel = getHouseDataModelFromHouseId(em,id);
           if(houseDataModel.isPresent()){
               HouseDataModel houseDataModelFromOptional = houseDataModel.get();
               return HouseAssembler.toDomain(houseFactory,houseDataModelFromOptional);
           }
           return null;

        }catch (RuntimeException e){
           return null;
        }
    }

    /**
     * Checks if a House entity with the specified ID exists in the database.
     * @param id The ID of the House entity to check.
     * @return True if a House entity with the specified ID exists, false otherwise or any RunTimeException occurred.
     * @throws IllegalArgumentException if the provided ID is null.
     */

    @Override
    public boolean isPresent(HouseIDVO id) {
        if(isNull(id)) {
            throw new IllegalArgumentException("ID is null");
        }
        try(EntityManager em = entityManagerFactory.createEntityManager()){

            Optional<HouseDataModel> houseDataModel = getHouseDataModelFromHouseId(em,id);
            return houseDataModel.isPresent();
        }catch (RuntimeException e){
            return false;
        }
    }

    /**
     * Updates a House entity in the database.
     * 1. Obtains HouseDataModel object associated with the given house id, from database;
     * 2. Invoked updateFromDomain() method to make the retrieved HouseDataModel update it's attributes based on
     * the given house attributes.
     * 3. Merge the updated HouseDataModel
     * @param house The updated House entity.
     * @return True if the update operation is successful, false otherwise, any RunTimeException
     * occurred or if not possible to update the HouseDataModel Object
     *
     */

    @Override
    public boolean update(House house) {
        if(isNull(house)){
            return false;
        }

        EntityTransaction et = null;

        try(EntityManager em = this.entityManagerFactory.createEntityManager()){
            HouseIDVO id = house.getId();
            Optional<HouseDataModel> optionalHouseDataModel = getHouseDataModelFromHouseId(em,id);

            if(optionalHouseDataModel.isPresent()){
                HouseDataModel houseDataModel = optionalHouseDataModel.get();

                if(houseDataModel.updateFromDomain(house)){
                    et = em.getTransaction();
                    et.begin();
                    em.merge(houseDataModel);
                    et.commit();
                    return true;
                }
                return false;
            }
            return false;
        }catch (RuntimeException e){

            if (et != null && et.isActive()) {
                et.rollback();
            }
            return false;
        }
    }

    /**
     * Retrieves the first House entity from the database.
     * @return An Optional with the first found House entity, an empty optional if the database is empty or any
     * RunTimeException occurred.
     */

    @Override
    public Optional<House> getFirstHouse() {
        try(EntityManager em = entityManagerFactory.createEntityManager())  {
            Query query = em.createQuery(QUERY).setMaxResults(1);
            HouseDataModel houseDataModel = (HouseDataModel) query.getSingleResult();

            return Optional.of(HouseAssembler.toDomain(houseFactory, houseDataModel));
        }catch (RuntimeException e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves the ID of the first House entity from the database.
     * @return The ID of the first House entity, or null if the database is empty.
     */

    @Override
    public HouseIDVO getFirstHouseIDVO() {
        Optional<House> house = getFirstHouse();
        return house.map(House::getId).orElse(null);
    }

    /**
     * Verifies if the given object is null. Although is not type-safe, this method is only used when there is
     * a previous appropriate type checking by the compiler.
     * @param object Nullable entity
     * @return true if null, false otherwise
     */

    private boolean isNull(Object object){
        return object == null;
    }

    /**
     * Retrieves a HouseDataModel entity from the database by its ID.
     * @param em The EntityManager used to perform the database operation.
     * @param id The ID of the HouseDataModel entity to retrieve.
     * @return An Optional containing the retrieved HouseDataModel entity, or empty if not found.
     */
    private Optional<HouseDataModel> getHouseDataModelFromHouseId(EntityManager em, HouseIDVO id){
        String houseId = id.getID();
        return Optional.ofNullable(em.find(HouseDataModel.class, houseId));
    }

}
