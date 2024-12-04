package com.example.lab2.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import com.example.lab2.entities.LignePanier;
import com.example.lab2.entities.Panier;
import com.example.lab2.entities.Produit;

@Named
@RequestScoped
public class LignePanierBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveLignePanier(LignePanier lignePanier) {
        if (lignePanier.getId() == null) {
            em.persist(lignePanier);
        } else {
            em.merge(lignePanier);
        }
    }

    public List<LignePanier> listLignePanier() {
        return em.createQuery("SELECT lp FROM LignePanier lp", LignePanier.class).getResultList();
    }

    @Transactional
    public void updateLignePanier(LignePanier lignePanier) {
        em.merge(lignePanier);
    }

    @Transactional
    public void deleteLignePanier(Long id) {
        LignePanier lignePanier = em.find(LignePanier.class, id);
        if (lignePanier != null) {
            em.remove(lignePanier);
        }
    }

    public LignePanier getLignePanier(Long id) {
        return em.find(LignePanier.class, id);
    }

    @Transactional
    public void updateQuantite(Long lignePanierId, int newQuantite) {
        LignePanier lignePanier = em.find(LignePanier.class, lignePanierId);
        if (lignePanier != null) {
            lignePanier.setQuantite(newQuantite);
            em.merge(lignePanier);
        }
    }

    public List<LignePanier> getLignePanierByPanier(Panier panier) {
        return em.createQuery("SELECT lp FROM LignePanier lp WHERE lp.panier = :panier", LignePanier.class)
                .setParameter("panier", panier)
                .getResultList();
    }

    public LignePanier findLignePanierByPanierAndProduit(Panier panier, Produit produit) {
        List<LignePanier> results = em.createQuery(
                        "SELECT lp FROM LignePanier lp WHERE lp.panier = :panier AND lp.produit = :produit", LignePanier.class)
                .setParameter("panier", panier)
                .setParameter("produit", produit)
                .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Transactional
    public void removeLignePanierFromPanier(Panier panier, Produit produit) {
        LignePanier lignePanier = findLignePanierByPanierAndProduit(panier, produit);
        if (lignePanier != null) {
            panier.getLignesPanier().remove(lignePanier);
            em.remove(lignePanier);
            em.merge(panier);
        }
    }
}