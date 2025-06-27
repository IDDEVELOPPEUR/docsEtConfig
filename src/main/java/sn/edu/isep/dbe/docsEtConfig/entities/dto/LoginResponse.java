package sn.edu.isep.dbe.docsEtConfig.entities.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String email;
    private String token;
    private String prenom;
    private String nom;
    private List<String> roles;
    private List<String> droits;
    private Date dateExpiration;
    private Date notBefore;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", roles=" + roles +
                ", droits=" + droits +
                ", dateExpiration=" + dateExpiration +
                ", notBefore=" + notBefore +
                '}';
    }
}
