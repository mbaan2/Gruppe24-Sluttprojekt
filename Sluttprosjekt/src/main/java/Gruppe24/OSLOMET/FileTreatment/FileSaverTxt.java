package Gruppe24.OSLOMET.FileTreatment;

import javafx.scene.control.Label;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaverTxt{
    public static void overwrite(String str, File selectedFile, Label lbl, String username) {
        try {
            FileWriter fw = new FileWriter(selectedFile);
            fw.write(str);
            fw.close();
            lbl.setText("Car(s) written to your file called "  + username + "sCars.txt");
        } catch (IOException e) {
            lbl.setText(e.getMessage());
        }
    }

    public static void append(String str, File selectedFile, Label lbl, String username) {
        try {
            FileWriter fw = new FileWriter(selectedFile, true);
            fw.write(str);
            fw.close();
            lbl.setText("Car(s) added to your current file called "  + username + "sCars.txt");
        } catch (IOException e) {
            lbl.setText(e.getMessage());
        }
    }
}

