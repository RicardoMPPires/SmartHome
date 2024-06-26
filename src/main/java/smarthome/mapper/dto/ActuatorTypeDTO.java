package smarthome.mapper.dto;


import org.springframework.hateoas.RepresentationModel;
import lombok.*;

/**
 * Represents a Data Transfer Object (DTO) for an actuator type.
 * This DTO is used to transfer actuator type information between different layers of the application.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActuatorTypeDTO extends RepresentationModel<ActuatorTypeDTO> {

    private String actuatorTypeID;
}