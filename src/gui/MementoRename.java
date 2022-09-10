package gui;

public class MementoRename implements Memento {
	
    private FilesFolders filesFolders;
    private String prevName;
    private String newName;
	
    public MementoRename(FilesFolders fF, String prevName, String newName) {
        this.filesFolders = fF;
        this.prevName = prevName;
        this.newName = newName;
    }
    

	public void restore() {
		filesFolders.updateName(prevName);
	}
	
	public void redo() {
		filesFolders.updateName(newName);
	}

	public void printInfo() {
//		System.out.println(filesFolders.getFile().getName() + " was previously named " + prevName);
		System.out.println("File " + prevName + " renamed " + newName);
	}

}
