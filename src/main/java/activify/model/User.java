package activify.model;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    // Constructor
    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters and setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to add a sport activity to the user
    public void addActivity(Activity activity) {
        // Add your logic here to associate the activity with the user
    }

    // Method to remove a sport activity from the user
    public void removeActivity(Activity activity) {
        // Add your logic here to disassociate the activity from the user
    }
}