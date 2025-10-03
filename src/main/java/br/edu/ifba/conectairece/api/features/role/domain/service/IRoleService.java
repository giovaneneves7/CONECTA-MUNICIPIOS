package br.edu.ifba.conectairece.api.features.role.domain.service;

public interface IRoleService {
    void addPermission(String permissionName, Long roleId);
    void removePermission(String permissionName, Long roleId);
}
