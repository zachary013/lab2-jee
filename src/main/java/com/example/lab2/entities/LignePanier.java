package com.example.lab2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lignes_panier")
public class LignePanier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id", nullable = false)
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private Integer quantite;

    @Column(name = "prix_total")
    private Double prixTotal;

    // Calcul automatique du prix total
    @PrePersist
    @PreUpdate
    public void calculerPrixTotal() {
        this.prixTotal = this.produit.getPrix().doubleValue() * this.quantite;
    }
}