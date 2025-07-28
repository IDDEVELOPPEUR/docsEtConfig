package sn.edu.isep.dbe.docsEtConfig.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sn.edu.isep.dbe.docsEtConfig.entities.Droit;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;
import sn.edu.isep.dbe.docsEtConfig.entities.User;
import sn.edu.isep.dbe.docsEtConfig.repositories.DroitRepository;
import sn.edu.isep.dbe.docsEtConfig.repositories.RoleRepository;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserRepository;

import java.util.List;

@Component
@Profile({"dev","test"})
@Order(1)
public class InitUser implements CommandLineRunner {
    private final RoleRepository roleRepository;
  private final  DroitRepository droitRepository;
    private final UserRepository userRepository;

    public InitUser(DroitRepository droitRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.droitRepository = droitRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("###############################################");
        System.out.println("Initialisation des données de l'utilisateur");
        Droit creerMagasin=new Droit("creerMag");
        Droit creerUser=new Droit("creerUser");
        Droit SuppUser=new Droit("suppUser");
        Droit SuppMagasin=new Droit("suppMag");
       droitRepository.save(creerMagasin);
       droitRepository.save(creerUser);
       droitRepository.save(SuppUser);
       droitRepository.save(SuppMagasin);




       System.out.println("###############################################");
       System.out.println("Les droits ont bien été créés");

        Role userRole=new Role("ROLE_USER");
        Role adminRole=new Role("ROLE_ADMIN");
        Role managerRole=new Role("ROLE_MANAGER");

        roleRepository.save(userRole);
        roleRepository.save(adminRole);
        roleRepository.save(managerRole);


        System.out.println("###############################################");
        System.out.println("Les roles ont bien été créés");

        User abdou=User.builder()
                .prenom("abdou")
                .nom("fall")
                .email("ab@gmail.com")
                .password("faller")
                .roles(List.of(adminRole))
                .build();

        User khoudia=User.builder()
                .prenom("Ibrahima")
                .nom("Diallo")
                .adresse("Dakar").password("passer").email("i@passer.com")
                .roles(List.of(userRole,managerRole))
                .droits(List.of(creerMagasin,SuppMagasin))
                .build();


        User fallou=User.builder()
                .prenom("fallou")
                .nom("diallo")
                .adresse("Saint-Louis")
                .email("diafallou@exemple.com")
                .password("fallou#2014")
                .roles(List.of(userRole))
                .build();

    userRepository.save(khoudia);
    userRepository.save(abdou);
    userRepository.save(fallou);

    System.out.println("###############################################");
    System.out.println("Les utilisateurs ont bien été créés");
    }

}
