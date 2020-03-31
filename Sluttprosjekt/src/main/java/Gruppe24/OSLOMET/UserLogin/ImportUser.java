package Gruppe24.OSLOMET.UserLogin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ImportUser {

    public static ObservableList<User> readUser(String path) throws IOException {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(path))){
            String line;

            while ((line = reader.readLine()) != null) {
                userList.add(ParseUser.parseUser(line));
            }
        }
        return userList;
    }
}
