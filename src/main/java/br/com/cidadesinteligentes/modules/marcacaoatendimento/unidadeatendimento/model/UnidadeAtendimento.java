package br.com.cidadesinteligentes.modules.marcacaoatendimento.unidadeatendimento.model;

import br.com.cidadesinteligentes.infraestructure.model.PersistenceEntity;
import br.com.cidadesinteligentes.modules.marcacaoatendimento.servico.model.Servico;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entidade base que representa uma unidade de atendimento genérica.
 * Pode ser estendida por tipos específicos como UBS, Hospital, Clínica, etc.
 * * @author Juan Teles Dias
 */
@Table(name = "unidades_atendimento") // Anotação TABLE adicionada
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_unidade", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class UnidadeAtendimento extends PersistenceEntity {

    private String nome;
    private String codigoCNES;
    private String endereco;
    private String telefone;
    private String horarioFuncionamento;

    @OneToMany(mappedBy = "unidade")
    private List<Servico> servicosOferecidos;

}