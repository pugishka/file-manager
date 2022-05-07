package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class GUIEventHandlers {
	
	private static GUIEventHandlers instance;
	
	// event to generate file and folder objects + update visual
	private EventHandler<ActionEvent> openFileMenu;
	// event to make icon + label vbox clickable
	private EventHandler<MouseEvent> openFileIcon;
	// event to return to previous folder
	private EventHandler<ActionEvent> returnPrevious;
	// event to rename file / folder
	private EventHandler<ActionEvent> renameCMenu;
	
	// generate event handlers
	public GUIEventHandlers() {
		this.openFileMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	File dir = new File(
        			"C:\\Users\\charo\\Documents\\Documents\\UDEM"
    			);
            	ItemFolder folder = new ItemFolder(dir, null);
//        		File[] contents = dir.listFiles();
//        		for (File item : contents) {
//        			folder.addFile(item);
//        		}
            	folder.showImmediateChildren();
            }
        };
        
        this.openFileIcon = new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {
        		FilesFolders f = (FilesFolders) 
            			((Node) e.getSource()).getUserData();
        		
        		if(e.getButton().equals(MouseButton.PRIMARY)){
                    if(e.getClickCount() == 2){
                    	String name = f.getClass().getSimpleName();
                    	
                    	if(name.equals("ItemFile")){
                    		System.out.println("File open");
                    		
                		} else if (name.equals("ItemFolder")) {
                			((ItemFolder) f).showImmediateChildren();
            			}
                    	
                    }
                }
        		if(e.getButton().equals(MouseButton.SECONDARY)){
        			ContextMenu cm = WindowF.getInstance().getFilesCMenu();
        			cm.setUserData(e.getSource());
        			cm.show((Node) e.getSource(), 
	    					e.getScreenX(),
	    					e.getScreenY()
	    					);
                }
        	}
        };
        
		this.returnPrevious = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	FlowPane fp = WindowF.getInstance().getFlowIcons();
            	ItemFolder folder = ((ItemFolder) fp.getUserData());
            	if(!(folder == null)) {
	            	folder.showImmediateChildren();
	        	}
            }
        };
        
		this.renameCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ContextMenu cm = WindowF.getInstance().getFilesCMenu();
            	Label v = (Label) ((Node) cm.getUserData()).lookup(".show");
            	v.getStyleClass().remove("show");
            	v.getStyleClass().add("hide");
            	
            	Label t = new Label("teeeeeeeeeeeeeeeeeeeeeeest");
            	((VBox) cm.getUserData()).getChildren().add(t);
            	
//            	Label.setMargin(t, new Insets(
//            			50.0, 50.0, 50.0, 50.0
//            			));
            	
            	System.out.println(t.getTranslateY());
            }
        };
        
	}
	
	// set openFileMenu event
	public void openFileMenuEvent(MenuItem m) {
        m.setOnAction(openFileMenu);
	}
	
	// set openFile event
	public void openFileIconEvent(VBox icon) {
        icon.setOnMouseClicked(openFileIcon);
	}
	
	// set returnPrevious event
	public void returnPreviousEvent(Button btn) {
        btn.setOnAction(returnPrevious);
	}
	
	// set rename event
	public void renameCMenuEvent(MenuItem m) {
        m.setOnAction(renameCMenu);
	}
	
//	// set returnPrevious event
//	public void contextMenuFileEvent(VBox icon) {
//        icon.setOnMouseClicked(contextMenuFile);
//	}
	
	// get instance
	public static GUIEventHandlers getInstance() {
		if (instance == null) {
			instance = new GUIEventHandlers();
		}
		return instance;
	}
}
