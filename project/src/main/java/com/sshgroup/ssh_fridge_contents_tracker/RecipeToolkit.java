package com.sshgroup.ssh_fridge_contents_tracker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class RecipeToolkit {

    public static ArrayList<Recipe> sortByPriceOfRemainingItems(ArrayList<Recipe> recipeList) {
        // TODO: implement here
        return recipeList;
    }

    public static ArrayList<Recipe> filterByCategory(ArrayList<Recipe> recipeList, Categories category) {
        // TODO: implement here
        return recipeList;
    }

    /**
     * A function that takes the name of an ingredient needed and the minimum quantity needed and uses webscraping techniques to find the cheapest item with the required quantity and returns the price of that item
     * @param ingredientName a string of the name of the ingredient required to find the price for
     * @param quantityNeeded the minimum quantity of the ingredient required. If item does not have a specified quantity, pass -1
     * @return a double of the price of the cheapest specified ingredient that is greater than the quantity needed. A negative number will be returned in the error case
     */
    public static double getCheapestPrice(String ingredientName, double quantityNeeded) {
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
            for (PriceQuantity eachItem : ocadoItems) {
                System.out.println(eachItem.toString());
            }
            System.out.println(ocadoItems.size());
            PriceQuantity minOcado = PriceQuantity.getMinimumPrice((ArrayList<PriceQuantity>) ocadoItems);
            if (minOcado == null) {
                return -2;
            }
            return minOcado.getPrice();
        }
        catch (MalformedURLException e) {
            System.out.println("Unable to connect to web store. Error: "+e.toString());
            return -2;
        }
    }
}
