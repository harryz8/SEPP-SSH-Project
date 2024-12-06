package com.sshgroup.ssh_fridge_contents_tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws MalformedURLException {
        if ((args.length > 0) && (args[0].toLowerCase().equals("i"))) {
            launch();
        }
        else {
            System.out.println("Command line mode goes here");
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
            System.exit(0);
        }
    }
}