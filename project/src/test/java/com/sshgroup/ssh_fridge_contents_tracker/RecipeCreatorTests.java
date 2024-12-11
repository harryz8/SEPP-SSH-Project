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
            //ingredient.setCost_per_kg(2);
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
            //cheese.setCost_per_kg(5);
            session.persist(cheese);

            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(cheese);
            recipeIngredients.setQuantity_needed(2);
            //recipeIngredients.setTotal_cost(10);
            session.persist(recipeIngredients);

            session.getTransaction().commit();

            Recipe_Ingredients association = session.get(Recipe_Ingredients.class, recipeIngredients.getRecipe_ingredients_id());
            assertNotNull(association, "Recipe_Ingredients association should exist.");
            assertEquals(recipe.getId(), association.getRecipe_id().getId());
            assertEquals(cheese.getIngredients_id(), association.getIngredients_id().getIngredients_id());
            //assertEquals(10, association.getTotal_cost());
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
            //sugar.setCost_per_kg(1);
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
        // check it can't be added twice
        try (Session session = sessionFactory.openSession()) {
            Ingredients pastIngredient = RecipeCreator.addIngredient("Kiwi", 0);
            Ingredients currentIngredient = RecipeCreator.findIngredient(session, "Kiwi");
            assertEquals(pastIngredient.getIngredients_id(), currentIngredient.getIngredients_id());
        }
    }
    @Test
    void testAddRecipe() {
        try (Session session = sessionFactory.openSession()) {
            Recipe pastRecipe = RecipeCreator.addRecipe("Beans on toast", "Cook beans, toast toast and then place the beans on the toast.", 5);
            List<Recipe> currentRecipe = RecipeCreator.findRecipe(session, "Beans on toast");
            boolean any = false;
            for (Recipe each : currentRecipe) {
                if (each.equals(pastRecipe)) {
                    any = true;
                    break;
                }
            }
            assertTrue(any);
        }
    }
    @Test
    void testAddLinkBetweenIngredientAndRecipe() {
        try (Session session = sessionFactory.openSession()) {
            Recipe pastRecipe = RecipeCreator.addRecipe("Beans on toast", "Cook beans, toast toast and then place the beans on the toast.", 5);
            Ingredients pastIngredient = RecipeCreator.addIngredient("Beans", 0);
            Ingredients nextIngredient = RecipeCreator.addIngredient("Toast", 0);
            Recipe_Ingredients link = RecipeCreator.addLinkBetweenIngredientAndRecipe(pastRecipe, pastIngredient, 400);
            RecipeCreator.addLinkBetweenIngredientAndRecipe(pastRecipe, nextIngredient, 190);
            assertTrue(link.recipe_id.getName().equals("Beans on toast"));
        }
    }
    @Test
    void testAddCategory() {
        try (Session session = sessionFactory.openSession()) {
            Category pastCat = RecipeCreator.addCategory("Vegan");
            Category currentCat = RecipeCreator.findCategory(session, "Vegan");
            assertTrue(pastCat.equals(currentCat));
        }
        // check it can't be added twice
        try (Session session = sessionFactory.openSession()) {
            Category pastCat = RecipeCreator.addCategory("Vegan");
            Category currentCat = RecipeCreator.findCategory(session, "Vegan");
            assertTrue(pastCat.equals(currentCat));
        }
    }
    @Test
    void testAddLinkBetweenCategoryAndRecipe() {
        try (Session session = sessionFactory.openSession()) {
            Recipe pastRecipe = RecipeCreator.addRecipe("Beans on toast", "Cook beans, toast toast and then place the beans on the toast.", 5);
            Category pastCat = RecipeCreator.addCategory("Vegan");
            Recipe_Category link = RecipeCreator.addLinkBetweenCategoryAndRecipe(pastRecipe, pastCat);
            assertTrue(link.recipe_id.getName().equals("Beans on toast"));
        }
    }
    @Test
    void testFindRecipe() {
        try (Session session = sessionFactory.openSession()) {
            List<Recipe> found = RecipeCreator.findRecipe(session, "qwerty");
            assertEquals(0, found.size());
        }
    }
    @Test
    void testFindIngredients() {
        try (Session session = sessionFactory.openSession()) {
            Ingredients found = RecipeCreator.findIngredient(session, "qwerty");
            assertNull(found);
        }
    }
    @Test
    void testFindCategory() {
        try (Session session = sessionFactory.openSession()) {
            Category found = RecipeCreator.findCategory(session, "qwerty");
            assertNull(found);
        }
    }
}


