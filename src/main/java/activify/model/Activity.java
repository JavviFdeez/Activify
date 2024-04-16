package activify.model;

import activify.model.User;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Activity {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final StringProperty duration = new SimpleStringProperty();
    private final IntegerProperty elevation = new SimpleIntegerProperty();
    private final StringProperty sport = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty routeType = new SimpleStringProperty();

    public Activity(double distance, String duration, int elevation, String sport, LocalDate date, String title, String description, String routeType) {
        this.distance.set(distance);
        this.duration.set(duration);
        this.elevation.set(elevation);
        this.sport.set(sport);
        this.date.set(date);
        this.title.set(title);
        this.description.set(description);
        this.routeType.set(routeType);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public double getDistance() {
        return distance.get();
    }

    public void setDistance(double distance) {
        this.distance.set(distance);
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public int getElevation() {
        return elevation.get();
    }

    public void setElevation(int elevation) {
        this.elevation.set(elevation);
    }

    public IntegerProperty elevationProperty() {
        return elevation;
    }

    public String getSport() {
        return sport.get();
    }

    public void setSport(String sport) {
        this.sport.set(sport);
    }

    public StringProperty sportProperty() {
        return sport;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getRouteType() {
        return routeType.get();
    }

    public void setRouteType(String routeType) {
        this.routeType.set(routeType);
    }

    public StringProperty routeTypeProperty() {
        return routeType;
    }
}