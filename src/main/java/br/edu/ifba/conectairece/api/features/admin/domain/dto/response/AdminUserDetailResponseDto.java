package br.edu.ifba.conectairece.api.features.admin.domain.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;

/**
 * DTO representing the detailed view of a user for an administrator,
 * including core details and a list of all associated profiles.
 * @author Caio Alves
 */
public record AdminUserDetailResponseDto(
    @JsonProperty("content") 
    AdminUserContentResponseDTO content,
    @JsonProperty("profiles") 
    List<ProfileResponseDTO> profiles
) {}
