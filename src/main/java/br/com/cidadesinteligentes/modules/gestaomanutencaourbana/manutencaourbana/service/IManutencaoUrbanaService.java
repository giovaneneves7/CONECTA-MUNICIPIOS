package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaAtualizarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.request.ManutencaoUrbanaCriarRequestDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.dto.response.ManutencaoUrbanaRetornarIdResponseDTO;

import java.util.List;

/**
 * Interface de serviço para gerenciar as operações de CRUD (Criação, Leitura, Atualização e Exclusão)
 * das solicitações de Manutenção Urbana.
 */
public interface IManutencaoUrbanaService {

    /**
     * Salva uma nova solicitação de manutenção urbana no sistema.
     *
     * @param dto DTO com os dados da solicitação a ser criada.
     * @return O DTO de resposta da solicitação recém-criada.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException Se houver erro de regra de negócio, como IDs de relacionamento não encontrados.
     */
    ManutencaoUrbanaResponseDTO save(ManutencaoUrbanaCriarRequestDTO dto);

    /**
     * Busca e retorna todas as solicitações de manutenção urbana cadastradas.
     *
     * @return Uma lista de DTOs de resposta contendo todas as solicitações.
     */
    List<ManutencaoUrbanaResponseDTO> findAll();

    /**
     * Busca uma solicitação de manutenção urbana pelo seu ID.
     *
     * @param id O ID (Long) da solicitação.
     * @return O DTO de resposta da solicitação encontrada.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException Se a solicitação não for encontrada.
     */
    ManutencaoUrbanaResponseDTO findById(Long id);

    /**
     * Atualiza uma solicitação de manutenção urbana existente.
     *
     * @param dto DTO com os dados atualizados da solicitação. Deve incluir o ID.
     * @return O DTO de resposta da solicitação atualizada.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException Se a solicitação não for encontrada ou se IDs de relacionamento forem inválidos.
     */
    ManutencaoUrbanaResponseDTO update(ManutencaoUrbanaAtualizarRequestDTO dto);

    /**
     * Exclui uma solicitação de manutenção urbana pelo seu ID.
     *
     * @param id O ID (Long) da solicitação a ser excluída.
     * @return Um DTO contendo o ID da solicitação que foi excluída com sucesso.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException Se a solicitação não for encontrada.
     */
    ManutencaoUrbanaRetornarIdResponseDTO delete(Long id);
}