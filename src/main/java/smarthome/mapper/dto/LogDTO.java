package smarthome.mapper.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Builder
@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all arguments
@Getter
public class LogDTO extends RepresentationModel<LogDTO> {
        private String logID;
        private String time;
        private String reading;
        private String sensorID;
        private String deviceID;
        private String sensorTypeID;
}
