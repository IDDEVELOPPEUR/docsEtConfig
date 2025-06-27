package sn.edu.isep.dbe.docsEtConfig.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "prenom", nullable = false)
    private String prenom;
    @Column(name = "email", nullable = false,unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;


    private String adresse;
    //pour ne pas changer automatiquement les requetes a chaque chargement
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Droit> droits;

}