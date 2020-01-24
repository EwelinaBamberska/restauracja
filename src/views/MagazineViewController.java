package views;

import app.data.magazine.MagazineItem;
import app.data.magazine.MagazineItemProperty;
import app.data.magazine.MagazineList;
import app.jdbc.MagazineJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

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
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }

    public void findIngredient(ActionEvent actionEvent) {
        String regexToFind = findIngredientTextField.getText();
        ArrayList<MagazineItem> itemsInDB = MagazineJdbcClass.getInstance().getItems();
        ingredientsTableView.getItems().clear();
        ObservableList<MagazineItemProperty> items= FXCollections.observableArrayList();
        ArrayList<MagazineItem> regexItems = new ArrayList<>();
        itemsInDB.forEach(position -> {if(position.getName().contains(regexToFind)) regexItems.add(position);});
//        MagazineList.getInstance().getItemsInMagazineRegex(regexToFind)
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
        topHBox.getChildren().add(showAllButton);
        findIngredientTextField.clear();
    }

    public void addIngredient(ActionEvent actionEvent) {
        String name = ingredientNameTextField.getText();
        int amount = Integer.valueOf(ingredientAmountTextField.getText());
        MagazineItem newItem = new MagazineItem(amount, name);
        MagazineJdbcClass.getInstance().addItem(newItem);
        ingredientNameTextField.clear();
        ingredientAmountTextField.clear();
        showIngredients();
    }
}
