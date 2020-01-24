package views;

import app.data.bill.Bill;
import app.jdbc.BillJdbcClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RateViewController implements Initializable {
    @FXML
    private RadioButton veryBadRadioButton;
    @FXML
    private RadioButton badRadioButton;
    @FXML
    private RadioButton okRadioButton;
    @FXML
    private RadioButton goodRadioButton;
    @FXML
    private RadioButton veryGoodRadioButton;
    @FXML
    private Button payForBillButton;

    private Bill actualBill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();
        veryBadRadioButton.setToggleGroup(group);
        badRadioButton.setToggleGroup(group);
        okRadioButton.setToggleGroup(group);
        goodRadioButton.setToggleGroup(group);
        veryGoodRadioButton.setToggleGroup(group);
    }

    public void initData(Bill actualBill) {
        this.actualBill = actualBill;
    }

    public void payForBill(ActionEvent actionEvent) {
        int rate = 3;
        if (veryBadRadioButton.isSelected())
            rate = 1;
        if (badRadioButton.isSelected())
            rate = 2;
        if (goodRadioButton.isSelected())
            rate = 4;
        if (veryGoodRadioButton.isSelected())
            rate = 5;

        BillJdbcClass.getInstance().payByBill(actualBill.getBillId(), rate);
        actualBill.setIfPaid(true);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}
