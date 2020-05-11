package example.binle.hbm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * this util class is to create hibernate sessionFactory with DataSource configuration
 */
public class HibernateUtilCustomPool {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry builder
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();

                Map<String, Object> settings = new HashMap<>();

                settings.put(Environment.HBM2DDL_AUTO, "update");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");

                settings.put("hibernate.connection.driver_class", "org.h2.Driver");
                settings.put("hibernate.connection.url", "jdbc:h2:./testDb3");
                settings.put("hibernate.connection.username", "sa");

                // Generally speaking, applications should not have to configure a ConnectionProvider
                // explicitly if using one of the Hibernate-provided implementations.
                // Hibernate will internally determine which ConnectionProvider to use based on settings.

                settings.put("hibernate.hikari.maximumPoolSize", "5");
                settings.put("hibernate.hikari.idleTimeout", "120000");

                // Apply settings
                registryBuilder.applySettings(settings);

                // Create registry
                registry = registryBuilder.build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // add mapping xml file as resource
                sources.addResource("employee.hbm.xml");

                // add annotated entity by class or by name
                sources.addAnnotatedClassName("example.binle.hbm.entity.Company");

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
