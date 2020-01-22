package views;

import app.data.menu.MenuItemProperty;
import app.data.menu.MenuList;
import app.data.order.ItemInOrderProperty;
import app.data.order.OrderItemProperty;
import app.data.order.OrderList;
import app.data.worker.WorkerItemProperty;
import app.data.worker.WorkerList;
import app.jdbc.MenuJdbcClass;
import app.jdbc.OrderJdbcClass;
import app.jdbc.WorkerJdbcClass;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class AllWorkersViewController implements Initializable {
    @FXML
    private HBox topHBox;
    @FXML
    private Button backToMenuButton;
    @FXML
    private TableView workersTableView;
    @FXML
    private Button addWorkerButton;
    @FXML
    private Button findWorkerButton;
    @FXML
    private TextField findWorkerTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableView();
        showWorkers();
    }

    private void showWorkers() {
        if(!WorkerList.getInstance().isIfDataDownloaded())
            WorkerJdbcClass.getInstance().getWorkers();
        workersTableView.getItems().clear();
        ObservableList<WorkerItemProperty> workersList = FXCollections.observableArrayList();
        WorkerList.getInstance().getWorkers()
                .forEach(position -> workersList.add(new WorkerItemProperty(position.getId_prac(),
                        position.getName(), position.getSurname(), position.isIf_manager(), position.isIf_cooker(), position.isIf_waiter())));
        workersTableView.setItems(workersList);
    }

    private void initializeTableView() {
        TableColumn<WorkerItemProperty,String> firstNameColumn = new TableColumn<>("Imie");
        TableColumn<WorkerItemProperty, String> surnameColumn = new TableColumn<>("Nazwisko");
        TableColumn<WorkerItemProperty, String> positionColumn = new TableColumn<>("Stanowisko");
        TableColumn<WorkerItemProperty, String> idColumn = new TableColumn<>("Id pracownika");
        workersTableView.getColumns().addAll(idColumn, firstNameColumn, surnameColumn, positionColumn);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<WorkerItemProperty, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<WorkerItemProperty, String>("surname"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<WorkerItemProperty, String>("position"));
        idColumn.setCellValueFactory(new PropertyValueFactory<WorkerItemProperty, String>("id_prac"));

        //add button columns

        workersTableView.setRowFactory( tv -> {
            TableRow<WorkerItemProperty> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    WorkerItemProperty rowData = row.getItem();
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource(
                                    "customerDialog.fxml"
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

                    WorkerInfoController controller =
                            loader.<WorkerInfoController>getController();
                    controller.initData(WorkerList.getInstance().getWorkerById(Integer.valueOf(rowData.getId_prac())));

                    stage.show();
                    //return stage
                }
            });
            return row;
        });
    }

    public void goToMenu(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "mainViewManager.fxml", 800, 600, getClass());
    }

    public void goToAddWorkerView(ActionEvent actionEvent) {
        JavaFXUtils.changeScene(actionEvent, "workerInfoView.fxml", 800, 600, getClass());
    }

    public void findWorker(ActionEvent actionEvent) {
        String regexToFind = findWorkerTextField.getCharacters().toString();
        if(!WorkerList.getInstance().isIfDataDownloaded())
            WorkerJdbcClass.getInstance().getWorkers();
        workersTableView.getItems().clear();
        ObservableList<WorkerItemProperty> workers = FXCollections.observableArrayList();
        WorkerList.getInstance().getWorkerRegex(regexToFind).
                forEach(position -> workers.add(new WorkerItemProperty(position.getId_prac(), position.getName(),
                        position.getSurname(), position.isIf_manager(), position.isIf_cooker(), position.isIf_waiter())));
        workersTableView.setItems(workers);
        Button showAllButton = JavaFXUtils.createButton("Pokaż wszystkich.");
        showAllButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showWorkers();
                topHBox.getChildren().remove(topHBox.getChildren().size() - 1);
            }
        });
        topHBox.getChildren().add(showAllButton);
    }

}
