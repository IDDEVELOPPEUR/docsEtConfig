package sn.edu.isep.dbe.docsEtConfig.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "droit")
public class Droit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "nom", nullable = false,unique = true)
    private String nom;

    public Droit(String nom) {
        this.nom = nom;
    }
}