package sn.edu.isep.dbe.docsEtConfig.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}