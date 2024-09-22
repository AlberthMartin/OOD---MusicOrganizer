package view;

import controller.MusicOrganizerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

public class ButtonPaneVBox extends VBox{

	private MusicOrganizerController controller;
	private MusicOrganizerWindow view;
	private Button flagClipsButton;
	private Button removeFlagFromClipsButton;
	private Button rateClipsButton;
	private Button redoButton;
	private Button undoButton;
	
	public static final int BUTTON_MIN_WIDTH = 150;
	
	public ButtonPaneVBox(MusicOrganizerController contr, MusicOrganizerWindow view) {
		super();
		this.controller = contr;
		this.view = view;
		
		flagClipsButton = createNewFlagClipsButton();
		this.getChildren().add(flagClipsButton);
		
		removeFlagFromClipsButton = createNewRemoveFlagFromClipsButton();
		this.getChildren().add(removeFlagFromClipsButton);
		
		rateClipsButton = createNewRateClipsButton();
		this.getChildren().add(rateClipsButton);
		
		undoButton = createNewUndoButton();
		this.getChildren().add(undoButton);
		//invisible as default
		undoButton.setVisible(false);
		
		redoButton = createNewRedoButton();
		this.getChildren().add(redoButton);
		//invisible as default
		redoButton.setVisible(false);
	}
	
	public Button getRedoButton() {
		return redoButton;}
	public Button getUndoButton() {
		return undoButton;}
	public void setVisibilityRedoButton(boolean Boolean) {
		redoButton.setVisible(Boolean);
	}
	public void setVisibilityUndoButton(boolean Boolean) {
		undoButton.setVisible(Boolean);
	}
	
	private Button createNewFlagClipsButton() {
		Button button = new Button("Flag clips");
		button.setTooltip(new Tooltip("Flagg selected clip"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.flagSelectedClips(view.getSelectedSoundClips());
			}

		});
		return button;
	}
	
	private Button createNewRemoveFlagFromClipsButton() {
		Button button = new Button("Remove Flag");
		button.setTooltip(new Tooltip("Remove flag from selected clips"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.removeFlagFromSelectedClips(view.getSelectedSoundClips());
			}
			
		});
		return button;
	}
	
	
	private Button createNewRateClipsButton() {
		Button button = new Button("Rate clips");
		button.setTooltip(new Tooltip("Rate selected clips"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.rateSoundClips(view.getSelectedSoundClips());
			}
			
			
		});
		return button;
	}
	
	private Button createNewUndoButton() {
		Button button = new Button("Undo");
		button.setTooltip(new Tooltip("Undo the last action"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.Undo();
			}
			
		});
		return button;
	}
	
	private Button createNewRedoButton() {
		Button button = new Button("Redo");
		button.setTooltip(new Tooltip("Redo and undon action"));
		button.setMinWidth(BUTTON_MIN_WIDTH);
		
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				controller.Redo();
			}
			
			
		});
		return button;
	}
	
}
