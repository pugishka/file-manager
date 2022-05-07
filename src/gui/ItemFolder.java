package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


public class ItemFolder implements FilesFolders{

	public ArrayList<FilesFolders> files = 
			new ArrayList<FilesFolders>();
	public File file;
	public String iconDir = "C:/Users/charo/eclipse-workspace/"
			+ "FileManager/folder_icon.png";
	public ItemFolder parent;
	
	public ItemFolder(File file, ItemFolder parent) {
		this.file = file;
		this.parent = parent;
		File[] contents = file.listFiles();
		for (File item : contents) {
			this.addFile(item, this);
		}
	}
	
	public void addFile(File item, ItemFolder parent) {
		if (item.isFile()) {
			files.add(new ItemFile(item, parent));
		} else if (item.isDirectory()) {
			files.add(new ItemFolder(item, parent));
		}
	}
	
	public ArrayList<VBox> getImmediateChildren(){
		ArrayList<VBox> s = new ArrayList<VBox>();
		Collections.sort(files);
		for (FilesFolders f : this.files) {
			s.add(f.generateIcon());
		}
		return s;
	}
	
	public ArrayList<String> getImmediateChildrenS(){
		ArrayList<String> s = new ArrayList<String>();
		Collections.sort(files);
		for (FilesFolders f : this.files) {
			s.add(f.getFile().getName());
		}
		return s;
	}
	
	public void setFiles(ArrayList<FilesFolders> files) {
		this.files = files;
	}

	public void showImmediateChildren() {
		FlowPane fp = WindowF.getInstance().getFlowIcons();
		fp.getChildren().clear();
		ArrayList<VBox> s = getImmediateChildren();
		for(VBox child : s) {
			fp.getChildren().add(child);
		}
		fp.setUserData(this);
	}

	public File getFile() {
		return this.file;
	}

	public String getIconDir() {
		return this.iconDir;
	}

	public ArrayList<FilesFolders> getFiles() {
		return files;
	}

	public ItemFolder getParent() {
		return parent;
	}
	
}
