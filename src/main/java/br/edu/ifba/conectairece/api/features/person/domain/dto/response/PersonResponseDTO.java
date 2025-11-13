package br.edu.ifba.conectairece.api.features.person.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for sending person data in API responses.
 * Contains basic person information without sensitive data.
 *
 * @author Jorge Roberto
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonResponseDTO {

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("birthDate")
    private LocalDate birthDate;

    @JsonProperty("gender")
    private String gender;
}
