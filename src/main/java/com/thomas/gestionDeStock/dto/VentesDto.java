package com.thomas.gestionDeStock.dto;

import com.thomas.gestionDeStock.model.Ventes;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class VentesDto {

    private Integer id;

    private String code;

    private Instant dateVente;

    private String commentaire;

    private List<LigneVenteDto> ligneVentes;

    private Integer idEntreprise;

    // Entité vers Dto pour la récupération des données
    public static VentesDto fromEntity(Ventes ventes) {
        if (ventes == null) {
            return null;
        }

        return VentesDto.builder()
                .id(ventes.getId())
                .code(ventes.getCode())
                .idEntreprise(ventes.getIdEntreprise())
                .commentaire(ventes.getCommentaire())
                .build();
    }

    // Dto vers Entité pour la persistance des données
    public static Ventes toEntity(VentesDto ventesDto) {
        if (ventesDto == null) {
            return null;
        }

        Ventes ventes = new Ventes();
        ventes.setId(ventesDto.getId());
        ventes.setCode(ventesDto.getCode());
        ventes.setCommentaire(ventesDto.getCommentaire());
        ventes.setIdEntreprise(ventesDto.getIdEntreprise());

        return ventes;
    }
}
