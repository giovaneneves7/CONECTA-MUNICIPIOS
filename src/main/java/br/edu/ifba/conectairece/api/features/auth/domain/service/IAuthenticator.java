package br.edu.ifba.conectairece.api.features.auth.domain.service;

/**
 * Interface with user authentication operation
 * @author Giovane Neves
 */
public interface IAuthenticator {

    /**
     * Authenticate a user with
     *
     * @param credentials The user credentials
     */
    void authenticate(Object credentials); //TODO: Criar classe AuthResult

    /**
     * Revoke user tokens when they log out, change their password, or engage in suspicious activity.
     *
     * @param token The user's current token
     */
    void revoke(String token);

}
