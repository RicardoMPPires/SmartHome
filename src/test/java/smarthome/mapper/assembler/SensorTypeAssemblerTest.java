package smarthome.mapper.assembler;

import org.junit.jupiter.api.Test;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.sensortype.SensorTypeFactoryImpl;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.persistence.jpa.datamodel.SensorTypeDataModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for the SensorTypeAssembler class.
 * This test class provides test cases for the conversion methods in SensorTypeAssembler,
 * specifically ensuring that data models are correctly transformed into domain objects.
 */
class SensorTypeAssemblerTest {

    /**
     * Tests the creation of a SensorTypeDataModel from a SensorType domain object.
     * Ensures that the data model correctly reflects the ID and unit of the SensorType.
     */

    @Test
    void whenDataModelCreated_thenReturnsSensorTypeDataModel() {
        // Arrange
        String sensorTypeID = "Temperature";
        String unit = "Celsius";

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        UnitVO unitVO = new UnitVO(unit);

        SensorType sensorType = new SensorType(sensorTypeIDVO, unitVO);
        String expectedId = sensorType.getId().getID();
        String expectedUnit = sensorType.getUnit().getValue();

        // Act
        SensorTypeDataModel SensorTypeDataModel = new SensorTypeDataModel(sensorType);

        // Assert
        assertEquals(SensorTypeDataModel.getSensorTypeId(), expectedId);
        assertEquals(SensorTypeDataModel.getUnit(), expectedUnit);
    }

    /**
     * Tests the toDomain method of the SensorTypeAssembler.
     * Verifies that the method correctly converts a SensorTypeDataModel to a SensorType domain object,
     * checking that the resulting domain object's ID and unit match the input data model.
     */

    @Test
    void whenToDomainIsCalled_thenReturnsSensorTypeDomainObject() {
        // Arrange
        String sensorTypeID = "Temperature";
        String unit = "Celsius";

        SensorTypeIDVO sensorTypeIDVO = new SensorTypeIDVO(sensorTypeID);
        UnitVO unitVO = new UnitVO(unit);

        SensorType sensorType = new SensorType(sensorTypeIDVO, unitVO);

        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);
        String sensorTypeId = sensorTypeDataModel.getSensorTypeId();
        String unitOfSensor = sensorTypeDataModel.getUnit();

        SensorTypeFactory factory = new SensorTypeFactoryImpl();

        // Act
        SensorType sensorTypeDomain = SensorTypeAssembler.toDomain(factory, sensorTypeDataModel);

        // Arrange
        assertEquals(sensorTypeDomain.getId().getID(), sensorTypeId);
        assertEquals(sensorTypeDomain.getUnit().getValue(), unitOfSensor);
    }

    /**
     *  Tests the toDomain method's ability to process a list of SensorTypeDataModels.
     *  Ensures that the method returns a list of SensorType domain objects, verifying that each
     *  domain object in the list correctly reflects the corresponding data model's ID and unit.
     */

    @Test
    void whenToDomainIsCalled_thenReturnsListWithSensorTypeDomainObjects() {
        // Arrange
        String sensorTypeID1 = "Temperature";
        String unit1 = "Celsius";
        String sensorTypeID2 = "Humidity";
        String unit2 = "%";

        SensorTypeIDVO sensorTypeIDVO1 = new SensorTypeIDVO(sensorTypeID1);
        UnitVO unitVO1 = new UnitVO(unit1);

        SensorTypeIDVO sensorTypeIDVO2 = new SensorTypeIDVO(sensorTypeID2);
        UnitVO unitVO2 = new UnitVO(unit2);

        SensorType sensorType1 = new SensorType(sensorTypeIDVO1, unitVO1);
        SensorType sensorType2 = new SensorType(sensorTypeIDVO2, unitVO2);


        SensorTypeDataModel sensorTypeDataModel1 = new SensorTypeDataModel(sensorType1);
        SensorTypeDataModel sensorTypeDataModel2 = new SensorTypeDataModel(sensorType2);

        List<SensorTypeDataModel> sensorTypeDataModelList = List.of(sensorTypeDataModel1, sensorTypeDataModel2);

        SensorTypeFactory factory = new SensorTypeFactoryImpl();

        // Act
        Iterable<SensorType> sensorTypeIterableList = SensorTypeAssembler.toDomain(factory, sensorTypeDataModelList);

        // Assert
        List<SensorType> sensorTypeList = new ArrayList<>();
        sensorTypeIterableList.forEach(sensorTypeList::add);

        SensorType sensorTypeFirst = sensorTypeList.get(0);
        SensorType sensorTypeSecond = sensorTypeList.get(1);

        assertEquals(sensorTypeDataModel1.getSensorTypeId(), sensorTypeFirst.getId().getID());
        assertEquals(sensorTypeDataModel1.getUnit(), sensorTypeFirst.getUnit().getValue());
        assertEquals(sensorTypeDataModel2.getSensorTypeId(), sensorTypeSecond.getId().getID());
        assertEquals(sensorTypeDataModel2.getUnit(), sensorTypeSecond.getUnit().getValue());
    }
}
