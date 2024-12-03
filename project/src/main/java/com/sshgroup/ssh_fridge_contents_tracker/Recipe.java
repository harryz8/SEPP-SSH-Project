package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.ArrayList;

public class Recipe {
    private int id;
    private String name;
    private String instructions;

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getInstructions(){
        return instructions;
    }

    public ArrayList<Ingredient> getIngredientList(){
         return null;
    }

    public int getQuantity(int ingredientID){
        return 0;
    }

    public static Categories getCategory(){
        return null;
    }
}
