package br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.profissionalsaude.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório para gerenciamento de dados de Profissionais de Saúde.
 *
 * @author Juan Teles Dias
 */
@Repository
public interface IProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, UUID> {

    /**
     * Verifica se já existe um profissional com o CRM informado.
     *
     * @param crm o número do CRM.
     * @return true se existir.
     */
    boolean existsByCrm(String crm);

    /**
     * Verifica se existe outro profissional com o CRM informado, exceto o ID passado.
     * Usado na atualização.
     */
    boolean existsByCrmAndIdNot(String crm, UUID id);

    // Futuro: boolean existsByCpf(String cpf); // herdado de Profile, se necessário
}