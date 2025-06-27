package sn.edu.isep.dbe.docsEtConfig.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isep.dbe.docsEtConfig.entities.UserToken;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {
}