package gui;

import java.io.File;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ActionEvents {
	
	private static String root = "C:\\Users\\charo\\Documents\\Documents\\UDEM";
	
	protected static void copy() {
    	Object obj = WindowF.getInstance().getCurrentSelection();
    	VBox vbox = null;
    	if(obj.getClass().getSimpleName().equals("VBox")) {
        	vbox = (VBox) obj;
    	}
    	
    	((FilesFolders) vbox.getUserData()).copy();

		System.out.println("copy");
	}
	
	protected static void paste(boolean key) {
    	if(key) {
    		WindowF.getInstance().getCurrentFolder().paste();
    		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
    	} else {
        	Object obj = WindowF.getInstance().getCurrentSelection();
        	if(obj.getClass().getSimpleName().equals("VBox")) {
        		ItemFolder parent = (ItemFolder) ((VBox) obj).getUserData();
            	parent.paste();
        	}
        	if(obj.getClass().getSimpleName().equals("FlowPane")) {
        		ItemFolder parent = 
        				WindowF.getInstance().getCurrentFolder();
        		parent.paste();
        		parent.showImmediateChildren();
        	}
    	}
	}
	
	protected static void delete() {
		Object obj = WindowF.getInstance().getCurrentSelection();
    	VBox vbox = null;
    	if(obj.getClass().getSimpleName().equals("VBox")) {
        	vbox = (VBox) obj;
    	}

		FilesFolders file = (FilesFolders) vbox.getUserData();
		String name = file.getFile().getName();
    	file.delete();
		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
		
		String newName = file.getFile().getName();
//		System.out.println(newName);
    	MementoDelete md = new MementoDelete(
			file,
			file.getParent(),
			name,
			newName
		);
    	Command.getInstance().addMemento(md);
    	System.out.println("delete");
	}
	
	protected static void initWorkspace() {
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
	
	protected static void returnFolder() {
    	ItemFolder folder = WindowF.getInstance().
    			getCurrentFolder().getParent();
    	if(!(folder == null)) {
        	folder.showImmediateChildren();
    	}
	}
	
	protected static void rename() {
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
                	
                	((FilesFolders) vbox.getUserData()).updateName(nn);
                	WindowF.getInstance().getCurrentFolder().showImmediateChildren();
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
	
}
