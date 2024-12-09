package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.SessionFactory;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeToolkit {

    SessionFactory session = DatabaseAccess.setup();

    public static ArrayList<Recipe> sortByPriceOfRemainingItems(ArrayList<Recipe> recipeList) {
        ArrayList<Ingredient> ingList = new ArrayList<>();
        // 2d list for recipes and their costs
        Map<Recipe, Double> costsForRecipes = new HashMap<>();
        // Iterate through the recipe list
        for (Recipe r : recipeList){
            double temp = 0.0;
            Integer recipeID = r.getId();
            // get list of ingredients and loop through
            ingList = r.getIngredientList();
            for (Ingredient i : ingList){
                // get quantity needed and quantity have. if have < needed then add the cost of that ingredient to temp
                int need = DatabaseAccess.get(i.getID());
                int have = i.getQuantity();
                if (need <= have){
                    temp = 0.0;
                } else{
                    temp += i.getCost();
                }
                costsForRecipes.put(r, temp);
            }
        }
        // now that the costs for each recipe are assigned we sort the list based on these costs
        // makes a list of mapped entries with Recipe as the key and the cost as the value. Sort by the value
        List<Map.Entry<Recipe, Double>> sortedCosts = new ArrayList<>(costsForRecipes.entrySet());
        sortedCosts.sort(Map.Entry.comparingByValue());

        // Then add the sorted recipes to a new list
        ArrayList<Recipe> sortedList = new ArrayList<>();
        for(Map.Entry<Recipe, Double> entry : sortedCosts){
            sortedList.add(entry.getKey());
        }
        return sortedList;
    }


    public static ArrayList<Recipe> filterByCategory(ArrayList<Recipe> recipeList, Categories category) {
        // declare new list
        ArrayList<Recipe> newList = new ArrayList<>();
        // loop through original list and only add to the new list if the category matches
        for(Recipe r : recipeList){
            if (Recipe.getCategory().equals(category)){
                newList.add(r);
            }
            CacheMap.cache.put(ingredientName, String.valueOf(quantityNeeded), minOcado);
            return minOcado;
        }
        catch (MalformedURLException e) {
            System.out.println("Unable to connect to web store. Error: "+e.toString());
            return null;
        }
    }
}
