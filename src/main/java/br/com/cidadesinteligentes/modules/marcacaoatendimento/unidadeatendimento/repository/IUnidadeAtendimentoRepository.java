package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.repository;

import br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model.UnidadeAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositório para gerenciamento de dados de Unidades de Atendimento.
 * Estende JpaRepository para fornecer operações de CRUD padrão.
 *
 * @author Juan Teles Dias
 */
@Repository
public interface IUnidadeAtendimentoRepository extends JpaRepository<UnidadeAtendimento, UUID> {

    /**
     * Verifica se existe alguma unidade com o código CNES informado.
     * Utilizado na validação de criação (SAVE).
     *
     * @param codigoCNES o código a ser verificado.
     * @return true se existir, false caso contrário.
     */
    boolean existsByCodigoCNES(String codigoCNES);

    /**
     * Verifica se existe alguma unidade com o código CNES informado, EXCETO a unidade com o ID passado.
     * Utilizado na validação de atualização (UPDATE) para garantir que não estamos
     * usando um CNES que pertence a outra unidade, mas permitindo manter o próprio CNES.
     *
     * @param codigoCNES o código a ser verificado.
     * @param id o ID da unidade atual que deve ser ignorada na busca.
     * @return true se existir outra unidade com este CNES.
     */
    boolean existsByCodigoCNESAndIdNot(String codigoCNES, UUID id);
}