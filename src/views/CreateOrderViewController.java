package views;

import app.data.magazine.MagazineItem;
import app.data.order.ItemInOrder;
import app.data.order.ItemInOrderProperty;
import app.jdbc.MagazineJdbcClass;
import app.jdbc.OrderJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderViewController implements Initializable {
    @FXML
    private Button back_to_orders_view_button;
    @FXML
    private VBox create_order_view_vbox;
    @FXML
    private TableView items_table;
    @FXML
    private AutocompletionTextField name_text_field;
    @FXML
    private TextField amount_text_field;
    @FXML
    private Button add_new_position_button;
    @FXML
    private Button submit_button;

    private List<ItemInOrder> itemsInCreatedOrder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemsInCreatedOrder = new ArrayList<>();
        initializeTableView();
        showActualElements();
    }

    private void showActualElements() {
        items_table.getItems().clear();
        ObservableList<ItemInOrderProperty> menuItems = FXCollections.observableArrayList();
        itemsInCreatedOrder.forEach(position -> menuItems.add(new ItemInOrderProperty(position.getName(), position.getAmountOfProduct(), -1)));
        items_table.setItems(menuItems);
    }

    private void initializeTableView() {
        items_table.setEditable(true);

        TableColumn<ItemInOrderProperty,String> nameColumn = new TableColumn<>("Nazwa dania");
        TableColumn<ItemInOrderProperty, String> amountColumn = new TableColumn<>("Ilość");
        items_table.getColumns().addAll(nameColumn, amountColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<ItemInOrderProperty, String>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<ItemInOrderProperty, String>("amountOfProduct"));

        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        amountColumn.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<ItemInOrderProperty, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<ItemInOrderProperty, String> t) {
                    ItemInOrderProperty position = ((ItemInOrderProperty) t.getTableView().getItems().get(
                            t.getTablePosition().getRow()));
                    int amount = Integer.valueOf(t.getNewValue());
                    getByName(position.getName()).setAmountOfProduct(amount);
                }
            }
        );

        //add button columns
        TableColumn<ItemInOrderProperty, Void> deleteButton = new TableColumn<>("Usuń");


        Callback<TableColumn<ItemInOrderProperty, Void>, TableCell<ItemInOrderProperty, Void>> cellFactory1 = new Callback<TableColumn<ItemInOrderProperty, Void>, TableCell<ItemInOrderProperty, Void>>() {
            @Override
            public TableCell<ItemInOrderProperty, Void> call(final TableColumn<ItemInOrderProperty, Void> param) {
                final TableCell<ItemInOrderProperty, Void> cell = new TableCell<ItemInOrderProperty, Void>() {
                    private final Button delete = new Button("Usuń");
                    {
                        delete.setOnAction((ActionEvent event)->{
                            ItemInOrderProperty data = getTableView().getItems().get(getIndex());
                            ItemInOrder toRemove = getByName(data.getName());
                            itemsInCreatedOrder.remove(toRemove);
                            showActualElements();
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
        items_table.getColumns().add(deleteButton);
    }

    public void submit_button(ActionEvent actionEvent) {
        int orderId = OrderJdbcClass.getInstance().createOrder();

        for (ItemInOrder item:
             itemsInCreatedOrder) {
            item.setOrderId(orderId);
            OrderJdbcClass.getInstance().addItemInOrder(item);
        }
        go_to_orders_view(actionEvent);
    }

    public void go_to_orders_view(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "ordersView.fxml", 800, 600, getClass());
    }

    public void add_new_position(ActionEvent actionEvent) {
        String name = name_text_field.getCharacters().toString();
        String amount = amount_text_field.getCharacters().toString();
        name_text_field.clear();
        amount_text_field.clear();
        int amountInt = Integer.valueOf(amount);
        ItemInOrder item = new ItemInOrder(name, amountInt, -1);
        itemsInCreatedOrder.add(item);
        showActualElements();
    }

    public ItemInOrder getByName(String name){
        for (ItemInOrder i:
             itemsInCreatedOrder) {
            if (i.getName().equals(name))
                return i;
        }
        return null;
    }

    public void showItemsInMagazine(KeyEvent keyEvent) {
        ArrayList<MagazineItem> items = MagazineJdbcClass.getInstance().getItems();
        ArrayList<MagazineItem> regexArray = new ArrayList<>();
        items.forEach(position -> {if(position.getName().contains(name_text_field.getText())) regexArray.add(position);});
        regexArray.forEach(item -> name_text_field.getEntries().add(item.getName()));
    }
}
