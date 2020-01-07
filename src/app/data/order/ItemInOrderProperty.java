package app.data.order;

import javafx.beans.property.SimpleStringProperty;

public class ItemInOrderProperty {
    private SimpleStringProperty name;
    private SimpleStringProperty amountOfProduct;
    private SimpleStringProperty orderId;

    public ItemInOrderProperty(String name, int amount, int orderId){
        this.name = new SimpleStringProperty(name);
        this.amountOfProduct = new SimpleStringProperty(String.valueOf(amount));
        this.orderId = new SimpleStringProperty(String.valueOf(orderId));
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAmountOfProduct() {
        return amountOfProduct.get();
    }

    public SimpleStringProperty amountOfProductProperty() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(String amountOfProduct) {
        this.amountOfProduct.set(amountOfProduct);
    }

    public String getOrderId() {
        return orderId.get();
    }

    public SimpleStringProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId.set(orderId);
    }
}
