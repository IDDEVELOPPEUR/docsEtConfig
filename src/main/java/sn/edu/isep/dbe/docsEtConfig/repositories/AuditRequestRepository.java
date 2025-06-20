package sn.edu.isep.dbe.docsEtConfig.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isep.dbe.docsEtConfig.entities.AuditRequest;

public interface AuditRequestRepository extends JpaRepository<AuditRequest, Long> {
}
