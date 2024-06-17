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
        activityRepository.createActivity(activity);
    }

    public List<Activity> getActivitiesByUserId(int userId) {
        return activityRepository.getActivitiesByUserId(userId);
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
        activityRepository.updateActivity(activity);
    }

    public void deleteActivity(long id) {
        activityRepository.deleteActivity(id);
    }
}