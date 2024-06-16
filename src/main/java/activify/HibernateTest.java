package activify;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import activify.util.SessionFactoryUtil;

public class HibernateTest {
    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Conexión a la base de datos exitosa!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}