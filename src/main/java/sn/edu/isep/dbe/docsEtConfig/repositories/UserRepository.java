package sn.edu.isep.dbe.docsEtConfig.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sn.edu.isep.dbe.docsEtConfig.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

}