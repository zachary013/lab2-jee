package com.example.lab2.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.lab2.entities.Panier;
import com.example.lab2.entities.LignePanier;
import com.example.lab2.entities.Produit;

@Named
@SessionScoped
public class PanierBean implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private ClientBean clientBean;

    private Panier panierActuel;

    @Transactional
    public void savePanier(Panier panier) {
        if (panier.getId() == null) {
            em.persist(panier);
        } else {
            em.merge(panier);
        }
        this.panierActuel = panier;
    }

    public List<Panier> listPanier() {
        return em.createQuery("SELECT p FROM Panier p", Panier.class).getResultList();
    }

    @Transactional
    public void updatePanier(Panier panier) {
        em.merge(panier);
        if (panierActuel != null && panierActuel.getId().equals(panier.getId())) {
            this.panierActuel = panier;
        }
    }

    @Transactional
    public void deletePanier(Long id) {
        Panier panier = em.find(Panier.class, id);
        if (panier != null) {
            em.remove(panier);
            if (panierActuel != null && panierActuel.getId().equals(id)) {
                this.panierActuel = null;
            }
        }
    }

    @Transactional
    public void addToPanier(Produit produit, int quantite) {
        if (panierActuel == null) {
            panierActuel = new Panier();
            panierActuel.setClient(clientBean.getClientActuel());
            em.persist(panierActuel);
        }

        LignePanier lignePanier = panierActuel.getLignesPanier().stream()
                .filter(l -> l.getProduit().getId().equals(produit.getId()))
                .findFirst()
                .orElse(null);

        if (lignePanier != null) {
            lignePanier.setQuantite(lignePanier.getQuantite() + quantite);
        } else {
            lignePanier = new LignePanier();
            lignePanier.setProduit(produit);
            lignePanier.setQuantite(quantite);
            lignePanier.setPanier(panierActuel);
            panierActuel.getLignesPanier().add(lignePanier);
        }

        em.merge(panierActuel);
    }

    @Transactional
    public void removeFromPanier(LignePanier lignePanier) {
        panierActuel.getLignesPanier().remove(lignePanier);
        em.remove(lignePanier);
        em.merge(panierActuel);
    }

    public List<LignePanier> getLignesPanier() {
        return panierActuel != null ? panierActuel.getLignesPanier() : new ArrayList<>();
    }

    public double getTotal() {
        return getLignesPanier().stream()
                .mapToDouble(ligne -> ligne.getProduit().getPrix().doubleValue() * ligne.getQuantite())
                .sum();
    }

    @Transactional
    public void clearPanier() {
        if (panierActuel != null) {
            em.remove(panierActuel);
            panierActuel = null;
        }
    }

    // Add getter and setter for panierActuel
    public Panier getPanierActuel() {
        return panierActuel;
    }

    public void setPanierActuel(Panier panierActuel) {
        this.panierActuel = panierActuel;
    }
}