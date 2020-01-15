package app.data.magazine;

import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.List;

public class MagazineList {
    private List<MagazineItem> itemsInMagazine = new ArrayList<>();
    private static MagazineList instance;

    public MagazineList(){
        instance = this;
    }

    public static MagazineList getInstance(){
        return instance;
    }

    public List<MagazineItem> getItemsInMagazine() {
        return itemsInMagazine;
    }

    public void setItemsInMagazine(List<MagazineItem> itemsInMagazine) {
        this.itemsInMagazine = itemsInMagazine;
    }

    public List<MagazineItem> getItemsInMagazineRegex(String regexToFind) {
        List<MagazineItem> items = new ArrayList<>();
        for (MagazineItem m:
             itemsInMagazine) {
            if(m.getName().contains(regexToFind))
                items.add(m);
        }
        return items;
    }
}
