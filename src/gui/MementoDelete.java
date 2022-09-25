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
	
    public MementoDelete(FilesFolders fF, String prevDir, String prevName) {
        this.filesFolders = fF;
        this.prevDir = prevDir;
        this.prevName = prevName;
    }
    
	public void restore() {
		// search file in recycle bin folder
    	FlowPane fp = WindowF.getInstance().getFlowIcons();
//		filesFolders.updatePath(prevDir, (ItemFolder) fp.getUserData());
		filesFolders.updatePath(prevDir, WindowF.getInstance().getCurrentFolder());
	}
	
	public void redo() {
    	FlowPane fp = WindowF.getInstance().getFlowIcons();
//		filesFolders.delete((ItemFolder) fp.getUserData());
    	filesFolders.delete(WindowF.getInstance().getCurrentFolder());
	}

	public void printInfo() {
		System.out.println("File deleted");
	}

}