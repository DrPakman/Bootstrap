package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> Index() {
        return userRepository.findAll();
    }

    public User getUserByUsername(int id) {
        return userRepository.findById(id).orElse(null);
    }
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }
    @Transactional
    public User updateUser (User user) {
        return userRepository.save(user);
    }
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
