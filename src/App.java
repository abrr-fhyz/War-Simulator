package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        int sceneWidth = 1010, sceneHeight = 710;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../assets/simulationAlpha.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation Window");
        primaryStage.setMinWidth(sceneWidth);
        primaryStage.setMaxWidth(sceneWidth);
        primaryStage.setMinHeight(sceneHeight);
        primaryStage.setMaxHeight(sceneHeight);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
