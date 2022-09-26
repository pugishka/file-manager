package gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.layout.FlowPane;

public class MementoDelete implements Memento {
	
    private FilesFolders filesFolders;
    private String prevDir;
    private String prevName;
    private String newName;
	
    public MementoDelete(FilesFolders fF, String prevDir, String prevName, String newName) {
        this.filesFolders = fF;
        this.prevDir = prevDir;
        this.prevName = prevName;
        this.newName = newName;
    }
    
	public void restore() {
		filesFolders.updatePath(prevDir, WindowF.getInstance().getCurrentFolder());
    	filesFolders.updateName(prevName, false);
    	WindowF.getInstance().getCurrentFolder().showImmediateChildren();
	}
	
	public void redo() {
    	filesFolders.updateName(newName, false);
    	filesFolders.delete(WindowF.getInstance().getCurrentFolder());
    	WindowF.getInstance().getCurrentFolder().showImmediateChildren();
	}

	public void printInfo() {
		System.out.println("File deleted");
	}

}