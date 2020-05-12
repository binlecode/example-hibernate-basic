package example.binle.hbm;

import example.binle.hbm.entity.Business;
import example.binle.hbm.entity.Company;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.MetadataSource;

/**
 * this util class is to create hibernate sessionFactory using hibernate.cfg.xml
 */
public class HibernateUtilWithCfgXml {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Hibernate can build service registry via cfg.xml and/or properties
                registry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .loadProperties("hibernate.properties")
                        .build();

                // entity field and relation mappings can be defined in cfg.xml files or by annotation
                // for xml configured mapping classes, they can only be added to metadata via xml file
                // for annotated mapping entities, they can be added programmatically
                MetadataSources metadataSources = new MetadataSources(registry)
                        .addAnnotatedClass(Business.class)
                        .addAnnotatedClass(Company.class);

                Metadata metadata = metadataSources.getMetadataBuilder().build();

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
