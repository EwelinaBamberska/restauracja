package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainViewWaiterController {
    @FXML
    private Button create_bill_button;
    @FXML
    private Button take_item_from_magazine_button;
    @FXML
    private Button work_hours_button;
    @FXML
    private Button log_out_button;
    @FXML
    private ListView open_bills_list;

    @FXML
    public void initialize(){
        //TODO:show_list
    }

    public void go_to_create_bill_view(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "createBillView.fxml", 800.0, 600.0, getClass());

    }

    public void go_to_take_item_from_magazine(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "takeItemFromMagazineView.fxml", 800.0, 600.0, getClass());

    }

    public void show_work_hours(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "showWorkHours.fxml", 800.0, 600.0, getClass());
    }

    public void log_out(ActionEvent actionEvent) {
        //TODO: log out
    }
}
