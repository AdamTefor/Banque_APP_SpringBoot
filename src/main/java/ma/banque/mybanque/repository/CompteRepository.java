package ma.banque.mybanque.repository;

import ma.banque.mybanque.entity.Compte;
import ma.banque.mybanque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findByUser(User user);
}
