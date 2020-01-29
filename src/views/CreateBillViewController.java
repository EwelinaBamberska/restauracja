package views;

import app.data.bill.Bill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class CreateBillViewController  {

    @FXML
    private Button backToMenuButton;

    @FXML
    private TextField tableNumberTextField;

    public void go_to_order_items_view(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "billInfoView.fxml", 800.0, 600.0, getClass());
    }

    public void go_to_main_menu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800.0, 600.0, getClass());

    }


}