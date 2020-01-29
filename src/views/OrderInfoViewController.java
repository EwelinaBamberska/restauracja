package views;

import app.data.magazine.MagazineItem;
import app.data.magazine.MagazineList;
import app.data.order.*;
import app.jdbc.MagazineJdbcClass;
import app.jdbc.OrderJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class OrderInfoViewController implements Initializable {
    @FXML
    private Button backToOrdersButton;
    @FXML
    private TableView itemsTableView;
    @FXML
    private AutocompletionTextField nameOfAddedItemTextField;
    @FXML
    private TextField amountOfAddedItemTextField;
    @FXML
    private Button addItemToOrderButton;
    @FXML
    private HBox topHBox;
    @FXML
    private Label deliverLabel;
    @FXML
    private VBox bottomVBox;

    private Order actualOrder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Order order) {
        this.actualOrder = order;
        if(!actualOrder.isIfDelivered()) {
            deliverLabel.setText("Order is not received.");
        }
        else {
            deliverLabel.setText("Order is received.");
            bottomVBox.setVisible(false);
        }
        initializeTable();
        showItemsInTable();
        if(!actualOrder.isIfDelivered())
            addDeliverButton();
    }

    private void addDeliverButton() {
        Button deliverButton = JavaFXUtils.createButton("Receive");
        topHBox.getChildren().add(deliverButton);
        deliverButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrderJdbcClass.getInstance().claimOrder(actualOrder.getOrderId());
                actualOrder.setIfDelivered(true);
                deliverLabel.setText("Order is received.");
                topHBox.getChildren().remove(1);
                bottomVBox.setVisible(false);
            }
        });
    }

    private void showItemsInTable() {
        ArrayList<ItemInOrder> itemsInOrder = OrderJdbcClass.getInstance().getProductsInOrdersFromDatabase(Integer.valueOf(actualOrder.getOrderId()));
        itemsTableView.getItems().clear();
        ObservableList<ItemInOrderProperty> orderItems = FXCollections.observableArrayList();
        itemsInOrder.forEach(position -> orderItems.add(new ItemInOrderProperty(position.getName(), position.getAmountOfProduct(), position.getOrderId())));
        itemsTableView.setItems(orderItems);
    }

    private void initializeTable() {
        TableColumn<ItemInOrderProperty,String> productNameColumn = new TableColumn<>("Product name");
        TableColumn<ItemInOrderProperty, String> productAmountColumn = new TableColumn<>("Amount");

        itemsTableView.getColumns().addAll(productNameColumn, productAmountColumn);

        productNameColumn.setCellValueFactory(new PropertyValueFactory<ItemInOrderProperty, String>("name"));
        productAmountColumn.setCellValueFactory(new PropertyValueFactory<ItemInOrderProperty, String>("amountOfProduct"));


        if(!actualOrder.isIfDelivered()) {
            TableColumn<ItemInOrderProperty, Void> deleteButton = new TableColumn<>("Delete product from order.");

            Callback<TableColumn<ItemInOrderProperty, Void>, TableCell<ItemInOrderProperty, Void>> cellFactory1 = new Callback<TableColumn<ItemInOrderProperty, Void>, TableCell<ItemInOrderProperty, Void>>() {
                @Override
                public TableCell<ItemInOrderProperty, Void> call(final TableColumn<ItemInOrderProperty, Void> param) {
                    final TableCell<ItemInOrderProperty, Void> cell = new TableCell<ItemInOrderProperty, Void>() {
                        private final Button delete = new Button("Delete");

                        {
                            delete.setOnAction((ActionEvent event) -> {
                                ItemInOrderProperty data = getTableView().getItems().get(getIndex());

                                OrderJdbcClass.getInstance().deleteItemFromOrder(Integer.valueOf(data.getOrderId()), data.getName());
//                                actualOrder.deleteItem(data.getName());
                                showItemsInTable();
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
            itemsTableView.getColumns().add(deleteButton);
        }

    }

    public void backToOrdersView(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "ordersView.fxml", 800, 600, getClass());
    }

    public void showHints(KeyEvent keyEvent) {
        ArrayList<MagazineItem> itemsInMagazine = MagazineJdbcClass.getInstance().getItems();
        ArrayList<MagazineItem> regexItems = new ArrayList<>();
        itemsInMagazine.forEach(pos -> {
            if(pos.getName().toUpperCase().contains(nameOfAddedItemTextField.getText().toUpperCase()))
                regexItems.add(pos);});
        regexItems.forEach(item -> nameOfAddedItemTextField.getEntries().add(item.getName()));
    }

    public void addItemToOrder(ActionEvent actionEvent) {
        String name = nameOfAddedItemTextField.getText();
        int amount = Integer.valueOf(amountOfAddedItemTextField.getText());
        ItemInOrder item = new ItemInOrder(name, amount, actualOrder.getOrderId());

        OrderJdbcClass.getInstance().addItemInOrder(item);
        nameOfAddedItemTextField.clear();
        amountOfAddedItemTextField.clear();
        showItemsInTable();
    }
}
