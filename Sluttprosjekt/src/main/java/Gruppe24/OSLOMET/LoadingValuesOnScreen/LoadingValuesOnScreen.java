package Gruppe24.OSLOMET.LoadingValuesOnScreen;

import Gruppe24.OSLOMET.Car.Car;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class LoadingValuesOnScreen {

    public static List<RadioButton> creatingList(List<RadioButton> buttonList, List<Car> valuesList, ToggleGroup toggleGroup){

        for(int i = 0; i< valuesList.size(); i++){
            String str = "";
            str = valuesList.get(i).getName();
            RadioButton newButton = new RadioButton(str);
            newButton.setToggleGroup(toggleGroup);
            buttonList.add(newButton);
            newButton.setStyle("-fx-padding: 20px; -fx-min-width: 175px;");
        }
        return buttonList;
    }

    public static List<CheckBox> creatingList(List<CheckBox> buttonList, List<Car> valuesList){

        for(int i = 0; i< valuesList.size(); i++){
            String str = "";
            str = valuesList.get(i).getName();
            CheckBox newButton = new CheckBox(str);
            buttonList.add(newButton);
            newButton.setStyle("-fx-padding: 20px; -fx-min-width: 175px;");
        }
        return buttonList;
    }

    public static <T> VBox returnVbox(List<T> valuesList, VBox vbox){
        int antall = valuesList.size();
        int numberOfRows = (int) Math.ceil(antall/3);
        int rows = 0;
        int numberOfButtons = 0;
        int numberOfButtonPerLine = 3;

        while (rows <= numberOfRows){
            HBox newHBox = new HBox();
            newHBox.setStyle("-fx-min-height: 40px; -fx-max-height: 40px");
            for(int i = 0; i< (numberOfButtonPerLine); i++){
                if(numberOfButtons < valuesList.size()) {
                    newHBox.getChildren().add((Node) valuesList.get(numberOfButtons));
                    numberOfButtons++;
                }
            }
            vbox.getChildren().add(newHBox);
            rows++;
        }

        return vbox;

    }


}
