package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.Date;
import java.util.List;

public class Recipe {
    int recipe_id;
    String recipe_name;
    private List<String> recipe_instruction;
    int ingredients_id;
    private List<String> ingredients;
    int quantity;
    int min_cost;
    String store_link;
    int quantity_needed;
    int total_cost;
    int category_id;
    String category_name;
    Date last_update_date;



    public Recipe(int recipe_id, String recipe_name, List<String> recipe_instruction, List<String> ingredients, int ingredients_id, int quantity, int min_cost, String store_link, int quantity_needed, int total_cost, int category_id, String category_name, Date last_update_date) {
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

    public List<String> getInstruction() {
        return recipe_instruction;
    }

    public void setRecipe_instruction(List<String> recipe_instruction) {
        this.recipe_instruction = recipe_instruction;
    }

    public int getIngredients_id() {
        return ingredients_id;
    }

    public void setIngredients_id(int ingredients_id) {
        this.ingredients_id = ingredients_id;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
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