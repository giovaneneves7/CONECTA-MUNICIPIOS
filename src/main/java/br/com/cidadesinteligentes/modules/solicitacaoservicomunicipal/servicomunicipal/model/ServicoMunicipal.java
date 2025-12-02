package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model;

import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.Fluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a entidade de Serviço Municipal fornecido pela prefeitura.
 * Esta classe armazena informações específicas do serviço, como nome e descrição.
 *
 * <p>Regras de Negócio:
 * <ul>
 * <li>Um serviço municipal pode ter múltiplas solicitações.</li>
 * <li>Um serviço municipal possui um fluxo de trabalho associado.</li>
 * </ul>
 *
 * @author Caio Alves, Andesson Reis
 */

@Entity
@Table(name = "servicos_municipais")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ServicoMunicipal extends SimplePersistenceEntity {

    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "servicoMunicipal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> solicitacoes = new ArrayList<>();

    @OneToOne(mappedBy = "servicoMunicipal")
    private Fluxo fluxo;

}
