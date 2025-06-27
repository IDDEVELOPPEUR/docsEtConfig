package sn.edu.isep.dbe.docsEtConfig.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_token")
public class UserToken {
    @Id
    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne
    private User user;
    private Date notBefore;
    private Date expiresAt;

}