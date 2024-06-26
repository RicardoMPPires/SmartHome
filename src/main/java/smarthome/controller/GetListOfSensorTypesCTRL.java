package smarthome.controller;

import smarthome.domain.sensortype.SensorType;
import smarthome.mapper.dto.SensorTypeDTO;
import smarthome.mapper.SensorTypeMapper;
import smarthome.service.SensorTypeService;

import java.util.List;

public class GetListOfSensorTypesCTRL {

    private final SensorTypeService sensorTypeService;


    /**
     * Constructs an instance of GetListOfSensorTypesCTRL with the specified SensorTypeService.
     * It ensures that the provided SensorTypeService is not null and throws an IllegalArgumentException if it is.
     *
     * @param sensorTypeService The SensorTypeService used to retrieve a list of sensor types.
     * @throws IllegalArgumentException if the sensorTypeService parameter is null.
     */
    public GetListOfSensorTypesCTRL(SensorTypeService sensorTypeService) {
        if (areParamsNull(sensorTypeService)) {
            throw new IllegalArgumentException("Invalid parameters.");
        }
        this.sensorTypeService = sensorTypeService;
    }

    /**
     * Obtains a list of sensor types by communicating with the encapsulated sensorTypeService.
     * It then communicates with the SensorTypeMapper to translate the list of sensor types into a list of SensorTypeDTO.
     *
     * @return List <SensorTypeDTO>
     */
    public List<SensorTypeDTO> getListOfSensorTypes() {
        List<SensorType> sensorTypeList = this.sensorTypeService.getListOfSensorTypes();
        return SensorTypeMapper.domainToDTO(sensorTypeList);
    }


    /**
     * Checks if any of the provided parameters are null.
     *
     * @param params The parameters to check for null.
     * @return true if any of the parameters are null, false otherwise.
     */
    private boolean areParamsNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                return true;
            }
        }
        return false;
    }
}
