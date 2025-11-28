package br.com.cidadesinteligentes.infraestructure.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.User;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserDetailsService} used by Spring Security
 * to load user authentication details based on email.
 *
 * @author Jorge Roberto
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    /**
     * Loads a user by their email.
     *
     * @param email the user's email
     * @return the user as {@link UserDetails}
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return (UserDetails) user;
    }

}