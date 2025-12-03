package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.service;

public interface ICargoService {
    void adicionarPermissao(String nomePermissao, Long idCargo);
    void removerPermissao(String nomePermissao, Long idCargo);
}
