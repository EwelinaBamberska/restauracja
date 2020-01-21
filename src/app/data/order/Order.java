package app.data.order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int managerId;
    private String managerName;
    private int orderId;
    private boolean ifDelivered;

    private List<ItemInOrder> products = new ArrayList<>();
    private boolean productsDownloaded = false;

    public Order(int managerId , int orderId, String ifDelivered, String managerName){
        this.managerId = managerId;
        this.orderId = orderId;
        this.ifDelivered = ifDelivered.equals("T");
        this.managerName = managerName;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isIfDelivered() {
        return ifDelivered;
    }

    public void setIfDelivered(boolean ifDelivered) {
        this.ifDelivered = ifDelivered;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public List<ItemInOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ItemInOrder> products) {
        this.products = products;
    }

    public void addItemToList(ItemInOrder item){
        products.add(item);
    }

    public boolean isDownloadedData() {
        return productsDownloaded;
    }

    public void setDownloadedData(boolean productsDownloaded){
        this.productsDownloaded = productsDownloaded;
    }


    public void deleteItem(String name) {
        ItemInOrder itemToDelete = null;
        for (ItemInOrder item:
             products) {
            if(item.getName().equals(name)){
                itemToDelete = item;
                break;
            }
        }
        if(itemToDelete != null)
            products.remove(itemToDelete);
    }

    public List<ItemInOrder> getProductsByRegex(String text) {
        List<ItemInOrder> items = new ArrayList<>();
        for (ItemInOrder item:
             products) {
            if(item.getName().toUpperCase().contains(text.toUpperCase())){
                items.add(item);
            }
        }
        return items;
    }
}
