package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.repository.ICategoriaManutencaoUrbanaRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public CategoriaResponseDTO createCategoria(CategoriaManutencaoUrbana categoria) {
        // Regra de negócio: Poderia validar se já existe nome aqui
        // if (repository.existsByNome(categoria.getNome())) { ... }

        CategoriaManutencaoUrbana savedEntity = repository.save(categoria);
        return objectMapperUtil.map(savedEntity, CategoriaResponseDTO.class);
    }

    @Override
    public List<CategoriaResponseDTO> findAllCategorias() {
        return objectMapperUtil.mapAll(repository.findAll(), CategoriaResponseDTO.class);
    }

    @Override
    public CategoriaResponseDTO findCategoriaById(Long id) {
        CategoriaManutencaoUrbana entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + id));

        return objectMapperUtil.map(entity, CategoriaResponseDTO.class);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO updateCategoria(Long id, CategoriaManutencaoUrbana categoriaComNovosDados) {
        CategoriaManutencaoUrbana categoriaExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para atualização."));

        // Atualiza os dados
        categoriaExistente.setNome(categoriaComNovosDados.getNome());
        categoriaExistente.setDescricao(categoriaComNovosDados.getDescricao());

        CategoriaManutencaoUrbana updatedEntity = repository.save(categoriaExistente);
        return objectMapperUtil.map(updatedEntity, CategoriaResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteCategoria(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada para exclusão.");
        }
        repository.deleteById(id);
    }
}