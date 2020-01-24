package views;

<<<<<<< HEAD
import app.data.bill.BillItemProperty;
import app.data.worker.LoggedWorker;
import app.data.worker.WorkerList;
=======
import app.data.bill.Bill;
import app.data.bill.BillItemProperty;
import app.data.bill.BillList;
import app.data.worker.LoggedWorker;
import app.data.worker.WorkerItemProperty;
import app.data.worker.WorkerList;
import app.jdbc.BillJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
import java.util.ResourceBundle;

public class MainViewManagerController implements Initializable {
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
<<<<<<< HEAD
    @FXML
    private CheckBox toPaidCheckBox;
    @FXML
    private CheckBox paidCheckBox;
    @FXML
    private CheckBox onlyManagerCheckBox;
    @FXML
    private TableView billsTableView;

=======
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
    @FXML
    private TableView open_bills_list;
    @FXML
    private CheckBox toPaidCheckBox;
    @FXML
    private CheckBox paidCheckBox;
    @FXML
    private CheckBox onlyManagerCheckBox;
    @FXML
    private TableView billsTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD
        if(!LoggedWorker.getInstance().isIf_manager()) {
            onlyManagerCheckBox.setVisible(false);
            onlyManagerCheckBox.setSelected(true);
        }
        initializeBillTable();
        showBills();
    }

    private void showBills() {
    }

    private void initializeBillTable() {
        TableColumn<BillItemProperty, String> billIDColumn = new TableColumn<>("Id rachunku");
        TableColumn<BillItemProperty, String> sumPriceColumn = new TableColumn<>("Sumaryczna cena");
        TableColumn<BillItemProperty, String> tableNumberColumn = new TableColumn<>("Nr stolika");
        TableColumn<BillItemProperty, String> dateColumn = new TableColumn<>("Data utworzenia");
        TableColumn<BillItemProperty, String> ifPaidColumn = new TableColumn<>("Stan");
        TableColumn<BillItemProperty, String> rateColumn = new TableColumn<>("Ocena pracownika");
        billsTableView.getColumns().addAll(billIDColumn, sumPriceColumn, tableNumberColumn);

        if(LoggedWorker.getInstance().isIf_manager()){
            TableColumn<BillItemProperty, String> workerIDColumn = new TableColumn<>("Id pracownika");
            workerIDColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("workerId"));
            billsTableView.getColumns().add(workerIDColumn);
        }
        billsTableView.getColumns().addAll(dateColumn, ifPaidColumn, rateColumn);
        billIDColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("billID"));
        sumPriceColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("sumPrice"));
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("tableNumber"));



        //add button columns

        billsTableView.setRowFactory( tv -> {
            TableRow<BillItemProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BillItemProperty rowData = row.getItem();
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "billInfoView.fxml"
                            )
                    );

                    Stage stage = new Stage(StageStyle.DECORATED);
                    try {
                        stage.setScene(
                                new Scene(
                                        (Pane) loader.load()
                                )
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    WorkerInfoController controller =
//                            loader.<WorkerInfoController>getController();
//                    controller.initData(WorkerList.getInstance().getWorkerById(Integer.valueOf(rowData.getId_prac())));

                    stage.show();
                    //return stage
                }
            });
            return row;
        });
=======
//            onlyManagerCheckBox.setVisible(false);
//            onlyManagerCheckBox.setSelected(true);
        BillTableViewController.initializeBillTable(billsTableView, getClass());
        BillTableViewController.showBills(toPaidCheckBox.isSelected(), paidCheckBox.isSelected(),
                onlyManagerCheckBox.isSelected(), billsTableView);
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
    }

    public void go_to_create_bill_view(ActionEvent actionEvent) {
    }

    public void go_to_take_item_from_magazine(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "takeItemFromMagazineView.fxml", 800, 600, getClass());
    }

    public void show_work_hours(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "showWorkHours.fxml",800, 600, getClass());
    }

    public void log_out(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "loginToApp.fxml", 800, 600, getClass());
    }

    public void go_to_menu_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "menuView.fxml", 800, 600, getClass());
    }

    public void go_to_orders_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "ordersView.fxml", 800, 600, getClass());
    }

    public void go_to_workers_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "allWorkersView.fxml", 800, 600, getClass());
    }

    public void go_to_magazine_options(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "magazineView.fxml", 800, 600, getClass());
    }

<<<<<<< HEAD

    public void showSelectedBills(ActionEvent actionEvent) {
=======
    public void showSelectedBills(ActionEvent actionEvent) {
        BillTableViewController.showBills(toPaidCheckBox.isSelected(), paidCheckBox.isSelected(),
                onlyManagerCheckBox.isSelected(), billsTableView);
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
    }
}
