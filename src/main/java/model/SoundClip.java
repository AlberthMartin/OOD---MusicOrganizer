package model;

import java.io.File;

/**
 * SoundClip is a class representing a digital
 * sound clip file on disk.
 */
public class SoundClip {

	private File file;
	private String name;
	private boolean flagged;
	private int rating;
	
	/**
	 * Make a SoundClip from a file.
	 * Requires file != null and name.
	 */

	public SoundClip(File file) {
		assert file != null;	
		this.file=file;	
		this.flagged = false;
		//start value to -1 to indicate that the sound clip has not been rated
		this.rating = -1;
	}

	/**
	 * @return the file containing this sound clip.
	 */
	public File getFile() {
		return this.file;
	}
	
	public String toString(){
		if(flagged == true && rating != -1) {
			return file.getName() + "           F      Rating:   " + rating;
		}
		else if(rating != -1) {
			return file.getName() + "           Rating:   " + rating;
		}
		else if (flagged == true) {
			return file.getName() + "           F";
		}
		else {
			return file.getName();
		}
	}

	public boolean getFlagged() {
		return flagged;
	}
	
	public void setFlagged(boolean b) {
		this.flagged = b;
	}
	
	public void setRating(int rating) {
		if(rating>=0 && rating<=5) {
			this.rating = rating;
		}
		else {
			System.out.print("rating can only be 0-5");
		}
	}
	
	public int getRating() {
		return this.rating;
	}
	
	@Override
	public boolean equals(Object obj) {
		return 
			obj instanceof SoundClip
			&& ((SoundClip)obj).file.equals(file);
	}
	
	
	@Override
	public int hashCode() {
		return file.hashCode();
	}
	
	public void setName(String n) {
		this.name=n;
	}
	
	public String getName() {
			return name;
	}

}

