package br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.tipoprofissionalsaude.model.TipoProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório para gerenciamento de dados de Tipos de Profissional.
 *
 * @author Juan Teles Dias
 */
@Repository
public interface ITipoProfissionalSaudeRepository extends JpaRepository<TipoProfissionalSaude, UUID> {

    /**
     * Verifica se já existe um tipo cadastrado com o mesmo nome (ignorando maiúsculas/minúsculas).
     *
     * @param nome o nome a ser verificado.
     * @return true se existir.
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Verifica se existe outro tipo com o mesmo nome, exceto o do ID informado.
     * Usado na atualização.
     */
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, UUID id);
}