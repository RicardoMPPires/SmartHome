package smarthome.persistence.mem;

import smarthome.domain.house.House;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.persistence.HouseRepository;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Optional;

public class HouseRepositoryMem implements HouseRepository {

    private final LinkedHashMap<HouseIDVO, House> data = new LinkedHashMap<>();


    /**
     * Saves a HouseEntity unto the repository
     * @return False if entity is null or ID is null or already present, True otherwise.
     */
    @Override
    public boolean save(House entity) {
        if(entity == null || entity.getId() == null || isPresent(entity.getId())){
            return false;
        }
       data.put(entity.getId(),entity);
        return true;
    }


    /**
     * Finds all HouseEntities saved unto the repository;
     * @return Iterable.
     */
    @Override
    public Iterable<House> findAll() {
        return data.values();
    }


    /**
     * Finds HouseEntity by IDVO
     * @param id IDVO
     * @return HouseEntity matching the inserted IDVO if present, null otherwise.
     */
    @Override
    public House findById(HouseIDVO id) {
        if(isPresent(id)){
            return data.get(id);
        } else{
            return null;
        }
    }


    /**
     * Verifies if a HouseEntity is present in the repository, queries by ID;
     * @param id IDVO
     * @return True if present, False otherwise.
     */
    @Override
    public boolean isPresent(HouseIDVO id) {
        return data.containsKey(id);
    }

    /**
     * Getter method to retrieve the first House
     * @return An Optional with the first found House entity, an empty Optional if there is no House in the map.
     */
    public Optional<House> getFirstHouse(){
        Iterator<House> iterator = this.data.values().iterator();
        if(!iterator.hasNext()){
            return Optional.empty();
        }
        else {
            return Optional.of(iterator.next());
        }
    }
    /**
     * Getter method to retrieve the first HouseIDVO
     * @return FirstHouseIDVO
     */
    public HouseIDVO getFirstHouseIDVO() {
        Iterator<HouseIDVO> iterator = this.data.keySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        } else {
            return iterator.next();
        }
    }

    @Override
    public boolean update(House entity) {
        if(entity == null || entity.getId() == null){
            return false;
        }
        data.put(entity.getId(),entity);
        return true;
    }
}
