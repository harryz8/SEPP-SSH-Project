package com.sshgroup.ssh_fridge_contents_tracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class MainController {
    @FXML GridPane window;
    ArrayList<Recipe> recipeList = new ArrayList<>();
    final int RECIPEFRAMESIZE = 1;
    @FXML
    void initialize() {
        GridPane innerGrid = new GridPane();
        ScrollPane sp = new ScrollPane(innerGrid);
        sp.prefWidthProperty().bind(window.widthProperty()); //makes width of scrollpane same as window
        window.add(sp, 0, 2);
        innerGrid.prefWidthProperty().bind(sp.widthProperty()); //makes width of innerGrid same as scrollpane
        //loadRecipes()
        for (int i = 0; i < recipeList.size(); i+=RECIPEFRAMESIZE) {
            Label item = new Label();
            //here fill the label with information stored in the recipe
            innerGrid.add(item, 0, i);
        }

    }
}