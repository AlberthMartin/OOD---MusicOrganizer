package controller;


import java.util.List;
import java.util.Set;
import java.util.*;

import model.Album;
import model.FlaggedSoundClips;
import model.GreatSoundClips;
import model.SoundClip;
import model.SoundClipBlockingQueue;
import model.SoundClipLoader;
import model.SoundClipPlayer;
import view.MusicOrganizerWindow;
import model.Observer;

public class MusicOrganizerController implements Subject {

    private MusicOrganizerWindow view;
    private SoundClipBlockingQueue queue;
    private Album root;
    private List<Observer> observers;
    private UndoRedo undoRedo;
    private FlaggedSoundClips flaggedsoundclipsAlbum;
    private GreatSoundClips greatsoundclipsAlbum;

    //Identifing the "type" of the memento to know what to do when undoing or redoing.
    private final String type_addAlbum = "addAlbum";
    private final String type_deleteAlbum = "deleteAlbum";
    private final String type_addSC = "addSC";
    private final String type_removeSC = "removeSC";


    public MusicOrganizerController() {

        // TODO: Create the root album for all sound clips
        root = new Album(null, "All Sound Clips");


        // Create the blocking queue
        queue = new SoundClipBlockingQueue();

        // Create a separate thread for the sound clip player and start it

        (new Thread(new SoundClipPlayer(queue))).start();

        // Responsible for managing the undo and redo stacks
        undoRedo = new UndoRedo();

        //Create the array to hold the observers to the class
        observers = new ArrayList<Observer>();

        //Create the search based albums
        flaggedsoundclipsAlbum = new FlaggedSoundClips(null, "Flagged Sound Clips", this, this);

        greatsoundclipsAlbum = new GreatSoundClips(null, "Great Sound Clips", this, this);
    }

    /**
     * Load the sound clips found in all subfolders of a path on disk. If path is not
     * an actual folder on disk, has no effect.
     */
    public Set<SoundClip> loadSoundClips(String path) {
        Set<SoundClip> clips = SoundClipLoader.loadSoundClips(path);

        List<SoundClip> Arrayclips = new ArrayList<>(clips);

        for (int i = 0; i < Arrayclips.size(); i++) {
            root.addClip(Arrayclips.get(i));
        }

        return clips;
    }

    public void registerView(MusicOrganizerWindow view) {
        this.view = view;
    }

    /**
     * Returns the root album
     */
    public Album getRootAlbum() {
        return root;
    }

    public Album getFlaggedsoundclipsAlbum() {
        return flaggedsoundclipsAlbum;
    }

    public Album getGreatsoundclipsAlbum() {
        return greatsoundclipsAlbum;
    }

    /**
     * Adds an album to the Music Organizer
     */
    public Album addNewAlbum(Album parent, String title) {
        //precondition
        assert parent != null;
        assert title != null;

        //Creates an new album
        Album album = new Album(parent, title);
        //Adds the new subalbum to it's parents sub album
        parent.addSubAlbum(album);
        //updates the view for album added
        view.onAlbumAdded(parent, album);
        //saves the action
        undoRedo.save("addAlbum", album, parent, null);
        view.updateRedoButtonVisibility(undoRedo.getRedoStack());
        view.updateUndoButtonVisibility(undoRedo.getUndoStack());

        //postcondition
        assert album.getParentAlbum() == parent;
        assert album.getTitle() == title;
        assert album.getParentAlbum().getSubAlbums().contains(album);

        return album;
    }

    /**
     * Removes an album from the Music Organizer
     */
    public void deleteAlbum(Album album) {
        //precondition
        assert album != null;

        //Removes the album from it's parent album's subalbums
        album.getParentAlbum().removeSubAlbum(album);
        //Updates the view
        view.onAlbumRemoved(album);
        //Saves the action
        undoRedo.save("deleteAlbum", album, album.getParentAlbum(), null);
        view.updateRedoButtonVisibility(undoRedo.getRedoStack());
        view.updateUndoButtonVisibility(undoRedo.getUndoStack());

        //postcondition
        assert !album.getParentAlbum().getSubAlbums().contains(album);

    }

