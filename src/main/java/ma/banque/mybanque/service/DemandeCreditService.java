package ma.banque.mybanque.service;

import ma.banque.mybanque.entity.DemandeCredit;
import ma.banque.mybanque.entity.User;

import java.util.List;

public interface DemandeCreditService {
    DemandeCredit creerDemande(DemandeCredit demande);
    List<DemandeCredit> toutesLesDemandes();
    List<DemandeCredit> demandesParUser(User user);
    DemandeCredit trouverParId(Long id);
    void supprimerDemande(Long id);
}
