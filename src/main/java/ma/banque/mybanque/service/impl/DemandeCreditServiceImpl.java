package ma.banque.mybanque.service.impl;

import ma.banque.mybanque.entity.DemandeCredit;
import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.repository.DemandeCreditRepository;
import ma.banque.mybanque.service.DemandeCreditService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeCreditServiceImpl implements DemandeCreditService {

    private final DemandeCreditRepository demandeRepo;

    public DemandeCreditServiceImpl(DemandeCreditRepository demandeRepo) {
        this.demandeRepo = demandeRepo;
    }

    @Override
    public DemandeCredit creerDemande(DemandeCredit demande) {
        return demandeRepo.save(demande);
    }

    @Override
    public List<DemandeCredit> toutesLesDemandes() {
        return demandeRepo.findAll();
    }

    @Override
    public List<DemandeCredit> demandesParUser(User user) {
        return demandeRepo.findByUser(user);
    }

    @Override
    public DemandeCredit trouverParId(Long id) {
        return demandeRepo.findById(id).orElse(null);
    }

    @Override
    public void supprimerDemande(Long id) {
        demandeRepo.deleteById(id);
    }
}
