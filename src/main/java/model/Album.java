package model;
import java.util.*;

/*
 * The 'Album' class represents a single album in the music organizer application. Each
 * album can contain a number of child albums or sound clips and have one parent album, creating a
 * hierarchical structure for organizing music files.
 * 
 * Each instance of the 'Album' class has a name, a parent album (except for the root album) and a list
 * of sub albums and sound clips. The class provides methods for managing the album.
 * 
 * The unique feature of this 'Album' class is that when a sound clip is added to a sub-album, the clip is 
 * automatically added to all of the parent albums as well. Similarly, when a clip is removed from a parent
 * album, the class ensures that it is removed from all sub-albums as well. This ensures that the hierarchy
 * of albums and sound clips is maintained properly
 */
public class Album{

		//The title of the album
		private String title;
		
		//The sound clips that are stored in the album
		private List <SoundClip> soundClips;
		
		//The albums parent album, null if root album
		private Album ParentAlbum;
		
		//The albums sub albums are stored in this list
		private List<Album> SubAlbums;

		
	/* This is the constructor for the album. When creating a new album it always need a parent album and title.
	 * The constructor also creates the array list sound clips where the sound clips in the album are stored, and the array
	 * sub albums where the albums sub albums are stored.
	 */
	public Album(Album parentalbum, String title){
		
		assert title != null; //a album needs a name
		assert !title.isBlank();
		assert parentalbum != null || title.equals("All Sound Clips");
		
		this.title = title;
		this.ParentAlbum = parentalbum;
		this.soundClips = new ArrayList<SoundClip>();
		this.SubAlbums = new ArrayList<Album>();
	}

	/*
	 * Getters and setters
	 */
	public String getTitle() {
		return title;
	}
	public String toString() {
		return title;
	}
	
	public Album getParentAlbum() {
		return ParentAlbum;
	}
	
	public List<Album> getSubAlbums() {
		return SubAlbums;
	}
	
	public List<SoundClip> getSoundClips(){
			return soundClips;
	}
	public void removeAllSoundClips() {
		soundClips.clear();
	}
	
	// Sets the current albums parent album
	public void setParentAlbum(Album parentalbum) {	
		this.ParentAlbum = parentalbum;
	}
	
	// Add a sub album to the current album
	public void addSubAlbum(Album subAlbum) {
		//precondition
		assert subAlbum != null;
		SubAlbums.add(subAlbum);
		
		//postcondition
		assert SubAlbums.contains(subAlbum);
		assert subAlbum.getParentAlbum() == this;
		
	}
	//Removes a sub album from the album.
	public void removeSubAlbum(Album subAlbum) {
		//precondition
		assert subAlbum != null;
		SubAlbums.remove(subAlbum);	
		//postcondition
		assert !SubAlbums.contains(subAlbum);
		
	}

	/*
	 * A method that goes through all the albums parent albums
	 * and adds the clip to them.
	 */
	public void addClipToParentAlbums(SoundClip clip) {
		//precondition 
		assert clip != null;
		
		Album parentAlbum = ParentAlbum;
        while (parentAlbum != null) {
        		if(!parentAlbum.containsClip(clip)) {
        			parentAlbum.addClip(clip);
            		
            		//postcondition
            		assert ParentAlbum.getSoundClips().contains(clip);
            		parentAlbum = parentAlbum.getParentAlbum();
        		}
        		else {
        			parentAlbum = parentAlbum.getParentAlbum();
        		}
        }
	}
	//New method for assignment 3, to be able to undo adding soundclips
	//It is a method to remove a clip from all it's parentalbums (not rootAlbum)
	public void removeClipFromParentAlbums(SoundClip clip) {
		Album parentAlbum = ParentAlbum;
		
		assert clip != null;
		
		while(parentAlbum != null && !parentAlbum.getTitle().equals("All Sound Clips")) {
			if(parentAlbum.containsClip(clip)) {
				parentAlbum.removeClip(clip);
				parentAlbum = parentAlbum.getParentAlbum();
			}
			else {
				parentAlbum = parentAlbum.getParentAlbum();
			}
		}
	}
	
	/*
	 * This method walks through the entire sub-album hierarchy of the current 
	 * album, removing the clip from all of the sub-albums that contains it.
	 * The method takes the clip to be removed as a parameter. It loops through
	 * all of the album's sub-albums using a for loop. For each sub-album, the 
	 * method calls the 'removeClip' method to remove the clip from the sub-album's
	 * 'soundClips' list. The method then recursively calls the 'removeClipFromSubAlbums
	 * on the sub-album to remove the clip from all of its sub-albums as well.
	 */
	public void removeClipFromSubAlbums(SoundClip clip) {	
		//precondition
		assert clip != null;
		
			for(Album subAlbum : SubAlbums) {
				subAlbum.removeClip(clip);
				//postcondition
				assert !subAlbum.getSoundClips().contains(clip);
				subAlbum.removeClipFromSubAlbums(clip);
			}
		}
	//New method for assignment 3
	public void addClipToSubAlbums(SoundClip clip) {
		assert clip != null;
		
		for(Album subAlbum : SubAlbums) {
			subAlbum.addClip(clip);
			
			subAlbum.addClipToSubAlbums(clip);
			
		}
	}
	
	//Adds a clip to the album
	public void addClip(SoundClip clip) {
		//precondition
		assert clip != null;
		assert !soundClips.contains(clip);
		
		if(!soundClips.contains(clip)) {
			soundClips.add(clip);
			
			//postcondition
			assert soundClips.contains(clip);
		}
		
		
	}
	
	//Removes the sound clip from the current album.
	public void removeClip(SoundClip clip) {
		//precondition
		assert clip != null;
		
		soundClips.remove(clip);
		
		//postcondition 
		assert !soundClips.contains(clip);
		
	}
	
	//Checks if the album contains another album.
	public boolean containsAlbum(Album album) {
		//Precondition
		assert album != null;
		
		if(album == this) {
			return true;
		}
		for(Album subAlbum : SubAlbums) {
			if(subAlbum.containsAlbum(album)){
				return true;
			}
		}
		return false;
	}
	
	//Checks if the album contains a clip
	public boolean containsClip(SoundClip clip) {
		//Preconditions
		assert clip != null;
		return soundClips.contains(clip);
	    }
	
	//Represents the title of the albums and names of the songs in the album.
	public String toString2() {
		String result = "Album:" + title + "\n";
		result += "Sound clips: \n";
		for (SoundClip clip : soundClips) {
			result += clip.getName() + "\n";
		}
		return result;
	}


}
