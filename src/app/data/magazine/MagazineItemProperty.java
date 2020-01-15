package app.data.magazine;

import javafx.beans.property.SimpleStringProperty;

public class MagazineItemProperty {
    private SimpleStringProperty amount;
    private SimpleStringProperty name;

    public MagazineItemProperty(int amount, String name){
        this.amount = new SimpleStringProperty(String.valueOf(amount));
        this.name = new SimpleStringProperty(name);
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
