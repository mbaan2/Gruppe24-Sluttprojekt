package Gruppe24.OSLOMET.UserLogin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class UserTableView {
    private transient ObservableList<User> list = FXCollections.observableArrayList();

    public void attachTableView(TableView<User> tv) {
        tv.setItems(list);
    }

    public void addElement(User user) {
        list.add(user);
    }

    public void resetTableView() {
        list.clear();
    }

    public void setVisible(TableView<User> tv) {
        tv.setVisible(true);
    }

    public void setNotVisible(TableView<User> tv) {
        tv.setVisible(false);
    }
}
