package activify.repo;

import activify.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User getUserByUsername(String username) {
        String hql = "FROM User WHERE name = :username";
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }


    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }
    }

    public User getUser(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.update(user);
        }
    }

    public void deleteUser(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.load(User.class, id);
            if (user != null) {
                session.delete(user);
            }
        }
    }
}