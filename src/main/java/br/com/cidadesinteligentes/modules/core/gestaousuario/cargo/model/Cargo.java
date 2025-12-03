package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.model.Permissao;
import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Perfil;
import br.com.cidadesinteligentes.infraestructure.model.SimplePersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Entidade que representa um cargo (role) no sistema.
 *
 * @author Giovane Neves,Jorge Roberto, Caio Alves
 */
@Entity
@Table(name = "cargos")
@Data
public class Cargo extends SimplePersistenceEntity {

    @Column(name = "nome",  nullable = false, length = 50)
    String nome;
    @Column(name = "descricao")
    String descricao;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permissoes_cargo",
            joinColumns = @JoinColumn(name = "cargo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<Permissao> permissoes;

    @OneToMany(mappedBy = "cargo")
    private List<Perfil> perfis = new ArrayList<>();
}
