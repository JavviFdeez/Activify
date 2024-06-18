package activify.repo;

import activify.model.Activity;
import activify.util.SessionFactoryUtil;
import org.hibernate.*;

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

    public List<Activity> getActivitiesByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Activity> query = session.createQuery("FROM Activity WHERE user.id = :userId", Activity.class);
            query.setParameter("userId", userId);
            List<Activity> activities = query.list();

            for (Activity activity : activities) {
                // Inicializar la propiedad user
                Hibernate.initialize(activity.getUser());
            }

            return activities;
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

    public void deleteActivity(int id) {
        Session session = sessionFactory.openSession();

        try {
            Transaction tx = session.beginTransaction();

            // Operaciones con la sesión (cargar, modificar, eliminar entidades)
            Activity activity = session.load(Activity.class, id);
            session.delete(activity);

            tx.commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar la actividad", e);
        } finally {
            session.close();
        }
    }
}