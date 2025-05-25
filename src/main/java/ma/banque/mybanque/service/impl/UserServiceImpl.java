package ma.banque.mybanque.service.impl;

import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.repository.UserRepository;
import ma.banque.mybanque.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User ajouterUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> listerUsers() {
        return userRepository.findAll();
    }

    @Override
    public User trouverParId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User trouverParEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void supprimerUser(Long id) {
        userRepository.deleteById(id);
    }

}
