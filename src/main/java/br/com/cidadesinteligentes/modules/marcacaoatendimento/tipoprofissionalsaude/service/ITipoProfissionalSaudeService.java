package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;

import java.util.List;
import java.util.UUID;

/**
 * Interface que define os contratos de serviço para o gerenciamento de Tipos de Profissional.
 *
 * <p>
 * Segue o padrão onde a camada de serviço opera estritamente com Entidades,
 * aplicando regras de negócio e validações.
 * </p>
 *
 * @author Juan Teles Dias
 * @since 1.0
 */
public interface ITipoProfissionalSaudeService {

    /**
     * Salva um novo tipo de profissional.
     *
     * @param entity a entidade populada.
     * @return a entidade persistida.
     */
    TipoProfissionalSaude save(TipoProfissionalSaude entity);

    /**
     * Retorna todos os tipos cadastrados.
     *
     * @return lista de entidades.
     */
    List<TipoProfissionalSaude> findAll();

    /**
     * Busca um tipo pelo ID.
     *
     * @param id o UUID.
     * @return a entidade encontrada.
     */
    TipoProfissionalSaude findById(UUID id);

    /**
     * Atualiza um tipo existente.
     *
     * @param id o UUID do registro a ser atualizado.
     * @param entity a entidade com os novos dados.
     * @return a entidade atualizada.
     */
    TipoProfissionalSaude update(UUID id, TipoProfissionalSaude entity);

    /**
     * Remove um tipo do sistema.
     *
     * @param id o UUID do registro a ser excluído.
     * @return a entidade excluída.
     */
    TipoProfissionalSaude delete(UUID id);
}