package sn.edu.isep.dbe.docsEtConfig.service;

import org.springframework.stereotype.Service;
import sn.edu.isep.dbe.docsEtConfig.entities.Magasin;
import sn.edu.isep.dbe.docsEtConfig.repositories.MagasinRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MagasinService {
    private final MagasinRepository magasinRepository;

    public MagasinService(MagasinRepository magasinRepository) {
        this.magasinRepository = magasinRepository;
    }
    public List<Magasin> getAllMagasins() {
        return magasinRepository.findAll();
    }
    //optional permet de contenir l'objet pour une verification de son existance au niveau du controlleur pour voir si l'objet dont on cherche l'id existe ou pas
    public Optional<Magasin> getMagasinById(int id) {
        return magasinRepository.findById(id);
    }
    public Magasin ajoutMagasin(Magasin magasin) {
        //verifier le nom....
        return magasinRepository.save(magasin);
    }
    public Magasin modifierMagasin(Magasin magasin) {
        //verifier avant
        return magasinRepository.save(magasin);

    }
    public void supprimerMagasin(Magasin magasin) {
        magasinRepository.delete(magasin);
    }
//méthode pour rechercher un magasin par son nom !
    public Optional<Magasin> magasinParNom(String nom) {
        return magasinRepository.findByNom(nom);
    }
}
