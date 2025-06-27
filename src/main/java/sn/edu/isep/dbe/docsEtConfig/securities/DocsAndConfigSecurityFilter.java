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
import sn.edu.isep.dbe.docsEtConfig.entities.UserToken;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserRepository;
import sn.edu.isep.dbe.docsEtConfig.repositories.UserTokenRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class DocsAndConfigSecurityFilter extends OncePerRequestFilter {
    //les loggers permettent de gérer les logs de manière différente selon le niveau de détail de ce log.
    //un log est un message qui permet de décrire un événement qui se passe dans le programme.
    //un filter est un composant qui permet de filtrer les requêtes qui ne sont pas autorisées !

    private static final Logger logger= LoggerFactory.getLogger("MySecurituFilter");
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    public DocsAndConfigSecurityFilter(UserRepository userRepository, UserTokenRepository userTokenRepository) {
        this.userRepository = userRepository;
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)throws ServletException, IOException
    {
        logger.info("récuperation entete d'authentification");
        String authorization=request.getHeader("Authorization");
        logger.info("teste si l'entete existe et commence par Bearer");
        if (authorization!=null && authorization.startsWith("Bearer ")){
            logger.info("récuperatin du token ");
            String token=authorization.substring(7);
            logger.info("recupere userToken correspondant en base");
            Optional<UserToken> userTokenData=userTokenRepository.findById(token);
            logger.info("teste si le userToken existe");
            if (userTokenData.isPresent()){
                logger.info("le userToken existe et on va le valider");
                UserToken userToken=userTokenData.get();
                Date now=new Date();
                logger.info("verifie si la date d'aujourd'hui entre la date de creation et la date de validation");
                if (userToken.getExpiresAt().after(now) && userToken.getNotBefore().before(now)){
                    User user=userToken.getUser();
                    String login=user.getEmail();

                    List<GrantedAuthority> authorities=new ArrayList<>();
                    for (Role role:user.getRoles()){
                        authorities.add(new SimpleGrantedAuthority(role.getNom()));
                    }
                    for (Droit droit:user.getDroits()){
                        authorities.add(new SimpleGrantedAuthority(droit.getNom()));
                    }
                   SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,login,authorities));
                }

            }

        }


        filterChain.doFilter(request,response);
    }
// userRepository
}
