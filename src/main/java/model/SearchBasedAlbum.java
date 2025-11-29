package model;

import java.util.*;
import java.util.Observer;

import controller.MusicOrganizerController;
import controller.Subject;

/*
 * This is a class that represents a search based album. The difference between the ordinary albums and
 * search based albums are that the user can not directly add or remove sound clips from the album. Search based albums
 * updates instead automatically each time a user updates a sound clip in a album, for example flag or rate a sound clip.
 * The search based album can not have sub albums.
 */
public abstract class SearchBasedAlbum extends Album implements Observer {
	private List <SoundClip> SearchResult; 
	private String title;
	private List <SoundClip> UpdatedSoundClips;
	private Subject MusicOrganizerController;
	private MusicOrganizerController controller;
	
	public SearchBasedAlbum(String title,Album parentalbum, Subject MusicOrganizerController, MusicOrganizerController controller) {
		super(parentalbum, title);
		this.MusicOrganizerController = MusicOrganizerController;
		//Putting this class as an observer to the controller class
		//this.MusicOrganizerController.registerObserver(this);
		
		this.controller = controller;
		this.title = title;
		this.SearchResult = new ArrayList<SoundClip>();

	}
	
	//True if the search criteria is met and false otherwise.
	public abstract boolean searchCriteria(SoundClip clip);
	
	//Gets the updated sound clips from the controller
	public void getUpdatedSoundClips(){
		UpdatedSoundClips = controller.getRootAlbum().getSoundClips();
	}
	//Removes the last search
	public void deletePreviousSearch() {
		SearchResult.clear();
	}
	//Executes a new search sorting according to the search criteria that
	//varies depending on what type of search based album.
	public void newSearch(List <SoundClip> allSoundClips) {
		for (SoundClip soundClip : allSoundClips) {
			//adds the sound clip to the search result if the search criteria is true
			if(searchCriteria(soundClip)) {
				SearchResult.add(soundClip);
			}
	}
	}
	//Template method, which is being called when a soundclip gets modified in the controller class
	public void update() {
		getUpdatedSoundClips();
		
		deletePreviousSearch();
		
		newSearch(UpdatedSoundClips);
		
	}
	public List <SoundClip> getSoundClips() {
		return SearchResult;
	}
	public void addSubAlbum(Album subAlbum) {
		throw new UnsupportedOperationException("addSubAlbum is not implemented");
	}
	public void removeSubAlbum(Album subAlbum) {
		throw new UnsupportedOperationException("removeSubAlbum is not implemented");
	}
	public void addClipToParentAlbums(SoundClip clip) {
		throw new UnsupportedOperationException("addClipToParentAlbums is not implemented");
	}
	public void removeClipFromParentAlbums(SoundClip clip){
		throw new UnsupportedOperationException("removeClipFromParentAlbums is not implemented");
	}
	public void removeClipFromSubAlbums(SoundClip clip) {	
		throw new UnsupportedOperationException("removeClipFromSubAlbums is not implemented");
	}
	public void addClipToSubAlbums(SoundClip clip) {
		throw new UnsupportedOperationException("addClipToSubAlbums is not implemented");
	}

	public String getTitle() {
		return title;
	}
	
}