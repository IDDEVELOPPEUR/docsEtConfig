package sn.edu.isep.dbe.docsEtConfig.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isep.dbe.docsEtConfig.entities.Droit;

import java.util.Optional;

public interface DroitRepository extends JpaRepository<Droit, Integer>{

    Optional<Droit> findByNom(String nom);
}
