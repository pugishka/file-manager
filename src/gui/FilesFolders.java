package gui;

import java.io.File;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
import java.awt.Toolkit;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public interface FilesFolders extends Comparable<FilesFolders> {
	
	public File getFile();
	public void setFile(File f);
	public String getIconDir();
	public ItemFolder getParent();

	public default VBox generateIcon() {
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(15));
		
		
		Image image = new Image(getIconDir());
		ImageView icon = new ImageView(image);
		icon.setFitWidth(80);
		icon.setPreserveRatio(true);
		
		Label label = new Label(getFile().getName());
		label.setWrapText(true);
		label.getStyleClass().add("show");
		
		vbox.setMaxWidth(80);
		vbox.getChildren().add(icon);
		vbox.getChildren().add(label);
		vbox.setFillWidth(true);
//		System.out.println(this.getFile().getName());
		vbox.setUserData(this);
		vbox.getStyleClass().add("icon");
		
		GUIEventHandlers eHandler = GUIEventHandlers.getInstance();
		eHandler.openFileIconEvent(vbox);
		return vbox;
	}
	
	public default int compareTo(FilesFolders item){
		boolean isFile1 = getFile().isFile();
		boolean isFile2 = item.getFile().isFile();
		int r = 0;
		if (isFile1 && !isFile2) {
			r = 1;
		} else if (!isFile1 && isFile2) {
			r = -1;
		} else {
			r = getFile().getName().compareTo(item.getFile().getName());
		}
		return r;
    }
	
	public default void updateName(String name) {
//		FlowPane fp = WindowF.getInstance().getFlowIcons();
		int i = getParent().getFiles().indexOf(this);
		
		File f = getFile();
		String s = (String) f.getParent() + "/" + name;
		File newF = new File(s);
		
		if(newF.exists()) {
			//TODO
		} else {
			f.renameTo(newF);
			this.setFile(newF);
			getParent().getFiles().set(i, this);
			getParent().showImmediateChildren();
		}
	}
	
	public default void updatePath(String path, ItemFolder reload) {
		int i = getParent().getFiles().indexOf(this);
		
		File f = getFile();
		String s = path + "/" + f.getName();
		File newF = new File(s);
		
		if(newF.exists()) {
			//TODO
		} else {
			f.renameTo(newF);
			this.setFile(newF);
			getParent().getFiles().remove(i);
			
			// find the FilesFolders to put this in
			FilesFolders newFolder = GUIEventHandlers.getInstance().getRootObject();
			File root = newFolder.getFile();
			
			// separate each folder from root directory
			ArrayList<String> dir = new ArrayList<String>();
			newF = new File(path);
			
			// TODO
			// Can a file and folder have the same name ?
			
	    	while (newF.getParentFile() != null && !(newF.toString().equals(root.toString()))) {
	    		dir.add(newF.getName());
		        newF = newF.getParentFile();
	    	}
	    	
	    	for (int j=dir.size()-1; j>=0; j--) {
				newFolder = ((ItemFolder) newFolder).getFilesFolders(dir.get(j));
	    	}
	    	
			((ItemFolder) newFolder).getFiles().add(this);
			reload.showImmediateChildren();
			this.setParent((ItemFolder) newFolder);
		}
	}
	
	public void setParent(ItemFolder parent);
	
	
	public default void delete(ItemFolder reload) {
		if(reload == null) {
			reload = this.getParent();
		}
		updatePath(GUIEventHandlers.getInstance().getRoot() + "/recycleBin", reload);
	}
	
	public default void deleteBin() {
		int i = getParent().getFiles().indexOf(this);
		Desktop.getDesktop().moveToTrash(this.getFile());
		getParent().getFiles().remove(i);
		getParent().showImmediateChildren();
	}
	
	public default void copy() {
        
		ArrayList<File> listFiles = new ArrayList<File>();
		listFiles.add(this.getFile());
		FileTransferable ft = new FileTransferable(listFiles);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(ft, new ClipboardOwner() {
            @Override
            public void lostOwnership(
            		Clipboard clipboard, 
            		Transferable contents) {}
        });
	}
	
}
