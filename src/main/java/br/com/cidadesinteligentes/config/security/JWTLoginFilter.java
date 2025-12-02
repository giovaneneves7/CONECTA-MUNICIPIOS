package br.com.cidadesinteligentes.config.security;

import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.model.Usuario;
import br.com.cidadesinteligentes.modules.core.gestaousuario.usuario.repository.IUsuarioRepository;
import br.com.cidadesinteligentes.infraestructure.service.TokenAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * JWT authentication filter executed once per request.
 *
 * Intercepts all requests and verifies if a valid JWT token
 * is present in the Authorization header. If valid, authenticates
 * the user in the Spring Security context.
 *
 * Public endpoints are ignored by this check.
 *
 * @author Jorge Roberto
 */
@Component
public class JWTLoginFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTLoginFilter.class);
    private static final List<String> PUBLIC_ENDPOINTS = List.of("/api/v1/auth/users/user", "/api/v1/auth/sessions/session",
            "/h2-console/**", "/api/v1/auth/**", "/api/v1/functions/**", "/api/v1/functions", "/api/v1/categories/**", "/api/v1/categories"
            , "/api/v1/municipal-services/**", "/api/v1/municipal-services", "/api/v1/requests/**", "/api/v1/requests", "/api/v1/requirement-types/**",
            "/api/v1/requirement-types", "/api/v1/construction-license-requirements", "/api/v1/profiles", "/api/v1/profiles/**", "/api/v1/users", "/api/v1/users/**",
            "/api/v1/public-servant-profiles", "/api/v1/public-servant-profiles/**", "/api/v1/roles", "/api/v1/roles/**", "/api/v1/admin-profiles",
            "/api/v1/admin-profiles/**", "/api/v1/general-evaluation-items", "/api/v1/general-evaluation-items/**", "/api/v1/documents", "/api/v1/documents/**",
            "/api/v1/evaluation-items", "/api/v1/evaluation-items/**");

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenAuthenticationService tokenAuthenticationService;
    private final IUsuarioRepository userRepository;


    public JWTLoginFilter(TokenAuthenticationService tokenAuthenticationService,
                          IUsuarioRepository userRepository) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userRepository = userRepository;
    }

    /**
     * Executes the JWT authentication filter on each request.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain filter chain
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Optional<String> tokenOpt = extractToken(request);

            if (tokenOpt.isPresent()) {
                String email = tokenAuthenticationService.validateToken(tokenOpt.get());

                if (email != null) {
                    Usuario user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new IllegalStateException("User not found for email: " + email));

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    LOGGER.warn("Invalid JWT token provided for request to {}", path);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("JWT authentication failed: {}", ex.getMessage(), ex);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Checks if the current endpoint is public and does not require authentication.
     *
     * @param path request path
     * @return true if public
     */
    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }

    /**
     * Extracts the token from the Authorization header of the request.
     *
     * @param request HTTP request
     * @return {@link Optional} with the token, if present
     */
    private Optional<String> extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTH_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return Optional.of(header.substring(BEARER_PREFIX.length()));
        }
        return Optional.empty();
    }
}

