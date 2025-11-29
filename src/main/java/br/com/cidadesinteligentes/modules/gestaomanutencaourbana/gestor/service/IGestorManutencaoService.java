package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IGestorManutencaoService {
    GestorResponseDTO createGestor(GestorRequestDTO dto);
    List<GestorResponseDTO> findAllGestores();
    GestorResponseDTO findGestorById(UUID id);
    GestorResponseDTO updateGestor(UUID id, GestorRequestDTO dto);
    void deleteGestor(UUID id);
}