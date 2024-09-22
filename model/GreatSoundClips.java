package model;

import java.util.Observable;

import controller.MusicOrganizerController;
import controller.Subject;

public class GreatSoundClips extends SearchBasedAlbum{

	public GreatSoundClips(Album parentalbum, String title, Subject MusicOrganizerController, MusicOrganizerController controller) {
		super(title,parentalbum, MusicOrganizerController, controller);
		
	}

	@Override
	public boolean searchCriteria(SoundClip clip) {
		if(clip.getRating() == 4 || clip.getRating() == 5) {
			return true;
		}
		else {
			return false;	
		}
	
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

}
