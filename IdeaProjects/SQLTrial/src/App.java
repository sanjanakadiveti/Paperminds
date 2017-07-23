import java.sql.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
public class App extends Application {
    private StartScreen layout;
    private Scene scene;
    private ListScreen listScreen;
    private ListView listView;
    @Override
    public void start(Stage primaryStage) {
        layout = new StartScreen();
        layout.getEntryButton().setOnMouseClicked(e -> {
            primaryStage.setScene(showList());
        });
        scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public Scene showList() {
        try {
            listScreen = new ListScreen();
            Scene listScene = new Scene(listScreen);
            listScene.getStylesheets().add("File:./src/style.css");
            return listScene;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        launch(args);
    }
}