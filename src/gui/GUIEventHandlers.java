package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JTextField;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.event.KeyListener;
import javafx.scene.input.KeyEvent;

// vbox userData = FilesFolders
// fp userData = ItemFolder currently opened

// TODO
// error messages
// refresh if files changed in real file explorer

public class GUIEventHandlers{
	
	private static GUIEventHandlers instance;
	
	private String root;
	private ItemFolder recycleBin;
	private ItemFolder rootObject;
	
	// event to generate file and folder objects + update visual
	private EventHandler<ActionEvent> openFileMenu;
	// event to make icon + label vbox clickable
	private EventHandler<MouseEvent> openFileIcon;
	// event to return to previous folder
	private EventHandler<ActionEvent> returnPrevious;
	// event to rename file / folder
	private EventHandler<ActionEvent> renameCMenu;
	// event to delete file / folder
	private EventHandler<ActionEvent> deleteCMenu;
	// event to copy file / folder
	private EventHandler<ActionEvent> copyCMenu;
	// event to paste file / folder
	private EventHandler<ActionEvent> pasteCMenu;
	// listen to keys pressed and released
	private EventHandler<KeyEvent> listenKeysPressed;
	private EventHandler<KeyEvent> listenKeysReleased;
	private List<KeyCode> keysPressed = new ArrayList<KeyCode>();
	// undo (ctrl+z) pressed ? 
	private boolean undoPressed = false;
	// redo (ctrl+y) pressed ?
	private boolean redoPressed = false;
	// copy (ctrl+c) pressed ?
	private boolean copyPressed = false;
	// paste (ctrl+v) pressed ?
	private boolean pastePressed = false;
	// delete pressed ?
	private boolean deletePressed = false;
	// event click on window
	private EventHandler<MouseEvent> clickWindow;
	
	
	
	// generate event handlers
	public GUIEventHandlers() {
		
		this.root = "C:\\Users\\charo\\Documents\\Documents\\UDEM";
		
		// open root folder
		this.openFileMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ActionEvents.initWorkspace();
            }
        };
        
        
        // Left of right click on file/folder icon
        // - open file
        // - open folder
        // - show contextual menu
        this.openFileIcon = new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {
        		FilesFolders f = (FilesFolders) 
            			((VBox) e.getSource()).getUserData();
            	String name = f.getClass().getSimpleName();
        		
        		if(e.getButton().equals(MouseButton.PRIMARY)){
                    if(e.getClickCount() == 2){
                    	if(name.equals("ItemFile")){
                    		
                    		// TODO
                    		// Open file
                    		System.out.println("File open");
                    		
                		} else if (name.equals("ItemFolder")) {
                			((ItemFolder) f).showImmediateChildren();
            			}
                    } else {
                    	WindowF.getInstance().setCurrentSelection(
                    			(VBox) e.getSource());
                    }
                }
        		
        		if(e.getButton().equals(MouseButton.SECONDARY)){
        			ContextMenu cm = null;
                	if(name.equals("ItemFile")){
            			cm = WindowF.getInstance().getCMenu("file");
                		
            		} else if (name.equals("ItemFolder")) {
            			cm = WindowF.getInstance().getCMenu("folder");
        			}
                	WindowF.getInstance().setCurrentSelection(
                			(VBox) e.getSource());
                	
        			cm.show((VBox) e.getSource(), 
	    					e.getScreenX(),
	    					e.getScreenY()
	    					);
                }
        	}
        };
        
        // button to return to the parent folder, up to the root folder
		this.returnPrevious = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ActionEvents.returnFolder();
            }
        };
        
        // rename a file/folder
        // TODO
        // name already used
		this.renameCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ActionEvents.rename();
            }
        };
        
        // delete a file/folder
        // TODO
        // delete triggered by delete key
        this.deleteCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ActionEvents.delete();
            }
        };
        
        this.copyCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ActionEvents.copy();
            }
        };
            
        // memento in itemFolder
        this.pasteCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ActionEvents.paste(false);
        	}
        };
        
        
        // listening to the keys, wait for shortcuts
        
        // TODO
        // implement other ctrl shortcuts
        this.listenKeysPressed = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	if(!keysPressed.contains(event.getCode())) {
                	keysPressed.add(event.getCode());
            	}
            	
            	// only the first detected will = true
            	
//            	ArrayList<Boolean> actionsPressed = new ArrayList<Boolean>(
//            			Arrays.asList(
//            					undoPressed, 
//            					redoPressed, 
//            					copyPressed, 
//            					pastePressed)
//            			);
            	
            	// ctrl + z
            	if(event.isControlDown()) {
                	if(keysPressed.contains(KeyCode.Z)) {
                		undoPressed = true;
                	} else if (keysPressed.contains(KeyCode.Y)) {
                		redoPressed = true;
                	} else if (keysPressed.contains(KeyCode.C)) {
                		copyPressed = true;
                	} else if(keysPressed.contains(KeyCode.V)) {
                		pastePressed = true;
                	}
            	} else if(keysPressed.contains(KeyCode.DELETE)){
            		deletePressed = true;
            	}
            	
