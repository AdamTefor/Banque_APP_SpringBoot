package ma.banque.mybanque.service;

import ma.banque.mybanque.entity.Compte;
import ma.banque.mybanque.entity.User;

public interface CompteService {
    Compte creerCompte(Compte compte);
    Compte trouverParId(Long id);
    Compte trouverParUser(User user);
    void supprimerCompte(Long id);

    void enregistrerCompte(Compte compte);
}

