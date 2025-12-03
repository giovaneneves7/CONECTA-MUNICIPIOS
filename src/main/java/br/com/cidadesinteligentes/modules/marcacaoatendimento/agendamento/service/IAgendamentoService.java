package br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.model.Agendamento;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.agendamento.enums.StatusAgendamento;

import java.util.List;
import java.util.UUID;

/**
 * Interface que define os contratos de serviço para o gerenciamento de Agendamentos.
 * Responsável por declarar as operações de negócio disponíveis para o controlador.
 *
 * Camada de serviço deve sempre trabalhar com ENTIDADES,
 * enquanto o controller converte entidade ↔ DTO.
 *
 * @author Juan
 */
public interface IAgendamentoService {

    /**
     * Cria um novo agendamento.
     *
     * @param entity entidade Agendamento já mapeada pelo Controller.
     * @return entidade persistida.
     */
    Agendamento save(Agendamento entity);

    /**
     * Retorna todos os agendamentos.
     *
     * @return lista de entidades.
     */
    List<Agendamento> findAll();

    /**
     * Busca um agendamento específico pelo ID.
     *
     * @param id o UUID do agendamento.
     * @return a entidade encontrada.
     */
    Agendamento findById(UUID id);

    /**
     * Atualiza um agendamento existente.
     *
     * @param id     o UUID do agendamento.
     * @param entity dados atualizados (já convertidos pelo controller).
     * @return entidade atualizada.
     */
    Agendamento update(UUID id, Agendamento entity);

    /**
     * Atualiza apenas o status do agendamento.
     *
     * @param id     o UUID.
     * @param status novo status.
     * @return entidade atualizada.
     */
    Agendamento updateStatus(UUID id, StatusAgendamento status);

    /**
     * Cancela (exclusão lógica) um agendamento.
     *
     * @param id o UUID.
     * @return entidade cancelada.
     */
    Agendamento delete(UUID id);
}
