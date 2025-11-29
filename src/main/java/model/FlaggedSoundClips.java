package model;

import java.util.Observable;

import controller.MusicOrganizerController;
import controller.Subject;

public class FlaggedSoundClips extends SearchBasedAlbum{

	public FlaggedSoundClips(Album  parentalbum, String title, Subject MusicOrganizerController, MusicOrganizerController controller) {
		super(title, parentalbum, MusicOrganizerController, controller);
	}

	@Override
	public boolean searchCriteria(SoundClip clip) {
		if(clip.getFlagged()) {
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
