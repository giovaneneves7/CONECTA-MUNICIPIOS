package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service;

// Import baseado na imagem da estrutura de pastas enviada
import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaCreateRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.request.CategoriaUpdateRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
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
    public CategoriaResponseDTO save(CategoriaCreateRequestDTO dto) {
        // Validação de regra de negócio: Nome único
        if (repository.existsByNome(dto.nome())) {
            throw new BusinessException("Já existe uma categoria com o nome informado.");
        }

        // Converte o Record (DTO) para Entidade
        CategoriaManutencaoUrbana entity = objectMapperUtil.map(dto, CategoriaManutencaoUrbana.class);

        CategoriaManutencaoUrbana savedEntity = repository.save(entity);
        return objectMapperUtil.map(savedEntity, CategoriaResponseDTO.class);
    }

    @Override
    public List<CategoriaResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), CategoriaResponseDTO.class);
    }

    @Override
    public CategoriaResponseDTO findById(Long id) {
        // Usa o .map() seguido de .orElseThrow() conforme sugerido no print
        return repository.findById(id)
                .map(entity -> objectMapperUtil.map(entity, CategoriaResponseDTO.class))
                .orElseThrow(() -> new BusinessException("Categoria não encontrada com o ID: " + id));
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(CategoriaUpdateRequestDTO dto) {
        // Busca a entidade ou lança BusinessException usando o ID que vem dentro do DTO de update
        CategoriaManutencaoUrbana categoriaExistente = repository.findById(dto.id())
                .orElseThrow(() -> new BusinessException("Categoria não encontrada para atualização com o ID: " + dto.id()));

        // Atualiza os dados manualmente para garantir que o ID não mude
        categoriaExistente.setNome(dto.nome());
        categoriaExistente.setDescricao(dto.descricao());

        CategoriaManutencaoUrbana updatedEntity = repository.save(categoriaExistente);
        return objectMapperUtil.map(updatedEntity, CategoriaResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Categoria não encontrada para exclusão com o ID: " + id);
        }
        repository.deleteById(id);
    }
}