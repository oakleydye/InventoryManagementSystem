import UserControls.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Oakley Dye
 *
 * Main class, houses startup methods.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserControls/MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Inventory Management System");
        MainWindowController controller = loader.getController();
        controller.init();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
