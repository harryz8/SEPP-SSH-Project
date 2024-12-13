package com.sshgroup.ssh_fridge_contents_tracker;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {
    public Label title;
    @FXML GridPane window;
    List<Map.Entry<Recipe, Double>> recipeList = MainApplication.recipeList;
    final int RECIPEFRAMESIZE = 1;
    @FXML
    void initialize() {
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(4);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(8);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(88);
        window.getRowConstraints().addAll(row1, row2, row3);
        title.setMinHeight(Region.USE_PREF_SIZE);
        VBox innerVBox = new VBox();
        innerVBox.setSpacing(10);
        innerVBox.setStyle("-fx-padding: 10 0 10 0");
        ScrollPane sp = new ScrollPane(innerVBox);
        sp.setStyle("-fx-padding: 10 0 10 0");
        sp.prefWidthProperty().bind(window.widthProperty());
        sp.prefHeightProperty().bind(window.heightProperty());//makes width of scrollpane same as window
        window.add(sp, 0, 2);
        window.alignmentProperty().set(Pos.CENTER);
        innerVBox.prefWidthProperty().bind(sp.widthProperty().subtract(25)); //makes width of innerVBox same as scrollpane
        innerVBox.prefHeightProperty().bind(sp.heightProperty());
        innerVBox.setId("innerVBox");
        //System.out.println("Recipes no.: "+String.valueOf(recipeList.size()));
        for (int i = 0; i < recipeList.size(); i+=RECIPEFRAMESIZE) {
            VBox item = new VBox();
            item.getStyleClass().add("Item");
            //Label item = new Label();
            item.setPadding(new Insets(10, 0, 10, 0));
            //item.setWrapText(true);
            //item.setText(recipeList.get(i).getKey().getName()+"\n"+recipeList.get(i).getKey().getInstruction()+"\nNumber of servings: "+String.valueOf(recipeList.get(i).getKey().getNumber_servings())+"\nPrice of items not in the fridge: £"+String.valueOf(recipeList.get(i).getValue())+"\n");
            Label title = new Label();
            item.getChildren().add(title);
            title.getStyleClass().add("Item_title");
            title.setWrapText(true);
            title.setMinHeight(Region.USE_PREF_SIZE);
            title.setText(recipeList.get(i).getKey().getName());
            Label priceOfRemaining = new Label();
            item.getChildren().add(priceOfRemaining);
            priceOfRemaining.getStyleClass().add("Item_price");
            priceOfRemaining.setWrapText(true);
            priceOfRemaining.setText("Price of items not in the fridge: £"+String.valueOf(recipeList.get(i).getValue()));
            Label numServings = new Label();
            item.getChildren().add(numServings);
            numServings.getStyleClass().add("Item_servings");
            numServings.setWrapText(true);
            numServings.setText("Number of servings: "+String.valueOf(recipeList.get(i).getKey().getNumber_servings()));
            Label ingredientDisc = new Label();
            item.getChildren().add(ingredientDisc);
            ingredientDisc.getStyleClass().add("ingredient_text");
            ingredientDisc.setWrapText(true);
            ingredientDisc.setMinHeight(Region.USE_PREF_SIZE);
            ingredientDisc.setText("Ingredients (either in litres or grams or just number of):");
            DatabaseAccess db = new DatabaseAccess();
            for (Recipe_Ingredients each : db.getIngredient_RecipeListRecipe(recipeList.get(i).getKey())) {
                Label ingredient = new Label();
                item.getChildren().add(ingredient);
                if (each.getQuantity_needed() < 1) {
                    ingredient.setText(each.getIngredients_id().getIngredients() + ": " + String.format("%.2g%n", each.getQuantity_needed()));
                }
                else {
                    ingredient.setText(each.getIngredients_id().getIngredients() + ": " + String.valueOf(each.getQuantity_needed()));
                }
                ingredient.getStyleClass().add("Ingredient");
                ingredient.setMinHeight(Region.USE_PREF_SIZE);
                ingredient.setWrapText(true);
            }
            Label instructions = new Label();
            item.getChildren().add(instructions);
            instructions.getStyleClass().add("Item_instruction");
            instructions.setWrapText(true);
            instructions.setMinHeight(Region.USE_PREF_SIZE);
            instructions.setText(recipeList.get(i).getKey().getInstruction());
            innerVBox.getChildren().add(item);
        }
    }
}