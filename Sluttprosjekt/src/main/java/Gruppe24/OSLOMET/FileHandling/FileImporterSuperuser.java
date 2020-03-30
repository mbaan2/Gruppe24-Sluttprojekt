package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.OptionBtn;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileImporterSuperuser implements FileImporter{

    @Override
    public void open(ArrayList<OptionBtn> optionBtnList, Path filepath) throws IOException{
        try(InputStream is = Files.newInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(is)){
            ArrayList<OptionBtn> radioButtons = (ArrayList<OptionBtn>) ois.readObject();
            optionBtnList.clear();
            radioButtons.addAll(optionBtnList);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            throw new IOException("Could not load. Check stack trace.");
        }
    }
}
