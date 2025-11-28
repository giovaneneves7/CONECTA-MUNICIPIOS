package br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.atualizacao.model;


import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.solicitacao.model.Request;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
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
 * Represents a Update entity that stores information about Request entity updates made by the public servants.
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "updates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Update extends PersistenceEntity {

    private LocalDateTime timestamp;

    private String note;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

}
