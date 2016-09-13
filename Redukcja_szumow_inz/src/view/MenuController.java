package view;

import java.io.File;

import address.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MenuController {
	
	@FXML
	private ChoiceBox chooseMethod;
	
	@FXML
	private CheckBox matrix3x3;
	@FXML
	private CheckBox matrix5x5;
	@FXML
	private CheckBox matrix7x7;
	
	@FXML
	private ImageView image;
	
	private FileChooser fileChooser;
	
	final String[] listMethod={"Filtr medianowy","Filtr Gaussa","Inne"};
	
	private MainApp mainApp;
	private ObservableList<String> list=FXCollections.observableArrayList(listMethod);
	
	/*
	 * Set settings in main menu.
	 */
	@FXML
	private void initialize(){
		chooseMethod.setValue(listMethod[0]);
		chooseMethod.setItems(list);
		chooseMethod.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    choose(newValue.toString());
		});
	}
	
	/*
	 * Choose method to filtr image.
	 */
	private void choose(String value){
		if (value==listMethod[0] || value==listMethod[1]){
			matrix3x3.setDisable(false);
			matrix5x5.setDisable(false);
			matrix7x7.setDisable(false);
		}else{
			matrix3x3.setDisable(true);
			matrix5x5.setDisable(true);
			matrix7x7.setDisable(true);
		}
	}
	
	@FXML
	private void loadImageFromFile(){
		fileChooser=new FileChooser();
		FileChooser.ExtensionFilter filter= new FileChooser.ExtensionFilter("Image files (*.jpg, *.png)", "*.jpg","*.png");
		fileChooser.getExtensionFilters().add(filter);
		File selectedFile=fileChooser.showOpenDialog(null);
		if(selectedFile!=null){
			try{
				@SuppressWarnings("deprecation")
				Image tempImage=new Image(selectedFile.toURL().toString());
				image.setImage(tempImage);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
