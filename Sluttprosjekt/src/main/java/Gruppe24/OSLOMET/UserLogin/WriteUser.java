package Gruppe24.OSLOMET.UserLogin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteUser {

    public static void writeString(File selectedFile, String str) throws IOException {
        try {
            FileWriter fw = new FileWriter(selectedFile);
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            throw new IOException("Failed to write on this file");
        }
    }
}