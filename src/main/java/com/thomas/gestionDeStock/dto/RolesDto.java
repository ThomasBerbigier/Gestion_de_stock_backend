package com.thomas.gestionDeStock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thomas.gestionDeStock.model.Roles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolesDto {

    private Integer id;

    private String roleName;

    @JsonIgnore
    private UtilisateurDto utilisateur;

    // Entité vers Dto pour la récupération des données
    public static RolesDto fromEntity(Roles role) {
        if (role == null) {
            return null;
        }

        return RolesDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    // Dto vers Entité pour la persistance des données
    public static Roles toEntity(RolesDto rolesDto) {
        if (rolesDto == null) {
            return null;
        }

        Roles role = new Roles();
        role.setId(rolesDto.getId());
        role.setRoleName(rolesDto.getRoleName());
        role.setUtilisateur(UtilisateurDto.toEntity(rolesDto.getUtilisateur()));

        return role;
    }
}
