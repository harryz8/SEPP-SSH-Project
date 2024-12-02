package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.ArrayList;

abstract class PriceQuantity implements Comparable<PriceQuantity> {
    double price;
    double quantity = 0;
    String link;

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

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

    @Override
    public String toString() {
        return "| "+this.getLink()+" | "+this.getPrice()+" | "+this.getQuantity()+" |";
    }
}
