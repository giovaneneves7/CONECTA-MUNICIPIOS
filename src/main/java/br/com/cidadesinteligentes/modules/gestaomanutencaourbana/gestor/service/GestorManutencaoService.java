package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.service;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.repository.IGestorManutencaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GestorManutencaoService implements IGestorManutencaoService {

    private final IGestorManutencaoRepository repository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public GestorResponseDTO createGestor(GestorRequestDTO dto) {
        GestorSolicitacoesManutencaoUrbana gestor = objectMapperUtil.map(dto, GestorSolicitacoesManutencaoUrbana.class);
        GestorSolicitacoesManutencaoUrbana saved = repository.save(gestor);
        return mapToResponse(saved);
    }

    @Override
    public List<GestorResponseDTO> findAllGestores() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public GestorResponseDTO findGestorById(UUID id) {
        GestorSolicitacoesManutencaoUrbana gestor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gestor não encontrado com ID: " + id));
        return mapToResponse(gestor);
    }

    @Override
    @Transactional
    public GestorResponseDTO updateGestor(UUID id, GestorRequestDTO dto) {
        GestorSolicitacoesManutencaoUrbana gestor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gestor não encontrado com ID: " + id));

        gestor.setImageUrl(dto.getImagemUrl());

        GestorSolicitacoesManutencaoUrbana updated = repository.save(gestor);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteGestor(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Gestor não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    // Método auxiliar para mapear manualmente o tipo, caso o mapper automático não pegue o @Discriminator
    private GestorResponseDTO mapToResponse(GestorSolicitacoesManutencaoUrbana entity) {
        GestorResponseDTO response = objectMapperUtil.map(entity, GestorResponseDTO.class);
        response.setTipo("GESTOR_MANUTENCAO");
        return response;
    }
}