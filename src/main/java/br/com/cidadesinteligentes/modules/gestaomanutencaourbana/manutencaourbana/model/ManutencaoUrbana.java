package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.model;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Viabilidade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "requisicao_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id")
public class ManutencaoUrbana extends MunicipalService {

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaManutencaoUrbana categoria;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "gestor_id")
    private GestorSolicitacoesManutencaoUrbana gestor;

    @Column(name = "data_criada", nullable = false, updatable = false)
    private LocalDateTime dataCriada;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "viabilidade", nullable = false)
    private Viabilidade viabilidade;

    @Column(name = "protocolo", nullable = false)
    private String protocolo;

    @PrePersist
    public void prePersist() {
        this.dataCriada = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        if (this.viabilidade == null) {
            this.viabilidade = Viabilidade.EM_ANALISE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}