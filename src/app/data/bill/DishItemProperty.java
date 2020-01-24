package app.data.bill;

import javafx.beans.property.SimpleStringProperty;

public class DishItemProperty {
    private SimpleStringProperty billId;
    private SimpleStringProperty amount;
    private SimpleStringProperty name;

    public DishItemProperty(int billId, int amount, String name){
        this.billId = new SimpleStringProperty(String.valueOf(billId));
        this.amount = new SimpleStringProperty(String.valueOf(amount));
        this.name = new SimpleStringProperty(name);
    }

    public String getBillId() {
        return billId.get();
    }

    public SimpleStringProperty billIdProperty() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId.set(billId);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
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
}
