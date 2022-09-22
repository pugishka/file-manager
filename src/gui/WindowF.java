package gui;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



//cm userData = vbox
//vbox userData = FilesFolders
//fp userData = ItemFolder currently opened

public class WindowF extends Application {
	
	private static WindowF instance;
	public Scene scene;
	public FlowPane flowIcons;
	public AnchorPane anchorPane;
	public ContextMenu fileCMenu;
	public ContextMenu folderCMenu;
	public ContextMenu windowCMenu;
	public GUIEventHandlers eHandler;
	// TODO
	// separate class maybe for current selected and opened
	public Object selected;
	public ItemFolder currentFolder;
	
	@Override
    public void start(Stage stage) throws IOException {
		instance = this;
		this.eHandler = GUIEventHandlers.getInstance();
		this.fileCMenu = generateCMenu("file");
		this.folderCMenu = generateCMenu("folder");
		this.windowCMenu = generateCMenu("window");
		
		stage.setTitle("Explorer");
		stage.setX(0);
		stage.setY(0);
		BorderPane main = new BorderPane();
		
		main.setTop(generateMenu());
		
		anchorPane = new AnchorPane();
		flowIcons = new FlowPane();
		AnchorPane.setTopAnchor(flowIcons, 0.0);
		AnchorPane.setBottomAnchor(flowIcons, 0.0);
		AnchorPane.setLeftAnchor(flowIcons, 0.0);
		AnchorPane.setRightAnchor(flowIcons, 0.0);
		eHandler.clickWindowEvent(flowIcons);
		anchorPane.getChildren().add(flowIcons);
		
		main.setCenter(anchorPane); 
		
		Button returnBtn = new Button("Return"); 
		main.setRight(returnBtn);
		eHandler.returnPreviousEvent(returnBtn);
		
		this.scene = new Scene(main, 900, 600);
		scene.getStylesheets().add("css.css");
		
		scene.setOnKeyPressed(eHandler.undoKeyPressed());
		scene.setOnKeyReleased(eHandler.undoKeyReleased());

		stage.setScene(scene);
        debug();
        stage.show();
    }
	
	public VBox generateMenu() {
		Menu mfiles = new Menu("Files");
		MenuItem mopen = new MenuItem("Open");
		eHandler.openFileMenuEvent(mopen);
		mfiles.getItems().add(mopen);
		MenuBar mb = new MenuBar();
		mb.getMenus().add(mfiles);
		VBox v = new VBox(mb);
		return v;
	}
	
	public ContextMenu generateCMenu(String type) {
        ContextMenu cm = new ContextMenu();
        
        MenuItem rename = new MenuItem("Rename");
        eHandler.renameCMenuEvent(rename);
        MenuItem delete = new MenuItem("Delete");
        eHandler.deleteCMenuEvent(delete);
        MenuItem copy = new MenuItem("Copy");
        eHandler.copyCMenuEvent(copy);
        MenuItem paste = new MenuItem("Paste");
        eHandler.pasteCMenuEvent(paste);

        if(type.equals("file")) {
            cm.getItems().add(rename);
            cm.getItems().add(delete);
            cm.getItems().add(copy);
        }
        
        if(type.equals("folder")) {
            cm.getItems().add(rename);
            cm.getItems().add(delete);
            cm.getItems().add(copy);
            cm.getItems().add(paste);
        }

        if(type.equals("window")) {
            cm.getItems().add(paste);
        }
        
		return cm;
	}
	
	public void debug() {
    	String root = "C:\\Users\\charo\\Documents\\Documents\\UDEM";
    	File dir = new File(root);
    	File recycleBin = new File(root + "/recycleBin");
    	if (!recycleBin.exists()){
    		recycleBin.mkdirs();
    	}
    	ItemFolder folder = new ItemFolder(dir, null);
    	folder.showImmediateChildren();
    	GUIEventHandlers.getInstance().setRootObject(folder);
    	GUIEventHandlers.getInstance().setRecycleBin(
    			(ItemFolder) folder.getFilesFolders("recycleBin"));
    	this.currentFolder = folder;
	}
	
	public FlowPane getFlowIcons() {
		return flowIcons;
	}
	
	public static WindowF getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ContextMenu getCMenu(String type) {
        if(type.equals("file")) {
    		return fileCMenu;
        } else if (type.equals("folder")) {
    		return folderCMenu;
        } else if (type.equals("window")) {
    		return windowCMenu;
        }
        return null;
	}

	public AnchorPane getAnchorPane() {
		return anchorPane;
	}

	public Scene getScene() {
		return scene;
	}
	
	public void setCurrentSelection(Object selected) {
		this.selected = selected;
	}
	
	public Object getCurrentSelection() {
		return this.selected;
	}
	
	public void setCurrentFolder(ItemFolder folder) {
		this.currentFolder = folder;
	}
	
	public ItemFolder getCurrentFolder() {
		return this.currentFolder;
	}
	
}
