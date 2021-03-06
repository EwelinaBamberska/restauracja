package views;

import app.data.order.Order;
import app.data.order.OrderItemProperty;
import app.jdbc.OrderJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersViewController implements Initializable {
    @FXML
    private HBox centre_orders_view_hbox;
    @FXML
    private TableView orders_items_table;
    @FXML
    private Button back_to_menu_button;
    @FXML
    private Button create_new_order_button;
    @FXML
    private CheckBox unclaimedOrdersCheckBox;
    @FXML
    private CheckBox claimedOrdersCheckBox;
    @FXML
    private CheckBox myOrdersCheckBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        unclaimedOrdersCheckBox.setSelected(true);
        myOrdersCheckBox.setSelected(true);
        initializeTableViews();
        showOrders();

    }

    private void showOrders() {
        ArrayList<Order> ordersInDB = OrderJdbcClass.getInstance().getOrdersFromDatabase(unclaimedOrdersCheckBox.isSelected(), claimedOrdersCheckBox.isSelected(), myOrdersCheckBox.isSelected());
        orders_items_table.getItems().clear();
        ObservableList<OrderItemProperty> orderItems = FXCollections.observableArrayList();
        ordersInDB.forEach(position -> orderItems.add(new OrderItemProperty(position.getManagerId(), position.getOrderId(), position.isIfDelivered())));
        orders_items_table.setItems(orderItems);
    }

    private void initializeTableViews() {
        TableColumn<OrderItemProperty,String> managerNameColumn = new TableColumn<>("Manager ID");
        TableColumn<OrderItemProperty, String> orderIdColumn = new TableColumn<>("Order ID");

        orders_items_table.getColumns().addAll(orderIdColumn, managerNameColumn);

        managerNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemProperty, String>("managerName"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemProperty, String>("orderId"));

        //add button columns
        TableColumn<OrderItemProperty, Void> deleteButton = new TableColumn<>("Claim order.");

        orders_items_table.setRowFactory( tv -> {
            TableRow<OrderItemProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    OrderItemProperty rowData = row.getItem();

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "orderInfoView.fxml"
                            )
                    );

                    Stage stage = (Stage) orders_items_table.getScene().getWindow();
                    try {
                        stage.setScene(
                                new Scene(
                                        (Pane) loader.load()
                                )
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    OrderInfoViewController controller =
                            loader.<OrderInfoViewController>getController();
                    controller.initData(OrderJdbcClass.getInstance().getOrder(Integer.valueOf(rowData.getOrderId())));

                    stage.show();
                }
            });
            return row ;
        });
    }


    public void go_to_menu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }

    public void create_new_order(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "createOrderView.fxml", 800, 600, getClass());
    }

    public void showOrdersAction(ActionEvent actionEvent) {
        showOrders();
    }

}
