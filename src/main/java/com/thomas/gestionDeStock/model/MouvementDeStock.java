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

    @Column(name = "typemouvement")
    private String typeMouvement;

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;
}
