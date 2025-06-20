package sn.edu.isep.dbe.docsEtConfig.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.edu.isep.dbe.docsEtConfig.entities.AuditRequest;
import sn.edu.isep.dbe.docsEtConfig.repositories.AuditRequestRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Component
public class AuditRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AuditRequestRepository auditRequestRepository;
    @Override
    protected void doFilterInternal(


            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String ipAdress=request.getRemoteAddr();
        String uri=request.getRequestURI();
        String method=request.getMethod();
        String userAgent=request.getHeader("User-Agent");

        System.out.println("ip "+ipAdress+" uri "+uri+" method "+method);
        AuditRequest auditRequest=AuditRequest.builder()
                .uri(uri)
                .ipAdress(ipAdress)
                .method(method)
                .navigateur(userAgent)
                .dateRequest(new Date())
                .build();
        auditRequestRepository.save(auditRequest);


        //on passe le filtre au fils pour que celui-ci puisse continuer à travailler avec les données de la requête
        //c'est aussi pour laisser passer les requetes qui ne sont pas autorisées !

            filterChain.doFilter(request,response);
            //retour controller
        auditRequest.setStatus(response.getStatus());
        auditRequestRepository.save(auditRequest);

    }
}
