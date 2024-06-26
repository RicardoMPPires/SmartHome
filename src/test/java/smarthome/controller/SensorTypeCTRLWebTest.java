package smarthome.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.mapper.dto.SensorTypeDTO;
import smarthome.service.SensorTypeService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class SensorTypeCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorTypeService sensorTypeService;

    /**
     * This test ensures that the GetListOfSensorTypesWebCTRL controller returns a list of sensor types when a valid service is provided.
     *
     * @throws Exception If an error occurs during the test.
     * @assert: The controller returns a list of sensor types and those sensor types are correctly mapped to DTOs.
     */
    @Test
    public void givenValidService_whenGetSensorTypes_thenReturnSensorTypeList() throws Exception {
        SensorTypeIDVO sensorTypeIDVO1 = new SensorTypeIDVO("1");
        SensorTypeIDVO sensorTypeIDVO2 = new SensorTypeIDVO("2");
        UnitVO unitVO1 = new UnitVO("Celsius");
        UnitVO unitVO2 = new UnitVO("Percentage");
        SensorType sensorType1 = new SensorType(sensorTypeIDVO1, unitVO1);
        SensorType sensorType2 = new SensorType(sensorTypeIDVO2, unitVO2);
        List<SensorType> sensorTypes = Arrays.asList(sensorType1, sensorType2);

        given(sensorTypeService.getListOfSensorTypes()).willReturn(sensorTypes);

        MvcResult mvcResult = mockMvc.perform(get("/sensortypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseNode = mapper.readTree(responseBody);
        JsonNode sensorTypeDTOsNode = responseNode.get("_embedded").get("sensorTypeDTOList");
        List<SensorTypeDTO> sensorTypeDTOs = mapper.convertValue(sensorTypeDTOsNode, new TypeReference<List<SensorTypeDTO>>() {
        });

        assertEquals(2, sensorTypeDTOs.size());
        assertEquals("1", sensorTypeDTOs.get(0).getSensorTypeID());
        assertEquals("Celsius", sensorTypeDTOs.get(0).getUnit());
        assertEquals("2", sensorTypeDTOs.get(1).getSensorTypeID());
        assertEquals("Percentage", sensorTypeDTOs.get(1).getUnit());
    }
}

