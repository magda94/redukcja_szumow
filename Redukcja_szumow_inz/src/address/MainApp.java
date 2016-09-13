package address;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MenuController;


public class MainApp extends Application{
	private Stage primaryStage;
	private BorderPane rootLayout;
	

	@Override
	public void start(Stage primaryStage){
		this.primaryStage=primaryStage;
		this.primaryStage.setTitle("Redukcja szum�w");
		
		initRootLayout();
		showMainMenu();
	}
	
	/*
	 * Initializes the root layout.
	 */
	public void initRootLayout(){
		try{
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/RootApplication.fxml"));
			rootLayout=(BorderPane)loader.load();
			
			Scene scene=new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Show main menu of application.
	 */
	public void showMainMenu(){
		try{
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("../view/MainMenu.fxml"));
			AnchorPane menu=(AnchorPane)loader.load();
			
			rootLayout.setCenter(menu);
			MenuController controller=loader.getController();
			controller.setMainApp(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Returns the main stage.
	 */
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	public static void main(String[]args){
		launch(args);
	}
}
