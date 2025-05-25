package ma.banque.mybanque.controller;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import ma.banque.mybanque.entity.Compte;
import ma.banque.mybanque.entity.DemandeCredit;
import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.service.CompteService;
import ma.banque.mybanque.service.DemandeCreditService;
import ma.banque.mybanque.service.UserService;
import ma.banque.mybanque.utils.PdfExporter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final DemandeCreditService demandeCreditService;
    private final CompteService compteService;

    public AdminController(UserService userService,
                           DemandeCreditService demandeCreditService,
                           CompteService compteService) {
        this.userService = userService;
        this.demandeCreditService = demandeCreditService;
        this.compteService = compteService;
    }

    // ====================== DASHBOARD ======================

    @GetMapping("/dashboard")
    public String tableauDeBord(Model model) {
        model.addAttribute("users", userService.listerUsers());
        model.addAttribute("demandes", demandeCreditService.toutesLesDemandes());
        return "admin/dashboard";
    }

    // ====================== UTILISATEURS ======================

    @GetMapping("/users")
    public String listerUtilisateurs(Model model) {
        model.addAttribute("users", userService.listerUsers());
        return "admin/users-list";
    }

    @GetMapping("/users/ajouter")
    public String formAjouterUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    @PostMapping("/users/enregistrer")
    public String enregistrerUser(@ModelAttribute("user") User user,
                                  @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/images/profil/" + filename);
            Files.copy(imageFile.getInputStream(), path);
            user.setPhoto(filename);
        }
        userService.ajouterUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/supprimer/{id}")
    public String supprimerUser(@PathVariable Long id) {
        userService.supprimerUser(id);
        return "redirect:/admin/users";
    }

    // ====================== COMPTES ======================

    @GetMapping("/comptes/ajouter/{id}")
    public String creerComptePourClient(@PathVariable Long id, Model model) {
        User user = userService.trouverParId(id);
        model.addAttribute("user", user);
        model.addAttribute("compte", new Compte());
        return "admin/compte-form";
    }

    @PostMapping("/comptes/enregistrer")
    public String enregistrerCompte(@ModelAttribute Compte compte, @RequestParam Long userId) {
        User user = userService.trouverParId(userId);
        compte.setUser(user);
        compteService.enregistrerCompte(compte);
        return "redirect:/admin/users";
    }

    @GetMapping("/comptes/modifier/{id}")
    public String modifierCompte(@PathVariable Long id, Model model) {
        Compte compte = compteService.trouverParId(id);
        model.addAttribute("compte", compte);
        model.addAttribute("user", compte.getUser());
        return "admin/compte-form";
    }

    @PostMapping("/comptes/modifier")
    public String enregistrerModification(@ModelAttribute("compte") Compte compte) {
        compteService.enregistrerCompte(compte);
        return "redirect:/admin/users";
    }

    // ====================== DEMANDES ======================

    @GetMapping("/demandes")
    public String listerDemandes(Model model) {
        model.addAttribute("demandes", demandeCreditService.toutesLesDemandes());
        return "admin/demandes-list";
    }

    @GetMapping("/demandes/{id}")
    public String voirDemande(@PathVariable Long id, Model model) {
        DemandeCredit demande = demandeCreditService.trouverParId(id);
        model.addAttribute("demande", demande);
        return "admin/demande-details";
    }

    @PostMapping("/demandes/accepter/{id}")
    public String approuverDemande(@PathVariable Long id) {
        DemandeCredit demande = demandeCreditService.trouverParId(id);
        demande.setStatut("Acceptée");
        demandeCreditService.creerDemande(demande);
        return "redirect:/admin/demandes";
    }

    @PostMapping("/demandes/refuser/{id}")
    public String refuserDemande(@PathVariable Long id) {
        DemandeCredit demande = demandeCreditService.trouverParId(id);
        demande.setStatut("Refusée");
        demandeCreditService.creerDemande(demande);
        return "redirect:/admin/demandes";
    }

    // ====================== PDF EXPORT ======================

    @GetMapping("/demandes/pdf")
    public void exporterPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=demandes.pdf");

        List<DemandeCredit> demandes = demandeCreditService.toutesLesDemandes();
        PdfExporter exporter = new PdfExporter(demandes);
        exporter.exporter(response);
    }
}
