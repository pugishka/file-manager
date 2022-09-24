package gui;

public class MementoPaste implements Memento {
	
    private FilesFolders parent;
    private FilesFolders newFile;
	
    public MementoPaste(FilesFolders parent, FilesFolders newFile) {
        this.parent = parent;
        this.newFile = newFile;
    }
    

	public void restore() {
		newFile.delete(WindowF.getInstance().getCurrentFolder());
	}
	
	public void redo() {
		newFile.updatePath(parent.getFile().getPath(), WindowF.getInstance().getCurrentFolder());
	}

	public void printInfo() {
//		System.out.println(filesFolders.getFile().getName() + " was previously named " + prevName);
		System.out.println("File " + newFile.getFile().getName() + " pasted to " + parent.getFile().getName());
	}
}
