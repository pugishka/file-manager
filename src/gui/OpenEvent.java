//package gui;
//
//import java.io.File;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Label;
//
//public class OpenEvent implements EventHandler<ActionEvent>{
//
//	@Override
//	public void handle(ActionEvent e) {
//    	File home = new File(
//			"C:\\Users\\charo\\Documents\\Documents\\UDEM"
//		);
//		File[] contents = home.listFiles();
//		for (File item : contents) {
//			Label label = null;
//			if (item.isFile()) {
//				label = new Label("File : " + item.getName());
//			} else if (item.isDirectory()) {
//				label = new Label("Dir : " + item.getName());
//			}
//			container.getChildren().add(label);
//		}
//	}
//
//}
