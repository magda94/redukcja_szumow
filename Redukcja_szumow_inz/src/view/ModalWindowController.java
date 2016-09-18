package view;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ModalWindowController {
	
	@FXML
	private ImageView filterImage;
	
	@FXML
	private Button saveImage;
	@FXML
	private Button closeWindow;

	private MainApp app;
	
	public void setApp(MainApp app){
		this.app=app;
	}
}
