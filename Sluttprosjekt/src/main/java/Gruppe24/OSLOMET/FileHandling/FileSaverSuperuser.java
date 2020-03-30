package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.OptionBtn;
import javafx.scene.control.RadioButton;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSaverSuperuser implements FileSaver{
    @Override
    public void save(ArrayList<OptionBtn> optionBtnList, Path filepath) throws IOException{
        Path path = Paths.get("optionBtns.jobj");
        try(OutputStream os = Files.newOutputStream(filepath);
            ObjectOutputStream out = new ObjectOutputStream(os)){
            out.writeObject(optionBtnList);
        }
    }
}
