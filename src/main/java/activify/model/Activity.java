package activify.model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "distance")
    private double distance;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "elevation")
    private int elevation;

    @Column(name = "sport")
    private String sport;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "title")
    private String title;

    @Column(name = "user_id")
    private int userId;

    // Propiedades para JavaFX
    @Transient
    private final IntegerProperty idProperty = new SimpleIntegerProperty();

    @Transient
    private final DoubleProperty distanceProperty = new SimpleDoubleProperty();

    @Transient
    private final StringProperty durationProperty = new SimpleStringProperty();

    @Transient
    private final IntegerProperty elevationProperty = new SimpleIntegerProperty();

    @Transient
    private final StringProperty sportProperty = new SimpleStringProperty();

    @Transient
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();

    @Transient
    private final StringProperty titleProperty = new SimpleStringProperty();

    public Activity() {
        // Constructor por defecto requerido por JPA
    }

    public Activity(double distance, Time duration, int elevation, String sport, LocalDate date, String title, int userId) {
        this.distance = distance;
        this.duration = duration;
        this.elevation = elevation;
        this.sport = sport;
        this.date = date;
        this.title = title;
        this.userId = userId;
    }

    // Getters y setters para JPA
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Métodos para JavaFX
    public IntegerProperty idProperty() {
        return idProperty;
    }

    public DoubleProperty distanceProperty() {
        return distanceProperty;
    }

    public StringProperty durationProperty() {
        return durationProperty;
    }

    public IntegerProperty elevationProperty() {
        return elevationProperty;
    }

    public StringProperty sportProperty() {
        return sportProperty;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return dateProperty;
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }
}