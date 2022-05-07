package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root, 200, 200);

        stage.setScene(scene);
        stage.setTitle("Hello FXML");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
