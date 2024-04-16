package activify.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SomeService {
    private EntityManagerFactory entityManagerFactory;

    public SomeService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void someMethod() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
        } finally {
            entityManager.close();
        }
    }
}

