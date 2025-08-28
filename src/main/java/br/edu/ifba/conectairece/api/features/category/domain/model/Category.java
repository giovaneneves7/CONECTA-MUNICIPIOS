package br.edu.ifba.conectairece.api.features.category.domain.model;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.conectairece.api.features.municipal_service.domain.model.MunicipalService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Category entity used to classify municipal services.
 * This class stores category-specific information such as name, description, and image URL.
 * It also maintains a many-to-many relationship with {@link MunicipalService}.
 *
 * @author Caio Alves
 */

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String imageUrl;

    @ManyToMany(mappedBy = "categories")
    private List<MunicipalService> municipalServices = new ArrayList<>();
}
