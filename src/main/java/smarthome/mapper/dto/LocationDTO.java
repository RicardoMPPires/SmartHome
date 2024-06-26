package smarthome.mapper.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a Data Transfer Object (DTO) for a location.
 * This DTO is used to transfer location information between different layers of the application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LocationDTO extends RepresentationModel<LocationDTO> {
    private String door;
    private String street;
    private String city;
    private String country;
    private String postalCode;
    private double latitude;
    private double longitude;
}