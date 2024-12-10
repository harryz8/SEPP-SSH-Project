package com.sshgroup.ssh_fridge_contents_tracker;

import org.hibernate.SessionFactory;

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
            Integer result = dbAccess.updateIngredientQuantity(i.ingredients_id, randomNum);
            count += result;
        }

        System.out.println(count);
        if (count == ingList.size()){
            return true;
        } else {
            return false;
        }
    }
}
