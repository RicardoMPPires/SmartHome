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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.persistence.ActuatorTypeRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc
@SpringBootTest
class ActuatorTypeCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActuatorTypeRepository actuatorTypeRepository;


    /**
     * This test ensures that the GetListOfActuatorTypesWebCTRL controller returns a list of actuator types when a valid
     * service is provided.
     *
     * @throws Exception If an error occurs during the test.
     * @assert: The controller returns a list of actuator types and those sensor types are correctly mapped to DTOs.
     */
    @Test
    void whenGetActuatorTypes_thenReturnListOfActuatorTypes() throws Exception {
        ActuatorTypeIDVO actuatorTypeIDVO1 = new ActuatorTypeIDVO("SwitchActuator");
        ActuatorType actuatorType1 = new ActuatorType(actuatorTypeIDVO1);
        ActuatorTypeIDVO actuatorTypeIDVO2 = new ActuatorTypeIDVO("RollerBlindActuator");
        ActuatorType actuatorType2 = new ActuatorType(actuatorTypeIDVO2);
        ActuatorTypeIDVO actuatorTypeIDVO3 = new ActuatorTypeIDVO("DecimalValueActuator");
        ActuatorType actuatorType3 = new ActuatorType(actuatorTypeIDVO3);
        ActuatorTypeIDVO actuatorTypeIDVO4 = new ActuatorTypeIDVO("IntegerValueActuator");
        ActuatorType actuatorType4 = new ActuatorType(actuatorTypeIDVO4);

        List<ActuatorType> actuatorTypeList = Arrays.asList(actuatorType1, actuatorType2, actuatorType3, actuatorType4);

        given(actuatorTypeRepository.findAll()).willReturn(actuatorTypeList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/actuatortypes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseNode = mapper.readTree(content);
        JsonNode actuatorTypeDTOsNode = responseNode.get("_embedded").get("actuatorTypeDTOList");
        List<ActuatorTypeDTO> actuatorTypeDTOS = mapper.convertValue(actuatorTypeDTOsNode, new TypeReference<List<ActuatorTypeDTO>>() {
        });


        assertEquals(4, actuatorTypeDTOS.size());
        assertEquals("SwitchActuator", actuatorTypeDTOS.get(0).getActuatorTypeID());
        assertEquals("RollerBlindActuator", actuatorTypeDTOS.get(1).getActuatorTypeID());
        assertEquals("DecimalValueActuator", actuatorTypeDTOS.get(2).getActuatorTypeID());
        assertEquals("IntegerValueActuator", actuatorTypeDTOS.get(3).getActuatorTypeID());
    }

}