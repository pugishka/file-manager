package gui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;

public class GUIEventHandlers {
	
	private static GUIEventHandlers instance;
	
	// event to generate file and folder objects + update visual
	private EventHandler<ActionEvent> openE;
	
	// generate event handlers
	public GUIEventHandlers() {
        openE = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	File dir = new File(
        			"C:\\Users\\charo\\Documents\\Documents\\UDEM"
    			);
//        		File[] contents = dir.listFiles();
//        		FilesFolders.getInstance().clear();
//        		for (File item : contents) {
//        			if (item.isFile()) {
//            			FilesFolders.getInstance().addFile(item);
//        			} else if (item.isDirectory()) {
//            			FilesFolders.getInstance().addFolder(item);
//        			}
//        		}
//        		FilesFolders.getInstance().showIcons();
            	ItemFolder folder = new ItemFolder(dir);
        		File[] contents = dir.listFiles();
        		for (File item : contents) {
        			folder.addFile(item);
        		}
            	folder.showImmediateChildren();
            }
        };
	}
	
	// set open files event on node
	public void openFilesEvent(MenuItem mopen) {
        mopen.setOnAction(openE);
	}
	
	// get instance
	public static GUIEventHandlers getInstance() {
		if (instance == null) {
			instance = new GUIEventHandlers();
		}
		return instance;
	}
}
