package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.request.GestorCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.dto.response.GestorRetornarIdResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.repository.IGestorManutencaoRepository;
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

    // Constante para garantir o retorno correto do tipo
    private static final String TIPO_GESTOR = "GESTOR_MANUTENCAO";

    @Override
    @Transactional
    public GestorResponseDTO save(GestorCriarRequestDTO dto) {
        GestorSolicitacoesManutencaoUrbana entity = objectMapperUtil.map(dto, GestorSolicitacoesManutencaoUrbana.class);
        GestorSolicitacoesManutencaoUrbana savedEntity = repository.save(entity);

        return mapToResponse(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GestorResponseDTO> findAll() {
        List<GestorSolicitacoesManutencaoUrbana> list = repository.findAll();
        return list.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GestorResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public GestorResponseDTO update(GestorAtualizarRequestDTO dto) {
        GestorSolicitacoesManutencaoUrbana gestorExistente = repository.findById(dto.id())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        // Atualizando propriedades herdadas de Profile
        gestorExistente.setImagemUrl(dto.imagemUrl());

        GestorSolicitacoesManutencaoUrbana updatedEntity = repository.save(gestorExistente);
        return mapToResponse(updatedEntity);
    }


    @Override
    @Transactional
    public GestorRetornarIdResponseDTO delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }
        repository.deleteById(id);
        return new GestorRetornarIdResponseDTO(id);
    }

    // Método auxiliar para garantir que o tipo seja preenchido corretamente no Response
    private GestorResponseDTO mapToResponse(GestorSolicitacoesManutencaoUrbana entity) {
        // Assume que GestorResponseDTO tem um construtor ou record que aceita id, imagemUrl e tipo
        // para mapear id e imagemUrl
        GestorResponseDTO response = objectMapperUtil.map(entity, GestorResponseDTO.class);
        return new GestorResponseDTO(
                response.id(),
                response.imagemUrl(),
                TIPO_GESTOR
        );
    }
}