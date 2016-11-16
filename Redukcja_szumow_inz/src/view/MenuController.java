package view;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;

import address.AdaptiveBilateralFilter;
import address.AdaptiveMedianFilter;
import address.AdaptiveThresholding;
import address.BilateralFilter;
import address.BrightnessChanger;
import address.CannyFilter;
import address.ContrastChanger;
import address.Filter;
import address.FourierTransform;
import address.GaussianFilter;
import address.LaplacianFilter;
import address.MainApp;
import address.MedianFilter;
import address.SobelFilter;
import address.Thresholding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MenuController {
	
	@FXML
	private ChoiceBox<String> chooseMethod;
	
	@FXML
	private CheckBox matrix3x3;
	@FXML
	private CheckBox matrix5x5;
	@FXML
	private CheckBox matrix7x7;
	@FXML
	private CheckBox derivativeX0;
	@FXML
	private CheckBox derivativeX1;
	@FXML
	private CheckBox derivativeX2;
	@FXML
	private CheckBox derivativeY0;
	@FXML
	private CheckBox derivativeY1;
	@FXML
	private CheckBox derivativeY2;
	@FXML
	private CheckBox adaptiveFilter;
	
	@FXML
	private Button filtrImage;
	@FXML
	private Button saveImage;
	@FXML 
	private Button resetImage;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Slider standardDeviationX;
	@FXML
	private Slider standardDeviationY;
	@FXML
	private Slider scaleSlider;
	@FXML
	private Slider deltaSlider;
	@FXML
	private Slider lowerThreshold;
	@FXML
	private Slider upperThreshold;
	@FXML
	private Slider sigmaColor;
	@FXML
	private Slider sigmaSpace;
	@FXML
	private Slider brightness;
	@FXML
	private Slider thresholding;
	@FXML
	private Slider constC;
	@FXML
	private Slider blockSize;
	
	@FXML
	private Label deviationXLabel;
	@FXML
	private Label deviationYLabel;
	@FXML
	private Label scaleLabel;
	@FXML
	private Label deltaLabel;
	@FXML
	private Label lowerThLabel;
	@FXML
	private Label upperThLabel;
	@FXML
	private Label sigmaColorLabel;
	@FXML
	private Label sigmaSpaceLabel;
	@FXML
	private Label brightnessLabel;
	@FXML
	private Label thresholdingLabel;
	@FXML
	private Label constCLabel;
	@FXML
	private Label blockSizeLabel;
	
	private Image image;
	private Image originalImage;
	
	private FileChooser fileChooser;
	
	final String[] listMethod={"Filtr medianowy","Filtr Gaussa","Laplasjan","Filtr Sobela","Filtr Canny'ego","Bilateral","Transformata Fouriera","Jasnoœæ","Kontrast","Progowanie","Progowanie adaptacyjne","Inne"};
	
	private MainApp mainApp;
	private ObservableList<String> list=FXCollections.observableArrayList(listMethod);
	
	private Mat imageMatrix;
	
	private String path;

	/*
	 * Set settings in main menu.
	 */
	@FXML
	private void initialize(){
		deviationXLabel.setText("0");
		deviationYLabel.setText("0");
		scaleLabel.setText("0");
		deltaLabel.setText("0");
		lowerThLabel.setText("0");
		upperThLabel.setText("0");
		sigmaColorLabel.setText("0");
		sigmaSpaceLabel.setText("0");
		brightnessLabel.setText("0%");
		thresholdingLabel.setText("0");
		constCLabel.setText("0");
		blockSizeLabel.setText("0");
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
		
		ChangeListener standardDeviationXChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				deviationXLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener standardDeviationYChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				deviationYLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener scaleChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				scaleLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener deltaChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				deltaLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener derX0Changer = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            derivativeX1.setSelected(false);
		        	derivativeX2.setSelected(false);
		}};
		
		ChangeListener derX1Changer = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            derivativeX0.setSelected(false);
		        	derivativeX2.setSelected(false);
		}};
		
		ChangeListener derX2Changer = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            derivativeX0.setSelected(false);
		        	derivativeX1.setSelected(false);
		}};
		
		ChangeListener derY0Changer = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            derivativeY1.setSelected(false);
		        	derivativeY2.setSelected(false);
		}};
		
		ChangeListener derY1Changer = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            derivativeY0.setSelected(false);
		        	derivativeY2.setSelected(false);
		}};
		
		ChangeListener derY2Changer = new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		        if (new_val)
		            derivativeY0.setSelected(false);
		        	derivativeY1.setSelected(false);
		}};
		
		ChangeListener lowerChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				lowerThLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener upperChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				upperThLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener colorChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				sigmaColorLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener spaceChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				sigmaSpaceLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener brightnessChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				brightnessLabel.setText(String.format("%.2f", new_val)+"%");
			}
		};
		
		ChangeListener thresholdingChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				thresholdingLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener constCChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				constCLabel.setText(String.format("%.2f", new_val));
			}
		};
		
		ChangeListener blockSizeChanger=new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				blockSizeLabel.setText(String.format("%d", new_val.intValue()));
			}
		};
		
		standardDeviationX.valueProperty().addListener(standardDeviationXChanger);
		standardDeviationY.valueProperty().addListener(standardDeviationYChanger);
		scaleSlider.valueProperty().addListener(scaleChanger);
		deltaSlider.valueProperty().addListener(deltaChanger);
		matrix3x3.selectedProperty().addListener(changeSize3x3);
		matrix5x5.selectedProperty().addListener(changeSize5x5);
		matrix7x7.selectedProperty().addListener(changeSize7x7);
		derivativeX0.selectedProperty().addListener(derX0Changer);
		derivativeX1.selectedProperty().addListener(derX1Changer);
		derivativeX2.selectedProperty().addListener(derX2Changer);
		derivativeY0.selectedProperty().addListener(derY0Changer);
		derivativeY1.selectedProperty().addListener(derY1Changer);
		derivativeY2.selectedProperty().addListener(derY2Changer);
		lowerThreshold.valueProperty().addListener(lowerChanger);
		upperThreshold.valueProperty().addListener(upperChanger);
		sigmaColor.valueProperty().addListener(colorChanger);
		sigmaSpace.valueProperty().addListener(spaceChanger);
		brightness.valueProperty().addListener(brightnessChanger);
		thresholding.valueProperty().addListener(thresholdingChanger);
		constC.valueProperty().addListener(constCChanger);
		blockSize.valueProperty().addListener(blockSizeChanger);
	}
	
	/*
	 * Choose method to filtr image.
	 */
	private void choose(String value){
		if (value==listMethod[0] || value==listMethod[1] || value==listMethod[2] || value==listMethod[3] 
				|| value==listMethod[4] || value==listMethod[5] || value==listMethod[6] || value==listMethod[7]
				|| value==listMethod[8] || value==listMethod[9] || value==listMethod[10]){
			setEnableMatrix();
			//Median Filter
			if(value==listMethod[0]){
				setEnableAdaptive();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Gaussian Filter
			if(value==listMethod[1]){
				setEnableDeviation();
				setDisableAdaptive();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Laplacian Filter
			if(value==listMethod[2]){
				setEnableScaleAndDelta();
				setDisableAdaptive();
				setDisableDeviation();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Sobel Filter
			if(value==listMethod[3]){
				setEnableScaleAndDelta();
				setEnableDerivative();
				setDisableAdaptive();
				setDisableDeviation();
				setDisableThresholds();
				setDisableSigmas();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Canny Filter
			if(value==listMethod[4]){
				setEnableThresholds();
				setDisableAdaptive();
				setDisableSigmas();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Bilateral Filter
			if(value==listMethod[5]){
				setEnableSigmas();
				setEnableAdaptive();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Fourier Transform
			if(value==listMethod[6]){
				setDisableMatrix();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableAdaptive();
				setDisableBrightness();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Brightness
			if(value==listMethod[7]){
				setEnableBrightness();
				setDisableMatrix();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableAdaptive();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Contrast
			if(value==listMethod[8]){
				setEnableBrightness();
				setDisableMatrix();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableAdaptive();
				setDisableThresholding();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Thresholding
			if(value==listMethod[9]){
				setEnableThresholding();
				setDisableAdaptive();
				setDisableMatrix();
				setDisableBrightness();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
				setDisableConstC();
				setDisableBlockSize();
			}
			//Adaptive Thresholding
			if(value==listMethod[10]){
				setEnableConstC();
				setEnableBlockSize();
				setDisableAdaptive();
				setDisableMatrix();
				setDisableBrightness();
				setDisableDeviation();
				setDisableScaleAndDelta();
				setDisableDerivative();
				setDisableThresholds();
				setDisableSigmas();
			}
		//Others
		}else{
			setDisableMatrix();
			setDisableDeviation();
			setDisableScaleAndDelta();
			setDisableDerivative();
			setDisableThresholds();
			setDisableSigmas();
			setDisableAdaptive();
			setDisableBrightness();
			setDisableThresholding();
			setDisableConstC();
			setDisableBlockSize();
		}
	}
	
	/*
	 * Set disable slider for const C.
	 */
	private void setDisableConstC(){
		constC.setDisable(true);
	}
	
	/*
	 * Set enable slider for const C.
	 */
	private void setEnableConstC(){
		constC.setDisable(false);
	}
	
	/*
	 * Set disable slider for block size.
	 */
	private void setDisableBlockSize(){
		blockSize.setDisable(true);
	}
	
	/*
	 * Set enable slider for block size.
	 */
	private void setEnableBlockSize(){
		blockSize.setDisable(false);
	}
	
	/*
	 * Set disable slider for thresholding.
	 */
	private void setDisableThresholding(){
		thresholding.setDisable(true);
	}
	
	/*
	 * Set enable slider for thresholding.
	 */
	private void setEnableThresholding(){
		thresholding.setDisable(false);
	}
	
	/*
	 * Set disable slider for brightness.
	 */
	private void setDisableBrightness(){
		brightness.setDisable(true);
	}
	
	/*
	 * Set enable slider for brightness.
	 */
	private void setEnableBrightness(){
		brightness.setDisable(false);
	}
	
	/*
	 * Set disable checkbox for adaptive filter.
	 */
	private void setDisableAdaptive(){
		adaptiveFilter.setDisable(true);
	}
	
	/*
	 * Set enable checkbox for adaptive filter.
	 */
	private void setEnableAdaptive(){
		adaptiveFilter.setDisable(false);
	}
	
	/*
	 * Set disable sliders for color and space sigma.
	 */
	private void setDisableSigmas(){
		sigmaColor.setDisable(true);
		sigmaSpace.setDisable(true);
	}
	
	/*
	 * Set enable sliders for color and space sigma.
	 */
	private void setEnableSigmas(){
		sigmaColor.setDisable(false);
		sigmaSpace.setDisable(false);
	}
	
	/*
	 * Set disable sliders for thresholds.
	 */
	private void setDisableThresholds(){
		lowerThreshold.setDisable(true);
		upperThreshold.setDisable(true);
	}
	
	/*
	 * Set enable sliders for thresholds.
	 */
	private void setEnableThresholds(){
		lowerThreshold.setDisable(false);
		upperThreshold.setDisable(false);
	}
	
	/*
	 * Set disable checkboxes for derivative X and Y.
	 */
	private void setDisableDerivative(){
		derivativeX0.setDisable(true);
		derivativeX1.setDisable(true);
		derivativeX2.setDisable(true);
		
		derivativeY0.setDisable(true);
		derivativeY1.setDisable(true);
		derivativeY2.setDisable(true);
	}
	
	/*
	 * Set enable checkboxes for derivative for X and Y.
	 */
	private void setEnableDerivative(){
		derivativeX0.setDisable(false);
		derivativeX1.setDisable(false);
		derivativeX2.setDisable(false);
		
		derivativeY0.setDisable(false);
		derivativeY1.setDisable(false);
		derivativeY2.setDisable(false);
	}
	
	/*
	 * Set disable sliders for scale and delta.
	 */
	private void setDisableScaleAndDelta(){
		scaleSlider.setDisable(true);
		deltaSlider.setDisable(true);
	}
	
	/*
	 * Set enable sliders for scale and delta.
	 */
	private void setEnableScaleAndDelta(){
		scaleSlider.setDisable(false);
		deltaSlider.setDisable(false);
	}
	
	/*
	 * Set disable sliders for standard deviation.
	 */
	private void setDisableDeviation(){
		standardDeviationX.setDisable(true);
		standardDeviationY.setDisable(true);
	}
	
	/*
	 * Set enable sliders for standard deviation.
	 */
	private void setEnableDeviation(){
		standardDeviationX.setDisable(false);
		standardDeviationY.setDisable(false);
	}
	
	/*
	 * Set disable checkboxes for matrix's size.
	 */
	private void setDisableMatrix() {
		matrix3x3.setDisable(true);
		matrix5x5.setDisable(true);
		matrix7x7.setDisable(true);
	}
	
	/*
	 * Set enable checkboxes for matrix's size.
	 */
	private void setEnableMatrix(){
		matrix3x3.setDisable(false);
		matrix5x5.setDisable(false);
		matrix7x7.setDisable(false);
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
				//@SuppressWarnings("deprecation")
				this.path=selectedFile.getAbsolutePath();
				this.image=new Image(selectedFile.toURL().toString());
				this.originalImage=image;
				this.imageMatrix=null;
				imageView.setImage(image);
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
		if(!matrix3x3.isDisable() ){
			if(matrix3x3.isSelected() || matrix5x5.isSelected() || matrix7x7.isSelected()){
				//non adaptive medianFilter 
				if(chooseMethod.getSelectionModel().getSelectedIndex()==0 && !isAdaptive()){
					int size=getSize();
					MedianFilter medianFilter = null;
					if(imageMatrix==null){
						try {
							medianFilter=new MedianFilter(path,size);
						} catch (Exception e) {
							showLoadAlert();
						}
					}else
						medianFilter=new MedianFilter(imageMatrix,size);
					if(medianFilter!=null){
						filtrImage(medianFilter);
					}
				}
				//adaptive median filter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==0 && isAdaptive()){
					int size=getSize();
					System.out.println("JESTEM");
					AdaptiveMedianFilter adaptiveMedianFilter = null;
					if(imageMatrix==null){
						try{
							adaptiveMedianFilter=new AdaptiveMedianFilter(path,size);
						}catch(Exception e){
							showLoadAlert();
						}
					}else
						adaptiveMedianFilter=new AdaptiveMedianFilter(imageMatrix,size);
					if(adaptiveMedianFilter!=null){
						filtrImage(adaptiveMedianFilter);
						System.out.println("SKONCZY£EM!");
					}
				}
				//GaussianFilter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==1){
					int size=getSize();
					double devX=getDeviationX();
					double devY=getDeviationY();
					GaussianFilter gaussianFilter = null;
					if(imageMatrix==null){
						try {
							gaussianFilter=new GaussianFilter(path,size,devX,devY);
						} catch (Exception e) {
							showLoadAlert();
						}
					}else
						gaussianFilter=new GaussianFilter(imageMatrix,size,devX,devY);
					if(gaussianFilter!=null){
						filtrImage(gaussianFilter);
					}
				}
				//Laplacian filter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==2){
					int size=getSize();
					double scale=getScale();
					double delta=getDelta();
					LaplacianFilter laplacianFilter = null;
					if(imageMatrix==null)
						try {
							laplacianFilter=new LaplacianFilter(path,size,scale,delta);
						} catch (Exception e) {
							showLoadAlert();
						}
					else
						laplacianFilter=new LaplacianFilter(imageMatrix,size,scale,delta);
					if(laplacianFilter!=null){
						filtrImage(laplacianFilter);
					}
				}
				//Sobel filter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==3){
					if(checkIfDerivativeSelected()){	
						int size=getSize();
						double scale=getScale();
						double delta=getDelta();
						int dx=getDerivativeX();
						int dy=getDerivativeY();
						SobelFilter sobelFilter = null;
						if(imageMatrix==null){
							try {
								sobelFilter=new SobelFilter(path,size,dx,dy,scale,delta);
							} catch (Exception e) {
								showLoadAlert();
							}
						}else
							sobelFilter=new SobelFilter(imageMatrix,size,dx,dy,scale,delta);
						if(sobelFilter!=null){
							filtrImage(sobelFilter);
						}
					}else{
						showDerivativeAlert();
					}
				}
				//CannyFilter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==4){
					int size=getSize();
					double lower=getLowerThreshold();
					double upper=getUpperThreshold();
					CannyFilter cannyFilter = null;
					if(imageMatrix==null){
						try {
							cannyFilter=new CannyFilter(path,size,lower,upper);
						} catch (Exception e) {
							showLoadAlert();
						}
					}else
						cannyFilter=new CannyFilter(imageMatrix,size,lower,upper);
					if(cannyFilter!=null){
						filtrImage(cannyFilter);
					}
				}
				//BilateralFilter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==5 && !isAdaptive()){
					int size=getSize();
					double devColor=getSigmaColor();
					double devSpace=getSigmaSpace();
					BilateralFilter bilateralFilter = null;
					if(imageMatrix==null){
						try {
							bilateralFilter=new BilateralFilter(path,size,devColor,devSpace);
						} catch (Exception e) {
							showLoadAlert();
						}
					}else
						bilateralFilter=new BilateralFilter(imageMatrix,size,devColor,devSpace);
					if(bilateralFilter!=null){
						filtrImage(bilateralFilter);
					}
				}
				//AdaptiveBilateralFilter
				else if(chooseMethod.getSelectionModel().getSelectedIndex()==5 && isAdaptive()){
					int size=getSize();
					double devSpace=getSigmaSpace();
					AdaptiveBilateralFilter adaptiveBilateralFilter = null;
					if(imageMatrix==null){
						try {
							adaptiveBilateralFilter=new AdaptiveBilateralFilter(path,size,devSpace);
						} catch (Exception e) {
							showLoadAlert();
						}
					}else
						adaptiveBilateralFilter=new AdaptiveBilateralFilter(imageMatrix,size,devSpace);
					if(adaptiveBilateralFilter!=null){
						filtrImage(adaptiveBilateralFilter);
					}
				}
			}else{
				showMatrixAlert();
			}
		}
		//Fourier Transform
		else if(chooseMethod.getSelectionModel().getSelectedIndex()==6){
			FourierTransform fourierTransform = null;
			if(imageMatrix==null){
				try{
					fourierTransform=new FourierTransform(path,0);
				}catch (Exception e){
					showLoadAlert();
				}
			}else
				fourierTransform=new FourierTransform(imageMatrix,0);
			if(fourierTransform!=null){
				filtrImage(fourierTransform);
			}
		}
		//Brightness change
		else if(chooseMethod.getSelectionModel().getSelectedIndex()==7){
			double betaBrightness = getBrightness() * 255;
			BrightnessChanger brightnessChanger = null;
			if(imageMatrix==null){
				try{
					brightnessChanger = new BrightnessChanger(path, 0, betaBrightness);
				}catch(Exception e){
					showLoadAlert();
				}
			}else
				brightnessChanger = new BrightnessChanger(imageMatrix,0, betaBrightness);
			if(brightnessChanger!=null){
				filtrImage(brightnessChanger);
			}
		}
		//Contrast change
		else if(chooseMethod.getSelectionModel().getSelectedIndex()==8){
			double alphaContrast = getBrightness();
			ContrastChanger contrastChanger = null;
			if(imageMatrix==null){
				try{
					contrastChanger = new ContrastChanger(path,0,alphaContrast);
				}catch(Exception e){
					showLoadAlert();
					e.printStackTrace();
				}
			}else
				contrastChanger = new ContrastChanger(imageMatrix,0,alphaContrast);
			if(contrastChanger!=null){
				filtrImage(contrastChanger);
			}
		}
		//Thresholding
		else if(chooseMethod.getSelectionModel().getSelectedIndex()==9){
			double thresholdValue = getThreshold();
			Thresholding thresholding = null;
			if(imageMatrix==null){
				try{
					thresholding = new Thresholding(path,0,thresholdValue);
				}catch(Exception e){
					showLoadAlert();
				}
			}else
				thresholding = new Thresholding(imageMatrix,0,thresholdValue);
			if(thresholding!=null){
				filtrImage(thresholding);
			}
		}
		//Adaptive Thresholding
		else if(chooseMethod.getSelectionModel().getSelectedIndex()==10){
			double constantC = getConstC();
			int blockSizeValue = getBlockSize();
			try{
				if(blockSizeValue%2==0)
					throw new Exception();
			}catch(Exception e){
				showBlockSizeAlert();
			}
			AdaptiveThresholding adaptiveThresholding = null; 
			if(imageMatrix==null){
				try{
					adaptiveThresholding = new AdaptiveThresholding(path,blockSizeValue,constantC);
				}catch(Exception e){
					showLoadAlert();
				}
			}else
				adaptiveThresholding = new AdaptiveThresholding(imageMatrix,blockSizeValue,constantC);
			if(adaptiveThresholding!=null){
				filtrImage(adaptiveThresholding);
			}
		}
	}
	
	/*
	 * Load correct filter
	 */
	private void filtrImage(Filter filter){
		filter.filtrImage();
		Image fImage=filter.returnFiltredImage();
		imageMatrix=filter.returnMatrix();
		imageView.setImage(fImage);
		saveImage.setDisable(false);
		resetImage.setDisable(false);
	}
	
	/*
	 * Check if derivative X and Y are selected correctly.
	 */
	private boolean checkIfDerivativeSelected(){
		if(!derivativeX0.isSelected() && !derivativeX1.isSelected() && !derivativeX2.isSelected())
			return false;
		if(!derivativeY0.isSelected() && !derivativeY1.isSelected() && !derivativeY2.isSelected())
			return false;
		if(derivativeX0.isSelected() && derivativeY0.isSelected())
			return false;
		return true;
	}
	
	/*
	 * Shows alert that block size isn't odd value.
	 */
	private void showBlockSizeAlert(){
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText("B³êdny rozmiar bloku!");
		alert.setContentText("Aby progowanie by³o mo¿liwe rozmiar bloku musi byc wartoœci¹ nieparzyst¹.");
		alert.showAndWait();
	}
	
	/*
	 * Shows alert that image does not load.
	 */
	private void showLoadAlert(){
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText("B³¹d za³adowania obrazu!");
		alert.setContentText("Aby przefiltrowaæ obraz musi mieæ on min. 3 kana³y.");
		alert.showAndWait();
	}
	
	/*
	 * Shows alert that derivatives X and Y are unchecked.
	 */
	private void showDerivativeAlert(){
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText("Nie zaznaczono poprawnie rzêdu pochodnej dla X lub Y!");
		alert.setContentText("Aby dokonaæ filtracji obrazu tym filtrem nale¿y wybraæ rz¹d pochodnej tak by suma rzêdów by³a wiêksza od 0.");
		alert.showAndWait();
	}
	
	/*
	 * Shows alert that matrix's size is unchecked.
	 */
	private void showMatrixAlert(){
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText("Nie zaznaczono rozmiaru macierzy!");
		alert.setContentText("Aby dokonaæ filtracji obrazu tym filtrem nale¿y wybraæ rozmiar macierzy.");
		alert.showAndWait();
	}
	
	/*
	 * Show alert that both edges are unchecked.
	 */
	private void showEdgesAlert(){
		Alert alert=new Alert(AlertType.ERROR);
		alert.setTitle("B³¹d");
		alert.setHeaderText("Nie zaznaczono rodzaju wykrywanych krawêdzi!");
		alert.setContentText("Aby dokonaæ filtracji obrazu tym filtrem nale¿y wybraæ przynajmniej jeden rodzaj krawêdzi, które maj¹ byæ wykryte.");
		alert.showAndWait();
	}
	
	/*
	 * Return true if filter is adaptive
	 */
	private boolean isAdaptive(){
		return adaptiveFilter.isSelected();
	}
	
	/*
	 * Return constant c from slider.
	 */
	private double getConstC(){
		return constC.getValue();
	}
	
	/*
	 * Return block size from slider.
	 */
	private int getBlockSize(){
		return (int)blockSize.getValue();
	}
	
	/*
	 * Return threshold from slider.
	 */
	private double getThreshold(){
		return thresholding.getValue();
	}
	
	/*
	 * Return betaBrighntess from slider.
	 */
	private double getBrightness(){
		return brightness.getValue()/100;
	}
	
	/*
	 * Return sigmaColor from slider.
	 */
	private double getSigmaColor(){
		return sigmaColor.getValue();
	}
	
	/*
	 * Return sigmaSpace from slider.
	 */
	private double getSigmaSpace(){
		return sigmaSpace.getValue();
	}
	
	/*
	 * Return lower threshold from slider.
	 */
	private double getLowerThreshold(){
		return lowerThreshold.getValue();
	}
	
	/*
	 * Return upper threshold from slider.
	 */
	private double getUpperThreshold(){
		return upperThreshold.getValue();
	}
	
	/*
	 * Return derivative X from checkboxes.
	 */
	private int getDerivativeX(){
		if(derivativeX0.isSelected())
			return 0;
		if(derivativeX1.isSelected())
			return 1;
		return 2;
	}
	
	/*
	 * Return derivative Y from checkboxes.
	 */
	private int getDerivativeY(){
		if(derivativeY0.isSelected())
			return 0;
		if(derivativeY1.isSelected())
			return 1;
		return 2;
	}
	
	/*
	 * Return scale from slider.
	 */
	private double getScale(){
		return scaleSlider.getValue();
	}
	
	/*
	 * Return delta from slider.
	 */
	private double getDelta(){
		return deltaSlider.getValue();
	}
	
	/*
	 * Return standard deviation in X direction from slider.
	 */
	private double getDeviationX(){
		return standardDeviationX.getValue();
	}
	
	/*
	 * Return standard deviation in Y direction from slider.
	 */
	private double getDeviationY(){
		return standardDeviationY.getValue();
	}
	
	/*
	 * Return matrix's size which choose user.
	 */
	private int getSize(){
		int size=0;
		if(matrix3x3.isSelected())
			size=3;
		else if(matrix5x5.isSelected())
			size=5;
		else if(matrix7x7.isSelected())
			size=7;
		return size;
	}
	
	/*
	 * Save image in jpg or png format.
	 */
	@FXML
	private void saveImageasFile(){
		FileChooser fileChooser=new FileChooser();
		
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG(*.jpg )" , "*.jpg"),
												new FileChooser.ExtensionFilter("PNG (*.png)", "*.png" ));
		
		File file=fileChooser.showSaveDialog(null);
		Image tempImage=imageView.getImage();
		if(file!=null){
			BufferedImage bImage = SwingFXUtils.fromFXImage(tempImage, null);
		    try {
		      ImageIO.write(bImage, "png", file);
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		}
	}
	
	@FXML
	private void getOriginalImage(){
		imageView.setImage(originalImage);
		imageMatrix=null;
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
