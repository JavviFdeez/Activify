package activify.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {
    private static final String PERSISTENCE_UNIT_NAME = "activify-persistence-unit";
    private static EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}