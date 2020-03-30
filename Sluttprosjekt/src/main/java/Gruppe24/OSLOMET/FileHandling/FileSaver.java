package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.OptionBtn;
import javafx.scene.control.RadioButton;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public interface FileSaver{
    void save(ArrayList<OptionBtn> optionBtnList, Path filepath) throws IOException;
}
