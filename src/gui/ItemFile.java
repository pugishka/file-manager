package gui;

import java.io.File;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class ItemFile implements FilesFolders {
	
	public File file;
	public String iconDir = "C:/Users/charo/eclipse-workspace/"
			+ "FileManager/file_icon.png";
	public ItemFolder parent;
	
	public ItemFile(File file, ItemFolder parent) {
		this.file = file;
		this.parent = parent;
	}

	public File getFile() {
		return this.file;
	}

	public String getIconDir() {
		return this.iconDir;
	}

	public ItemFolder getParent() {
		return parent;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
}
