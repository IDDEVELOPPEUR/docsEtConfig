package sn.edu.isep.dbe.docsEtConfig.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import sn.edu.isep.dbe.docsEtConfig.entities.Magasin;
import sn.edu.isep.dbe.docsEtConfig.service.MagasinService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/magasins")
@Tag(name = "Gestion des magasins", description = "C'est un API de gestion des magasins !")
public class MagasinController {
    private final MagasinService magasinService;
    private final ContentNegotiatingViewResolver contentNegotiatingViewResolver;

    public MagasinController(MagasinService magasinService, ContentNegotiatingViewResolver contentNegotiatingViewResolver) {
        this.magasinService = magasinService;
        this.contentNegotiatingViewResolver = contentNegotiatingViewResolver;
    }
    @ApiResponses(value = {

    @ApiResponse(responseCode = "200", description = "Il s'agit de la liste des magasins trouvés !"),
    @ApiResponse(responseCode = "400",description = "Il n'y a pas de magasins au niveau de la liste des magasins !"),
    @ApiResponse(responseCode = "500",description = "Il y a une erreur interne !")
    })

    @GetMapping
    @Operation(summary = "la liste des magasins",description = "Cette URL permet de retourner la liste de tous les magasins disponibles !",

    responses = {
            @ApiResponse(responseCode = "200", description = "Il s'agit de la liste des magasins trouvés !"),
            @ApiResponse(responseCode = "400",description = "Il n'y a pas de magasins au niveau de la liste des magasins !"),
            @ApiResponse(responseCode = "500",description = "Il y a une erreur interne !")

    })
    public List<Magasin> getMagasins() {
        return magasinService.getAllMagasins();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Détails d'un magasin",
            description = "Cette URL permet de retourner un magasin unique identifié par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Il y a une erreur interne !"),
            @ApiResponse(responseCode = "404", description = "Le magasin n'a pas été trouvé !"),
            @ApiResponse(responseCode = "200", description = "Le magasin a été trouvé ! ",

            content=@Content(
                    mediaType = "application/json",
                    schema=@Schema(implementation = Magasin.class),
                    examples ={@ExampleObject("{\n" +
                            "  \"id\": 1,\n" +
                            "  \"nom\": \"OUS-mag\",\n" +
                            "  \"adresse\": \"Diamniadio\",\n" +
                            "  \"telephone\": \"77 9999 98 43\",\n" +
                            "  \"description\": \"c'est un magasin de ousseynou\"\n" +
                            "}")}
            )
            )
    })
    public ResponseEntity getMagasinById(
            @Parameter(description = "L'identifiant (id) du magasin à rechercher.")
            @PathVariable int id) {
        Optional<Magasin> magasin = magasinService.getMagasinById(id);
        if (magasin.isPresent()) {
            return ResponseEntity.ok(magasin.get());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(
            summary = "Ajouter un magasin",
            description = "permet d'ajouter un magasin",
            responses={
                    @ApiResponse(responseCode = "500", description = "Il y a une erreur interne  au niveau du serveur!"),
                    @ApiResponse(responseCode = "404", description = "Le magasin n'a pas été ajouté !"),
                    @ApiResponse(responseCode = "200", description = "Le magasin est ajouté ! ")}
    )
    @PostMapping
    public ResponseEntity ajoutNouvelMagasin(@RequestBody Magasin magasin) {
//        if (magasin.getId() == null) {
//            return ResponseEntity.status(600).body("L'id est obligatoire");
  //      }
        if (magasin.getNom() == null) {
            return ResponseEntity.status(601).body("Le nom est obligatoire");
        }  if (magasin.getAdresse() == null) {
            return ResponseEntity.status(602).body("L'adresse est obligatoire");
        }
        Optional<Magasin> tmp = magasinService.magasinParNom(magasin.getNom());
        if(tmp.isPresent()) {
            return ResponseEntity.status(603).body("Un magasin avec le nom"+magasin.getNom()+"exit déja");
        }
        magasinService.ajoutMagasin(magasin);
        return ResponseEntity.status(201).body("le magasin suivant a été ajouté avec succes! "+magasin.getNom()+"\n"+magasin.getAdresse()+"\n"+magasin.getTelephone()+"\n"+magasin.getDescription());

    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Modifier un magasin",
            description = "elle nous permet de modifier un magasin dont id (l'identifiant) est précisée !",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le magasin est mise à jour avec succes !",
                          content=  @Content(
                                    mediaType = "application/json",
                                    schema=@Schema(implementation = Magasin.class))
                    ),
                    @ApiResponse(responseCode = "400",description = "Les données ne sont pas valides !"),
                    @ApiResponse(responseCode = "404",description = "Le magasin avec cette id n'a pas été trouvée !"),
                    @ApiResponse(responseCode = "404",description = "Désolé, il y a eu une erreur interne !")

            }

    )
    public ResponseEntity modifierMagasin(@Parameter(description = "l'identifiant du magasin à modifier !")@PathVariable Integer id,@RequestBody Magasin magasin){

        Optional<Magasin> magasinEx=magasinService.getMagasinById(id);

        if (!magasinEx.isPresent()){
            return ResponseEntity.notFound().build();
        }

        //je vérifie si le nom est renseigné
        if (magasin.getNom()==null){
            return ResponseEntity.status(601).body("le nom doit être renseigner");
        }

        //je vérifie si le nom est renseigné
        if (magasin.getAdresse()==null){
            return ResponseEntity.status(602).body("L'adresse doit être renseigner");
        }

        magasin.setId(id);
        magasinService.ajoutMagasin(magasin);
        return ResponseEntity.ok(magasin);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un magasin",
            description = "cette methode permet de supprimer un magasin dont l'id est précisé.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Le magasin a été supprimé avec succès."),
                    @ApiResponse(responseCode = "404", description = "Le magasin avec cette id n'a pas été trouvé."),
                    @ApiResponse(responseCode = "500", description = "Désolé, il y a eu une erreur interne.")
            }
    )
    public ResponseEntity supprimerMagasin(@Parameter(description = "L'identifiant du magasin à supprimer") @PathVariable Integer id) {
        try {
            Optional<Magasin> magasinEx = magasinService.getMagasinById(id);
            if (!magasinEx.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            magasinService.supprimerMagasin(magasinEx.get());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Pour gérer les erreurs internes de manière global
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}