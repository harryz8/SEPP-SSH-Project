package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CameraSimulator {

    SessionFactory session = DatabaseAccess.setup();
    DatabaseAccess dbAccess = new DatabaseAccess();

    public Boolean randomQuantities(){
        Integer count = 0;
        List<Ingredients> ingList = dbAccess.getAllIngredients();
        Random rand = new Random();

        for (Ingredients i : ingList){
            Integer randomNum = rand.nextInt(10);
            Integer result = dbAccess.updateIngredientQuantity(i.getIngredients_id(), randomNum);
            count += result;
        }

        System.out.println(count);
        if (count == ingList.size()){
            return true;
        } else {
            return false;
        }
    }

    public Boolean recipePassedInMoreExpensiveSimulator(Recipe r){
        ArrayList<Ingredients> ingList = (ArrayList<Ingredients>) dbAccess.getIngredientListRecipe(r);
        int count = 0;
        int temp = 0;
        Integer result = 0;
        for(Ingredients i : ingList){
            if ((count % 2 ) == 0){
                result = dbAccess.updateIngredientQuantity(i.getIngredients_id(), 0);
            }
            count ++;
            temp += result;
        }
        if (temp > 0){
            return true;
        } else {
            return false;
        }

    }
}
