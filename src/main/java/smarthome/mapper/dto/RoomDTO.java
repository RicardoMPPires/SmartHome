package smarthome.mapper.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a Data Transfer Object (DTO) for a room.
 * This DTO is used to transfer room information between different layers of the application.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RoomDTO extends RepresentationModel<RoomDTO> {
    private String id;
    private String roomName;
    private int floor;
    private double roomHeight;
    private double roomLength;
    private double roomWidth;
    private String houseID;
}
