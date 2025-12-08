package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.request.EnderecoCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.dto.response.EnderecoRetornarIdResponseDTO;

import java.util.List;

public interface IEnderecoService {

    /**
     * Cria um novo endereço.
     * @param dto Dados do novo endereço.
     * @return DTO com os dados do endereço salvo.
     */
    EnderecoResponseDTO save(EnderecoCriarRequestDTO dto);

    /**
     * Lista todos os endereços cadastrados.
     * @return Lista de DTOs de resposta.
     */
    List<EnderecoResponseDTO> findAll();

    /**
     * Busca um endereço pelo seu ID.
     * @param id Identificador único do endereço.
     * @return DTO do endereço encontrado.
     */
    EnderecoResponseDTO findById(Long id);

    /**
     * Atualiza os dados de um endereço existente.
     * @param dto Dados para atualização (contém o ID).
     * @return DTO do endereço atualizado.
     */
    EnderecoResponseDTO update(EnderecoAtualizarRequestDTO dto);

    /**
     * Remove um endereço pelo ID.
     * @param id Identificador do endereço a ser removido.
     * @return O ID do endereço que foi excluído.
     */
    public EnderecoRetornarIdResponseDTO delete(Long id);
}