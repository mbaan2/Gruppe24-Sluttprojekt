package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<NewCar> table;


    public ObservableList<NewCar> list = FXCollections.observableArrayList();
    public ObservableList<NewCar2> list2 = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setList();

        TableColumn user = new TableColumn("user");
        table.getColumns().add(user);

        TableColumn name = new TableColumn("name");
        table.getColumns().add(name);

        TableColumn fuel = new TableColumn("fuel");
        table.getColumns().add(fuel);

        TableColumn wheels = new TableColumn("wheels");
        table.getColumns().add(wheels);

        TableColumn color = new TableColumn("color");
        table.getColumns().add(color);

        TableColumn addone = new TableColumn("Add one");
        table.getColumns().add(addone);

        //HERE WE CAN ADD AN EXTRA CHECK TO CHECK THROUGH THE LIST SO WE KNOW THE MAX AMOUNT OF ADD ONES ON A CAR
        for (int i = 0; i < 5; i++) {
            TableColumn tc = new TableColumn("Add one" + (i + 1));
            addone.getColumns().add(tc);
        }


        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            fuel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                    return new SimpleStringProperty((p.getValue().getFuel()));
                }
            });

            wheels.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                    return new SimpleStringProperty((p.getValue().getWheels()));
                }
            });

            color.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                    return new SimpleStringProperty((p.getValue().getColor()));
                }
            });


            for (int j = 0; j < list.get(i).adonneFROMCLASS.length; j++) {
                System.out.println(list.get(i).adonneFROMCLASS.length);
                int finalJ = j;
                TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addone.getColumns().get(j);

                tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                        return new SimpleStringProperty(p.getValue().adonneFROMCLASS[finalJ]);
                    }
                });
            }
        }






        /*
        for (int i = 0; i < list2.size(); i++) {

            user.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar2, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar2, String> p) {
                    return new SimpleStringProperty((p.getValue().getUser()));
                }
            });

            name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar2, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar2, String> p) {
                    return new SimpleStringProperty((p.getValue().getName()));
                }
            });

            fuel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar2, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar2, String> p) {
                    System.out.println(p.getValue().getUser());
                    return new SimpleStringProperty((p.getValue().fuel.getName()));
                }
            });

            wheels.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar2, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar2, String> p) {
                    return new SimpleStringProperty((p.getValue().wheels.getName()));
                }
            });

            color.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar2, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar2, String> p) {
                    return new SimpleStringProperty((p.getValue().color.getName()));
                }
            });


            /*
            for (int j = 0; j < list2.get(i).addones.length; j++) {
                System.out.println(list2.get(i).addones.length);
                int finalJ = j;
                TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addone.getColumns().get(j);

                tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar2, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar2, String> p) {
                            return new SimpleStringProperty(p.getValue().addones[finalJ].getName());
                    }
                });
            }
            */


        table.setItems(list);

        }



    public void setList(){

        String[] array1 = {"hei", null};
        String[] array2 = {"hei", "ja"};

        NewCar one = new NewCar("Diesle (2000kr)", "Big Wheels (1000kr)", ("Red (2000kr)"), array1);
        NewCar two = new NewCar("Benzine (2000kr)", "Small Wheels (1000kr)", ("Green (2000kr)"), array2);
        NewCar three = new NewCar("Diesle (2000kr)", "Big Wheels (1000kr)", ("Red (2000kr)"), array1);
        NewCar four = new NewCar("Diesle (2000kr)", "Big Wheels (1000kr)", ("Red (2000kr)"), array1);

        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);

        Car wheelBig = new CarParts("Big Wheels", 1000);
        Car wheelMedium = new CarParts("Medium Wheels", 500);
        Car wheelSmall = new CarParts("Small Wheels", 0);

        Car red = new CarParts("Red", 1000);

        Car diesel = new CarParts("Diesel", 20_000);

        Car gps = new CarParts("GPS", 100);
        Car stereo = new CarParts("Stereo", 500);

        CarCategory addone = new CarCategory("addone");
        addone.add(gps);
        addone.add(stereo);


        NewCar2 aCar = new NewCar2("123", "vroem");
        aCar.set(0, diesel);
        aCar.set(1, wheelBig);
        aCar.set(2, red);
        aCar.set(3, addone);

        NewCar2 aCar2 = new NewCar2("234", "Boem");
        aCar.set(0, diesel);
        aCar.set(1, wheelMedium);
        aCar.set(2, red);
        aCar.set(3, addone);

        list2.add(aCar);
        list2.add(aCar2);

        NewCar five = new NewCar(aCar.getFuel(), aCar.getWheels(), aCar.getColor(), aCar.getAddOne());
        list.add(five);



    }



    /*
    for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            fuel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                    return new SimpleStringProperty((p.getValue().getFuel()));
                }
            });

            wheels.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                    return new SimpleStringProperty((p.getValue().getWheels()));
                }
            });

            color.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                    return new SimpleStringProperty((p.getValue().getColor()));
                }
            });



            for (int j = 0; j < list.get(i).adonneFROMCLASS.length; j++) {
                System.out.println(list.get(i).adonneFROMCLASS.length);
                int finalJ = j;
                TableColumn<NewCar, String> tc = (TableColumn<NewCar, String>) addone.getColumns().get(j);

                tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NewCar, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<NewCar, String> p) {
                            return new SimpleStringProperty(p.getValue().adonneFROMCLASS[finalJ]);
                    }
                });
            }


        }
     */

}
