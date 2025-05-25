package ma.banque.mybanque.controller;

import ma.banque.mybanque.entity.DemandeCredit;
import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.service.DemandeCreditService;
import ma.banque.mybanque.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/client/demandes")
public class DemandeController {

    private final DemandeCreditService demandeCreditService;
    private final UserService userService;

    public DemandeController(DemandeCreditService demandeCreditService, UserService userService) {
        this.demandeCreditService = demandeCreditService;
        this.userService = userService;
    }

    // Affiche le formulaire pour créer une nouvelle demande
    @GetMapping("/ajouter")
    public String formulaireDemande(Model model) {
        model.addAttribute("demande", new DemandeCredit());
        return "client/demande-form";
    }

    // Enregistre une nouvelle demande
    @PostMapping("/ajouter")
    public String enregistrerDemande(@ModelAttribute DemandeCredit demande, Principal principal) {
        String email = principal.getName();
        User user = userService.trouverParEmail(email);

        demande.setUser(user);
        demande.setDate(LocalDate.now());
        demande.setStatut("En attente");

        demandeCreditService.creerDemande(demande);

        return "redirect:/client/demandes";
    }

    // Supprimer une demande en attente
    @GetMapping("/supprimer/{id}")
    public String supprimerDemande(@PathVariable Long id, Principal principal) {
        DemandeCredit demande = demandeCreditService.trouverParId(id);

        // Vérifie que la demande appartient à l'utilisateur connecté
        if (demande != null && demande.getUser().getEmail().equals(principal.getName())
                && "En attente".equalsIgnoreCase(demande.getStatut())) {
            demandeCreditService.supprimerDemande(id);
        }

        return "redirect:/client/demandes";
    }
}
