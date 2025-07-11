package sn.edu.isep.dbe.docsEtConfig.entities.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InscriptionRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;


    private String adresse;
    private List<String> roles;
    private List<String> droits;
}
