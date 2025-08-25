package br.edu.ifba.conectairece.api.infraestructure.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for pagination responses.
 * Wraps paginated results with metadata about the current page, total pages, and elements.
 *
 * @author Jorge Roberto
 */
@Getter @Setter
public class PageableDTO {

    /**
     * The list of elements on the current page.
     */
    private List content = new ArrayList<>();

    /**
     * Indicates if this is the first page.
     */
    private boolean first;

    /**
     * Indicates if this is the last page.
     */
    private boolean last;

    /**
     * The current page number (zero-based index).
     */
    @JsonProperty("page")
    private int number;

    /**
     * The size of the page (number of elements per page).
     */
    private int size;

    /**
     * The number of elements on the current page.
     */
    @JsonProperty("pageElements")
    private int numberOfElements;

    /**
     * The total number of pages available.
     */
    private int totalPages;

    /**
     * The total number of elements across all pages.
     */
    private int totalElements;
}
