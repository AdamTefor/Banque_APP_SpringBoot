package ma.banque.mybanque.entity;

import jakarta.persistence.*;

@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double solde;
    private String devise; // MAD, EUR, USD, GBP

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructeurs
    public Compte() {}

    public Compte(double solde, String devise, User user) {
        this.solde = solde;
        this.devise = devise;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
