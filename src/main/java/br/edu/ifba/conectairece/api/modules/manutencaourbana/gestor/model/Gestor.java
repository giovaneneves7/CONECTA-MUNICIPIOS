package br.edu.ifba.conectairece.api.modules.manutencaourbana.gestor.model;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gestores")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_gestor")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Gestor extends Profile {

}
