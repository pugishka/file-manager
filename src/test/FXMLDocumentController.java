package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FXMLDocumentController implements Initializable {
    
    @FXML VBox test;
    public void testAction(ActionEvent event) throws IOException {
	    VBox newLoadedPane = 
	    		FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
	    test.getChildren().clear();
	    test.getChildren().add(newLoadedPane); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}