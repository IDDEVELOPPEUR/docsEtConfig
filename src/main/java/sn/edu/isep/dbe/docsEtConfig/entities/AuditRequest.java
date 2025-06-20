package sn.edu.isep.dbe.docsEtConfig.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "audit_request")
public class AuditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAdress;
    private String uri;
    private String method;
    private Date dateRequest;
    private String navigateur;
    private Integer status;
}
