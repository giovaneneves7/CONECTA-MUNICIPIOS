package br.edu.ifba.conectairece.api.modules.manutencaourbana.gestor.model;

import br.edu.ifba.conectairece.api.features.profile.domain.model.Profile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("GESTOR_MANUTENCAO")
public class GestorSolicitacoesManutencaoUrbana extends Profile {

}
