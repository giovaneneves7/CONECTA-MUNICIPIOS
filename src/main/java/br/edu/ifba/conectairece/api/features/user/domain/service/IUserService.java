package br.edu.ifba.conectairece.api.features.user.domain.service;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.response.UserDataResponseDTO;
import br.edu.ifba.conectairece.api.features.profile.domain.dto.response.ProfileResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserDataResponseDTO getUserById(final UUID id);
    List<ProfileResponseDTO> getUserProfiles(final UUID id);
    ProfileResponseDTO findActiveProfileByUserId(UUID id);
}
