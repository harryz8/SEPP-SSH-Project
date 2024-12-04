package com.sshgroup.ssh_fridge_contents_tracker;
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "recipe_category_table")

public class Recipe_Category {
    @Id
    int recipe_category_id;
    @ManyToOne
    Recipe recipe_id;
    @ManyToOne
    Category category_id;


    public Recipe_Category(){
    }

    public Recipe_Category(int recipe_category_id, Recipe recipe_id, Category category_id){
        this.recipe_category_id = recipe_category_id;
        this.recipe_id = recipe_id;
        this.category_id = category_id;
    }

    public int getRecipe_category_id() {
        return recipe_category_id;
    }
    public void setRecipe_category_id(int recipe_category_id){
        this.recipe_category_id = recipe_category_id;
    }


    public Recipe getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Recipe recipe_id) {
        this.recipe_id = recipe_id;
    }


    public Category getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Category category_id) {
        this.category_id = category_id;
    }
}
