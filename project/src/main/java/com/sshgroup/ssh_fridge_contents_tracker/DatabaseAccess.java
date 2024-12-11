package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
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

    public Category getCategory(Recipe recipe){
        Category cat = null;
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT rec_cat.category_id FROM com.sshgroup.ssh_fridge_contents_tracker.Recipe_Category rec_cat WHERE recipe_id = :rec";
            Query query = session.createQuery(hql);
            query.setParameter("rec", recipe);
            List<Category> result = query.getResultList();
            if (!result.isEmpty()){
                cat = result.get(0);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return cat;
    }

    public List<Ingredients> getAllIngredients(){
        List<Ingredients> ingList = null;
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT i FROM com.sshgroup.ssh_fridge_contents_tracker.Ingredients i";
            Query query = session.createQuery(hql);
            ingList = query.getResultList();
        } catch(Exception e){
            System.out.println(e);
        }
        return ingList;
    }

    public Integer updateIngredientQuantity(Integer ingredient_id, Integer newQuantity){
        Integer rowsAffected = 0;
        try(Session session = sessionFactory.openSession()){
            Transaction trans = session.beginTransaction();

            String hql = "UPDATE com.sshgroup.ssh_fridge_contents_tracker.Ingredients i SET i.quantity_available =:newQuantity WHERE ingredients_id = :iID";
            Query query = session.createQuery(hql);
            query.setParameter("newQuantity", newQuantity);
            query.setParameter("iID", ingredient_id);

            rowsAffected = query.executeUpdate();
            trans.commit();

            String hql2 = "SELECT i.quantity_available FROM com.sshgroup.ssh_fridge_contents_tracker.Ingredients i ";
            Query query2 = session.createQuery(hql2);
            List<Double> result = query2.getResultList();
            System.out.println(result.get(0));
            System.out.println("Updated Rows: " + rowsAffected);
        } catch (Exception e){
            System.out.println(e);
        }
        return rowsAffected;
    }


    public List<Ingredients> getIngredientListRecipe(Recipe recipe){
        List<Ingredients> ingList = null;
        try (Session session  = sessionFactory.openSession()){
            String hql = "SELECT i.ingredients_id FROM com.sshgroup.ssh_fridge_contents_tracker.Recipe_Ingredients i WHERE recipe_id = :rec";
            Query query = session.createQuery(hql);
            query.setParameter("rec", recipe);
            ingList = query.getResultList();
        }catch(Exception e){
            System.out.println(e);
        }
        return ingList;
    }

    public Double recipeGetQuantity(Ingredients ingredient, Recipe recipe){
        Double quantityNeed = null;
        try (Session session = sessionFactory.openSession()){
            String hql = "SELECT rec_ing.quantity_needed FROM com.sshgroup.ssh_fridge_contents_tracker.Recipe_Ingredients rec_ing WHERE recipe_id =:rID AND ingredients_id = :iID";
            Query query = session.createQuery(hql);
            query.setParameter("rID", recipe);
            query.setParameter("iID", ingredient);
            List<Double> result = query.list();
            System.out.println(result.toString());

            if(!result.isEmpty()){
                quantityNeed = result.get(0);
            }
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return quantityNeed;
    }

    public Integer ingredientsGetQuantity(Integer ingredient_id){
        Integer ingredientHave = null;
        try(Session session = sessionFactory.openSession()){
            String hql = "SELECT ing.quantity_available FROM com.sshgroup.ssh_fridge_contents_tracker.Ingredients ing WHERE ingredients_id =:iID";
            Query query = session.createQuery(hql);
            query.setParameter("iID", ingredient_id);
            List<Integer> result = query.list();

            if(!result.isEmpty()){
                ingredientHave = result.get(0);
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }

        return ingredientHave;

    }


    public static List<CacheTable> getAllCacheTableRecords() {
        List<CacheTable> cacheItems;
        try (Session session = sessionFactory.openSession()) {
            cacheItems = session.createQuery("FROM CacheTable", CacheTable.class).getResultList();
        }
        return cacheItems;
    }
}
