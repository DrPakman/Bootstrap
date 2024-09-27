package habsida.spring.boot_security.demo.service;

import habsida.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    public List<User> Index();
    public User getUserByUsername(int id);
    public void createUser(User user);
    public User updateUser(User user);
    public void deleteUser(int id);
}
