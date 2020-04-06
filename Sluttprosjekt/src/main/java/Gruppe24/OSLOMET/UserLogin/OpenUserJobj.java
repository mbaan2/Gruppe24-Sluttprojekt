package Gruppe24.OSLOMET.UserLogin;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.HashMap;

public class OpenUserJobj {
    public static HashMap<String, String> userList(Path path){
        HashMap<String, String> userBase = new HashMap<>();

        try (FileInputStream in = new FileInputStream(String.valueOf(path));
             ObjectInputStream oin = new ObjectInputStream(in)) {
                userBase = (HashMap<String, String>) oin.readObject();
            } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return userBase;
    }
}
