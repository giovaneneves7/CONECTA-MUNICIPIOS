package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.model;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model.GestorSolicitacoesManutencaoUrbana;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Prioridade;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.enums.Viabilidade;
import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.servicomunicipal.model.MunicipalService;
// ManutencaoUrbana.java
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.endereco.model.Endereco;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "requisicao_servico") // Nome da tabela específica deste filho
@Data
@NoArgsConstructor
@AllArgsConstructor
// Importante: Chama o equals da classe pai para comparar IDs corretamente
@EqualsAndHashCode(callSuper = true)
// Define que a coluna "id" desta tabela é FK da tabela "municipal_services"
@PrimaryKeyJoinColumn(name = "id")
public class ManutencaoUrbana extends MunicipalService {

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaManutencaoUrbana categoria;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "gestor_id") // Pode ser null no início (antes de alguém assumir)
    private GestorSolicitacoesManutencaoUrbana gestor;

    @Column(name = "data_criada", nullable = false, updatable = false)
    private LocalDateTime dataCriada;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING) // Salva como texto ("ALTA", "BAIXA")
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Enumerated(EnumType.STRING) // Salva como texto
    @Column(name = "viabilidade", nullable = false)
    private Viabilidade viabilidade;

    @Column(name = "protocolo", nullable = false)
    private String protocolo;

    // --- MÉTODOS AUXILIARES ---

    @PrePersist
    public void prePersist() {
        this.dataCriada = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        if (this.viabilidade == null) {
            this.viabilidade = Viabilidade.EM_ANALISE; // Valor padrão
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void atualizarStatus() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void adicionarHistorico() {
        // Lógica futura
    }
}