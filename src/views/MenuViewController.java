package views;

import app.data.menu.MenuItemProperty;
import app.data.menu.MenuList;
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
import javafx.scene.layout.VBox;
import javafx.util.Callback;


import java.net.URL;
import java.util.ResourceBundle;

public class MenuViewController implements Initializable {
    @FXML
    private VBox centre_menu_view_vbox;
    @FXML
    private TableView menu_items_table;
    @FXML
    private TextField name_text_field;
    @FXML
    private TextField price_text_field;
    @FXML
    private Button add_new_position_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
        showItemsInMenu();
    }

    private void showItemsInMenu() {
        System.out.println("Baza");
        if(!MenuList.getInstance().isDownloadedData())
            MenuJdbcClass.getInstance().getMenuItemsFromDatabase();
        System.out.println(MenuList.getInstance().getMenuPositionList().size());
        menu_items_table.getItems().clear();
        ObservableList<MenuItemProperty> menuItems = FXCollections.observableArrayList();
            MenuList.getInstance().getMenuPositionList().forEach(position -> menuItems.add(new MenuItemProperty(position.getName(), position.getPrice())));
        menu_items_table.setItems(menuItems);
    }

    private void initializeTableColumns() {
        menu_items_table.setEditable(true);
//        menu_items_table.setPrefHeight(centre_menu_view_vbox.getHeight());
//        menu_items_table.setMinHeight(centre_menu_view_vbox.getHeight());
//        menu_items_table.setMaxHeight(centre_menu_view_vbox.getHeight());

        TableColumn<MenuItemProperty,String> positionNameColumn = new TableColumn<>("Nazwa dania");
        TableColumn<MenuItemProperty, String> positionPriceColumn = new TableColumn<>("Cena");
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
                        MenuList.getInstance().modifyPriceOfPosisiton(position.getName(), newPrice);
                        MenuJdbcClass.getInstance().modifyPrice(position.getName(), newPrice);
                    }
                }
        );

        //add button columns
        TableColumn<MenuItemProperty, Void> deleteButton = new TableColumn<>("Usuń");


        Callback<TableColumn<MenuItemProperty, Void>, TableCell<MenuItemProperty, Void>> cellFactory1 = new Callback<TableColumn<MenuItemProperty, Void>, TableCell<MenuItemProperty, Void>>() {
            @Override
            public TableCell<MenuItemProperty, Void> call(final TableColumn<MenuItemProperty, Void> param) {
                final TableCell<MenuItemProperty, Void> cell = new TableCell<MenuItemProperty, Void>() {
                    private final Button delete = new Button("Usuń");
                    {
                        delete.setOnAction((ActionEvent event)->{
                            MenuItemProperty data = getTableView().getItems().get(getIndex());
                            MenuList.getInstance().removePosition(data.getName());
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
        String name = name_text_field.getCharacters().toString();
        String price = price_text_field.getCharacters().toString().replace(",", ".");
        name_text_field.clear();
        price_text_field.clear();
        Double priceValue = Double.parseDouble(price);
        MenuPosition newPosition = new MenuPosition(name, priceValue);
        MenuList.getInstance().addMenuPosition(newPosition);
        MenuJdbcClass.getInstance().addMenuPosition(newPosition);
        showItemsInMenu();
    }

    public void go_to_menu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }
}
