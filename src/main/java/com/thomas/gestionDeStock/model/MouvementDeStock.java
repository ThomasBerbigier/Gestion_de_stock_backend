package com.thomas.gestionDeStock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mouvementdestock")
public class MouvementDeStock extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;
}
