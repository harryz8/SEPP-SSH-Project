package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class RecipeCreator {
    public static void addRecipe(String recipeName, String recipeInstructions) {
        SessionFactory sessionFactory = DatabaseAccess.setup();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Recipe recipe = new Recipe();
            recipe.setRecipe_name(recipeName);
            recipe.setRecipe_instruction(recipeInstructions);
            session.persist(recipe);
            session.getTransaction().commit();
        }
    }

    public static void addIngredient(String ingredientName, int quantityNeeded) {
        SessionFactory sessionFactory = DatabaseAccess.setup();
        try (Session session = sessionFactory.openSession()) {
            Ingredients ingredient = findIngredient(session, ingredientName);
            if (ingredient == null) {
                ingredient = new Ingredients();
                ingredient.setIngredients(ingredientName);
                ingredient.setQuantity(quantityNeeded);
                //ingredient.setCost_per_kg(0);
            }
            else {
                ingredient.setQuantity(ingredient.getQuantity() + quantityNeeded);
            }
            session.persist(ingredient);
            session.getTransaction().commit();
        }
    }

    public static void addLinkBetweenIngredientAndRecipe(Recipe recipe, Ingredients ingredient, int quantityNeeded) {
        SessionFactory sessionFactory = DatabaseAccess.setup();
        try (Session session = sessionFactory.openSession()) {
            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(ingredient);
            recipeIngredients.setQuantity_needed(quantityNeeded);
            //recipeIngredients.setTotal_cost(ingredient.getMin_cost() * quantityNeeded);
            session.persist(recipeIngredients);
            session.getTransaction().commit();
        }
    }

    static Ingredients findIngredient(Session session, String name) {
        return session.createQuery("FROM Ingredients WHERE ingredients_name = :name", Ingredients.class)
                .setParameter("name", name)
                .uniqueResult();
    }
}