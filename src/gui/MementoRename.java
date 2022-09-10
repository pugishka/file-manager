package gui;

public class MementoRename implements Memento {
	
    private FilesFolders filesFolders;
    private String prevName;
	
    public MementoRename(FilesFolders fF, String prevName) {
        this.filesFolders = fF;
        this.prevName = prevName;
    }
    

	public void restore() {
		filesFolders.updateName(prevName);
	}

	public void printInfo() {
		System.out.println(filesFolders.getFile().getName() + " was named " + prevName);
	}

}
