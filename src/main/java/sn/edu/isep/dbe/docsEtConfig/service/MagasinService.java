package sn.edu.isep.dbe.docsEtConfig.service;

import org.springframework.stereotype.Service;
import sn.edu.isep.dbe.docsEtConfig.entities.Magasin;
import sn.edu.isep.dbe.docsEtConfig.entities.dto.MagasinDto;
import sn.edu.isep.dbe.docsEtConfig.repositories.MagasinRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MagasinService {
    private final MagasinRepository magasinRepository;

    public MagasinService(MagasinRepository magasinRepository) {
        this.magasinRepository = magasinRepository;
    }

    public List<MagasinDto> getAllMagasins() {
        return magasinRepository.findAll().stream()
                .map(MagasinDto::fromMagasin)
                .toList();
        //équivalent aux instructions suivantes
//        List<MagasinDto> resultat= new ArrayList<>();
//        List<Magasin> magasins = magasinRepository.findAll();
//        for (Magasin magasin : magasins) {
//            MagasinDto tmp= MagasinDto.fromMagasin(magasin);
//            resultat.add(tmp);
//        }
//        return resultat;
    }

    //optional permet de contenir l'objet pour une verification de son existance au niveau du controlleur pour voir si l'objet dont on cherche l'id existe ou pas
    public Optional<MagasinDto> getMagasinById(int id) {
        Optional<Magasin>  magasin = magasinRepository.findById(id);
        if (magasin.isEmpty()) {
            return Optional.empty();
        }

        MagasinDto res= MagasinDto.fromMagasin(magasin.get());
        return Optional.of(res);
    }

    public Magasin ajoutMagasin(Magasin magasin) {
        //verifier le nom....
        return magasinRepository.save(magasin);
    }
    public Magasin modifierMagasin(Magasin magasin) {
        //verifier avant
        return magasinRepository.save(magasin);

    }
    public void supprimerMagasinById(Integer magasinId) {
        magasinRepository.deleteById(magasinId);
    }
//méthode pour rechercher un magasin par son nom !
    public Optional<Magasin> magasinParNom(String nom) {
        return magasinRepository.findByNom(nom);
    }
}
