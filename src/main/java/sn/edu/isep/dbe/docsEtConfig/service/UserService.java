package sn.edu.isep.dbe.docsEtConfig.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;
import sn.edu.isep.dbe.docsEtConfig.entities.User;
import sn.edu.isep.dbe.docsEtConfig.entities.UserToken;
import sn.edu.isep.dbe.docsEtConfig.entities.dto.LoginResponse;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserRepository;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserTokenRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    @Value("${magasin.auth.token.length}")
    private int tokenSize;

    @Value("${magasin.auth.token.caracteres}")
    private String caracteres;

    @Value("${magasin.auth.token.validite}")
    private int validite;

    //le constructeur
    public UserService(UserRepository userRepository, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
    }

//methode getUserByEmail permet de récupérer un utilisateur par son email
    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    //la methode login permet de vérifier si le login est valide ou non
    public LoginResponse login(String email, String password){

       Optional<User> userData =userRepository.findByEmail(email);
        //verification si le user est present
       if(userData.isPresent()){
           //recuperation l'objet user correspondant au login
           User user=userData.get();

           //comparaison du mot de passe avec celui stocké dans la base de données pour vérifier si le login est valide ou non
           if(user.getPassword().equals(password)){
               // creation d'une liste pour stocker les roles
               List<String> roles= new ArrayList<>();

               // creation d'une liste pour stocker les droits
               List<String> droits= new ArrayList<>();

               //on parcourt des roles de l'utilisateur(les droits et les roles sont inclus)
               for(Role role:user.getRoles()){

                   //récuperation nom du role dans la base de données
                   String nomRole=role.getNom();

                   //on vérifie si le nom du role commence par ROLE_ ou non,
                   // si oui, on ajoute le role dans la liste des roles
                   // si non, on l'ajoute dans la liste des droits
                   if (nomRole.startsWith("ROLE_")){
                       //on ajoute le nom du role dans la liste des roles
                       roles.add(nomRole);
                   }
                   else{
                       droits.add(nomRole);
                   }

               }
               //crée un nouveau token pour l'utilisateur et on le stocke dans la base de données'
                UserToken userToken=generateUserToken(user);



             return  LoginResponse.builder()
                     .email(user.getEmail())
                     .prenom(user.getPrenom())
                     .nom(user.getNom())
                     .token(userToken.getToken())
                     .droits(droits)
                     .roles(roles)
                     .dateExpiration(userToken.getExpiresAt())
                     .notBefore(userToken.getNotBefore())

                     .build();
           }


       }

        return null;
    }


    //methode generateUserToken permet de générer un token pour un utilisateur
    public UserToken generateUserToken(User user){

        while (true){
            //genere un token
            String token=generateToken();
            //recherche si le token existe en base de données
            Optional<UserToken> userTokenData=userTokenRepository.findById(token);
            //teste si le token n'existe pas en base de données
            if (userTokenData.isEmpty()){
                //le token n'existe pas et on crée le token
                Date now=new Date();
                //conversion nombre d'heures en millisecondes
                long nbMilliSeconds=validite*60*60*1000;

                //Generation date d'expiration en ajoutant le nombre de millisecondes à la date now
                Date expirationDate=new Date(now.getTime()+nbMilliSeconds);

                UserToken userToken=new UserToken();
                userToken.setToken(token);
                userToken.setUser(user);
                userToken.setNotBefore(now);
                userToken.setExpiresAt(expirationDate);
                userTokenRepository.save(userToken);
                return userToken;
            }
        }

    }

    public String generateToken(){

        //creation du builder pour créer une chaine de caractères
        // qui sera retournée à la fin de la méthode
        StringBuilder tokenBuilder=new StringBuilder();

        //boucle pour générer un nombre aléatoire de caractères
        // pour la chaine de caractères qui sera retournée à la fin de la méthode
        for (int i = 0; i < tokenSize; i++) {

            //on génère un nombre aléatoire entre 0 et caracteres.length pour obtenir un caractère aléatoire
            int randomIndex= (int)(Math.random()*caracteres.length());

            //on ajoute le caractère aléatoire à la chaine de caractères qui sera retournée à la fin de la méthode
            tokenBuilder.append(caracteres.charAt(randomIndex));
        }
        return tokenBuilder.toString();
    }



}
