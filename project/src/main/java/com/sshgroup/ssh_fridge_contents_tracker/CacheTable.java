package com.sshgroup.ssh_fridge_contents_tracker;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cache_table")
public class CacheTable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
     */
    public CacheTable(String name_and_quantity_string, String price_quantity, long date_updated) {
        this.price_quantity = price_quantity;
        this.name_and_quantity_string = name_and_quantity_string;
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
