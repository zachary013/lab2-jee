package com.example.lab2.beans;

import com.example.lab2.entities.Produit;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ProduitBean implements Serializable {

    private Produit produit = new Produit();
    private List<Produit> produits = new ArrayList<>();

    public String saveProduit() {
        // Add logic to save produit
        produits.add(produit);
        produit = new Produit();
        return "produit-list?faces-redirect=true";
    }

    public String editProduit(Produit produit) {
        this.produit = produit;
        return "produit-form?faces-redirect=true";
    }

    public String deleteProduit(Produit produit) {
        produits.remove(produit);
        return "produit-list?faces-redirect=true";
    }

    // Getters and Setters
    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}