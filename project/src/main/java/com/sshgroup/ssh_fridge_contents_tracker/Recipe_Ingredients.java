package com.sshgroup.ssh_fridge_contents_tracker;
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "recipe_ingredients_table")

public class Recipe_Ingredients {
    @Id
    int recipe_ingredients_id;

    @ManyToOne
    Recipe recipe_id;
    @ManyToOne
    Ingredients ingredients_id;


    @Column
    private int quantity_needed;
    @Column
    private int total_cost;

    public Recipe_Ingredients() {

    }

    public Recipe_Ingredients(int recipe_ingredients_id, Recipe recipe_id, Ingredients ingredients_id, int quantity_needed, int total_cost) {
        this.recipe_ingredients_id = recipe_ingredients_id;
        this.recipe_id = recipe_id;
        this.ingredients_id = ingredients_id;
        this.quantity_needed = quantity_needed;
        this.total_cost = total_cost;
    }

        public int getRecipe_ingredients_id() {
            return recipe_ingredients_id;
        }
        public void setRecipe_ingredients_id(int recipe_ingredients_id){
            this.recipe_ingredients_id = recipe_ingredients_id;
        }

        public Recipe getRecipe_id() {
            return recipe_id;
        }

         public void setRecipe_id(Recipe recipe_id) {
            this.recipe_id = recipe_id;
        }

        public Ingredients getIngredients_id() {
            return ingredients_id;
        }

        public void setIngredients_id(Ingredients ingredients_id) {
            this.ingredients_id = ingredients_id;
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

}
