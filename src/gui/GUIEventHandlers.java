package gui;

import java.io.File;
import java.util.ArrayList;
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
	// event click on window
	private EventHandler<MouseEvent> clickWindow;
	
	
	
	// generate event handlers
	public GUIEventHandlers() {
		
		this.root = "C:\\Users\\charo\\Documents\\Documents\\UDEM";
		
		// open root folder
		this.openFileMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
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
//            	FlowPane fp = WindowF.getInstance().getFlowIcons();
//            	ItemFolder folder = (ItemFolder)
//            			((FilesFolders) fp.getUserData()).getParent();;
            	ItemFolder folder = (ItemFolder) WindowF.getInstance().getCurrentFolder().getParent();
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
//            	ContextMenu cm = WindowF.getInstance().getCMenu("file");
//            	VBox vbox = (VBox) cm.getUserData();

            	Object obj = WindowF.getInstance().getCurrentSelection();
            	if(obj.getClass().getSimpleName().equals("VBox")) {
                	VBox vbox = (VBox) obj;
                	
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
                        	// previous name, new name
                        	String pn = ((FilesFolders) vbox.getUserData()).
                        			getFile().getName();
                        	String nn = t.getText();
                        	File file = new File(WindowF.getInstance().getCurrentFolder().getFile().getPath() + "/" + nn);
                        	if(file.exists()) {
                        		WindowF.getInstance().alertMessage("name");
                        	}
                        	((FilesFolders) vbox.getUserData()).updateName(nn, true);
                        	MementoRename mr = new MementoRename(
                    			(FilesFolders) vbox.getUserData(),
                    			pn,
                    			nn
                			);
                        	Command.getInstance().addMemento(mr);
            	        }
                	});
                	
                	scene.setOnMousePressed(event -> {
    	                if (!t.equals(event.getTarget())) {
    		                vbox.requestFocus();
    	                }
                	});
            	}
            	
            	
            }
        };
        
        // delete a file/folder
        // TODO
        // delete triggered by delete key
        this.deleteCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
//            	ContextMenu cm = WindowF.getInstance().getFileCMenu();
//            	VBox vbox = (VBox) cm.getUserData();
//            	VBox vbox = WindowF.getInstance().getCurrentSelection();
            	

            	Object obj = WindowF.getInstance().getCurrentSelection();
            	VBox vbox = null;
            	if(obj.getClass().getSimpleName().equals("VBox")) {
                	vbox = (VBox) obj;
            	}

        		String path = getRoot() + "/recycleBin";
        		String name = ((FilesFolders) vbox.getUserData()).getFile().getName();
        		String newName = name;
        		File newF = new File(path + "/" + name);
        		int i = 1;
        		while(newF.exists()) {
        			newName = FilenameUtils.removeExtension(name) 
        					+ " (" + i + ")." 
        					+ FilenameUtils.getExtension(name);
            		newF = new File(path + "/" + newName);
            		i += 1;
            		System.out.println(newName);
        		}
        		if(!newName.equals(name)) {
        			((FilesFolders) vbox.getUserData()).updateName(newName, false);
        		}
        		// stopped last time
            	MementoDelete md = new MementoDelete(
        			(FilesFolders) vbox.getUserData(),
        			((FilesFolders) vbox.getUserData()).getFile().getParentFile().toString(),
        			name,
        			newName
    			);
            	Command.getInstance().addMemento(md);
            	((FilesFolders) vbox.getUserData()).delete(null);
            }
        };
        
        this.copyCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	Object obj = WindowF.getInstance().getCurrentSelection();
            	VBox vbox = null;
            	if(obj.getClass().getSimpleName().equals("VBox")) {
                	vbox = (VBox) obj;
            	}
            	
            	((FilesFolders) vbox.getUserData()).copy();
            }
        };
            
        // TODO
        // paste in current folder
        // paste triggered by ctrl+v
        this.pasteCMenu = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
            	Object obj = WindowF.getInstance().getCurrentSelection();
            	if(obj.getClass().getSimpleName().equals("VBox")) {
                	((ItemFolder) ((VBox) obj).getUserData()).paste();
            	}
            	if(obj.getClass().getSimpleName().equals("FlowPane")) {
            		WindowF.getInstance().getCurrentFolder().paste();
            		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
            	}
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
            	
            	// ctrl + z
            	if(keysPressed.contains(KeyCode.Z) 
            			&& event.isControlDown() 
            			&& !redoPressed
            			&& !copyPressed
            			&& !pastePressed) {
            		undoPressed = true;
            	}
            	
            	// ctrl + y
            	if(keysPressed.contains(KeyCode.Y) 
            			&& event.isControlDown() 
            			&& !undoPressed
            			&& !copyPressed
            			&& !pastePressed) {
            		redoPressed = true;
            	}
            	
            	// ctrl + c
            	if(keysPressed.contains(KeyCode.C) 
            			&& event.isControlDown() 
            			&& !undoPressed
            			&& !redoPressed
            			&& !pastePressed) {
            		copyPressed = true;
            	}
            	
            	// ctrl + v
            	if(keysPressed.contains(KeyCode.V) 
            			&& event.isControlDown() 
            			&& !undoPressed
            			&& !redoPressed
            			&& !copyPressed) {
            		pastePressed = true;
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
            	if((!keysPressed.contains(KeyCode.C) || !event.isControlDown())
            			&& copyPressed){
            		copyPressed = false;
            		Object obj = ((Node) WindowF.getInstance().getCurrentSelection());
            		if(obj.getClass().getSimpleName().equals("VBox")) {
            			((FilesFolders) ((VBox) obj).getUserData()).copy();
                		System.out.println("copy");
            		}
            	}
            	if((!keysPressed.contains(KeyCode.V) || !event.isControlDown())
            			&& pastePressed){
            		pastePressed = false;
            		WindowF.getInstance().getCurrentFolder().paste();
            		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
            		System.out.println("paste");
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
