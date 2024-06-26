package smarthome.controller;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.mapper.dto.ActuatorDTO;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.ActuatorMapper;
import smarthome.mapper.ActuatorTypeMapper;
import smarthome.mapper.DeviceMapper;
import smarthome.service.ActuatorService;
import smarthome.domain.vo.actuatorvo.Settings;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;


import java.util.Optional;

public class AddActuatorToDeviceCTRL {

    private final ActuatorService actuatorService;

    /**
     * Constructs an instance of AddActuatorToDeviceCTRL with the provided ActuatorService dependency.
     * This constructor initializes the AddActuatorToDeviceCTRL with the ActuatorService used to add actuators to devices.
     * It ensures that the provided ActuatorService is not null and throws an IllegalArgumentException if it is.
     * @param actuatorService The ActuatorService used to add actuators to devices.
     * @throws IllegalArgumentException if the actuatorService parameter is null. This exception is thrown to indicate
     * an invalid or missing service.
     */
    public AddActuatorToDeviceCTRL(ActuatorService actuatorService) {
        if (actuatorService == null) {
            throw new IllegalArgumentException("Invalid service");
        }
        this.actuatorService = actuatorService;
    }

    /**
     * Adds an Actuator to a Device.
     * This method attempts to add an Actuator to a Device using the provided DTOs. It first maps the DTOs to their
     * corresponding Value Objects. Then, it calls the addActuator method of the ActuatorService with these Value
     * Objects. If the Actuator is successfully added (i.e., the returned Optional is not empty), it returns true. If
     * the Actuator is not added (i.e., the returned Optional is empty) or if an IllegalArgumentException is thrown, it
     * returns false.
     * @param actuatorDTO The DTO containing the data for the Actuator to be added.
     * @param actuatorTypeDTO The DTO containing the data for the type of the Actuator to be added.
     * @param deviceDTO The DTO containing the data for the Device to which the Actuator will be added.
     * @return true if the Actuator is successfully added, false otherwise.
     * @throws IllegalArgumentException if any of the DTOs are null or if they contain invalid data.
     */
    public boolean addActuatorToDevice(ActuatorDTO actuatorDTO, ActuatorTypeDTO actuatorTypeDTO, DeviceDTO deviceDTO) {
        try{
            ActuatorNameVO actuatorNameVO = ActuatorMapper.createActuatorNameVO(actuatorDTO);
            Settings settings = ActuatorMapper.createSettingsVO(actuatorDTO);
            ActuatorTypeIDVO actuatorTypeIDVO = ActuatorTypeMapper.createActuatorTypeIDVO(actuatorTypeDTO);
            DeviceIDVO deviceIDVO = DeviceMapper.createDeviceID(deviceDTO);
            Optional<Actuator> opt = actuatorService.addActuator(actuatorNameVO,actuatorTypeIDVO,deviceIDVO,settings);
            return (opt.isPresent());
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
