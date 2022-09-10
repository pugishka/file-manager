package gui;

import java.util.ArrayList;

public class Command{
	
	private static Command instance;
    private ArrayList<Memento> mementos = new ArrayList<Memento>();
    private int index = -1;
    
    public void addMemento(Memento m) {
		int i = mementos.size()-1;
		while(i != index) {
    		mementos.remove(i);
    		i--;
		}
		mementos.add(m);
    	index++;
    	printAll();
    }
    
    private void printAll() {
    	for(int i=0; i<mementos.size(); i++) {
    		mementos.get(i).printInfo();
    	}
    }
    
    public void undo() {
    	if (index > -1) {
    		Memento lastM = mementos.get(index);
    		lastM.restore();
    		index--;
    	}
    }
    
    public void redo() {
    	if (index < (mementos.size()-1)) {
    		Memento nextM = mementos.get(index+1);
    		nextM.restore();
    		index++;
    	}
    }
    
	public static Command getInstance() {
		if (instance == null) {
			instance = new Command();
		}
		return instance;
	}
}