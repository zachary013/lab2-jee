package com.example.lab2.beans;

import com.example.lab2.entities.LignePanier;
import com.example.lab2.entities.Panier;
import com.example.lab2.entities.Produit;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class LignePanierBean implements Serializable {

    private LignePanier lignePanier = new LignePanier();
    private List<LignePanier> lignesPanier = new ArrayList<>();
    private Long selectedPanierId;
    private Long selectedProduitId;

    public String saveLignePanier() {
        lignePanier.calculerPrixTotal();
        // Add logic to save lignePanier
        lignesPanier.add(lignePanier);
        lignePanier = new LignePanier();
        return "ligne-panier-list?faces-redirect=true";
    }

    public String editLignePanier(LignePanier lignePanier) {
        this.lignePanier = lignePanier;
        this.selectedPanierId = lignePanier.getPanier().getId();
        this.selectedProduitId = lignePanier.getProduit().getId();
        return "ligne-panier-form?faces-redirect=true";
    }

    public String deleteLignePanier(LignePanier lignePanier) {
        lignesPanier.remove(lignePanier);
        return "ligne-panier-list?faces-redirect=true";
    }

    // Getters and Setters
    public LignePanier getLignePanier() {
        return lignePanier;
    }

    public void setLignePanier(LignePanier lignePanier) {
        this.lignePanier = lignePanier;
    }

    public List<LignePanier> getLignesPanier() {
        return lignesPanier;
    }

    public void setLignesPanier(List<LignePanier> lignesPanier) {
        this.lignesPanier = lignesPanier;
    }

    public Long getSelectedPanierId() {
        return selectedPanierId;
    }

    public void setSelectedPanierId(Long selectedPanierId) {
        this.selectedPanierId = selectedPanierId;
    }

    public Long getSelectedProduitId() {
        return selectedProduitId;
    }

    public void setSelectedProduitId(Long selectedProduitId) {
        this.selectedProduitId = selectedProduitId;
    }
}