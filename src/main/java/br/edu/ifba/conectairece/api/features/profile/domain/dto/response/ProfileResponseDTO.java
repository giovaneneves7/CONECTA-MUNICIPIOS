package br.edu.ifba.conectairece.api.features.profile.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for sending function data in API responses.
 *
 * @author Jorge Roberto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileResponseDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("imageUrl")
    private String imageUrl;
}
