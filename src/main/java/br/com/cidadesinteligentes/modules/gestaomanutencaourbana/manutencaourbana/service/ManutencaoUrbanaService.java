package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.repository.ICategoriaManutencaoUrbanaRepository;
// ManutencaoUrbana.java
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.repository.IEnderecoRepository;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.repository.IGestorManutencaoRepository;
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
    // Repositórios necessários para as relações
    private final ICategoriaManutencaoUrbanaRepository categoriaRepository;
    private final IEnderecoRepository enderecoRepository;
    private final IGestorManutencaoRepository gestorRepository;

    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public ManutencaoUrbanaResponseDTO create(ManutencaoUrbanaRequestDTO dto) {
        ManutencaoUrbana entity = objectMapperUtil.map(dto, ManutencaoUrbana.class);

        // 1. Busca e seta a Categoria
        CategoriaManutencaoUrbana categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada ID: " + dto.getCategoriaId()));
        entity.setCategoria(categoria);

        // 2. Busca e seta o Endereço
        Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado ID: " + dto.getEnderecoId()));
        entity.setEndereco(endereco);

        // 3. Busca e seta o Gestor (se informado)
        if (dto.getGestorId() != null) {
            GestorSolicitacoesManutencaoUrbana gestor = gestorRepository.findById(dto.getGestorId())
                    .orElseThrow(() -> new EntityNotFoundException("Gestor não encontrado ID: " + dto.getGestorId()));
            entity.setGestor(gestor);
        }

        ManutencaoUrbana saved = repository.save(entity);
        return objectMapperUtil.map(saved, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    public List<ManutencaoUrbanaResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    public ManutencaoUrbanaResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(entity -> objectMapperUtil.map(entity, ManutencaoUrbanaResponseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada ID: " + id));
    }

    @Override
    @Transactional
    public ManutencaoUrbanaResponseDTO update(Long id, ManutencaoUrbanaRequestDTO dto) {
        ManutencaoUrbana entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada ID: " + id));

        // Atualiza campos simples
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrioridade(dto.getPrioridade());
        entity.setImageURL(dto.getImageURL());
        entity.setProtocolo(dto.getProtocolo());

        // Atualiza Relações se mudaram
        if (!entity.getCategoria().getId().equals(dto.getCategoriaId())) {
            CategoriaManutencaoUrbana novaCategoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            entity.setCategoria(novaCategoria);
        }

        if (!entity.getEndereco().getId().equals(dto.getEnderecoId())) {
            Endereco novoEndereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
            entity.setEndereco(novoEndereco);
        }

        if (dto.getGestorId() != null) {
            // Verifica se mudou ou se não tinha gestor antes
            if (entity.getGestor() == null || !entity.getGestor().getId().equals(dto.getGestorId())) {
                GestorSolicitacoesManutencaoUrbana novoGestor = gestorRepository.findById(dto.getGestorId())
                        .orElseThrow(() -> new EntityNotFoundException("Gestor não encontrado"));
                entity.setGestor(novoGestor);
            }
        }

        ManutencaoUrbana updated = repository.save(entity);
        return objectMapperUtil.map(updated, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Manutenção não encontrada ID: " + id);
        }
        repository.deleteById(id);
    }
}