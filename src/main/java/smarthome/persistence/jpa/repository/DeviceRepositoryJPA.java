package smarthome.persistence.jpa.repository;

import jakarta.persistence.*;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.assembler.DeviceAssembler;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the DeviceRepository interface using Java Persistence API (JPA).
 * This class interacts with the database to perform CRUD operations on Device entities.
 * An EntityManagerFactory is injected in constructor and for each CRUD operation an EntityManager is created and closed after its realization.
 */
public class DeviceRepositoryJPA implements DeviceRepository {

    private final DeviceFactory deviceFactory;

    private final EntityManagerFactory entityManagerFactory;
    private static final String QUERY = "SELECT e FROM DeviceDataModel e";

    /**
     * Constructs a new DeviceRepositoryJPA object.
     * @param deviceFactory  Factory for creating Device objects.
     * @param entityManagerFactory Factory for managing EntityManagers.
     */

    public DeviceRepositoryJPA(DeviceFactory deviceFactory, EntityManagerFactory entityManagerFactory) {
        this.deviceFactory = deviceFactory;
        this.entityManagerFactory = entityManagerFactory;
    }
    /**
     * Saves a Device entity to the database.
     *
     * @param device The Device entity to be saved.
     * @return True if the operation is successful, false otherwise.
     * @throws IllegalArgumentException if the provided Device entity is null.
     */
    @Override
    public boolean save(Device device) {
        if(device == null){
            throw new IllegalArgumentException("Device is null");
        }

        try(EntityManager em = entityManagerFactory.createEntityManager()){
            EntityTransaction et = em.getTransaction();
            et.begin();
            DeviceDataModel deviceDataModel = new DeviceDataModel(device);
            em.persist(deviceDataModel);
            et.commit();
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }
    /**
     * Retrieves all Device entities from  a room in the database.
     * It's used a constant JPQL to this operation in QUERY.
     * @return A List containing all Device entities from a given room.
     */

    @Override
    public Iterable<Device> findByRoomID(RoomIDVO roomID) {
        try (EntityManager em = this.entityManagerFactory.createEntityManager()) {
            TypedQuery<DeviceDataModel> query = em.createQuery("SELECT d FROM DeviceDataModel d WHERE d.roomID = :roomID", DeviceDataModel.class);
            query.setParameter("roomID", roomID.getID());
            List<DeviceDataModel> list = query.getResultList();
            return DeviceAssembler.toDomainList(deviceFactory, list);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
    /**
     * Retrieves all Device entities from the database.
     * It's used a constant JPQL to this operation in QUERY.
     * @return An Iterable containing all Device entities.
     */

    @Override
    public Iterable<Device> findAll() {
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Query query = em.createQuery(QUERY);
            List<DeviceDataModel> devicesDataModels = query.getResultList();
            return DeviceAssembler.toDomainList(deviceFactory, devicesDataModels);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
    /**
     * Retrieves a Device entity from the database by its ID.
     * @param id The ID of the Device entity to retrieve.
     * @return The Device entity corresponding to the provided ID, null if not found or any RunTimeException occurred.
     * @throws IllegalArgumentException if the provided ID is null.
     */
    @Override
    public Device findById(DeviceIDVO id) {
    if(isNull(id)) {
        throw new IllegalArgumentException("ID is null");
    }
        try(EntityManager em = entityManagerFactory.createEntityManager()){

        Optional<DeviceDataModel> deviceDataModel = getDeviceDataModelFromDeviceId(em,id);
        if(deviceDataModel.isPresent()){
            DeviceDataModel deviceDataModelFromOptional = deviceDataModel.get();
            return DeviceAssembler.toDomain(deviceFactory,deviceDataModelFromOptional);
        }
        return null;

    }catch (RuntimeException e){
        return null;
    }
}
    /**
     * Checks if a Device entity with the specified ID exists in the database.
     * @param id The ID of the Device entity to check.
     * @return True if a Device entity with the specified ID exists, false otherwise or any RunTimeException occurred.
     * @throws IllegalArgumentException if the provided ID is null.
     */

    @Override
    public boolean isPresent(DeviceIDVO id) {
        if (isNull(id)) {
            throw new IllegalArgumentException("ID is null");
        }
        try (EntityManager em = entityManagerFactory.createEntityManager()) {

            Optional<DeviceDataModel> deviceDataModel = getDeviceDataModelFromDeviceId(em, id);
            return deviceDataModel.isPresent();
        } catch (RuntimeException e) {
            return false;
        }
    }
    /**
     * Updates a Device entity in the database.
     * 1. Obtains DeviceDataModel object associated with the given device id, from database;
     * 2. Invoked updateFromDomain() method to make the retrieved DeviceDataModel update it's attributes based on
     * the given device attributes.
     * 3. Merge the updated DeviceDataModel
     * @param device The updated Device entity.
     * @return True if the update operation is successful, false otherwise, any RunTimeException
     * occurred or if not possible to update the DeviceDataModel Object
     *
     */

    public boolean update(Device device) {
        if(isNull(device)){
            return false;
        }

        EntityTransaction et = null;

        try(EntityManager em = this.entityManagerFactory.createEntityManager()){
            DeviceIDVO id = device.getId();
            Optional<DeviceDataModel> optionalDeviceDataModel = getDeviceDataModelFromDeviceId(em,id);

            if(optionalDeviceDataModel.isPresent()){
                DeviceDataModel deviceDataModel = optionalDeviceDataModel.get();

                if(deviceDataModel.updateFromDomain(device)){
                    et = em.getTransaction();
                    et.begin();
                    em.merge(deviceDataModel);
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
     * Retrieves a DeviceDataModel entity from the database by its ID.
     * @param em The EntityManager used to perform the database operation.
     * @param id The ID of the DeviceDataModel entity to retrieve.
     * @return An Optional containing the retrieved DeviceDataModel entity, or empty if not found.
     */

    private Optional<DeviceDataModel> getDeviceDataModelFromDeviceId(EntityManager em, DeviceIDVO id){
        String deviceID = id.getID();
        return Optional.ofNullable(em.find(DeviceDataModel.class, deviceID));
    }

    /**
     * Helper method to check if an object is null.
     * @param object The object to check.
     * @return True if the object is null, false otherwise.
     */
    private boolean isNull(Object object){
        return object == null;
    }
}

