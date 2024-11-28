package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.Date;
import java.util.List;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;
import jdk.jfr.Name;

@Entity
@Table(name = "recipe_table")
public class Recipe {
    @Id
    int recipe_id;
    int ingredients_id;
    int category_id;

    @Column
    private String recipe_name;
    @Column
    private String recipe_instruction;
    @Column
    private String ingredients;
    @Column
    private int quantity;
    @Column
    private int min_cost;
    @Column
    private String store_link;
    @Column
    private int quantity_needed;
    @Column
    private int total_cost;
    @Column
    private String category_name;

    @Temporal(TemporalType.DATE)
    Date last_update_date;

    public Recipe(){

    }



    public Recipe(int recipe_id, String recipe_name, String recipe_instruction, String ingredients, int ingredients_id, int quantity, int min_cost, String store_link, int quantity_needed, int total_cost, int category_id, String category_name, Date last_update_date) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_instruction = recipe_instruction;
        this.ingredients_id = ingredients_id;
        this.ingredients = ingredients;
        this.quantity = quantity;
        this.min_cost = min_cost;
        this.store_link = store_link;
        this.quantity_needed = quantity_needed;
        this.total_cost = total_cost;
        this.category_id = category_id;
        this.category_name = category_name;
        this.last_update_date = last_update_date;

    }

    public int getId() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getName() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getInstruction() {
        return recipe_instruction;
    }

    public void setRecipe_instruction(String recipe_instruction) {
        this.recipe_instruction = recipe_instruction;
    }

    public int getIngredients_id() {
        return ingredients_id;
    }

    public void setIngredients_id(int ingredients_id) {
        this.ingredients_id = ingredients_id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMin_cost() {
        return min_cost;
    }

    public void setMin_cost(int min_cost) {
        this.min_cost = min_cost;
    }

    public String getStore_link() {
        return store_link;
    }

    public void setStore_link(String store_link) {
        this.store_link = store_link;
    }

    public int getQuantity_needed() {
        return quantity_needed;
    }

    public void setQuantity_needed(int quantity_needed) {
        this.quantity_needed = quantity_needed;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Date getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(Date last_update_date) {
        this.last_update_date = last_update_date;
    }
    
}