package com.sshgroup.ssh_fridge_contents_tracker;

import com.sshgroup.ssh_fridge_contents_tracker.DatabaseAccess;
import com.sshgroup.ssh_fridge_contents_tracker.Ingredients;
import com.sshgroup.ssh_fridge_contents_tracker.Recipe;
import com.sshgroup.ssh_fridge_contents_tracker.Recipe_Ingredients;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RecipeCreator {
    public static void main(String[] args) {
        SessionFactory sessionFactory = DatabaseAccess.setup();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the recipe name: ");
            String recipeName = scanner.nextLine();

            System.out.print("Enter recipe instructions: ");
            String recipeInstructions = scanner.nextLine();

            List<String> ingredientNames = new ArrayList<>();
            List<Integer> amountNeeded = new ArrayList<>();

            while (true) {
                System.out.print("Enter ingredient name (or done): ");
                String temp = scanner.nextLine();
                if (Objects.equals(temp, "done")) {
                    break;
                }
                ingredientNames.add(temp);

                System.out.print("Enter amount required: ");
                int amount = Integer.parseInt(scanner.nextLine());
                amountNeeded.add(amount);
            }

            // Perform database operations within a transaction
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();

                Recipe recipe = new Recipe();
                recipe.setRecipe_name(recipeName);
                recipe.setRecipe_instruction(recipeInstructions);
                session.persist(recipe);

                for (int i = 0; i < ingredientNames.size(); i++) {
                    String ingredientName = ingredientNames.get(i);
                    int quantityNeeded = amountNeeded.get(i);

                    Ingredients ingredient = findIngredient(session, ingredientName);
                    if (ingredient == null) {
                        // If ingredient doesn't exist, create a new one
                        ingredient = new Ingredients();
                        ingredient.setIngredients(ingredientName);
                        ingredient.setQuantity(quantityNeeded); // Initialize with the amount needed
                        ingredient.setCost_per_kg(0); // Default cost if unknown
                        session.persist(ingredient);
                    } else {
                        // Update the quantity if the ingredient exists
                        ingredient.setQuantity(ingredient.getQuantity() + quantityNeeded);
                    }

                    // Create and persist Recipe_Ingredients entry
                    Recipe_Ingredients recipeIngredients = new Recipe_Ingredients();
                    recipeIngredients.setRecipe_id(recipe);
                    recipeIngredients.setIngredients_id(ingredient);
                    recipeIngredients.setQuantity_needed(quantityNeeded);
                    recipeIngredients.setTotal_cost(ingredient.getMin_cost() * quantityNeeded);
                    session.persist(recipeIngredients);
                }

                session.getTransaction().commit();
            }
        } finally {
            sessionFactory.close();
        }
    }

    private static Ingredients findIngredient(Session session, String name) {
        return session.createQuery("FROM Ingredients WHERE ingredients_name = :name", Ingredients.class)
                .setParameter("name", name)
                .uniqueResult();
    }
}
