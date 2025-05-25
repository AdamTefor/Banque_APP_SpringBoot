package ma.banque.mybanque.service.impl;

import ma.banque.mybanque.entity.Compte;
import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.repository.CompteRepository;
import ma.banque.mybanque.service.CompteService;
import org.springframework.stereotype.Service;

@Service
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;

    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    @Override
    public Compte creerCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public Compte trouverParId(Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    @Override
    public Compte trouverParUser(User user) {
        return compteRepository.findByUser(user);
    }

    @Override
    public void supprimerCompte(Long id) {
        compteRepository.deleteById(id);
    }
    public void enregistrerCompte(Compte compte) {
        compteRepository.save(compte);
    }

}
