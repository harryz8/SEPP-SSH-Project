package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.ArrayList;

/**
 * A class that cannot be used to create objects; is used as a template for extending classes
 */
abstract class PriceQuantity implements Comparable<PriceQuantity> {

    /**
     * The price of the item at the {@link #link}
     */
    double price;

    /**
     * The quantity of the item at the {@link #link}. If quantity is not applicable, it remains set as 0
     */
    double quantity = 0;

    /**
     * The link to the item on the web
     */
    String link;

    /**
     * Getter method for {@link #price}
     * @return {@link #price}
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter method for {@link #quantity}
     * @return {@link #quantity}
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Getter method for {@link #link}
     * @return {@link #link}
     */
    public String getLink() {
        return link;
    }

    /**
     * Setter method for {@link #link}
     * @param link takes a string link to the item's webpage
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Setter method for {@link #price}
     * @param price takes a price double (in pounds £GBP)
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter method for {@link #quantity}
     * @param quantity takes a quantity double (should be in grams or litres or just numbers of object)
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Compares this object with another passed as a. It compares based on their price
     * @param a the object to be compared.
     * @return 0 if they are equal, 1 if this is greater than a, -1 otherwise including on a==null
     */
    @Override
    public int compareTo(PriceQuantity a) {
        if (a == null) {
            return -1;
        }
        return (Double.compare(this.getPrice(), a.getPrice()));
    }

    /**
     * Static function that takes an ArrayList of priceQuantities and uses the compareTo function to find the one with the minimum price.
     * @param originalList the ArrayList that holds the priceQuantites to find the minimum of
     * @return the PriceQuantity with the minimum price, or null on error
     */
    public static PriceQuantity getMinimumPrice(ArrayList<PriceQuantity> originalList) {
        if (originalList == null || originalList.isEmpty()) {
            return null;
        }
        PriceQuantity min = originalList.get(0);
        for (int i=1; i<originalList.size(); i++) {
            switch (min.compareTo(originalList.get(i))) {
                case (0), (-1):
                    break;
                case (1) :
                    min = originalList.get(i);
                    break;
            }
        }
        return min;
    }

    /**
     * Gives a string concatenation and formatting of {@link #quantity}, {@link #price} and {@link #link}
     * @return string as described in description of this method
     */
    @Override
    public String toString() {
        return "| "+this.getLink()+" | "+this.getPrice()+" | "+this.getQuantity()+" |";
    }
}
