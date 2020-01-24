package app.data.bill;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class BillItemProperty {
    private SimpleStringProperty billID;
    private SimpleStringProperty sumPrice;
    private SimpleStringProperty tableNumber;
    private SimpleStringProperty workerId;
    private SimpleStringProperty date;
    private SimpleStringProperty ifPaid;
    private SimpleStringProperty rate;

    public BillItemProperty(int billId, int rate, float sumPrice,
                            int tableNumber, int workerId, Date date, boolean ifPaid){
        this.billID = new SimpleStringProperty(String.valueOf(billId));
        this.sumPrice = new SimpleStringProperty(String.valueOf(sumPrice));
        this.tableNumber = new SimpleStringProperty(String.valueOf(tableNumber));
        this.workerId = new SimpleStringProperty(String.valueOf(workerId));
        this.date = new SimpleStringProperty(date.toString());
        this.ifPaid = (ifPaid) ? new SimpleStringProperty("Opłacono") : new SimpleStringProperty("Nie opłacono");
        this.rate = (rate != -1) ? new SimpleStringProperty(String.valueOf(rate)): new SimpleStringProperty("");
    }

    public String getBillID() {
        return billID.get();
    }

    public SimpleStringProperty billIDProperty() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID.set(billID);
    }

    public String getSumPrice() {
        return sumPrice.get();
    }

    public SimpleStringProperty sumPriceProperty() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice.set(sumPrice);
    }

    public String getTableNumber() {
        return tableNumber.get();
    }

    public SimpleStringProperty tableNumberProperty() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber.set(tableNumber);
    }

    public String getWorkerId() {
        return workerId.get();
    }

    public SimpleStringProperty workerIdProperty() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId.set(workerId);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getIfPaid() {
        return ifPaid.get();
    }

    public SimpleStringProperty ifPaidProperty() {
        return ifPaid;
    }

    public void setIfPaid(String ifPaid) {
        this.ifPaid.set(ifPaid);
    }

    public String getRate() {
        return rate.get();
    }

    public SimpleStringProperty rateProperty() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate.set(rate);
    }
}
