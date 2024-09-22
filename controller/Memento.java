package controller;

import java.util.List;
import model.Album;
import model.SoundClip;


public class Memento {
	
	private Album album;
	private Album parentAlbum;
	private List<SoundClip> clips;
	private String type;

	
	public Memento(String type, Album album, Album parentAlbum, List<SoundClip> clips) {
		this.parentAlbum = parentAlbum;
		this.setAlbum(album);
		this.type = type;
		this.setClips(clips);
		
		
	}
	
	public Album getParentAlbum() {
		return parentAlbum;
	}
	public void setParentAlbum(Album parentAlbum) {
		this.parentAlbum = parentAlbum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<SoundClip> getClips() {
		return clips;
	}

	public void setClips(List<SoundClip> clips) {
		this.clips = clips;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

}
