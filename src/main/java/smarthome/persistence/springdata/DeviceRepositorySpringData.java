package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;

import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.assembler.DeviceAssembler;
import smarthome.persistence.DeviceRepository;

import smarthome.persistence.jpa.datamodel.DeviceDataModel;
import java.util.Collections;
import java.util.Optional;

@Repository
public class DeviceRepositorySpringData implements DeviceRepository {

    private final IDeviceRepositorySpringData iDeviceRepositorySpringData;
    private final DeviceFactory deviceFactory;

    /**
     * Constructs a new DeviceRepositorySpringData instance with the specified dependencies.
     *
     * @param deviceFactory               The factory for creating device entities.
     * @param iDeviceRepositorySpringData The Spring Data JPA repository for device entities.
     */

    public DeviceRepositorySpringData(DeviceFactory deviceFactory, IDeviceRepositorySpringData iDeviceRepositorySpringData){
        this.iDeviceRepositorySpringData = iDeviceRepositorySpringData;
        this.deviceFactory = deviceFactory;
    }

    /**
     * Saves a device entity to the database.
     *
     * @param device The device entity to save.
     * @return true if the device was successfully saved, false otherwise.
     * @throws IllegalArgumentException if the device is null.
     */

    @Override
    public boolean save(Device device) {
        if(isNull(device)) {
            throw new IllegalArgumentException("Device is null");
        }
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        try{
            this.iDeviceRepositorySpringData.save(deviceDataModel);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    /**
     * Retrieves all device entities from the database.
     *
     * @return An iterable of device entities. Null if a DataAccessExceptions occurs.
     */

    @Override
    public Iterable<Device> findAll() {
        try{
            Iterable<DeviceDataModel> deviceDataModelIterable = this.iDeviceRepositorySpringData.findAll();
            return DeviceAssembler.toDomainList(this.deviceFactory,deviceDataModelIterable);
        }catch (DataAccessException e){
            return null;
        }

    }

    /**
     * Finds a device entity by its ID.
     *
     * @param id The ID of the device entity.
     * @return The device entity if found, otherwise null.
     */

    @Override
    public Device findById(DeviceIDVO id) {
        if(isNull(id)){
            return null;
        }
        String deviceID = id.getID();
        try{
            Optional<DeviceDataModel> deviceDataModelOpt = this.iDeviceRepositorySpringData.findById(deviceID);

            if(deviceDataModelOpt.isPresent()){
                DeviceDataModel deviceDataModel = deviceDataModelOpt.get();
                return DeviceAssembler.toDomain(this.deviceFactory,deviceDataModel);
            }
            return null;

        }catch (DataAccessException e){
            return null;
        }
    }
    /**
     * Finds devices associated with a specific room ID.
     *
     * @param roomID The ID of the room to search for devices.
     * @return A list of devices associated with the specified room ID. If no devices are found or if an error occurs during the search, an empty list is returned.
     */

    @Override
    public Iterable<Device> findByRoomID(RoomIDVO roomID) {
        try {
            Iterable<DeviceDataModel> deviceDataModelIterable = this.iDeviceRepositorySpringData.findByRoomID(roomID.getID());
            return DeviceAssembler.toDomainList(deviceFactory, deviceDataModelIterable);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Checks if a device entity with the given ID exists in the database.
     *
     * @param id The ID of the device entity.
     * @return true if the device entity exists, false otherwise.
     */

    @Override
    public boolean isPresent(DeviceIDVO id) {
        if(isNull(id)){
            return false;
        }
        return findById(id) != null;
    }



    /**
     * Updates a Device entity in the database.
     * 1. Obtains DeviceDataModel object associated with the given device id, from database;
     * 2. Invoked updateFromDomain() method to make the retrieved DeviceDataModel update it's attributes based on
     * the given device attributes.
     * 3. Save the updated DeviceDataModel. This save method will persist if entity is not yet managed or merge if already managed.
     * @param device The updated Device entity.
     * @return True if the update operation is successful, false otherwise, any DataAccessException
     * occurred or if not possible to update the DeviceDataModel Object
     *
     */

    public boolean update(Device device) {
        if(isNull(device)){
            return false;
        }

        try{
            String deviceID = device.getId().getID();
            Optional<DeviceDataModel> deviceDataModelOpt = this.iDeviceRepositorySpringData.findById(deviceID);

            if(deviceDataModelOpt.isPresent()){
                DeviceDataModel deviceDataModel = deviceDataModelOpt.get();

                if(deviceDataModel.updateFromDomain(device)){
                    this.iDeviceRepositorySpringData.save(deviceDataModel);
                    return true;
                }
                return false;
            }
            return false;
        }catch (DataAccessException e){
            return false;
        }
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


}
