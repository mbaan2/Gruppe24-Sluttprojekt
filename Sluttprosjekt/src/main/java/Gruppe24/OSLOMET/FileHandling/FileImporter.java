package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.OptionBtn;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface FileImporter{
    void open(ArrayList<OptionBtn> optionBtnList, Path filepath) throws IOException;
}
