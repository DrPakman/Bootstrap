package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;
    private final RoleService roleService;

    public UserService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    public List<User> index() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();

    }

    @Transactional(readOnly = true)
    public User show(int id) {
        return entityManager.find(User.class, id);

    }

    @Transactional
    public void save(User user, List<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleService.findRoleByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        user.setRoles(roles);
        entityManager.persist(user);
    }

    @Transactional
    public void update(int id, User updateUser) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setUsername(updateUser.getUsername());
            user.setLastname(updateUser.getLastname());
            user.setAge(updateUser.getAge());
            user.setEmail(updateUser.getEmail());
            user.setPassword(updateUser.getPassword());
            entityManager.merge(user);
        }

    }

    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}



