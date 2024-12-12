package com.sshgroup.ssh_fridge_contents_tracker;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {
    @FXML GridPane window;
    List<Map.Entry<Recipe, Double>> recipeList = MainApplication.recipeList;
    final int RECIPEFRAMESIZE = 1;
    @FXML
    void initialize() {
        GridPane innerGrid = new GridPane();
        ScrollPane sp = new ScrollPane(innerGrid);
        sp.prefWidthProperty().bind(window.widthProperty()); //makes width of scrollpane same as window
        window.add(sp, 0, 2);
        innerGrid.prefWidthProperty().bind(sp.widthProperty()); //makes width of innerGrid same as scrollpane
        innerGrid.minHeight(2500);
        //System.out.println("Recipes no.: "+String.valueOf(recipeList.size()));
        for (int i = 0; i < recipeList.size(); i+=RECIPEFRAMESIZE) {
            Label item = new Label();
            item.setPadding(new Insets(10, 0, 10, 0));
            item.setWrapText(true);
            item.setText(recipeList.get(i).getKey().getName()+"\n"+recipeList.get(i).getKey().getInstruction()+"\nNumber of servings: "+String.valueOf(recipeList.get(i).getKey().getNumber_servings())+"\nPrice of items not in the fridge: "+String.valueOf(recipeList.get(i).getValue())+"\n");
            innerGrid.add(item, 0, i);
        }

    }
}