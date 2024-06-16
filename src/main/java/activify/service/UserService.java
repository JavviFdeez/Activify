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
        // Obtener el usuario con el nombre de usuario proporcionado
        User user = userRepository.getUserByUsername(username);

        // Verificar si se encontró un usuario con el nombre de usuario dado
        if (user != null) {
            // Verificar si la contraseña coincide
            if (user.getPassword().equals(password)) {
                // Las credenciales son válidas, devuelve el usuario
                return user;
            }
        }

        // Las credenciales no son válidas, devuelve null
        return null;
    }

    // Método para obtener el ID del usuario actualmente autenticado
    public int getCurrentUserId() {
        // Ejemplo ficticio: aquí obtienes el usuario actual desde la sesión o algún contexto de seguridad
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getId();
        } else {
            throw new IllegalStateException("No hay usuario autenticado o sesión activa.");
        }
    }

    // Método ficticio para obtener el usuario actual
    private User getCurrentUser() {
        // Este es un ejemplo simplificado
        return SessionManager.getCurrentUser();
    }

}