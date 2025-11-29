package controller;


import java.util.List;
import java.util.Stack;
import model.Album;
import model.SoundClip;


/*
 * This class is responsible for managing the undo and redo stacks. The undo stack
 * contains a sequence of Memento object that represents the user state at different points in time
 * The redo stack contains Memento objects that were previously undone and can be redone later.
 * 
 */
public class UndoRedo {
    private Stack<Memento> undoStack;
    private Stack<Memento> redoStack;
  

    public UndoRedo() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    public Stack<Memento> getUndoStack(){
    	return undoStack;
    }
    public Stack<Memento> getRedoStack(){
    	return redoStack;
    }
    
    public void save(String type, Album album, Album parentAlbum, List<SoundClip> clips) {
        Memento memento = new Memento(type, album, parentAlbum, clips);
        undoStack.push(memento);
        redoStack.clear();
    }
}

