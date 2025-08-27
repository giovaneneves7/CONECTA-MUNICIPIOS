package br.edu.ifba.conectairece.api.features.municipal_service.domain.model;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.conectairece.api.features.category.domain.model.Category;
import br.edu.ifba.conectairece.api.features.request.domain.model.Request;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a MunicipalService entity that models a public service provided by the municipality.
 * This class stores service-specific information such as name and description.
 * It also manages relationships with {@link Request} and {@link Category}.
 *
 * - One municipal service can have multiple requests.
 * - A municipal service can belong to multiple categories.
 *
 * @author Caio Alves
 */

@Entity
@Table(name = "municipal_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalService {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "municipalService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "municipal_service_categories",
        joinColumns = @JoinColumn(name = "municipal_service_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
}
