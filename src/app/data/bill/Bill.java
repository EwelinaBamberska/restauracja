package app.data.bill;

import java.sql.Date;

public class Bill {
    private int billId;
    private int rate;
    private float sumPrice;
    private int tableNumber;
    private int workerId;
    private Date billDate;
    private boolean ifPaid;

    public Bill(int billId, int rate, float sumPrice, int tableNumber, int workerId, Date billDate, String ifPaid){
        this.billId = billId;
        this.rate = rate;
        this.sumPrice = sumPrice;
        this.tableNumber = tableNumber;
        this.workerId = workerId;
        this.billDate = billDate;
        this.ifPaid = ifPaid.equals("T");
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public float getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(float sumPrice) {
        this.sumPrice = sumPrice;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public boolean isIfPaid() {
        return ifPaid;
    }

    public void setIfPaid(boolean ifPaid) {
        this.ifPaid = ifPaid;
    }
}
