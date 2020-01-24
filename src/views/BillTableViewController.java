package views;

import app.data.bill.Bill;
import app.data.bill.BillItemProperty;
import app.jdbc.BillJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class BillTableViewController {

    public static void initializeBillTable(TableView tableView, Class clazz) {
        TableColumn<BillItemProperty, String> billIDColumn = new TableColumn<>("Id rachunku");
        TableColumn<BillItemProperty, String> sumPriceColumn = new TableColumn<>("Sumaryczna cena");
        TableColumn<BillItemProperty, String> tableNumberColumn = new TableColumn<>("Nr stolika");
        TableColumn<BillItemProperty, String> dateColumn = new TableColumn<>("Data utworzenia");
        TableColumn<BillItemProperty, String> ifPaidColumn = new TableColumn<>("Stan");
        TableColumn<BillItemProperty, String> rateColumn = new TableColumn<>("Ocena pracownika");
        tableView.getColumns().addAll(billIDColumn, sumPriceColumn, tableNumberColumn);

        TableColumn<BillItemProperty, String> workerIDColumn = new TableColumn<>("Id pracownika");
        workerIDColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("workerId"));
        tableView.getColumns().add(workerIDColumn);

        tableView.getColumns().addAll(dateColumn, ifPaidColumn, rateColumn);

        billIDColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("billID"));
        sumPriceColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("sumPrice"));
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("tableNumber"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("date"));
        ifPaidColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("ifPaid"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<BillItemProperty, String>("rate"));



        //add button columns

        tableView.setRowFactory( tv -> {
            TableRow<BillItemProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BillItemProperty rowData = row.getItem();
                    FXMLLoader loader = new FXMLLoader(
                            clazz.getResource(
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

                    BillInfoViewController controller =
                            loader.<BillInfoViewController>getController();
                    controller.initData(BillJdbcClass.getInstance().getBillByID(Integer.valueOf(rowData.getBillID())));

                    stage.show();
                    //return stage
                }
            });
            return row;
        });
    }

    public static void showBills(boolean toPay, boolean paid, boolean onlyMine, TableView tableView) {
        ArrayList<Bill> bills = BillJdbcClass.getInstance().getBills(toPay, paid, onlyMine);
        tableView.getItems().clear();
        ObservableList<BillItemProperty> billsList = FXCollections.observableArrayList();
        bills.forEach(position -> billsList.add(new BillItemProperty(position.getBillId(), position.getRate(),
                position.getSumPrice(), position.getTableNumber(), position.getWorkerId(), position.getBillDate(), position.isIfPaid())));
        tableView.setItems(billsList);
    }
}
