package gui;

import java.util.ArrayList;

public interface Memento {
    
	// TODO
	// make mementos for other undo/redo events
	// - paste
	// - delete
	// - move
	// - create folder
    
    public void restore();
    public void redo();

	public void printInfo();
}
