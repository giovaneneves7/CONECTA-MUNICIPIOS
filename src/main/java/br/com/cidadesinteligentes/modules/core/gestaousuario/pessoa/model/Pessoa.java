package br.com.cidadesinteligentes.modules.core.gestaousuario.pessoa.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Classe base abstrata que representa uma entidade Pessoa no sistema.
 * Esta classe serve como classe pai para todos os tipos de pessoa e contém atributos comuns.
 * Ela é mapeada para a tabela "pessoas" no banco de dados.
 *
 * @author Jorge Roberto, Caio Alves
 */
@Entity
@Table(name = "pessoas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Pessoa extends PersistenceEntity {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "nomeCompleto",nullable = false, length = 100)
    private String nomeCompleto;

    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "genero", nullable = false)
    private String genero;

    @OneToOne(mappedBy = "pessoa")
    private Usuario usuario;
}
