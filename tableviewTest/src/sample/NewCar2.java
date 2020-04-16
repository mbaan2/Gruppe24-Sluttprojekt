package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class NewCar2 {
        public SimpleStringProperty user;
        public SimpleStringProperty name;
        public Car[] features;

        public NewCar2 (String user, String name){
            this.user = new SimpleStringProperty(user);
            this.name = new SimpleStringProperty(name);
            this.features = new Car[4];
        }

    public void set(int index, Car carparts){
            features[index] = carparts;
    }

    public String[] getAddOne(){
            int index = features[3].size();
            String[] addones = new String[index];
            for(int i=0; i<features[3].size(); i++){
                addones[i] = features[3].getByIndex(i).getName();
                System.out.println(features[3].getByIndex(i).getName());
            }
            return addones;
    }


    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getFuel(){
        String name = "";
        if(features[0] != null){
            name = features[0].getName();
        }

        return name;    }

    public String getWheels(){
        String name = "";
        if(features[1] != null){
            name = features[1].getName();
        }

        return name;
    }

    public String getColor(){
            String name = "";
            if(features[2] != null){
                name = features[2].getName();
            }

            return name;

    }

    public int size(){
        return features[4].size();
    }


}
