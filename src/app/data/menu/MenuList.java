package app.data.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuList {
    private static MenuList menuList = new MenuList();
    private List<MenuPosition> menuPositionList = new ArrayList<>();
    private boolean downloadedData = false;

    private MenuList(){ }

    public List<MenuPosition> getMenuPositionList() {
        return menuPositionList;
    }

    public void setMenuPositionList(List<MenuPosition> menuPositionList) {
        this.menuPositionList = menuPositionList;
    }

    public void addMenuPosition(MenuPosition position){
        this.menuPositionList.add(position);
    }

    public static MenuList getInstance(){
        return menuList;
    }

    public boolean isDownloadedData() {
        return downloadedData;
    }

    public void setDownloadedData(boolean downloadedData) {
        this.downloadedData = downloadedData;
    }

    public void removePosition(String name) {
        MenuPosition positionToDelete = null;
        for (MenuPosition p:
             menuPositionList) {
            if(name.equals(p.getName())) {
                positionToDelete = p;
                break;
            }
        }
        menuPositionList.remove(positionToDelete);
    }

    public void modifyPriceOfPosisiton(String name, Double newPrice) {
        for (MenuPosition p:
                menuPositionList) {
            if(name.equals(p.getName())) {
                p.setPrice(newPrice);
                break;
            }
        }
    }
}
