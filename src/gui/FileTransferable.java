package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// stackoverflow.com/questions/31798646/can-java-system-clipboard-copy-a-file

public class FileTransferable implements Transferable {

    private List listFiles;

    public FileTransferable(List listFiles) {
        this.listFiles = listFiles;
    }

	@Override
	public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.javaFileListFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.javaFileListFlavor.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException {
        return listFiles;
	}

}
