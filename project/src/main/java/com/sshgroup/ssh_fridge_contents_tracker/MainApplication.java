package com.sshgroup.ssh_fridge_contents_tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_PASSWORD;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_URL;
import static org.hibernate.cfg.JdbcSettings.JAKARTA_JDBC_USER;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import java.io.IOException;
import java.net.MalformedURLException;

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
        //CacheMap.cache.close();
        super.stop();
    }

    public static void main(String[] args) throws MalformedURLException {
        if ((args.length > 0) && (args[0].toLowerCase().equals("i"))) {
            launch();
        }
        else {
            CacheMap.cache.load();
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