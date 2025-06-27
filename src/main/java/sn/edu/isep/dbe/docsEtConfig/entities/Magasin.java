package sn.edu.isep.dbe.docsEtConfig.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//grâce à lombok, on peut creer facilement les getters et setters sans les écrire dans le code car ils seront générés au moment de la compilation.

@Setter
@Getter
@Entity
//pour @Table on l'utilise seulement si on veut changer le nom de la table pour que ça ne soit pas identique au nom de la classe car par défaut c'est le nom de la classe.
@Table(name = "magasin")
public class Magasin{
    // le generateValue permet de generer l'id de chaque objet et de faire une auto-incrementation.
    @Id
    //Schema est utilisé pour l'annotation des variables, mais aussi le nom de la classe, c'est-à-dire le nom du model
    @Schema(description = "identifiant du magasin")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //le @Column nullable false nous permet de dire que cette colonne ne doit pas être nulle.

    @Column(nullable = false)
    @Schema(description = "nom du magasin")
    private String nom;

    @Schema(description = "la description du magasin")
    @Column(nullable = false)
    private String adresse;

    private String telephone;

    private String description;

    @ManyToOne
    @JoinColumn(name = "cree_par",nullable = false)
    private User createur;

    @Override
    public String toString() {
        return "Magasin{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
