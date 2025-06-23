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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class DocsAndConfigSecurityFilter extends OncePerRequestFilter {
    //les loggers permettent de gérer les logs de manière différente selon le niveau de détail de ce log.
    //un log est un message qui permet de décrire un événement qui se passe dans le programme.
    //un filter est un composant qui permet de filtrer les requêtes qui ne sont pas autorisées !

    private static final Logger logger= LoggerFactory.getLogger("MySecurituFilter");

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
        logger.error("requete sur l'url : "+request.getRequestURI());
        List<GrantedAuthority> roles=List.of(
                new SimpleGrantedAuthority("SuppMag"),//droit
                new SimpleGrantedAuthority("ROLE_ADMIN"),//un role
                new SimpleGrantedAuthority("ROLE_MANAGER"),//un role
                new SimpleGrantedAuthority("ROLE_USER")
        );
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                "abdou fall","afall@isepat.edu.sn",roles
        ));
        filterChain.doFilter(request,response);
    }

}
