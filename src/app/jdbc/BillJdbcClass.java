package app.jdbc;

import app.data.bill.Bill;

import java.util.ArrayList;

public class BillJdbcClass {
    private static BillJdbcClass instance = new BillJdbcClass();
    public static BillJdbcClass getInstance(){
        return instance;
    }

    private BillJdbcClass(){
    }

    public ArrayList<Bill> getBills(boolean selected, boolean paidCheckBoxSelected, boolean onlyManagerCheckBoxSelected) {
        ArrayList<Bill> bills = new ArrayList<>();
        return bills;
    }

    public Bill getBillByID(Integer valueOf) {
        Bill bill = null;
        return bill;
    }
}
