package sn.edu.isep.dbe.docsEtConfig.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;
import sn.edu.isep.dbe.docsEtConfig.entities.User;
import sn.edu.isep.dbe.docsEtConfig.entities.dto.InscriptionRequest;
import sn.edu.isep.dbe.docsEtConfig.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscription")
public class InscriptionController {

    private final RoleService roleService;

    public InscriptionController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public EntityResponse<?>inscrire( @RequestBody InscriptionRequest request){
        List<Role>roles=request.getRoles().stream()
                .map(roleName->roleService.findByNom(roleName).get())
                .toList();

        User user= User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .password(request.getPassword())
                .prenom(request.getPrenom())
                .adresse(request.getAdresse())



                .build();
    }
}
