package habsida.spring.boot_security.demo.repositories;

import habsida.spring.boot_security.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByName(String name);
    List<Role> findByNameIn(List<String> names);
}
