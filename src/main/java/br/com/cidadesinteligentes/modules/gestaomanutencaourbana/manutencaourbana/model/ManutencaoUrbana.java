package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.manutencaourbana.model;


import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "requisicao_servico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoUrbana extends PersistenceEntity {

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_criada", nullable = false)
    private LocalDateTime dataCriada;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(name = "status", nullable = false)
    private StatusRequisicao status;

    @Column(name = "image_url", nullable = false)
    private String imageURL;

    @Column(name = "viabilidade", nullable = false)
    private Viabilidade viabilidade;

    @Column(name = "protocolo", nullable = false)
    private String protocolo;

    // MÉTODOS EXIGIDOS PELO DIAGRAMA

    public void atualizarStatus() {
        // Lógica para atualizar o status
    }

    public void adicionarHistorico() {
        // Lógica para adicionar ao histórico
    }
}