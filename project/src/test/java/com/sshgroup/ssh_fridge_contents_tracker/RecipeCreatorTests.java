package com.sshgroup.ssh_fridge_contents_tracker;

import jakarta.persistence.criteria.CriteriaBuilder;
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
            session.persist(cheese);

            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(cheese);
            recipeIngredients.setQuantity_needed(2);
            session.persist(recipeIngredients);

            session.getTransaction().commit();

            Recipe_Ingredients association = session.get(Recipe_Ingredients.class, recipeIngredients.getRecipe_ingredients_id());
            assertNotNull(association, "Recipe_Ingredients association should exist.");
            assertEquals(recipe.getId(), association.getRecipe_id().getId());
            assertEquals(cheese.getIngredients_id(), association.getIngredients_id().getIngredients_id());
        }
    }

    @Test
    void testRecipeCategoryAssociation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Recipe recipe = new Recipe();
            recipe.setRecipe_name("Pizza");
            recipe.setRecipe_instruction("Bake dough, add toppings, bake again.");
            session.persist(recipe);

            Category vegan = new Category();
            vegan.setCategory_name("Vegan");
            session.persist(vegan);

            Recipe_Category recipeCategories = new Recipe_Category();
            recipeCategories.setRecipe_id(recipe);
            recipeCategories.setCategory_id(vegan);
            session.persist(recipeCategories);

            session.getTransaction().commit();

            Recipe_Category association = session.get(Recipe_Category.class, recipeCategories.getRecipe_category_id());
            assertNotNull(association, "Recipe_Categories association should exist.");
            assertEquals(recipe.getId(), association.getRecipe_id().getId());
            assertEquals(vegan.getCategory_id(), association.getCategory_id().getCategory_id());
        }
    }

    @Test
    void testFindIngredient() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Ingredients sugar = new Ingredients();
            sugar.setIngredients("Sugar");
            sugar.setQuantity(15);
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
            Ingredients currentIngredient = RecipeCreator.findIngredient(session, "testingredient");
            assertNull(currentIngredient, "Ingredient should not exist.");
        }
    }
    @Test
    void testAddIngredient() {
        try (Session session = sessionFactory.openSession()) {
            Ingredients pastIngredient = RecipeCreator.addIngredient("Kiwi", 0);
            Ingredients currentIngredient = RecipeCreator.findIngredient(session, "Kiwi");
            assertEquals(pastIngredient.getIngredients_id(), currentIngredient.getIngredients_id());
        }
    }
}
