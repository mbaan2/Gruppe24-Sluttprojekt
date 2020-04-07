package Gruppe24.OSLOMET.UserLogin;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class WriteUserJobj {

    public static void SaveUser(Path path, HashMap<String, String> userList) throws IOException {


        try {
            FileOutputStream os = new FileOutputStream("users.jobj");
            // Seems to work when you stay in the file. When you go out and want to redo it. it wont add
            // FileOutputStream os = new FileOutputStream(new File("users.jobj"), true);
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
