package view;


import controller.MusicOrganizerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class ButtonPaneHBox extends HBox {

	private MusicOrganizerController controller;
	private MusicOrganizerWindow view;
	private Button newAlbumButton;
	private Button deleteAlbumButton;
	private Button addSoundClipsButton;
	private Button removeSoundClipsButton;	
	private Button playButton;
	
	public static final int BUTTON_MIN_WIDTH = 150;

	
	/*
	 * The constructor for the HBox(Horizontal box), a box that organizes the buttons in a horisontal line. Adds all the buttons to
	 * the HBox
	 * 
	 */
	public ButtonPaneHBox(MusicOrganizerController contr, MusicOrganizerWindow view) {
		super();
		this.controller = contr;
		this.view = view;
		
		newAlbumButton = createNewAlbumButton();
		this.getChildren().add(newAlbumButton);

		deleteAlbumButton = createDeleteAlbumButton();
		this.getChildren().add(deleteAlbumButton);
		
		addSoundClipsButton = createAddSoundClipsButton();
		this.getChildren().add(addSoundClipsButton);
		
		removeSoundClipsButton = createRemoveSoundClipsButton();
		this.getChildren().add(removeSoundClipsButton);
		
		playButton = createPlaySoundClipsButton();
		this.getChildren().add(playButton);

	}
	public Button getNewAlbumButton() {
		return newAlbumButton;
	}
	public Button getDeleteAlbumButton() {
		return deleteAlbumButton;
	}
	public Button getAddSoundClipsButton() {
		return addSoundClipsButton;
	}
	public Button getRemoveSoundClipsButton() {
		return removeSoundClipsButton;
	}
	public Button getPlayButton() {
		return playButton;
	}
	
	
	/*
	 * Each method below creates a single button. The buttons are also linked
	 * with event handlers, so that they react to the user clicking on the buttons
	 * in the user interface
	 */

	private Button createNewAlbumButton() {
		Button button = new Button("New Album");
		button.setTooltip(new Tooltip("Create new sub-album to selected album"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(view.getSelectedAlbum() != null) {
					controller.addNewAlbum(view.getSelectedAlbum(), view.promptForAlbumName());
				}
				else {
					view.displayMessage("Before creating an album you need to select the parent album");
				}
				
			}
			
		});
		return button;
	}
	
	private Button createDeleteAlbumButton() {
		Button button = new Button("Remove Album");
		button.setTooltip(new Tooltip("Remove selected album"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(view.getSelectedAlbum() != null) {
					controller.deleteAlbum(view.getSelectedAlbum());
				}
				else {
					view.displayMessage("Select the album you want to delete");
				}
				
			}
			
		});
		return button;
	}
	
	private Button createAddSoundClipsButton() {
		Button button = new Button("Add Sound Clips");
		button.setTooltip(new Tooltip("Add selected sound clips to selected album"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(view.getSelectedAlbum() != null && view.getSelectedSoundClips() != null) {
					controller.addSoundClips(view.getSelectedSoundClips(), view.getSelectedAlbum());
				}
				else {
					view.displayMessage("Make sure you have de album you want to add the sound clips to selected");
				}
			}
			
		});
		return button;
	}
	
	private Button createRemoveSoundClipsButton() {
		Button button = new Button("Remove Sound Clips");
		button.setTooltip(new Tooltip("Remove selected sound clips from selected album"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(view.getSelectedAlbum() != null && view.getSelectedSoundClips() != null) {
					controller.removeSoundClips(view.getSelectedSoundClips(), view.getSelectedAlbum());	
				}
				else {
					view.displayMessage("Something went wrong");
				}
			
			}
			
		});
		return button;
	}
	
	private Button createPlaySoundClipsButton() {
		Button button = new Button("Play Sound Clips");
		button.setTooltip(new Tooltip("Play selected sound clips"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {				
				controller.playSoundClips();
			}
			
		});
		return button;
	}

	
}
