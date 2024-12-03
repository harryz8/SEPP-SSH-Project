package com.sshgroup.ssh_fridge_contents_tracker;

import java.util.Scanner;
import java.util.ArrayList;
public class RecipeCreator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello");

        System.out.print("Enter the recipe name: ");
        String recipe_name = scanner.nextLine();

        System.out.print("Enter recipe instructions: ");
        String recipe_instructions = scanner.nextLine();

        ArrayList<String> ingredient_name = new ArrayList<String>();
        ArrayList<Integer> amount_needed = new ArrayList<Integer>();

        while(true) {
            System.out.print("Enter ingredient name (or done): ");
            String temp = scanner.nextLine();
            if (temp == "done") {
                break;
            } else {
                ingredient_name.add(temp);
            }
            System.out.print("Enter amount required: ");
            amount_needed.add(scanner.nextInt());
        }

    }
}
