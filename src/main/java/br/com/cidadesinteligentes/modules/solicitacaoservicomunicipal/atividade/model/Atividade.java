package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atividade.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.AtividadeFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Acompanhamento;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representando uma atividade do fluxo de serviço municipal no sistema.
 * <p>
 * Esta classe é a entidade principal para as atividades do fluxo de serviço municipal,
 * contendo informações básicas como nome, código, ordem e
 * relacionamento com o fluxo associado.
 * </p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@Entity
@Table(name = "Atividades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Atividade extends PersistenceEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo",  nullable = false, unique = true)
    private String codigo;

    @Column(name = "image_url",  nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "atividade")
    private List<AtividadeFluxo> atividadeFluxos = new ArrayList<>();

    @OneToMany(mappedBy = "atividade")
    private List<Acompanhamento> acompanhamentos = new ArrayList<>();

}
