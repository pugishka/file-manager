package gui;

import java.io.File;


public class ItemFile implements FilesFolders {
	
	public File file;
	
	public ItemFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return this.file;
	}

	public String generateIcon() {
		return this.file.getName();
	}
	
}
