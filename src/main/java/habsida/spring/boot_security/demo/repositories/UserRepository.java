package habsida.spring.boot_security.demo.repositories;

import habsida.spring.boot_security.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
   Optional <User> findByUsername(String username);
}
