package views;

<<<<<<< HEAD
import app.data.order.ItemInOrderProperty;
=======
import app.data.order.Order;
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
import app.data.order.OrderItemProperty;
import app.data.order.OrderList;
import app.jdbc.OrderJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersViewController implements Initializable {
    @FXML
    private HBox centre_orders_view_hbox;
    @FXML
    private TableView orders_items_table;
    @FXML
    private TableView items_table_view;
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
        initializeTableViews();
        showOrders();
    }

    private void showOrders() {
//        if(!OrderList.getInstance().isDownloadedData())
        ArrayList<Order> ordersInDB = OrderJdbcClass.getInstance().getOrdersFromDatabase(unclaimedOrdersCheckBox.isSelected(), claimedOrdersCheckBox.isSelected(), myOrdersCheckBox.isSelected());
        orders_items_table.getItems().clear();
        ObservableList<OrderItemProperty> orderItems = FXCollections.observableArrayList();
//        OrderList.getInstance().getOrderList(unclaimedOrdersCheckBox.isSelected(), claimedOrdersCheckBox.isSelected(), myOrdersCheckBox.isSelected())
        ordersInDB.forEach(position -> orderItems.add(new OrderItemProperty(position.getManagerId(), position.getOrderId(), position.isIfDelivered())));
        orders_items_table.setItems(orderItems);
    }

    private void initializeTableViews() {
<<<<<<< HEAD
//        System.out.println(centre_orders_view_hbox.getWidth() + " " + centre_orders_view_hbox.getHeight());
//        orders_items_table.setPrefSize(centre_orders_view_hbox.getWidth(), centre_orders_view_hbox.getHeight());
//        orders_items_table.setMinSize(centre_orders_view_hbox.getWidth(), centre_orders_view_hbox.getHeight());
//        orders_items_table.setMaxSize(centre_orders_view_hbox.getWidth(), centre_orders_view_hbox.getHeight());

        TableColumn<OrderItemProperty,String> managerNameColumn = new TableColumn<>("Meneger");
=======
        TableColumn<OrderItemProperty,String> managerNameColumn = new TableColumn<>("Meneger ID");
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
        TableColumn<OrderItemProperty, String> orderIdColumn = new TableColumn<>("Identyfikator zamówienia");
        orders_items_table.getColumns().addAll(orderIdColumn, managerNameColumn);

        managerNameColumn.setCellValueFactory(new PropertyValueFactory<OrderItemProperty, String>("managerName"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<OrderItemProperty, String>("orderId"));

        //add button columns
        TableColumn<OrderItemProperty, Void> deleteButton = new TableColumn<>("Odbierz zamówienie");

        orders_items_table.setRowFactory( tv -> {
            TableRow<OrderItemProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    OrderItemProperty rowData = row.getItem();
<<<<<<< HEAD
//                    orders_items_table.setPrefWidth(centre_orders_view_hbox.getWidth() / 2);
//                    orders_items_table.setMinWidth(centre_orders_view_hbox.getWidth() / 2);
//                    orders_items_table.setMaxWidth(centre_orders_view_hbox.getWidth() / 2);
//                    items_table_view.setMaxSize(centre_orders_view_hbox.getWidth() / 2, centre_orders_view_hbox.getHeight());
//                    items_table_view.setPrefSize(centre_orders_view_hbox.getWidth() / 2, centre_orders_view_hbox.getHeight());
//                    items_table_view.setMinSize(centre_orders_view_hbox.getWidth() / 2, centre_orders_view_hbox.getHeight());
                    items_table_view.setVisible(true);
                    showItemsInOrder(rowData.getOrderId());
=======
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
                    controller.initData(OrderJdbcClass.getInstance().getOrder(Integer.valueOf(rowData.getOrderId())));

                    stage.show();
>>>>>>> e474bd95ce9da7c4a7ddd4798f584bf70e181962
                }
            });
            return row ;
        });

        Callback<TableColumn<OrderItemProperty, Void>, TableCell<OrderItemProperty, Void>> cellFactory1 = new Callback<TableColumn<OrderItemProperty, Void>, TableCell<OrderItemProperty, Void>>() {
            @Override
            public TableCell<OrderItemProperty, Void> call(final TableColumn<OrderItemProperty, Void> param) {
                final TableCell<OrderItemProperty, Void> cell = new TableCell<OrderItemProperty, Void>() {
                    private final Button delete = new Button("Odbierz");
                    {
                        delete.setOnAction((ActionEvent event)->{
                            OrderItemProperty data = getTableView().getItems().get(getIndex());

                            OrderJdbcClass.getInstance().claimOrder(data.getOrderId());
                            OrderList.getInstance().claimOrder(Integer.valueOf(data.getOrderId()));
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(delete);
                        }
                    }
                };
                return cell;
            }
        };
        deleteButton.setCellFactory(cellFactory1);
        orders_items_table.getColumns().add(deleteButton);

        //initialize items table
        TableColumn<ItemInOrderProperty,String> productNameColumn = new TableColumn<>("Nazwa towaru");
        TableColumn<ItemInOrderProperty, String> productAmountColumn = new TableColumn<>("Ilość");

        items_table_view.getColumns().addAll(orderIdColumn, managerNameColumn);

        productNameColumn.setCellValueFactory(new PropertyValueFactory<ItemInOrderProperty, String>("name"));
        productAmountColumn.setCellValueFactory(new PropertyValueFactory<ItemInOrderProperty, String>("orderId"));
    }

    private void showItemsInOrder(String orderId) {
        if(!OrderList.getInstance().getOrder(Integer.valueOf(orderId)).isDownloadedData())
            OrderJdbcClass.getInstance().getProductsInOrdersFromDatabase(Integer.valueOf(orderId));
        items_table_view.getItems().clear();
        ObservableList<ItemInOrderProperty> orderItems = FXCollections.observableArrayList();
        OrderList.getInstance().getOrder(Integer.valueOf(orderId)).getProducts().forEach(position -> orderItems.add(new ItemInOrderProperty(position.getName(), position.getAmountOfProduct(), position.getOrderId())));
        items_table_view.setItems(orderItems);
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
