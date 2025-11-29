package br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.service;

import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.dto.response.CategoriaResponseDTO;
import br.com.cidadesinteligentes.modules.gestaomanutencaourbana.categoria.model.CategoriaManutencaoUrbana;

import java.util.List;

public interface ICategoriaManutencaoUrbanaService {

    CategoriaResponseDTO createCategoria(CategoriaManutencaoUrbana categoria);

    List<CategoriaResponseDTO> findAllCategorias();

    CategoriaResponseDTO findCategoriaById(Long id);

    CategoriaResponseDTO updateCategoria(Long id, CategoriaManutencaoUrbana categoriaAtualizada);

    void deleteCategoria(Long id);
}