package activify.repo;

import activify.model.Activity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ActivityRepository {

    private final SessionFactory sessionFactory;

    public ActivityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Activity> getAllActivities() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT a FROM Activity a", Activity.class).getResultList();
        }
    }

    public void createActivity(Activity activity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(activity);
            session.getTransaction().commit();
        }
    }

    public Activity getActivity(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Activity.class, id);
        }
    }

    public void updateActivity(Activity activity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(activity);
            session.getTransaction().commit();
        }
    }

    public void deleteActivity(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Activity activity = session.get(Activity.class, id);
            if (activity != null) {
                session.delete(activity);
            }
            session.getTransaction().commit();
        }
    }
}
