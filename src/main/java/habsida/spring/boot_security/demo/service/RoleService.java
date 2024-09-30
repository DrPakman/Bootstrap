package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
//
//    public List<Role> findAllRoles() {
//        return roleRepository.findAll();
//    }

    public List<Role> findRolesByNames(List<String> roleNames) {
        return roleRepository.findByRoleNameIn(roleNames);
    }
}
