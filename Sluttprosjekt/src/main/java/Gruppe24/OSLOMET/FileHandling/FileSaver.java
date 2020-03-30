package Gruppe24.OSLOMET.FileHandling;

import Gruppe24.OSLOMET.Car.CarpartOption;

import java.io.IOException;
import java.util.List;

public interface FileSaver{
    void save(List<CarpartOption> carpartOptionList, String carpartOptionListName) throws IOException;
}
