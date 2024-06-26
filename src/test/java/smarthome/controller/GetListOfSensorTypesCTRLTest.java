package smarthome.controller;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.sensortype.SensorTypeFactoryImpl;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.mapper.dto.SensorTypeDTO;
import smarthome.persistence.SensorTypeRepository;
import smarthome.service.SensorTypeService;
import smarthome.service.SensorTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetListOfSensorTypesCTRLTest {

    /**
     * Test for the constructor of GetListOfSensorTypesCTRL.
     * Tests if the constructor throws an IllegalArgumentException when the sensorTypeService is null.
     */
    @Test
    void whenNullSensorTypeService_constructorThrowsIllegalArgumentException() {
        //Arrange
        String expectedMessage = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GetListOfSensorTypesCTRL(null));

        //Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test for the getListOfSensorTypes method of AddSensorToDeviceCTRL.
     * Tests if the method returns a list of SensorTypeDTO of all the available sensor types.
     * The expected size of the list is 12, which corresponds to the number of sensor types in the config.properties file.
     * The first sensor type in the list is HumiditySensor.
     * The mock SensorTypeRepository is set up to return a list of SensorType objects with the same sensor types as the
     * config.properties file.
     * The method should return a list of SensorTypeDTO with a size of 12 and the first sensor type id should be
     * HumiditySensor.
     */
    @Test
    void whenGetListOfSensorTypes_thenReturnListOfSensorTypeDTO() throws ConfigurationException {
        //Arrange
        int expectedSize = 12;
        String expectedSensorTypeID = "HumiditySensor";
        String path = "config.properties";
        SensorTypeRepository doubleSensorTypeRepository = mock(SensorTypeRepository.class);
        Configurations config = new Configurations();
        Configuration configuration = config.properties(path);
        String[] arrayOfTypes = configuration.getStringArray("unit");
        List<SensorType> mockSensorTypes = new ArrayList<>();
        for (String string : arrayOfTypes) {
            String[] splitString = string.split("\\|");
            SensorTypeIDVO type = new SensorTypeIDVO(splitString[0]);
            UnitVO unit = new UnitVO(splitString[1]);
            SensorType sensorType = new SensorType(type, unit);
            mockSensorTypes.add(sensorType);
        }
        when(doubleSensorTypeRepository.findAll()).thenReturn(mockSensorTypes);
        SensorTypeFactory sensorTypeFactory = new SensorTypeFactoryImpl();
        SensorTypeService sensorTypeService = new SensorTypeServiceImpl(doubleSensorTypeRepository, sensorTypeFactory, path);

        GetListOfSensorTypesCTRL getListOfSensorTypesCTRL = new GetListOfSensorTypesCTRL(sensorTypeService);

        //Act
        List<SensorTypeDTO> result = getListOfSensorTypesCTRL.getListOfSensorTypes();

        //Assert
        assertEquals(expectedSize, result.size());
        assertEquals(expectedSensorTypeID, result.get(0).getSensorTypeID());
    }

}