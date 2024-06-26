package smarthome.persistence.mem;

import smarthome.domain.device.Device;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.persistence.DeviceRepository;

import java.util.*;
import java.util.stream.Collectors;


public class DeviceRepositoryMem implements DeviceRepository {

    private final LinkedHashMap<DeviceIDVO, Device> data = new LinkedHashMap<>();

    /**
     * Saves an entity onto the repository;
     *
     * @param entity Entity
     * @return True or False
     */
    @Override
    public boolean save(Device entity) {
        if (!validateEntity(entity) || data.containsKey(entity.getId())){
            return false;
        }
        data.put(entity.getId(), entity);
        return true;
    }

    /**
     * Verifies if an entity is valid;
     *
     * @param entity Entity
     * @return True or False
     */
    private boolean validateEntity(Device entity) {
        return entity != null && entity.getId() != null;
    }

    /**
     * Finds all entities saved onto the repository;
     *
     * @return Iterable.
     */
    @Override
    public Iterable<Device> findAll() {
        return data.values();
    }

    /**
     * Finds an entity by its ID;
     *
     * @param id IDVO
     * @return Entity
     */
    @Override
    public Device findById(DeviceIDVO id) {
        if (!isPresent(id)) {
            return null;
        } else {
            return data.get(id);
        }
    }

    /**
     * Verifies if an entity is present in the repository;
     *
     * @param id IDVO
     * @return True or False
     */
    @Override
    public boolean isPresent(DeviceIDVO id) {
        return data.containsKey(id);
    }

    /**
     * Finds all devices in a room by RoomIDVO
     *
     * @param roomID RoomIDVO
     * @return List of devices in the room
     */
    public List<Device> findByRoomID(RoomIDVO roomID) {
        return data.values().stream()
                .filter(device -> roomID.equals(device.getRoomID()))
                .collect(Collectors.toList());
    }
    @Override
    public boolean update(Device entity) {
        if(entity == null || entity.getId() == null){
            return false;
        }
        data.put(entity.getId(),entity);
        return true;
    }
}