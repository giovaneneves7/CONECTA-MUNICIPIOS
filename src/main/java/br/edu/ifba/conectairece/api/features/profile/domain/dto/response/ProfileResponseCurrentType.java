package br.edu.ifba.conectairece.api.features.profile.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) for sending active profile type.
 *
 * @author Jorge Roberto
 */
public record ProfileResponseCurrentType (
        @JsonProperty("active_type")
        String activeType,

        @JsonProperty("role")
        String role
) {}
