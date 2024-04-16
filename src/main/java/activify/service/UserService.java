package activify.service;

import activify.model.User;
import activify.repo.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (userRepository.getUserByUsername(user.getName()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
        }
        userRepository.createUser(user);
    }

    public User getUser(long id) {
        return userRepository.getUser(id);
    }

    public void updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (userRepository.getUser(user.getId()) == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        userRepository.updateUser(user);
    }

    public void deleteUser(long id) {
        if (userRepository.getUser(id) == null) {
            throw new IllegalArgumentException("El usuario no existe");
        }
        userRepository.deleteUser(id);
    }

    public User login(String username, String password) {
        // Logic to verify credentials in the database
        // Return the user if credentials are valid, otherwise return null
        return null; // Here would go the logic to check in the database
    }
}