package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.List;

public class Recipe {
    int id;
    String name;
    String instruction;
    private List<String> ingredients;

    public Recipe(int id, String name, String instruction, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.instruction = instruction;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInstruction() {
        return instruction;
    }

    public List<String> getIngredients() {
        return ingredients;
    }


}
