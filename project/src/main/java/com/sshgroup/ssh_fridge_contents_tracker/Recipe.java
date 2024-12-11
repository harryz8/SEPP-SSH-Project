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
    @GeneratedValue(strategy=GenerationType.AUTO)
    int recipe_id;


    @Column
    private String recipe_name;
    @Column(columnDefinition="TEXT")
    private String recipe_instruction;
    @Column
    private int number_servings;


    public Recipe(){

    }



    public Recipe(String recipe_name, String recipe_instruction, int number_servings) {
        this.recipe_name = recipe_name;
        this.recipe_instruction = recipe_instruction;
        this.number_servings = number_servings;
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

    public void setNumber_servings(int number_servings) {
        this.number_servings = number_servings;
    }

    public int getNumber_servings() {
        return number_servings;
    }

    public boolean equals(Recipe otherRecipe) {
        if (otherRecipe == null) {
            return false;
        }
        return (this.getId() == otherRecipe.getId());
    }
}