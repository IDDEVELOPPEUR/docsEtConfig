package sn.edu.isep.dbe.docsEtConfig.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isep.dbe.docsEtConfig.entities.Magasin;

import java.util.Optional;

public interface MagasinRepository extends JpaRepository<Magasin, Integer> {
    //ce sont les méthodes génériques qui sont créér par spring-boot et il suffit de les cré
    Optional<Magasin> findByNom(String nom);
}
