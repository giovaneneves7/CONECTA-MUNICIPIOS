package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.service;

import br.com.cidadesinteligentes.infraestructure.exception.BusinessException;
import br.com.cidadesinteligentes.infraestructure.exception.BusinessExceptionMessage;
import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.repository.IEnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService implements IEnderecoService {

    private final IEnderecoRepository repository;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @Transactional
    public EnderecoResponseDTO save(EnderecoCriarRequestDTO dto) {
        Endereco entity = objectMapperUtil.map(dto, Endereco.class);
        Endereco savedEntity = repository.save(entity);

        return objectMapperUtil.map(savedEntity, EnderecoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), EnderecoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EnderecoResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(entity -> objectMapperUtil.map(entity, EnderecoResponseDTO.class))
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(EnderecoAtualizarRequestDTO dto) {
        Endereco enderecoExistente = repository.findById(dto.id())
                .orElseThrow(() -> new BusinessException(BusinessExceptionMessage.NOT_FOUND.getMessage()));

        // Atualização manual para garantir consistência dos dados parciais
        enderecoExistente.setRua(dto.rua());
        enderecoExistente.setNumero(dto.numero());
        enderecoExistente.setBairro(dto.bairro());
        enderecoExistente.setCidade(dto.cidade());
        enderecoExistente.setEstado(dto.estado());
        enderecoExistente.setCep(dto.cep());
        enderecoExistente.setLatitude(dto.latitude());
        enderecoExistente.setLongitude(dto.longitude());

        Endereco updatedEntity = repository.save(enderecoExistente);
        return objectMapperUtil.map(updatedEntity, EnderecoResponseDTO.class);
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