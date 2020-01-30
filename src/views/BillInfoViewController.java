package views;

import app.data.DataValidation;
import app.data.bill.Bill;
import app.data.bill.DishInBill;
import app.data.bill.DishItemProperty;
import app.data.menu.MenuPosition;
import app.data.worker.LoggedWorker;
import app.jdbc.BillJdbcClass;
import app.jdbc.MenuJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BillInfoViewController implements Initializable {
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

    private Bill actualBill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(Bill bill) {
        this.actualBill = bill;
        System.out.println(actualBill.getBillId());
        if(!actualBill.isIfPaid()) {
            deliverLabel.setText("Not paid bill.");
            addDeliverButton();
        }
        else {
            deliverLabel.setText("Paid bill.");
            bottomVBox.setVisible(false);
        }
        initializeTable();
        showItemsInTable();
    }

    private void addDeliverButton() {
        Button deliverButton = JavaFXUtils.createButton("Pay for bill");
        topHBox.getChildren().add(deliverButton);
        deliverButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deliverLabel.setText("Paid bill.");
                topHBox.getChildren().remove(1);
                bottomVBox.setVisible(false);
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "rateView.fxml"
                        )
                );

                Stage stage = (Stage) bottomVBox.getScene().getWindow();
                try {
                    stage.setScene(new Scene((Pane) loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RateViewController controller =
                        loader.<RateViewController>getController();
                controller.initData(actualBill);

                stage.show();
            }
        });
    }

    private void showItemsInTable() {
        ArrayList<DishInBill> itemsInOrder = BillJdbcClass.getInstance().getItemsOnBill(Integer.valueOf(actualBill.getBillId()));
        itemsTableView.getItems().clear();
        ObservableList<DishItemProperty> orderItems = FXCollections.observableArrayList();
        itemsInOrder.forEach(position -> orderItems.add(new DishItemProperty(position.getBillId(), position.getAmount(), position.getItemName())));
        itemsTableView.setItems(orderItems);
    }

    private void initializeTable() {
        TableColumn<DishItemProperty,String> productNameColumn = new TableColumn<>("Dish name");
        TableColumn<DishItemProperty, String> productAmountColumn = new TableColumn<>("Amount");

        itemsTableView.getColumns().addAll(productNameColumn, productAmountColumn);

        productNameColumn.setCellValueFactory(new PropertyValueFactory<DishItemProperty, String>("name"));
        productAmountColumn.setCellValueFactory(new PropertyValueFactory<DishItemProperty, String>("amount"));


        if(!actualBill.isIfPaid()) {
            TableColumn<DishItemProperty, Void> deleteButton = new TableColumn<>("Delete dish from bill.");

            Callback<TableColumn<DishItemProperty, Void>, TableCell<DishItemProperty, Void>> cellFactory1 = new Callback<TableColumn<DishItemProperty, Void>, TableCell<DishItemProperty, Void>>() {
                @Override
                public TableCell<DishItemProperty, Void> call(final TableColumn<DishItemProperty, Void> param) {
                    final TableCell<DishItemProperty, Void> cell = new TableCell<DishItemProperty, Void>() {
                        private final Button delete = new Button("Delete");
                        {
                            delete.setOnAction((ActionEvent event) -> {
                                DishItemProperty data = getTableView().getItems().get(getIndex());

                                BillJdbcClass.getInstance().deleteDishFromBill(Integer.valueOf(data.getBillId()), data.getName());
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
        if(LoggedWorker.getInstance().isIf_manager())
            JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
        else
            JavaFXUtils.changeScene(actionEvent, "mainViewWaiter.fxml", 800, 600, getClass());

    }

    public void showHints(KeyEvent keyEvent) {
        ArrayList<MenuPosition> itemsInMagazine = MenuJdbcClass.getInstance().getMenuItemsFromDatabase();
        ArrayList<MenuPosition> regexItems = new ArrayList<>();
        itemsInMagazine.forEach(pos -> {
            if(pos.getName().toUpperCase().contains(nameOfAddedItemTextField.getText().toUpperCase()))
                regexItems.add(pos);});
        regexItems.forEach(item -> nameOfAddedItemTextField.getEntries().add(item.getName()));
    }

    public void addItemToOrder(ActionEvent actionEvent) {
        int checking = 0;
        String name = nameOfAddedItemTextField.getText();
        String amount_validation = amountOfAddedItemTextField.getText();
        if (nameOfAddedItemTextField.getText() == null || nameOfAddedItemTextField.getText().trim().isEmpty()) {
            ErrorBox.showError("Error", "Name input can't be empty");
            checking++;
        }
        if (amountOfAddedItemTextField.getText() == null || amountOfAddedItemTextField.getText().trim().isEmpty()) {
            ErrorBox.showError("Error", "Amount input can't be empty");
            checking++;
        }

        if ( DataValidation.checkSpecialChars(name)=="" || DataValidation.checkSpecialChars(amount_validation)=="") {
            ErrorBox.showError("Error", "Neither input can contain special characters");
            checking++;
        }

        if (DataValidation.checkOnlyLetters(name)=="") {
            ErrorBox.showError("Error", "Name input can't contain numbers");
            checking++;
        }

        if (DataValidation.checkOnlyNumbers(amount_validation)=="") {
            ErrorBox.showError("Error", "Amount input must be an integer");
            checking++;
        }
        if (checking==0) {
            name = name.toLowerCase().trim();
            int amount = Integer.valueOf(amountOfAddedItemTextField.getText());
            DishInBill item = new DishInBill(actualBill.getBillId(), amount, name);
            BillJdbcClass.getInstance().addDishToBill(item);
            BillJdbcClass.getInstance().setPrice(actualBill.getBillId());
            nameOfAddedItemTextField.clear();
            amountOfAddedItemTextField.clear();
            showItemsInTable();
        }
    }
}