    /**
     * Adds sound clips to an album and its parent albums
     */
    public void addSoundClips(List<SoundClip> clips, Album album) {
        //precondition
        assert album != null;

        for (SoundClip clip : clips) {
            assert clip != null;
        }

        for (SoundClip clip : clips) {
            //Adds the clip to the album
            album.addClip(clip);
            //adds the clip to the parent albums
            album.addClipToParentAlbums(clip);
            //Updates the view
            view.onClipsUpdated();
        }
        //Saves the action
        undoRedo.save("addSC", album, album.getParentAlbum(), clips);
        view.updateRedoButtonVisibility(undoRedo.getRedoStack());
        view.updateUndoButtonVisibility(undoRedo.getUndoStack());

        for (SoundClip clip : clips) {
            assert album.getSoundClips().contains(clip);
            assert album.getParentAlbum().getSoundClips().contains(clip) || album.getParentAlbum() == null;
        }
    }

    /**
     * Removes sound clips from an album and from it's subalbum
     */
    public void removeSoundClips(List<SoundClip> clips, Album album) {
        //preconditions
        assert album != null;

        for (SoundClip clip : clips) {
            assert clip != null;
            assert album.containsClip(clip);
        }

        for (SoundClip clip : clips) {
            //removes the clip from the album
            album.removeClip(clip);
            //removes the clip from the sub albums
            album.removeClipFromSubAlbums(clip);
            //updates the view
            view.onClipsUpdated();
        }
        //saves the action
        undoRedo.save("removeSC", album, album.getParentAlbum(), clips);
        view.updateRedoButtonVisibility(undoRedo.getRedoStack());
        view.updateUndoButtonVisibility(undoRedo.getUndoStack());
        //postcondition
        for (SoundClip clip : clips) {
            assert !album.containsClip(clip);
        }

    }

    /*
     * Undoes the latest action
     */
    public void Undo() {
        if (undoRedo.getUndoStack().empty()) {
            //Noting to undo
            return;
        }
        Memento memento = undoRedo.getUndoStack().pop();
        undoRedo.getRedoStack().push(memento);

        //if your previous action was to add an album
        //Undo adding an album
        if (memento.getType().equals(type_addAlbum)) {
            memento.getParentAlbum().removeSubAlbum(memento.getAlbum());
            view.onAlbumRemoved(memento.getAlbum());
        }

        //If your previous action was to delete a album
        //Undo deleting an album
        else if (memento.getType().equals(type_deleteAlbum)) {
            if (memento.getParentAlbum() != null) {

                //adds the prevouisly deleted album to it's parent albums sub album
                memento.getParentAlbum().addSubAlbum(memento.getAlbum());

                view.onAlbumAdded(memento.getParentAlbum(), memento.getAlbum());

                //also reqursively goes thrue the memento albums sub albums and update the view
                recursivelyGoThruSubAlbumsAndUpdateView(memento.getAlbum());
            }
        }

        //If your previous action was adding soundclips to an album
        //Undo adding soundclips
        else if (memento.getType().equals(type_addSC)) {

            for (SoundClip clip : memento.getClips()) {
                memento.getAlbum().removeClip(clip);
                memento.getAlbum().removeClipFromParentAlbums(clip);
                view.onClipsUpdated();
            }
        }
        //if your previous action was removing soundclips
        //Undo removing soundclips
        else if (memento.getType().equals(type_removeSC)) {
            //adds the previously deleted clips back to the album
            for (SoundClip clip : memento.getClips()) {
                memento.getAlbum().addClip(clip);
                memento.getAlbum().addClipToSubAlbums(clip);
                view.onClipsUpdated();
            }
        }
        view.updateRedoButtonVisibility(undoRedo.getRedoStack());
        view.updateUndoButtonVisibility(undoRedo.getUndoStack());
    }


