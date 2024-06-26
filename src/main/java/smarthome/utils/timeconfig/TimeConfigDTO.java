package smarthome.utils.timeconfig;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TimeConfigDTO extends RepresentationModel<TimeConfigDTO> {

    private String initialDate;
    private String initialTime;
    private String endDate;
    private String endTime;
    private String deltaMin;
}