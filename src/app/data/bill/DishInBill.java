package app.data.bill;

public class DishInBill {
    private int billId;
    private int amount;
    private String itemName;

    public DishInBill(int billId, int amount, String itemName){
        this.amount = amount;
        this.billId = billId;
        this.itemName = itemName;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
