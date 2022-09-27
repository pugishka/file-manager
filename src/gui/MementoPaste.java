package gui;

public class MementoPaste implements Memento {
	
    private ItemFolder parent;
    private FilesFolders newFile;
	
    public MementoPaste(ItemFolder parent, FilesFolders newFile) {
        this.parent = parent;
        this.newFile = newFile;
    }
    

	public void restore() {
		newFile.delete();
		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
	}
	
	public void redo() {
		newFile.updatePath(parent);
		WindowF.getInstance().getCurrentFolder().showImmediateChildren();
	}

	public void printInfo() {
//		System.out.println(filesFolders.getFile().getName() + " was previously named " + prevName);
		System.out.println("File " + newFile.getFile().getName() + " pasted to " + parent.getFile().getName());
	}
}
