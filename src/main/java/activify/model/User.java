package activify.model;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")  // Añadir campo para la contraseña
    private String password;     // Nuevo campo para almacenar la contraseña

    // Constructor, getters y setters

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;  // Inicializar el campo de la contraseña
    }

    public User(String username, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;  // Inicializar el campo de la contraseña
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to add a sport activity to the user
    public void addActivity(Activity activity) {
        //this.SportActivity.add(activity);
    }

    // Method to remove a sport activity from the user
    public void removeActivity(Activity activity) {
        //this.SportActivity.remove(activity);
    }
}