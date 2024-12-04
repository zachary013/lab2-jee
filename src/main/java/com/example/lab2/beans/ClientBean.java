package com.example.lab2.beans;

import com.example.lab2.entities.Client;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class ClientBean implements Serializable {

    private Client client = new Client();
    private List<Client> clients = new ArrayList<>();

    public String saveClient() {
        client.setDateInscription(new Date());
        // Add logic to save client
        clients.add(client);
        client = new Client();
        return "client-list?faces-redirect=true";
    }

    public String editClient(Client client) {
        this.client = client;
        return "client-form?faces-redirect=true";
    }

    public String deleteClient(Client client) {
        clients.remove(client);
        return "client-list?faces-redirect=true";
    }

    // Getters and Setters
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}