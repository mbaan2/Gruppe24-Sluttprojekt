package Gruppe24.OSLOMET.UserLogin;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class OpenUserJobj {
    public static HashMap<String, String> userList(Path path){
        HashMap<String, String> userBase = new HashMap<>();

        try (InputStream in = Files.newInputStream(path);
            ObjectInputStream oin = new ObjectInputStream(in)) {
                userBase = (HashMap<String, String>) oin.readObject();
            } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return userBase;
    }
}
