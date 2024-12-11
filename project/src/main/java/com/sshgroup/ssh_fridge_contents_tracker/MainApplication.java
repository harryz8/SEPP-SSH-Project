package com.sshgroup.ssh_fridge_contents_tracker;

import jakarta.enterprise.context.Initialized;
import jakarta.persistence.criteria.From;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.spi.InitializeCollectionEventListener;

import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_PASSWORD;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_URL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CacheMap.cache.load();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DatabaseAccess.setup().close();
        super.stop();
    }

    public static void loadRecipes() {
        Recipe pancakeRecipe = RecipeCreator.addRecipe("Buttermilk pancakes", "First sieve the flour, baking powder and salt together in a roomy bowl and make a well in the centre. After that, whisk the buttermilk and 3fl oz/75ml cold water together in a jug and gradually whisk this into the bowl, slowly incorporating the flour with each new addition of liquid. Finally, add the eggs a little at a time until you have a smooth batter.\n" +
                "Now place a large, solid frying pan over a medium heat, add 2 teaspoons of the lard and heat it until the fat shimmers. Then, using a tablespoon of batter per pancake, place 2 or 3 spoonfuls into the pan.\n" +
                "They will take about 1 minute to turn golden brown, then turn them over using a spatula and fork, being careful not to splash yourself with the hot fat. Give them another 45 seconds on the other side, by which time they should have puffed up like little soufflés, then briefly rest them on some kitchen paper to absorb any excess fat.\n" +
                "Repeat this with the rest of the batter, adding a little more lard if necessary. They will keep warm in a low oven, but to enjoy them at their best, have everyone seated to eat them as soon as they come out of the pan.", 6);
        Ingredients buttermilk = RecipeCreator.addIngredient("Buttermilk", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(pancakeRecipe, buttermilk, 0.12);
        Ingredients plainFlour = RecipeCreator.addIngredient("Plain flour", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(pancakeRecipe, plainFlour, 150);
        Ingredients bakingPowder = RecipeCreator.addIngredient("Baking powder", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(pancakeRecipe, bakingPowder, 2.84);
        Ingredients salt = RecipeCreator.addIngredient("Salt", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(pancakeRecipe, salt, 0.3);
        Ingredients egg = RecipeCreator.addIngredient("Egg", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(pancakeRecipe, egg, 3);
        Ingredients butter = RecipeCreator.addIngredient("Butter", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(pancakeRecipe, butter, 50);
        Category desert = RecipeCreator.addCategory("Desert");
        RecipeCreator.addLinkBetweenCategoryAndRecipe(pancakeRecipe, desert);
        Recipe spinachAndCoconutRecipe = RecipeCreator.addRecipe("Spinach and coconut dal with pitta and yoghurt", "Heat the oil in a large saucepan over a low–medium heat. Add the onion, ginger and 3 cloves of garlic, cover with a lid and cook, stirring frequently, for 10 minutes or until softened, but not coloured.\n" +
                "Add the curry powder and lentils and stir well to make sure the lentils are well coated. Add the stock, bring to a simmer and then cook, stirring regularly, for 15–20 minutes, until the lentils are pale yellow and only retain a little bite. Add the coconut milk and spinach, turn off the heat and stir to combine. Replace the lid and leave the spinach to fully wilt.\n" +
                "Season with salt and pepper. This recipe makes four portions – eat one portion now and divide the remaining three portions into three sealed containers. Keep two portions in the fridge and put one portion in the freezer (see Recipe Tip 2 for details). Serve the dal with the toasted pitta and yoghurt garnished with red chilli, if using.", 4);
        Ingredients oil = RecipeCreator.addIngredient("Oil", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, oil, 0.0147868);
        Ingredients onion = RecipeCreator.addIngredient("Onion", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, onion, 1);
        Ingredients ginger = RecipeCreator.addIngredient("Root ginger", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, ginger, 25);
        Ingredients garlic = RecipeCreator.addIngredient("Garlic", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, garlic, 1);
        Ingredients curryPowder = RecipeCreator.addIngredient("Curry powder", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, curryPowder, 28.35);
        Ingredients redLentils = RecipeCreator.addIngredient("Red lentils", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, redLentils, 500);
        Ingredients vegStockCube = RecipeCreator.addIngredient("Vegetable stock cube", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, vegStockCube, 1);
        Ingredients coconutMilk = RecipeCreator.addIngredient("Coconut milk", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, coconutMilk, 0.4);
        Ingredients spinach = RecipeCreator.addIngredient("Spinach", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, spinach, 150);
        Ingredients pittaBread = RecipeCreator.addIngredient("Pitta bread", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, pittaBread, 1);
        Ingredients plainYoghurt = RecipeCreator.addIngredient("Plain yoghurt", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, plainYoghurt, 50);
        Ingredients redChilli = RecipeCreator.addIngredient("Red chilli", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, redChilli, 0.3333333333333333333333);
        Ingredients salt2 = RecipeCreator.addIngredient("Salt", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, salt2, 0.3);
        Ingredients pepper = RecipeCreator.addIngredient("Black pepper", 0);
        RecipeCreator.addLinkBetweenIngredientAndRecipe(spinachAndCoconutRecipe, pepper, 0.3);
        Category vegetarian  = RecipeCreator.addCategory("Vegetarian");
        RecipeCreator.addLinkBetweenCategoryAndRecipe(spinachAndCoconutRecipe, vegetarian);
    }

    public static void main(String[] args) throws MalformedURLException {
        loadRecipes();
        CacheMap.cache.load();
        if ((args.length > 0) && (args[0].toLowerCase().equals("i"))) {
            launch();
        }
        else {
            System.out.println("Command line mode goes here");
            Scanner RecipeScanner = new Scanner(System.in);
            String forScan;
            System.out.println("Choose whether you want to add a recipe or get a recipe (;  :");
            System.out.println("Please write add or get");
            forScan = RecipeScanner.nextLine();
            if (forScan.toLowerCase().equals("add")){
//                System.out.println("Please enter the dish name: ");
//                String recipe_name = RecipeScanner.nextLine();
//                System.out.println("Now please enter the recipe instruction and " + "quantity needed");
//                String recipe_instruction = RecipeScanner.next();
//                int quantity_needed = RecipeScanner.nextInt();
                RecipeScanner.close();
                RecipeCreator.createRecipe();
            } else if (forScan.toLowerCase().equals("get")) {
                RecipeScanner.close();
                Loading loading = new Loading();
                Thread loadingThread = new Thread(loading);
                loadingThread.start();
                //===========Do Stuff Here Whilst User Is Waiting===================
                try {
                    Thread.sleep(10000);
                }
                catch (InterruptedException e) {
                    System.out.println("Main thread interrupted: "+ e.toString());
                }
                //===========Stop The Loading Symbol================================
                loadingThread.interrupt();
                System.out.println("The recipe for the dish is: ");
            } else {
                System.out.println("Please enter a valid command (: ");
                RecipeScanner.close();
            }

            //cleanup
            DatabaseAccess.setup().close();
            System.exit(0);
        }
    }
}