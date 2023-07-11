package by.akoltovich.task;

//import by.akoltovich.task.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = loadFXML();
        scene.getStylesheets().add(getClass().getResource("/customization.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Scene loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/NewTreeTableView.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
