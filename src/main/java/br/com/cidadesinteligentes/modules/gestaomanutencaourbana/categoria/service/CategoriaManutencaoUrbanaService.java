package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaRetornarIdResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.repository.ICategoriaManutencaoUrbanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaManutencaoUrbanaService implements ICategoriaManutencaoUrbanaService {

    private final ICategoriaManutencaoUrbanaRepository repository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public CategoriaResponseDTO save(CategoriaCriarRequestDTO dto) {
        if (repository.existsByNome(dto.nome())) {
            throw new BusinessException(BusinessExceptionMessage.ATTRIBUTE_VALUE_ALREADY_EXISTS.getAttributeValueAlreadyExistsMessage("Nome"));
        }

        CategoriaManutencaoUrbana entity = objectMapperUtil.map(dto, CategoriaManutencaoUrbana.class);
        CategoriaManutencaoUrbana savedEntity = repository.save(entity);

        return objectMapperUtil.map(savedEntity, CategoriaResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), CategoriaResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(entity -> objectMapperUtil.map(entity, CategoriaResponseDTO.class))
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(CategoriaAtualizarRequestDTO dto) {
        CategoriaManutencaoUrbana categoriaExistente = repository.findById(dto.id())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        categoriaExistente.setNome(dto.nome());
        categoriaExistente.setDescricao(dto.descricao());

        CategoriaManutencaoUrbana updatedEntity = repository.save(categoriaExistente);
        return objectMapperUtil.map(updatedEntity, CategoriaResponseDTO.class);
    }

    @Override
    @Transactional
    public CategoriaRetornarIdResponseDTO delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }
        repository.deleteById(id);
        return new CategoriaRetornarIdResponseDTO(id);
    }
}