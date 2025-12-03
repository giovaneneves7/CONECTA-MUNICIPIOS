package br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import java.util.List;
import java.util.UUID;

/**
 * Interface que define os contratos de serviço para o gerenciamento de Serviços.
 * Responsável por declarar as operações de negócio disponíveis para o controlador.
 *
 * <p>
 * A camada de serviço deve sempre trabalhar com ENTIDADES,
 * enquanto o controller é responsável pela conversão entidade ↔ DTO.
 * </p>
 *
 * @author Juan Teles Dias
 * @since 1.0
 */
public interface IServicoService {

    /**
     * Cria e persiste um novo serviço no sistema.
     *
     * @param servico a entidade {@link Servico} já mapeada (contendo nome e ID da unidade).
     * @return a entidade persistida e completa.
     */
    Servico save(Servico servico);

    /**
     * Retorna todos os serviços cadastrados.
     *
     * @return lista de entidades {@link Servico}.
     */
    List<Servico> findAll();

    /**
     * Busca um serviço específico pelo seu identificador único.
     *
     * @param id o UUID do serviço.
     * @return a entidade encontrada.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException se não encontrado.
     */
    Servico findById(UUID id);

    /**
     * Atualiza os dados de um serviço existente.
     *
     * @param id      o UUID do serviço a ser atualizado.
     * @param servico entidade contendo os dados atualizados (nome, unidade).
     * @return a entidade atualizada e persistida.
     */
    Servico update(UUID id, Servico servico);

    /**
     * Remove um serviço do sistema.
     *
     * @param id o UUID do serviço a ser excluído.
     * @return a entidade que foi excluída (para fins de confirmação).
     */
    Servico delete(UUID id);
}