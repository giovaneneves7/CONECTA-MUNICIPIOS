package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.service;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;

import java.util.List;
import java.util.UUID;

/**
 * Interface que define os contratos de serviço para o gerenciamento de Profissionais de Saúde.
 * <p>
 * Responsável por declarar as operações de negócio disponíveis, garantindo que o controlador
 * possa delegar a lógica complexa (validação, persistência, verificação de integridade)
 * sem conhecer os detalhes da implementação.
 * </p>
 *
 * @author Juan Teles Dias
 * @since 1.0
 */
public interface IProfissionalSaudeService {

    /**
     * Salva um novo profissional de saúde no sistema.
     * <p>
     * O metodo espera receber uma entidade com os dados cadastrais preenchidos e os relacionamentos
     * (Unidade, Tipos, Serviços) contendo objetos com apenas o ID definido (Proxy).
     * O serviço se encarregará de buscar as entidades completas e validar as regras de negócio.
     * </p>
     *
     * @param entity A entidade {@link ProfissionalSaude} parcialmente populada.
     * @return A entidade persistida e completa.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException Se houver violação de regras (ex: CRM duplicado).
     */
    ProfissionalSaude save(ProfissionalSaude entity);

    /**
     * Retorna a lista de todos os profissionais de saúde cadastrados.
     *
     * @return Lista de entidades {@link ProfissionalSaude}.
     */
    List<ProfissionalSaude> findAll();

    /**
     * Busca um profissional específico pelo seu identificador único.
     *
     * @param id O UUID do profissional.
     * @return A entidade encontrada.
     * @throws br.com.cidadesinteligentes.infraestructure.exception.BusinessException Se o profissional não for encontrado.
     */
    ProfissionalSaude findById(UUID id);

    /**
     * Atualiza os dados de um profissional existente.
     * <p>
     * Verifica se o novo CRM (se alterado) já não pertence a outro profissional e
     * atualiza os vínculos (Unidade, Serviços, Tipos).
     * </p>
     *
     * @param id     O UUID do profissional a ser atualizado.
     * @param entity A entidade contendo os novos dados (transportada do controller).
     * @return A entidade atualizada.
     */
    ProfissionalSaude update(UUID id, ProfissionalSaude entity);

    /**
     * Remove um profissional do sistema.
     * <p>
     * A exclusão só é permitida se não houver registros dependentes críticos (como agendamentos)
     * vinculados a este profissional.
     * </p>
     *
     * @param id O UUID do profissional a ser excluído.
     * @return A entidade que foi excluída (para fins de confirmação).
     */
    ProfissionalSaude delete(UUID id);
}