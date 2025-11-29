package br.com.cidadesinteligentes.modules.core.gestaousuario.cargo.service;

public interface IRoleService {
    void addPermission(String permissionName, Long roleId);
    void removePermission(String permissionName, Long roleId);
}