//            	
//            	if
//            			&& event.isControlDown() 
//            			&& !undoPressed
//            			&& !copyPressed
//            			&& !pastePressed) {
//            	}
//            	
//            	// ctrl + c
//            	if(
//            			&& event.isControlDown() 
//            			&& !undoPressed
//            			&& !redoPressed
//            			&& !pastePressed) {
//            	}
//            	
//            	// ctrl + v
//            			&& event.isControlDown() 
//            			&& !undoPressed
//            			&& !redoPressed
//            			&& !copyPressed) {
//            	}
            	
            }
        };
        
	    this.listenKeysReleased = new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent event) {
	        	
	        	keysPressed.remove(event.getCode());
	        	
            	if((!keysPressed.contains(KeyCode.Z) || !event.isControlDown())
            			&& undoPressed){
            		undoPressed = false;
            		Command.getInstance().undo();
            		System.out.println("undo");
            	}
            	if((!keysPressed.contains(KeyCode.Y) || !event.isControlDown())
            			&& redoPressed){
            		redoPressed = false;
            		Command.getInstance().redo();
            		System.out.println("redo");
            	}
            	if((!keysPressed.contains(KeyCode.C) || !event.isControlDown())
            			&& copyPressed){
            		copyPressed = false;
            		ActionEvents.copy();
            	}
            	if((!keysPressed.contains(KeyCode.V) || !event.isControlDown())
            			&& pastePressed){
            		pastePressed = false;
            		ActionEvents.paste(true);
            		System.out.println("paste");
            	}
            	if(!keysPressed.contains(KeyCode.DELETE) && deletePressed) {
            		deletePressed = false;
            		
            		ActionEvents.delete();
//            		Object obj = ((Node) WindowF.getInstance().getCurrentSelection());
//            		if(obj.getClass().getSimpleName().equals("VBox")) {
//            			((FilesFolders) ((VBox) obj).getUserData()).delete();
//                		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
//                		System.out.println("delete");
//            		}
            	}
	        }
	    };
	    
	    // TODO
	    // when window clicked
	    
        this.clickWindow = new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {
        		String target = e.getTarget().getClass().getSimpleName();
    			ContextMenu cm = WindowF.getInstance().getCMenu("window");
    			if(target.equals("FlowPane")) {
	        		if(e.getButton().equals(MouseButton.SECONDARY)){
	                	WindowF.getInstance().setCurrentSelection((FlowPane) e.getTarget());
	        			cm.show((Node) e.getTarget(), 
		    					e.getScreenX(),
		    					e.getScreenY()
		    					);
	                } else {
	                	WindowF.getInstance().setCurrentSelection((FlowPane) e.getTarget());
	            		cm.hide();
	                }
    			} else {
            		cm.hide();
    			}
        	}
        };
        
        // TODO
        // message that file/folder sent to recycle bin
        
        // TODO
        // shortcut recycle bin
            
        // TODO
        // new folder
            
        // TODO
        // select area
        
        
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
	
	// set delete event
	public void deleteCMenuEvent(MenuItem m) {
        m.setOnAction(deleteCMenu);
	}
	
	// set copy event
	public void copyCMenuEvent(MenuItem m) {
        m.setOnAction(copyCMenu);
	}
	
	// set paste event
	public void pasteCMenuEvent(MenuItem m) {
        m.setOnAction(pasteCMenu);
	}
	
	// set undo event
	
	public EventHandler<KeyEvent> undoKeyPressed() {
		return this.listenKeysPressed;
	}
	public EventHandler<KeyEvent> undoKeyReleased() {
		return this.listenKeysReleased;
	}
	
	// set right click on window event
	
	public void clickWindowEvent(FlowPane window) {
	    window.setOnMouseClicked(clickWindow);
	}
	
	// get instance
	public static GUIEventHandlers getInstance() {
		if (instance == null) {
			instance = new GUIEventHandlers();
		}
		return instance;
	}

	public String getRoot() {
		return this.root;
	}

	public void setRecycleBin(ItemFolder rB) {
		this.recycleBin = rB;
	}

	public void setRootObject(ItemFolder rO) {
		this.rootObject = rO;
	}

	public ItemFolder getRecycleBin() {
		return this.recycleBin;
	}

	public ItemFolder getRootObject() {
		return this.rootObject;
	}
}
