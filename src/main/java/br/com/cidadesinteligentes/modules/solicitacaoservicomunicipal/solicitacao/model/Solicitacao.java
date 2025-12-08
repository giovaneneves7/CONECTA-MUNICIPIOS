package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.itemavaliacaogeral.model.ItemAvaliacaoGeral;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.acompanhamento.model.Acompanhamento;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model.Atualizacao;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.ServicoMunicipal;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.enums.SolicitacaoStatus;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.infraestructure.model.StatusHistory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa a entidade de Solicitação, que armazena informações sobre pedidos
 * de serviços
 * realizados pelos cidadãos.
 * <p>
 * Esta classe contém detalhes como número de protocolo, datas de criação e
 * atualização,
 * data estimada de conclusão, tipo e observações.
 * </p>
 * <p>
 * Está vinculada a um {@link ServicoMunicipal}, representando o serviço
 * específico que está sendo solicitado.
 * </p>
 * Hooks de ciclo de vida:
 * <ul>
 * <li>{@link #prePersist()} inicializa as datas de criação e atualização quando
 * a entidade é salva pela primeira vez.</li>
 * <li>{@link #preUpdate()} atualiza a data de modificação sempre que a entidade
 * é alterada.</li>
 * </ul>
 *
 * @author Caio Alves, Giovane Neves, Andesson Reis
 */
@Entity
@Table(name = "solicitacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Solicitacao extends PersistenceEntity {

    /**
     * Número do protocolo gerado para a solicitação (único).
     */
    @Column(name = "numero_protocolo", nullable = false, unique = true)
    private String numeroProtocolo;

    /**
     * Perfil do usuário que realizou a solicitação.
     */
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    /**
     * Data e hora em que a solicitação foi criada.
     */
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    /**
     * Data estimada para a conclusão do serviço solicitado.
     */
    @Column(name = "data_previsao_conclusao")
    private LocalDateTime dataPrevisaoConclusao;

    /**
     * Data e hora da última atualização da solicitação.
     */
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    /**
     * Tipo da solicitação.
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * Observações ou notas adicionais sobre a solicitação.
     */
    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "servico_municipal_id")
    private ServicoMunicipal servicoMunicipal;

    /**
     * Lista de acompanhamentos (histórico de atividades/status) desta solicitação.
     */
    @OneToMany(mappedBy = "solicitacao", orphanRemoval = true)
    private List<Acompanhamento> acompanhamentos = new ArrayList<>();

    /**
     * Lista de atualizações ou interações realizadas na solicitação.
     */
    @OneToMany(mappedBy = "solicitacao", orphanRemoval = true)
    private List<Atualizacao> atualizacoes = new ArrayList<>();

    /**
     * Itens de avaliação geral vinculados a esta solicitação.
     */
    @OneToMany(mappedBy = "solicitacao", orphanRemoval = true)
    private List<ItemAvaliacaoGeral> itensAvaliacaoGeral = new ArrayList<>();

    /**
     * Histórico consolidado de status da solicitação.
     */
    @OneToOne
    @JoinColumn(name = "historico_status_id", unique = true)
    private StatusHistory historicoStatus;

    /**
     * Status atual da solicitação.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SolicitacaoStatus status;

    /**
     * Método executado antes da persistência inicial da entidade.
     * Define as datas de criação e atualização com o momento atual.
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        this.dataCriacao = agora;
        this.dataAtualizacao = agora;
    }

    /**
     * Método executado antes de qualquer atualização na entidade.
     * Atualiza o campo de data de atualização para o momento atual.
     */
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }


}
