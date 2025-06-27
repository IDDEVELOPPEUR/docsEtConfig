package sn.edu.isep.dbe.docsEtConfig.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //ici l'id ne doit pas être nulle seulement !
    @Column(name = "id", nullable = false)
    private  Integer id;
    //ici le nom est unique et ne doit pas être null !
    @Column(name = "nom",unique=true ,nullable = false)
    private String nom;

    public Role(String nom) {
        this.nom = nom;
    }
}