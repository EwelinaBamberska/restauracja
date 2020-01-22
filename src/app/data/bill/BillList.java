package app.data.bill;

import java.util.ArrayList;
import java.util.List;

public class BillList {
    private static BillList instance = new BillList();
    private List<Bill> bills = new ArrayList<>();
    private BillList(){}

    public static BillList getInstance() {
        return instance;
    }

    public static void setInstance(BillList instance) {
        BillList.instance = instance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Bill getBillByID(Integer valueOf) {
        for (Bill b:
             bills) {
            if(b.getBillId() == valueOf)
                return b;
        }
        return null;
    }
}
