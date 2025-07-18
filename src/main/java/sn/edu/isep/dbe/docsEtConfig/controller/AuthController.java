package sn.edu.isep.dbe.docsEtConfig.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.edu.isep.dbe.docsEtConfig.entities.dto.LoginRequest;
import sn.edu.isep.dbe.docsEtConfig.entities.dto.LoginResponse;
import sn.edu.isep.dbe.docsEtConfig.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){

        //nouvel ajout
        System.out.println("##########  Login ! ########");
        System.out.println("Le login est : "+loginRequest.getEmail());
        System.out.println("Le mot de passe est : "+loginRequest.getPassword());
        //fin nouvel ajout

        LoginResponse loginResponse=userService.login(loginRequest.getEmail(),loginRequest.getPassword());

        //nouvel ajout
        System.out.println("loginReponse ="+loginResponse);
        //fin nouvel ajout

        if(loginResponse==null){
            return ResponseEntity.status(450).body("Le login ou le mot de passe est incorrect !");
        }else {
            return ResponseEntity.ok(loginResponse);
        }
    }
}
