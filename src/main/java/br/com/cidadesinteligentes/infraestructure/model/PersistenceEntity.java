package br.com.cidadesinteligentes.infraestructure.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;


/**
 * Base class for persistent entities.
 *
 * <p>Provides an automatically generated unique identifier for subclasses.
 * Can be used as a superclass for other JPA entities.</p>
 *
 * @author Jorge Roberto
 */
@MappedSuperclass
public class PersistenceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include @ToString.Include
    @Getter private UUID id;
}
