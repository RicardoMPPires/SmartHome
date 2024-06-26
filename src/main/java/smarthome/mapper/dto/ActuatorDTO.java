package smarthome.mapper.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object that contains the actuator ID, name, type and device ID.
 * It is also contains the lower limit, upper limit and precision of the actuator for specific Actuators.
 * It is used to transfer data between the Actuator class and the ActuatorMapper class.
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActuatorDTO extends RepresentationModel<ActuatorDTO>{

    private String actuatorId;
    private String actuatorName;
    private String actuatorTypeID;
    private String deviceID;
    private String lowerLimit;
    private String upperLimit;
    private String precision;
    private String status;
}
