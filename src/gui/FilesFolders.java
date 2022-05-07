package gui;

import java.io.File;
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
	
}
