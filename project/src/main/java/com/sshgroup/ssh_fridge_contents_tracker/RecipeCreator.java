package com.sshgroup.ssh_fridge_contents_tracker;

import com.sshgroup.ssh_fridge_contents_tracker.DatabaseAccess;
import com.sshgroup.ssh_fridge_contents_tracker.Ingredients;
import com.sshgroup.ssh_fridge_contents_tracker.Recipe;
import com.sshgroup.ssh_fridge_contents_tracker.Recipe_Ingredients;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RecipeCreator {
    public static void createRecipe() {
        SessionFactory sessionFactory = DatabaseAccess.setup();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the recipe name: ");
            String recipeName = scanner.nextLine();

            System.out.print("Enter recipe instructions: ");
            String recipeInstructions = scanner.nextLine();

            List<String> ingredientNames = new ArrayList<>();
            List<Double> amountNeeded = new ArrayList<>();

            while (true) {
                System.out.print("Enter ingredient name (or done): ");
                String temp = scanner.nextLine();
                if ("done".equals(temp)) {
                    break;
                }
                ingredientNames.add(temp);

                System.out.print("Enter amount required: ");
                double amount = Double.parseDouble(scanner.nextLine());
                amountNeeded.add(amount);
            }

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Recipe recipe = new Recipe();
                recipe.setRecipe_name(recipeName);
                recipe.setRecipe_instruction(recipeInstructions);
                session.persist(recipe);

                for (int i = 0; i < ingredientNames.size(); i++) {
                    String ingredientName = ingredientNames.get(i);
                    double quantityNeeded = amountNeeded.get(i);

                    Ingredients ingredient = findIngredient(session, ingredientName);
                    if (ingredient == null) {
                        ingredient = new Ingredients();
                        ingredient.setIngredients(ingredientName);
                        ingredient.setQuantity(quantityNeeded);
                        session.persist(ingredient);
                    } else {
                        ingredient.setQuantity(ingredient.getQuantity() + quantityNeeded);
                    }

                    Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
                    recipeIngredients.setRecipe_id(recipe);
                    recipeIngredients.setIngredients_id(ingredient);
                    recipeIngredients.setQuantity_needed(quantityNeeded);
                    session.persist(recipeIngredients);
                }

                session.getTransaction().commit();
            }
        }
    }

    static Ingredients findIngredient(Session session, String name) {
        return session.createQuery("FROM Ingredients WHERE ingredients_name = :name", Ingredients.class)
                .setParameter("name", name)
                .uniqueResult();
    }

    @Transactional
    public static Recipe addRecipe(String recipeName, String recipeInstructions) {
        if (recipeName == null || recipeInstructions == null || recipeName.isEmpty() || recipeInstructions.isEmpty()) {
            return null;
        }
        SessionFactory sessionFactory = DatabaseAccess.setup();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Recipe recipe = new Recipe();
            recipe.setRecipe_name(recipeName);
            recipe.setRecipe_instruction(recipeInstructions);
            session.persist(recipe);
            return recipe;
        }
    }

    @Transactional
    public static Ingredients addIngredient(String ingredientName, double quantityAvailiable) {
        if (ingredientName == null || ingredientName.isEmpty()) {
            return null;
        }
        SessionFactory sessionFactory = DatabaseAccess.setup();
        try (Session session = sessionFactory.openSession()) {
            Ingredients ingredient = findIngredient(session, ingredientName);
            if (ingredient == null) {
                ingredient = new Ingredients();
                ingredient.setIngredients(ingredientName);
                ingredient.setQuantity(quantityAvailiable);
                //ingredient.setCost_per_kg(0);
            }
            else {
                ingredient.setQuantity(ingredient.getQuantity() + quantityAvailiable);
            }
            session.persist(ingredient);
            return ingredient;
        }
    }

    @Transactional
    public static Recipe_Ingredients addLinkBetweenIngredientAndRecipe(Recipe recipe, Ingredients ingredient, double quantityNeeded) {
        if (recipe == null || ingredient == null) {
            return null;
        }
        SessionFactory sessionFactory = DatabaseAccess.setup();
        try (Session session = sessionFactory.openSession()) {
            Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
            recipeIngredients.setRecipe_id(recipe);
            recipeIngredients.setIngredients_id(ingredient);
            recipeIngredients.setQuantity_needed(quantityNeeded);
            session.persist(recipeIngredients);
            return recipeIngredients;
        }
    }
}
