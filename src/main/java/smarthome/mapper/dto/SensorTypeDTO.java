package smarthome.mapper.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a Data Transfer Object (DTO) for a sensor type.
 * This DTO is used to transfer sensor type information between different layers of the application.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SensorTypeDTO extends RepresentationModel<SensorTypeDTO> {

    private String sensorTypeID;
    private String unit;
}