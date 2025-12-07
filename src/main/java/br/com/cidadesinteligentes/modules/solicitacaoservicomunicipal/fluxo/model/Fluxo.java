package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
 * Classe representando um fluxo de serviço municipal no sistema.
 * <p>
 * Esta classe é a principal entidade para os fluxos de serviços municipais,
 * contendo informações básicas, como código, nome, descrição e
 * relacionamento com as atividades associadas.
 * </p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@Entity
@Table(name = "fluxos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Fluxo extends PersistenceEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToOne
    @JoinColumn(name = "servico_municipal_id", nullable = false, unique = true)
    private ServicoMunicipal servicoMunicipal;

    @OneToMany(mappedBy = "fluxo")
    private List<AtividadeFluxo> atividadeFluxos = new ArrayList<>();

}
