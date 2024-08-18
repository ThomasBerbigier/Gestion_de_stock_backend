package com.thomas.gestionDeStock.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mouvementdestock")
public class MouvementDeStock extends AbstractEntity{

    @Column(name = "datemouvement")
    private Instant dateMouvement;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "typemouvementdestock")
    @Enumerated(EnumType.STRING)
    private TypeMouvementDeStock typeMouvementDeStock;

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    @Column(name = "identreprise")
    private Integer idEntreprise;
}
