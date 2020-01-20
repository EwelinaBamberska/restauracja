package views;

import app.data.magazine.MagazineItem;
import app.data.magazine.MagazineItemProperty;
import app.data.magazine.MagazineList;
import app.data.worker.LoggedWorker;
import app.jdbc.MagazineJdbcClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class TakeITemFromMagazineViewController implements Initializable {
    @FXML
    private Button backToMenuButton;
    @FXML
    private AutocompletionTextField itemNameTextField;
    @FXML
    private TextField itemAmountTextField;
    @FXML
    private Button takeFromMagazineButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goToMainMenu(ActionEvent actionEvent) {
        if(LoggedWorker.getInstance().isIf_manager())
            JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
        else if (LoggedWorker.getInstance().isIf_cooker())
            JavaFXUtils.changeScene(actionEvent, "mainViewCook.fxml", 800, 600, getClass());
        else
            JavaFXUtils.changeScene(actionEvent, "mainViewWaiter.fxml", 800, 600, getClass());
    }

    public void takeFromMagazine(ActionEvent actionEvent) {
        String name = itemNameTextField.getText();
        int amount = Integer.valueOf(itemAmountTextField.getText());
        int magazineAmount = MagazineList.getInstance().getAmountOfItem(name);
        if (amount <= magazineAmount){
            MagazineJdbcClass.getInstance().takeItem(name, amount);
            goToMainMenu(actionEvent);
        }
        else{
            //ZA DUZO ZABRANE
        }
    }

    public void showEntries(KeyEvent inputMethodEvent) {
        MagazineJdbcClass.getInstance().getItems();
        List<MagazineItem> itemsToShow = MagazineList.getInstance().getItemsInMagazineRegex(itemNameTextField.getText());
        itemsToShow.forEach(item -> itemNameTextField.getEntries().add(item.getName()));
    }
}