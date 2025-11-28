package br.com.cidadesinteligentes.modules.core.gestaousuario.admin.model;

import br.com.cidadesinteligentes.modules.core.gestaousuario.perfil.model.Profile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Represents a super admin profile in the system.
 *
 * @author Jorge Roberto
 */
@Entity
@Getter @Setter
//@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("ADMIN")
public class AdminProfile extends Profile {
}
