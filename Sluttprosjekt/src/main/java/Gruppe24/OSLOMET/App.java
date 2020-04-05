package Gruppe24.OSLOMET;

import Gruppe24.OSLOMET.Car.Car;
import Gruppe24.OSLOMET.Car.CarCategory;
import Gruppe24.OSLOMET.Car.Carparts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();



        Car smallWheels = new Carparts("Small Wheels", 1000);
        Car mediumWheels = new Carparts("Medium Wheels", 1000);
        Car largeWheels = new Carparts("Big Wheels", 1000);

        Car wheels = new CarCategory("Wheels");
        wheels.add(smallWheels);
        wheels.add(mediumWheels);
        wheels.add(largeWheels);

        Car colorRed = new Carparts("Red", 2000);
        Car colorGreen = new Carparts("Green", 3000);

        Car color = new CarCategory("Color");
        color.add(colorRed);
        color.add(colorGreen);



    }

}