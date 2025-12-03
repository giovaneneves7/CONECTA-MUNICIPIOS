package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.repository.ICategoriaManutencaoUrbanaRepository;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.repository.IEnderecoRepository;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.repository.IGestorManutencaoRepository;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.model.ManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.repository.IManutencaoUrbanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManutencaoUrbanaService implements IManutencaoUrbanaService {

    private final IManutencaoUrbanaRepository repository;
    private final ICategoriaManutencaoUrbanaRepository categoriaRepository;
    private final IEnderecoRepository enderecoRepository;
    private final IGestorManutencaoRepository gestorRepository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public ManutencaoUrbanaResponseDTO save(ManutencaoUrbanaCriarRequestDTO dto) {
        ManutencaoUrbana entity = new ManutencaoUrbana();

        // Mapeamento manual dos campos simples para garantir a tradução DTO(PT) -> Entity(EN/Inherited)
        entity.setName(dto.nome());
        entity.setDescription(dto.descricao());
        entity.setPrioridade(dto.prioridade());
        entity.setImageURL(dto.urlImagem());
        entity.setProtocolo(dto.protocolo());

        // Relacionamentos
        CategoriaManutencaoUrbana categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setCategoria(categoria);

        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
        entity.setEndereco(endereco);

        if (dto.gestorId() != null) {
            GestorSolicitacoesManutencaoUrbana gestor = gestorRepository.findById(dto.gestorId())
                    .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
            entity.setGestor(gestor);
        }

        ManutencaoUrbana saved = repository.save(entity);
        return objectMapperUtil.map(saved, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManutencaoUrbanaResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ManutencaoUrbanaResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(entity -> objectMapperUtil.map(entity, ManutencaoUrbanaResponseDTO.class))
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public ManutencaoUrbanaResponseDTO update(ManutencaoUrbanaAtualizarRequestDTO dto) {
        ManutencaoUrbana entity = repository.findById(dto.id())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        // Atualização dos campos simples
        entity.setName(dto.nome());
        entity.setDescription(dto.descricao());
        entity.setPrioridade(dto.prioridade());
        entity.setViabilidade(dto.viabilidade());
        entity.setImageURL(dto.urlImagem());
        entity.setProtocolo(dto.protocolo());

        // Atualização de relacionamentos apenas se o ID mudou
        if (!entity.getCategoria().getId().equals(dto.categoriaId())) {
            CategoriaManutencaoUrbana novaCategoria = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
            entity.setCategoria(novaCategoria);
        }

        if (!entity.getEndereco().getId().equals(dto.enderecoId())) {
            Endereco novoEndereco = enderecoRepository.findById(dto.enderecoId())
                    .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
            entity.setEndereco(novoEndereco);
        }

        if (dto.gestorId() != null) {
            if (entity.getGestor() == null || !entity.getGestor().getId().equals(dto.gestorId())) {
                GestorSolicitacoesManutencaoUrbana novoGestor = gestorRepository.findById(dto.gestorId())
                        .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
                entity.setGestor(novoGestor);
            }
        } else {
            // Se o DTO vier com gestor null, removemos o gestor da entidade?
            // Geralmente sim, mas depende da regra de negócio. Aqui assumo que sim.
            entity.setGestor(null);
        }

        ManutencaoUrbana updated = repository.save(entity);
        return objectMapperUtil.map(updated, ManutencaoUrbanaResponseDTO.class);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage());
        }
        repository.deleteById(id);
        return id;
    }
}