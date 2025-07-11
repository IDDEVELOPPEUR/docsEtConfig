package sn.edu.isep.dbe.docsEtConfig.securities;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class DocsConfigSecurity {

    private final DocsAndConfigSecurityFilter docsAndConfigSecurityFilter;
    public DocsConfigSecurity(DocsAndConfigSecurityFilter docsAndConfigSecurityFilter) {
        this.docsAndConfigSecurityFilter = docsAndConfigSecurityFilter;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequest)->{
            authorizeRequest
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/inscription").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
//                    .requestMatchers("/api/v1/**").hasRole("USER")
                    .requestMatchers(HttpMethod.GET,"/api/v1/magasins").hasRole("USER")
                    //SI ON VEUX QUE LE USER ET L'ADMIN PUISSENT AJOUTER ,VOILA LE CODE SUIVANT
//                    .requestMatchers(HttpMethod.POST,"/api/v1/magasins").hasAnyRole("ADMIN","USER")
                    .requestMatchers(HttpMethod.POST,"/api/v1/magasins").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.PUT,"/api/v1/magasins").hasRole("MANAGER")
                    .requestMatchers(HttpMethod.DELETE,"/api/v1/magasins/**").hasAuthority("suppMag")
                    .anyRequest().authenticated();
        })
                .sessionManagement(session->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                })
                //addFilterBefore permet de
                .addFilterBefore(docsAndConfigSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
