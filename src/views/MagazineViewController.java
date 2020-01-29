package views;

import app.data.magazine.MagazineItem;
import app.data.magazine.MagazineItemProperty;
import app.jdbc.MagazineJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MagazineViewController implements Initializable {
    @FXML
    private HBox topHBox;
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private TableView ingredientsTableView;
    @FXML
    private TextField findIngredientTextField;
    @FXML
    private Button findIngredientButton;
    @FXML
    private TextField ingredientNameTextField;
    @FXML
    private TextField ingredientAmountTextField;
    @FXML
    private Button addIngredientButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
        showIngredients();
    }

    private void showIngredients() {
        ArrayList<MagazineItem> itemsInDB = MagazineJdbcClass.getInstance().getItems();
        ingredientsTableView.getItems().clear();
        ObservableList<MagazineItemProperty> items = FXCollections.observableArrayList();
        itemsInDB.forEach(position -> items.add(new MagazineItemProperty(position.getAmount(), position.getName())));
        ingredientsTableView.setItems(items);
    }

    private void initializeTableView() {
        TableColumn<MagazineItemProperty,String> itemNameColumn= new TableColumn<>("Nazwa");
        TableColumn<MagazineItemProperty, String> amountColumn = new TableColumn<>("Ilość");
        ingredientsTableView.getColumns().addAll(itemNameColumn, amountColumn);

        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<MagazineItemProperty, Void> deleteButton = new TableColumn<>("Delete");


        Callback<TableColumn<MagazineItemProperty, Void>, TableCell<MagazineItemProperty, Void>> cellFactory1 = new Callback<TableColumn<MagazineItemProperty, Void>, TableCell<MagazineItemProperty, Void>>() {
            @Override
            public TableCell<MagazineItemProperty, Void> call(final TableColumn<MagazineItemProperty, Void> param) {
                final TableCell<MagazineItemProperty, Void> cell = new TableCell<MagazineItemProperty, Void>() {
                    private final Button delete = new Button("Delete");
                    {
                        delete.setOnAction((ActionEvent event)->{
                            MagazineItemProperty data = getTableView().getItems().get(getIndex());
                            MagazineJdbcClass.getInstance().deleteItemFromMagazine(data.getName());
                            showIngredients();
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
        ingredientsTableView.getColumns().add(deleteButton);
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }

    public void findIngredient(ActionEvent actionEvent) {
        int checking = 0;
        String regexToFind = findIngredientTextField.getText();
        if (findIngredientTextField.getText() == null || findIngredientTextField.getText().trim().isEmpty()) {
            views.ErrorBox.showError("Error", "Input can't be empty");
            checking++;
        }
        findIngredientTextField.clear();
        if (app.data.DataValidation.checkSpecialChars(regexToFind) == "") {
            views.ErrorBox.showError("Error", "There isn't any food with special characters");
            checking++;
        }
        if (app.data.DataValidation.checkSize(32, regexToFind) == "") {
            views.ErrorBox.showError("Error", "Keyword can't be longer than 32 characters");
            checking++;
        }
        if(app.data.DataValidation.checkOnlyNumbers(regexToFind)==""){
            views.ErrorBox.showError("Error", "There can't be numbers in ingredients names");
            checking++;
        }
        if (checking == 0) {
            ArrayList<MagazineItem> itemsInDB = MagazineJdbcClass.getInstance().getItems();
            ingredientsTableView.getItems().clear();
            ObservableList<MagazineItemProperty> items = FXCollections.observableArrayList();
            ArrayList<MagazineItem> regexItems = new ArrayList<>();
            itemsInDB.forEach(position -> {
                if (position.getName().contains(regexToFind)) regexItems.add(position);
            });
            regexItems.forEach(position -> items.add(new MagazineItemProperty(position.getAmount(), position.getName())));
            ingredientsTableView.setItems(items);
            Button showAllButton = JavaFXUtils.createButton("Pokaż wszystko.");
            showAllButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showIngredients();
                    topHBox.getChildren().remove(topHBox.getChildren().size() - 1);
                }
            });
            if (topHBox.getChildren().size() == 3)
                topHBox.getChildren().add(showAllButton);
            findIngredientTextField.clear();
        }
    }

    public void addIngredient(ActionEvent actionEvent) {
        int checking = 0;
        String name = ingredientNameTextField.getText();
        String amount_validation = ingredientAmountTextField.getText();
        if (ingredientNameTextField.getText() == null || ingredientNameTextField.getText().trim().isEmpty()) {
            views.ErrorBox.showError("Error", "Name input can't be empty");
            checking++;
        }
        if (ingredientAmountTextField.getText() == null || ingredientAmountTextField.getText().trim().isEmpty()) {
            views.ErrorBox.showError("Error", "Amount input can't be empty");
            checking++;
        }

        if ( app.data.DataValidation.checkSpecialChars(name)=="" || app.data.DataValidation.checkSpecialChars(amount_validation)=="") {
            views.ErrorBox.showError("Error", "Neither input can contain special characters");
            checking++;
        }

        if (app.data.DataValidation.checkOnlyLetters(name)=="") {
            views.ErrorBox.showError("Error", "Name input can't contain numbers");
            checking++;
        }

        if (app.data.DataValidation.checkOnlyNumbers(amount_validation)=="") {
            views.ErrorBox.showError("Error", "Amount input must be an integer");
            checking++;
        }

        if(checking ==0) {
            name = name.toLowerCase();
            int amount = Integer.valueOf(ingredientAmountTextField.getText());
            MagazineItem newItem = new MagazineItem(amount, name);
            MagazineJdbcClass.getInstance().addItem(newItem);
            ingredientNameTextField.clear();
            ingredientAmountTextField.clear();
            showIngredients();
        }
    }
}

