package views;

import app.data.order.ItemInOrderProperty;
import app.data.order.Order;
import app.data.order.OrderItemProperty;
import app.data.order.OrderList;
import app.data.worker.WorkerItemProperty;
import app.data.worker.WorkerList;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrdersViewController implements Initializable {
    @FXML
    private HBox centre_orders_view_hbox;
    @FXML
    private TableView orders_items_table;
//    @FXML
//    private TableView items_table_view;
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
        initializeTableViews();
        showOrders();
    }

    private void showOrders() {
        if(!OrderList.getInstance().isDownloadedData())
            OrderJdbcClass.getInstance().getOrdersFromDatabase();
        orders_items_table.getItems().clear();
        ObservableList<OrderItemProperty> orderItems = FXCollections.observableArrayList();
        OrderList.getInstance().getOrderList(unclaimedOrdersCheckBox.isSelected(), claimedOrdersCheckBox.isSelected(), myOrdersCheckBox.isSelected())
                .forEach(position -> orderItems.add(new OrderItemProperty(position.getManagerName(), position.getOrderId(), position.isIfDelivered())));
        orders_items_table.setItems(orderItems);
    }

    private void initializeTableViews() {
        TableColumn<OrderItemProperty,String> managerNameColumn = new TableColumn<>("Meneger");
        TableColumn<OrderItemProperty, String> orderIdColumn = new TableColumn<>("Identyfikator zamówienia");
        orders_items_table.getColumns().addAll(orderIdColumn, managerNameColumn);

        managerNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemProperty, String>("managerName"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemProperty, String>("orderId"));

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

                    OrderInfoViewController controller =
                            loader.<OrderInfoViewController>getController();
                    controller.initData(OrderList.getInstance().getOrder(Integer.valueOf(rowData.getOrderId())));

                    stage.show();
                    //return stage
                }
            });
            return row;
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
