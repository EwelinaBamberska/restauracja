package views;

import app.data.menu.MenuItemProperty;
import app.data.menu.MenuPosition;
import app.jdbc.MenuJdbcClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuViewController implements Initializable {
    @FXML
    private VBox centre_menu_view_vbox;
    @FXML
    private TableView menu_items_table;
    @FXML
    private TextField name_text_field;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button addNewPositionButton;
    @FXML
    private TextField findItemInMenuTextView;
    @FXML
    private Button findItemInMenuButton;
    @FXML
    private HBox topHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
        showItemsInMenu();
    }

    private void showItemsInMenu() {
        ArrayList<MenuPosition> menuPositions = MenuJdbcClass.getInstance().getMenuItemsFromDatabase();
        menu_items_table.getItems().clear();
        ObservableList<MenuItemProperty> menuItems = FXCollections.observableArrayList();
        menuPositions.forEach(position -> menuItems.add(new MenuItemProperty(position.getName(), position.getPrice())));
        menu_items_table.setItems(menuItems);
    }

    private void initializeTableColumns() {
        menu_items_table.setEditable(true);

        TableColumn<MenuItemProperty,String> positionNameColumn = new TableColumn<>("Dish name");
        TableColumn<MenuItemProperty, String> positionPriceColumn = new TableColumn<>("Price");
        menu_items_table.getColumns().addAll(positionNameColumn, positionPriceColumn);

        positionNameColumn.setCellValueFactory(new PropertyValueFactory<MenuItemProperty, String>("name"));
        positionPriceColumn.setCellValueFactory(new PropertyValueFactory<MenuItemProperty, String>("price"));

        positionPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        positionPriceColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MenuItemProperty, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MenuItemProperty, String> t) {
                        MenuItemProperty position = ((MenuItemProperty) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        Double newPrice = Double.parseDouble(t.getNewValue().toString().replace(",", "."));
                        MenuJdbcClass.getInstance().modifyPrice(position.getName(), newPrice);
                    }
                }
        );

        //add button columns
        TableColumn<MenuItemProperty, Void> deleteButton = new TableColumn<>("Delete");


        Callback<TableColumn<MenuItemProperty, Void>, TableCell<MenuItemProperty, Void>> cellFactory1 = new Callback<TableColumn<MenuItemProperty, Void>, TableCell<MenuItemProperty, Void>>() {
            @Override
            public TableCell<MenuItemProperty, Void> call(final TableColumn<MenuItemProperty, Void> param) {
                final TableCell<MenuItemProperty, Void> cell = new TableCell<MenuItemProperty, Void>() {
                    private final Button delete = new Button("Delete");
                    {
                        delete.setOnAction((ActionEvent event)->{
                            MenuItemProperty data = getTableView().getItems().get(getIndex());
                            MenuJdbcClass.getInstance().deleteMenuPosition(data.getName());
                            showItemsInMenu();
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
        menu_items_table.getColumns().add(deleteButton);
    }


    public void add_new_position(ActionEvent actionEvent) {
        int checking = 0;
        String name = name_text_field.getCharacters().toString();
        String price = priceTextField.getCharacters().toString().replace(",", ".");
        if (app.data.DataValidation.checkEmpty(name) == "" || app.data.DataValidation.checkEmpty(price) == "") {
            views.ErrorBox.showError("Error", "Input can't be empty");
            checking++;
        }
        if (app.data.DataValidation.checkSpecialChars(name) == "") {
            views.ErrorBox.showError("Error", "Special characters are not allowed in names");
            checking++;
        }
        if(app.data.DataValidation.checkSize(32,name)==""){
            views.ErrorBox.showError("Error", "Name must be at most 32 characters long");
            checking++;
        }
        if(checking == 0) {
            name_text_field.clear();
            priceTextField.clear();
            try {
                Double priceValue = Double.parseDouble(price);
                MenuPosition newPosition = new MenuPosition(name, priceValue);
                MenuJdbcClass.getInstance().addMenuPosition(newPosition);
                showItemsInMenu();
            }catch(NumberFormatException e){
                ErrorBox.showError("Error","Price contains only numbers and  a comma");

            }
        }
    }

    public void go_to_menu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }

    public void findItemInMenu(ActionEvent actionEvent) {
        String regexToFind = findItemInMenuTextView.getCharacters().toString();
        ArrayList<MenuPosition> itemsInDB = MenuJdbcClass.getInstance().getMenuItemsFromDatabase();
        menu_items_table.getItems().clear();
        ObservableList<MenuItemProperty> menuItems = FXCollections.observableArrayList();

        ArrayList<MenuPosition> regexPos= new ArrayList<>();
        itemsInDB.forEach(pos ->{if(pos.getName().toUpperCase().contains(regexToFind.toUpperCase())) regexPos.add(pos);});

        regexPos.forEach(position -> menuItems.add(new MenuItemProperty(position.getName(), position.getPrice())));
        menu_items_table.setItems(menuItems);
        Button showAllButton = JavaFXUtils.createButton("Show all.");
        showAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showItemsInMenu();
                topHBox.getChildren().remove(topHBox.getChildren().size() - 1);
            }
        });
        if(topHBox.getChildren().size()== 3)
            topHBox.getChildren().add(showAllButton);
    }
}
