package br.edu.ifba.conectairece.api.features.person.domain.model;

import br.edu.ifba.conectairece.api.features.auth.domain.model.User;
import br.edu.ifba.conectairece.api.infraestructure.model.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Abstract base class representing a Person entity in the system.
 * This class serves as the parent class for all person types and contains common attributes.
 * It is mapped to the "people" table in the database.
 *
 * @author Jorge Roberto
 */
@Entity
@Table(name = "people")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class Person extends PersistenceEntity implements Serializable {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "full_name",nullable = false, length = 100)
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @OneToOne(mappedBy = "person")
    private User user;
}
