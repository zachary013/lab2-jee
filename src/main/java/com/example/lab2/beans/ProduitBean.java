package com.example.lab2.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import com.example.lab2.entities.Produit;

@Named
@ApplicationScoped
public class ProduitBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void saveProduit(Produit produit) {
        if (produit.getId() == null) {
            em.persist(produit);
        } else {
            em.merge(produit);
        }
    }

    public List<Produit> listProduit() {
        return em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
    }

    @Transactional
    public void updateProduit(Produit produit) {
        em.merge(produit);
    }

    @Transactional
    public void deleteProduit(Long id) {
        Produit produit = em.find(Produit.class, id);
        if (produit != null) {
            em.remove(produit);
        }
    }

    public Produit getProduit(Long id) {
        return em.find(Produit.class, id);
    }
}