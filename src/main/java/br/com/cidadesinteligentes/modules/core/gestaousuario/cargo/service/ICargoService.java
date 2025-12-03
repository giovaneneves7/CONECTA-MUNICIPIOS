package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.service;

import br.com.cidadesinteligentes.modules.core.gestaousuario.permissao.dto.response.PermissaoResponseDTO;

public interface ICargoService {
    /**
     * Função para adicionar uma permissão a um cargo de algum perfil de usuário.
     * */
    PermissaoResponseDTO adicionarPermissao(String nomePermissao, Long idCargo);

    /**
     * Função para remover uma permissão de um cargo de algum perfil de usuário.
     * */
    PermissaoResponseDTO removerPermissao(String nomePermissao, Long idCargo);
}