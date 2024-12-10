package com.sshgroup.ssh_fridge_contents_tracker;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ingredients_table")
public class Ingredients {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int ingredients_id;

    @Column
    private String ingredients_name;
    @Column
    private int quantity_available;



    public Ingredients() {

    }

    public Ingredients( int ingredients_id, String ingredients, int quantity_available, int cost_per_kg) {

        this.ingredients_id = ingredients_id;
        this.quantity_available = quantity_available;


    }
    public int getIngredients_id() {
        return ingredients_id;
    }

    public void setIngredients_id(int ingredients_id) {
        this.ingredients_id = ingredients_id;
    }

    public String getIngredients() {
        return ingredients_name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients_name = ingredients;
    }

    public int getQuantity() {
        return quantity_available;
    }

    public void setQuantity(int quantity) {
        this.quantity_available = quantity_available;
    }


}
