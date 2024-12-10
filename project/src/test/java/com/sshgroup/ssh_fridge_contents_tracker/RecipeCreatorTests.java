package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RecipeCreatorTests {

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
    void testRecipeCreation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Recipe recipe = new Recipe();
            recipe.setRecipe_name("Pasta");
            recipe.setRecipe_instruction("Boil pasta, add sauce.");
            session.persist(recipe);

            session.getTransaction().commit();

            Recipe foundRecipe = session.find(Recipe.class, recipe.getId());
            assertNotNull(foundRecipe);
            assertEquals("Pasta", foundRecipe.getName());
        }
    }


    @Test
    void testIngredientCreation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Ingredients ingredient = new Ingredients();
            ingredient.setIngredients("Tomato");
            ingredient.setQuantity(5);
            ingredient.setCost_per_kg(2);
            session.persist(ingredient);

            session.getTransaction().commit();

            Ingredients currentIngredient = session.get(Ingredients.class, ingredient.getIngredients_id());
            assertNotNull(currentIngredient, "Ingredient should remain in the database.");
            assertEquals("Tomato", currentIngredient.getIngredients());
        }
    }

    @Test
    void testRecipeIngredientsAssociation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Recipe recipe = new Recipe();
            recipe.setRecipe_name("Pizza");
            recipe.setRecipe_instruction("Bake dough, add toppings, bake again.");
            session.persist(recipe);

            Ingredients cheese = new Ingredients();
            cheese.setIngredients("Cheese");
            cheese.setQuantity(10);
            cheese.setCost_per_kg(5);
            session.persist(cheese);

            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(cheese);
            recipeIngredients.setQuantity_needed(2);
            recipeIngredients.setTotal_cost(10);
            session.persist(recipeIngredients);

            session.getTransaction().commit();

            Recipe_Ingredients association = session.get(Recipe_Ingredients.class, recipeIngredients.getRecipe_ingredients_id());
            assertNotNull(association, "Recipe_Ingredients association should exist.");
            assertEquals(recipe.getId(), association.getRecipe_id().getId());
            assertEquals(cheese.getIngredients_id(), association.getIngredients_id().getIngredients_id());
            assertEquals(10, association.getTotal_cost());
        }
    }

    @Test
    void testFindIngredient() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Ingredients sugar = new Ingredients();
            sugar.setIngredients("Sugar");
            sugar.setQuantity(15);
            sugar.setCost_per_kg(1);
            session.persist(sugar);

            session.getTransaction().commit();

            Ingredients currentIngredient = RecipeCreator.findIngredient(session, "Sugar");
            assertNotNull(currentIngredient, "Ingredient should be found.");
            assertEquals("Sugar", currentIngredient.getIngredients());
        }
    }

    @Test
    void testIngredientNotFound() {
        try (Session session = sessionFactory.openSession()) {
            Ingredients fetchedIngredient = RecipeCreator.findIngredient(session, "NonExistent");
            assertNull(fetchedIngredient, "Ingredient should not exist.");
        }
    }
}
