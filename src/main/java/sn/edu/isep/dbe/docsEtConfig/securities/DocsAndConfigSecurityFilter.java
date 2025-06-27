package sn.edu.isep.dbe.docsEtConfig.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.edu.isep.dbe.docsEtConfig.entities.Droit;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;
import sn.edu.isep.dbe.docsEtConfig.entities.User;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class DocsAndConfigSecurityFilter extends OncePerRequestFilter {
    //les loggers permettent de gérer les logs de manière différente selon le niveau de détail de ce log.
    //un log est un message qui permet de décrire un événement qui se passe dans le programme.
    //un filter est un composant qui permet de filtrer les requêtes qui ne sont pas autorisées !

    private static final Logger logger= LoggerFactory.getLogger("MySecurituFilter");
    private final UserRepository userRepository;

    public DocsAndConfigSecurityFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)throws ServletException, IOException
    {
        logger.debug("execution méthode doFilterInternal de la classe DocsAndConfigSecurityFilter");
        logger.info("requête sur l'url : "+request.getRequestURI());
        String userAgent =request.getHeader("User-Agent").toLowerCase();
        if(userAgent.contains("win")){

            logger.warn("l'utilisateur est sur une machine windows");
        }
        // le login et le password sont des paramètres qui sont envoyés dans la requête pour la connexion.
        String login=request.getHeader("email");
        String password=request.getHeader("password");
        Optional<User> userData=userRepository.findByEmail(login);
        logger.info("l'utilisateur est connecté avec le login : "+login);
        logger.info("l'utilisateur est connecté avec le password : "+password);
        if (userData.isPresent()){
            logger.info("l'utilisateur est présent ");

            List<GrantedAuthority> authorities=new ArrayList<>();
            User user=userData.get();
            if(user.getPassword().equals(password)){
                logger.warn("le mot de passe est incorrect");
                for(Role role:user.getRoles()){
                    authorities.add(new SimpleGrantedAuthority(role.getNom()));
                }
                for (Droit droit:user.getDroits()){
                    authorities.add(new SimpleGrantedAuthority(droit.getNom()));
                }
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user,user.getEmail(),authorities));
            }
        }
        filterChain.doFilter(request,response);
    }
// userRepository
}
