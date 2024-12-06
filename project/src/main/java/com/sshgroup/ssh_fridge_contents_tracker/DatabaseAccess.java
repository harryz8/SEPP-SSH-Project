package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.boot.Metadata;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;
import java.util.logging.*;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;

public class DatabaseAccess {
    private static SessionFactory sessionFactory;
    public static SessionFactory setup() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Ingredients.class)
                .addAnnotatedClass(Recipe.class)
                .addAnnotatedClass(Recipe_Category.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Recipe_Ingredients.class)
                .addAnnotatedClass(CacheTable.class)
                .setProperty(JAKARTA_JDBC_URL, "jdbc:postgresql://localhost:1234/")
                .setProperty(JAKARTA_JDBC_USER, "postgres")
                .setProperty(JAKARTA_JDBC_PASSWORD, "SSHproject")
                .setProperty(SHOW_SQL, "true")
                .setProperty(FORMAT_SQL, "true")
                .setProperty(HIGHLIGHT_SQL, "true")
                .buildSessionFactory();

        sessionFactory.getSchemaManager().exportMappedObjects(true);

        return sessionFactory;
    }
}
