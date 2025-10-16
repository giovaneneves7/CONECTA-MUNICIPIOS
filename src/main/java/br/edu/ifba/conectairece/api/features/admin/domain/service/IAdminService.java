package br.edu.ifba.conectairece.api.features.admin.domain.service;

import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAdminService {
    AdminResponseDTO createAdmin(UUID userId, AdminProfile admin);
    void update(UUID userId, AdminProfile admin);
    void delete(UUID id);
    AdminResponseDTO findById(UUID id);
    List<AdminResponseDTO> findAll(Pageable pageable);
}
