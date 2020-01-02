import app.jdbc.JdbcConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/loginToApp.fxml"));
        primaryStage.setTitle("Restaurant");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        JdbcConnector.getInstance().connectToDatabase();
        launch(args);
        JdbcConnector.getInstance().closeConnection();

    }
}
