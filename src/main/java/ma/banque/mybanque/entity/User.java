package ma.banque.mybanque.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    private String role; // ADMIN ou CLIENT

    @Column(name = "photo")
    private String photo; // nom du fichier de la photo

    // ========================
    // Relations
    // ========================

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Compte compte;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DemandeCredit> demandes;

    // ========================
    // Constructeurs
    // ========================

    public User() {}

    public User(String nom, String email, String motDePasse, String role) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    // ========================
    // Getters / Setters
    // ========================

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }

    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public Compte getCompte() { return compte; }

    public void setCompte(Compte compte) { this.compte = compte; }

    public List<DemandeCredit> getDemandes() { return demandes; }

    public void setDemandes(List<DemandeCredit> demandes) { this.demandes = demandes; }

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }
}
