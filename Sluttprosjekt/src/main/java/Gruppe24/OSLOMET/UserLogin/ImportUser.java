package Gruppe24.OSLOMET.UserLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImportUser {

    static ArrayList<User> readUser(ArrayList<User> userList, String path) throws IOException {
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(path))){
            String line;

            while ((line = reader.readLine()) != null) {
                userList.add(ParseUser.parseUser(line));
            }
        }
        return userList;
    }
}
