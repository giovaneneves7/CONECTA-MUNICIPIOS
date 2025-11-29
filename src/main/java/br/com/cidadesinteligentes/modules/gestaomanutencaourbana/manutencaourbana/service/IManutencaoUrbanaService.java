package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;

import java.util.List;

public interface IManutencaoUrbanaService {
    ManutencaoUrbanaResponseDTO create(ManutencaoUrbanaRequestDTO dto);
    List<ManutencaoUrbanaResponseDTO> findAll();
    ManutencaoUrbanaResponseDTO findById(Long id);
    ManutencaoUrbanaResponseDTO update(Long id, ManutencaoUrbanaRequestDTO dto);
    void delete(Long id);
}