package sn.edu.isep.dbe.docsEtConfig.service;

import org.springframework.stereotype.Service;
import sn.edu.isep.dbe.docsEtConfig.entities.Droit;
import sn.edu.isep.dbe.docsEtConfig.repositories.DroitRepository;

import java.util.Optional;

@Service
public class DroitService {
    private final DroitRepository droitRepository;

    public DroitService(DroitRepository droitRepository) {
        this.droitRepository = droitRepository;
    }

    public Optional<Droit> findByNom(String nom){
        return droitRepository.findByNom(nom);
    }
}
