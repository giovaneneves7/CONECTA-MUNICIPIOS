package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;

import java.util.List;
import java.util.UUID;

/**
 * Interface que define os contratos de serviço para o gerenciamento de Unidades de Atendimento.
 * Responsável por declarar as operações de negócio disponíveis para o controlador.
 *
 * <p>
 * A camada de serviço trabalha estritamente com a entidade {@link UnidadeAtendimento},
 * delegando ao controlador a responsabilidade de conversão de DTOs.
 * </p>
 *
 * @author Juan Teles Dias
 * @since 1.0
 */
public interface IUnidadeAtendimentoService {

    /**
     * Valida e persiste uma nova unidade de atendimento no sistema.
     *
     * @param unidadeAtendimento a entidade {@link UnidadeAtendimento} já instanciada e populada.
     * @return a entidade persistida.
     */
    UnidadeAtendimento save(UnidadeAtendimento unidadeAtendimento);

    /**
     * Retorna todas as unidades de atendimento cadastradas no sistema.
     *
     * @return lista de entidades {@link UnidadeAtendimento}.
     */
    List<UnidadeAtendimento> findAll();

    /**
     * Busca uma unidade de atendimento específica pelo seu identificador único.
     *
     * @param id o UUID da unidade.
     * @return a entidade encontrada.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException se a unidade não for encontrada.
     */
    UnidadeAtendimento findById(UUID id);

    /**
     * Atualiza os dados cadastrais de uma unidade de atendimento existente.
     *
     * @param id                o UUID da unidade a ser atualizada.
     * @param dadosAtualizados  a entidade contendo os novos dados (transportada do controller).
     * @return a entidade atualizada e persistida.
     */
    UnidadeAtendimento update(UUID id, UnidadeAtendimento dadosAtualizados);

    /**
     * Remove uma unidade de atendimento do sistema.
     *
     * @param id o UUID da unidade a ser excluída.
     * @return a entidade que foi excluída.
     */
    UnidadeAtendimento delete(UUID id);
}