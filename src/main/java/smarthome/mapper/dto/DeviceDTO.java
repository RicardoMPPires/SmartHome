package smarthome.mapper.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private String deviceID;
    private String deviceName;
    private String deviceModel;
    private String deviceStatus;
    private String roomID;
}