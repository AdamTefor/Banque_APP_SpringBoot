package ma.banque.mybanque;

import ma.banque.mybanque.entity.User;
import ma.banque.mybanque.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MybanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybanqueApplication.class, args);
    }

    // 🧠 Insérer un ADMIN automatiquement
    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            if (userService.trouverParEmail("admin@bankati.com") == null) {
                User admin = new User();
                admin.setNom("Adam Tefor");
                admin.setEmail("admin@bankati.com");
                admin.setMotDePasse("admin123");
                admin.setRole("ADMIN");

                userService.ajouterUser(admin);
                System.out.println("✔ Utilisateur admin inséré avec succès");
            }
        };
    }
}
