package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.SessionFactory;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;

/**
 * A class of static methods related to filtering a list of {@link com.sshgroup.ssh_fridge_contents_tracker.Recipe}
 */
public class RecipeToolkit {

    SessionFactory session = DatabaseAccess.setup();
    DatabaseAccess dbAccess = new DatabaseAccess();

    public ArrayList<Recipe> sortByPriceOfRemainingItems(ArrayList<Recipe> recipeList) {
        ArrayList<Ingredients> ingList = new ArrayList<>();
        // 2d list for recipes and their costs
        Map<Recipe, Double> costsForRecipes = new HashMap<>();
        // Iterate through the recipe list
        for (Recipe r : recipeList) {
            double temp = 0.0;
            Integer recipeID = r.getId();
            // get list of ingredients and loop through
            ingList = new ArrayList<>();
            for (Ingredients i : ingList) {
                // get quantity needed and quantity have. if have < needed then add the cost of that ingredient to temp
                Double need = dbAccess.recipeGetQuantity(i, r);
                int have = dbAccess.ingredientsGetQuantity(i.getIngredients_id());
                if (need <= have) {
                    temp += 0.0;
                } else {
                    PriceQuantity priceQ = getCheapestIngredient(i.getIngredients(), need);
                    double cost = priceQ.getPrice();
                    temp += cost;
                    System.out.println("temp");
                }
            }
            costsForRecipes.put(r, temp);
        }
        // now that the costs for each recipe are assigned we sort the list based on these costs
        // makes a list of mapped entries with Recipe as the key and the cost as the value. Sort by the value
        List<Map.Entry<Recipe, Double>> sortedCosts = new ArrayList<>(costsForRecipes.entrySet());
        sortedCosts.sort(Map.Entry.comparingByValue());
        // Then add the sorted recipes to a new list
        ArrayList<Recipe> sortedList = new ArrayList<>();
        for (Map.Entry<Recipe, Double> entry : sortedCosts) {
            sortedList.add(entry.getKey());
        }
        return sortedList;
    }

    public  ArrayList<Recipe> filterByCategory(ArrayList<Recipe> recipeList, Category category) {
        // declare new list
        ArrayList<Recipe> newList = new ArrayList<>();
        // loop through original list and only add to the new list if the category matches
        for(Recipe r : recipeList){
            if (dbAccess.getCategory(r).equals(category)){
                newList.add(r);
            }
        }
        return newList;
    }
    /**
     * A function that takes the name of an ingredient needed and the minimum quantity needed and uses webscraping techniques to find the cheapest item with the required quantity and returns the price of that item
     * @param ingredientName a string of the name of the ingredient required to find the price for
     * @param quantityNeeded the minimum quantity of the ingredient required. If item does not have a specified quantity, pass -1
     * @return the {@link com.sshgroup.ssh_fridge_contents_tracker.PriceQuantity} of the cheapest specified ingredient that is greater than the quantity needed. Null will be returned in the error case
     */
    public static PriceQuantity getCheapestIngredient(String ingredientName, double quantityNeeded) {
        PriceQuantity minItem = CacheMap.cache.get(ingredientName, String.valueOf(quantityNeeded));
        if (minItem != null) {
//            System.out.println("Yay!");
            return minItem;
        }
        ArrayList<PriceQuantity> ocadoItems = new ArrayList<>();
        try {
            //Ocado Scraper
            try {
                URL url = new URL("https://www.ocado.com/search?entry=" + ingredientName);
                WebScraper ws = new WebScraper(url);
                String body = ws.getWebpageHtml();
                if (body == null) {
                    throw new NoItemsException("Ocado Scraper returned valid no results");
                }
                String el2 = WebScraper.getElementByID(body, "div", "main-column", "class");
                if (el2 == null) {
                    throw new NoItemsException("Ocado Scraper returned valid no results");
                }
                String element = WebScraper.getElementByID(el2, "ul", "fops fops-regular fops-shelf", "class");
                if (element == null) {
                    throw new NoItemsException("Ocado Scraper returned valid no results");
                }
                ArrayList<String> LIs = WebScraper.getUnorderedListItems(element);
                for (String li : LIs) {
                    String li2 = null;
                    li2 = WebScraper.getElementByID(li, "div", "fop-item", "class");
                    if (li2 == null) {
                        li2 = WebScraper.getElementByID(li, "div", "fop-item fop-item-offer", "class");
                        if (li2 == null) {
                            continue;
                        }
                    }
                    String linkElement = WebScraper.getElement(li2, "a");
                    if (linkElement == null) {
                        continue;
                    }
                    String link = WebScraper.getParameterValue(linkElement, "href");
                    if (link == null) {
                        continue;
                    }
                    try {
                        OcadoPriceQuantity newItem = new OcadoPriceQuantity("https://www.ocado.com" + link);
                        ocadoItems.add(newItem);
                    }
                    catch (ItemNotFoundException e) {
                        System.out.println(e.toString());
                        continue;
                    }
                }
                if (ocadoItems.isEmpty()) {
                    throw new NoItemsException("Ocado Scraper returned valid no results");
                }
            }
            catch (NoItemsException e) {
                System.out.println("Error: "+ e.toString());
            }
            if (quantityNeeded > 0) {
                ocadoItems.removeIf(s -> s.getQuantity() < quantityNeeded);
            }
            PriceQuantity minOcado = PriceQuantity.getMinimumPrice((ArrayList<PriceQuantity>) ocadoItems);
            if (minOcado == null) {
                return null;
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