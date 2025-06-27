package sn.edu.isep.dbe.docsEtConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import sn.edu.isep.dbe.docsEtConfig.entities.Magasin;
import sn.edu.isep.dbe.docsEtConfig.entities.User;
import sn.edu.isep.dbe.docsEtConfig.repositories.MagasinRepository;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserRepository;

@Component
@Order(2)
//pour dire que ce fichier doit s'executer que seuelement au niveau du profile dev on met la ligne suivante
//si on veut que ça s'execute sur les profiles dev et test(test n'existe pas )
@Profile({"dev","test"})
public class InitMagasin implements CommandLineRunner {
// ici on a pas besoin de @Autowired car on a un constructeur
    // le final veut dire, on ne peut plus le modifier une fois initialisée
    private final MagasinRepository magasinRepository;
    private final UserRepository userRepository;

    //ces lignes suivantes font la correspondance des données récupérées au niveau de : application-dev.properties et les affecter aux variables déclarées de manières respective.
    @Value("${magasin.dfe.nom}")
    private String nomMagasin;
    @Value("${magasin.dfe.adresse}")
    private String adresseMagasin;
    @Value("${magasin.dfe.telephone}")
    private String telMagasin;
    @Value("${magasin.gen.nombre}")
    private Integer nombreMagasin;

    //le constructeur
    public InitMagasin(MagasinRepository magasinRepository, UserRepository userRepository) {
        this.magasinRepository = magasinRepository;
        this.userRepository = userRepository;
    }
//la méthode implémentée
    @Override
    public void run(String... args) throws Exception {
        System.out.println("###############################################");
        System.out.println("Initialisation des données de magasin");
        Magasin mag = new Magasin();
        mag.setNom(nomMagasin);
        mag.setAdresse(adresseMagasin);
        mag.setTelephone(telMagasin);
        User user=userRepository.findById(1).get();
        mag.setCreateur(user);

        //on fait une boucle qui crée 20 magasins avec comme nombre limite la variable nombreMagasin
        magasinRepository.save(mag);
        for (int i = 1; i < nombreMagasin+1; i++) {
            Magasin m = new Magasin();
            m.setNom("magasin" + i);
            m.setAdresse("adresse" + i);
            m.setTelephone("tel" + i);
            m.setCreateur(user);
            magasinRepository.save(m);

        }

    }
}
