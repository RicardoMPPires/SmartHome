package smarthome.mapper.assembler;

import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.persistence.jpa.datamodel.SensorTypeDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides static utility methods for converting SensorTypeDataModel objects to
 * SensorType domain objects. This class is used to translate data retrieved
 * from a persistence layer into domain objects that can be used throughout the application.
 */
public class SensorTypeAssembler {

    /**
     * Private constructor to prevent instantiation of the class,
     * enforcing that all functionality is accessed statically
     */
    private SensorTypeAssembler(){}

    /**
     * Converts a single SensorTypeDataModel into a SensorType domain object.
     * This method utilizes a SensorTypeFactory to create a new SensorType
     * instance based on the identifiers and value objects provided by the data model.
     *
     * @param factory The factory to create a SensorType domain object.
     * @param sensorTypeDataModel The data model that contains data retrieved from the database.
     * @return A new SensorType domain object corresponding to the provided data model.
     */
    public static SensorType toDomain(SensorTypeFactory factory, SensorTypeDataModel sensorTypeDataModel)
    {
        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeDataModel.getSensorTypeId());
        UnitVO unitVO = new UnitVO(sensorTypeDataModel.getUnit());
        return factory.createSensorType(sensorTypeIDVO, unitVO);
    }


    /**
     * Converts an Iterable of SensorTypeDataModel objects to a List of
     * SensorType domain objects. Each data model object is individually transformed into
     * a domain object, and the resulting list contains all the created domain objects.
     *
     * @param factory The factory to create SensorType domain objects.
     * @param listDataModel An iterable collection of SensorTypeDataModel objects.
     * @return A list of SensorType domain objects corresponding to the provided list of data models.
     */
    public static Iterable<SensorType> toDomain(SensorTypeFactory factory, Iterable<SensorTypeDataModel> listDataModel)
    {
        List<SensorType> listDomain = new ArrayList<>();

        listDataModel.forEach( sensorTypeDataModel ->
        {
            SensorType sensorTypeDomain = toDomain(factory, sensorTypeDataModel);

            listDomain.add(sensorTypeDomain);
        });

        return listDomain;
    }
}
