package ma.banque.mybanque.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DemandeCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;
    private String statut; // "En attente", "Acceptée", "Refusée"
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructeurs
    public DemandeCredit() {}

    public DemandeCredit(double montant, String statut, LocalDate date, User user) {
        this.montant = montant;
        this.statut = statut;
        this.date = date;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
