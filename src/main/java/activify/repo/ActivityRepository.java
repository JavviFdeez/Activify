package activify.repo;

import activify.model.Activity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class ActivityRepository {

    private final SessionFactory sessionFactory;

    public ActivityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Activity> getAllActivities() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Activity", Activity.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle error gracefully in your application
        }
    }

    public void createActivity(Activity activity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(activity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear la actividad", e);
        }
    }

    public Activity getActivity(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Activity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle error gracefully in your application
        }
    }

    public void updateActivity(Activity activity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(activity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar la actividad", e);
        }
    }

    public void deleteActivity(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Activity activity = session.get(Activity.class, id);
            if (activity != null) {
                session.delete(activity);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar la actividad", e);
        }
    }
}