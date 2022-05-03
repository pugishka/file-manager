package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.Label;


public class ItemFolder implements FilesFolders {

	public ArrayList<FilesFolders> files = 
			new ArrayList<FilesFolders>();
	public File file;
	
	public ItemFolder(File file) {
		this.file = file;
	}
	
	public void addFile(File item) {
		if (item.isFile()) {
			files.add(new ItemFile(item));
		} else if (item.isDirectory()) {
			files.add(new ItemFolder(item));
		}
	}
//	
//	public void addFolder(File item) {
//		folders.add(new ItemFolder(item));
//	}

	public File getFile() {
		return this.file;
	}

	public String generateIcon() {
		return this.file.getName();
	}
	
	public ArrayList<String> getImmediateChildren(){
		ArrayList<String> s = new ArrayList<String>();
		for (FilesFolders f : this.files) {
			s.add(f.generateIcon());
		}
		return s;
	}
	
	public void showImmediateChildren() {
		WindowF.getInstance().getFlowIcons().getChildren().clear();
		ArrayList<String> s = getImmediateChildren();
		for(String name : s) {
			Label label = new Label(name + "---");
			WindowF.getInstance().getFlowIcons().getChildren().add(label);
		}
	}
	
//	public void showIcons() {
////		WindowF.getInstance().getFlowIcons().getChildren().clear();
////		for (ItemFolder item : ListFilesFolders.getInstance().getFolders()) {
////			Label label = new Label(
////				item.getClass() + " " + item.getFile().getName()
////			);
////			WindowF.getInstance().getFlowIcons().getChildren().add(label);
////		}
////		for (ItemFile item : ListFilesFolders.getInstance().getFiles()) {
////			Label label = new Label(
////				item.getClass() + " " + item.getFile().getName()
////			);
////			WindowF.getInstance().getFlowIcons().getChildren().add(label);
////		}
//		for (ItemFile item : files) {
//			Node icon = item.generateIcon(); 
//			WindowF.getInstance().getFlowIcons().getChildren().add(icon);
//		}
//	}
	
//	public ItemFolder(File file) {
//		this.file = file;
//	}
//
//	public File getFile() {
//		return file;
//	}
//
//	public void setFile(File file) {
//		this.file = file;
//	}
	
}
