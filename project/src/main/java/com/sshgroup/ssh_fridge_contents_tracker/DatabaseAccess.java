package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.boot.Metadata;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.List;
import java.util.Properties;
import java.util.logging.*;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;

public class DatabaseAccess {

    /**
     * the session factory currently open
     */
    private static SessionFactory sessionFactory;

    /**
     * Starts the session factory or returns the active one
     * @return a sessionFactory
     */
    public static SessionFactory setup() {
        if (sessionFactory == null) {
            try {
                Class.forName("org.postgresql.Driver");
                sessionFactory = new Configuration()
                        .addAnnotatedClass(Ingredients.class)
                        .addAnnotatedClass(CacheTable.class)
                        .addAnnotatedClass(Ingredients.class)
                        .addAnnotatedClass(Recipe.class)
                        .addAnnotatedClass(Recipe_Category.class)
                        .addAnnotatedClass(Category.class)
                        .addAnnotatedClass(Recipe_Ingredients.class)
                        .setProperty(JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:5437/ssh")
                        .setProperty(JAKARTA_JDBC_USER, "group")
                        .setProperty(JAKARTA_JDBC_PASSWORD, "example")
                        .setProperty(SHOW_SQL, "false")
                        .setProperty(LOG_JDBC_WARNINGS, "false")
                        .buildSessionFactory();
                /*
                        .setProperty(DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
                        .setProperty(DRIVER, "org.postgresql.Driver")
                        .setProperty(FORMAT_SQL, "true")
                        .setProperty(HIGHLIGHT_SQL, "true")
                 */

                sessionFactory.getSchemaManager().exportMappedObjects(true);
            } catch (ClassNotFoundException e) {
                System.out.println("Error: " + e.toString());
            }
            catch (org.hibernate.service.spi.ServiceException e) {
                System.out.println("Could not connect to the postgreSQL port. Please check it is running and try again.");
                System.exit(1);
            }
        }
        return sessionFactory;
    }

    public static List<CacheTable> getAllCacheTableRecords() {
        List<CacheTable> cacheItems;
        try (Session session = sessionFactory.openSession()) {
            cacheItems = session.createQuery("FROM CacheTable", CacheTable.class).getResultList();
        }
        return cacheItems;
    }
}
