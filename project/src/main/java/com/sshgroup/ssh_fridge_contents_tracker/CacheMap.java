package com.sshgroup.ssh_fridge_contents_tracker;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.criteria.Root;
import java.io.*;
import java.util.*;

/**
 * A class extending HashMap which adds methods to save to and load from the cache.csv resource.
 */
public class CacheMap extends HashMap<String, PriceQuantity> {

    /**
     * The cache variable used everywhere in the code
     */
    public static CacheMap cache = new CacheMap();

    /**
     * Constructor, calls the constructor of HashMap
     */
    public CacheMap() {
        super();
    }

    /**
     * populates this class with mappings stored in the file, but only if the file is less than 7 days old.
     */
    public void load() {
        //get all from hybernate
        Date today = new Date();
        long sevenDays = (today.getTime()) + ((1000*60*60*24)*7);
        List<CacheTable> cacheItems = DatabaseAccess.getAllCacheTableRecords();
        for (CacheTable each : cacheItems) {
            if (each.getDate_updated() < sevenDays) {
                String[] priceQuantityList = each.getPrice_quantity().split("\\|");
                PriceQuantity eachPQ = new PriceQuantity(priceQuantityList[1].strip(), Double.parseDouble(priceQuantityList[2].strip()), Double.parseDouble(priceQuantityList[3].strip()));
                this.put(each.getName_and_quantity_string(), eachPQ);
            }
        }
    }

    /**
     * populates the database with all mappings in this class
     */
    public void saveAll() {
        Date today = new Date();
        for (Map.Entry<String, PriceQuantity> each : this.entrySet()) {
            DatabaseAccess.setup().inTransaction(session -> session.persist(new CacheTable(this.size()+1, each.getKey(), each.getValue().toString(), today.getTime())));
        }
    }

    /**
     * Overloads the put method from HashMap
     * @param itemName the name of the item in the mapping
     * @param itemQuantity the quantity of the item in the mapping (0 if not applicable)
     * @param value1 the priceQuantity object related to by the itemName and itemQuantity
     * @return value
     */
    public PriceQuantity put(String itemName, String itemQuantity, PriceQuantity value1) {
        Date today = new Date();
        String key1 = itemName+" | "+itemQuantity;
        DatabaseAccess.setup().inTransaction(session -> session.persist(new CacheTable(this.size()+1, key1, value1.toString(), today.getTime())));
        return super.put(key1, value1);
    }

    /**
     * Overloads the get method from HashMap
     * @param itemName the name of the item in the mapping
     * @param itemQuantity the quantity of the item in the mapping (0 if not applicable)
     * @return the {@link com.sshgroup.ssh_fridge_contents_tracker.PriceQuantity} linked to by the mapping
     */
    public PriceQuantity get(String itemName, String itemQuantity) {
        String key = itemName+" | "+itemQuantity;
        return super.get(key);
    }
}
