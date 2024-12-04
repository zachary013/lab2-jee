package com.example.lab2.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import com.example.lab2.entities.Client;

@Named
@SessionScoped
public class ClientBean implements Serializable {

    @PersistenceContext
    private EntityManager em;

    private Client clientActuel;

    @Transactional
    public void saveClient(Client client) {
        if (client.getId() == null) {
            em.persist(client);
        } else {
            em.merge(client);
        }
        this.clientActuel = client;
    }

    public List<Client> listClient() {
        return em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Transactional
    public void updateClient(Client client) {
        em.merge(client);
        if (clientActuel != null && clientActuel.getId().equals(client.getId())) {
            this.clientActuel = client;
        }
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = em.find(Client.class, id);
        if (client != null) {
            em.remove(client);
            if (clientActuel != null && clientActuel.getId().equals(id)) {
                this.clientActuel = null;
            }
        }
    }

}