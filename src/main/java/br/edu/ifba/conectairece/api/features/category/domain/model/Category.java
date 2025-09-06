package br.edu.ifba.conectairece.api.features.category.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.conectairece.api.features.municipalservice.domain.model.MunicipalService;
import jakarta.persistence.*;
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
public class Category implements Serializable{

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "categories")
    private List<MunicipalService> municipalServices = new ArrayList<>();
}
