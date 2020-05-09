package example.binle.hbm;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * this util class is to create hibernate sessionFactory without using hibernate.cfg.xml
 */
public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                // Create registry builder
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Map<String, String> settings = new HashMap<>();
//                settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
//                settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/BORAJI");
//                settings.put("hibernate.connection.username", "postgres");
//                settings.put("hibernate.connection.password", "admin");
//                settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");

                settings.put("hibernate.connection.driver_class", "org.h2.Driver");
                settings.put("hibernate.connection.url", "jdbc:h2:./testDb;MODE=MYSQL;DATABASE_TO_LOWER=TRUE");
                // url: jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
                settings.put("hibernate.connection.username", "sa");
//                settings.put("hibernate.connection.password", "sa");
//                settings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

                // Apply settings
                registryBuilder.applySettings(settings);

                // Create registry
                registry = registryBuilder.build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
