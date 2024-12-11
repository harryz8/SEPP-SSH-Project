package com.sshgroup.ssh_fridge_contents_tracker;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RecipeTookitTests {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void setup() {
        sessionFactory = DatabaseAccess.setup();
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
    @Test
    void testGetAllIngredients(){
        DatabaseAccess dbAccess = new DatabaseAccess();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Ingredients sugar = new Ingredients();
        sugar.setIngredients("Sugar");
        sugar.setQuantity(15);
        session.persist(sugar);

        session.getTransaction().commit();

        List<Ingredients> ingList = dbAccess.getAllIngredients();
        System.out.println("Fetched List: " + ingList.toString());

        assertNotEquals(ingList, null);
    }

    @Test
    void testUpdateIngredientsQuantity(){
        try(Session session = sessionFactory.openSession()) {
            DatabaseAccess dbAccess = new DatabaseAccess();

            session.beginTransaction();
            Ingredients sugar = new Ingredients();
            sugar.setIngredients("Sugar");
            sugar.setQuantity(15);
            session.persist(sugar);

            session.getTransaction().commit();

            List<Ingredients> ingList = dbAccess.getAllIngredients();
            System.out.println(ingList.get(0).getIngredients() + " " + ingList.get(0).ingredients_id + " " + ingList.get(0).getQuantity() + " before updating");

            dbAccess.updateIngredientQuantity(sugar.getIngredients_id(), 10);
            ingList = dbAccess.getAllIngredients();
            System.out.println(ingList.get(0).getIngredients() + " " + ingList.get(0).ingredients_id + " " + ingList.get(0).getQuantity() + " after updating");
            assertEquals(10, ingList.get(0).getQuantity());
        }
    }

    @Test
    void testGetRecipeIngredientList(){
        try(Session session = sessionFactory.openSession()) {
            DatabaseAccess dbAccess = new DatabaseAccess();
            session.beginTransaction();

            Recipe recipe = new Recipe();
            recipe.setRecipe_name("Pizza");
            recipe.setRecipe_instruction("Bake dough, add toppings, bake again.");
            session.persist(recipe);

            Ingredients cheese = new Ingredients();
            cheese.setIngredients("Cheese");
            cheese.setQuantity(10);
            session.persist(cheese);

            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(cheese);
            recipeIngredients.setQuantity_needed(2);
            session.persist(recipeIngredients);

            session.getTransaction().commit();

            List<Ingredients> ingList = dbAccess.getIngredientListRecipe(recipe);
            assertEquals(cheese.getIngredients(), ingList.get(0).getIngredients());

        }
    }

    @Test
    void testGetQuantity(){
        try(Session session = sessionFactory.openSession()){
            DatabaseAccess dbAccess = new DatabaseAccess();
            session.beginTransaction();

            Recipe recipe = new Recipe();
            recipe.setRecipe_name("Pizza");
            recipe.setRecipe_instruction("Bake dough, add toppings, bake again.");
            session.persist(recipe);

            Ingredients cheese = new Ingredients();
            cheese.setIngredients("Cheese");
            cheese.setQuantity(10);
            session.persist(cheese);

            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(cheese);
            recipeIngredients.setQuantity_needed(2);
            session.persist(recipeIngredients);

            session.getTransaction().commit();

            Double quantity = dbAccess.recipeGetQuantity(cheese, recipe);

            assertEquals(2, quantity);

        }
    }

    @Test
    void testGetCategory(){
        


    }

}



