package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTests {
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void setup() {
        sessionFactory = DatabaseAccess.setup();
    }
    @Test
    void addRecipeTest() throws MalformedURLException {
        InputStream original = System.in;
        byte[] bytes = ("add"+ System.lineSeparator() +
                "Toast"+ System.lineSeparator() +
                "Toast the bread"+System.lineSeparator()+
                "1"+System.lineSeparator()+
                "Bread"+System.lineSeparator()+
                "38"+System.lineSeparator()+
                "done"+System.lineSeparator()+
                "done").getBytes();
        ByteArrayInputStream test = new ByteArrayInputStream(bytes);
        System.setIn(test);
        String[] args = new String[1];
        MainApplication.main(args);
        try (Session session = sessionFactory.openSession()) {
            assertNotNull(RecipeCreator.findRecipe(session, "Toast"));
        }
        finally {
            System.setIn(original);
        }
    }
}
