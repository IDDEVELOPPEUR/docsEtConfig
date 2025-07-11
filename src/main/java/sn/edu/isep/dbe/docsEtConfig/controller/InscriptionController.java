package sn.edu.isep.dbe.docsEtConfig.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.edu.isep.dbe.docsEtConfig.entities.Droit;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;
import sn.edu.isep.dbe.docsEtConfig.entities.User;
import sn.edu.isep.dbe.docsEtConfig.entities.dto.InscriptionRequest;
import sn.edu.isep.dbe.docsEtConfig.service.DroitService;
import sn.edu.isep.dbe.docsEtConfig.service.RoleService;
import sn.edu.isep.dbe.docsEtConfig.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscription")
public class InscriptionController {

    private final RoleService roleService;
    private final DroitService droitService;
    private final UserService userService;

    public InscriptionController(RoleService roleService, DroitService droitService, UserService userService) {
        this.roleService = roleService;
        this.droitService = droitService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> inscrire(@RequestBody InscriptionRequest request){
        System.out.println("request = "+request);
        List<Role>roles=request.getRoles().stream()
                .map(roleName->roleService.findByNom(roleName).get())
                .toList();

        List<Droit> droits=request.getDroits().stream()
                .map(droitName->droitService.findByNom(droitName).get())
                .toList();
        User user= User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .password(request.getPassword())
                .prenom(request.getPrenom())
                .adresse(request.getAdresse())
                .roles(roles)
                .droits(droits)
                .build();
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
