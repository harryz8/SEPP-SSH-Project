package com.sshgroup.ssh_fridge_contents_tracker;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cache_table")
public class CacheTable {

    @Id
    int cache_id;

    @Column
    String price_quantity;

    @Column
    String name_and_quantity_string;

    @Column
    long date_updated;

    /**
     * Blank constructor
     */
    public CacheTable() {

    }

    /**
     * Constructor that sets all fields
     * @param price_quantity the {@link com.sshgroup.ssh_fridge_contents_tracker.PriceQuantity} after .toString()
     * @param name_and_quantity_string the key
     * @param date_updated date that the record was last changed as a long
     */
    public CacheTable(int cache_id, String name_and_quantity_string, String price_quantity, long date_updated) {
        this.cache_id = cache_id;
        this.price_quantity = price_quantity;
        this.name_and_quantity_string = name_and_quantity_string;
        this.date_updated = date_updated;
    }

    public void setCache_id(int cache_id) {
        this.cache_id = cache_id;
    }

    public int getCache_id() {
        return cache_id;
    }

    public long getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(long date_updated) {
        this.date_updated = date_updated;
    }

    public String getName_and_quantity_string() {
        return name_and_quantity_string;
    }

    public String getPrice_quantity() {
        return price_quantity;
    }

    public void setName_and_quantity_string(String name_and_quantity_string) {
        this.name_and_quantity_string = name_and_quantity_string;
    }

    public void setPrice_quantity(String price_quantity) {
        this.price_quantity = price_quantity;
    }
}
