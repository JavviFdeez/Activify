package activify.javvifdez.com.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sport_activities")
public class SportActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distance")
    private double distance;

    @Column(name = "duration")
    private int duration;

    @Column(name = "elevation")
    private int elevation;

    @Column(name = "sport")
    private String sport;

    @Column(name = "activity_date")
    private Date activityDate;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor por defecto
    public SportActivity() {}

    // Constructor con parámetros
    public SportActivity(double distance, int duration, int elevation, String sport, Date activityDate,
                         String title, User user) {
        this.distance = distance;
        this.duration = duration;
        this.elevation = elevation;
        this.sport = sport;
        this.activityDate = activityDate;
        this.title = title;
        this.user = user;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
