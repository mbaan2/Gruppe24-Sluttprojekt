package Gruppe24.OSLOMET.FileTreatment;

import Gruppe24.OSLOMET.Car.Carparts;
import Gruppe24.OSLOMET.Car.NewCar;
import Gruppe24.OSLOMET.ExceptionClasses.OpenFileException;
import Gruppe24.OSLOMET.ExceptionClasses.SaveFileException;
import Gruppe24.OSLOMET.UserLogin.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class FileSaverJobj {
    public static void SaveCarCategory(Path path, List<Carparts> list) throws SaveFileException {
        try {
            OutputStream os = Files.newOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(list);
        } catch (IOException e) {
            throw new SaveFileException("Problems with saving, please close the program and start over. If this happens again contact the developer.");
        }
    }

    public static void SavingCarArray(String path, List<NewCar> carList) throws OpenFileException {
        try{
            ObjectOutputStream os1 = new ObjectOutputStream(new FileOutputStream(path));
            os1.writeObject(carList.get(0));
            os1.close();

            for (int i = 1; i < carList.size(); i++){
                ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream(path, true)){
                    protected void writeStreamHeader() throws IOException{
                        reset();
                    }
                };
                os2.writeObject(carList.get(i));
                os2.close();
            }
        } catch (IOException e){
            throw new OpenFileException("Problems with loading file, please close the program and start over. If this happens again contact the developer.");
        }
    }

    public static void addingOnlyOneCarObject (String path, NewCar carObject) throws IOException {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path, true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            os.writeObject(carObject);
            os.close();
        } catch (IOException e) {
            throw new SaveFileException("Problems with saving, please close the program and start over. If this happens again contact the developer.");        }
    }

    public static void SaveUser(HashMap<String, String> userList) throws SaveFileException {
        try {
            FileOutputStream os = new FileOutputStream(StandardPaths.usersJOBJPath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(userList);
            oos.flush();
            oos.close();
            os.close();
        } catch (IOException e) {
            throw new SaveFileException("Problems with saving, please close the program and start over. If this happens again contact the developer.");
        }
    }

    public static void SaveUserList(List<User> userList) throws SaveFileException {
        try {
            OutputStream os = Files.newOutputStream(Paths.get(StandardPaths.userListJOBJPath));
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(userList);
        } catch (IOException e) {
            throw new SaveFileException("Problems with saving, please close the program and start over. If this happens again contact the developer.");
        }
    }

    public static void SaveSecretQList(List<String> choiceBoxList) throws SaveFileException{
        try {
            FileOutputStream os = new FileOutputStream(StandardPaths.secretQPath);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(choiceBoxList);
            oos.flush();
            oos.close();
            os.close();
        } catch (IOException e) {
            throw new SaveFileException("Problems with saving, please close the program and start over. If this happens again contact the developer.");
        }
    }
}

