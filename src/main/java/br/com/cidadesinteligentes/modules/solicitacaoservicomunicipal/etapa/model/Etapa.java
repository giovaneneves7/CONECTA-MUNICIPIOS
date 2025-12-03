package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.etapa.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.fluxo.model.EtapaFluxo;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Monitoring;
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
 * Class representing a municipal service flow step in the system.
 * <p>
 * This class is the main entity for municipal service's flow steps,
 * containing basic information such as name, code, order and
 * relationship with associated flow.
 * </p>
 *
 * @author Giovane Neves, Andesson Reis
 */
@Entity
@Table(name = "etapas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Etapa extends PersistenceEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "code",  nullable = false, unique = true)
    private String code;

    @Column(name = "image_url",  nullable = false)
    private String imageUrl;


    @OneToMany(mappedBy = "etapa")
    private List<EtapaFluxo> etapaFluxos = new ArrayList<>();

    @OneToMany(mappedBy = "etapa")
    private List<Monitoring> monitoramentos = new ArrayList<>();

}
