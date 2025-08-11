package br.edu.ifba.conectairece.api.features.auth.domain.model;

import br.edu.ifba.conectairece.api.features.auth.domain.enums.UserStatus;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entity representing a user in the system.
 *
 * Implements {@link UserDetails} for integration with Spring Security.
 * Includes auditing fields, status, and access role.
 *
 * Uses Lombok and JPA annotations for persistence and auditing.
 *
 * @author Jorge Roberto
 */
@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true) //apenas o campo id
@ToString(onlyExplicitlyIncluded = true,  callSuper = true)
@EntityListeners(AuditingEntityListener.class) //Auditoria
public class User extends PersistenceEntity implements UserDetails, Serializable {

    @Column(name = "name",  nullable = false, length = 100)
    private String name;

    @Email
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;

    // Data/hora em que o usuário foi criado
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Data/hora da última atualização no usuário
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Usuário responsável pela criação
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    // Usuário responsável pela última atualização
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role.getName());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }
}
