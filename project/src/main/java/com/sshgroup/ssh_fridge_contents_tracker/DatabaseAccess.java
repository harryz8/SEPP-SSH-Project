package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;


public class DatabaseAccess {
    public static SessionFactory setup() {
        var sessionFactoryin = new Configuration()
                .addAnnotatedClass(Ingredients.class)
                .addAnnotatedClass(Recipe.class)
                .addAnnotatedClass(Recipe_Category.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Recipe_Ingredients.class)
                .addAnnotatedClass(CacheTable.class)
                .setProperty(JAKARTA_JDBC_URL, "jdbc:postgresql://postgres/")
                .setProperty(JAKARTA_JDBC_USER, "postgres")
                .setProperty(JAKARTA_JDBC_PASSWORD, "example")
                .setProperty(SHOW_SQL, "true")
                .setProperty(FORMAT_SQL, "true")
                .setProperty(HIGHLIGHT_SQL, "true")
                .buildSessionFactory();

        sessionFactoryin.getSchemaManager().exportMappedObjects(true);
        return sessionFactoryin;
    }
}
