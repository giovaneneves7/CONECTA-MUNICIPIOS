package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;

import java.util.List;

public interface IManutencaoUrbanaService {
    ManutencaoUrbanaResponseDTO save(ManutencaoUrbanaCriarRequestDTO dto);
    List<ManutencaoUrbanaResponseDTO> findAll();
    ManutencaoUrbanaResponseDTO findById(Long id);
    ManutencaoUrbanaResponseDTO update(ManutencaoUrbanaAtualizarRequestDTO dto);
    Long delete(Long id);
}