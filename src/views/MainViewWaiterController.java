package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

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
    private TableView openBillsList;
    @FXML
    private CheckBox toPaidCheckBox;
    @FXML
    private CheckBox paidCheckBox;

    @FXML
    public void initialize(){
        BillTableViewController.initializeBillTable(openBillsList, getClass());
        BillTableViewController.showBills(toPaidCheckBox.isSelected(), paidCheckBox.isSelected(),
                true, openBillsList);
    }

    public void go_to_create_bill_view(ActionEvent actionEvent) {
        String table;   //w tym Stringu jest przechowana informacja o numerze stolika, trzeba by jeszcze wydobyć id_rachunku, żeby dodawanie potraw miało jakąś podstawę do tego, do którego rachunku je dodajemy
        //w sumie,możemy w billInfoController jako ID_rachunku użyć obecnej wartości ID_rachunku_seq, tyle że trzeba będzie zrobić odrębną funkcję dla modyfikacji gdy użytkownik kliknie na liście
        //na razie nie grzebię, myślę że warto żebyś sama na to rzuciła okiem, jako że ten widok to twoje dzieło, a rozwiązań może być kilka
        table = GetTableNumber.getTableNumber();
        app.jdbc.BillJdbcClass.getInstance().createBill(table);
        JavaFXUtils.changeScene(actionEvent, "billInfoView.fxml",800, 600, getClass());

    }

    public void go_to_take_item_from_magazine(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "takeItemFromMagazineView.fxml", 800.0, 600.0, getClass());

    }

    public void show_work_hours(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "showWorkHours.fxml", 800.0, 600.0, getClass());
    }

    public void log_out(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "loginToApp.fxml", 800, 600, getClass());
    }

    public void showSelectedBills(ActionEvent actionEvent) {
        BillTableViewController.showBills(toPaidCheckBox.isSelected(), paidCheckBox.isSelected(),
                true, openBillsList);
    }
}
