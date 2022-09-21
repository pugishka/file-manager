package gui;

import java.util.ArrayList;

public interface Memento {
    
	// TODO
	// make mementos for other undo/redo events
	// - paste
	// - move
	// - create folder
    
    public void restore();
    public void redo();

	public void printInfo();
}
