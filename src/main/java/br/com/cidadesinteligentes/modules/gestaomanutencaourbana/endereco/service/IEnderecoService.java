package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;

import java.util.List;

public interface IEnderecoService {
    EnderecoResponseDTO create(EnderecoRequestDTO dto);
    List<EnderecoResponseDTO> findAll();
    EnderecoResponseDTO findById(Long id);
    EnderecoResponseDTO update(Long id, EnderecoRequestDTO dto);
    void delete(Long id);
}