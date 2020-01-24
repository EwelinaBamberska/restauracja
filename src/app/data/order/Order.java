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

    public Order(int managerId , int orderId, String ifDelivered){
        this.managerId = managerId;
        this.orderId = orderId;
        this.ifDelivered = ifDelivered.equals("T");
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

    public boolean isProductsDownloaded() {
        return productsDownloaded;
    }

    public void setProductsDownloaded(boolean productsDownloaded) {
        this.productsDownloaded = productsDownloaded;
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


}
