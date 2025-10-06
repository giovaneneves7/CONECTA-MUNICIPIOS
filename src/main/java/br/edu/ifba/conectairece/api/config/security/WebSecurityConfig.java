package br.edu.ifba.conectairece.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application security configuration class.
 *
 * Defines filters, session policies, endpoint permissions and
 * JWT authentication using the {@link JWTLoginFilter}.
 *
 * Also registers the password encoder and the authentication manager.
 *
 * @author Jorge Roberto
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JWTLoginFilter jwtLoginFilter;

    /**
     * Defines security rules and adds the JWT filter to the chain.
     *
     * @param http Spring Security HTTP configuration
     * @return the resulting {@link SecurityFilterChain}
     * @throws Exception in case of errors
     */
    // TODO: Review the permissions below. They are temporarily open for development/testing.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // Authorization to access Swagger
                        .requestMatchers("/swagger-ui/**","/v3/api-docs/**","/swagger-resources/**","/swagger-resources","/webjars/**").permitAll()
                        // CORS and generic public endpoints
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Pré-voo CORS
                        // Authentication
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/sessions/session").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/users/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/auth/users/user/**").permitAll()

                        //Employees function /api/v1/function
                        .requestMatchers(HttpMethod.POST, "/api/v1/functions/function").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/functions/function").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/functions/function").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/functions/function/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/functions").permitAll()

                        // Functions for Category /api/v1/categories
                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/category").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/category").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/category/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/category/**").permitAll()


                        // Functions for Municipal Services /api/v1/municipal-services
                        .requestMatchers(HttpMethod.POST, "/api/v1/municipal-services/municipal-service").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/municipal-services").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/municipal-services/municipal-service").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/municipal-services/municipal-service/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/municipal-services/municipal-service").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/municipal-services/municipal-service/**").permitAll()

                        // Functions for Requests /api/v1/requests
                        .requestMatchers(HttpMethod.POST, "/api/v1/requests/request").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/requests").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/requests/request").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/requests/request/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/requests/request").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/requests/request/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/requests/request").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/requests/request/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v1/requests/request/*/review/accept").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/requests/request/*/review/reject").permitAll()

                        // Functions for Requirement Types /api/v1/requirement-types
                        .requestMatchers(HttpMethod.POST, "/api/v1/requirement-types/requirement-type").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/requirement-types").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/requirement-types/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/requirement-types/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/requirement-types/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/requirement-types/**").permitAll()

                        // Functions for Construction License Requirements /api/v1/construction-license-requirements
                        .requestMatchers(HttpMethod.POST, "/api/v1/construction-license-requirements/construction-license-requirement").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/construction-license-requirements").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/construction-license-requirements/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/construction-license-requirements/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/construction-license-requirements/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/construction-license-requirements/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/construction-license-requirements/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/construction-license-requirements/**").permitAll()
        

                        //Profiles - /api/v1/profiles
                        .requestMatchers(HttpMethod.POST, "/api/v1/profiles/profile").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/profiles/profile").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/profiles/profile").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/profiles/profile").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/profiles/profile/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/profiles/**").permitAll()

                        //Users - /api/v1/users
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/users/user/status").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/users/user/status/**").permitAll()

                        //Monitorings - /api/v1/monitorings
                        .requestMatchers(HttpMethod.POST, "/api/v1/monitorings").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/monitorings/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/monitorings").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/monitorings/**").permitAll()

                        // Updates - /api/v1/updates
                        .requestMatchers(HttpMethod.POST, "/api/v1/updates").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/updates/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/updates").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/updates/**").permitAll()

                        // Technical Responsibles - /api/v1/technical-responsibles
                        .requestMatchers(HttpMethod.POST, "/api/v1/technical-responsibles/users/*/profiles/technical-responsible").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/technical-responsibles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/technical-responsibles/technical-responsible/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/technical-responsibles/technical-responsible/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/technical-responsibles/*/requirements/*/accept").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/technical-responsibles/*/requirements/*/refuse").permitAll()

                        //Public servant controller
                        .requestMatchers(HttpMethod.POST, "/api/v1/public-servant-profiles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/public-servant-profiles/public-servant-profile").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/public-servant-profiles/public-servant-profile/**").permitAll()

                        //Roles
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/roles/role").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/roles/role/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/role").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/roles/role/**").permitAll()

                        // Database console for testing
                        //TODO: Remove it
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Returns the password encoder using BCrypt algorithm.
     *
     * @return an instance of {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns the authentication manager configured by Spring.
     *
     * @param authenticationConfiguration authentication configuration
     * @return the {@link AuthenticationManager}
     * @throws Exception in case of errors
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
