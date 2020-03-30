package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileImporter{
    void open(List<CarpartOption> carpartOptionList, Path filepath) throws IOException;
}
