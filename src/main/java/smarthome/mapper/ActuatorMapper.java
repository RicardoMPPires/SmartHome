package smarthome.mapper;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.mapper.dto.ActuatorDTO;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Mapper class for Actuator
 * It contains methods to create ActuatorIDVO, ActuatorNameVO, ActuatorTypeIDVO and DeviceIDVO objects from ActuatorDTO objects.
 * It also contains a method to convert a list of domain Actuator objects to a list of ActuatorDTO objects.
 */

public class ActuatorMapper {

    private static final String ERRORMESSAGE = "ActuatorDTO cannot be null.";

    /**
     * Method to create an ActuatorNameVO object from an ActuatorDTO object.
     * It throws an IllegalArgumentException if the ActuatorDTO object is null.
     *
     * @param actuatorDTO ActuatorDTO object
     * @return ActuatorNameVO object
     */

    public static ActuatorNameVO createActuatorNameVO(ActuatorDTO actuatorDTO) {
        if (actuatorDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        } else {
            return new ActuatorNameVO(actuatorDTO.getActuatorName());
        }
    }

    /**
     * Method to create a Settings object from an ActuatorDTO object.
     * It throws an IllegalArgumentException if the ActuatorDTO object is null.
     *
     * @param actuatorDTO ActuatorDTO object
     * @return Settings object
     */
    public static Settings createSettingsVO(ActuatorDTO actuatorDTO) {
        if (actuatorDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        String lowerLimit = actuatorDTO.getLowerLimit();
        String upperLimit = actuatorDTO.getUpperLimit();
        String precision = actuatorDTO.getPrecision();
        try {
            if (precision == null && lowerLimit != null && upperLimit != null) {
                return new IntegerSettingsVO(lowerLimit, upperLimit);
            }
            if (precision != null && lowerLimit != null && upperLimit != null) {
                return new DecimalSettingsVO(lowerLimit, upperLimit, precision);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
        return null;
    }

    /**
     * Method to create an ActuatorTypeIDVO object from an ActuatorDTO object.
     * It throws an IllegalArgumentException if the ActuatorDTO object is null.
     *
     * @param actuatorDTO ActuatorDTO object
     * @return ActuatorTypeIDVO object
     */

    public static ActuatorTypeIDVO createActuatorTypeIDVO(ActuatorDTO actuatorDTO) {
        if (actuatorDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        } else {
            return new ActuatorTypeIDVO(actuatorDTO.getActuatorTypeID());
        }
    }

    /**
     * Method to create a DeviceIDVO object from an ActuatorDTO object.
     * It throws an IllegalArgumentException if the ActuatorDTO object is null or if the deviceID attribute is null.
     *
     * @param actuatorDTO ActuatorDTO object
     * @return DeviceIDVO object
     */

    public static DeviceIDVO createDeviceIDVO(ActuatorDTO actuatorDTO) {
        if (actuatorDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        } else {
            if (actuatorDTO.getDeviceID() == null) {
                throw new IllegalArgumentException("Device ID cannot be null");
            }
            UUID deviceID = UUID.fromString(actuatorDTO.getDeviceID());
            return new DeviceIDVO(deviceID);
        }
    }

    /**
     * Creates an instance of {@code ActuatorStatusVO} from the provided {@code ActuatorDTO}.
     *
     * @param actuatorDTO the {@code ActuatorDTO} instance containing the actuator status.
     *                    Must not be {@code null}.
     * @return a new instance of {@code ActuatorStatusVO} representing the status of the actuator.
     * @throws IllegalArgumentException if {@code actuatorDTO} is {@code null}.
     */
    public static ActuatorStatusVO createActuatorStatusVO (ActuatorDTO actuatorDTO){
        if (actuatorDTO == null) {
            throw new IllegalArgumentException(ERRORMESSAGE);
        } else {
            return new ActuatorStatusVO(actuatorDTO.getStatus());
        }
    }

    /**
     * Method to convert a domain Actuator object to a DTO ActuatorDTO object.
     * It throws an IllegalArgumentException if the Actuator object is null.
     *
     * @param actuator Actuator object
     * @return ActuatorDTO object
     */
    public static ActuatorDTO domainToDTO(Actuator actuator) {
        if (actuator == null) {
            throw new IllegalArgumentException("Invalid actuator, DTO cannot be created");
        }
        return ActuatorDTO.builder()
                .actuatorId(actuator.getId().getID())
                .actuatorName(actuator.getActuatorName().getValue())
                .actuatorTypeID(actuator.getActuatorTypeID().getID())
                .deviceID(actuator.getDeviceID().getID())
                .lowerLimit(actuator.getLowerLimit())
                .upperLimit(actuator.getUpperLimit())
                .precision(actuator.getPrecision())
                .status(actuator.getActuatorStatus().getValue())
                .build();
    }

    /**
     * Converts a list of Actuator domain objects to a list of ActuatorDTO.
     * This method iterates through the provided list of Actuator domain objects, extracts relevant information
     * and creates a new list of ActuatorDTOs with this information.
     *
     * @param actuators The list of Actuator domain objects to be converted.
     * @return A new list of ActuatorDTO Data Transfer Objects representing the converted actuators.
     */
    public static List<ActuatorDTO> domainToDTO(List<Actuator> actuators) {
        List<ActuatorDTO> actuatorDTOList = new ArrayList<>();
        for (Actuator actuator : actuators) {
            ActuatorDTO actuatorDTO = domainToDTO(actuator);
            actuatorDTOList.add(actuatorDTO);
        }
        return actuatorDTOList;
    }

    /**
     * Method to create an ActuatorIDVO object from an ActuatorDTO object.
     * It throws an IllegalArgumentException if the ActuatorDTO object is null.
     *
     * @param actuatorId String
     * @return ActuatorIDVO object
     */
    public static ActuatorIDVO createActuatorIDVO(String actuatorId) {
        if (actuatorId == null) {
            throw new IllegalArgumentException("ActuatorID cannot be null");
        }
        UUID id = UUID.fromString(actuatorId);
        return new ActuatorIDVO(id);
    }

    /**
     * Creates a new DeviceIDVO object from the provided device ID string.
     * Validates the provided device ID string to ensure it is not null, empty or blank.
     * Then, instantiates a new UUID object from the provided device ID string.
     *
     * @param deviceId the device ID string to be converted to a DeviceIDVO object
     * @return a new DeviceIDVO object created from the provided device ID string
     */
    public static DeviceIDVO createDeviceIDVOFromString(String deviceId) {
        if (deviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("DeviceID cannot be null");
        } else {
            UUID id = UUID.fromString(deviceId);
            return new DeviceIDVO(id);
        }
    }
}