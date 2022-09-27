package gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.layout.FlowPane;

public class MementoDelete implements Memento {
	
    private FilesFolders filesFolders;
    private ItemFolder prevDir;
    private String prevName;
    private String newName;
	
    public MementoDelete(FilesFolders fF, ItemFolder prevDir, String prevName, String newName) {
        this.filesFolders = fF;
        this.prevDir = prevDir;
        this.prevName = prevName;
        this.newName = newName;
    }
    
	public void restore() {
		filesFolders.updatePath(prevDir);
    	filesFolders.updateName(prevName);
    	WindowF.getInstance().getCurrentFolder().showImmediateChildren();
	}
	
	public void redo() {
    	filesFolders.updateName(newName);
    	filesFolders.delete();
    	WindowF.getInstance().getCurrentFolder().showImmediateChildren();
	}

	public void printInfo() {
		System.out.println("File deleted");
	}

}