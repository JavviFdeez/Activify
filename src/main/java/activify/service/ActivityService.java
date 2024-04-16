package activify.service;

import activify.model.Activity;
import activify.repo.ActivityRepository;

import java.util.List;

public class ActivityService {

    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public void createActivity(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }
        // Añade más validaciones según sea necesario
        activityRepository.createActivity(activity);
    }

    public Activity getActivity(long id) {
        return activityRepository.getActivity(id);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.getAllActivities();
    }

    public void updateActivity(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("La actividad no puede ser nula");
        }
        if (activityRepository.getActivity(activity.idProperty().getValue()) == null) {
            throw new IllegalArgumentException("La actividad no existe");
        }
        // Añade más validaciones según sea necesario
        activityRepository.updateActivity(activity);
    }


    public void deleteActivity(long id) {
        if (activityRepository.getActivity(id) == null) {
            throw new IllegalArgumentException("La actividad no existe");
        }
        activityRepository.deleteActivity(id);
    }
}
