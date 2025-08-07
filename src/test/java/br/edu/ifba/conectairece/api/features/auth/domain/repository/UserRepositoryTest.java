package br.edu.ifba.conectairece.api.features.auth.domain.repository;

import br.edu.ifba.conectairece.api.features.auth.domain.dto.request.UserRegisterRequestDTO;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.Role;
import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

/**
 * Unit tests for {@link UserRepository}.
 * These tests validate basic repository operations, like fetching users by email.
 *
 * @author Jorge Roberto
 */
@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapperUtil objectMapperUtil;

    /**
     * Provides a test bean for {@link ObjectMapperUtil}.
     */
    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapperUtil objectMapperUtil() {
            return new ObjectMapperUtil();
        }
    }

    @Test
    @DisplayName("Should find user by valid email")
    void findByEmail_withValidEmail_shouldReturnSuccess() {
        String email = "test@gmail.com";

        UserRegisterRequestDTO dto = new UserRegisterRequestDTO();
        dto.setEmail("test@gmail.com");
        dto.setName("test");
        dto.setPassword("123456");
        dto.setRole(Role.CITIZEN);

        createUser(dto);

        Optional<User> result = this.userRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should return empty when email does not exist")
    void findByEmail_withInvalidEmail_ShouldReturnNotFound() {
        String email = "test@gmail.com";
        Optional<User> result = this.userRepository.findByEmail(email);

        assertThat(result.isEmpty()).isTrue();
    }

    /**
     * Helper method to persist a user in the database.
     *
     * @param data User registration data
     * @return persisted {@link User}
     */
    private User createUser (UserRegisterRequestDTO data) {
        User newUser = objectMapperUtil.map(data, User.class);
        newUser.setStatus(UserStatus.ACTIVE);
        this.entityManager.persist(newUser);
        return newUser;
    }
}