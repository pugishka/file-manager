package gui;
import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WindowF extends Application {
	
	private static WindowF instance;
	public FlowPane flowIcons;
	public GUIEventHandlers eHandler;
	
	@Override
    public void start(Stage stage) {
		instance = this;
		stage.setTitle("Explorer");
		BorderPane main = new BorderPane();
		flowIcons = new FlowPane();
		eHandler = GUIEventHandlers.getInstance();
		
		main.setTop(generateMenu());
		main.setCenter(flowIcons);
		Scene scene = new Scene(main, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }
	
	public VBox generateMenu() {
		Menu mfiles = new Menu("Files");
		MenuItem mopen = new MenuItem("Open");
		eHandler.openFilesEvent(mopen);
		mfiles.getItems().add(mopen);
		MenuBar mb = new MenuBar();
		mb.getMenus().add(mfiles);
		VBox v = new VBox(mb);
		return v;
	}
	
	public FlowPane getFlowIcons() {
		return flowIcons;
	}
	
	public static WindowF getInstance() {
		return instance;
	}

	public static void main(String[] args) {
//		Colors c = new Colors();
		launch(args);
	}
	
	
}
