package smarthome.mapper.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonPropertyOrder({"houseID"})
public class HouseDTO extends RepresentationModel<HouseDTO> {
    private String houseID;
    private String door;
    private String street;
    private String city;
    private String country;
    private String postalCode;
    private double latitude;
    private double longitude;
}
