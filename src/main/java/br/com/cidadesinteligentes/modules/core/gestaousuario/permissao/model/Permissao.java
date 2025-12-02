package br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa a permissão de uma role no sistema.
 *
 * @author Giovane Neves
 */
@Entity
@Table(name = "permissoes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Permissao  extends PersistenceEntity {

    @Column(name = "nome", nullable = false, length = 100)
    String nome;

}
