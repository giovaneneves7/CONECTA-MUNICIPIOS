package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.gestor.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("GESTOR_MANUTENCAO")
public class GestorSolicitacoesManutencaoUrbana extends Profile {

}
