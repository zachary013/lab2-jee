package com.example.lab2.beans;

import com.example.lab2.entities.Panier;
import com.example.lab2.entities.Client;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class PanierBean implements Serializable {

    private Panier panier = new Panier();
    private List<Panier> paniers = new ArrayList<>();
    private Long selectedClientId;

    public String savePanier() {
        panier.setDateCreation(new Date());
        // Add logic to save panier
        paniers.add(panier);
        panier = new Panier();
        return "panier-list?faces-redirect=true";
    }

    public String editPanier(Panier panier) {
        this.panier = panier;
        this.selectedClientId = panier.getClient().getId();
        return "panier-form?faces-redirect=true";
    }

    public String deletePanier(Panier panier) {
        paniers.remove(panier);
        return "panier-list?faces-redirect=true";
    }

    // Getters and Setters
    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public List<Panier> getPaniers() {
        return paniers;
    }

    public void setPaniers(List<Panier> paniers) {
        this.paniers = paniers;
    }

    public Long getSelectedClientId() {
        return selectedClientId;
    }

    public void setSelectedClientId(Long selectedClientId) {
        this.selectedClientId = selectedClientId;
    }
}