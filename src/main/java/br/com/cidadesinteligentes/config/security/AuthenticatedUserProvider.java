package br.com.cidadesinteligentes.config.security;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Component responsible for retrieving the authenticated user
 * from Spring Security's context.
 *
 * Returns an {@link Optional<User>} with the authenticated user.
 *
 * @author Jorge Roberto
 */
@Component
public class AuthenticatedUserProvider {

    /**
     * Retrieves the authenticated user from the security context.
     *
     * @return {@link Optional} containing the user if present
     */
    public Optional<Usuario> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Usuario user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
