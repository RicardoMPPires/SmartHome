package smarthome.persistence.springdata;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactory;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.mapper.assembler.HouseAssembler;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.jpa.datamodel.HouseDataModel;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the HouseRepository interface using Spring Data JPA.
 * This class interacts with the database to perform CRUD operations on house entities.
 */

@Repository
public class HouseRepositorySpringData implements HouseRepository {

    private final IHouseRepositorySpringData iHouseRepositorySpringData;
    private final HouseFactory houseFactory;

    /**
     * Constructs a new HouseRepositorySpringData instance with the specified dependencies.
     *
     * @param houseFactory               The factory for creating house entities.
     * @param iHouseRepositorySpringData The Spring Data JPA repository for house entities.
     */

    public HouseRepositorySpringData(HouseFactory houseFactory, IHouseRepositorySpringData iHouseRepositorySpringData){
        this.iHouseRepositorySpringData = iHouseRepositorySpringData;
        this.houseFactory = houseFactory;
    }

    /**
     * Saves a house entity to the database.
     *
     * @param house The house entity to save.
     * @return true if the house was successfully saved, false otherwise.
     * @throws IllegalArgumentException if the house is null.
     */

    @Override
    public boolean save(House house) {
        if(isNull(house)) {
            throw new IllegalArgumentException("House is null");
        }
        HouseDataModel houseDataModel = new HouseDataModel(house);

        try{
            this.iHouseRepositorySpringData.save(houseDataModel);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }

    /**
     * Retrieves all house entities from the database.
     *
     * @return An iterable of house entities. Null if a DataAccessExceptions occurs.
     */

    @Override
    public Iterable<House> findAll() {
        try{
            Iterable<HouseDataModel> houseDataModelIterable = this.iHouseRepositorySpringData.findAll();
            return HouseAssembler.toDomain(this.houseFactory,houseDataModelIterable);
        }catch (DataAccessException e){
            return null;
        }

    }

    /**
     * Finds a house entity by its ID.
     *
     * @param id The ID of the house entity.
     * @return The house entity if found, otherwise null.
     */

    @Override
    public House findById(HouseIDVO id) {
        if(isNull(id)){
            return null;
        }
        String houseID = id.getID();
        try{
            Optional<HouseDataModel> houseDataModelOpt = this.iHouseRepositorySpringData.findById(houseID);

            if(houseDataModelOpt.isPresent()){
                HouseDataModel houseDataModel = houseDataModelOpt.get();
                return HouseAssembler.toDomain(this.houseFactory,houseDataModel);
            }
            return null;

        }catch (DataAccessException e){
            return null;
        }
    }

    /**
     * Checks if a house entity with the given ID exists in the database.
     *
     * @param id The ID of the house entity.
     * @return true if the house entity exists, false otherwise.
     */

    @Override
    public boolean isPresent(HouseIDVO id) {
        if(isNull(id)){
            return false;
        }
       return findById(id) != null;
    }

    /**
     * Retrieves the first house entity from the database.
     * In the business model only one house is expected so by finding all houses and accessing
     * the first element, it's being obtained the first house.

     * @return The first and only house entity if found, wrapped in a House Optional, otherwise an empty Optional.
     */

    @Override
    public Optional<House> getFirstHouse() {
        try{
            List<HouseDataModel> houseDataModelList = this.iHouseRepositorySpringData.findAll();

            if(!houseDataModelList.isEmpty()){
                HouseDataModel houseDataModel = houseDataModelList.get(0);
                return Optional.of(HouseAssembler.toDomain(houseFactory,houseDataModel));
            }
            return Optional.empty();
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves the ID of the first house entity from the database.
     *
     * @return The ID of the first house entity if found, otherwise null.
     */

    @Override
    public HouseIDVO getFirstHouseIDVO() {
        Optional<House> house = getFirstHouse();
        return house.map(House::getId).orElse(null);
    }

    /**
     * Updates a House entity in the database.
     * 1. Obtains HouseDataModel object associated with the given house id, from database;
     * 2. Invoked updateFromDomain() method to make the retrieved HouseDataModel update it's attributes based on
     * the given house attributes.
     * 3. Save the updated HouseDataModel. This save method will persist if entity is not yet managed or merge if already managed.
     * @param house The updated House entity.
     * @return True if the update operation is successful, false otherwise, any DataAccessException
     * occurred or if not possible to update the HouseDataModel Object
     *
     */

    @Override
    public boolean update(House house) {
        if(isNull(house)){
            return false;
        }

        try{
            String houseID = house.getId().getID();
            Optional<HouseDataModel> houseDataModelOpt = this.iHouseRepositorySpringData.findById(houseID);

            if(houseDataModelOpt.isPresent()){
                HouseDataModel houseDataModel = houseDataModelOpt.get();

                if(houseDataModel.updateFromDomain(house)){
                    this.iHouseRepositorySpringData.save(houseDataModel);
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
