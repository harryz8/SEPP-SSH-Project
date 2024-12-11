package com.sshgroup.ssh_fridge_contents_tracker;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class MainController {
    @FXML GridPane window;
    ArrayList<Recipe> recipeList = MainApplication.recipeList;
    final int RECIPEFRAMESIZE = 1;
    @FXML
    void initialize() {
        GridPane innerGrid = new GridPane();
        ScrollPane sp = new ScrollPane(innerGrid);
        sp.prefWidthProperty().bind(window.widthProperty()); //makes width of scrollpane same as window
        window.add(sp, 0, 2);
        innerGrid.prefWidthProperty().bind(sp.widthProperty()); //makes width of innerGrid same as scrollpane
        innerGrid.prefHeight(1200);
        System.out.println("Recipes no.: "+String.valueOf(recipeList.size()));
        for (int i = 0; i < recipeList.size(); i+=RECIPEFRAMESIZE) {
            Label item = new Label();
            item.setPadding(new Insets(10, 0, 10, 0));
            item.setWrapText(true);
            item.setText(recipeList.get(i).getName()+"\n"+recipeList.get(i).getInstruction()+"\nNumber of servings: "+String.valueOf(recipeList.get(i).getNumber_servings())+"\n");
            innerGrid.add(item, 0, i);
        }

    }
}