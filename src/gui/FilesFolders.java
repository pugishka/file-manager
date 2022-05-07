package gui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public interface FilesFolders extends Comparable<FilesFolders> {
	
	public File getFile();
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
		System.out.println(this.getFile().getName());
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
		}
		return r;
    }
	
	public default void updateName(String name) {
		FlowPane fp = WindowF.getInstance().getFlowIcons();
		int i = ((ItemFolder) fp.getUserData()).getFiles().indexOf(this);
		
		File f = getFile();
		String s = (String) f.getParent() + "/" + name;
//		System.out.println((new File(s)).exists());
		
		File newF = new File(s);
		if(newF.exists()) {
			//TODO
		} else {
			f.renameTo(newF);
			getParent().getFiles().set(i, this);
//			getParent().setFiles();
			//TODO
			getParent().getFiles().set(i, this);
			System.out.println(f.generateIcon().getChildren());
			((ItemFolder) fp.getUserData()).showImmediateChildren();
			System.out.println(getParent().getImmediateChildrenS());
		}
	}
	
}
