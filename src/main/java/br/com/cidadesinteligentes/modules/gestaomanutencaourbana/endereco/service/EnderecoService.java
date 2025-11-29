package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.service;

import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.repository.IEnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public EnderecoResponseDTO create(EnderecoRequestDTO dto) {
        Endereco endereco = objectMapperUtil.map(dto, Endereco.class);
        Endereco saved = repository.save(endereco);
        return objectMapperUtil.map(saved, EnderecoResponseDTO.class);
    }

    @Override
    public List<EnderecoResponseDTO> findAll() {
        return objectMapperUtil.mapAll(repository.findAll(), EnderecoResponseDTO.class);
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));
        return objectMapperUtil.map(endereco, EnderecoResponseDTO.class);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoRequestDTO dto) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com ID: " + id));

        // Atualizando os campos
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        endereco.setCep(dto.getCep());
        endereco.setLatitude(dto.getLatitude());
        endereco.setLongitude(dto.getLongitude());

        Endereco updated = repository.save(endereco);
        return objectMapperUtil.map(updated, EnderecoResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Endereço não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}