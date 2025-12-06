package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model;

import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Solicitacao;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Representa uma entidade de Atualização que armazena informações sobre as
 * atualizações realizadas na entidade Solicitação pelos servidores públicos.
 *
 * @author Giovane Neves, Andesson Reis
 */
@Entity
@Table(name = "atualizacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Atualizacao extends PersistenceEntity {

    /**
     * A data e hora em que a atualização ocorreu.
     */
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    /**
     * Observações ou notas sobre a atualização.
     */
    @Column(name = "observacao")
    private String observacao;

    /**
     * A solicitação vinculada a esta atualização.
     */
    @ManyToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

}