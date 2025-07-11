package sn.edu.isep.dbe.docsEtConfig.service;

import org.springframework.stereotype.Service;
import sn.edu.isep.dbe.docsEtConfig.entities.Role;
import sn.edu.isep.dbe.docsEtConfig.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role>findAll(){
        return roleRepository.findAll();
    }
    public Optional<Role> findByNom(String nom){
        return roleRepository.findByNom(nom);
    }
}
