package views;

import app.data.bill.Bill;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class BillInfoViewController implements Initializable {
    private Bill actualBill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Bill billByID) {
        this.actualBill = billByID;
    }

}
