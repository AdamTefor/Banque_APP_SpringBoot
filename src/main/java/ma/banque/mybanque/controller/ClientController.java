package ma.banque.mybanque.controller;

import ma.banque.mybanque.entity.Compte;
import ma.banque.mybanque.entity.DemandeCredit;
import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.service.CompteService;
import ma.banque.mybanque.service.DemandeCreditService;
import ma.banque.mybanque.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final UserService userService;
    private final CompteService compteService;
    private final DemandeCreditService demandeCreditService;

    public ClientController(UserService userService, CompteService compteService, DemandeCreditService demandeCreditService) {
        this.userService = userService;
        this.compteService = compteService;
        this.demandeCreditService = demandeCreditService;
    }

    // ✅ Tableau de bord client
    @GetMapping("/dashboard")
    public String tableauClient(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.trouverParEmail(email);
        model.addAttribute("user", user);

        // Récupération du compte associé
        Compte compte = compteService.trouverParUser(user);
        model.addAttribute("compte", compte);

        // Récupération des demandes associées
        List<DemandeCredit> demandes = demandeCreditService.demandesParUser(user);
        model.addAttribute("nbDemandes", demandes.size());

        return "client/dashboard";
    }

    // ✅ Voir les infos du compte
    @GetMapping("/compte")
    public String voirCompte(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.trouverParEmail(email);
        model.addAttribute("user", user);

        Compte compte = compteService.trouverParUser(user);

        if (compte != null) {
            model.addAttribute("compte", compte);

            double montant = compte.getSolde();
            model.addAttribute("MAD", montant);
            model.addAttribute("EUR", montant / 11); // taux fictif
            model.addAttribute("USD", montant / 10);
            model.addAttribute("GBP", montant / 12);
        } else {
            model.addAttribute("messageErreur", "⚠ Aucun compte n’est encore associé à votre profil.");
        }

        return "client/compte-profil";
    }

    // ✅ Liste des demandes
    @GetMapping("/demandes")
    public String mesDemandes(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.trouverParEmail(email);

        List<DemandeCredit> demandes = demandeCreditService.demandesParUser(user);
        model.addAttribute("demandes", demandes);

        return "client/demandes-list";
    }

    // ✅ Suivi d'une demande
    @GetMapping("/demandes/{id}")
    public String suiviDemande(@PathVariable Long id, Model model) {
        DemandeCredit demande = demandeCreditService.trouverParId(id);
        model.addAttribute("demande", demande);
        return "client/demande-suivi";
    }
}
