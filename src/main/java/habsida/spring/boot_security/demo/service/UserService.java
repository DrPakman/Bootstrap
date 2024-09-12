package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
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
    public void createUserWithRoles(User user, List<String> roleNames) {
        Set<Role> roles = roleNames.stream()
                .map(roleService::findRoleByName)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        entityManager.persist(user);
    }

    @Transactional
    public void updateUserWithRoles(int id, User updateUser, List<String> roleNames) {
        User userToBeUpdate = entityManager.find(User.class, id);
        if (userToBeUpdate != null) {
            userToBeUpdate.setUsername(updateUser.getUsername());
            userToBeUpdate.setLastname(updateUser.getLastname());
            userToBeUpdate.setAge(updateUser.getAge());
            userToBeUpdate.setEmail(updateUser.getEmail());
            userToBeUpdate.setPassword(updateUser.getPassword());

            Set<Role> roles = roleNames.stream()
                    .map(roleService::findRoleByName)
                    .collect(Collectors.toSet());
            userToBeUpdate.setRoles(roles);

            entityManager.merge(userToBeUpdate);
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
