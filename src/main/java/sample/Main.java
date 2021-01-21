package sample;


import BD.CreateDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //CreateDatabase createDatabase=new CreateDatabase();
        //createDatabase.create();
        FXMLLoader loader=new FXMLLoader();
        URL url = new File("src/main/resources/sample.fxml").toURI().toURL();;
        loader.setLocation(url);
        Parent root = loader.load();
        primaryStage.setTitle("CarsOla");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
        Controller c =loader.getController();
        primaryStage.setOnCloseRequest(eu->{
            eu.consume();
            try {
                c.close(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);

    }
}
