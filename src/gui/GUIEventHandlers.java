package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTextField;

import java.util.HashMap;
import java.util.List;

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

// cm userData = vbox
// vbox userData = FilesFolders
// fp userData = ItemFolder currently opened

public class GUIEventHandlers{
	
	private static GUIEventHandlers instance;
	
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
	
	
	
	// generate event handlers
	public GUIEventHandlers() {
		
		// open root folder
		this.openFileMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	File dir = new File(
        			"C:\\Users\\charo\\Documents\\Documents\\UDEM"
    			);
            	ItemFolder folder = new ItemFolder(dir, null);
            	folder.showImmediateChildren();
            }
        };
        
        // Left of right click on file/folder icon
        // - open file
        // - open folder
        // - show contextual menu
        this.openFileIcon = new EventHandler<MouseEvent>() {
        	public void handle(MouseEvent e) {
        		FilesFolders f = (FilesFolders) 
            			((Node) e.getSource()).getUserData();
        		
        		if(e.getButton().equals(MouseButton.PRIMARY)){
                    if(e.getClickCount() == 2){
                    	String name = f.getClass().getSimpleName();
                    	
                    	if(name.equals("ItemFile")){
                    		
                    		// TODO
                    		// Open file
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
        
        // button to return to the parent folder, up to the root folder
		this.returnPrevious = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	FlowPane fp = WindowF.getInstance().getFlowIcons();
            	ItemFolder folder = (ItemFolder)
            			((FilesFolders) fp.getUserData()).getParent();
            	if(!(folder == null)) {
	            	folder.showImmediateChildren();
	        	}
            }
        };
        
        // rename a file/folder
        // TODO
        // name already used
		this.renameCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ContextMenu cm = WindowF.getInstance().getFilesCMenu();
            	VBox vbox = (VBox) cm.getUserData();
            	Label v = (Label) vbox.lookup(".show");
            	AnchorPane ap = WindowF.getInstance().getAnchorPane();
            	Scene scene = WindowF.getInstance().getScene();
            	v.getStyleClass().remove("show");
            	v.getStyleClass().add("hide");
            	
            	Text text = new Text(v.getText());
                new Scene(new Group(text));
            	TextField t = new TextField(v.getText());

            	Bounds b = v.localToScene(v.getBoundsInLocal());
            	Bounds b2 = ap.localToScene(ap.getBoundsInLocal());
            	AnchorPane.setLeftAnchor(t, b.getMinX() - b2.getMinX());
            	AnchorPane.setTopAnchor(t, b.getMinY() - b2.getMinY());
            	ap.getChildren().add(t);
            	
            	t.setMaxWidth(b2.getMaxX() - b.getMinX());
        		Double prefW = text.getLayoutBounds().getWidth() + 30;
        		if(prefW < t.getMaxWidth()-30) {
        			t.setPrefWidth(prefW);
        		} else {
        			t.setPrefWidth(t.getMaxWidth()-30);
        		}
        		
        		t.requestFocus();
        		t.positionCaret(t.getText().length());
            	
            	t.textProperty().addListener((o, ov, nv) -> {
            		text.setText(nv);
            		Double newW = text.getLayoutBounds().getWidth() + 30;
            		if(newW < t.getMaxWidth()-30) {
            			t.setPrefWidth(newW);
            		} 
            	});
            	
            	
            	t.focusedProperty().addListener((o, ov, nv) -> {
                    if (!nv) {
                    	ap.getChildren().remove(t);
                    	v.getStyleClass().remove("hide");
                    	v.getStyleClass().add("show");
                    }
                });
            	
            	t.setOnKeyReleased(event -> {
        	        if (event.getCode().equals(KeyCode.ENTER)) {
                    	ap.getChildren().remove(t);
                    	v.getStyleClass().remove("hide");
                    	v.getStyleClass().add("show");
                    	String pn = ((FilesFolders) vbox.getUserData()).
                    			getFile().getName();
                    	String nn = t.getText();
                    	((FilesFolders) vbox.getUserData()).updateName(nn);
                    	MementoRename mr = new MementoRename(
                			(FilesFolders) vbox.getUserData(),
                			pn,
                			nn
            			);
                    	Command.getInstance().addMemento(mr);
        	        }
            	});
            	
            	scene.setOnMousePressed(event -> {
	                if (!t.equals(event.getSource())) {
		                vbox.requestFocus();
	                }
            	});
            	
            	
            }
        };
        
        // delete a file/folder
        // TODO
        // delete triggered by delete key
        this.deleteCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ContextMenu cm = WindowF.getInstance().getFilesCMenu();
            	VBox vbox = (VBox) cm.getUserData();
            	((FilesFolders) vbox.getUserData()).delete();
            }
        };
        
        // TODO
        // copy triggered by ctrl+c
            
        this.copyCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	ContextMenu cm = WindowF.getInstance().getFilesCMenu();
            	VBox vbox = (VBox) cm.getUserData();
            	((FilesFolders) vbox.getUserData()).copy();
            }
        };
            
        // TODO
        // paste
        // paste triggered by ctrl+v
            
        this.pasteCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){}
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
            	
            	// ctrl + z
            	if(keysPressed.contains(KeyCode.Z) 
            			&& event.isControlDown() 
            			&& !redoPressed) {
            		undoPressed = true;
            	}
            	
            	// ctrl + y
            	if(keysPressed.contains(KeyCode.Y) 
            			&& event.isControlDown() 
            			&& !undoPressed) {
            		redoPressed = true;
            	}
            	
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
//
//	@Override
//	public void keyTyped(KeyEvent e) {
//		System.out.println("test");
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		System.out.println("test");
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		System.out.println("test");
//		
//	}
	
	// get instance
	public static GUIEventHandlers getInstance() {
		if (instance == null) {
			instance = new GUIEventHandlers();
		}
		return instance;
	}
}