    //A helper method for the undo method
    private void recursivelyGoThruSubAlbumsAndUpdateView(Album album) {
        assert album != null;
        //adds back the subalbums to the album
        if (album.getSubAlbums() != null) {
            for (Album subAlbum : album.getSubAlbums()) {
                view.onAlbumAdded(album, subAlbum);
                recursivelyGoThruSubAlbumsAndUpdateView(subAlbum);
            }
        }
    }

    /*
     * Redoes the latest undo
     */
    public void Redo() {
        if (undoRedo.getRedoStack().empty()) {
            // Nothing to redo
            return;
        }
        // Get the last memento from the redo stack
        Memento memento = undoRedo.getRedoStack().pop();
        undoRedo.getUndoStack().push(memento);

        // Redo adding an album
        if (memento.getType().equals(type_addAlbum)) {
            memento.getParentAlbum().addSubAlbum(memento.getAlbum());
            view.onAlbumAdded(memento.getParentAlbum(), memento.getAlbum());
        }
        // Redo deleting an album
        else if (memento.getType().equals(type_deleteAlbum)) {
            memento.getAlbum().getParentAlbum().removeSubAlbum(memento.getAlbum());
            view.onAlbumRemoved(memento.getAlbum());
        }
        //Redo adding sound clips
        else if (memento.getType().equals(type_addSC)) {
            for (SoundClip clip : memento.getClips()) {
                memento.getAlbum().addClip(clip);
                memento.getAlbum().addClipToParentAlbums(clip);
                view.onClipsUpdated();
            }
        }
        //Redo removing sound clips
        else if (memento.getType().equals(type_removeSC)) {
            for (SoundClip clip : memento.getClips()) {
                memento.getAlbum().removeClip(clip);
                memento.getAlbum().removeClipFromSubAlbums(clip);
                view.onClipsUpdated();
            }
        }
        view.updateRedoButtonVisibility(undoRedo.getRedoStack());
        view.updateUndoButtonVisibility(undoRedo.getUndoStack());
    }

    public void flagSelectedClips(List<SoundClip> soundClips) {
        //Flags the selected sound clips
        for (SoundClip SC : soundClips) {
            SC.setFlagged(true);
        }
        //Update the view
        if (view.getSelectedAlbum() != null) {
            view.onClipsUpdated();
        } else {
            //If no album was selected
            view.onClipsUpdatedNoAlbumSelected();
        }

        //notify observers
        notifyObservers();
    }

    public void removeFlagFromSelectedClips(List<SoundClip> soundClips) {
        //Removes the flag from the selected sound clips.
        for (SoundClip SC : soundClips) {
            SC.setFlagged(false);
        }

        //Updates the view
        if (view.getSelectedAlbum() != null) {
            view.onClipsUpdated();
        } else {
            //If no album was selected
            view.onClipsUpdatedNoAlbumSelected();
        }

        //Notify the search based albums
        notifyObservers();
    }

    public void rateSoundClips(List<SoundClip> soundClips) {
        //Goes thru all the selected sound clips
        for (SoundClip SC : soundClips) {
            //Gets the rating from user returned as a string
            String s = view.promptForRateSoundClip();
            //Checks if the user input was accurate
            if (s.equals("0") || s.equals("1") || s.equals("2") || s.equals("3")
                    || s.equals("4") || s.equals("5")) {
                //turns the rating from string to integer
                int i = Integer.parseInt(s);
                //Sets the sound clips rating
                SC.setRating(i);

                //Updates the view
                if (view.getSelectedAlbum() != null) {
                    view.onClipsUpdated();
                } else {
                    view.onClipsUpdatedNoAlbumSelected();
                }

                //Notify the observers
                notifyObservers();
            } else {
                //If the users input was incorrect
                view.displayMessage("Rating needs to be 0-5");
            }
        }
    }

    /**
     * Puts the selected sound clips on the queue and lets
     * the sound clip player thread play them. Essentially, when
     * this method is called, the selected sound clips in the
     * SoundClipTable are played.
     */
    public void playSoundClips() {
        List<SoundClip> l = view.getSelectedSoundClips();
        queue.enqueue(l);
        for (int i = 0; i < l.size(); i++) {
            view.displayMessage("Playing " + l.get(i));
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }

    }
}
