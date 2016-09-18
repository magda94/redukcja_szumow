package view;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import address.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	private Button filtrImage;
	@FXML
	private Button saveImage;
	@FXML 
	private Button resetImage;
	
	@FXML
	private ImageView imageView;
	
	private Image image;
	
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
		ChangeListener changeSize3x3 = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            matrix5x5.setSelected(false);
		        	matrix7x7.setSelected(false);
		}};
		
		ChangeListener changeSize5x5 = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            matrix3x3.setSelected(false);
		        	matrix7x7.setSelected(false);
		}};
		
		ChangeListener changeSize7x7 = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            matrix3x3.setSelected(false);
		        	matrix5x5.setSelected(false);
		}};
		matrix3x3.selectedProperty().addListener(changeSize3x3);
		matrix5x5.selectedProperty().addListener(changeSize5x5);
		matrix7x7.selectedProperty().addListener(changeSize7x7);
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
	
	/*
	 * Load image from file. Allow extension *.jpg and *.png
	 */
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
				imageView.setImage(tempImage);
				filtrImage.setDisable(false);
				saveImage.setDisable(true);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	/*
	 * Show filtered image
	 */
	@FXML
	private void showFilterImage(){
		//dla filtrów potrzebuj¹cych rozmiar macierzy
		if(!matrix3x3.isDisable()){
			if(matrix3x3.isSelected() || matrix5x5.isSelected() || matrix7x7.isSelected()){
				saveImage.setDisable(false);
				resetImage.setDisable(false);
			}else{
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("B³¹d");
				alert.setHeaderText("Nie zaznaczono rozmiaru macierzy!");
				alert.setContentText("Aby dokonaæ filtracji obrazu tym filtrem nale¿y wybraæ rozmiar macierzy.");
				alert.showAndWait();
			}
		}
	}
	
	@FXML
	private void saveImageasFile(){
		FileChooser fileChooser=new FileChooser();
		
		FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("Image files (*.jpg, *.png)", "*.jpg","*.png");
		fileChooser.getExtensionFilters().add(filter);
		
		File file=fileChooser.showSaveDialog(null);
		image=imageView.getImage();
		if(file!=null){
			BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		    try {
		      ImageIO.write(bImage, "png", file);
		    } catch (Exception e) {
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
