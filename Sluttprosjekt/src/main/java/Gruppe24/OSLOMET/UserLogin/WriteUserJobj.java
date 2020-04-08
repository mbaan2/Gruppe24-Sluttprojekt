package Gruppe24.OSLOMET.UserLogin;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class WriteUserJobj {

    public static void SaveUser(HashMap<String, String> userList) throws IOException {


        try {
            FileOutputStream os = new FileOutputStream("users.jobj");
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(userList);
            oos.flush();
            oos.close();
            os.close();
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
