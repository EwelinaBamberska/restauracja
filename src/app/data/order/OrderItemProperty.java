package app.data.order;

import javafx.beans.property.SimpleStringProperty;

public class OrderItemProperty {
    private SimpleStringProperty managerName;
    private SimpleStringProperty orderId;
    private SimpleStringProperty delivered;

    public OrderItemProperty(int managerName, int orderId, boolean delivered){
        this.managerName = new SimpleStringProperty(String.valueOf(managerName));
        this.orderId = new SimpleStringProperty(String.valueOf(orderId));
        this.delivered = (delivered) ? new SimpleStringProperty("Dostarczono") : new SimpleStringProperty("Otwarte");
    }

    public String getManagerName() {
        return managerName.get();
    }

    public SimpleStringProperty managerNameProperty() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName.set(managerName);
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

    public String getDelivered() {
        return delivered.get();
    }

    public SimpleStringProperty deliveredProperty() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered.set(delivered);
    }
}
