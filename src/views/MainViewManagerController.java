package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainViewManagerController {
    @FXML
    private Button create_bill_button;
    @FXML
    private Button take_item_from_magazine_button;
    @FXML
    private Button work_hours_button;
    @FXML
    private Button log_out_button;

    @FXML
    private Button menu_button;
    @FXML
    private Button orders_button;
    @FXML
    private Button magazine_button;
    @FXML
    private Button workers_button;

    @FXML
    private ListView open_bills_list;


    public void go_to_create_bill_view(ActionEvent actionEvent) {
    }

    public void go_to_take_item_from_magazine(ActionEvent actionEvent) {
    }

    public void show_work_hours(ActionEvent actionEvent) {
    }

    public void log_out(ActionEvent actionEvent) {
    }

    public void go_to_menu_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "menuView.fxml", 800, 600, getClass());
    }

    public void go_to_orders_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "ordersView.fxml", 800, 600, getClass());
    }

    public void go_to_workers_options(ActionEvent actionEvent) {
    }

    public void go_to_magazine_options(ActionEvent actionEvent) {
    }
}
