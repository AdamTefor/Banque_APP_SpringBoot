package ma.banque.mybanque.service;

import ma.banque.mybanque.entity.User;

import java.util.List;

public interface UserService {
    User ajouterUser(User user);
    List<User> listerUsers();
    User trouverParId(Long id);
    User trouverParEmail(String email);
    void supprimerUser(Long id);
}
