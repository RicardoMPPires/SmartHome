package smarthome.mapper.assembler;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.actuatortype.ActuatorTypeFactoryImpl;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.persistence.jpa.datamodel.ActuatorTypeDataModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ActuatorTypeAssemblerTest is a test class for the ActuatorTypeAssembler class, providing test cases for the
 * conversion methods in ActuatorTypeAssembler.
 */
class ActuatorTypeAssemblerTest {

    /**
     * This test verifies that when the actuatorTypeToDomain method is called, the correct ActuatorType object is created.
     * It first creates a String with a valid actuatorTypeID, then creates an ActuatorTypeIDVO object with this String.
     * Afterward, instantiates an ActuatorType object with the ActuatorTypeIDVO object.
     * It then creates an ActuatorTypeDataModel receiving the ActuatorType object as a parameter.
     * It creates an ActuatorTypeFactoryImpl object and, then, strings with the expected id retrieved from the ActuatorType object
     * adn the ActuatorTypeDataModel object, respectively.
     * Then, it instantiates an ActuatorType object using the actuatorTypeToDomain method and, finally, checks if the id of
     * the created object is equals both to the expected id from the ActuatorType instantiated to converto to a data model
     * and the expected id from the ActuatorTypeDataModel object.
     */
    @Test
    void whenActuatorTypeToDomainMethodIsCalled_thenReturnsActuatorType() {
        //Arrange
        String actuatorTypeID = "SwitchActuator";

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);

        ActuatorType actuatorType = new ActuatorType(actuatorTypeIDVO);

        ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);

        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactoryImpl();

        String expected1 = actuatorType.getId().getID();
        String expected2 = actuatorTypeDataModel.getActuatorTypeID();

        //Act
        ActuatorType actuatorTypeFromDataModel = ActuatorTypeAssembler.actuatorTypeToDomain(actuatorTypeFactory,actuatorTypeDataModel);
        String result = actuatorTypeFromDataModel.getId().getID();

        //Assert
        assertEquals(expected1,result);
        assertEquals(expected2,result);
    }

    /**
     * This test verifies that when the actuatorTypeListToDomain method is called, the correct ActuatorType list is created.
     * It first creates two actuatorTypeID strings, then creates two ActuatorTypeIDVO objects with these strings.
     * Afterward, instantiates two ActuatorType objects with the ActuatorTypeIDVO objects.
     * It then creates two ActuatorTypeDataModel objects receiving the ActuatorType objects as parameters.
     * It creates a list of ActuatorTypeDataModel objects and adds the two created objects to it.
     * It creates an ActuatorTypeFactoryImpl object and, then, strings with the expected id retrieved from the ActuatorType objects
     * and the ActuatorTypeDataModel objects. It also attributes to an integer the expected size of the list.
     * Then, it instantiates an Iterable of ActuatorType objects using the actuatorTypeListToDomain method.
     * Afterward, it iterates over the Iterable and checks if the size of the list is the expected one, then creating an
     * ActuatorType list to be able to retrieve the ids and checks if the id of the objects in the list are the expected ones,
     * comparing them both to the expected id from the ActuatorType objects instantiated to convert to a data model and the expected id
     * from the ActuatorTypeDataModel objects.
     */
    @Test
    void whenActuatorTypeListToDomainIsCalled_thenReturnsActuatorTypeList() {
        //Arrange
        String actuatorTypeId1 = "SwitchActuator";
        String actuatorTypeId2 = "IntegerValueActuator";

        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO(actuatorTypeId1);
        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO(actuatorTypeId2);

        ActuatorType actuatorType1 = new ActuatorType(actuatorTypeIDVO1);
        ActuatorType actuatorType2 = new ActuatorType(actuatorTypeIDVO2);

        ActuatorTypeDataModel actuatorTypeDataModel1 = new ActuatorTypeDataModel(actuatorType1);
        ActuatorTypeDataModel actuatorTypeDataModel2 = new ActuatorTypeDataModel(actuatorType2);

        List<ActuatorTypeDataModel> dataModelList = new ArrayList<>();
        dataModelList.add(actuatorTypeDataModel1);
        dataModelList.add(actuatorTypeDataModel2);

        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactoryImpl();

        String expectedType1 = actuatorType1.getId().getID();
        String expectedType2 = actuatorType2.getId().getID();

        String expectedDataModel1 = actuatorTypeDataModel1.getActuatorTypeID();
        String expectedDataModel2 = actuatorTypeDataModel2.getActuatorTypeID();

        int expectedSize = 2;

        //Act
        Iterable<ActuatorType> actuatorTypeList = ActuatorTypeAssembler.actuatorTypeListToDomain(actuatorTypeFactory, dataModelList);

        //Assert
        Iterator<ActuatorType> actuatorTypeIterator = actuatorTypeList.iterator();
        int resultSize = IteratorUtils.size(actuatorTypeIterator);

        assertEquals(expectedSize,resultSize);

        List<ActuatorType> actuatorTypeListAux = new ArrayList<>();
        actuatorTypeList.forEach(actuatorTypeListAux::add);

        ActuatorType actuatorTypeAux1 = actuatorTypeListAux.get(0);
        ActuatorType actuatorTypeAux2 = actuatorTypeListAux.get(1);

        String result1 = actuatorTypeAux1.getId().getID();
        String result2 = actuatorTypeAux2.getId().getID();

        assertEquals(expectedType1,result1);
        assertEquals(expectedDataModel1,result1);

        assertEquals(expectedType2,result2);
        assertEquals(expectedDataModel2,result2);

    }

}