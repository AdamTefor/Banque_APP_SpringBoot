package ma.banque.mybanque.repository;

import ma.banque.mybanque.entity.DemandeCredit;
import ma.banque.mybanque.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeCreditRepository extends JpaRepository<DemandeCredit, Long> {
    List<DemandeCredit> findByUser(User user);
}
