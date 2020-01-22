package app.jdbc;

import app.data.magazine.MagazineItem;
import javafx.scene.control.TableView;

public class MagazineJdbcClass {
    private static MagazineJdbcClass instance = new MagazineJdbcClass();
    
    private MagazineJdbcClass(){}
    
    public static MagazineJdbcClass getInstance() {
        return instance;
    }

    public void getItems() {
    }

    public void addItem(MagazineItem newItem) {
    }
}
