package activify.javvifdez.com.Model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Crea el registro de servicios estándar
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

            // Crea los metadatos a partir de las fuentes (annotations o hbm.xml)
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();

            // Construye la fábrica de sesiones
            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError("Error al inicializar la SessionFactory: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Cierre de la fábrica de sesiones (libera todos los recursos)
        getSessionFactory().close();
    }
}

