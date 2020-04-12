package Gruppe24.OSLOMET.UserLogin;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class OpenUserJobj {
    // Not used
    /*
    public static HashMap<String, String> userListOpenJOBJ(Path path){
        HashMap<String, String> userBase = null;

        try (FileInputStream in = new FileInputStream("users.jobj");
             ObjectInputStream oin = new ObjectInputStream(in)) {
                userBase = (HashMap) oin.readObject();
                in.close();
                oin.close();
            } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Set set = userBase.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry mentry = (Map.Entry)iterator.next();

        }
        
        System.out.println(userBase.get(1));


        return userBase;
    }
    */
}
