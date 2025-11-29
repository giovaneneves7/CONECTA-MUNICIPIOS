package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.model.ManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.repository.IManutencaoUrbanaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManutencaoUrbanaService implements IManutencaoUrbanaService {

    private final IManutencaoUrbanaRepository repository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public ManutencaoUrbanaResponseDTO create(ManutencaoUrbanaRequestDTO dto) {
        // Mapeia o DTO para a entidade (incluindo campos herdados como 'name')
        ManutencaoUrbana entity = objectMapperUtil.map(dto, ManutencaoUrbana.class);

        // Salva (O Hibernate faz insert na tabela 'municipal_services' E 'requisicao_servico')
        ManutencaoUrbana saved = repository.save(entity);

        return objectMapperUtil.map(saved, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    public List<ManutencaoUrbanaResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    public ManutencaoUrbanaResponseDTO findById(Long id) {
        ManutencaoUrbana entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção Urbana não encontrada com ID: " + id));
        return objectMapperUtil.map(entity, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    @Transactional
    public ManutencaoUrbanaResponseDTO update(Long id, ManutencaoUrbanaRequestDTO dto) {
        ManutencaoUrbana entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção Urbana não encontrada para atualização."));

        // Atualiza campos herdados (MunicipalService)
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        // Atualiza campos específicos (ManutencaoUrbana)
        entity.setPrioridade(dto.getPrioridade());
        entity.setImageURL(dto.getImageURL());
        entity.setProtocolo(dto.getProtocolo());
        ManutencaoUrbana updated = repository.save(entity);
        return objectMapperUtil.map(updated, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Manutenção Urbana não encontrada para exclusão.");
        }
        repository.deleteById(id);
    }
}