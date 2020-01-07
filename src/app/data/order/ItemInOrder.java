package app.data.order;

public class ItemInOrder {
    private String name;
    private int amountOfProduct;
    private int orderId;

    public ItemInOrder(String name, int amountOfProduct, int orderId){
        this.name = name;
        this.amountOfProduct = amountOfProduct;
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
