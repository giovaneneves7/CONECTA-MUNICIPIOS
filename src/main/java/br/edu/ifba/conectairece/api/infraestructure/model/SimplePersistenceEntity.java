package br.edu.ifba.conectairece.api.infraestructure.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Base class for simple persistent entities (uses long as id type).
 *
 * <p>Provides an automatically generated unique identifier for subclasses.
 * Can be used as a superclass for other JPA entities.</p>
 *
 * @author Giovane Neves
 */
@MappedSuperclass
public class SimplePersistenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include @ToString.Include
    @Getter
    private long id;

}
