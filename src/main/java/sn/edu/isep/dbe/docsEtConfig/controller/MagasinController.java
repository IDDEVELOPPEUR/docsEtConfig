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

//    @Operation(summary = "La récupération de  tous les magasins.",
//            description = "Cette URL retourne la liste de tous les magasins disponibles !")
//    //une autre manière  de le faire
//    @Operation(summary = ".....",description = ";...",
//    responses = {
//            @ApiResponse(responseCode = "NUMERO"),
//    ............
//    }
  //fin d'exemple  )
    @ApiResponses(value = {

    @ApiResponse(responseCode = "200", description = "Il s'agit de la liste des magasins trouvés !"),
    @ApiResponse(responseCode = "400",description = "Il n'y a pas de magasins au niveau de la liste des magasins !"),
    @ApiResponse(responseCode = "500",description = "Il y a une erreur interne !")
    })

    @GetMapping
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
    @PostMapping
    public ResponseEntity ajoutNouvelMagasin(@RequestBody Magasin magasin) {
        if (magasin.getId() == null) {
            return ResponseEntity.status(600).body("L'id est obligatoire");
        }  if (magasin.getNom() == null) {
            return ResponseEntity.status(601).body("Le nom est obligatoire");
        }  if (magasin.getAdresse() == null) {
            return ResponseEntity.status(602).body("L'adresse est obligatoire");
        }
        Optional<Magasin> tmp = magasinService.magasinParNom(magasin.getNom());
        if(tmp.isPresent()) {
            return ResponseEntity.status(603).body("Un magasin avec le nom"+magasin.getNom()+"exit déja");
        }
        magasinService.ajoutMagasin(magasin);
        return ResponseEntity.status(201).body(magasin);

    }
}