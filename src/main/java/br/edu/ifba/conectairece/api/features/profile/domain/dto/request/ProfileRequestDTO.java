package br.edu.ifba.conectairece.api.features.profile.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Data Transfer Object (DTO) for receiving profile data in API requests.
 * Contains validation annotations to ensure data integrity.
 *
 * @author Jorge Roberto
 */
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProfileRequestDTO {
    @JsonProperty(value = "type")
    @NotNull(message = "Type is mandatory.")
    @NotBlank(message = "Type cannot be blank.")
    private String type;

    @JsonProperty(value = "imageUrl")
    private String imageUrl;
}
