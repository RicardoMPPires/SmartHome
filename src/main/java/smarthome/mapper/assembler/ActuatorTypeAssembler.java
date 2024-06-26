package smarthome.mapper.assembler;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.persistence.jpa.datamodel.ActuatorTypeDataModel;

import java.util.ArrayList;
import java.util.List;

/** This class provides static utility methods used to translate data retrieved
 * from a persistence layer into domain objects that can be used throughout the application.
 * It converts ActuatorTypeDataModel objects to ActuatorType domain objects.
 */
public class ActuatorTypeAssembler {

    /** This constructor is set to private to ensure that no objects of this class will be instantiated.
     * Given that all of its methods are static, there is no need to instantiate an ActuatorTypeAssembler object to
     * access the utility methods provided bellow.
     * */
    private ActuatorTypeAssembler() {}

    /**
     * This method converts an ActuatorTypeDataModel object into an ActuatorType domain object
     * It is injected with an ActuatorTypeFactory and an ActuatorTypeDataModel is also given as parameter.
     * for the ActuatorType object to be created, the method first instantiates a ActuatorTypeIDVO, necessary for the creation
     * of the ActuatorTYpe, using the getter from the ActuatorTypeDataModel class. Then, it uses the ActuatorTypeFactory to create
     * the ActuatorType object with the ActuatorTypeIDVO, returning the ActuatorType object.
     *
     * @param actuatorTypeFactory The ActuatorTypeFactory used to create ActuatorType objects.
     * @param actuatorTypeDataModel The ActuatorTypeDataModel object to be converted.
     * @return The ActuatorType object created.
     */
    public static ActuatorType actuatorTypeToDomain(ActuatorTypeFactory actuatorTypeFactory, ActuatorTypeDataModel actuatorTypeDataModel){
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeDataModel.getActuatorTypeID());
        return actuatorTypeFactory.createActuatorType(actuatorTypeIDVO);
    }


    /**
     * This method converts an Iterable of ActuatorTypeDataModel objects into a list of ActuatorType domain objects.
     * It is injected with an ActuatorTypeFactory and an Iterable of ActuatorTypeDataModel objects is also given as parameter.
     * The method first creates an empty list of ActuatorType objects. Then, it iterates over the ActuatorTypeDataModel objects
     * and converts each one to an ActuatorType object using the actuatorTypeToDomain() method. Then, it adds each one of these to
     * the list created previously. Finally, it returns the list of ActuatorType objects.
     *
     * @param actuatorTypeFactory The ActuatorTypeFactory used to create ActuatorType objects.
     * @param actuatorTypeDataModelList The Iterable of ActuatorTypeDataModel objects to be converted.
     * @return The list of ActuatorType objects created.
     */
    public static Iterable<ActuatorType> actuatorTypeListToDomain(ActuatorTypeFactory actuatorTypeFactory, Iterable<ActuatorTypeDataModel> actuatorTypeDataModelList) {
        List<ActuatorType> actuatorTypeList = new ArrayList<>();

        actuatorTypeDataModelList.forEach(actuatorTypeDataModel -> {
            ActuatorType actuatorType = actuatorTypeToDomain(actuatorTypeFactory,actuatorTypeDataModel);
            actuatorTypeList.add(actuatorType);
        });

        return actuatorTypeList;
    }
}
